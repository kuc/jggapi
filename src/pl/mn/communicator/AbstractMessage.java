package pl.mn.communicator;

/**
 * Wiadomo�� do serwera rozm�w.
 * 
 * @author mnaglik
 */
public abstract class AbstractMessage {
	/**
	 * Nr adresata wiadomo�ci
	 */
	protected int toUser;
	
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
		this.toUser = toUser;
		this.text = text;
	}

	/**
	 * Pobierz u�ytkownika do kt�rego jest wiadomo��
	 * 
	 * @return User 
	 */
	public int getUser() {
		return toUser;
	}

	/**
	 * Pobierz tre�� wiadomo��i
	 * 
	 * @return String
	 */
	public String getText() {
		return text;
	}
}
