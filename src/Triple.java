
public class Triple {

	private Function funct;
	private String[] params;
	private boolean value;
	
	public Triple() {
		this.funct = new Function();
		this.params = new String[0];
	    this.value = false;
		
	}
	
	public Triple(Function funct) {
		this();
		this.funct = funct;
	}
	
	public Triple(Function funct, String[] params) {
		this(funct);
		this.params = new String[params.length];
		for(int i = 0; i < params.length; i++) {
			this.params[i] = params[i];
		}
	}
	
	public Triple(Function funct, String[] params, boolean val) {
		this(funct,params);
		this.value = val;
	}
	
	public Function getFunct() {
		return funct;
	}

	public void setFunct(Function funct) {
		this.funct = funct;
	}

	public String[] getParams() {
		return params;
	}

	public void setParams(String[] params) {
		this.params = params;
	}

	public boolean getValue() {
		return value;
	}

	public void setValue(boolean value) {
		this.value = value;
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
