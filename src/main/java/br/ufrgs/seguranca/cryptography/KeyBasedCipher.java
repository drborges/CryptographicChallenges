package br.ufrgs.seguranca.cryptography;

public interface KeyBasedCipher {

	public Hexadecimal encrypt(String message, String secretKey) throws Exception;
	public String decrypt(Hexadecimal encryptedMessage, String secretKey) throws Exception;
	
}
