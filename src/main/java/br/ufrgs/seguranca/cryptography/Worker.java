package br.ufrgs.seguranca.cryptography;

import java.io.BufferedWriter;
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

		cipher = new AESCipher();
		keyGenerator = new AsciiKeyGenerator(suffixSize, lower, upper);
	}

	public String call() throws Exception {

		keyGenerator.generate();

		StringBuilder builder = new StringBuilder();
		for (char[] keySuffix : keyGenerator.getGeneratedKeys()) {

			String key = builder.append(partialKey).append(String.valueOf(keySuffix)).toString();
			String decodedMessage = cipher.decrypt(encodedMessage, key);
			if (containsDictionaryWords(decodedMessage)) {
				writeMessageAndKeyIntoFile(decodedMessage, key);
			}
		}

		return "dummy";
	}

	private void writeMessageAndKeyIntoFile(String message, String key) throws IOException {

		FileWriter fstream = new FileWriter("/src/test/resources/decoded_" + System.currentTimeMillis() + ".txt");
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
