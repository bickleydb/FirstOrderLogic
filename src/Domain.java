import java.util.ArrayList;
import java.util.Arrays;

import org.w3c.dom.NodeList;


public class Domain {
	public String domainName;
	protected ArrayList<String> questions;
	protected ArrayList<String> expressions;
	protected ArrayList<Function> functions;
	protected ArrayList<Const> constants;
	
	public static ArrayList<Domain> readDomains () {
		DomainReader reader = new DomainReader("xml/Domain1.xml");
		return reader.getAllDomains();
	}
	
	public static String[] domainNames(ArrayList<Domain> in) {
		String[] names = new String[in.size()];
		for(int i = 0; i < in.size(); i++) {
			names[i] = in.get(i).domainName;
		}
		return names;
	}
	
	public Domain(String name, ArrayList<Function> domainFuncts, ArrayList<Const> consts,
			ArrayList<String> quests) {
		this.domainName = name;
		this.questions = new ArrayList<String>();
		this.expressions = new ArrayList<String>();
		this.functions = domainFuncts;
		this.constants = consts;
		for(int i = 0; i < quests.size(); i++){
			this.questions.add(quests.get(i).substring(0, quests.get(i).indexOf("!")));
			this.expressions.add(quests.get(i).substring(quests.get(i).indexOf("!")+1));
			
		}
	}

	public Domain() {
		// TODO Auto-generated constructor stub
	}

	public Function getFunction(String name) {
		String str = name.substring(0, name.indexOf("["));
		str = str.trim();
		//System.out.println(str);
		for(int i = 0; i < this.functions.size(); i++) {
			String checking = functions.get(i).getFunctionName();
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
	
	public boolean equals(Object other) {
		if( !(other instanceof Domain)) {
			return false;
		}
		Domain o = (Domain)other;
		if(this.domainName.equals(o.domainName))
			return true;
		return false;
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
		ProgramGui.main(args);

	}

	public String[] getQuestionNames() {
		String[] names = new String[this.questions.size()];
		for(int i = 0; i < this.questions.size(); i++) {
			names[i] = this.questions.get(i);
		}
		return names;
	}

}



