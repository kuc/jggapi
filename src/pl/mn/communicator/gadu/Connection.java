package pl.mn.communicator.gadu;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;

import org.apache.log4j.Logger;

import pl.mn.communicator.ILocalUser;
import pl.mn.communicator.IMessage;
import pl.mn.communicator.IServer;
import pl.mn.communicator.IStatus;


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
 * @author mnaglik
 */
public final class Connection extends pl.mn.communicator.AbstractConnection {
	private static Logger logger = Logger.getLogger(Connection.class);
	private IServer server;
	private ILocalUser localUser;

	private ConnectionThread connectionThread;


	private boolean isConnected = false;
	/**
	 * Konstruktor polaczenia z serwerem gg.
	 * 
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
	 * 
	 * @throws UnknownHostException nieznany serwer 
	 * @throws IOException nie powiodla si� pr�ba po��czenia - nie ma po��czenia sieciowego?
	 */
	public void connect() throws UnknownHostException, IOException {
		connectionThread.openConnection();
		isConnected = true;
	}

	/**
	 * Zamyka po��czenie z serwerem gg.
	 * 
	 * @throws IOException b��d przy zamykaniu po��czenia
	 */
	public void disconnect() throws IOException {
		changeStatus(new Status(Status.OFF_LINE));
		connectionThread.closeConnection();
		isConnected = false;
	}

	/**
	 * Wy�lij wiadomo�� do serwera gg.
	 * 
	 * @see Message
	 * @param message wiadomo�� do wys�ania.
	 */
	public void sendMessage(IMessage message) throws IOException {
		if (isConnected){
			GGSendMsg messageOut = new GGSendMsg(message);
			connectionThread.sendPackage(messageOut);
		}
	}
	
	/**
	 * Zmien aktualny status.<BR>
	 * 
	 * @param status - kolejny status
	 */
	public void changeStatus(IStatus status) throws IOException{
		if (isConnected){
			GGStatus newStatus = new GGStatus(status.getStatus());
			connectionThread.sendPackage(newStatus);
		}
	}

	/**
	 * @author mnaglik
	 * 
	 * Watek polaczenia
	 */
	private class ConnectionThread implements Runnable {
		private Socket socket;
		private Thread thread;
		private BufferedInputStream dataInput;
		private BufferedOutputStream dataOutput;
		private int pingTimeCount = 0;
		/**
		 * @see java.lang.Runnable#run()
		 */
		public void run() {
			System.out.println("run..");

			byte[] headerData = new byte[8];

			try {
				while (thread != null) {
					if (dataInput.available() > 0) {
						dataInput.read(headerData);

						decodePocket(new GGHeader(headerData));
					} else {

						pingTimeCount++;
						if (pingTimeCount > 200) {
							// TODO poprawne wysylanie pinga
							//sendPackage(GGPing.getPing());
							System.out.println("ping...");
							pingTimeCount = 0;
						}

					}
					Thread.sleep(100);
				}
			} catch (IOException e) {
				System.out.println("e" + e);
			} catch (InterruptedException e) {
				System.out.println("e" + e);
			} finally {
				System.out.println("doing finally..");
				try {
					closeConnection();
				} catch (IOException e) {
					System.err.println(e);
				}
			}
			System.out.println("run ended");
		}

		/**
		 * Dekoduje pakiet i wywoluje odpowiednia metode
		 * z konkretnego listenera
		 * 
		 * @param ggHeader naglowek wiadomosci
		 * @throws IOException
		 */
		private void decodePocket(GGHeader ggHeader) throws IOException {
			byte[] keyBytes = new byte[ggHeader.getLength()];
			dataInput.read(keyBytes);

			switch (ggHeader.getType()) {
				case 1 :

					GGWelcome ggWelcome = new GGWelcome(keyBytes);
					GGLogin ggLogin =
						new GGLogin(
							socket.getLocalAddress().getAddress(),
							socket.getLocalPort(),
							localUser,
							ggWelcome);
					sendPackage(ggLogin);
					break;
				case 3 :
					if (connectionListener != null)
						connectionListener.connectionEstablished();
					System.err.println("Login ok");
					break;
				case 9 :
					if (connectionListener != null)
						connectionListener.connectionError("9");
					System.err.println("Error logging 9");
					this.closeConnection();
					break;
				case 10 :
					GGRecvMsg message = new GGRecvMsg(keyBytes);
					
					IMessage messageOut = new Message(message.getSender(),message.getMessage());
					if (messageListener != null)
						messageListener.messageArrived(messageOut);
					break;
				case 11 :
					if (connectionListener != null)
						connectionListener.connectionError("11");
					System.err.println("Error logging 11");
					this.closeConnection();
					break;

				default :
					System.err.println("brak");
			}

		}

		private synchronized void sendPackage(GGOutgoingPackage outgoingPackage) {
			int header = outgoingPackage.getHeader();
			int length = outgoingPackage.getLength();
			
			byte [] contents = outgoingPackage.getContents();
		
			try {
				dataOutput.write(GGConversion.intToByte(header));
				if (length>0){
					dataOutput.write(GGConversion.intToByte(length));
					dataOutput.write(contents);
				}
				dataOutput.flush();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		/**
		 * Otworz polaczenie
		 * 
		 * @throws UnknownHostException
		 * @throws IOException
		 */
		private void openConnection()
			throws UnknownHostException, IOException {

			socket = new Socket(server.getAddress(), server.getPort());

			dataInput = new BufferedInputStream(socket.getInputStream());
			dataOutput = new BufferedOutputStream(socket.getOutputStream());

			thread = new Thread(this);
			thread.start();

		}

		/**
		 * Zamknij polaczenie
		 * 
		 * @throws IOException
		 */
		private void closeConnection() throws IOException {
			thread = null;
			dataInput = null;
			dataOutput = null;
			socket.close();
		}
	}

}
