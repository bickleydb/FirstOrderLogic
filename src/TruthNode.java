import java.util.Arrays;

/**
 * 
 * @author Daniel Bickley
 * 
 */
public class TruthNode {
	public String name;
	public String type;
	public String domain;
	TruthNode[] nodes;
	int startArr = 0;
	int totalNums = 0;

	/**
	 * @param name
	 * @param type
	 * @param domain
	 */
	public TruthNode(String name, String type, String domain) {
		this.name = name;
		this.type = type;
		this.domain = domain;
		nodes = new TruthNode[10];
	}

	/**
	 * 
	 */
	public String toString() {
		String rtn = "";
		rtn = rtn + name + "\n";
		int depth = 1;
		for (int i = 0; i < nodes.length; i++) {
			rtn = toString(rtn, depth);
		}
		return rtn;
	}

	/**
	 * 
	 * @param rtn
	 * @param depth
	 * @return
	 */
	public String toString(String rtn, int depth) {
		for (int i = 0; i < depth; i++) {
			rtn = rtn + "--";
		}
		rtn = rtn + name + "\n";
		depth++;
		for (int i = 0; i < nodes.length; i++) {
			rtn = toString(rtn, depth);
		}
		return rtn;

	}

	/**
	 * @param add
	 * @return
	 */
	public boolean addNode(TruthNode add) {
		if (totalNums == nodes.length)
			resizeNums();
		else {
			nodes[(startArr + totalNums) % nodes.length] = add;
			totalNums++;
		}
		return true;
	}

	/**
	 * 
	 */
	private void resizeNums() {
		TruthNode[] copy = new TruthNode[nodes.length * 2];
		for (int i = 0; i < nodes.length; i++) {
			copy[i] = nodes[i];
		}
		this.nodes = copy;

	}
}
