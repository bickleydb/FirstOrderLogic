
public class StatementNode { 
	String name;
	String kindOfNode;
	int numberItems = 0;
	StatementNode left;
	StatementNode right;
	StatementNode center;
	
	public StatementNode (String name, String kindOfNode) {
		this.name = name;
		this.kindOfNode = kindOfNode;
	}
	
	public void addNode (StatementNode add) {
		if(add.kindOfNode.equals("Scope")) {
			center = add;
			return;
		}
		if(right == null){
			right = add;
		} else {
			left = add;
		}
	}
	

}
