package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * U�ytkownik serwera rozm�w.
 *  
 * @author mnaglik
 */
public class AbstractUser {
	private static Logger logger = Logger.getLogger(AbstractUser.class);

	protected int number;
	protected boolean onLine;
	protected String name;

	public AbstractUser(int number, String name, boolean onLine) {
		this.number = number;
		this.onLine = onLine;
		this.name = name;
	}

	/**
	 * Zwr�� nick u�ytkownika.
	 * 
	 * @return String
	 */
	public String getName() {
		return name;
	}

	/**
	 * Zwr�� numer u�ytkownika.
	 * 
	 * @return int
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * Zmie� nick u�ytkownika
	 * 
	 * @param name nowe nick u�ytkownika
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * Zmie� numer u�ytkownika.
	 * 
	 * @param number nowy numer u�ytkownika
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * Zwr�� status u�ytkownika.
	 * True - u�ytkownik online
	 * 
	 * @return boolean
	 */
	public boolean isOnLine() {
		return onLine;
	}

	/**
	 * Zmie� status u�ytkownika.
	 * 
	 * @param onLine nowy status u�ytkownika
	 */
	public void setOnLine(boolean onLine) {
		this.onLine = onLine;
	}

	/**
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	public boolean equals(Object o) {
		return (o instanceof AbstractUser) && (number == ((AbstractUser) o).getNumber());
	}

	/**
	 * @see java.lang.Object#hashCode()
	 */
	public int hashCode() {
		return number;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + name + ":" + number + ":" + onLine + "]";
	}

}
