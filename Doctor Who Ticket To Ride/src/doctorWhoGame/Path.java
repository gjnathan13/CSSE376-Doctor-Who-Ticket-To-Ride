package doctorWhoGame;

public class Path {

	private final Node[] nodes;
	private int length;
	
	public Path(Node n1, Node n2){
		this(n1, n2, 0);
	}
	
	public Path(Node n1, Node n2, int length){
		if (n1.equals(n2))
			System.err.println("Path made from same node.");
			
		this.nodes = new Node[]{n1, n2};
		
		this.length = length>=0?length:0;
	}

	public Node[] getNodes() {
		return this.nodes.clone();
	}
	
	public int getLength(){
		return this.length;
	}

}
