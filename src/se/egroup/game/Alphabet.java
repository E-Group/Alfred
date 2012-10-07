package se.egroup.game;

import java.util.Random;

/**
 * A class that contains the alphabetic functions of the game
 * @author Lalle
 *
 */
public class Alphabet {

	private static final char[] LETTERS = {
		'A','B','C','D','E','F','G','H','I','J','K',
		'L','M','N','O','P','Q','R','S','T','U','V',
		'W','X','Y','Z','Å','Ä','Ö'
	};
	
	private static final char[] TEST_LETTERS = {
		'A','B'
	};
	
	private static final int[] LETTER_VALUES = {
		1,	// A
		1,	// B
		1,	// C
		1,	// D
		1,	// E
		1,	// F
		1,	// G
		1,	// H
		1,	//osv.
	};
	
	/**
	 * Randomizes a new letter
	 * @return the letter
	 */
	public char getNewLetter(){
		Random random = new Random();
		//return LETTERS[random.nextInt(LETTERS.length)];
		return TEST_LETTERS[random.nextInt(TEST_LETTERS.length)];
	}
	
	/**
	 * Returns the value of a letter
	 * @param letter - the letters int value (A=0)
	 * @return the value of the letter
	 */
	public int getValue(int letter){
		return LETTER_VALUES[letter];
	}
	
}
