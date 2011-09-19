package br.ufrgs.seguranca.cryptography;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.Callable;

public class Worker implements Callable<String> {

	private Hexadecimal encodedMessage;
	private String partialKey;
	private Set<String> dictionary;

	private AsciiKeyGenerator keyGenerator;
	private KeyBasedCipher cipher;

	public Worker(String encodedMessage, String partialKey, Set<String> dictionary, int suffixSize, int lower, int upper) {

		this.encodedMessage = new Hexadecimal().setValue(encodedMessage);
		this.partialKey = partialKey;
		this.dictionary = dictionary;

		cipher = new AESCipher();
		keyGenerator = new AsciiKeyGenerator(suffixSize, lower, upper);
	}

	private String computePadding(String key) throws Exception {

		String hexaValue = cipher.encrypt(key, key).getValue();
		return hexaValue.substring(hexaValue.length() / 2);
	}

	public String call() throws Exception {

		StringBuilder keyBuilder = new StringBuilder();
		String key = keyBuilder.append(partialKey).append(String.valueOf(keyGenerator.next())).toString();

		boolean done = false;
		while (!done) {
			
			try {
				System.out.println(key);
				
				String padding = computePadding(key);
				String decodedMessage = cipher.decrypt(encodedMessage.setPadding(padding), key);
				if (containsDictionaryWords(decodedMessage)) {
					writeMessageAndKeyIntoFile(decodedMessage, key);
				}
				
				keyBuilder = new StringBuilder();
				key = keyBuilder.append(partialKey).append(String.valueOf(keyGenerator.next())).toString();
				
			} catch (NoMoreKeysException e) {
				done = true;
			}
		}

		return "dummy";
	}

	private void writeMessageAndKeyIntoFile(String message, String key) throws IOException {

		File file = new File("/home/diego/Desktop/decoded_" + System.currentTimeMillis() + ".txt");
		file.createNewFile();
		
		FileWriter fstream = new FileWriter(file);
		BufferedWriter out = new BufferedWriter(fstream);
		out.write("Key: " + key);
		out.newLine();
		out.newLine();
		out.write("Message: " + message);
		out.close();
	}

	private boolean containsDictionaryWords(String decodedMessage) {

		for (String word : dictionary) {
			if (decodedMessage.contains(word)) {
				return true;
			}
		}

		return false;
	}

	public void setKeyBasedCipher(KeyBasedCipher cipher) {
		this.cipher = cipher;
	}
}
