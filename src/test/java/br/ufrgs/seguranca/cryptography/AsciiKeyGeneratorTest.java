package br.ufrgs.seguranca.cryptography;

import junit.framework.Assert;

import org.junit.Test;

public class AsciiKeyGeneratorTest {

	private AsciiKeyGenerator keyGenerator;
	
	public static final int KEY_SIZE = 2;
	public static final int LOWER_ASCII_VALUE = 57;
	public static final int UPPER_ASCII_VALUE = 58;
	

	@Test
	public void shouldGenerateTheThreeFirstKeys() throws NoMoreKeysException {
	
		keyGenerator = new AsciiKeyGenerator(KEY_SIZE, LOWER_ASCII_VALUE, UPPER_ASCII_VALUE);
		
		String firstKey = keyGenerator.next();
		String secondKey = keyGenerator.next();
		String thirdKey = keyGenerator.next();
		
		Assert.assertEquals("9!", firstKey);
		Assert.assertEquals("9\"", secondKey);
		Assert.assertEquals("9#", thirdKey);
	}

	@Test
	public void shouldComputeOverflowOfTheLastKeyCharacter() {
		
		keyGenerator = new AsciiKeyGenerator(KEY_SIZE, LOWER_ASCII_VALUE, UPPER_ASCII_VALUE);
		
		char[] key = new char[] { 36, 126, 127 };
		keyGenerator.computeOverflow(key, 2);
		
		Assert.assertEquals("%!!", String.valueOf(key));
	}
}
