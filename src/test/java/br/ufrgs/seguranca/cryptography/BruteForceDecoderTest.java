package br.ufrgs.seguranca.cryptography;

import java.io.FileNotFoundException;
import java.util.concurrent.ExecutionException;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Unit test dor {@link BruteForceDecoder}
 * 
 * @author diego
 * @since Sep 17, 2011
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class BruteForceDecoderTest {

	@Test
	public void decoderShouldUserOneWorkerPerAvaiableCPU() throws FileNotFoundException, InterruptedException, ExecutionException {
		
		String encodedMessage = MessageExtractor.extract("src/main/resources/message");
		
		BruteForceDecoder decoder = new BruteForceDecoder(encodedMessage, "Key2Group02");
		decoder.setMissingKeySuffixSize(5);
		decoder.decode();
		
	}
	
	@Test
	public void shouldWriteResultIntoFile() throws Exception {
		
		BruteForceDecoder.writeToFile("essasenhaehfraca", "A506A19333F306AC2C62CBE931963AE7");
	}
}
