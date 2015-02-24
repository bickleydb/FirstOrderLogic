import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.*;

import javax.swing.ComboBoxModel;

/**
 * 
 * @author Daniel Bickley
 * 
 */
public class World {

	private ArrayList<Triple> truthVals;
	
	public World() {
		truthVals = new ArrayList<Triple>();
		truthVals.add(new Triple(new Function("Drinks With"),new String[]{"Homer", "Bob"},true));
		truthVals.add(new Triple(new Function("Friends With"),new String[]{"Homer", "Bob"},false));
		truthVals.add(new Triple(new Function("Ghey"),new String[]{"Homer"},false));
		
		
	}
}
