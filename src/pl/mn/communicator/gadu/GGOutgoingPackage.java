/*
 * Copyright (c) 2003 Marcin Naglik (mnaglik@gazeta.pl)
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the Free
 * Software Foundation; either version 2 of the License, or (at your option)
 * any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.mn.communicator.gadu;

/**
 * Pakiet wychodz�cy gg.
 * @version $Revision: 1.5 $
 * @author mnaglik
 */
interface GGOutgoingPackage {
	/**
	 * Zwr�� nag��wek pakietu
	 * @return int
	 */
	int getHeader();

	/**
	 * Zwr�� d�ugo�� pakietu
	 * D�ugo�� bez nag��wka i inta zawieraj�cego d�ugo�� ca�ego pakietu.
	 * @return int
	 */
	int getLength();

	/**
	 * Zwr�� bajty z zawarto�ci� pakietu do wys�ania
	 * @return byte[] zawarto�� pakietu
	 */
	byte [] getContents();
}
