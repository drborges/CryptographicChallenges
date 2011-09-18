package br.ufrgs.seguranca.cryptography;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MessageExtractor {

	/**
	 * Extracts a message from a file.
	 * 
	 * @param messageFilePath the path to the file holding the message
	 * 
	 * @return a String with the extracted message
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static String extract(String messageFilePath) throws FileNotFoundException {
		
		return extract(new File(messageFilePath));
	}
	
	/**
	 * Extracts a message from a file.
	 * 
	 * @param messageFile the file holding the message
	 * 
	 * @return a String with the extracted message
	 * 
	 * @throws FileNotFoundException Thrown if the file is not found
	 */
	public static String extract(File messageFile) throws FileNotFoundException {

		StringBuilder messageBuilder = new StringBuilder();
		String lineSeparator = System.getProperty("line.separator");
		Scanner scanner = new Scanner(messageFile, "UTF-8");

		try {
			while (scanner.hasNextLine()) {
				messageBuilder.append(scanner.nextLine()).append(lineSeparator);
			}
			
		} finally {
			scanner.close();
		}

		return messageBuilder.toString().trim();
	}
}
