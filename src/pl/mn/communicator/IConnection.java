/*
 * Created on 2003-10-01
 *
 * To change the template for this generated file go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
package pl.mn.communicator;

import java.io.IOException;
import java.net.UnknownHostException;

/**
 * @author mna
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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
	public void sendMessage(AbstractMessage message)
		throws IOException;
	/**
	 * Zmien aktualny status u�ytkownika.<BR>
	 * 
	 * @param status - kolejny status
	 */
	public void changeStatus(AbstractStatus status)
		throws IOException;
}