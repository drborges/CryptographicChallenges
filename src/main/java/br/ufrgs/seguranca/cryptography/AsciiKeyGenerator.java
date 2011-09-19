package br.ufrgs.seguranca.cryptography;

import java.util.Arrays;

public class AsciiKeyGenerator {

	private static final int ASCII_LOWER_VALUE = 33;
	private static final int ASCII_UPPER_VALUE = 126;
	
	private final int upperAsciiValue;
	private final int lowerAsciiValue;
	private final int keySize;
	private final int lastCharIndex;

	private char[] lastGeneratedKey;

	public AsciiKeyGenerator(int keySize, int lowerAsciiValue, int upperAsciiValue) {

		this.keySize = keySize;
		this.lowerAsciiValue = lowerAsciiValue;
		this.upperAsciiValue = upperAsciiValue;
		
		lastCharIndex = keySize - 1;
		
		initialize();
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
	
	public void initialize() {
		lastGeneratedKey = getKeyTemplate();
		lastGeneratedKey[0] = (char) lowerAsciiValue;
	}
	
	/**
	 * Gets the next valid key.
	 * @throws NoMoreKeysException 
	 */
	public String next() throws NoMoreKeysException {

		lastGeneratedKey[lastCharIndex]++;
		String key = String.valueOf(lastGeneratedKey);
		
		if (lastGeneratedKey[lastCharIndex] > ASCII_UPPER_VALUE) {
			
			lastGeneratedKey[lastCharIndex] = ASCII_LOWER_VALUE;
			computeOverflow(lastGeneratedKey, lastCharIndex - 1);
			
			if (lastGeneratedKey[0] > upperAsciiValue) {
				throw new NoMoreKeysException("All possible keys were already generated.");
			}
		}
		
		return key;
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
}
