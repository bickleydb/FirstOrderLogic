
public class StatementTree {
    StatementNode root;
    
    public StatementTree() {
    	root = new StatementNode("root","root");
    }
    
    public void buildTree(String input) {
    	System.out.println(input);
    	String variableSection = input.substring(0, input.indexOf("("));
    	addScopes(variableSection);
    }
    
    public void addScopes(String vars) {
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
    }
    
    public String toString() {
    	String rtn = root.name;
    	int depth = 1;
    	for(int i = 0; i < root.children.length; i++) {
    		if(root.children[i] != null) {
    		  rtn = rtn + "\n" +  toString(root.children[i],depth);
    		}
    	}
    	return rtn;
    }
    
    public String toString(StatementNode node, int depth) {
    	String rtn = "";
    	for(int i = 0; i < depth; i++) {
    		rtn = rtn + "--";
    	}
    	rtn = rtn + node.name;
    	depth++;
    	for(int i = 0; i < node.children.length; i++) {
    		if(node.children[i]!= null) {
    			rtn = rtn + "\n" + toString(node.children[i],depth);
    		}
    	}
    	return rtn;
    }
}
