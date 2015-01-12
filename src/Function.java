import java.util.Arrays;

/**
 * Function class for use with FirstOrderLogic program. This class encapsulates
 * data about each function in a given world. Each function has a String name
 * that separates it from other Functions. Then, each Function has a domain
 * where it is "based in" for use with other classes for additional checking.
 * Each function also has a set of parameters that it will return "true" for.
 * Currently, the order of the input parameters does not matter for error
 * checking, but that should hopefully be added in later.
 * 
 * @author Daniel Bickley
 *
 */

public class Function {

	public String functionName;
	private String[][] goodConfigurations;
	private String domain;

	/**
	 * Default Constructor, initializes the instance variables to something
	 * besides NULL. Any arrays are initialized to size 0, and Strings are
	 * initialized to "EMPTY"
	 */

	public Function() {
		this.functionName = "EMPTY";
		this.domain = "EMPTY";
		this.goodConfigurations = new String[0][0];
	}

	/**
	 * Constructor that only specifies the function name. The domain and
	 * possible parameters remain unchanged from Default Constructor.
	 * 
	 * @param functionName
	 *            Denotes the name of the function.
	 * 
	 */
	public Function(String functionName) {
		this();
		this.functionName = functionName;

	}

	/**
	 * Constructor that specifies the function name and the domain. The possible
	 * true parameters remain unchanged from Default Constructor.
	 * 
	 * @param functionName
	 *            Denotes the name of the function.
	 * @param domain
	 *            Denotes the domain that the function is a part of.
	 * 
	 */

	public Function(String functionName, String domain) {
		this();
		this.functionName = functionName;
		this.domain = domain;

	}

	/**
	 * Constructor that specifies the function name, domain, and possible true
	 * parameters.
	 * 
	 * @param functionName
	 *            Denotes the name of the function.
	 * @param domain
	 *            Denotes the domain that the function is a part of.
	 * @param goodConfigurations
	 *            Array that contains all of the parameters that the function
	 *            will return true for.
	 */
	public Function(String functionName, String domain,
			String[][] goodConfigurations) {
		this();
		this.functionName = functionName;
		this.domain = domain;
		this.goodConfigurations = goodConfigurations;
	}

	/**
	 * Returns a String representation of the function, showing the name,
	 * domain, number of parameters, and every parameter that the function will
	 * return true for in a human readable format.
	 */
	public String toString() {
		String rtn = "";
		rtn = rtn + this.functionName + getParamString() + "\n";
		rtn = rtn + "Function will return true for: ";
		rtn = rtn + "\n" + getGoodConfigs();
		return rtn;

	}

	/*
	 * public String[] nextPermutation() { return null;
	 * 
	 * }
	 */

	/**
	 * Returns a String representation of every parameter that the function will
	 * return true for. The output of the method is in the format
	 * 
	 * [ Param 1A, Param 2A, ... , Param NA] [ Param 1B, Param 2B, ... , Param
	 * NB] etc.
	 * 
	 * 
	 * @return String list of parameters that the function will return true for.
	 * 
	 */
	public String getGoodConfigs() {
		String rtn = "";
		for (int i = 0; i < goodConfigurations.length; i++) {
			//Arrays.sort(goodConfigurations[i]);
			rtn = rtn + Arrays.toString(goodConfigurations[i]) + "\n";
		}
		return rtn;
	}

	/**
	 * Creates a String representation of a list of parameters the function can
	 * take, in variable form. For instance, if a Function has 3 params, this
	 * method will return (x, y, z) if a Function has 1 param, this method will
	 * return (z) If a Function has 0 params, this method will return ()
	 * 
	 * @return String representation of the list of parameters of a function
	 */
	private String getParamString() {
		String params = "(";
		if (this.goodConfigurations[0].length == 0) {
			return params + ")";
		}
		for (int i = 0; i < this.goodConfigurations[0].length - 1; i++) {
			params = params
					+ (char) ('z' - this.goodConfigurations[0].length + 1 + i)
					+ ",";
		}
		params = params + "z)";
		return params;
	}
	
	public String getNameParams(){
		return "" + this.functionName + " " + getParamString();	
	}

	public static void main(String[] args) {
		String[][] params = { {"Larry", "Bob", "Charlie" },
				{ "Huey", "Dewey", "Louie" }, { "Abercrombie", "Fitch" } };
		Function test = new Function("TEST", "test", params);
		System.out.println(test.toString());

	}

	public String getFunctionName() {
		return functionName;
	}

	public String[][] getGoodConfigurations() {
		return goodConfigurations;
	}

	public String getDomain() {
		return domain;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}

	public void setGoodConfigurations(String[][] goodConfigurations) {
		this.goodConfigurations = goodConfigurations;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

}
