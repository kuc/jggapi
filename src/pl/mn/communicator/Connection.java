/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;

import pl.mn.communicator.gadu.GGAddNotify;
import pl.mn.communicator.gadu.GGConversion;
import pl.mn.communicator.gadu.GGHeader;
import pl.mn.communicator.gadu.GGLogin;
import pl.mn.communicator.gadu.GGNewStatus;
import pl.mn.communicator.gadu.GGNotify;
import pl.mn.communicator.gadu.GGNotifyReply;
import pl.mn.communicator.gadu.GGOutgoingPackage;
import pl.mn.communicator.gadu.GGPing;
import pl.mn.communicator.gadu.GGRecvMsg;
import pl.mn.communicator.gadu.GGRemoveNotify;
import pl.mn.communicator.gadu.GGSendMsg;
import pl.mn.communicator.gadu.GGStatus;
import pl.mn.communicator.gadu.GGWelcome;
import pl.mn.communicator.logger.Logger;
import pl.mn.communicator.util.Util;

/**
 * Implementacja klienta gg.<BR>
 * S�u�y do tworzenia po��czenia.
 * <BR><BR>
 * <i>Przyk�ad u�ycia:</i><BR><BR>
 * <code>
 * LocalUser user = new LocalUser(1234,"password");<BR>
 * Server server = ServerAddress.getHost(user);<BR>
 * Server s = new Server(server.user);<BR>
 * Connection c = new Connection();<BR><BR>
 * try{<BR>
 * &nbsp; &nbsp; c.connect();<BR>
 * }catch(Exception e){<BR>
 * &nbsp; &nbsp; ...<BR>
 * }
 * </code>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @version $Id: Connection.java,v 1.1 2004-10-27 00:51:54 winnetou25 Exp $
 */
public final class Connection extends pl.mn.communicator.AbstractConnection {

	private static Logger logger = Logger.getLogger(Connection.class);
    private IServer server;
    private ILocalUser localUser;
    private ConnectionThread connectionThread;
    private boolean isConnected = false;

    /**
     * Konstruktor polaczenia z serwerem gg.
     * @param server serwer gg.
     * @param localUser uzytkownik gg.
     */
    public Connection(IServer server, ILocalUser localUser) {
        this.server = server;
        this.localUser = localUser;
        connectionThread = new ConnectionThread();
    }

    /**
     * Pod��cz sie do serwera gg.<BR>
     * Pr�buje ��czy� si� z serwerem gg, na podstawie danych<BR>
     * z konstruktora.<BR>
     * W wypadku niepowodzenie wyrzuca odpowiednie wyj�tki
     * @throws IOException nie powiodla si� pr�ba po��czenia
     * - nie ma po��czenia sieciowego?
     */
    public void connect() throws IOException {
        connectionThread.openConnection();
        isConnected = true;
    }

    /**
     * Zamyka po��czenie z serwerem gg.
     * @throws IOException b��d przy zamykaniu po��czenia
     */
    public void disconnect() throws IOException {
        setStatus(new Status(Status.OFF_LINE));
        connectionThread.closeConnection();
        isConnected = false;
        if (connectionListener != null) {
        	connectionListener.disconnected();
        }
    }

    /**
     * Wy�lij wiadomo�� do serwera gg.
     * @see Message
     * @param message wiadomo�� do wys�ania.
     * @throws IOException b��d wysy�ania wiadomo�ci
     */
    public void sendMessage(IMessage message) throws IOException {
        if (isConnected) {
            GGSendMsg messageOut = new GGSendMsg(message);
            connectionThread.sendPackage(messageOut);
        }
    }

    private IStatus actualStatus = new Status(Status.OFF_LINE);
    /**
     * Zmien aktualny status.<BR>
     * @param status - kolejny status
     * @throws IOException {@inheritDoc}
     */
    public void setStatus(IStatus status) throws IOException {
    	logger.debug("zmieniam status na "+status.toString());
    	GGNewStatus newStatus = new GGNewStatus(status.getStatus(), "");
        connectionThread.sendPackage(newStatus);
        actualStatus = status;
    }
    
    /**
     * Pobierz aktualny status.
     * @return aktualny status
     */
    public IStatus getStatus() {
    	return actualStatus;
    }
    
    /**
     * @see pl.mn.communicator.IConnection
     * #addMonitoredUser(pl.mn.communicator.IUser)
     */
    public void addMonitoredUser(IUser user) throws IOException {
        if (isConnected) {
            logger.debug("dodaje monitorowanego uzytkownika: " +
                user.getNumber());

            GGAddNotify addNotify = new GGAddNotify(user.getNumber());
            connectionThread.sendPackage(addNotify);
        }
    }

    /**
     * @see pl.mn.communicator.IConnection
     * #removeMonitoredUser(pl.mn.communicator.IUser)
     */
    public void removeMonitoredUser(IUser user) throws IOException {
        if (isConnected) {
            logger.debug("usuwam monitorowanego uzytkownika: " +
                user.getNumber());

            GGRemoveNotify removeNotify = new GGRemoveNotify(user.getNumber());
            connectionThread.sendPackage(removeNotify);
        }
    }

    /**
     * Watek polaczenia
     */
    private class ConnectionThread implements Runnable {
        private static final int HEADER_LENGTH = 8;
        private static final int PING_COUNT = 1000;
        private static final int THREAD_SLEEP_TIME = 100;
        private static final int GG_PACKAGE_WELCOME = 1;
        private static final int GG_PACKAGE_LOGIN_OK = 3;
        private static final int GG_PACKAGE_LOGIN_ERROR = 9;
        private static final int GG_PACKAGE_MESSAGE = 10;
        private static final int GG_PACKAGE_CONNECTION_ERROR = 11;
        private static final int GG_NOTIFY_REPLY = 0x11;
        private static final int GG_STATUS = 0x0f;
        private Socket socket;
        private Thread thread;
        private BufferedInputStream dataInput;
        private BufferedOutputStream dataOutput;
        private int pingTimeCount = 0;

        /**
         * @see java.lang.Runnable#run()
         */
        public void run() {
            logger.debug("Run...");

            byte[] headerData = new byte[HEADER_LENGTH];

            try {
                while (thread != null) {
                    if (dataInput.available() > 0) {
                        dataInput.read(headerData);

                        decodePocket(new GGHeader(headerData));
                    } else {
                        pingTimeCount++;

                        if (pingTimeCount > PING_COUNT) {
                            sendPackage(GGPing.getPing());
                            logger.debug("Ping...");
                            pingTimeCount = 0;
                        }
                    }

                    Thread.sleep(THREAD_SLEEP_TIME);
                }
            } catch (IOException e) {
                logger.error(e, e);
            } catch (InterruptedException e) {
                logger.error(e, e);
            } finally {
                logger.debug("Doing finally");

                try {
                    closeConnection();
                } catch (IOException e) {
                    logger.error(e);
                }
            }

            logger.debug("Run ended.");
        }

        /**
         * Dekoduje pakiet i wywoluje odpowiednia metode
         * z konkretnego listenera
         * @param ggHeader naglowek wiadomosci
         * @throws IOException b��d dekodowania pakietu
         */
        private void decodePocket(GGHeader ggHeader) throws IOException {
            byte[] keyBytes = new byte[ggHeader.getLength()];
            dataInput.read(keyBytes);
            logger.debug("Pakiet przychodzacy: " +
                Util.bytesToString(keyBytes));

            switch (ggHeader.getType()) {
            case GG_PACKAGE_WELCOME:

                GGWelcome ggWelcome = new GGWelcome(keyBytes);
                GGLogin ggLogin = new GGLogin(socket.getLocalAddress()
                                                    .getAddress(),
                        socket.getLocalPort(), localUser, ggWelcome);
                sendPackage(ggLogin);

                break;

            case GG_PACKAGE_LOGIN_OK:
                logger.debug("Login OK");

                if (monitoredUsers != null) {
                    logger.debug("Wysylam liste uzytkownikow do serwera");
                    sendPackage(new GGNotify(
                            (IUser[]) monitoredUsers.toArray(new IUser[0])));
                }

                setStatus(new Status(Status.ON_LINE));

                
                if (connectionListener != null) {
                    connectionListener.connectionEstablished();
                }
                break;

            case GG_PACKAGE_LOGIN_ERROR:

                if (connectionListener != null) {
                    connectionListener.connectionError("9");
                }

                logger.debug("Loggin ERROR");
                this.closeConnection();

                break;

            case GG_PACKAGE_MESSAGE:

                GGRecvMsg message = new GGRecvMsg(keyBytes);
                IMessage messageOut = new Message(message.getSender(),
                        message.getMessage());

                if (messageListener != null) {
                    messageListener.messageArrived(messageOut);
                }

                break;

            case GG_PACKAGE_CONNECTION_ERROR:

                if (connectionListener != null) {
                    connectionListener.connectionError("11");
                }

                logger.debug("Error logging 11");
                this.closeConnection();

                break;

            case GG_NOTIFY_REPLY:

                GGNotifyReply notify = new GGNotifyReply(keyBytes);
                Map usersStatus = notify.getUsersState();
                Iterator i = usersStatus.keySet().iterator();

                if (userListener != null) {
                    while (i.hasNext()) {
                        IUser user = (User) i.next();
                        IStatus status = (Status) usersStatus.get(user);
                        userListener.userStatusChanged(user, status);
                        logger.debug("Uzytkownik " + user +
                            " zmienil status: " + status);
                    }
                }

                break;

            case GG_STATUS:

                GGStatus status = new GGStatus(keyBytes);

                if (userListener != null) {
                    userListener.userStatusChanged(status.getUser(),
                        status.getStatus());
                    logger.debug("Uzytkownik " + status.getUser() +
                        " zmienil status: " + status.getStatus());
                }

                break;

            default:
                logger.debug("Unknown package");
            }
        }

        /**
         * @param outgoingPackage pakiet wychodz�cy
         */
        private synchronized void sendPackage(GGOutgoingPackage outgoing) {
            int header = outgoing.getHeader();
            int length = outgoing.getLength();

            byte[] contents = outgoing.getContents();

            try {
                dataOutput.write(GGConversion.intToByte(header));

                dataOutput.write(GGConversion.intToByte(length));
                if (length > 0) {
                    dataOutput.write(contents);
                }

                dataOutput.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        /**
         * Otworz polaczenie
         * @throws UnknownHostException
         * @throws IOException b��d otwierania po��czenia
         */
        private void openConnection() throws IOException {
            socket = new Socket(server.getAddress(), server.getPort());

            dataInput = new BufferedInputStream(socket.getInputStream());
            dataOutput = new BufferedOutputStream(socket.getOutputStream());

            thread = new Thread(this);
            thread.start();
        }

        /**
         * Zamknij polaczenie
         * @throws IOException b��d zamykania po��czenia
         */
        private void closeConnection() throws IOException {
            thread = null;
            dataInput = null;
            dataOutput = null;
            socket.close();
        }
    }

}