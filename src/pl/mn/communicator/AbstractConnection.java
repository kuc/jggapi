package pl.mn.communicator;

import org.apache.log4j.Logger;

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
 * 
 * @author mnaglik
 */
public abstract class AbstractConnection implements IConnection {
	private static Logger logger = Logger.getLogger(AbstractConnection.class);
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
	 * 
	 * @see UserListener
	 * @param userListener obiekt listenera
	 */
	public void addUserListener(UserListener userListener) {
		this.userListener = userListener;
	}
	/**
	 * Usuwa listenera u�ytkownik�w.<BR>
	 * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see UserListener
	 */
	public void removeUserListener() {
		this.userListener = null;
	}

	/**
	 * Dodaj listenera zwi�zanego z po��czeniem.<BR>
	 * Obs�uguje on takie zdarzenia jak nawi�zanie po��czenia,
	 * zerwanie po��czenia itp.
	 * 
	 * @see ConnectionListener
	 * @param connectionListener obiekt listenera
	 */
	public void addConnectionListener(ConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}
	/**
	 * Usuwa listenera zwi�zanego z po��czeniem.<BR>
	 * J�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see ConnectionListener
	 */
	public void removeConnectionListener() {
		this.connectionListener = null;
	}
	/**
	 * Dodaje listenera wiadomo�ci.<BR>
	 * Obs�uguje on takie zdarzenia jak nadej�cie wiadomo�ci.
	 * 
	 * @see MessageListener 
	 * @param messageListener obiekt listenera
	 */
	public void addMessageListener(MessageListener messageListener) {
		this.messageListener = messageListener;
	}

	/**
	 * Usuwa listenera wiadomo�ci.<BR>
	 * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see MessageListener
	 */
	public void removeMessageListener() {
		this.messageListener = null;
	}
}