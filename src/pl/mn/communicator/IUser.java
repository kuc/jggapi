package pl.mn.communicator;

/**
 * @author mnaaglik
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
 */
public interface IUser {
	/**
	 * Zwr�� nick u�ytkownika.
	 * 
	 * @return String
	 */
	public String getName();
	/**
	 * Zwr�� numer u�ytkownika.
	 * 
	 * @return int
	 */
	public int getNumber();
	/**
	 * Zmie� nick u�ytkownika
	 * 
	 * @param name nowe nick u�ytkownika
	 */
	public void setName(String name);
	/**
	 * Zmie� numer u�ytkownika.
	 * 
	 * @param number nowy numer u�ytkownika
	 */
	public void setNumber(int number);
	/**
	 * Zwr�� status u�ytkownika.
	 * True - u�ytkownik online
	 * 
	 * @return boolean
	 */
	public boolean isOnLine();
	/**
	 * Zmie� status u�ytkownika.
	 * 
	 * @param onLine nowy status u�ytkownika
	 */
	public void setOnLine(boolean onLine);
}