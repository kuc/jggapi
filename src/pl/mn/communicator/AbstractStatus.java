package pl.mn.communicator;

import java.util.HashMap;
import java.util.Map;

/**
 * Klasa reprezentuj�ca status u�ytkownika.
 * Dodatkowe statusy specyficzne dla serwer�w rozm�w
 * mog� zosta� dodane w podklasach.
 * 
 * @author mnaglik
 */
public abstract class AbstractStatus {
	/**
	 * Status on-line
	 */
	public final static int ON_LINE = 1;
	/**
	 * Status off-line
	 */
	public final static int OFF_LINE = 0;
	/**
	 * Aktualny status 
	 */
	protected int actualStatus; 

	public AbstractStatus(int status) {
		this.actualStatus = status;
	}
	
	/**
	 * Pobierz aktualny status
	 * 
	 * @return int
	 */
	public int getStatus() {
		return actualStatus;
	}

	/**
	 * Pobierz dost�pne statusy.
	 * Zwraca map� dost�pnych status�w.
	 * Kluczem jest Integer z nr statusu,
	 * a wartoscia String z nazw� statusu
	 * 
	 * @return Map
	 */
	public Map getAvaiableStatuses() {
		HashMap map = new HashMap();

		map.put(new Integer(AbstractStatus.ON_LINE),"On Line");
		map.put(new Integer(AbstractStatus.OFF_LINE),"Off Line");
	
		return map;
	}
}
