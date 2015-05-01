package doctorWhoGame;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Node {

	private final int id;
	private final String name;
	private int xCoord;
	private int yCoord;

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
		this.id = i;
		this.name = name;
		this.xCoord = x;
		this.yCoord = y;
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

	@Override
	public boolean equals(Object n1) {
		return (((Node) n1).id == this.id && ((Node) n1).name == this.name);
	}

}
