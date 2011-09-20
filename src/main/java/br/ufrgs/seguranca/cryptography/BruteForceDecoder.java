package br.ufrgs.seguranca.cryptography;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class BruteForceDecoder {
	
	private KeyBasedCipher cipher;
	
	private final String encodedMessage;
	private int missingKeySuffixSize;
	private String partialKey;
	
	private int availableProcessorsCount;
	
	public BruteForceDecoder(String encodedMessage, String partialKey) {

		this.cipher = new AESCipher();
		this.encodedMessage = encodedMessage;
		this.partialKey = partialKey;
		
		missingKeySuffixSize = 0;
		availableProcessorsCount = Runtime.getRuntime().availableProcessors();
	}

	public void decode() throws InterruptedException {
		
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessorsCount);

		Worker w1 = new Worker(encodedMessage, partialKey, 5, 33, 57);
		Worker w2 = new Worker(encodedMessage, partialKey, 5, 58, 82);
		Worker w3 = new Worker(encodedMessage, partialKey, 5, 83, 106);
		Worker w4 = new Worker(encodedMessage, partialKey, 5, 107, 126);
		
		executor.submit(w1);
		executor.submit(w2);
		executor.submit(w3);
		executor.submit(w4);
		
		executor.awaitTermination(2, TimeUnit.HOURS);
	}
	
	public void setMissingKeySuffixSize(int missingSuffixSize) {
		this.missingKeySuffixSize = missingSuffixSize;
	}

	public int getMissingKeySuffixSize() {
		return missingKeySuffixSize;
	}

}