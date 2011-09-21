package br.ufrgs.seguranca.cryptography;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * Component used to encrypt/decrypt messages using AES cipher
 * 
 * @author diego
 */
public class AESCipher {

	public static final String CHARSET = "UTF-8";
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
	public Hexadecimal encrypt(String message, String secretKey)
			throws Exception {

		Key key = new SecretKeySpec(secretKey.getBytes(CHARSET), AES_CIPHER);

		Cipher c = Cipher.getInstance(AES_CIPHER);

		c.init(Cipher.ENCRYPT_MODE, key);

		return new Hexadecimal(c.doFinal(message.getBytes()));
	}

	/**
	 * Decrypts a hexadecimal message into the plain message
	 * 
	 * @param encodedMessage
	 *            The hexadecimal encrypted message
	 * @param secretKey
	 *            the secret key used to encrypt the message
	 * 
	 * @return The decrypted message
	 * 
	 * @throws Exception
	 *             Thrown if any problem occur when decrypting the message
	 */
	public String decrypt(Hexadecimal encodedMessage, String secretKey)
			throws Exception {
		
		Key key = new SecretKeySpec(secretKey.getBytes(CHARSET), AES_CIPHER);

		Cipher c = Cipher.getInstance(AES_CIPHER);

		c.init(Cipher.DECRYPT_MODE, key);

		String decodedMessage = new String(c.doFinal(encodedMessage.asByteArray()));
		
		if (encodedMessage.hasPadding()) {
			decodedMessage = removePaddingFromDecodedMessage(decodedMessage, encodedMessage.getPadding(), secretKey);
		}
		
		return decodedMessage;
	}
	
	/**
	 * Computes the padding hexadecimal value for encoded messages using the provided key
	 * 
	 * @param key used to encode a message
	 * 
	 * @return the hexadecimal string value of the computed padding
	 * 
	 * @throws Exception Thrown if any error occur in the computation process
	 */
	public String computePadding(String key) throws Exception {

		String hexaValue = encrypt(key, key).getValue();
		String padding = hexaValue.substring(hexaValue.length() / 2);
		
		return padding;
	}
	
	/**
	 * Removes the padding from the decoded message
	 * 
	 * @param decodedMessage the decoded message to remove padding from
	 * @param padding 
	 * @param key the key used to decoded the message
	 * 
	 * @return the message without the padding
	 * 
	 * @throws Exception Thrown if an error occur in the padding computation process
	 */
	public String removePaddingFromDecodedMessage(String decodedMessage, Hexadecimal padding, String key) throws Exception {

		String decodedPadding = decrypt(padding, key);
		
		return decodedMessage.substring(0, decodedMessage.length() - decodedPadding.length());
	}

}
