package pl.mn.communicator;

/**
 * Listener ze zdarzeniami po��czenia.
 * 
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
public interface ConnectionListener {
	/**
	 * Po��czenie zosta�o pomy�lnie nawi�zane.
	 */
	public void connectionEstablished();

	/**
	 * Roz�aczono z serwerem.<BR>
	 * Wywo�ywane podczas celowego roz��czania z serwerem.
	 */
	public void disconnected();

	/**
	 * Problem z po��czeniem.<BR>
	 * Wyst�pi� b��d w po��czeniu.
	 * 
	 * @param error tekstowy opis b��du zwi�zanego z po��czeniem 
	 */
	public void connectionError(String error);
}
