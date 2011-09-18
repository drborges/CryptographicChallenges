package br.ufrgs.seguranca.cryptography;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorkerTest {

	private Worker worker;
	
	public static final String ENCODED_MESSAGE = "A506A19333F306AC2C62CBE931963AE7";
	public static final String EXPECTED_MESSAGE = "Texto para teste";
	public static final String PARTIAL_KEY = "essasenhaeh";
	
	@Mock KeyBasedCipher cipher;
	
	@Before
	public void setUp() throws Exception {
		worker = new Worker(ENCODED_MESSAGE, PARTIAL_KEY, 2, 33, 57);
		worker.setKeyBasedCipher(cipher);
	}

	@Test
	public void test() throws Exception {
		
		String decodedMessage = worker.call();
		
		Assert.assertEquals(EXPECTED_MESSAGE, decodedMessage);
	}

}
