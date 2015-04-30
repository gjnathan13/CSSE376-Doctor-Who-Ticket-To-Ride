package doctorWhoGame;

import java.awt.Point;
import java.awt.geom.Point2D;
import java.awt.geom.Point2D.Double;

public class Node {

	private final int id;
	private int xCoord;
	private int yCoord;
	
	public Node(int i, int x, int y) {
		this.id = i;
		this.xCoord = x;
		this.yCoord = y;
	}

	public int getID() {
		return id;
	}
	
	public Point2D getNodePoint() {
		return new Point2D.Double(xCoord, yCoord);
	}

}
