import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;

/**
 * Class that is used to determine if a given statement is consistent with the
 * rules of a given world. This is done by creating a tree structure of
 * StatementNodes and evaluating them based on the sequence of the nodes.
 * 
 * @author Daniel Bickley
 * 
 */
public class StatementTree {
	public Domain curDomain;
	
	public static HashMap<String, String> vars;
	public static ArrayList<Function> functs;
	
	public StatementNode root;
	

	/**
	 * Creates the root of a tree, from which everything will be built on.
	 */
	public StatementTree(Domain dom) {
		this.curDomain = dom;
		root = new StatementNode("root", "root",this);
		vars = new HashMap<String, String>();
		functs = dom.getFunctions();
	}

	public static Function getFunctionFromList(Function fun) {
		for (int i = 0; i < functs.size(); i++) {
			if (fun.functionName.equals(functs.get(i).getFunctionName()))
				return functs.get(i);
		}
		return null;
	}

	/**
	 * Actually builds the tree before evaluation.
	 * 
	 * @param world
	 *            The world the statement will be evaluated with
	 * @param input
	 *            The statement that will be evaluated
	 */
	public void buildTree(String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();
		//translateToGoodFormat(uni, input);
		//getStatementsAndLinkers(input, statements, linkers, curDomain2);
		StatementNode placeOnTree = root;
        placeOnTree.center = new StatementNode(this);

		// This section adds the "For All" and "There Exists" to the tree
		// Then moves it down to the actual meat of the tree
		/*addScopes(statements, linkers, placeOnTree);
		while (placeOnTree.center != null) {
			placeOnTree = placeOnTree.center;
		}

		input = input.substring(input.indexOf(" ") + 1);

		// Recursively builds the tree
		placeOnTree.center = new StatementNode(); */
		placeOnTree = placeOnTree.center;
		betterBuildTree(input, placeOnTree);
	}
	
	private void betterBuildTree(String input, StatementNode placeOnTree) {
		input = input.trim();
		if(input.charAt(0)==Constants.NOT && (input.charAt(1)==Constants.FOR_ALL || input.charAt(1) == Constants.THERE_EXISTS)) {
			placeOnTree.name = Character.toString(Constants.NOT);
			placeOnTree.center = new StatementNode(this);
			placeOnTree = placeOnTree.center;
			placeOnTree.name = input.substring(1, 3);
			placeOnTree.kindOfNode = "Scope";
			placeOnTree.center = new StatementNode(this);
			betterBuildTree(input.substring(3),placeOnTree.center);
			return;
		}
		
		if(input.charAt(0) == Constants.FOR_ALL || input.charAt(0) == Constants.THERE_EXISTS) {
		   placeOnTree.name = input.substring(0,2);
		   placeOnTree.kindOfNode = "Scope";
		   placeOnTree.center = new StatementNode(this);
		   betterBuildTree(input.substring(2),placeOnTree.center);
		   return;
		}
		
		while(input.charAt(0) == '(' && input.charAt(input.length()-1) == ')') {
			input = input.substring(1,input.length()-1);
		}
		
		int highPrecendence = 0;
		int lowPrecedence = 0;
		
		int numParens = 0;
		for(int position = 0; position < input.length(); position++) {
			if(input.charAt(position) == '(') {
				numParens++;
			}
			if(input.charAt(position) == ')') {
				numParens--;
			}
			if(numParens!=0)
				continue;
			
			if(input.charAt(position) == Constants.AND || input.charAt(position) == Constants.OR) {
				highPrecendence++;
				placeOnTree.name = Character.toString(input.charAt(position));
				String leftPart = input.substring(0,position);
				placeOnTree.left = new StatementNode(this);
				betterBuildTree(leftPart,placeOnTree.left);
				String rightPart = input.substring(position+1);
				placeOnTree.right = new StatementNode(this);
				betterBuildTree(rightPart,placeOnTree.right);
				return;
			}
			
			
		}
			/*
			if(input.charAt(position) == Constants.NOT) {
				placeOnTree.name = Character.toString(Constants.NOT);
				placeOnTree.center = new StatementNode();
				betterBuildTree(input.substring(position+1),placeOnTree.center);
				return;
			}
			
			if(Constants.binary.indexOf(input.charAt(position)) != -1) {
				placeOnTree.name = Character.toString(input.charAt(position));
				String leftPart = input.substring(0,position);
				placeOnTree.left = new StatementNode();
			    betterBuildTree(leftPart, placeOnTree.left);
			    String rightPart = input.substring(position+1);
			    placeOnTree.right = new StatementNode();
			    betterBuildTree(rightPart, placeOnTree.right);
			    return;*/
			
		if(highPrecendence == 0) {
			for(int position = 0; position < input.length(); position++) {
				if(input.charAt(position) == '(') {
					numParens++;
				}
				if(input.charAt(position) == ')') {
					numParens--;
				}
				if(numParens!=0)
					continue;
				
				if(input.charAt(position) == Constants.NOT) {
					placeOnTree.name = Character.toString(Constants.NOT);
					placeOnTree.center = new StatementNode(this);
					betterBuildTree(input.substring(position+1),placeOnTree.center);
					return;
				}
				
				if(input.charAt(position) == Constants.IMPLIES || input.charAt(position) == Constants.IFF) {
					highPrecendence++;
					placeOnTree.name = Character.toString(input.charAt(position));
					String leftPart = input.substring(0,position);
					placeOnTree.left = new StatementNode(this);
					betterBuildTree(leftPart,placeOnTree.left);
					String rightPart = input.substring(position+1);
					placeOnTree.right = new StatementNode(this);
					betterBuildTree(leftPart,placeOnTree.right);
					return;
				}
				
				
			}
			
		}
		
		input = input.trim();
		String functName = input.substring(0,input.indexOf("[")+1);
		placeOnTree.name = input;
		placeOnTree.function = this.curDomain.getFunction(functName);
		return;
		
	}

	/**
	 * Recursively builds the tree, one node at a time. Has special instructions
	 * on how to insert a "not" node, as they work slightly differently.
	 * 
	 * @param input
	 * @param placeOnTree
	 */
	private void buildTree(String input, StatementNode placeOnTree) {
		input = input.trim();
		
		if(input.charAt(0) == Constants.THERE_EXISTS) {
			placeOnTree.name = input.substring(0,2);
			placeOnTree.kindOfNode = "Scope";
			placeOnTree.center = new StatementNode(this);
			
			input = input.substring(2);
			buildTree(input,placeOnTree.center);
			return;
		}
		
		if(input.charAt(0) == Constants.FOR_ALL) {
			placeOnTree.name = input.substring(0,2);
			placeOnTree.kindOfNode = "Scope";
			placeOnTree.center = new StatementNode(this);
			input = input.substring(2);
			buildTree(input,placeOnTree.center);
			return;
		}

		// When parenthisized correctly, the "NOT" will have an index of 2 when
		// it needs to be added.
		if (input.charAt(0) == Constants.NOT) {

			input = input.substring(1);

			// Input has everything after the "not" from the input in the
			// correct syntax
			placeOnTree.name = Character.toString(Constants.NOT);
			placeOnTree.center = new StatementNode(this);
			buildTree(input, placeOnTree.center);
			return;

		}

		if (input.charAt(0) == '(')
			input = input.substring(1);
		if (input.charAt(input.length() - 1) == ')')
			input = input.substring(0, input.length() - 1);

		int startString = 1;
		int numOpenParens = 1;
		while ((startString < input.length() && !isSeperator(input
				.charAt(startString)))
				|| (startString < input.length() && numOpenParens != 0)) {
			if (input.charAt(startString) == '(')
				numOpenParens++;
			if (input.charAt(startString) == ')')
				numOpenParens--;
			startString++;
		}

		// If startString is at the end of the string, then it only consists of
		// a function
		if (startString >= input.length() - 1) {
			// System.outprintln(input);
			placeOnTree.function = StatementTree.getFunctionFromList(curDomain
					.getFunction(input.substring(0, input.indexOf("]") + 1)));
			placeOnTree.name = placeOnTree.function.getFunctionName();
			return;
		}

		// Build the left and right of the tree.
		placeOnTree.name = Character.toString(input.charAt(startString));

		String left = input.substring(0, startString);
		if (left.length() != 0) {
			placeOnTree.left = new StatementNode(this);
			buildTree(left, placeOnTree.left);
		}
		String right = input.substring(startString + 1, input.length());
		if (right.length() != 0) {
			placeOnTree.right = new StatementNode(this);
			buildTree(right, placeOnTree.right);
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
		while (moveToNextStep) {
			moveToNextStep = false;
			String cur = statements.get(0);
			boolean found = false;
			for (int i = 0; i < cur.length(); i++) {
				if (isScope(cur.charAt(i))) {
					placeOnTree.center = new StatementNode(cur, "Scope",null);
					placeOnTree = placeOnTree.center;
					found = true;
					statements.remove(0);
					linkers.remove(0);
				}
			}
			if (found)
				moveToNextStep = true;
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
		String[] functionNames = curDomain.getFunctionWithoutParams();
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
			ArrayList<String> statements, ArrayList<String> linkers,
			Universe uni) {
		boolean endOfScope = false;
		for (int i = 0; i < input.length(); i++) {
			char cur = input.charAt(i);
			switch (cur) {
			case ('('):
				statements.add("");
				linkers.add("(");
				break;
			case (')'):
				statements.add("");
				linkers.add(")");
				break;
			case(' '):
				break;
			default:
				if (isScope(cur) && !endOfScope) {
					if (cur == Constants.NOT) {
						statements.add(input.substring(i, i + 1));
					} else {
						statements.add(input.substring(i, i + 2));
						i++;
					}
					linkers.add("(");
				} else {
					endOfScope = true;
					if (isFunctionInWorld(input, i, uni) != null) {
						int closeBracketIndex = i;
						while (input.charAt(closeBracketIndex) != ']')
							closeBracketIndex++;
						Function funct = curDomain.getFunction(input.substring(i,
								closeBracketIndex + 1));
						if (!functs.contains(funct)) {
							String relevantInfo = input.substring(i);
							funct.setParameters(relevantInfo.substring(
									relevantInfo.indexOf("[") + 1,
									closeBracketIndex - i));
							functs.add(funct);
						}
						statements.add(input
								.substring(i, closeBracketIndex + 1));

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
		return "";
	}

	/**
	 * Creates a simple string representation of the tree
	 */
	public String toString() {
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

	public static String evaluate(StatementTree... statements) {
		int numEval = 0;
		for(int i = 0; i < statements.length-1; i++) {
			if(!statements[i].curDomain.equals(statements[i+1].curDomain))
				System.err.println("YOU DUN GOOFED");
		}
		
		ArgumentList[] args = new ArgumentList[functs.size()];
		for (int i = 0; i < functs.size(); i++) {
			ArrayList<ArrayList<String>> allParams = new ArrayList<ArrayList<String>>();
			for (int t = 0; t < functs.get(i).getNumParams(); t++) {
				ArrayList<String> lst = ArgumentList.createSet(statements[0].curDomain
						.getConstantNames());
				allParams.add(lst);
			}
			ArrayList<String>[] parameters = new ArrayList[allParams.size()];
			for (int q = 0; q < parameters.length; q++) {
				parameters[q] = allParams.get(q);
			}
			args[i] = new ArgumentList(parameters);
		}
		World world = new World(args);

		for (HashSet<String>[] items : world) {
			//System.out.print("TESTING WHEN");
			//for (HashSet<String> set : items) {
		//		System.out.print(set);
			//}
			numEval++;
			 //System.out.println(" IS TRUE ");
			 //System.out.println(numEval);
			 boolean same = statements[0].root.evaluate(items);
		     boolean second = statements[1].root.evaluate(items);
		    // System.out.println(numEval);
		     if(same != second) {
		    	 System.err.println(numEval);
		    	 System.exit(0);
		    	 String badWorld = "";
		    	 for (HashSet<String> set : items) {
		    		   for(String part : set) {
		    			   badWorld = badWorld + part;
		    		   }
					}
		    	 return "Your test failed when [" + badWorld + "] is true.";
		     }
			//	System.out.println("First Statement: "
			//			+ statements[0].root.evaluate(items) + " ");

			//	System.out.println("True Statement Statement: "
					//	+ statements[1].root.evaluate(items) + " ");
				//(statements[i].root.evaluate(items))
				
			//}
			//System.out.println();

		}
		return "All tests passed. The statements are equivalent.";
	}

	@SuppressWarnings("unchecked")
	public void evaluate() {
		System.out.println("asdfasdfasdf");

	}

	/**
	 * For debugging
	 */
	public static void main(String[] args) {
		/*Domain dom1 = new Domain();
		StatementTree tree = new StatementTree(curDomain);
		StatementTree tree2 = new StatementTree(curDomain);
		System.out.println(Constants.FOR_ALL);
		tree.buildTree(curDomain, "∀x P[x,HOMER]");
		tree2.buildTree(curDomain,"∀x "+Constants.NOT+"(P[x,HOMER])");
		//tree2.buildTree(uni, "∀x  ((" + Constants.NOT + ")P[x])");
		// tree.buildTree(uni, "∀x  (((K[x])" + Constants.OR + "(P[x]))" +
		// Constants.AND + "((" + Constants.NOT + ")(Q[x])" + Constants.AND +
		// "((T[x])" + Constants.AND + "(C[x])))");
		StatementTree.evaluate(tree,tree2);*/
	}
}
