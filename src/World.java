import java.io.File;
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

	public boolean evaluate(Function function, String[] params) {
		String functionName = function.functionName;
		for(int i = 0; i < truthVals.size(); i++) {
			Triple cur = truthVals.get(i);
			if(cur.getFunct().functionName.equals(function.functionName)) {
				boolean ok = true;
				for(int t = 0; t < cur.getParams().length; t++) {
					if(!params[t].equals(cur.getParams()[t])) {
						ok = false;
					}
				}
				if(ok) {
					return cur.getValue();
				}
				
			}
		}
		return false;
	}
}
