
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
	public int numParams;
	private String parameters;
	private String domain;

	/**
	 * Default Constructor, initializes the instance variables to something
	 * besides NULL. Any arrays are initialized to size 0, and Strings are
	 * initialized to "EMPTY"
	 */

	public Function() {
		this.functionName = "EMPTY";
		this.domain = "EMPTY";
		this.parameters = "";
		numParams = 0;
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
		numParams = 0;

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
		numParams = 0;

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
			String parameters, int numParam) {
		this();
		this.functionName = functionName;
		this.domain = domain;
		this.parameters = parameters;
		this.numParams = numParam;
	}
	

	/**
	 * Returns a String representation of the function, showing the name,
	 * domain, number of parameters, and every parameter that the function will
	 * return true for in a human readable format.
	 */
	public String toString() {
		String rtn = "";
		rtn = rtn + this.functionName + getParamString() + "\n";
		return rtn;

	}

	/*
	 * public String[] nextPermutation() { return null;
	 * 
	 * }
	 */

	/**
	 * Creates a String representation of a list of parameters the function can
	 * take, in variable form. For instance, if a Function has 3 params, this
	 * method will return (x, y, z) if a Function has 1 param, this method will
	 * return (z) If a Function has 0 params, this method will return ()
	 * 
	 * @return String representation of the list of parameters of a function
	 */
	String getParamString() {
		String params = "[";
		params = params + this.parameters + "]"; 
		return params;
	}
	
	public String getNameParams(){
		return "" + this.functionName + " " + getParamString();	
	}

	public static void main(String[] args) {
		String[] params = {"Larry", "Bob", "Charlie" };
		Function test = new Function("TEST", "test");
		System.out.println(test.toString());

	}
	
	public boolean equals(Object o) {
		if(!(o instanceof Function))
			return false;
		Function other = (Function) o;
		if(!this.functionName.equals(other.functionName))
			return false;
		if(this.numParams != other.numParams)
			return false;
		return true;
	}

	public String getFunctionName() {
		return functionName;
	}

	public String getDomain() {
		return domain;
	}
	
	public String getParams() {
		return this.parameters;
	}

	public void setFunctionName(String functionName) {
		this.functionName = functionName;
	}
	
	public int getNumParams() {
		return this.numParams;
	}
	
	public void setNumParams(int paramNum) {
		this.numParams = paramNum;
		
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setParameters(String param) {
		this.parameters = param;
		
	}

}
