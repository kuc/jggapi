package pl.mn.communicator;

import java.util.Map;

/**
 * Klasa reprezentuj�ca status u�ytkownika.
 * Dodatkowe statusy specyficzne dla serwer�w rozm�w
 * mog� zosta� dodane w podklasach.
 * 
 * @version $Revision: 1.3 $
 * @author mnaglik
 */
public interface IStatus {
	/**
	 * Status on-line
	 */
	public final static int ON_LINE = 1;

	/**
	 * Status off-line
	 */
	public final static int OFF_LINE = 0;

	/**
	 * Pobierz aktualny status
	 * 
	 * @return int
	 */
	public int getStatus();
	/**
	 * Ustaw aktualny status
	 * 
	 * @return int
	 */
	public void setStatus(int status);
	/**
	 * Pobierz dost�pne statusy.
	 * Zwraca map� dost�pnych status�w.
	 * Kluczem jest Integer z nr statusu,
	 * a wartoscia String z nazw� statusu
	 * 
	 * @return Map
	 */
	public Map getAvaiableStatuses();
}