package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * U�ytkownik lokalny klienta rozm�w.<BR>
 * Obiekt reprezentuje lokalnego u�ytkownika.
 * 
 * @author mnaglik
 */
public abstract class AbstractLocalUser {
	private static Logger logger = Logger.getLogger(AbstractLocalUser.class);
	/**
	 * Numer u�ytkownika
	 */
	protected int userNo;

	/**
	 * Has�o u�ytkownika
	 */
	protected String password;

	/**
	 * Utw�rz u�ytkownika lokalnego.
	 * 
	 * @param userNo nr uzytkownika
	 * @param password has�o
	 */
	public AbstractLocalUser(int userNo, String password) {
		this.userNo = userNo;
		this.password = password;
	}
	/**
	 * Pobierz has�o u�ytkownika.<BR>
	 * Has�o jest w postaci niezaszyfrowanej. 
	 * 
	 * @return String password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * Pobierz nr u�ytkownika.
	 * 
	 * @return int userNo
	 */
	public int getUserNo() {
		return userNo;
	}

}
