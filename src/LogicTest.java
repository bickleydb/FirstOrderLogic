import java.io.File;


public class LogicTest {
	XMLReader[] readers;
	
	
	public LogicTest () {
		File findFiles = new File("src/");
		String[] files = findFiles.list();
		int numWorlds = 0;
		for(int i = 0; i < files.length; i++) {
		  if(files[i].toLowerCase().indexOf("world") != -1) {
			  numWorlds++;
		  }
		}
		
		readers = new XMLReader[numWorlds];
		int numReaders = 0;
		for(int i = 0; i < files.length; i++) {
			  if(files[i].toLowerCase().indexOf("world") != -1) {
				 readers[numReaders] = new XMLReader("src/"+files[i]);
				 numReaders++;
			  }
			}
	}
	
	
	
	public boolean getTruth(XMLReader world, String input) {
		boolean rtn = false;
		String truth = world.getTruth();
		String [] stuff = world.toArr(truth);
		for(int i = 0; i < stuff.length; i++) {
			System.out.println(stuff[i]);
		}
		return rtn;
	}



	public void grade(String toAdd) {
		for(int i = 0; i < readers.length; i++) {
			System.out.println(toAdd);
			getTruth(readers[i],toAdd);
		}
		
	}

}
