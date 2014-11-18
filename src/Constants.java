
public class Constants {
	public static final char FOR_ALL = '\u2200';
	public static final char THERE_EXISTS = '\u2203';
	public static final char NOT = '\u00ac';
	public static final char AND = '\u2227';
	public static final char OR = '\u2228';
	public static final char IMPLIES = '\u21d2';
	public static final char IFF = '\u21d4';
	public static final char X = 'x';
	public static final char Y = 'y';
	public static final String X_PRIME = "x\'";
	public static final String Y_PRIME = "y\'";
	
	public static final String[] seperators = { Character.toString(AND), Character.toString(OR), Character.toString(IMPLIES), Character.toString(IFF)};
	public static final String[] variables = { Character.toString(X)+" " , Character.toString(Y) +" ", X_PRIME+" ", Y_PRIME+" "} ;
	public static final String[] scopes = { Character.toString(FOR_ALL), Character.toString(THERE_EXISTS) };
	

}
