import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * A node that is used to create a tree for the statement to be evaluated. Each
 * node contains the name of the node, a string that stores the kind of the
 * node, as well as three different StatementNodes. The three StatementNode
 * instance variables are used to create the tree. The StatementNode "center" is
 * used for scopes and single operand operators such as "not". The "left" and
 * "right" StatementNodes are used for multi-operand operators. The idea is that
 * the tree will be read from left to right. Often, the functions will be
 * located on the "left" StatementNode, with the operators generally found on
 * the right.
 * 
 * @author Daniel Bickley
 * 
 */
public class StatementNode {
	protected StatementTree tree;
	protected String name;
	protected String kindOfNode;
	protected int numberItems = 0;
	protected StatementNode left;
	protected StatementNode right;
	protected StatementNode center;
	protected Function function;

	/**
	 * Name and kind of Node constructor, fairly self explanitory.
	 * 
	 * @param name
	 * @param kindOfNode
	 */
	public StatementNode(String name, String kindOfNode,StatementTree tree) {
		this.name = name;
		this.kindOfNode = kindOfNode;
		this.tree = tree;
	}

	/**
	 * Function constructor that is used to create a node that actually
	 * represents a function.
	 * 
	 * @param funct
	 */
	public StatementNode(Function funct, StatementTree contained) {
		this.name = funct.getFunctionName();
		this.kindOfNode = "function";
		this.tree = contained;
	}

	public StatementNode(StatementTree contained) {
		this.name = "";
		this.kindOfNode = "";
		this.tree = contained;
	}

	/**
	 * Automatically adds a node based on the kind of node. If the node is a
	 * scope, it is automatically added to the center. Then, the node is added
	 * to right, and then if there is something that is already at right, the
	 * node is added to left.
	 * 
	 * @param add
	 *            StatementNode that is going to be added into the tree.
	 */
	public void addNode(StatementNode add) {
		if (add.kindOfNode.equals("Scope")) {
			center = add;
			return;
		}
		if (right == null) {
			right = add;
		} else {
			left = add;
		}
	}

	protected String buildBar() {
		String bar = "|";
		for (int i = 1; i < Constants.INDENT_SIZE; i++) {
			bar = bar + "-";
		}
		bar = bar + "|";
		for (int i = 1; i < Constants.INDENT_SIZE; i++) {
			bar = bar + "-";
		}
		bar = bar + "|";
		return bar;
	}

	public String toString() {
		String rtn = String.format("%" + Constants.INDENT_SIZE + "s", "");
		rtn = rtn + this.name
				+ String.format("%" + Constants.INDENT_SIZE + "s\n", "");
		return rtn;
	}

	/**
	 * \ Evaluates the tree according to the worled specified by the parameter.
	 * 
	 * @param items
	 *            World that we are using to evaluate it
	 * @return
	 */
	public boolean evaluate(HashSet<String>[] items) {
		if (this.kindOfNode.equals("root")) {
			return this.center.evaluate(items);

		}
		if (this.kindOfNode.equals("Scope")) { // If the node is "For All" or
												// "There Exists"
			if (this.name.indexOf(Constants.FOR_ALL) != -1) {
				String[] constants = tree.curDomain.getConstantNames();
				for (int i = 0; i < constants.length; i++) {
					// Adds the current constant to the list of constants for
					// parameters
					StatementTree.vars
							.put(this.name.substring(1), constants[i]);
					if (this.center.evaluate(items) == false) {
						StatementTree.vars.remove(constants[i]);
						return false;
					}
					// Removes the current constant to prepare for the next one
					StatementTree.vars.remove(constants[i]);

				}
				return true;
			} else if (this.name.indexOf(Constants.THERE_EXISTS) != -1) {
				String[] constants = tree.curDomain.getConstantNames();
				for (int i = 0; i < constants.length; i++) {
					// Adds the current constant to the list of constants for
					// parameters
					StatementTree.vars
							.put(this.name.substring(1), constants[i]);
					if (this.center.evaluate(items) == true) {
						StatementTree.vars.remove(constants[i]);
						return true;
					}
					// Removes the current constant to prepare for the next one
					StatementTree.vars.remove(constants[i]);

				}
				return false;
			}

		}

		if (this.function != null) { // If the node has a function specified
			int paramIndex = StatementTree.functs.indexOf(this.function);
			if (paramIndex == -1)
				return false;

			ArrayList<String> userParams = new ArrayList<String>();
			int index = 0;
			HashSet<String> curParams = items[paramIndex];
			Iterator it = curParams.iterator();
			// System.out.println("TRUE PARAMS FOR " +
			// StatementTree.functs.get(paramIndex).getFunctionName());

			ArrayList<String> trueParams = new ArrayList<String>();
			while (it.hasNext()) {
				trueParams.add((String) it.next());
			}

			
			// params = params + ";";
			/*
			 * while(params.indexOf(";")!= -1) {
			 * if(!params.substring(0,params.indexOf(";")).equals("null")) {
			 * userParams.add(params.substring(0, params.indexOf(";"))); }
			 * params = params.substring(params.indexOf(";") +1 );
			 * 
			 * }
			 */

			int ithParam = 0;
			boolean cont = false;
			
			String functParams = this.name.substring(this.name.indexOf("[")+1, this.name.indexOf("]")) + ",";
			
			String compareString = "";
			while(functParams.length()!= 0) {
				String curParam = functParams.substring(0,functParams.indexOf(","));
				curParam = curParam.trim();
				if(!curParam.equals(curParam.toUpperCase())) {
					curParam = StatementTree.vars.get(curParam);
				}
				compareString = compareString.trim();
				compareString = compareString+curParam+";";
				functParams = functParams.substring(functParams.indexOf(",")+1);
			}
			
			for(int i = 0; i <trueParams.size(); i++) {
				String correctOutput = trueParams.get(i)+";";
				if(compareString.equals(correctOutput))
					return true;
			}
			return false;
	
		}

		if (this.name.charAt(0) == Constants.AND) {
			boolean left = this.left.evaluate(items);
			boolean right = this.right.evaluate(items);
			if (left && right) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0) == Constants.OR) {
			boolean left = this.left.evaluate(items);
			boolean right = this.right.evaluate(items);
			if (left || right) {
				return true;
			}
			return false;
		}

		if (this.name.charAt(0) == Constants.IFF) {
			boolean left = this.left.evaluate(items);
			boolean right = this.right.evaluate(items);
			if ((left && right) || (!left && !right)) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0) == Constants.IMPLIES) {
			boolean left = this.left.evaluate(items);
			boolean right = this.right.evaluate(items);
			if (!left || (left && right)) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0) == (Constants.NOT)) {
			return !this.center.evaluate(items);
		}

		return false;
	}

	private String parseParams(String params) {
		int numCommas = 1;
		for (int i = 0; i < params.length(); i++) {
			if (params.charAt(i) == ',')
				numCommas++;
		}
		String rtn = StatementTree.vars.get(params.substring(0, (params
				.indexOf(',') < 0) ? params.length() : params.indexOf(',')));
		if (numCommas >= 2)
			rtn = rtn + ";";
		params = params.substring((params.indexOf(',') < 0) ? params.length()
				: params.indexOf(',') + 1);
		for (int t = 1; t < numCommas; t++) {
			rtn = rtn
					+ StatementTree.vars.get(params.substring(0,
							(params.indexOf(',') < 0) ? params.length()
									: params.indexOf(',')));
			if (t < numCommas - 1)
				rtn = rtn + ";";
			params = params.substring((params.indexOf(',') < 0) ? params
					.length() : params.indexOf(','));
		}
		return rtn;
	}

	public static void main(String[] args) {
		StatementTree.main(args);
	}

}
