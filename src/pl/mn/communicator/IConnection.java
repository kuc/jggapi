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

import java.io.IOException;

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
 * @version $Revision: 1.12 $
 * @author mnaglik
 */
public interface IConnection {
    /**
     * Dodaj listenera u�ytkownik�w.<BR>
     * Obs�uguje odpowiednie zdarzenia zwi�zane z u�ytkownikami
     * takie jak pryj�cie i odej�cie u�ytkownika
     * @see UserListener
     * @param userListener obiekt listenera
     */
    void addUserListener(UserListener userListener);

    /**
     * Usuwa listenera u�ytkownik�w.<BR>
     * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see UserListener
     */
    void removeUserListener();

    /**
     * Dodaj listenera zwi�zanego z po��czeniem.<BR>
     * Obs�uguje on takie zdarzenia jak nawi�zanie po��czenia,
     * zerwanie po��czenia itp.
     * @see ConnectionListener
     * @param connectionListener obiekt listenera
     */
    void addConnectionListener(ConnectionListener connectionListener);

    /**
     * Usuwa listenera zwi�zanego z po��czeniem.<BR>
     * J�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see ConnectionListener
     */
    void removeConnectionListener();

    /**
     * Dodaje listenera wiadomo�ci.<BR>
     * Obs�uguje on takie zdarzenia jak nadej�cie wiadomo�ci.
     * @see MessageListener
     * @param messageListener obiekt listenera
     */
    void addMessageListener(MessageListener messageListener);

    /**
     * Usuwa listenera wiadomo�ci.<BR>
     * Je�eli nie ma aktywnego listenera nic si� nie dzieje.
     * @see MessageListener
     */
    void removeMessageListener();

    /**
     * Pod��cz sie do serwera rozm�w.<BR>
     * Pr�buje ��czy� si� z serwerem rozm�w, na podstawie danych<BR>
     * z konstruktora.<BR>
     * W wypadku niepowodzenie wyrzuca odpowiednie wyj�tki
     * @throws UnknownHostException nieznany serwer
     * @throws IOException nie powiodla si� pr�ba po��czenia
     * - nie ma po��czenia sieciowego?
     */
    void connect() throws IOException;

    /**
     * Zamyka po��czenie z serwerem rozm�w.
     * @throws IOException b��d przy zamykaniu po��czenia
     */
    void disconnect() throws IOException;

    /**
     * Wy�lij wiadomo�� do serwera rozm�w.
     * @see AbstractMessage
     * @param message wiadomo�� do wys�ania.
     * @throws IOException b��d wysy�ania wiadomo�ci
     */
    void sendMessage(IMessage message) throws IOException;

    /**
     * Zmien aktualny status u�ytkownika.<BR>
     * @param status - kolejny status
     * @throws IOException b��d zmiany statusu
     */
    void changeStatus(IStatus status) throws IOException;

    /**
     * Wy�lij list� monitorowanych u�ytkownik�w.<br>
     * Lista zawiera u�ytkownik�w dla kt�rych serwer
     * przesy�a informacje o zmianie statusu
     * @param userList lista u�ytkownik�w
     * @throws IOException b��d wysy�ania listy u�ytkownik�w
     */
    void sendMonitoredUserList(Collection userList) throws IOException;

    /**
     * Dodaj u�ytkownika do u�ytkownik�w monitorowanych.<br>
     * Dla dodanego u�ytkownika b�dzie przesy�ana informacja o zmianie
     * statusu.
     * @param user u�ytkownik do monitorowania
     * @throws IOException b��d dodawania u�ytkownika
     */
    void addMonitoredUser(IUser user) throws IOException;

    /**
     * Usu� u�ytkownika z listy u�ytkownik�w monitorowanych.<br>
     * Dla podanego u�ytkownika nie b�dzie przesy�ana informacja
     * o zmianie statusu.
     * @param user u�tykownik do niemonitorowania
     * @throws IOException b�ad usuwania u�ytkownika
     */
    void removeMonitoredUser(IUser user) throws IOException;
}
