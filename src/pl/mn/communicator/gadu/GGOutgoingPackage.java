package pl.mn.communicator.gadu;

/**
 * Pakiet wychodz�cy gg.
 * 
 * @author mnaglik
 */
interface GGOutgoingPackage {
	/**
	 * Zwr�� nag��wek pakietu
	 * 
	 * @return int
	 */
	int getHeader();
	
	/**
	 * Zwr�� d�ugo�� pakietu
	 * D�ugo�� bez nag��wka i inta zawieraj�cego d�ugo�� ca�ego pakietu.
	 *  
	 * @return int
	 */
	int getLength();
	
	/**
	 * Zwr�� bajty z zawarto�ci� pakietu do wys�ania
	 * 
	 * @return byte[]
	 */
	byte [] getContents();
}
