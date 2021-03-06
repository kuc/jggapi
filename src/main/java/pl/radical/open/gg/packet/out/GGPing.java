package pl.radical.open.gg.packet.out;

import pl.radical.open.gg.packet.GGOutgoingPackage;
import pl.radical.open.gg.packet.OutgoingPacket;

/**
 * Outgoing packet, ping type that is from time to time being send to Gadu-Gadu server.
 * 
 * @author <a href="mailto:mnaglik@gazeta.pl">Marcin Naglik</a>
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
@OutgoingPacket(type = 0x08, label = "GG_PING")
public final class GGPing implements GGOutgoingPackage {

	public static final int GG_PING = 0x08;

	private static byte[] data = new byte[0];

	private static GGPing ping = null;

	/**
	 * Prywatny konstruktor.
	 */
	private GGPing() {
	}

	public static GGPing getPing() {
		if (ping == null) {
			ping = new GGPing();
		}
		return ping;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getPacketType()
	 */
	public int getPacketType() {
		return GG_PING;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getLength()
	 */
	public int getLength() {
		return data.length;
	}

	/**
	 * @see pl.radical.open.gg.packet.GGOutgoingPackage#getContents()
	 */
	public byte[] getContents() {
		return data;
	}

}
