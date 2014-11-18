import java.io.File;
import java.util.ArrayList;

public class LogicTest {
	XMLReader[] readers;
	TruthTree[] worlds;
	ArrayList<String> everyFunction;

	/**
	 * 
	 */
	public LogicTest() {
		everyFunction = new ArrayList<String>();
		File findFiles = new File("src/");
		String[] files = findFiles.list();
		int numWorlds = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].toLowerCase().indexOf("world") != -1) {
				numWorlds++;
			}
		}
		worlds = new TruthTree[numWorlds];
		int worldIndex = 0;
		for (int i = 0; i < files.length; i++) {
			if (files[i].indexOf(".xml") != -1
					&& files[i].indexOf("world") != -1) {
				worlds[worldIndex] = buildTree(files[i]);

			}
		}
	}

	public boolean evaluateStatement(String input) {
		String goodInput = translateToGoodFormat(input);
		System.out.println(removeSpaces(goodInput));
		return true;
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

	private String translateToGoodFormat(String input) {
		ArrayList<String> statements = new ArrayList<String>();
		ArrayList<String> linkers = new ArrayList<String>();

		for (int i = 0; i < input.length(); i++) {
			char cur = input.charAt(i);
			if (cur == '(') {
				statements.add("");
				linkers.add("(");
			}
			for (int t = 0; t < Constants.scopes.length; t++) {
				if (Character.toString(cur).equals(Constants.scopes[t])) {
					statements.add(input.substring(i, i + 2));
					linkers.add("(");
				}
			}
			for (int t = 0; t < this.everyFunction.size(); t++) {
				int size = everyFunction.get(t).length();
				if (i + size <= input.length()
						&& everyFunction.get(t).trim().length() != 0) {
					if (input.substring(i, i + size).indexOf(
							everyFunction.get(t)) != -1) {
						int closeBracketIndex = i;
						while (input.charAt(closeBracketIndex) != ']')
							closeBracketIndex++;
						statements.add(input
								.substring(i, closeBracketIndex + 1));
						i = closeBracketIndex;

					}
				}
			}
			for (int t = 0; t < Constants.seperators.length; t++) {
				if (Character.toString(cur).equals(Constants.seperators[t])) {
					linkers.add(Character.toString(cur));
				}
			}
		}

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
		while(rtn.charAt(index) == '(') {
			removeExtraParens++;
			index++;
		}
		rtn = rtn.substring(removeExtraParens);
		rtn = rtn.substring(0, rtn.length()-removeExtraParens-1);

		for (int i = 0; i < modifiers.size(); i++) {
			rtn = modifiers.get(i) + rtn;
		}
		return rtn;
	}

	private String[] getSeperators(String input) {
		int numSeperators = 0;
		for (int i = 0; i < input.length(); i++) {
			for (int t = 0; t < Constants.seperators.length; t++) {
				if (Character.toString(input.charAt(i)).equals(
						Constants.seperators[t])) {
					numSeperators++;
				}
			}
		}
		String[] rtn = new String[numSeperators];
		int index = 0;
		for (int i = 0; i < input.length(); i++) {
			for (int t = 0; t < Constants.seperators.length; t++) {
				if (Character.toString(input.charAt(i)).equals(
						Constants.seperators[t])) {
					rtn[index] = Constants.seperators[t];
					rtn[index].trim();
					index++;
				}
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

	public String toString() {
		String rtn = "";
		for (int i = 0; i < worlds.length; i++) {
			rtn = rtn + worlds[i].toString();
		}
		return rtn;
	}

	/**
	 * @param string
	 * @return
	 */
	private TruthTree buildTree(String string) {
		XMLReader reader = new XMLReader("src/" + string);
		TruthTree world = new TruthTree(string);
		String[] func = reader.toArr(reader.getFunctions(false), true);
		for (int i = 0; i < func.length; i++) {
			world.addFunction(func[i]);
			this.everyFunction.add(func[i]);
		}

		for (int i = 0; i < world.root.nodes.length; i++) {
			if (world.root.nodes[i] != null) {
				ArrayList<String> truths = reader
						.getTruth(world.root.nodes[i].name);
				for (int t = 0; t < truths.size(); t++) {
					world.addTruth(world.root.nodes[i], truths.get(t));
				}
				truths.size();
			} else
				return world;

		}

		return world;
	}
}
