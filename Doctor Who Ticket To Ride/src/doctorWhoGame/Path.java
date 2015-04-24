package doctorWhoGame;

public class Path {

	private final Node[] nodes;
	
	// TODO: do some input checking. Make sure they aren't the same node
	public Path(Node n1, Node n2) {
		this.nodes = new Node[]{n1, n2};
	}

	public Node[] getNodes() {
		return this.nodes.clone();
	}

}
