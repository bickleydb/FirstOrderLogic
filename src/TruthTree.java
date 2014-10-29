
public class TruthTree {
	TruthNode root;
	
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
	
	public boolean addTruth (TruthNode cur, String params, int depth) {
		String searching = params;
		if(depth == 0)
			return true;
		if(params.indexOf(":") != -1) {
			searching = params.substring(0, params.indexOf(":"));
			params = params.substring(params.indexOf(":")+1);
		}
		if(params.indexOf(":") == -1) {
			params = "";
			depth = 1;
		}
		
		System.out.println("Searching: " + searching + "  Param: " + params);
		TruthNode[] toAdd = cur.nodes;
		for(int i = 0; i < toAdd.length; i++) {
			if(toAdd[i]!= null) {
				if(toAdd[i].name.indexOf(searching) != -1) {
				   return addTruth(toAdd[i],params,depth-1);
				}
			}
			cur.addNode(new TruthNode(searching,"",""));
			return addTruth(toAdd[i],params,depth-1);
		}
		
		return true;
	}
	public boolean addVariable (String variableName, String functionName, String domain) {
		TruthNode function = null;
		for(int i = 0; i < root.nodes.length; i++) {
			if(!(root.nodes[i] == null)) {
				if(functionName.indexOf(":") != -1) {
				   functionName = functionName.substring(functionName.indexOf(":"));
				}
			  if(root.nodes[i].name.equals(functionName)) {
				System.out.println("Node name: " + root.nodes[i].name);
				function = root.nodes[i];
			  }
			}
		}
		if(function == null)
			return false;
		
		for(int i = 0; i < function.nodes.length; i++) {
			if(function.nodes[i] == null) {
				break;
			}
			if(function.nodes[i].name.equals(variableName))
				return false;
		}
		
		function.addNode(new TruthNode(variableName,"variable",domain));
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

	public TruthNode getFunction(String functionName) {
		for(int i = 0; i < root.nodes.length; i++){
			if(root.nodes[i] != null && root.nodes[i].name.indexOf(functionName)!= -1)
				return root.nodes[i];
		}
		return null;
	}

}
