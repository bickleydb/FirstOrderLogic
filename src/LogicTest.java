import java.io.File;
import java.util.ArrayList;

import org.w3c.dom.NodeList;


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
				//System.out.println(worlds[worldIndex]);
			}
		}
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
		String[] func = reader.toArr(reader.getFunctions(),true);
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
