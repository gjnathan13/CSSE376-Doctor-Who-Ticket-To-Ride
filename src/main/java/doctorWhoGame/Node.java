package doctorWhoGame;

import java.awt.Color;
import java.awt.geom.Point2D;

public class Node {

	private final int id;
	private final String name;
	private int xCoord;
	private int yCoord;
	private String abbr;
	private Color color;

	public Node(int i) {
		this(i, 0, 0, "");
	}

	public Node(int i, String name) {
		this(i, 0, 0, name);
	}

	public Node(int i, int x, int y) {
		this(i, x, y, "");
	}

	public Node(int i, int x, int y, String name) {
		this(i, x, y, name, "", null);
	}

	public Node(int i, int xPos, int yPos, String name, String abbr, Color color) {
		this.id = i;
		this.xCoord = xPos;
		this.yCoord = yPos;
		this.name = name;
		this.abbr = abbr;
		this.color = color;

	}

	public int getID() {
		return this.id;
	}

	public String getName() {
		return this.name;
	}

	public Point2D getNodePoint() {
		return new Point2D.Double(xCoord, yCoord);
	}

	public String getAbbreviation() {
		return this.abbr;
	}

	public Color getNodeColor() {
		return this.color;
	}

	@Override
	public boolean equals(Object n1) {
		return (((Node) n1).id == this.id && ((Node) n1).name == this.name);
	}

}
