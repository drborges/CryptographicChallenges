package br.ufrgs.seguranca.cryptography;

import junit.framework.Assert;

import org.junit.Test;

public class HexadecimalTest {

	private Hexadecimal hexadecimal;
	
	@Test
	public void shouldCreateHexadecimalFromString() {
		
		String plainMessage = "Hello World";
		
		hexadecimal = new Hexadecimal(plainMessage);
		
		Assert.assertEquals(CipherUtils.asHex(plainMessage.getBytes()), hexadecimal.getValue());
	}

	@Test
	public void shouldCreateHexadecimalFromByteArray() {
		
		String plainMessage = "Hello World";
		
		hexadecimal = new Hexadecimal(plainMessage.getBytes());
		
		Assert.assertEquals(CipherUtils.asHex(plainMessage.getBytes()), hexadecimal.getValue());
	}
}
