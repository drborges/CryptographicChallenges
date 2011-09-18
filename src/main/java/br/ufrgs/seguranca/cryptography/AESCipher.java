package br.ufrgs.seguranca.cryptography;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Component used to encrypt/decrypt messages using AES cipher
 * 
 * @author diego
 */
public class AESCipher implements KeyBasedCipher {

	public static final String AES_CIPHER = "AES";

	/**
	 * Encrypt a string message using AES algorithm based on the provided secret
	 * key.
	 * 
	 * @param message
	 *            the message to be encrypted
	 * @param secretKey
	 *            the secret key used by AES algorithm to encrypt the message
	 * 
	 * @return a {@link Hexadecimal} representation of the encrypted message
	 * 
	 * @throws Exception
	 *             Thrown if any problem occur when encrypting the message
	 */
	public Hexadecimal encrypt(String message, String secretKey) throws Exception {

		Key key = new SecretKeySpec(secretKey.getBytes(), AES_CIPHER);

		Cipher c = Cipher.getInstance(AES_CIPHER);

		c.init(Cipher.ENCRYPT_MODE, key);

		return new Hexadecimal(c.doFinal(message.getBytes()));
	}

	/**
	 * Decrypts a hexadecimal message into the plain message
	 * 
	 * @param encryptedMessage
	 *            The hexadecimal encrypted message
	 * @param secretKey
	 *            the secret key used to encrypt the message
	 * 
	 * @return The decrypted message
	 * 
	 * @throws Exception
	 *             Thrown if any problem occur when decrypting the message
	 */
	public String decrypt(Hexadecimal encryptedMessage, String secretKey) throws Exception {

		Key key = new SecretKeySpec(secretKey.getBytes(), AES_CIPHER);

		Cipher c = Cipher.getInstance(AES_CIPHER);

		c.init(Cipher.DECRYPT_MODE, key);

		return new String(c.doFinal(encryptedMessage.asByteArray()));
	}
}
