package br.ufrgs.seguranca.cryptography;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class AsciiKeyGeneratorTest {

	private AsciiKeyGenerator keyGenerator;
	
	public static final int KEY_SIZE = 2;
	public static final int LOWER_ASCII_VALUE = 57;
	public static final int UPPER_ASCII_VALUE = 58;
	
	@Before
	public void setUp() throws Exception {
		
		keyGenerator = new AsciiKeyGenerator(KEY_SIZE, LOWER_ASCII_VALUE, UPPER_ASCII_VALUE);
	}

	@Test
	public void shouldGenerateAllPossibleAsciiKeysBetweenProvidedRange() {
		
		keyGenerator.generate();
		
		Assert.assertEquals("9!", String.valueOf(keyGenerator.getGeneratedKeys().get(0)));
		Assert.assertEquals("9&", String.valueOf(keyGenerator.getGeneratedKeys().get(5)));
		Assert.assertEquals("9+", String.valueOf(keyGenerator.getGeneratedKeys().get(10)));
	}
	
	@Test
	public void shouldGenerate61852Keys() {
		
		keyGenerator.generate();
		
		Assert.assertEquals(188, keyGenerator.getGeneratedKeys().size());
	}

	@Test
	public void updateKeyShould() {
		
		char[] key = new char[] { '$', '~', '~' };
		keyGenerator.incValueAt(key, 1);
		
		Assert.assertEquals("%!~", String.valueOf(key));
	}
}
