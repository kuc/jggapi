/*
 * Copyright (c) 2003-2005 JGGApi Development Team. All Rights Reserved.
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
package pl.mn.communicator.packet.in;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import pl.mn.communicator.LocalUser;

/**
 * Created on 2004-12-11
 * 
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGUserListReply.java,v 1.1 2005-11-05 23:34:52 winnetou25 Exp $
 */
public class GGUserListReply implements GGIncomingPackage {

	public final static int GG_USERLIST_REPLY  = 0x0010;
	
	private final static int GG_USERLIST_PUT_REPLY  = 0x00;        /* początek eksportu listy */
	private final static int GG_USERLIST_PUT_MORE_REPLY  = 0x02;    /* kontynuacja */

	private final static int GG_USERLIST_GET_MORE_REPLY  = 0x04;    /* początek importu listy */
	private final static int GG_USERLIST_GET_REPLY = 0x06; 			/* ostatnia część importu */ 
	
	private byte m_type = -1;
	
	private Collection m_users = null;
	
	public GGUserListReply(byte[] data) throws IOException {
		m_type = (byte) data[0];
		if (isGetMoreReply() || isGetReply()) {
			m_users = createUsersCollection(data);
		}
	}
	
	/**
	 * @see pl.mn.communicator.packet.in.GGIncomingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_USERLIST_PUT_REPLY;
	}
	
	private Collection createUsersCollection(byte[] data) throws IOException {
		ArrayList localUsers = new ArrayList();

		String contactListString = new String(data, 1, data.length-1, "windows-1250");
		BufferedReader bufReader = new BufferedReader(new StringReader(contactListString));

		ArrayList lines = new ArrayList();
		String line = null; 
		while ((line = bufReader.readLine()) != null) {
		    lines.add(line);
		}

		for (Iterator it = lines.iterator(); it.hasNext();) {
		    String subline = (String) it.next();
		    String[] contactListStrings = subline.split(";");
			List contactList = Arrays.asList(contactListStrings);
			LocalUser localUser = createLocalUser(contactList);
			localUsers.add(localUser);
		}
		
		return localUsers;
	}
	
//	imie;nazwisko;pseudo;wyswietlane;telefon;grupa;uin;adres@email;0;;0; //stara wersja
//	imi�;nazwisko;pseudonim;wy�wietlane;telefon_kom�rkowy;grupa;uin;adres_email;dost�pny;�cie�ka_dost�pny;wiadomo��;�cie�ka_wiadomo��;ukrywanie;telefon_domowy
	private LocalUser createLocalUser(final List entries) {
		String firstName = null;
		String lastName = null;
		String nickName = null;
		String displayName = null;
		String telephone = null;
		String group = null;
		String uin = null;
		String email = null;

		final Iterator it = entries.iterator();

		if (it.hasNext()) {
			firstName = (String) it.next();
		}
		if (it.hasNext()) {
			lastName = (String) it.next();
		}
		if (it.hasNext()) {
			nickName = (String) it.next();
		}
		if (it.hasNext()) {
			displayName = (String) it.next();
		}
		if (it.hasNext()) {
			telephone = (String) it.next();
		}
		if (it.hasNext()) {
			group = (String) it.next();
		}
		if (it.hasNext()) {
			uin = (String) it.next();
		}
		if (it.hasNext()) {
			email = (String) it.next();
		}
		
		final LocalUser localUser = new LocalUser();
		if (!isEmpty(firstName)) {
			localUser.setFirstName(firstName);
		}
		if (!isEmpty(lastName)) {
			localUser.setLastName(lastName);
		}
		if (!isEmpty(nickName)) {
			localUser.setNickName(nickName);
		}
		if (!isEmpty(displayName)) {
			localUser.setDisplayName(displayName);
		}
		if (!isEmpty(telephone)) {
			localUser.setTelephone(telephone);
		}
		if (!isEmpty(group)) {
			localUser.setGroup(group);
		}
		int uinInt = -1;
		try {
		    uinInt = Integer.valueOf(uin).intValue();
			if (uinInt != -1 && !isEmpty(uin)) {
			    localUser.setUin(uinInt);
			}
		} catch (NumberFormatException ex) {
		    //ignore
		}
		if (!isEmpty(email)) {
			localUser.setEmailAddress(email);
		}
		
		return localUser;
	}
	
	public Collection getContactList() {
		return m_users;
	}
	
	public boolean isPutReply() {
		return m_type == GG_USERLIST_PUT_REPLY;
	}
	
	public boolean isPutMoreReply() {
		return m_type == GG_USERLIST_PUT_MORE_REPLY;
	}
	
	public boolean isGetReply() {
		return m_type == GG_USERLIST_GET_REPLY;
	}
	
	public boolean isGetMoreReply() {
		return m_type == GG_USERLIST_GET_MORE_REPLY;
	}
	
	private boolean isEmpty(String text) {
		return (text == null || text.trim().equals(""));
	}
	
}
