import java.util.ArrayList;

/**
 * Class that is used to determine if a given statement is consistent with the
 * rules of a given world. This is done by creating a tree structure of
 * StatementNodes and evaluating them based on the sequence of the nodes.
 * 
 * @author Daniel Bickley
 * 
 */
public class StatementTree {
	StatementNode root;

	/**
	 * Creates the root of a tree, from which everything will be built on.
	 */
	public StatementTree() {
		root = new StatementNode("root", "root");
	}

	/**
	 * Actually builds the tree before evaluation.
	 * 
	 * @param world
	 *            The world the statement will be evaluated with
	 * @param input
	 *            The statement that will be evaluated
	 */
	public void buildTree(World world, String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();
		getStatementsAndLinkers(input, statements, linkers, world);
		StatementNode placeOnTree = root;
		StatementNode currentRung = root;
		addScopes(statements, linkers, placeOnTree);
		while (placeOnTree.center != null) {
			placeOnTree = placeOnTree.center;
			currentRung = placeOnTree;
		}
		buildTree(statements, linkers, placeOnTree, true);
	}

	/**
	 * Method designed to recursively create the tree, where the first object in
	 * the statement and linkers Arraylists is used to add onto the tree, after
	 * the scopes are first added by a helper method.
	 * 
	 * @param statements
	 *            List of Statements to be added
	 * @param linkers
	 *            List of linking statements to be added
	 * @param placeOnTree
	 *            The current parent StatementNode
	 * @param firstTime
	 *            If this is the first time the method is called
	 * @return Returns true of adding the node was successful.
	 */
	private boolean buildTree(ArrayList<String> statements,
			ArrayList<String> linkers, StatementNode placeOnTree,
			boolean firstTime) {
		if (statements.size() == 0 && linkers.size() == 0)
			return true;
		if(statements.size() == 1 && linkers.size() == 0) {
		 placeOnTree.right=new StatementNode(statements.get(0), "Function");
		 return true;
		}
		String link = linkers.get(0);
		if (isSeperator(link.charAt(0))) {
			if (firstTime) {
				placeOnTree.center = new StatementNode(link, "Seperator");
				placeOnTree.center.left = new StatementNode(statements.get(0),
						"Function");
				linkers.remove(0);
				statements.remove(0);
				return buildTree(statements, linkers, placeOnTree.center, false);
			} else {
				placeOnTree.right = new StatementNode(link, "Seperator");
				placeOnTree.right.left = new StatementNode(statements.get(0),
						"Function");
				linkers.remove(0);
				statements.remove(0);
				return buildTree(statements, linkers, placeOnTree.right, false);
			}
		}
		if (link.equals("(")) {
			int linkIndex = linkers.size() - 2;
			int statementIndex = statements.size() - 2;
			statements.remove(0);
			linkers.remove(0);
			while (linkers.get(linkIndex) != ")") {
				linkIndex--;
			}
			while (statements.get(statementIndex) != "") {
				statementIndex--;
			}
			ArrayList<String> recurseStatement = new ArrayList<String>();
			ArrayList<String> recurseLinkers = new ArrayList<String>();
			for (int i = 0; i < statementIndex; i++) {
				recurseStatement.add(statements.get(0));
				statements.remove(0);
			}
			for (int i = 0; i < linkIndex; i++) {
				recurseLinkers.add(linkers.get(0));
				linkers.remove(0);
			}
			statements.remove(0);
			linkers.remove(0);
			StatementNode keep = placeOnTree;
		    buildTree(statements, linkers, placeOnTree, false);
			return buildTree(recurseStatement, recurseLinkers, keep, true);
			
		}

		if (link.equals(Constants.NOT)) {
			placeOnTree.center = new StatementNode(
					Character.toString(Constants.NOT), "Linker");
			statements.remove(0);
			linkers.remove(0);
			return buildTree(statements, linkers, placeOnTree.center, false);

		}

		return true;
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
	private String isFunctionInWorld(String input, int index, World world) {
		String[] functionNames = world.getFunctionWithoutParams();
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
			ArrayList<String> statements, ArrayList<String> linkers, World world) {
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

						if (isFunctionInWorld(input, i, world) != null) {
							int closeBracketIndex = i;
							while (input.charAt(closeBracketIndex) != ']')
								closeBracketIndex++;
							statements.add(input.substring(i,
									closeBracketIndex + 1));
							i = closeBracketIndex;
						} else {

							for (int t = 0; t < Constants.seperators.length; t++) {
								if (Character.toString(cur).equals(
										Constants.seperators[t])) {
									linkers.add(Character.toString(cur));
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
	 * 
	 * @param world
	 *            World that the input is being compared with
	 * @param input
	 *            String that is going to be translated to the correct format
	 * @return
	 */
	private String translateToGoodFormat(World world, String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();
		getStatementsAndLinkers(input, statements, linkers, world);

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
	private static boolean isSeperator(char input) {
		for (int t = 0; t < Constants.seperators.length; t++) {
			if (Character.toString(input).equals(Constants.seperators[t])) {
				return true;
			}
		}
		return false;

	}

	// This section contains archaic code that I'm keeping around just in case.
	// Ignore it.
	/**
	 * 
	 * @param input
	 * @return
	 * 
	 *         public static String[] getSeperators(String input) { int
	 *         numSeperators = 0; for (int i = 0; i < input.length(); i++) { if
	 *         (isSeperator(input.charAt(i))) numSeperators++; } String[] rtn =
	 *         new String[numSeperators]; int index = 0; for (int i = 0; i <
	 *         input.length(); i++) { if (isSeperator(input.charAt(i))) {
	 *         rtn[index] = Constants.seperators[i]; rtn[index].trim(); index++;
	 *         } } return rtn; }
	 * 
	 *         /**
	 * 
	 * @param input
	 * @return
	 * 
	 *         private String[] getSections(String input) { String[] rtn = new
	 *         String[getNumParts(input)]; int index = 0; int
	 *         numOfNonClosedParen = 0; int firstOpenParen = -1; for (int i = 0;
	 *         i < input.length(); i++) { if (input.charAt(i) == '(') { if
	 *         (numOfNonClosedParen == 1) firstOpenParen = i;
	 *         numOfNonClosedParen++; } if (input.charAt(i) == ')') {
	 *         numOfNonClosedParen--; }
	 * 
	 *         if (numOfNonClosedParen == 0) { for (int t = 0; t <
	 *         Constants.seperators.length; t++) { if
	 *         (Character.toString(input.charAt(i)).equals(
	 *         Constants.seperators[t])) { rtn[index] =
	 *         input.substring(firstOpenParen + 1, i); rtn[index] =
	 *         rtn[index].trim(); firstOpenParen = i + 1; index++; }
	 * 
	 *         } } } rtn[rtn.length - 1] = input.substring(firstOpenParen);
	 *         rtn[rtn.length - 1] = rtn[rtn.length - 1].trim(); return rtn; }
	 * 
	 *         /**
	 * 
	 * @param input
	 * @return
	 * 
	 *         private int getNumParts(String input) { int numOfNonClosedParen =
	 *         0; int numParts = 0; for (int i = 0; i < input.length(); i++) {
	 *         if (input.charAt(i) == '(') { numOfNonClosedParen++; } if
	 *         (input.charAt(i) == ')') { numOfNonClosedParen--; }
	 * 
	 *         if (numOfNonClosedParen == 0) { for (int t = 0; t <
	 *         Constants.seperators.length; t++) { if (input.substring(i, i +
	 *         1).equals( Constants.seperators[t])) numParts++; } }
	 * 
	 *         } return numParts + 1; }
	 */

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

	private String getIndentation(int depth, char location) {
		String rtn = "";
		for (int i = 0; i < depth * Constants.INDENT_SIZE; i++) {
			rtn = rtn + "-";
		}
		if (location == 'l')
			rtn = rtn.substring(Constants.INDENT_SIZE / 2);
		if (location == 'r') {
			for (int i = 0; i < Constants.INDENT_SIZE / 2; i++)
				rtn = rtn + "-";
		}
		return rtn;

	}

	/**
	 * For debugging
	 */
	public static void main(String[] args) {
		World world = new World();
		StatementTree tree = new StatementTree();
		tree.buildTree(world, "∀x P[x] ⇔ (P[x] ⇔ F[x]) ⇔ a [x]");
		System.out.println(tree);
	}
}
