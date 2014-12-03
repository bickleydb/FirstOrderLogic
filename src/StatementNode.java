
public class StatementNode { 
	String name;
	String kindOfNode;
	int numberItems = 0;
	StatementNode[] children;
	
	public StatementNode (String name, String kindOfNode) {
		this.name = name;
		this.kindOfNode = kindOfNode;
		children = new StatementNode[10];
	}
	
	public void addNode (StatementNode add) {
		if(numberItems == children.length) {
			StatementNode[] newArr = new StatementNode[children.length*2];
			for(int i = 0; i < children.length; i++) {
				newArr[i] = children[i];
			}
			children = newArr;
		}
		children[numberItems] = add;
		numberItems++;
	}

}
