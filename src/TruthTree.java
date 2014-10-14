
public class TruthTree {
	TruthNode root;
	
	public static void main(String[] args) {
		TruthTree test = new TruthTree("World 1");
		test.addFunction("Test");
		test.addConstant("testestest", "Test");
		test.addFunction("Hopefully this will work");
		System.out.println(test);
		
	}
	
	public TruthTree (String worldName) {
		root = new TruthNode(worldName, "world","banana");
	}
	
	public boolean addFunction (String functionName) {
		for(int i = 0; i < root.nodes.length; i++) {
			if(root.nodes[i] == null) {
				break;
			}
			if(root.nodes[i].name.equals(functionName))
				return false;
		}
		root.addNode(new TruthNode(functionName, "function","banana"));
		return true;
	}
	
	public boolean addConstant (String constantName, String functionName) {
		TruthNode function = null;
		for(int i = 0; i < root.nodes.length; i++) {
			if(!(root.nodes[i] == null)) {
				
			if(root.nodes[i].name.equals(functionName))
				function = root.nodes[i];
			}
		}
		if(function == null)
			return false;
		
		for(int i = 0; i < function.nodes.length; i++) {
			if(function.nodes[i] == null) {
				break;
			}
			if(function.nodes[i].name.equals(constantName))
				return false;
		}
		
		function.addNode(new TruthNode(constantName,"constant","banana"));
		return true;
	}
	
	public String toString () {
		return toString(root,0);
	
	}
	
	public String toString(TruthNode node, int level) {
		String rtn = "";
		String indent = "";
		if(level != 0)
			indent= indent + "|";
		for(int i = 0; i < level; i++) {
			indent = indent + "--";	
		}
		rtn = rtn + indent + node.name + "\n";
		
		for(int i = 0; i < node.nodes.length; i++) {
		    if(node.nodes[i] == null)
		    	return rtn;
		    else
		    	rtn = rtn + toString(node.nodes[i],level+1);
		}
		return rtn;
	}

}
