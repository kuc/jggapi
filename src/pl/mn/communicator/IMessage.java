package pl.mn.communicator;

/**
 * Wiadomo�� do serwera rozm�w.
 * 
 * @author mnaglik
 */
public interface IMessage {
	/**
	 * Pobierz u�ytkownika do kt�rego jest wiadomo��
	 * 
	 * @return User 
	 */
	public int getUser();
	/**
	 * Pobierz tre�� wiadomo��i
	 * 
	 * @return String
	 */
	public String getText();
	/**
	 * @param text
	 */
	public void setText(String text);
	/**
	 * @param user
	 */
	public void setUser(int user);
}