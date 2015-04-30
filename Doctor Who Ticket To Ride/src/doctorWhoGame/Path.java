package doctorWhoGame;

import java.util.ArrayList;
import java.awt.Color;

public class Path {

	private final Node[] nodes;
	private TrainColor pathColor;
	
	// TODO: do some input checking. Make sure they aren't the same node
	public Path(Node n1, Node n2, TrainColor t) {
		this.nodes = new Node[]{n1, n2};
		this.pathColor = t;
	}

	public Node[] getNodes() {
		return this.nodes.clone();
	}
	
	public Color getPathColor() {
		return null;
	}

}
