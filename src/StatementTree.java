import java.util.ArrayList;
import java.util.HashMap;

/**
 * Class that is used to determine if a given statement is consistent with the
 * rules of a given world. This is done by creating a tree structure of
 * StatementNodes and evaluating them based on the sequence of the nodes.
 * 
 * @author Daniel Bickley
 * 
 */
public class StatementTree {
	static ArrayList<String> vars;
	StatementNode root;
	static Universe uni;

	/**
	 * Creates the root of a tree, from which everything will be built on.
	 */
	public StatementTree(Universe uni) {
		root = new StatementNode("root", "root");
		vars = new ArrayList<String>();
		this.uni = uni;
	}

	/**
	 * Actually builds the tree before evaluation.
	 * 
	 * @param world
	 *            The world the statement will be evaluated with
	 * @param input
	 *            The statement that will be evaluated
	 */
	public void buildTree(Universe uni, String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();
		translateToGoodFormat(uni,input);
		getStatementsAndLinkers(input, statements, linkers, uni);
		StatementNode placeOnTree = root;
		
		//This section adds the "For All" and "There Exists" to the tree
		//Then moves it down to the actual meat of the tree
		addScopes(statements, linkers, placeOnTree);
		while (placeOnTree.center != null) {
			placeOnTree = placeOnTree.center;
		}
		
		input = input.substring(input.indexOf(" ") +1);
		
		//Recursively builds the tree
		placeOnTree.center = new StatementNode();
		placeOnTree = placeOnTree.center;
		buildTree(input, placeOnTree);
	}
	

	/**
	 * Recursively builds the tree, one node at a time. Has special instructions on how to insert a "not"
	 * node, as they work slightly differently.
	 * 
	 * @param input
	 * @param placeOnTree
	 */
	private void buildTree(String input, StatementNode placeOnTree) {
		input = input.trim();
		
		//When parenthisized correctly, the "NOT" will have an index of 2 when it needs to be added.
		if(input.charAt(2) == Constants.NOT) {
			
			input = input.substring(4);
			input = "(" + input;
			
			//Input has everything after the "not" from the input in the correct syntax
			placeOnTree.name=Character.toString(Constants.NOT);
			placeOnTree.center = new StatementNode();
			buildTree(input, placeOnTree.center);
			return;
			
		}
		
		if(input.charAt(0) == '(')
			input = input.substring(1);
		if(input.charAt(input.length()-1) == ')')
			input = input.substring(0,input.length()-1);
		
		int startString = 1;
		int numOpenParens = 1;
		while( (startString < input.length() && !isSeperator(input.charAt(startString))) || (startString<input.length() && numOpenParens != 0 )) {
			if(input.charAt(startString) == '(')
				numOpenParens++;
			if(input.charAt(startString) == ')')
				numOpenParens--;
			startString++;
		}
		
		//If startString is at the end of the string, then it only consists of a function
		if(startString >= input.length()-1) {
			//System.outprintln(input);
			placeOnTree.function = uni.getFunction(input.substring(0,input.indexOf("[")+1));
			placeOnTree.name = placeOnTree.function.getFunctionName();
			return;
		}
		
		//Build the left and right of the tree.
		placeOnTree.name = Character.toString(input.charAt(startString));

		String left = input.substring(0,startString);
		if(left.length()!= 0){
			placeOnTree.left = new StatementNode();
			buildTree(left,placeOnTree.left);
		}
		String right = input.substring(startString+1,input.length());
		if(right.length() != 0) {
			placeOnTree.right = new StatementNode();
			buildTree(right,placeOnTree.right);
			return;
		}
		
	}


	/**
	 * Helper method that adds all of the scopes of each variable and creates a
	 * chain of scopes starting at the root node.
	 * 
	 * @param statements
	 *            List of Statements to be added
	 * @param linkers
	 * @param placeOnTree
	 *            Location of the node the scopes are to be added to
	 */
	private void addScopes(ArrayList<String> statements,
			ArrayList<String> linkers, StatementNode placeOnTree) {
		boolean moveToNextStep = true;
		int statementIndex = 0;
		int linkerIndex = 0;
		while (moveToNextStep) {
			moveToNextStep = false;
			String cur = statements.get(0);
			boolean found = false;
			for (int i = 0; i < cur.length(); i++) {
				if (isScope(cur.charAt(i))) {
					placeOnTree.center = new StatementNode(cur, "Scope");
					placeOnTree = placeOnTree.center;
					found = true;
					statements.remove(0);
					linkers.remove(0);
				}
			}
			if (found)
				moveToNextStep = true;
			statementIndex++;
		}

	}

	/**
	 * Easy method to check if a given character is an example of a scope
	 * defined in Constants.java
	 * 
	 * @param cur
	 * @return
	 */
	private boolean isScope(char cur) {
		for (int t = 0; t < Constants.scopes.length; t++) {
			if (Character.toString(cur).equals(Constants.scopes[t])) {
				return true;
			}
		}
		return false;

	}

	/**
	 * Determines if a section starting at index of the input string is an
	 * example of a function with a given world.
	 * 
	 * @param input
	 *            String being analyzed
	 * @param index
	 *            Location of the string that is being analyzed
	 * @param world
	 *            The world that contains all of the functions that will be
	 *            compared to
	 * @return Null if the section does not match a function, or the function
	 *         name if it does match with something in the world.
	 */
	private String isFunctionInWorld(String input, int index, Universe uni) {
		String[] functionNames = uni.getFunctionWithoutParams();
		for (int t = 0; t < functionNames.length; t++) {
			int size = functionNames[t].length();
			if (index + size < input.length()) {
				String lookingAt = input.substring(index, index + size);
				if (lookingAt.equals(functionNames[t])) {
					return lookingAt;
				}
			}
		}
		return null;
	}

	/**
	 * Searches through a string input to separate all the important parts of
	 * the statement. The algorithm divides it into statements, such as the
	 * functions of the input and the scopes for each of the variables, and the
	 * linkers, which contains such things as the mathematical operators and
	 * Parenthesis(es)
	 * 
	 * 
	 * @param input
	 *            Statement to be taken apart
	 * @param statements
	 *            Empty ArrayList where data will be written in
	 * @param linkers
	 *            Empty ArrayList where data will be written in
	 * @param world
	 *            The world the input statement is being compared with
	 */
	private void getStatementsAndLinkers(String input,
			ArrayList<String> statements, ArrayList<String> linkers, Universe uni) {
		for (int i = 0; i < input.length(); i++) {
			char cur = input.charAt(i);
			if (cur == '(') {
				statements.add("");
				linkers.add("(");
			} else {
				if (cur == ')') {
					statements.add("");
					linkers.add(")");
				} else {
					if (isScope(cur)) {
						statements.add(input.substring(i, i + 2));
						linkers.add("(");
					} else {

						if (isFunctionInWorld(input, i, uni) != null) {
							int closeBracketIndex = i;
							while (input.charAt(closeBracketIndex) != ']')
								closeBracketIndex++;
							statements.add(input.substring(i,
									closeBracketIndex + 1));
							linkers.add("F");
							i = closeBracketIndex;
						} else {

							for (int t = 0; t < Constants.seperators.length; t++) {
								if (Character.toString(cur).equals(
										Constants.seperators[t])) {
									linkers.add(Character.toString(cur));
									statements.add("S");
								}
							}
						}
					}
				}
			}
		}
	}

	/**
	 * Takes in a statement and converts it to a different format so that the
	 * program can read and analyze it in a more efficient and readable manner.
	 *  @param world
	 *            World that the input is being compared with
	 * @param input
	 *            String that is going to be translated to the correct format
	 * @return
	 */
	private String translateToGoodFormat(Universe uni, String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();
		getStatementsAndLinkers(input, statements, linkers, uni);

		ArrayList<String> modifiers = new ArrayList<String>();
		String rtn = statements.remove(statements.size() - 1);
		for (int i = statements.size() - 1; i >= 0; i--) {
			if (linkers.get(i).equals("(")) {
				rtn = rtn + ")";
				rtn = "(" + rtn;
			} else {
				rtn = linkers.get(i) + rtn;
			}
			boolean scopeFound = false;
			for (int t = 0; t < Constants.scopes.length; t++) {
				if (statements.get(i).indexOf(Constants.scopes[t]) != -1) {
					modifiers.add(statements.get(i));
					scopeFound = true;
				}
			}
			if (!scopeFound) {
				rtn = statements.get(i) + rtn;
			}
		}
		int removeExtraParens = 0;
		int index = 1;
		while (rtn.charAt(index) == '(') {
			removeExtraParens++;
			index++;
		}
		rtn = rtn.substring(removeExtraParens);
		rtn = rtn.substring(0, rtn.length() - removeExtraParens - 1);

		for (int i = 0; i < modifiers.size(); i++) {
			rtn = modifiers.get(i) + rtn;
		}
		return rtn;
	}

	/**
	 * Determines if a given character is a separator defined in Constants.java
	 * 
	 * @param input
	 *            Character to be compared
	 * @return if the parameter is a separator or not.
	 */
	public static boolean isSeperator(char input) {
		for (int t = 0; t < Constants.seperators.length; t++) {
			if (Character.toString(input).equals(Constants.seperators[t])) {
				return true;
			}
		}
		return false;

	}
	
	/**
	 * Recursively creates a string representation of a tree starting at a given
	 * Node.
	 * 
	 * @param node
	 *            Node to start at
	 * @param depth
	 *            How far deep the recursion has gone
	 * @return String representation of the subtree
	 */
	public String toString(StatementNode node, int depthFromRoot, int lrc) {
		String rtn = "";
		return "";
	}

	/**
	 * Creates a simple string representation of the tree
	 */
	public String toString() {
		int width = this.getWidth(root, 0);
		String rtn = root.toString();
		rtn = rtn + root.buildBar();
		return rtn;
	}

	public int getWidth(StatementNode start, int depth) {
		depth = depth + 1;
		if (start.center != null) {
			return getWidth(start.center, depth);
		}
		if (start.right != null) {
			return getWidth(start.right, depth);
		}
		return depth + 1;
	}
	
	public boolean evaluate() {
		World curWorld = new World();
		return this.root.evaluate(curWorld);
	}

	/**
	 * For debugging
	 */
	public static void main(String[] args) {
		Universe uni = new Universe();
		StatementTree tree = new StatementTree(uni);
		tree.buildTree(uni, "∀x  (((K[x])" + Constants.OR + "(P[x]))" + Constants.AND + "((" + Constants.NOT + ")(Q[x])" + Constants.AND + "((T[x])" + Constants.AND + "(C[x])))");
		System.out.println("EVALED: " + tree.evaluate());
	}
}

