package doctorWhoGame;

import java.awt.Color;
import java.awt.geom.Line2D;
import java.util.HashMap;

public class Path {

	private final Node[] nodes;
	private TrainColor pathColor;
	private Color ownedColor;
	private int pathLength;
	private boolean highlighted;
	private boolean clicked;
	private Line2D.Double line;
	private int shift;

	private static HashMap<TrainColor, Color> pathColorMap = new HashMap<TrainColor, Color>();

	static {
		pathColorMap.put(TrainColor.Red, Color.RED);
		pathColorMap.put(TrainColor.Pink, Color.PINK);
		pathColorMap.put(TrainColor.Orange, Color.ORANGE);
		pathColorMap.put(TrainColor.Yellow, Color.YELLOW);
		pathColorMap.put(TrainColor.Green, Color.GREEN);
		pathColorMap.put(TrainColor.Blue, Color.BLUE);
		pathColorMap.put(TrainColor.White, Color.WHITE);
		pathColorMap.put(TrainColor.Black, Color.BLACK);
		pathColorMap.put(TrainColor.Rainbow, Color.GRAY);

	}

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
		if (pathColor != null) {
			return pathColorMap.get(pathColor);
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

	public void setOwnedColor(Color pColor) {
		this.ownedColor = pColor;
	}

	public Color getOwnedColor() {
			return ownedColor;
	}

	@Override
	public boolean equals(Object obj) {
		Path p2 = (Path) obj;
		return p2.nodes[0].equals(this.nodes[0]) && p2.nodes[1].equals(this.nodes[1])
				|| p2.nodes[0].equals(this.nodes[1]) && p2.nodes[1].equals(this.nodes[0]);
	}
}
