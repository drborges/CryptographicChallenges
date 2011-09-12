package br.ufrgs.seguranca.cryptography;

import java.io.File;
import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesAlgorithm {

	public static final String AES_CIPHER = "AES";

	/**
	 * Turns array of bytes into string
	 * 
	 * @param buf
	 *            Array of bytes to convert to hex string
	 * @return Generated hex string
	 */
	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}
	
	public static byte[] encrypt(String valueToEnc, String secretKey) throws Exception {
		
		Key key = new SecretKeySpec(secretKey.getBytes(), AES_CIPHER);
		
		Cipher ecCipher = Cipher.getInstance(AES_CIPHER);
		ecCipher.init(Cipher.ENCRYPT_MODE, key);
		return ecCipher.doFinal(valueToEnc.getBytes());
    }

    public static byte[] decrypt(String encryptedValue, String secretKey) throws Exception {
    	
    	Key key = new SecretKeySpec(secretKey.getBytes(), AES_CIPHER);
        Cipher c = Cipher.getInstance(AES_CIPHER);
        c.init(Cipher.DECRYPT_MODE, key);
        byte[] decValue = c.doFinal(encryptedValue.getBytes());
        
        return decValue;
    }

	public static void main(String[] args) throws Exception {

		String secretKey = args.length > 0 ? args[0] : "essasenhaehfraca";
		String filePath = args.length > 1 ? args[1] : "src/test/resources/testMessage";

		String encryptedMessage = MessageExtractor.extract(new File(filePath));

		byte[] byteMessage = encrypt("Texto para teste", secretKey);

		String strMessage = new String(byteMessage);
		String hexaMessage = asHex(byteMessage);
		System.out.println(hexaMessage);
		
		String dec = new String(decrypt(strMessage, secretKey));
		System.out.println(dec);
	}
}
