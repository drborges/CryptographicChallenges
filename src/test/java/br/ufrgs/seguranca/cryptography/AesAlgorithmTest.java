package br.ufrgs.seguranca.cryptography;

import java.io.File;
import java.security.AlgorithmParameterGenerator;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.SecretKeySpec;

import junit.framework.Assert;

import org.junit.Test;

public class AesAlgorithmTest {

	public static final String AES_CIPHER = "AES";

	/**
	 * Turns array of bytes into string
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

		return strbuf.toString();
	}

	public static byte[] encrypt(String valueToEnc, String secretKey)
			throws Exception {

		Key key = new SecretKeySpec(secretKey.getBytes(), AES_CIPHER);

		Cipher ecCipher = Cipher.getInstance(AES_CIPHER);
		ecCipher.init(Cipher.ENCRYPT_MODE, key);
		return ecCipher.doFinal(valueToEnc.getBytes());
	}

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

	public static byte[] hexToBytes(String hex) {
		return hexToBytes(hex.toCharArray());
	}

	@Test
	public void shouldEncryptAndDecryptMessage() throws Exception {

		String plainKey = "essasenhaehfraca";
		String plainMessage = "Texto para teste";

		Key key = new SecretKeySpec(plainKey.getBytes(), AES_CIPHER);

		// encrypting...
		byte[] messageBytesEncrypted = encrypt(plainMessage, plainKey);

		Assert.assertEquals(
				"a506a19333f306ac2c62cbe931963ae7dfcffa940360a40ffd5dc69b9c2e53ad",
				asHex(messageBytesEncrypted));

		System.out.println(asHex(messageBytesEncrypted));
		Assert.assertEquals(asHex(messageBytesEncrypted), asHex(hexToBytes(asHex(messageBytesEncrypted))));
		
		// decrypying...
		Cipher decCipher = Cipher.getInstance(AES_CIPHER);
		decCipher.init(Cipher.DECRYPT_MODE, key);

		byte[] messageBytesDecrypted = decCipher.doFinal(messageBytesEncrypted);

		Assert.assertEquals("Texto para teste", new String(
				messageBytesDecrypted));
	}

	public static void main(String[] args) throws Exception {

		String message = "Texto para teste";

		// usar força bruta para descobrir os possiveis valores de XXXXX
		String secretKey = "essasenhaehfraca";

		byte[] raw = secretKey.getBytes();
		SecretKeySpec skeySpec = new SecretKeySpec(raw, AES_CIPHER);

		// Instantiate the cipher
		Cipher cipher = Cipher.getInstance(AES_CIPHER);

		// Set the cipher to encrypt mode to encrypt the message using the
		// provided secret key
		cipher.init(Cipher.ENCRYPT_MODE, skeySpec);

		// encrypted message
		byte[] encryptedMessage = cipher.doFinal(message.getBytes());
		System.out.println("Encrypted string: " + asHex(encryptedMessage));

		cipher = Cipher.getInstance(AES_CIPHER);
		// Set the cipher to decrypt mode to decrypt the encrypted message
		cipher.init(Cipher.DECRYPT_MODE, skeySpec);

		// Decrypts the message
		byte[] decrypted = cipher.doFinal(encryptedMessage);
		String decryptedMessage = new String(decrypted);
		System.out.println("Decrypted string: " + decryptedMessage);
	}

}