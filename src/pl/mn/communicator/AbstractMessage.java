package pl.mn.communicator;

import org.apache.log4j.Logger;

/**
 * Wiadomo�� do serwera rozm�w.
 * 
 * @author mnaglik
 */
public abstract class AbstractMessage implements IMessage {
	private static Logger logger = Logger.getLogger(AbstractMessage.class);
	/**
	 * Nr adresata wiadomo�ci
	 */
	protected int user;
	
	/**
	 * Tre�� wiadomo�ci
	 */
	protected String text;

	/**
	 * Tworzy wiadomo�� do konkretnego u�ytkownika.
	 * 
	 * @param toUser nr u�ytkownika do kt�rego wysy�amy
	 * @param text wiadomo�� tekstowa
	 */
	public AbstractMessage(int toUser, String text) {
		this.user = toUser;
		this.text = text;
	}

	/**
	 * Pobierz u�ytkownika do kt�rego jest wiadomo��
	 * 
	 * @return User 
	 */
	public int getUser() {
		return user;
	}

	/**
	 * Pobierz tre�� wiadomo��i
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
	/**
	 * @param text
	 */
	public void setText(String text) {
		this.text = text;
	}

	/**
	 * @param user
	 */
	public void setUser(int user) {
		this.user = user;
	}

}
