package br.ufrgs.seguranca.cryptography;

import java.util.Set;
import java.util.concurrent.Callable;

public class Worker implements Callable<String> {

	private String encodedMessage;
	private String partialKey;
	private int suffixSize;
	private int lower;
	private int upper;
	
	private int currentAsciiValue;
	
	private Set<String> dictionary;
	private AsciiKeyGenerator keyGenerator;
	private KeyBasedCipher keyBasedCipher;

	public Worker(String encodedMessage, String partialKey, int suffixSize, int lower, int upper) {
		
		this.encodedMessage = encodedMessage;
		this.partialKey = partialKey;
		this.suffixSize = suffixSize;
		this.lower = lower;
		this.upper = upper;
		this.currentAsciiValue = lower;
		
		keyBasedCipher = new AESCipher();
	}
	
	public String call() throws Exception {
		
		
	}

	public void setKeyBasedCipher(KeyBasedCipher cipher) {
		this.keyBasedCipher = cipher;
	}
	
}
