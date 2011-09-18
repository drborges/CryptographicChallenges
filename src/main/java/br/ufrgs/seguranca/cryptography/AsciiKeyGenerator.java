package br.ufrgs.seguranca.cryptography;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class AsciiKeyGenerator {

	private static final int ASCII_LOWER_VALUE = 33;
	private static final int ASCII_UPPER_VALUE = 126;
	
	private final int upperAsciiValue;
	private final int lowerAsciiValue;
	private final int keySize;
	private final int lastCharIndex;

	private final List<char[]> generatedKeys = new LinkedList<char[]>();

	public AsciiKeyGenerator(int keySize, int lowerAsciiValue, int upperAsciiValue) {

		this.keySize = keySize;
		this.lowerAsciiValue = lowerAsciiValue;
		this.upperAsciiValue = upperAsciiValue;
		
		lastCharIndex = keySize - 1;
	}

	/**
	 * Gets the trivial key (all characters are equal to the ASCII_LOWER_VALUE) as
	 * template to generate all other possibilities.
	 * 
	 * @return the key template
	 */
	private char[] getKeyTemplate() {
		
		char[] key = new char[keySize];
		Arrays.fill(key, (char) ASCII_LOWER_VALUE);
		
		return key;
	}
	
	/**
	 * Generates all possible ASCII (33 to 126) keys in the provided range.
	 */
	public void generate() {
		
		char[] key = getKeyTemplate();

		int ascii = ASCII_LOWER_VALUE;
		key[0] = (char) lowerAsciiValue;
		
		boolean done = false;
		while (!done) {

			key[lastCharIndex] = (char) ascii;
			generatedKeys.add(key);

			key = key.clone();
			
			System.out.println(key);
			ascii++;
			if (ascii > ASCII_UPPER_VALUE) {
				
				ascii = ASCII_LOWER_VALUE;
				
				key[lastCharIndex] = ASCII_LOWER_VALUE;
				computeOverflow(key, lastCharIndex - 1);
				
				if (key[0] > upperAsciiValue) {
					done = true;
				}
			}
		}
	}

	/**
	 * Updates recursively the key characters.
	 * 
	 * <p>
	 * Given a key with 3 characters, the updateKey will increment the ASCII value of the character
	 * at position equals to the provided index. If the new value of the ASCII at that position is
	 * greater than the maximum allowed ASCII value, then an overflow operation is made, the value
	 * at the related position will be the lower (allowed) ASCII value and the previous position
	 * (index - 1) is then incremented.<br/>
	 * This process is repeated for each character in the key.
	 * </p>
	 * 
	 * @param key the key to be updated
	 * @param index the index of the first position to be updated in the key 
	 */
	public void computeOverflow(char[] key, int index) {

		if (index >= 0) {

			if (key[index] == ASCII_UPPER_VALUE) {
				
				key[index] = ASCII_LOWER_VALUE;
				computeOverflow(key, index - 1);
			}
			else {
				key[index]++;
			}
		}
	}

	public List<char[]> getGeneratedKeys() {
		return generatedKeys;
	}
}
