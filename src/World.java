import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.ComboBoxModel;

public class World {

	protected ArrayList<Function> functions;
	protected ArrayList<Const> constants;
	private String fileName;

	public World() {
		this("xml/world1.xml");
	}

	public World(String fileName) {
		this.fileName = fileName;
	    FunctionLoader loading = new FunctionLoader(fileName);
		this.functions = loading.readFunctions();
		this.constants = loading.readConstants();
	}

	public boolean checkStatement(String input) {
		return false;
	}
	
	public String[] getFunctionWithoutParams() {
		String[] rtn = new String[functions.size()];
		for(int i = 0; i < rtn.length; i++) {
			rtn[i] = functions.get(i).functionName;
		}
		return rtn;
	}
	
	public String[] getFunctionNames() {
		String[] rtn = new String[functions.size()];
		for(int i = 0; i < rtn.length; i++) {
			rtn[i] = functions.get(i).getNameParams();
		}
		return rtn;
	}
	
	public String[] getConstantNames() {
		String[] rtn = new String[constants.size()];
		for(int i = 0; i < rtn.length; i++) 
			rtn[i] = constants.get(i).getName();
		return rtn;
	}
	
	public String toString () {
		String rtn  = "Functions contained in the World of file " + this.fileName + "\n";
		rtn = rtn + Arrays.toString(FunctionLoader.getFunctionNames(this.functions)) + "\n";
		rtn = rtn + "Constants contained in the World of file " + this.fileName + "\n";
		rtn = rtn + Arrays.toString(FunctionLoader.getConstNames(this.constants)) + "\n";
		return rtn;
	}
	
	public ArrayList<Function> getFunctions() {
		return functions;
	}

	public ArrayList<Const> getConstants() {
		return constants;
	}

	public void setFunctions(ArrayList<Function> functions) {
		this.functions = functions;
	}

	public void setConstants(ArrayList<Const> constants) {
		this.constants = constants;
	}
	
	public String getFilename () {
		return this.fileName;
	}

	public static void main(String[] args) {
		World world1 = new World();
		System.out.println(world1.toString());

	}

}
