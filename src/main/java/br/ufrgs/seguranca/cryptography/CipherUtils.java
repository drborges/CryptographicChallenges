package br.ufrgs.seguranca.cryptography;

public class CipherUtils {

	/**
	 * Turns array of bytes into hex string
	 * 
	 * @param buf
	 *            Array of bytes to convert to hex string
	 * @return Generated hex string
	 */
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString().toUpperCase();
	}
	
	/**
	 * Converts a hex character sequence into a byte array
	 * 
	 * @param hex the character sequence to be converted
	 * 
	 * @return a byte array representation of the hex value
	 */
	public static byte[] hexToBytes(char[] hex) {
		int length = hex.length / 2;
		byte[] raw = new byte[length];
		for (int i = 0; i < length; i++) {
			int high = Character.digit(hex[i * 2], 16);
			int low = Character.digit(hex[i * 2 + 1], 16);
			int value = (high << 4) | low;
			if (value > 127)
				value -= 256;
			raw[i] = (byte) value;
		}
		return raw;
	}

	/**
	 * Converts a hex string into a byte array
	 * 
	 * @param hex the string to be converted
	 * 
	 * @return a byte array representation of the hex value
	 */
	public static byte[] hexToBytes(String hex) {
		return hexToBytes(hex.toCharArray());
	}
}
