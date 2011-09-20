package br.ufrgs.seguranca.cryptography;

import java.util.Arrays;
import java.util.Iterator;

public class AsciiKeyGenerator implements Iterator<String> {

	public static final int KEY_FIRST_CHAR_INDEX = 0;
	public static final int ASCII_LOWER_VALUE = 33;
	public static final int ASCII_UPPER_VALUE = 126;
	
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
		lastGeneratedKey = getKeyTemplate();
	}

	public AsciiKeyGenerator(char[] firstKey, int keySize, int lowerAsciiValue, int upperAsciiValue) {

		this.keySize = keySize;
		this.lowerAsciiValue = lowerAsciiValue;
		this.upperAsciiValue = upperAsciiValue;
		
		lastCharIndex = firstKey.length - 1;
		lastGeneratedKey = firstKey;
	}
	
	/**
	 * Gets a template to generate the first key.
	 * 
	 * <p>
	 * This template is a char array of length equals to the provided key size. Each char in the array
	 * has an initial value equals to the {@link AsciiKeyGenerator#ASCII_LOWER_VALUE} except the last char
	 * that has a value equals to {@link AsciiKeyGenerator#ASCII_LOWER_VALUE} - 1, and the first one, with value
	 * equals to the provided lower ASCII value.
	 * </p>
	 * 
	 * @return the key template
	 */
	private char[] getKeyTemplate() {
		
		char[] key = new char[keySize];
		Arrays.fill(key, (char) ASCII_LOWER_VALUE);
		
		key[0] = (char) lowerAsciiValue;
		key[lastCharIndex] = ASCII_LOWER_VALUE - 1;
		
		return key;
	}
	
	/**
	 * Returns the next generated key.
	 * 
	 * @return a string representation of the generated key
	 */
	public String next() {
		
		lastGeneratedKey[lastCharIndex]++;
		String key = String.valueOf(lastGeneratedKey);
		
		if (lastGeneratedKey[lastCharIndex] > ASCII_UPPER_VALUE) {	
			computeOverflow(lastGeneratedKey, lastCharIndex);	
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

		if (index >= KEY_FIRST_CHAR_INDEX) {

			if (key[index] >= ASCII_UPPER_VALUE) {
				
				key[index] = ASCII_LOWER_VALUE;
				computeOverflow(key, index - 1);
			}
			else {
				key[index]++;
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see java.util.Iterator#hasNext()
	 */
	public boolean hasNext() {
		return lastGeneratedKey[0] <= upperAsciiValue;
	}

	/**
	 * Not supported!
	 */
	public void remove() {
		throw new UnsupportedOperationException("This operation is not supported by this iterator.");
	}
}
