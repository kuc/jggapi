package pl.radical.open.gg.utils;

import pl.radical.open.gg.GGException;
import pl.radical.open.gg.dicts.Encoding;
import pl.radical.open.gg.packet.dicts.GGHashType;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.collections.primitives.ArrayCharList;
import org.apache.commons.collections.primitives.CharList;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author <a href="mailto:mati@sz.home.pl">Mateusz Szczap</a>
 */
public class GGUtils {

	private static final Logger log = LoggerFactory.getLogger(GGUtils.class);

	public static String prettyBytesToString(final byte[] bytes) {
		final StringBuffer received = new StringBuffer();
		received.append("{");

		final char[] dump = Hex.encodeHex(bytes);
		int i = 0;
		for (final char c : dump) {
			received.append(c);
			i++;
			if (i % 2 == 0) {
				received.append(" ");
			}
		}

		received.append("}");

		return received.toString();
	}

	public static int byteToInt(final byte[] buf) {
		return byteToInt(buf, 0);
	}

	public static int byteToInt(final byte[] buf, final int start) {
		int i = 0;
		int pos = start;

		int tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 0;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 8;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 16;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 24;
		i += tmp;

		return i;
	}

	public static int byteToShort(final byte[] buf, final int start) {
		int i = 0;
		int pos = start;

		int tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 0;
		i += tmp;
		tmp = unsignedByteToInt(buf[pos++]) << 8;
		i += tmp;

		return i;
	}

	public static byte[] intToByteArray(final int i) {
		final byte[] result = new byte[4];
		result[0] = (byte) (i & 0xFF);
		result[1] = (byte) (i >> 8 & 0xFF);
		result[2] = (byte) (i >> 16 & 0xFF);
		result[3] = (byte) (i >> 24 & 0xFF);

		return result;
	}

	public static byte[] shortToByteArray(final short s) {
		final byte[] result = new byte[2];
		result[0] = (byte) (s & 0xFF);
		result[1] = (byte) (s >> 8 & 0xFF);

		return result;
	}

	public static long secondsToMillis(final int seconds) {
		return seconds * 1000L;
	}

	public static int millisToSeconds(final long millis) {
		return (int) (millis / 1000L);
	}

	public static int unsignedByteToInt(final byte i) {
		if (i < 0) {
			return (i & 0x7F) + 0x80;
		} else {
			return i;
		}
	}

	public static byte[] intToByte(final int buf) {
		final byte[] toSend = new byte[4];

		toSend[3] = (byte) (buf >> 24 & 0xFF);
		toSend[2] = (byte) (buf >> 16 & 0xFF);
		toSend[1] = (byte) (buf >> 8 & 0xFF);
		toSend[0] = (byte) (buf & 0xFF);

		return toSend;
	}

	public static long unsignedIntToLong(int i) {
		long plus = 0;
		plus -= i & 0x80000000;
		i &= 0x7FFFFFFF;

		return i + plus;
	}

	public static String byteToString(final byte[] data, final int startIndex) {
		int counter = 0;

		while (counter + startIndex < data.length && data[counter + startIndex] != 0) {
			counter++;
		}

		final byte[] desc = new byte[counter];
		System.arraycopy(data, startIndex, desc, 0, counter);

		String returnString = null;
		try {
			returnString = new String(desc, Encoding.WINDOWS1250.getValue());
		} catch (final UnsupportedEncodingException ex) {
			log.warn("Unable to convert", ex);
			return new String(desc);
		}
		return returnString;
	}

	public static String byteToString(final byte[] data, final int startIndex, final Encoding encoding) {
		int counter = 0;

		while (counter + startIndex < data.length && data[counter + startIndex] != 0) {
			counter++;
		}

		final byte[] desc = new byte[counter];
		System.arraycopy(data, startIndex, desc, 0, counter);

		String returnString = null;
		try {
			returnString = new String(desc, encoding.getValue());
		} catch (final UnsupportedEncodingException ex) {
			log.warn("Unable to convert", ex);
			return new String(desc);
		}
		return returnString;
	}

	public static byte[] convertIntToByteArray(final int i) {
		final byte[] bytes = new byte[4];
		bytes[0] = (byte) (i & 0xFF);
		bytes[1] = (byte) (i >> 8 & 0xFF);
		bytes[2] = (byte) (i >> 16 & 0xFF);
		bytes[3] = (byte) (i >> 24 & 0xFF);

		return bytes;
	}

	public static int getLoginHash(final char[] password, final int seed) {
		long x;
		long y;
		long z;

		y = seed;

		int i;

		for (x = 0, i = 0; i < password.length; i++) {
			x = x & 0xffffff00 | password[i];
			y ^= x;

			int k = (int) y;
			k += x;
			y = GGUtils.unsignedIntToLong(k);

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			y ^= x;

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			k = (int) y;
			k -= x;
			y = GGUtils.unsignedIntToLong(k);

			k = (int) x;
			k <<= 8;
			x = GGUtils.unsignedIntToLong(k);

			y ^= x;

			z = y & 0x1f;
			y = GGUtils.unsignedIntToLong((int) (y << z | y >> 32 - z));
		}

		return (int) y;
	}

	public static char[] byteToCharArray(final byte[] bytes) {
		final CharList cl = new ArrayCharList();
		for (final byte b : bytes) {
			cl.add((char) b);
		}

		return cl.toArray();
	}

	private static byte[] getLoginHashSHA(final char[] password, final int seed) throws GGException {
		MessageDigest hash = null;
		try {
			hash = MessageDigest.getInstance("SHA1");
		} catch (final NoSuchAlgorithmException e) {
			log.error("Brak algorytmu SHA1", e);
			throw new GGException("Brak algorytmu SHA1", e);
		}

		hash.update(new String(password).getBytes());
		hash.update(GGUtils.intToByte(seed));

		return hash.digest();
	}

	public static byte[] getLoginHash(final char[] password, final int seed, final GGHashType hashType) throws GGException {
		switch (hashType) {
			case GG_LOGIN_HASH_GG32:
				return intToByte(getLoginHash(password, seed));
			case GG_LOGIN_HASH_SHA1:
				return getLoginHashSHA(password, seed);
			default:
				throw new GGException("Hash algorithm to be used during login was not specified");
		}
	}

	public static int copy(final InputStream input, final OutputStream output) throws IOException {
		final byte[] buffer = new byte[1024];
		int count = 0;
		int n = 0;
		while (-1 != (n = input.read(buffer))) {
			output.write(buffer, 0, n);
			count += n;
		}
		return count;
	}

}