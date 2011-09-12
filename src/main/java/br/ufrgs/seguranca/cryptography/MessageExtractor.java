package br.ufrgs.seguranca.cryptography;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class MessageExtractor {

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

		return messageBuilder.toString();
	}
}
