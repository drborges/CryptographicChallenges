package br.ufrgs.seguranca.cryptography;

import java.io.FileNotFoundException;

import junit.framework.Assert;

import org.junit.Test;

/**
 * Unit test for {@link MessageExtractor}
 * 
 * @author diego
 * @since Sep 17, 2011
 *
 */
public class MessageExtractorTest {

	public static final String EXPECTED_MESSAGE = "a506a19333f306ac\n2c62cbe931963ae7\ndfcffa940360a40f\nfd5dc69b9c2e53ad";
	
	@Test
	public void shouldExtractMessageFromFile() throws FileNotFoundException {
		
		String extractedMessage = MessageExtractor.extract("src/test/resources/testMessage");
		
		Assert.assertEquals(EXPECTED_MESSAGE, extractedMessage);
	}

	
	@Test(expected=FileNotFoundException.class)
	public void shouldThrownFileNotFoundExceptionIfTheProvidedFilePathDoesNotExist() throws FileNotFoundException {
		
		MessageExtractor.extract("dummy/path/no-file");
	}
	
}
