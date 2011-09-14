package br.ufrgs.seguranca.cryptography;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AESCipherTest {

	private AESCipher cipher;
	
	public static final String MESSAGE = "Texto para teste";
	public static final String HEXA_MESSAGE = "546578746F2070617261207465737465";
	
	public static final String SECRET_KEY = "essasenhaehfraca";
	
	@Before
	public void setUp() throws Exception {
		cipher = new AESCipher();
	}

	@Test
	public void testEncrypt() throws Exception {
		
		Hexadecimal hexa = cipher.encrypt(MESSAGE, SECRET_KEY);
		
		Assert.assertEquals(HEXA_MESSAGE, hexa.getValue());
	}

	@Test
	public void testDecrypt() {
		fail("Not yet implemented");
	}

}
