package pl.mn.communicator;

/**
 * U�ytkownik lokalny klienta rozm�w.<BR>
 * Obiekt reprezentuje lokalnego u�ytkownika.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface ILocalUser {
	/**
	 * Pobierz has�o u�ytkownika.<BR>
	 * Has�o jest w postaci niezaszyfrowanej. 
	 * 
	 * @return String password
	 */
	public String getPassword();
	/**
	 * Pobierz nr u�ytkownika.
	 * 
	 * @return int userNo
	 */
	public int getUserNo();
	/**
	 * @param password
	 */
	public void setPassword(String password);
	/**
	 * @param userNo
	 */
	public void setUserNo(int userNo);
}