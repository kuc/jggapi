/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved. This program is free software; you can
 * redistribute it and/or modify it under the terms of the GNU General Public License as published by the Free Software
 * Foundation; either version 2 of the License, or (at your option) any later version. This program is distributed in
 * the hope that it will be useful, but WITHOUT ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for more details. You should have received a
 * copy of the GNU General Public License along with this program; if not, write to the Free Software Foundation, Inc.,
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA
 */
package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.User;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGUser;
import pl.radical.open.gg.packet.GGUtils;

/**
 * Packet that deletes certain user from the list of monitored users.<BR>
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGRemoveNotify.java,v 1.1 2005/11/05 23:34:53 winnetou25 Exp $
 */
public class GGRemoveNotify implements GGOutgoingPackage, GGUser {

	public final static int GG_REMOVE_NOTIFY = 0x000E;

	/** Gadu-Gadu uin */
	private int m_uin = -1;

	private final User.UserMode m_userMode = null;
	private byte m_userType = GG_USER_BUDDY;

	public GGRemoveNotify(final int uin, final User.UserMode userMode) {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (userMode == null) {
			throw new NullPointerException("userMode cannot be null");
		}
		m_uin = uin;
		m_userType = GGConversion.getProtocolUserMode(userMode);
	}

	public int getUin() {
		return m_uin;
	}

	public User.UserMode getUserMode() {
		return m_userMode;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_REMOVE_NOTIFY;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return 5;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final byte[] dane = new byte[getLength()];

		final byte[] uin = GGUtils.intToByte(m_uin);
		System.arraycopy(uin, 0, dane, 0, uin.length);

		dane[4] = m_userType;

		return dane;
	}

}