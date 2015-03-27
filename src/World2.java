import java.io.File;
import java.util.*;

import javax.swing.ComboBoxModel;

/**
 * 
 * @author Daniel Bickley
 * 
 */
public class World2 {

	private ArrayList<Triple> truthVals;
	
	//Basic Ctor while testing worlds. I figured it would be easiest to just hard code triples, with 
	//functions, params, and the truth value. Examples are in the method.
	public World2() {
		truthVals = new ArrayList<Triple>();
		truthVals.add(new Triple(new Function("Drinks With"),new String[]{"Homer", "Bob"},true));
		truthVals.add(new Triple(new Function("Friends With"),new String[]{"Homer", "Bob"},false));
		truthVals.add(new Triple(new Function("Testing"),new String[]{"Homer"},false));
		
		
	}

	/**
	 * Evaluates a given function according to the param array.
	 * 
	 * @param function
	 * @param params
	 * @return
	 */
	public boolean evaluate(Function function, String[] params) {
		String functionName = function.functionName;
		for(int i = 0; i < truthVals.size(); i++) {
			Triple cur = truthVals.get(i);
			//Grabs function
			if(cur.getFunct().functionName.equals(function.functionName)) {
				boolean ok = true;
				//If all the params are the same for something in the world
				for(int t = 0; t < cur.getParams().length; t++) {
					if(!params[t].equals(cur.getParams()[t])) {
						ok = false;
					}
				}
				if(ok) { //IT will return the hard coded truth value
					return cur.getValue();
				}
				
			}
		}
		//If it doesnt match exactly, returns false (I don't know what you'd like to do in this scenario.
		return false;
	}
}
