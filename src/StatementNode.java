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
	public StatementNode(String name, String kindOfNode) {
		this.name = name;
		this.kindOfNode = kindOfNode;
	}

	/**
	 * Function constructor that is used to create a node that actually
	 * represents a function.
	 * 
	 * @param funct
	 */
	public StatementNode(Function funct) {
		this.name = funct.getFunctionName();
		this.kindOfNode = "function";
	}

	public StatementNode() {
		this.name = "";
		this.kindOfNode = "";
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

	public boolean evaluate(World curWorld) {
		if(this.kindOfNode.equals("Scope")|| this.kindOfNode.equals("root")) {
			return this.center.evaluate(curWorld);
		}
	
		if (this.function != null) {
			//System.out.println("Asdf");
			return true;
		}

		System.out.println(this.name.equals(Constants.IMPLIES));
		if (this.name.charAt(0) == Constants.AND) {
			boolean left = this.left.evaluate(curWorld);
			boolean right = this.right.evaluate(curWorld);
			if (left && right) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0) == Constants.OR) {
			boolean left = this.left.evaluate(curWorld);
			boolean right = this.right.evaluate(curWorld);
			if (left || right) {
				return true;
			}
			return false;
		}

		if (this.name.charAt(0) == Constants.IFF) {
			boolean left = this.left.evaluate(curWorld);
			boolean right = this.right.evaluate(curWorld);
			if ((left && right) || (!left && !right)) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0)== Constants.IMPLIES){
			boolean left = this.left.evaluate(curWorld);
			boolean right = this.right.evaluate(curWorld);
			if (!left || (left && right)) {
				return true;
			}
			return false;

		}

		if (this.name.charAt(0) == (Constants.NOT)) {
			return !this.center.evaluate(curWorld);
		}

		return false;
	}

	public static void main(String[] args) {
		StatementTree.main(args);
		//StatementNode test = new StatementNode("Test", "test");
		//System.out.println(test);
	}

}
