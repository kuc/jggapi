package pl.mn.communicator;

import org.apache.log4j.Logger;


/**
 * Klasa reprezentuj�ca serwer rozm�w.
 * 
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
public abstract class AbstractServer implements IServer {
	private static Logger logger = Logger.getLogger(AbstractServer.class);
	/**
	 * Adres ip, lub tekstowy serwera rozm�w
	 */
	protected String address;
	
	/**
	 * Numer portu serwera
	 */
	protected int port;

	/**
	 * Utworz adres serwera rozm�w.
	 * 
	 * @param address adres serwera
	 * @param port post serwera
	 */
	public AbstractServer(String address, int port) {
		this.address = address;
		this.port = port;
	}
	/**
	 * Zwr�� adres serwera rozm�w.
	 * 
	 * @return String
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * Zwr�� port serwera rozm�w.
	 *
	 *  @return int
	 */
	public int getPort() {
		return port;
	}

	/**
	 * @see java.lang.Object#toString()
	 */
	public String toString() {
		return "[" + this.address + "-" + this.port + "]";
	}
	/**
	 * @param address
	 */
	public void setAddress(String address) {
		this.address = address;
	}

	/**
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

}
