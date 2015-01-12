import java.util.ArrayList;

public class StatementTree {
	StatementNode root;

	public StatementTree() {
		root = new StatementNode("root", "root");
	}

	public void buildTree(String input) {
		System.out.println(input);
		String variableSection = input.substring(0, input.indexOf("("));
		StatementNode endScope = addScopes(variableSection);
	}

	public StatementNode addScopes(String vars) {
		StatementNode curAdd = root;
		for (int i = 1; i < vars.length(); i++) {
			for (int t = 0; t < Constants.scopes.length; t++) {
				if (vars.substring(i, i + 1).equals(Constants.scopes[t])) {
					String var = vars.substring(0, i);
					vars = vars.substring(i);
					i = 1;
					StatementNode newNode = new StatementNode(var, "Scope");
					curAdd.addNode(newNode);
					curAdd = newNode;
				}
			}
		}
		curAdd.addNode(new StatementNode(vars, "Scope"));
		return curAdd;
	}

	public String toString() {
		String rtn = root.name;
		int depth = 1;
		if (root.center != null) {
			rtn = rtn + "\n" + toString(root.center, depth);
		}
		return rtn;
	}

	private String removeSpaces(String input) {
		String cpy = input;
		for (int i = 0; i < cpy.length(); i++) {
			if (cpy.charAt(i) == ' ') {
				String firstHalf = cpy.substring(0, i);
				String secondHalf = cpy.substring(i + 1);
				cpy = firstHalf + secondHalf;
			}
		}
		return cpy;

	}

	private boolean isScope(char cur) {
		for (int t = 0; t < Constants.scopes.length; t++) {
			if (Character.toString(cur).equals(Constants.scopes[t])) {
				return true;
			}
		}
		return false;

	}

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

	private static boolean isSeperator(char input) {
		for (int t = 0; t < Constants.seperators.length; t++) {
			if (Character.toString(input).equals(Constants.seperators[t])) {
				return true;
			}
		}
		return false;

	}

	public static String[] getSeperators(String input) {
		int numSeperators = 0;
		for (int i = 0; i < input.length(); i++) {
			if (isSeperator(input.charAt(i)))
				numSeperators++;
		}
		String[] rtn = new String[numSeperators];
		int index = 0;
		for (int i = 0; i < input.length(); i++) {
			if (isSeperator(input.charAt(i))) {
				rtn[index] = Constants.seperators[i];
				rtn[index].trim();
				index++;
			}
		}
		return rtn;
	}

	private String[] getSections(String input) {
		String[] rtn = new String[getNumParts(input)];
		int index = 0;
		int numOfNonClosedParen = 0;
		int firstOpenParen = -1;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				if (numOfNonClosedParen == 1)
					firstOpenParen = i;
				numOfNonClosedParen++;
			}
			if (input.charAt(i) == ')') {
				numOfNonClosedParen--;
			}

			if (numOfNonClosedParen == 0) {
				for (int t = 0; t < Constants.seperators.length; t++) {
					if (Character.toString(input.charAt(i)).equals(
							Constants.seperators[t])) {
						rtn[index] = input.substring(firstOpenParen + 1, i);
						rtn[index] = rtn[index].trim();
						firstOpenParen = i + 1;
						index++;
					}

				}
			}
		}
		rtn[rtn.length - 1] = input.substring(firstOpenParen);
		rtn[rtn.length - 1] = rtn[rtn.length - 1].trim();
		return rtn;
	}

	private int getNumParts(String input) {
		int numOfNonClosedParen = 0;
		int numParts = 0;
		for (int i = 0; i < input.length(); i++) {
			if (input.charAt(i) == '(') {
				numOfNonClosedParen++;
			}
			if (input.charAt(i) == ')') {
				numOfNonClosedParen--;
			}

			if (numOfNonClosedParen == 0) {
				for (int t = 0; t < Constants.seperators.length; t++) {
					if (input.substring(i, i + 1).equals(
							Constants.seperators[t]))
						numParts++;
				}
			}

		}
		return numParts + 1;
	}

	public String toString(StatementNode node, int depth) {
		String rtn = "";
		for (int i = 0; i < depth; i++) {
			rtn = rtn + "--";
		}
		rtn = rtn + node.name + "\n";
		if (node.center != null) {
			rtn = rtn + toString(node.center, depth + 1);
		}
		if (node.left != null) {
			rtn = rtn + toString(node.left, depth + 1);
		}
		if (node.right != null) {
			rtn = rtn + toString(node.right, depth + 1);
		}
		return rtn;
	}

	public static void main(String[] args) {
		World world = new World();
		StatementTree tree = new StatementTree();
		String format = tree.translateToGoodFormat(world,
				"∀x P[x] ⇔ (P[x] ⇔ F[x]) ⇔ a [x]");
		System.out.println(format);
	}
}
