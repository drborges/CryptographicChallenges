package br.ufrgs.seguranca.cryptography;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AESCipherTest {

	private AESCipher cipher;
	
	public static final String MESSAGE = "Texto para teste";
	
	// Hexadecimal representation of the encrypted message (Provided by Weber)
	public static final String HEXA_MESSAGE = "A506A19333F306AC2C62CBE931963AE7";
	
	// This may be redundancy such as CRC or just padding
	public static final String HEXA_MESSAGE_PADDING = "DFCFFA940360A40FFD5DC69B9C2E53AD";
	
	public static final String SECRET_KEY = "essasenhaehfraca";
	
	@Before
	public void setUp() throws Exception {
		cipher = new AESCipher();
	}

	@Test
	public void shouldEncryptTheMessage() throws Exception {
		
		Hexadecimal hexa = cipher.encrypt(MESSAGE, SECRET_KEY);
		
		Assert.assertEquals(HEXA_MESSAGE + HEXA_MESSAGE_PADDING, hexa.getValue());
	}

	@Test
	public void shouldDecryptMessage() throws Exception {
		
		Hexadecimal encrypted = new Hexadecimal().setValue(HEXA_MESSAGE + HEXA_MESSAGE_PADDING);
		
		Assert.assertEquals(MESSAGE, cipher.decrypt(encrypted, SECRET_KEY));
	}

}
