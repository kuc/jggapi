package pl.mn.communicator;

/**
 * Listener wiadomo�ci.<BR>
 * Obs�uguje zdarzenia zwi�zane z wiadomo�ciami.<BR>
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface MessageListener {
	/**
	 * Nadesz�a wiadomo��.
	 * 
	 * @param message wiadomo�� z serwera rozm�w
	 */
	public void messageArrived(IMessage message);
}
