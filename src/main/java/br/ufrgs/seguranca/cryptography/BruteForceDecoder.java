package br.ufrgs.seguranca.cryptography;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class BruteForceDecoder {
	
	private KeyBasedCipher cipher;
	
	private final Hexadecimal ecodedMessage;
	private int missingKeySuffixSize;
	private String key;
	
	private int availableProcessorsCount;
	
	public BruteForceDecoder(Hexadecimal encodedMessage, String key) {

		this.cipher = new AESCipher();
		this.ecodedMessage = encodedMessage;
		this.key = key;
		
		missingKeySuffixSize = 0;
		availableProcessorsCount = Runtime.getRuntime().availableProcessors();
	}
	
		

	public Future<String> decode() {
		
		ExecutorService executor = Executors.newFixedThreadPool(availableProcessorsCount);

		Future<String> asynchResult = executor.submit(new Callable<String>() {
			public String call() {
				try {
					return cipher.decrypt(ecodedMessage, key);
				} catch (Exception e) {
					throw new RuntimeException(e);
				}
			}
		});
		
		return asynchResult;
	}
	
	public void setMissingKeySuffixSize(int missingSuffixSize) {
		this.missingKeySuffixSize = missingSuffixSize;
	}

	public Hexadecimal getEcodedMessage() {
		return ecodedMessage;
	}

	public int getMissingKeySuffixSize() {
		return missingKeySuffixSize;
	}

}