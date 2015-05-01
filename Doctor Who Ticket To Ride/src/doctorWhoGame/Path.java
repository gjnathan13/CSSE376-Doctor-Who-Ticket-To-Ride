package doctorWhoGame;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Path {

	private final Node[] nodes;
	private TrainColor pathColor;
	private int pathLength;
	private boolean highlighted;
	private boolean clicked;
	private Line2D.Double line;
	
	public Path(Node n1, Node n2){
		this(n1, n2, TrainColor.Red, 0);
	}
	
	// TODO: do some input checking. Make sure they aren't the same node
	public Path(Node n1, Node n2, TrainColor t, int pathLength) {
		if (n1.equals(n2))
			System.err.println("Path made from same node.");
		this.nodes = new Node[]{n1, n2};
		this.pathColor = t;
		this.pathLength = pathLength >=0?pathLength:0;
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
	
	public void setHighlighted(boolean highlighted){
		this.highlighted = highlighted;
	}
	
	public boolean getHighlighted(){
		return this.highlighted;
	}
	
	public void setClicked(boolean clicked){
		this.clicked = clicked;
	}
	
	public boolean getClicked(){
		return this.clicked;
	}
	
	public void setLine(Line2D.Double line){
		this.line = line;
	}
	
	public Line2D.Double getLine(){
		return this.line;
	}

}
