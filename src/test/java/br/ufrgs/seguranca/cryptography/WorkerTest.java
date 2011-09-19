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

	private static final String BIG_TEXT = "3AD5A2B4AB307932942D3A78ED8255EB" +
            "D8C473FCB32960346C3568FC8ED7B615" +
            "A48CF384BFFDBCFBD2BFEDCCBD65BAE7" +
            "07405BD70A93DE1EEF514A2D9F2710C3" +
            "9498B9E50D9A7784B0F5E27FF6459DF7" +
            "831897A6217824D7123671598F5DCAF7" +
            "2227D0CBBCC7A0A3B6501209FF2AD527" +
            "00EDC381AB87113EB212CDEBEE7063B4" +       
            "5E945010227A5CD3D71BD48437C40C37" +
            "9EA81C9EBF690E2B77A0AABE290E0FC3" +
            "EFFB1D0B43E9C3D783642EB36C6BA8F4" +
            "C8048BA1D1C6FD52CBEF093C55CD78D5" +
            "1BD62C15DBD1878C6A72E377516D566D" +
            "23E5AF78F46BDFB92FCDF661FD6F4E43" +
            "1C372E1C9D4D4CD316EC8D089ED2D206" +
            "452741326ED84EF07F61053E030822EB" +
            "ACF1576F43B4009C9D36A4A349C70A29" +
            "9312238EAE619D3ADC2DB034D40357F1";

	
	private Set<String> dictionary;
	
	@Before
	public void setUp() throws Exception {
		
		dictionary = new HashSet<String>();
		dictionary.add(" que ");
		dictionary.add(" um ");
		dictionary.add(" uma ");
		dictionary.add(" para ");
		dictionary.add(" os ");
		dictionary.add(" as ");
		dictionary.add(" ele ");
		dictionary.add(" ela ");
		dictionary.add(" voce ");
		dictionary.add("3AD5A2B4AB307932942D3A78ED8255EB");
	}

	@Test
	public void test() throws Exception {
		
		worker = new Worker(ENCODED_MESSAGE + HEXA_MESSAGE_PADDING, PARTIAL_KEY, dictionary, 2, 98, 99);
		
		String decodedMessage = worker.call();
		
		Assert.assertEquals(EXPECTED_MESSAGE, decodedMessage);
	}

	@Test
	public void bigText() throws Exception {
			
		KeyBasedCipher cipher = new AESCipher();
		Hexadecimal encoded = cipher.encrypt(BIG_TEXT, "essasenhaehfraca");
		
		worker = new Worker(encoded.getValue(), PARTIAL_KEY, dictionary, 2, 98, 99);
		
		worker.call();
	}
	
	@Test
	public void bigTextRocks() {
		
		Assert.assertEquals(BIG_TEXT, "3AD5A2B4AB307932942D3A78ED8255EBD8C473FCB32960346C3568FC8ED7B615A48CF384BFFDBCFBD2BFEDCCBD65BAE707405BD70A93DE1EEF514A2D9F2710C39498B9E50D9A7784B0F5E27FF6459DF7831897A6217824D7123671598F5DCAF72227D0CBBCC7A0A3B6501209FF2AD52700EDC381AB87113EB212CDEBEE7063B45E945010227A5CD3D71BD48437C40C379EA81C9EBF690E2B77A0AABE290E0FC3EFFB1D0B43E9C3D783642EB36C6BA8F4C8048BA1D1C6FD52CBEF093C55CD78D51BD62C15DBD1878C6A72E377516D566D23E5AF78F46BDFB92FCDF661FD6F4E431C372E1C9D4D4CD316EC8D089ED2D206452741326ED84EF07F61053E030822EBACF1576F43B4009C9D36A4A349C70A299312238EAE619D3ADC2DB034D40357F1");
	}
}
