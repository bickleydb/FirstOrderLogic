import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;

/**
 * 
 * @author Daniel Bickley
 * 
 */
public class Universe {

	protected ArrayList<Function> functions;
	protected ArrayList<Const> constants;
	private String fileName;

	/**
	 * 
	 */
	public Universe() {
		this("xml/universe.xml");
		System.out.println(constants.size());
		
	}

	/**
	 * 
	 * @param fileName
	 */
	public Universe(String fileName) {
		this.fileName = fileName;
		FunctionLoader loading = new FunctionLoader(fileName);
		this.functions = loading.readFunctions();
		this.constants = loading.readConstants();
	}

	/**
	 * 
	 * @param input
	 * @return
	 */
	public boolean checkStatement(String input) {
		return false;
	}
	
	public Function getFunction(String name) {
		//System.out.println(name);
		
		String str = name.substring(0, name.indexOf("["));
		str = str.trim();
		//System.out.println(str);
		for(int i = 0; i < this.functions.size(); i++) {
			if(str.equals(functions.get(i).getFunctionName())) {
				//System.out.println("asdf");
				return functions.get(i);
			}
		}
		return null;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getFunctionWithoutParams() {
		String[] rtn = new String[functions.size()];
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = functions.get(i).functionName;
		}
		return rtn;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getFunctionNames() {
		String[] rtn = new String[functions.size()];
		for (int i = 0; i < rtn.length; i++) {
			rtn[i] = functions.get(i).getNameParams();
		}
		return rtn;
	}

	/**
	 * 
	 * @return
	 */
	public String[] getConstantNames() {
		String[] rtn = new String[constants.size()];
		for (int i = 0; i < rtn.length; i++)
			rtn[i] = constants.get(i).getName();
		return rtn;
	}

	/**
	 * 
	 */
	public String toString() {
		String rtn = "Functions contained in the World of file "
				+ this.fileName + "\n";
		rtn = rtn
				+ Arrays.toString(this.getFunctionNames()
						) + "\n";
		rtn = rtn + "Constants contained in the World of file " + this.fileName
				+ "\n";
		rtn = rtn
				+ Arrays.toString(this.getConstNames())
				+ "\n";
		return rtn;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Function> getFunctions() {
		return functions;
	}
	
	/**
	 * 
	 * @param arr
	 * @return
	 */
	public String[] getConstNames() {
		String[] rtn = new String[this.constants.size()];
		for (int i = 0; i < this.constants.size(); i++)
			rtn[i] = this.constants.get(i).getName();
		return rtn;
	}

	/**
	 * 
	 * @return
	 */
	public ArrayList<Const> getConstants() {
		return constants;
	}

	/**
	 * 
	 * @param functions
	 */
	public void setFunctions(ArrayList<Function> functions) {
		this.functions = functions;
	}

	/**
	 * 
	 * @param constants
	 */
	public void setConstants(ArrayList<Const> constants) {
		this.constants = constants;
	}

	/**
	 * 
	 * @return
	 */
	public String getFilename() {
		return this.fileName;
	}
	
	/**
	 * Returns an Array of Strings that contain the name of each function defined in the parameter.
	 * This method is mostly used when translating a list of functions into just their names and
	 * parameters for use for the GUI.
	 * 
	 * @param arr
	 * @return
	 */
	public static String[] getFunctionNames(ArrayList<Function> arr) {
		String[] rtn = new String[arr.size()];
		for (int i = 0; i < arr.size(); i++)
			rtn[i] = arr.get(i).getNameParams();
		return rtn;
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Universe uni = new Universe();
		System.out.println(uni.toString());

	}

}
