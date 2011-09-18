package br.ufrgs.seguranca.cryptography;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import junit.framework.Assert;

import org.junit.Before;
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

	public static final String ENCODED_MESSAGE = "A506A19333F306AC2C62CBE931963AE7";
	public static final String EXPECTED_MESSAGE = "Texto para teste";
	public static final String PARTIAL_KEY = "essasenhaeh";
	
	private BruteForceDecoder decoder;

	private Hexadecimal encodedMessage;
		
	@Before
	public void setUp() throws Exception {
		
		encodedMessage = new Hexadecimal().setValue(ENCODED_MESSAGE);
		
		decoder = new BruteForceDecoder(encodedMessage, PARTIAL_KEY);
		
		decoder.setMissingKeySuffixSize(5);
	}

	@Test
	public void decoderShouldDiscoverKeyMissingSufixAndDecodeTheMessage() throws InterruptedException, ExecutionException {
		
		Future<String> decodedMessage = decoder.decode();
		
		Assert.assertEquals(EXPECTED_MESSAGE, decodedMessage.get());
	}

	@Test
	public void decoderShouldUserOneWorkerPerAvaiableCPU() {

	}
}
