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
package pl.mn.communicator.packet.out;

import pl.mn.communicator.ILocalStatus;
import pl.mn.communicator.packet.GGConversion;
import pl.mn.communicator.packet.GGStatuses;
import pl.mn.communicator.packet.GGUtils;
import pl.mn.communicator.packet.GGVersion;

/**
 *
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 * @version $Id: GGLogin60.java,v 1.1 2005-11-05 23:34:53 winnetou25 Exp $
 */
public class GGLogin60 implements GGOutgoingPackage {

	public final static int GG_LOGIN60 = 0x0015;

	/** Gadu-Gadu number that will be used during logging */
	private int m_uin = -1;
	
	/** Password that will be used during logging */
	private char[] m_password = null;

	/** Computed login hash based on seed retreived from Gadu-Gadu server */
	private int m_loginHash = -1;

	/** Initial status that will be set after logging */
	private int m_status = GGStatuses.GG_STATUS_AVAIL;

	/** Local IP */
	private byte[] m_localIP = new byte[] {(byte)0, (byte)0, (byte)0, (byte)0};

	/** Local port that we are listening on */
	private int m_localPort = 1550;
	
	/** ExternalIP */
	private byte[] m_externalIP = new byte[] {(byte)0, (byte) 0, (byte) 0, (byte) 0};

	/** External port */
	private int m_externalPort = 1550;
	
	/** size of image in kilobytes */
	private byte m_imageSize = 64;

	/** Description that will be set after successfuly logging */
	private String m_description = null;
	
	/** Version of the client */
	private int m_version =  GGVersion.VERSION_60_1_build_133;

	/** Return time */
	private int m_time = -1;
	
	public GGLogin60(int uin, char[] password, int seed) {
		if (uin < 0) throw new IllegalArgumentException("uin cannot be less than 0");
		if (password == null) throw new NullPointerException("password cannot be null");
		m_uin = uin;
		m_password = password;
		m_loginHash = GGUtils.getLoginHash(password, seed);
	}
	
	public void setStatus(ILocalStatus localStatus) {
		if (localStatus == null) throw new NullPointerException("localStatus cannot be null");
		m_status = GGConversion.getProtocolStatus(localStatus, localStatus.isFriendsOnly(), false);
		if (localStatus.isDescriptionSet()) {
			m_description = localStatus.getDescription();
		}
		if (localStatus.isReturnDateSet()) {
			m_time = GGUtils.millisToSeconds(localStatus.getReturnDate().getTime());
		}
	}
	
	public int getUin() {
		return m_uin;
	}
	
	public char[] getPassword() {
		return m_password;
	}
	
	public void setLocalIP(byte[] localIP) {
		if (localIP == null) throw new NullPointerException("localIP cannot be null");
		if (localIP.length != 4) throw new IllegalArgumentException("localIp table has to have 4 entries");
		m_localIP = localIP;
	}
	
	public byte[] getLocalIP() {
		return m_localIP;
	}
	
	public void setLocalPort(int port) {
		if (port < 0) throw new IllegalArgumentException("port cannot be null");
		m_localPort = port;
	} 

	public int getLocalPort() {
		return m_localPort;
	}
	
	public void setExternalIP(byte[] externalIP) {
		if (externalIP == null) throw new NullPointerException("externalIP cannot be null");
		if (externalIP.length != 4) throw new IllegalArgumentException("externalIP table has to have 4 entries");
		m_externalIP = externalIP;
	}

	public void setExternalPort(int externalPort) {
		if (externalPort < 0) throw new IllegalArgumentException("port cannot be null");
		m_externalPort = externalPort;
	}
	
	public void setImageSize(byte imageSize) {
		if (imageSize < 0) throw new IllegalArgumentException("imageSize cannot be less than 0");
		m_imageSize = imageSize;
	}
	
	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN60;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		int length = 4+4+4+4+1+4+2+4+2+1+1;
		if (m_description != null) {
			length+=m_description.length()+1;
			if (m_time != -1) {
				length+=4;
			}
		}
		return length;
	}

	/**
	 * @see pl.mn.communicator.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
        byte[] toSend = new byte[getLength()];

        toSend[0] = (byte) (m_uin & 0xFF);
        toSend[1] = (byte) ((m_uin >> 8) & 0xFF);
        toSend[2] = (byte) ((m_uin >> 16) & 0xFF);
        toSend[3] = (byte) ((m_uin >> 24) & 0xFF);

        toSend[4] = (byte) (m_loginHash & 0xFF);
        toSend[5] = (byte) ((m_loginHash >> 8) & 0xFF);
        toSend[6] = (byte) ((m_loginHash >> 16) & 0xFF);
        toSend[7] = (byte) ((m_loginHash >> 24) & 0xFF);

        toSend[8] = (byte) (m_status & 0xFF);
        toSend[9] = (byte) ((m_status >> 8) & 0xFF);
        toSend[10] = (byte) ((m_status >> 16) & 0xFF);
        toSend[11] = (byte) ((m_status >> 24) & 0xFF);

        toSend[12] = (byte) (m_version & 0xFF);
        toSend[13] = (byte) ((m_version >> 8) & 0xFF);
        toSend[14] = (byte) ((m_version >> 16) & 0xFF);
        toSend[15] = (byte) ((m_version >> 24) & 0xFF);

        toSend[16] = (byte) 0x00;
        
        toSend[17] = (byte) m_localIP[0];
        toSend[18] = (byte) m_localIP[1];
        toSend[19] = (byte) m_localIP[2];
        toSend[20] = (byte) m_localIP[3];

		toSend[21] = (byte) (m_localPort & 0xFF);
		toSend[22] = (byte) ((m_localPort >> 8) & 0xFF);

        toSend[23] = (byte) m_externalIP[0];
        toSend[24] = (byte) m_externalIP[1];
        toSend[25] = (byte) m_externalIP[2];
        toSend[26] = (byte) m_externalIP[3];

        toSend[27] = (byte) (m_externalPort & 0xFF);
        toSend[28] = (byte) ((m_externalPort >> 8) & 0xFF);

        toSend[29] = (byte) m_imageSize;
        toSend[30] = (byte) 0xBE;
        
        if (m_description != null) {
        	byte[] descBytes = m_description.getBytes();
        	for (int i=0; i<descBytes.length; i++) {
        		toSend[31+i] = descBytes[i];
        		if (m_time != -1) {
        			toSend[31+descBytes.length+1] = (byte) ((m_time >> 24) & 0xFF);
        			toSend[31+descBytes.length+2] = (byte) ((m_time >> 16) & 0xFF);
        			toSend[31+descBytes.length+3] = (byte) ((m_time >> 8) & 0xFF);
        			toSend[31+descBytes.length+4] = (byte) ((m_time) & 0xFF);
        		}
        	}
        }
        
        return toSend;
	}

}