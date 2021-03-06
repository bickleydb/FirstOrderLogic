/**
 * Class that encapsulates important constants for the entire program. These
 * include the encoding for special characters for writing math statements and
 * common variables. Also contains classifications for the special characters
 * for creating the statement tree for each input.
 * 
 * @author Daniel Bickley
 * 
 */

public class Constants {

	/**
	 * Does nothing. Shouldn't be called.
	 */
	private Constants() {}
	
	/**
	 * Character that represents "For All"
	 */
	public static final char FOR_ALL = '\u2200';

	
	public static final int INDENT_SIZE = 20;
	/**
	 * Character that represents "There Exists"
	 */
	public static final char THERE_EXISTS = '\u2203';

	/**
	 * Character that represents "Not"
	 */
	public static final char NOT = '\u00ac';

	/**
	 * Character that represents "And"
	 */
	public static final char AND = '\u2227';

	/**
	 * Character that represents "Or"
	 */
	public static final char OR = '\u2228';

	/**
	 * Character that represents an implication
	 */
	public static final char IMPLIES = '\u21d2';

	/**
	 * Character that represents if and only if
	 */
	public static final char IFF = '\u21d4';

	/**
	 * Character that that represents the common variable "X"
	 */
	public static final char X = 'x';

	/**
	 * Character that represents the common variable "Y"
	 */
	public static final char Y = 'y';

	/**
	 * String that represents the common variable "X'"
	 */
	public static final String X_PRIME = "x\'";

	/**
	 * String that represents the common variable "Y'"
	 */
	public static final String Y_PRIME = "y\'";

	
	public static final String binary = Character.toString(AND)+
		Character.toString(OR)+ Character.toString(IMPLIES)+
		Character.toString(IFF);
	/**
	 * Array that contains all of the characters that are used to separate and
	 * evaluate two different statements
	 */
	public static final String[] seperators = { Character.toString(AND),
			Character.toString(OR), Character.toString(IMPLIES),
			Character.toString(IFF) };

	/**
	 * Array that contains all of the pre-defined variables that can be used.
	 */
	public static final String[] variables = { Character.toString(X) + " ",
			Character.toString(Y) + " ", X_PRIME + " ", Y_PRIME + " " };

	/**
	 * Array that contains all of the scopes that are possible for a variable to
	 * be applied to.
	 * 
	 */
	public static final String[] scopes = { Character.toString(FOR_ALL),
			Character.toString(THERE_EXISTS), Character.toString(Constants.NOT) };

	
}
