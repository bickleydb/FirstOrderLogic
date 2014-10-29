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
		
		String[] truths = reader.toArr(reader.getTruth(),true);
		String functionName = "";
		for(int t = 0; t < truths.length; t++) {
			functionName = truths[t];
			t++;
			int numMore = Integer.parseInt(truths[t]);
			System.out.println("Function: " + functionName);
			
			
			for(int i = t+1; i <= t+numMore; i++) {
				System.out.println("Params: " + truths[i]);
				System.out.println(world.addTruth(world.getFunction(functionName), truths[i].substring(truths[i].indexOf(":")+1)+":",numMore));
				
			}
			t = t+numMore+1;
		}
		System.out.println(world);
		return world;
	}
}
