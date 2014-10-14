import java.util.Arrays;


public class TruthNode {
	public String name;
	public String type;
	public String domain;
	TruthNode[] nodes;
	int startArr = 0;
	int totalNums = 0;
	
	public TruthNode (String name, String type, String domain) {
		this.name = name;
		this.type = type;
		this.domain = domain;
		nodes = new TruthNode[10];
	}
	


	public boolean addNode (TruthNode add) {
		if(totalNums == nodes.length)
			resizeNums();
		else {
		  nodes[(startArr+totalNums)%nodes.length] = add;
		  totalNums++;
		}
		return true;
	}

	private void resizeNums() {
		TruthNode[] copy = new TruthNode[nodes.length*2];
		for(int i = 0; i < nodes.length; i++) {
		  copy[i] = nodes[i];	
		}
		this.nodes = copy;
		
	}
}
