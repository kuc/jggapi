package pl.mn.communicator;

import java.io.IOException;
import java.net.UnknownHostException;
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
 * 
 * @author mnaglik
 */
public interface IConnection {
	/**
	 * Dodaj listenera u�ytkownik�w.<BR>
	 * Obs�uguje odpowiednie zdarzenia zwi�zane z u�ytkownikami
	 * takie jak pryj�cie i odej�cie u�ytkownika
	 * 
	 * @see UserListener
	 * @param userListener obiekt listenera
	 */
	public void addUserListener(UserListener userListener);
	/**
	 * Usuwa listenera u�ytkownik�w.<BR>
	 * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see UserListener
	 */
	public void removeUserListener();
	/**
	 * Dodaj listenera zwi�zanego z po��czeniem.<BR>
	 * Obs�uguje on takie zdarzenia jak nawi�zanie po��czenia,
	 * zerwanie po��czenia itp.
	 * 
	 * @see ConnectionListener
	 * @param connectionListener obiekt listenera
	 */
	public void addConnectionListener(ConnectionListener connectionListener);
	/**
	 * Usuwa listenera zwi�zanego z po��czeniem.<BR>
	 * J�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see ConnectionListener
	 */
	public void removeConnectionListener();
	/**
	 * Dodaje listenera wiadomo�ci.<BR>
	 * Obs�uguje on takie zdarzenia jak nadej�cie wiadomo�ci.
	 * 
	 * @see MessageListener 
	 * @param messageListener obiekt listenera
	 */
	public void addMessageListener(MessageListener messageListener);
	/**
	 * Usuwa listenera wiadomo�ci.<BR>
	 * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
	 * 
	 * @see MessageListener
	 */
	public void removeMessageListener();
	/**
	 * Pod��cz sie do serwera rozm�w.<BR>
	 * Pr�buje ��czy� si� z serwerem rozm�w, na podstawie danych<BR>
	 * z konstruktora.<BR>
	 * W wypadku niepowodzenie wyrzuca odpowiednie wyj�tki
	 * 
	 * @throws UnknownHostException nieznany serwer 
	 * @throws IOException nie powiodla si� pr�ba po��czenia - nie ma po��czenia sieciowego?
	 */
	public void connect() throws UnknownHostException, IOException;
	/**
	 * Zamyka po��czenie z serwerem rozm�w.
	 * 
	 * @throws IOException b��d przy zamykaniu po��czenia
	 */
	public void disconnect() throws IOException;
	/**
	 * Wy�lij wiadomo�� do serwera rozm�w.
	 * 
	 * @see AbstractMessage
	 * @param message wiadomo�� do wys�ania.
	 */
	public void sendMessage(IMessage message)
		throws IOException;
	/**
	 * Zmien aktualny status u�ytkownika.<BR>
	 * 
	 * @param status - kolejny status
	 */
	public void changeStatus(IStatus status)
		throws IOException;

	/**
	 * Wy�lij list� monitorowanych u�ytkownik�w.<br>
	 * Lista zawiera u�ytkownik�w dla kt�rych serwer
	 * przesy�a informacje o zmianie statusu
	 * @param userList
	 */
	public void sendMonitoredUserList(Collection userList);
	
	/**
	 * Dodaj u�ytkownika dla kt�rego do u�ytkownik�w monitorowanych.<br>
	 * Dla dodanego u�ytkownika b�dzie przesy�ana informacja o zmianie
	 * statusu.
	 * @param user
	 */
	public void addMonitoredUser(IUser user);
	
	/**
	 * Usu� u�ytkownika z listy u�ytkownik�w monitorowanych.<br>
	 * Dla podanego u�ytkownika nie b�dzie przesy�ana informacja
	 * o zmianie statusu.
	 * @param user
	 */
	public void removeMonitoredUser(IUser user);
}