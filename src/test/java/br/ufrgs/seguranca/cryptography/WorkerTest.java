package br.ufrgs.seguranca.cryptography;

import java.util.HashSet;
import java.util.Set;

import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class WorkerTest {

	private Worker worker;
	
	public static final String ENCODED_MESSAGE = "A506A19333F306AC2C62CBE931963AE7";
	public static final String HEXA_MESSAGE_PADDING = "DFCFFA940360A40FFD5DC69B9C2E53AD";
	public static final String EXPECTED_MESSAGE = "Texto para teste";
	public static final String PARTIAL_KEY = "essasenhaehfra";
	
	@Before
	public void setUp() throws Exception {
		
		Set<String> dictionary = new HashSet<String>();
		dictionary.add(" que ");
		dictionary.add(" um ");
		dictionary.add(" uma ");
		dictionary.add(" para ");
		dictionary.add(" os ");
		dictionary.add(" as ");
		dictionary.add(" ele ");
		dictionary.add(" ela ");
		dictionary.add(" voce ");
		dictionary.add(" você ");
		
		worker = new Worker(ENCODED_MESSAGE + HEXA_MESSAGE_PADDING, PARTIAL_KEY, dictionary, 2, 98, 99);
	}

	@Test
	public void test() throws Exception {
		
		String decodedMessage = worker.call();
		
		Assert.assertEquals(EXPECTED_MESSAGE, decodedMessage);
	}

}
