package br.ufrgs.seguranca.cryptography;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class BruteForceDecoder {

	private final String encodedMessage;
	private int missingKeySuffixSize;
	private String partialKey;

	private int availableProcessorsCount;

	public BruteForceDecoder(String encodedMessage, String partialKey) {

		this.encodedMessage = encodedMessage;
		this.partialKey = partialKey;

		missingKeySuffixSize = 0;
		availableProcessorsCount = Runtime.getRuntime().availableProcessors();
	}

	public void decode() throws InterruptedException, ExecutionException {

		ExecutorService executor = Executors
				.newFixedThreadPool(availableProcessorsCount);

		// Worker w1 = new Worker(encodedMessage, partialKey, 5, 33, 57);
		// Worker w2 = new Worker(encodedMessage, partialKey, 5, 58, 82);
		// Worker w3 = new Worker(encodedMessage, partialKey, 5, 83, 106);
		// Worker w4 = new Worker(encodedMessage, partialKey, 5, 107, 126);

		Worker w1 = new Worker(encodedMessage, partialKey, 5, 33, 47);
		Worker w2 = new Worker(encodedMessage, partialKey, 5, 48, 57);
		Worker w3 = new Worker(encodedMessage, partialKey, 5, 58, 64);
		Worker w4 = new Worker(encodedMessage, partialKey, 5, 65, 75);
		Worker w5 = new Worker(encodedMessage, partialKey, 5, 76, 90);
		Worker w6 = new Worker(encodedMessage, partialKey, 5, 91, 105);
		Worker w7 = new Worker(encodedMessage, partialKey, 5, 106, 118);
		Worker w8 = new Worker(encodedMessage, partialKey, 5, 119, 126);

		Future<String> keyFromWork1 = executor.submit(w1);
		Future<String> keyFromWork2 = executor.submit(w2);
		Future<String> keyFromWork3 = executor.submit(w3);
		Future<String> keyFromWork4 = executor.submit(w4);
		Future<String> keyFromWork5 = executor.submit(w5);
		Future<String> keyFromWork6 = executor.submit(w6);
		Future<String> keyFromWork7 = executor.submit(w7);
		Future<String> keyFromWork8 = executor.submit(w8);
		
		executor.awaitTermination(24, TimeUnit.HOURS);

		System.out.println("Key 1: " + keyFromWork1.get());
		System.out.println("Key 2: " + keyFromWork2.get());
		System.out.println("Key 3: " + keyFromWork3.get());
		System.out.println("Key 4: " + keyFromWork4.get());
		System.out.println("Key 5: " + keyFromWork5.get());
		System.out.println("Key 6: " + keyFromWork6.get());
		System.out.println("Key 7: " + keyFromWork7.get());
		System.out.println("Key 8: " + keyFromWork8.get());
	}

	public void setMissingKeySuffixSize(int missingSuffixSize) {
		this.missingKeySuffixSize = missingSuffixSize;
	}

	public int getMissingKeySuffixSize() {
		return missingKeySuffixSize;
	}

	public static void writeToFile(String key, String encodedMessage) throws Exception {

		File result = new File("/home/diego/Desktop/result.txt");
		if (!result.exists()) {
			result.createNewFile();
		}
		
		AESCipher cipher = new AESCipher();
		String padding = cipher.computePadding(key);
		String decodedMessage = cipher.decrypt(new Hexadecimal().setValue(encodedMessage).setPadding(padding), key);
		
		FileWriter fstream = new FileWriter(result);
		BufferedWriter out = new BufferedWriter(fstream);
		out.append("\n######");
		out.append("\nKey: " + key);
		out.append("\nMessage: " + decodedMessage);
		out.append("\n######");
		// Close the output stream
		out.close();
		fstream.close();
	}
}