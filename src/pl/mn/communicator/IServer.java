package pl.mn.communicator;

/**
 * @author mnaglik
 *
 * To change the template for this generated type comment go to
 * Window - Preferences - Java - Code Generation - Code and Comments
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