package pl.mn.communicator;

/**
 * Listener zwi�zany ze zdarzeniami u�ytkownik�w.
 * 
 * @version $Revision: 1.2 $
 * @author mnaglik
 */
public interface UserListener {
	/**
	 * U�ytkownik pod��czy� si�.
	 * 
	 * @param userNumber nr u�ytkownika, kt�ry si� pod��czy�
	 */
	public void userEntered(int userNumber);

	/**
	 * U�ytkownik od��czy� si�.
	 * 
	 * @param userNumber nr u�ytkownika, kt�ry si� od��czy�
	 */
	public void userLeaved(int userNumber);
}
