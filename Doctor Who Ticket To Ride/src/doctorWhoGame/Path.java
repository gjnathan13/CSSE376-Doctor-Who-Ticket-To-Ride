package doctorWhoGame;

import java.util.ArrayList;
import java.awt.Color;
import java.awt.geom.Line2D;

public class Path {

	private final Node[] nodes;
	private TrainColor pathColor;
	private PlayerColor ownedColor;
	private int pathLength;
	private boolean highlighted;
	private boolean clicked;
	private Line2D.Double line;
	private int shift;

	public Path(Node n1, Node n2) {
		this(n1, n2, TrainColor.Red, 0);
	}

	public Path(Node n1, Node n2, TrainColor t, int pathLength) {
		this(n1, n2, t, pathLength, 0);
	}

	public Path(Node n1, Node n2, TrainColor t, int pathLength, int shift) {
		if (n1.equals(n2))
			System.err.println("Path made from same node.");
		this.nodes = new Node[] { n1, n2 };
		this.pathColor = t;
		this.pathLength = pathLength >= 0 ? pathLength : 0;
		this.shift = shift;
	}

	public Node[] getNodes() {
		return this.nodes.clone();
	}

	public int getPathLength() {
		return this.pathLength;
	}

	public int getShift() {
		return this.shift;
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

	public void setHighlighted(boolean highlighted) {
		this.highlighted = highlighted;
	}

	public boolean getHighlighted() {
		return this.highlighted;
	}

	public void setClicked(boolean clicked) {
		this.clicked = clicked;
	}

	public boolean getClicked() {
		return this.clicked;
	}

	public void setLine(Line2D.Double line) {
		this.line = line;
	}

	public Line2D.Double getLine() {
		return this.line;
	}

	public void setOwnedColor(PlayerColor pColor) {
		this.ownedColor = pColor;
	}

	public Color getOwnedColor() {
		if (this.ownedColor != null) {
			switch (ownedColor) {
			case Green: {
				return Color.GREEN;
			}
			case Blue: {
				return Color.BLUE;
			}
			case Magenta: {
				return Color.MAGENTA;
			}
			case Red: {
				return Color.RED;
			}
			case Yellow: {
				return Color.YELLOW;
			}
			}
			return null;
		} else {
			return null;
		}
	}

	@Override
	public boolean equals(Object obj) {
		Path p2 = (Path) obj;
		return  p2.nodes[0].equals(this.nodes[0]) &&
				p2.nodes[1].equals(this.nodes[1]) ||
				p2.nodes[0].equals(this.nodes[1]) &&
				p2.nodes[1].equals(this.nodes[0]);
	}
}
