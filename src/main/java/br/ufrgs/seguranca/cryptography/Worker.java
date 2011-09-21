package br.ufrgs.seguranca.cryptography;

import java.util.concurrent.Callable;

public class Worker implements Callable<String> {

	private Hexadecimal encodedMessage;
	private String partialKey;

	private AsciiKeyGenerator keyGenerator;
	private AESCipher cipher;
	
	private String bestKey;
	private int bestKeyEvaluationPoints;

	public Worker(String encodedMessage, String partialKey, int keySuffixSize, int lower, int upper) {

		this.encodedMessage = new Hexadecimal().setValue(encodedMessage);
		this.partialKey = partialKey;

		cipher = new AESCipher();
		keyGenerator = new AsciiKeyGenerator(keySuffixSize, lower, upper);
	}

	public String call() throws Exception {

		while (keyGenerator.hasNext()) {
		
			String key = partialKey + keyGenerator.next();

			encodedMessage.setPadding(cipher.computePadding(key));
			String decodedMessage = cipher.decrypt(encodedMessage, key);
			
			int decodedMessageEvaluationPoints = MessageEvaluator.evaluate(decodedMessage);
			if (decodedMessageEvaluationPoints > bestKeyEvaluationPoints && decodedMessageEvaluationPoints > 5) {
				bestKey = key;
				bestKeyEvaluationPoints = decodedMessageEvaluationPoints;
				
				System.out.println("## Best key so far: " + bestKey);
			}
		}

		return bestKey;
	}

	public void setKeyBasedCipher(AESCipher cipher) {
		this.cipher = cipher;
	}
}
