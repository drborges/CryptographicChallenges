package br.ufrgs.seguranca.cryptography;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.SliderUI;

public class BruteForceDecoder {
	
	private KeyBasedCipher cipher;
	
	private final String encodedMessage;
	private int missingKeySuffixSize;
	private String partialKey;
	
	private int availableProcessorsCount;
	private Set<String> dictionary;
	
	public BruteForceDecoder(String encodedMessage, String partialKey) {

		this.cipher = new AESCipher();
		this.encodedMessage = encodedMessage;
		this.partialKey = partialKey;
		
		missingKeySuffixSize = 0;
		availableProcessorsCount = Runtime.getRuntime().availableProcessors();
		
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
		dictionary.add(" e ");
		dictionary.add(" a ");
		dictionary.add(" o ");
		dictionary.add(" qual ");
		dictionary.add(" criptografia ");
		dictionary.add(" banana ");
		dictionary.add(" estudo ");
		dictionary.add(", ");
		dictionary.add(" palavra ");
		dictionary.add(" com ");
		dictionary.add(" sem ");
		dictionary.add(" trabalho ");
		dictionary.add(" desafio ");
	}

	public void decode() throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessorsCount);

		Worker w1 = new Worker(encodedMessage, partialKey, dictionary, 5, 33, 57);
		Worker w2 = new Worker(encodedMessage, partialKey, dictionary, 5, 58, 82);
		Worker w3 = new Worker(encodedMessage, partialKey, dictionary, 5, 83, 106);
		Worker w4 = new Worker(encodedMessage, partialKey, dictionary, 5, 107, 126);
		
		executor.submit(w1);
		executor.submit(w2);
		executor.submit(w3);
		executor.submit(w4);
		
		executor.awaitTermination(10, TimeUnit.HOURS);
	}
	
	public void setMissingKeySuffixSize(int missingSuffixSize) {
		this.missingKeySuffixSize = missingSuffixSize;
	}

	public int getMissingKeySuffixSize() {
		return missingKeySuffixSize;
	}

}