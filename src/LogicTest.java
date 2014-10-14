import java.io.File;

import org.w3c.dom.NodeList;


public class LogicTest {
	XMLReader[] readers;
	TruthTree[] worlds;
	
	public LogicTest () {
		File findFiles = new File("src/");
		String[] files = findFiles.list();
		int numWorlds = 0;
		for(int i = 0; i < files.length; i++) {
			//System.out.println(files[i]);
		  if(files[i].toLowerCase().indexOf("world") != -1) {
			  numWorlds++;
		  }
		}
		worlds = new TruthTree[numWorlds];
		int worldIndex = 0;
		for(int i = 0; i < files.length; i++) {
			if(files[i].indexOf(".xml") != -1 && files[i].indexOf("world") != -1) {
				worlds[worldIndex] = buildTree(files[i]);
				System.out.println(worlds[worldIndex]);
			}
		}
	}

	private TruthTree buildTree(String string) {
		XMLReader reader = new XMLReader("src/"+string);
		TruthTree world = new TruthTree(string);
		String[] func = reader.toArr(reader.getFunctions(),true);
		for(int i = 0; i < func.length; i++) 
			world.addFunction(func[i]);
		
		reader.getTruth();
		return world;
	}
}
