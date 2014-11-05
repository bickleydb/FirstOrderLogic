import java.io.File;
import java.util.ArrayList;


public class LogicTest {
	XMLReader[] readers;
	TruthTree[] worlds;
	
	/**
	 * 
	 */
	public LogicTest () {
		File findFiles = new File("src/");
		String[] files = findFiles.list();
		int numWorlds = 0;
		for(int i = 0; i < files.length; i++) {
		  if(files[i].toLowerCase().indexOf("world") != -1) {
			  numWorlds++;
		  }
		}
		worlds = new TruthTree[numWorlds];
		int worldIndex = 0;
		for(int i = 0; i < files.length; i++) {
			if(files[i].indexOf(".xml") != -1 && files[i].indexOf("world") != -1) {
				worlds[worldIndex] = buildTree(files[i]);
			
			}
		}
	}
	
	public boolean evaluateStatement(String input) {
		System.out.println(this);
	    getSections(input);
		return false;
	}
	
	private String[] getSections (String input) {
	    int parts = getNumParts(input);
	    String[] getOps = getOperations(input,parts-1);
	    String[] rtn = getParts(input,getOps);
	    for(int i = 0; i < rtn.length; i++)
	    	System.out.println(rtn[i]);
	   // System.out.println(rtn[0]);
	    return rtn;
	    
	    
	}
	
	private String[] getParts (String input, String[] ops) {
		String[] rtn = new String[ops.length+1];
		for(int i = 0; i < rtn.length-1; i++) {
			rtn[i] = input.substring(0, input.indexOf(ops[i]));
			input = input.substring(input.indexOf(ops[i])+1);
			//System.out.println(input);
		}
		rtn[rtn.length-1] = input;
		return rtn;
	}
	
	private String[] getOperations (String input, int parts) {
		String[] seperators = Constants.seperators;
		String[] getOps = new String[parts];
		int opIndex = 0;
		for(int i = 0; i < input.length(); i++) {
			for(int t = 0; t < seperators.length; t++) {
				if(input.substring(i,i+seperators[t].length()).equals(seperators[t])) {
					getOps[opIndex] = seperators[t];
					opIndex++;
				}
			}
	    }
		return getOps;
	}
	
	private int getNumParts (String input) {
		String[] seperators = Constants.seperators;
		int sections = 0;
		for(int i = 0; i < input.length(); i++) {
			for(int t = 0; t < seperators.length; t++) {
				if(input.substring(i,i+seperators[t].length()).equals(seperators[t]))
					sections++;
			}
	    }
	
		return sections+1;
	}
	
	public String toString() {
		String rtn = "";
		for(int i = 0; i < worlds.length; i++){
			rtn = rtn + worlds[i].toString();
		}
		return rtn;
	}

	/**
	 * @param string
	 * @return
	 */
	private TruthTree buildTree(String string) {
		XMLReader reader = new XMLReader("src/"+string);
		TruthTree world = new TruthTree(string);
		String[] func = reader.toArr(reader.getFunctions(false),true);
		for(int i = 0; i < func.length; i++) 
			world.addFunction(func[i]);
		
		for(int i = 0; i < world.root.nodes.length; i++) {
			if(world.root.nodes[i] != null) {
	    	  ArrayList<String> truths = reader.getTruth(world.root.nodes[i].name);
	    	  for(int t = 0; t < truths.size(); t++) {
	    		  world.addTruth(world.root.nodes[i], truths.get(t));
	    	  }
	    	  truths.size();
			}
			else
				return world;
		
		}

		return world;
	}
}
