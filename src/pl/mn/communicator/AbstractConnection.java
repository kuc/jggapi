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

import pl.mn.communicator.logger.Logger;

import java.util.Collection;


/**
 * Po��czenie z serwerem gg.<BR>
 * S�u�y do tworzenia po��czenia.
 * <BR><BR>
 * <i>Przyk�ad u�ycia:</i><BR><BR>
 * <code>
 * AbstractLocalUser user = new XXXLocalUser(1234,"password");<BR>
 * AbstractServer server = XXXServerAddress.getHost(user);<BR>
 * AbstractServer s = new XXXServer(server.user);<BR>
 * AcstractConnection c = new XXXConnection();<BR><BR>
 * try{<BR>
 * &nbsp; &nbsp; c.connect();<BR>
 * }catch(Exception e){<BR>
 * &nbsp; &nbsp; ...<BR>
 * }
 * </code>
 * @version $Revision: 1.11 $
 * @author mnaglik
 */
public abstract class AbstractConnection implements IConnection {
    private static Logger logger = Logger.getLogger(AbstractConnection.class);

    /**
     * Lista monitorowanych u�ytkownik�w.
     */
    protected Collection monitoredUsers;

    /**
     * Listener u�ytkownik�w
     */
    protected UserListener userListener = null;

    /**
     * Listener po��czenia
     */
    protected ConnectionListener connectionListener = null;

    /**
     * Listener wiadomo�ci
     */
    protected MessageListener messageListener = null;

    /**
     * Dodaj listenera u�ytkownik�w.<BR>
     * Obs�uguje odpowiednie zdarzenia zwi�zane z u�ytkownikami
     * takie jak pryj�cie i odej�cie u�ytkownika
     * @see UserListener
     * @param userListener obiekt listenera
     */
    public void addUserListener(UserListener userListener) {
        this.userListener = userListener;
    }

    /**
     * Usuwa listenera u�ytkownik�w.<BR>
     * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see UserListener
     */
    public void removeUserListener() {
        this.userListener = null;
    }

    /**
     * Dodaj listenera zwi�zanego z po��czeniem.<BR>
     * Obs�uguje on takie zdarzenia jak nawi�zanie po��czenia,
     * zerwanie po��czenia itp.
     * @see ConnectionListener
     * @param connectionListener obiekt listenera
     */
    public void addConnectionListener(ConnectionListener connectionListener) {
        this.connectionListener = connectionListener;
    }

    /**
     * Usuwa listenera zwi�zanego z po��czeniem.<BR>
     * J�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see ConnectionListener
     */
    public void removeConnectionListener() {
        this.connectionListener = null;
    }

    /**
     * Dodaje listenera wiadomo�ci.<BR>
     * Obs�uguje on takie zdarzenia jak nadej�cie wiadomo�ci.
     * @see MessageListener
     * @param messageListener obiekt listenera
     */
    public void addMessageListener(MessageListener messageListener) {
        this.messageListener = messageListener;
    }

    /**
     * Usuwa listenera wiadomo�ci.<BR>
     * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see MessageListener
     */
    public void removeMessageListener() {
        this.messageListener = null;
    }

    /**
     * Ustaw list� monitorowanych u�ytkownik�w.
     * @param userList lista u�ytkownik�w
     */
    public void setMonitoredUserList(Collection userList) {
        this.monitoredUsers = userList;
    }
}
