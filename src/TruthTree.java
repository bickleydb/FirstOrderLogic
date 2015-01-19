import java.util.Arrays;

/**
 * 
 * @author Daniel Bickley
 *
 */
public class TruthTree {
	TruthNode root;
	
	/**
	 * @param worldName
	 */
	public TruthTree (String worldName) {
		root = new TruthNode(worldName, "world","banana");
	}
	
	/**
	 * @param functionName
	 * @return
	 */
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
	
	/**
	 * @param cur
	 * @param params
	 * @return
	 */
	public boolean addTruth (TruthNode cur, String params) {
		int numParams = 0;
		for(int i = 0; i < params.length(); i++) {
			if(params.charAt(i) == ':')
				numParams++;
		}
		String[] truths = new String[numParams+1];
		for(int i = 0; i < truths.length; i++) {
			if(params.indexOf(":") != -1) {
			  truths[i] = params.substring(0,params.indexOf(":"));
			  params = params.substring(params.indexOf(":")+1);
			} else {
			  truths[i] = params;
			}
		}
		for(int i = 0; i < cur.nodes.length; i++) {
			if(cur.nodes[i] == null) {
				cur.nodes[i] = new TruthNode(truths[0], "truth", "b");
				return addTruthNode(cur.nodes[i],truths,1);
			} else {
				if(cur.nodes[i].name.equals(truths[0]))
					return addTruthNode(cur.nodes[i],truths,1);
			}
		}
		return true;
	}
	
	
	/**
	 * 
	 * @param cur
	 * @param params
	 * @param index
	 * @return
	 */
	public boolean addTruthNode(TruthNode cur, String[] params, int index) {
		//Arrays.sort(params);
		if(index >= params.length)
			return true;
		for(int i = 0; i < cur.nodes.length; i++) {
			if(cur.nodes[i] == null) {
				cur.nodes[i] = new TruthNode(params[index],"truth","b");
				return addTruthNode(cur.nodes[i],params,index+1);
			} else {
				if(cur.nodes[i].name.equals(params[index]))
				return addTruthNode(cur.nodes[i],params,index+1);
			}
		}
		return true;
	}
	/**
	 * @param variableName
	 * @param functionName
	 * @param domain
	 * @return
	 */
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
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	public String toString () {
		return toString(root,0);
	
	}
	
	/**
	 * @param node
	 * @param level
	 * @return
	 */
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

	/**
	 * @param functionName
	 * @return
	 */
	public TruthNode getFunction(String functionName) {
		for(int i = 0; i < root.nodes.length; i++){
			if(root.nodes[i] != null && root.nodes[i].name.indexOf(functionName)!= -1)
				return root.nodes[i];
		}
		return null;
	}

}
