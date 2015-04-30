package doctorWhoGame;

import java.util.ArrayList;
import java.awt.Color;

public class Path {

	private final Node[] nodes;
	private TrainColor pathColor;
	private int pathLength;
	
	// TODO: do some input checking. Make sure they aren't the same node
	public Path(Node n1, Node n2, TrainColor t, int pathLength) {
		this.nodes = new Node[]{n1, n2};
		this.pathColor = t;
		this.pathLength = pathLength;
	}

	public Node[] getNodes() {
		return this.nodes.clone();
	}
	
	public int getPathLength(){
		return this.pathLength;
	}
	
	public Color getPathColor() {
		switch (pathColor) {
		case Red: {
			return Color.RED;
		}
		case Pink: {
			return Color.PINK;
		}
		case Orange: {
			return Color.ORANGE;
		}
		case Yellow: {
			return Color.YELLOW;
		}
		case Green: {
			return Color.GREEN;
		}
		case Blue: {
			return Color.BLUE;
		}
		case White: {
			return Color.WHITE;
		}
		case Black: {
			return Color.BLACK;
		}
		case Rainbow: {
			return Color.GRAY;
		}
	}
		return null;
	}

}
