package pl.mn.communicator;

/**
 * Klasa reprezentuj�ca serwer rozm�w.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface IServer {
	/**
	 * Zwr�� adres serwera rozm�w.
	 * 
	 * @return String
	 */
	public String getAddress();
	/**
	 * Zwr�� port serwera rozm�w.
	 *
	 *  @return int
	 */
	public int getPort();
	/**
	 * @param address
	 */
	public void setAddress(String address);
	/**
	 * @param port
	 */
	public void setPort(int port);
}