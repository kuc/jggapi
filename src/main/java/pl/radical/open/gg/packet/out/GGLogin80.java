package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.GGNullPointerException;
import pl.radical.open.gg.ILocalStatus;
import pl.radical.open.gg.packet.GGConversion;
import pl.radical.open.gg.packet.GGHashType;
import pl.radical.open.gg.packet.GGStatuses;
import pl.radical.open.gg.packet.GGUtils;
import pl.radical.open.gg.packet.GGVersion;

import org.apache.commons.collections.primitives.ArrayByteList;
import org.apache.commons.collections.primitives.ByteList;

/**
 * <pre>
 * struct gg_login80 {
 *        int uin;              &laquo; numer Gadu-Gadu &raquo;
 *        char language[2];     &laquo; język: &quot;pl&quot; &raquo;
 *        char hash_type;       &laquo; rodzaj funkcji skrótu hasła &raquo;
 *        char hash[64];        &laquo; skrót hasła dopełniony \0 &raquo;
 *        int status;           &laquo; początkowy status połączenia &raquo;
 *        int flags;            &laquo; początkowe flagi połączenia &raquo;
 *        int features;         &laquo; opcje protokołu (0x00000367) &raquo;
 *        int local_ip;         &laquo; lokalny adres połączeń bezpośrednich (nieużywany) &raquo;
 *        short local_port;     &laquo; lokalny port połączeń bezpośrednich (nieużywany) &raquo;
 *        int external_ip;      &laquo; zewnętrzny adres (nieużywany) &raquo;
 *        short external_port;  &laquo; zewnętrzny port (nieużywany) &raquo;
 *        char image_size;      &laquo; maksymalny rozmiar grafiki w KB &raquo;
 *        char unknown2;        &laquo; 0x64 &raquo;
 *        int version_len;      &laquo; długość ciągu z wersją (0x23) &raquo;
 *        char version[];       &laquo; &quot;Gadu-Gadu Client build 10.0.0.10450&quot; (bez \0) &raquo;
 *        int description_size; &laquo; rozmiar opisu &raquo;
 *        char description[];   &laquo; opis (nie musi wystąpić, bez \0) &raquo;
 * };
 * 
 * <pre>
 * 
 * @author <a href="mailto:lukasz.rzanek@radical.com.pl>Łukasz Rżanek</a>
 */
public class GGLogin80 implements GGOutgoingPackage {

	public final static int GG_LOGIN80 = 0x0031;

	/**
	 * Gadu-Gadu number that will be used during logging
	 */
	private int m_uin = -1;


	/**
	 * Language of the client, default to "pl"
	 */
	final String M_LANGUAGE = "pl";


	/**
	 * Password that will be used during logging
	 */
	private char[] m_password = null;


	/**
	 * Login hash type that will be used to authenticate the user
	 */
	private final GGHashType m_hashType = GGHashType.GG_LOGIN_HASH_GG32;


	/**
	 * Computed login hash based on seed retreived from Gadu-Gadu server
	 */
	private char[] m_loginHash = null;


	/**
	 * Initial status that will be set after logging
	 */
	private int m_status = GGStatuses.GG_STATUS_AVAIL;


	/**
	 * Starting protocols flags
	 */
	private final int m_flags = 0x01;


	/**
	 * Protocol options - 0x00000367
	 */
	private final int m_features = 0x00000367;


	/**
	 * Local IP
	 */
	private byte[] m_localIP = new byte[] {
			(byte) 0, (byte) 0, (byte) 0, (byte) 0
	};


	/**
	 * Local port that we are listening on
	 */
	private int m_localPort = 1550;


	/**
	 * ExternalIP
	 */
	private byte[] m_externalIP = new byte[] {
			(byte) 0, (byte) 0, (byte) 0, (byte) 0
	};


	/**
	 * External port
	 */
	private int m_externalPort = 1550;


	/**
	 * size of image in kilobytes
	 */
	private byte m_imageSize = 64;

	/**
	 * Unknown property
	 */
	private final int m_unknown2 = 0x64;

	/**
	 * Version of the client
	 */
	private final int m_version_len = GGVersion.VERSION_80_build_8283;

	/**
	 * Version descriptive string
	 */
	private final String m_version = "Gadu-Gadu Client build 10.0.0.10450";

	/**
	 * The length of the status description
	 */
	private int m_description_size;


	/**
	 * Description that will be set after successfuly logging
	 */
	private String m_description = null;

	public GGLogin80(final int uin, final char[] password, final int seed) throws GGException {
		if (uin < 0) {
			throw new IllegalArgumentException("uin cannot be less than 0");
		}
		if (password == null) {
			throw new GGNullPointerException("password cannot be null");
		}
		m_uin = uin;
		m_password = password;
		m_loginHash = GGUtils.getLoginHash(password, seed, m_hashType);
	}

	public void setStatus(final ILocalStatus localStatus) {
		if (localStatus == null) {
			throw new GGNullPointerException("localStatus cannot be null");
		}
		m_status = GGConversion.getProtocolStatus(localStatus, localStatus.isFriendsOnly(), false);
		if (localStatus.isDescriptionSet()) {
			m_description = localStatus.getDescription();
			m_description_size = m_description.length();
		}
	}

	public int getUin() {
		return m_uin;
	}

	public char[] getPassword() {
		return m_password;
	}

	public void setLocalIP(final byte[] localIP) {
		if (localIP == null) {
			throw new IllegalArgumentException("localIP cannot be null");
		}
		if (localIP.length != 4) {
			throw new IllegalArgumentException("localIp table has to have 4 entries");
		}
		m_localIP = localIP;
	}

	public byte[] getLocalIP() {
		return m_localIP;
	}

	public void setLocalPort(final int port) {
		if (port < 0) {
			throw new IllegalArgumentException("port cannot be null");
		}
		m_localPort = port;
	}

	public int getLocalPort() {
		return m_localPort;
	}

	public void setExternalIP(final byte[] externalIP) {
		if (externalIP == null) {
			throw new GGNullPointerException("externalIP cannot be null");
		}
		if (externalIP.length != 4) {
			throw new IllegalArgumentException("externalIP table has to have 4 entries");
		}
		m_externalIP = externalIP;
	}

	public void setExternalPort(final int externalPort) {
		if (externalPort < 0) {
			throw new IllegalArgumentException("port cannot be null");
		}
		m_externalPort = externalPort;
	}

	public void setImageSize(final byte imageSize) {
		if (imageSize < 0) {
			throw new IllegalArgumentException("imageSize cannot be less than 0");
		}
		m_imageSize = imageSize;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_LOGIN80;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		int length = 4 + 2 + 4 + 4 + 4 + 1 + 4 + 2 + 4 + 2 + 1 + 1;
		if (m_description != null) {
			length += m_description.length() + 1;
		}
		return length;
	}

	/**
	 * @see pl.radical.open.gg.packet.out.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		final ByteList byteList = new ArrayByteList();

		byteList.add((byte) (m_uin & 0xFF));
		byteList.add((byte) (m_uin >> 8 & 0xFF));
		byteList.add((byte) (m_uin >> 16 & 0xFF));
		byteList.add((byte) (m_uin >> 24 & 0xFF));

		byteList.add(M_LANGUAGE.getBytes()[0]);
		byteList.add(M_LANGUAGE.getBytes()[1]);

		// login hash type
		byteList.add((byte) (m_hashType.getValue() & 0xFF));
		byteList.add((byte) (m_hashType.getValue() >> 8 & 0xFF));
		byteList.add((byte) (m_hashType.getValue() >> 16 & 0xFF));
		byteList.add((byte) (m_hashType.getValue() >> 24 & 0xFF));

		// login hash
		final byte[] loginHashBytes = new String(m_loginHash).getBytes();
		for (int i = 0; i < 64; i++) {
			if (i < loginHashBytes.length) {
				byteList.add(loginHashBytes[i]);
			} else {
				byteList.add(Character.UNASSIGNED);
			}
		}

		// status
		byteList.add((byte) (m_status & 0xFF));
		byteList.add((byte) (m_status >> 8 & 0xFF));
		byteList.add((byte) (m_status >> 16 & 0xFF));
		byteList.add((byte) (m_status >> 24 & 0xFF));

		// flags
		byteList.add((byte) (m_flags & 0xFF));
		byteList.add((byte) (m_flags >> 8 & 0xFF));
		byteList.add((byte) (m_flags >> 16 & 0xFF));
		byteList.add((byte) (m_flags >> 24 & 0xFF));

		// features (?? byte)
		byteList.add((byte) (m_features & 0xFF));
		byteList.add((byte) (m_features >> 8 & 0xFF));
		byteList.add((byte) (m_features >> 16 & 0xFF));
		byteList.add((byte) (m_features >> 24 & 0xFF));

		// local IP
		byteList.add(m_localIP[0]);
		byteList.add(m_localIP[1]);
		byteList.add(m_localIP[2]);
		byteList.add(m_localIP[3]);

		// local port
		byteList.add((byte) (m_localPort & 0xFF));
		byteList.add((byte) (m_localPort >> 8 & 0xFF));

		// external IP
		byteList.add(m_externalIP[0]);
		byteList.add(m_externalIP[1]);
		byteList.add(m_externalIP[2]);
		byteList.add(m_externalIP[3]);

		// external port
		byteList.add((byte) (m_externalPort & 0xFF));
		byteList.add((byte) (m_externalPort >> 8 & 0xFF));

		// image size
		byteList.add(m_imageSize);

		// unknown 2
		byteList.add((byte) m_unknown2); // ?

		// version length
		byteList.add((byte) (m_version_len & 0xFF));
		byteList.add((byte) (m_version_len >> 8 & 0xFF));
		byteList.add((byte) (m_version_len >> 16 & 0xFF));
		byteList.add((byte) (m_version_len >> 24 & 0xFF));

		for (final byte b : m_version.getBytes()) {
			byteList.add(b);
		}

		// description size
		byteList.add((byte) (m_description_size & 0xFF));
		byteList.add((byte) (m_description_size >> 8 & 0xFF));
		byteList.add((byte) (m_description_size >> 16 & 0xFF));
		byteList.add((byte) (m_description_size >> 24 & 0xFF));


		if (m_description != null) {
			final byte[] descBytes = m_description.getBytes();
			for (final byte b : descBytes) {
				byteList.add(b);
			}
		}

		return byteList.toArray();
	}

}
