
public class StatementTree {
    StatementNode root;
    
    public StatementTree() {
    	root = new StatementNode("root","root");
    }
    
    public void buildTree(String input) {
    	System.out.println(input);
    	String variableSection = input.substring(0, input.indexOf("("));
    	StatementNode endScope = addScopes(variableSection);
    	addSeperators(input);
    }
    
    public StatementNode addScopes(String vars) {
    	StatementNode curAdd = root;
    	for(int i = 1; i < vars.length(); i++) {
    		for(int t = 0; t < Constants.scopes.length; t++) {
    			if(vars.substring(i, i+1).equals(Constants.scopes[t])) {
    				String var = vars.substring(0, i);
    				vars = vars.substring(i);
    				i = 1;
    				StatementNode newNode = new StatementNode(var,"Scope");
    				curAdd.addNode(newNode);
    				curAdd = newNode;
    			}
    		}
    	}
    	curAdd.addNode(new StatementNode(vars,"Scope"));
    	return curAdd;
    }
    
    public void addSeperators (String input) {
    	StatementNode findNode = root;
     	String[] inputs = LogicTest.getSeperators(input);
    	while(findNode.center != null) 
    		findNode = findNode.center; 
    	StatementNode middleNode = new StatementNode(inputs[inputs.length/2], "Seperator");
    	findNode.center = middleNode;
    	int middle = inputs.length/2;
    	if(middle == 0)
    		return;
    	String[] left = new String[middle];
    	String[] right = new String[middle-1];
    	for(int i =0; i < left.length; i++) {
    		left[i] = inputs[i];
    	}
    	for(int i = 0; i < right.length; i++) {
    		right[i] = inputs[i+middle];
    	}
    
    	if(left.length != 0) {
    		addSeperators(middleNode.left,left);
    	}
    	if(right.length != 0) {
    		addSeperators(middleNode.right,right);
    	}
    	
 
    }
    
    private void addSeperators(StatementNode node, String[] inputs) {
    	node = new StatementNode(inputs[inputs.length/2], "Seperator");
    	System.out.println("ADDING: " + node.name);
    	int middle = inputs.length/2;
    	if(middle == 0)
    		return;
    	String[] left = new String[middle-1];
    	String[] right = new String[middle-1];
    	for(int i =0; i < left.length; i++) {
    		left[i] = inputs[i];
    	}
    	for(int i = 0; i < right.length; i++) {
    		right[i] = inputs[i+middle];
    	}
    	if(left.length != 0) {
    		addSeperators(node.left,left);
    	}
    	if(right.length != 0) {
    		addSeperators(node.right,right);
    	}
    	
 
		
	}

	public String toString() {
    	String rtn = root.name;
    	int depth = 1;
        if(root.center != null) {
        	rtn = rtn + "\n" + toString(root.center,depth);
        }
        return rtn;
    }
    
    public String toString(StatementNode node, int depth) {
    	String rtn = "";
    	for(int i = 0; i < depth; i++) {
    		rtn = rtn + "--";
    	}
    	rtn = rtn + node.name + "\n";
    	if(node.center != null) {
    		rtn = rtn + toString(node.center,depth+1);
    	}
    	if(node.left != null) {
    		System.out.println("asdfasdfasdf");
    		rtn = rtn + toString(node.left,depth+1);
    	}
    	if(node.right != null) {
    		rtn = rtn + toString(node.right,depth+1);
    	}
    	return rtn;
    }
}
