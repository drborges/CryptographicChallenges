package br.ufrgs.seguranca.cryptography;

import java.util.concurrent.Callable;

public class Worker implements Callable<String> {

	private Hexadecimal encodedMessage;
	private String partialKey;

	private AsciiKeyGenerator keyGenerator;
	private KeyBasedCipher cipher;
	
	private String bestKey;
	private int bestKeyEvaluationPoints;

	public Worker(String encodedMessage, String partialKey, int suffixSize, int lower, int upper) {

		this.encodedMessage = new Hexadecimal().setValue(encodedMessage);
		this.partialKey = partialKey;

		cipher = new AESCipher();
		keyGenerator = new AsciiKeyGenerator(suffixSize, lower, upper);
	}

	private String computePadding(String key) throws Exception {

		String hexaValue = cipher.encrypt(key, key).getValue();
		return hexaValue.substring(hexaValue.length() / 2);
	}

	public String call() throws Exception {

		while (keyGenerator.hasNext()) {
		
			StringBuilder keyBuilder = new StringBuilder();
			String key = keyBuilder.append(partialKey).append(String.valueOf(keyGenerator.next())).toString();

			String padding = computePadding(key);
			String decodedMessage = cipher.decrypt(encodedMessage.setPadding(padding), key);
			
			int decodedMessageEvaluationPoints = evaluate(decodedMessage);
			if (decodedMessageEvaluationPoints > bestKeyEvaluationPoints && decodedMessageEvaluationPoints > 10) {
				bestKey = key;
				bestKeyEvaluationPoints = decodedMessageEvaluationPoints;
				
				System.out.println("_______________");
				System.out.println(bestKey);
				System.out.println(bestKeyEvaluationPoints);
				System.out.println(decodedMessage);
			}
			
			if (key.equals("essasenhaehfraca")) System.out.println("#### ele acha a maldita chave!");
		}

		return bestKey;
	}

	private int evaluate(String key) {
		
		int points = 0;
		
		for (char c : key.toCharArray()) {
			if (c >= 33 && c <= 126) {
				points++;
			}
		}
		
		return points;
	}

	public void setKeyBasedCipher(KeyBasedCipher cipher) {
		this.cipher = cipher;
	}
}
