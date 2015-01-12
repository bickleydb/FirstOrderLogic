public class Const {
	private String domain;
	private String name;

	public Const() {
		this.domain = "EMPTY";
		this.name = "EMPTY";
	}

	public Const(String name) {
		this();
		this.name = name;
	}

	public Const(String name, String domain) {
		this();
		this.name = name;
		this.domain = domain;
	}

	public String toString() {
		return "" + this.name + " {" + this.domain + "}";
	}

	public static void main(String[] args) {
		Const test = new Const("Test", "Test domain");
		System.out.println(test);
	}

	public String getDomain() {
		return domain;
	}

	public String getName() {
		return name;
	}

	public void setDomain(String domain) {
		this.domain = domain;
	}

	public void setName(String name) {
		this.name = name;
	}
}
