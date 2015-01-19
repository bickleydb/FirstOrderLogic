/**
 * Constant class that is used to read in the constants available for each
 * world. Each constant is associated with a name and domain. The name refers to
 * what the constant is for use with interpreting input, the domain will be used
 * for more complicated testing to be implemented at a later date.
 * 
 * @author Daniel Bickley
 * 
 */

public class Const {

	private String domain;
	private String name;

	/**
	 * Simple constructor that initializes the name and domain to something. The
	 * domain and name will both be "EMPTY" in this situation. The strings will
	 * not actually be empty, and no null pointers, so everyone is happy.
	 * 
	 */
	public Const() {
		this.domain = "EMPTY";
		this.name = "EMPTY";
	}

	/**
	 * Name constructor to be used if the domain does not matter. Calls the no
	 * parameter constructor, then changes the name of the Constant to the
	 * parameter.
	 * 
	 * @param name
	 *            The name of the Constant
	 */
	public Const(String name) {
		this();
		this.name = name;
	}

	/**
	 * Name and domain constructor to be used when both variables need to be
	 * specified. Calls the no parameter constructor
	 * 
	 * @param name
	 *            The name of the constant
	 * @param domain
	 *            The name of the domain
	 */
	public Const(String name, String domain) {
		this();
		this.name = name;
		this.domain = domain;
	}

	/**
	 * Returns a string representation of the constant, in the format NAME {
	 * DOMAIN }
	 */
	public String toString() {
		return "" + this.name + " {" + this.domain + "}";
	}

	/**
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		Const test = new Const("Test", "Test domain");
		System.out.println(test);
	}

	/**
	 * Getter for the domain instance variable
	 * 
	 * @return The domain of the constant
	 */
	public String getDomain() {
		return domain;
	}

	/**
	 * Getter for the name instance variable
	 * 
	 * @return The name of the constant
	 */
	public String getName() {
		return name;
	}

	/**
	 * Setter for the domain of the constant
	 * 
	 * @param domain
	 *            The new domain of the constant
	 */
	public void setDomain(String domain) {
		this.domain = domain;
	}

	/**
	 * Setter for the name of the constant
	 * 
	 * @param name
	 *            The new name of the constant
	 */
	public void setName(String name) {
		this.name = name;
	}
}
