package br.ufrgs.seguranca.cryptography;

/**
 * Evaluates a decoded message based on the ASCII values of it's characters.
 * 
 * <p>
 * For each ASCII value between {@link MessageEvaluator#ASCII_LOWER_VALUE} and
 * {@link MessageEvaluator#ASCII_UPPER_VALUE} the evaluations value is incremented,
 * for all other chars outside this range, the value is decremented.
 * </p>
 * 
 * @author diego
 * @since Sep 20, 2011
 */
public class MessageEvaluator {

	public static final int ASCII_UPPER_VALUE = 126;
	public static final int ASCII_LOWER_VALUE = 33;

	/**
	 * Evaluates a decoded message.
	 * 
	 * @param message the message to be evaluated
	 * @return the evaluation value
	 */
	public static int evaluate(String message) {

		int points = 0;

		for (char c : message.toCharArray()) {
			
			if (c >= ASCII_LOWER_VALUE && c <= ASCII_UPPER_VALUE) {
				points++;
				
			} else {
				points--;
			}
		}

		return points;
	}

}
