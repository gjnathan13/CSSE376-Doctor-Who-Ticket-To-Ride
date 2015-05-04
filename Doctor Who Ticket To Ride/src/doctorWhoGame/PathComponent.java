package doctorWhoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class PathComponent extends JComponent {

	private final float LINE_WIDTH = 10;
	private final float HIGHTLIGHT_WIDTH = 15;
	private final float DASH_LENGTH = 50;
	private final float DASH_OFFSET = 30;
	private float PLANET_WIDTH = 3;
	private final double PLANET_RADIUS = 25;
	private Path pathOne;

	private boolean highlighted = false;
	private boolean selected = false;

	private Path[] pathArray;
	private Node[] nodeArray;
	private Gameboard gameboard;

	public PathComponent(Path[] pathArray, Gameboard gameboard) {
		this(pathArray, null, gameboard);
	}

	public PathComponent(Path[] pArray, Node[] nodeArray, Gameboard gameboard) {
		this.pathArray = pArray;
		this.gameboard = gameboard;
		this.nodeArray = nodeArray;

		if (pArray != null) {
			for (Path path : pathArray) {
				Line2D.Double line = new Line2D.Double(
						path.getNodes()[0].getNodePoint(),
						path.getNodes()[1].getNodePoint());
				path.setLine(line);
			}
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Path path : pathArray) {
			Line2D.Double line = path.getLine();
			int lineWidth = (int) Math.abs(line.getX2() - line.getX1());
			int lineHeight = (int) Math.abs(line.getY2() - line.getY1());
			int xSmall = Math.min((int) line.getX2(), (int) line.getX1());
			int ySmall = Math.min((int) line.getY2(), (int) line.getY1());

			float lineLength = (float) Math.sqrt(Math.pow(lineWidth, 2)
					+ Math.pow(lineHeight, 2));
			float spacing = (lineLength - (DASH_OFFSET * 2) - (path
					.getPathLength() * DASH_LENGTH))
					/ (path.getPathLength() - 1);

			float[] dashArray = new float[(2 * path.getPathLength() - 1) + 4];
			dashArray[0] = 0;
			dashArray[1] = DASH_OFFSET;
			dashArray[dashArray.length - 1] = 0;
			dashArray[dashArray.length - 2] = DASH_OFFSET;
			for (int i = 2; i < dashArray.length - 2; i++) {
				if (i % 2 == 0) {
					dashArray[i] = DASH_LENGTH;
				} else {
					dashArray[i] = spacing;
				}
			}

			if (path.getHighlighted() == true || path.getClicked() == true) {
				g2.setColor(Color.CYAN);
				float[] highlightArray = new float[5];
				highlightArray[0] = 0;
				highlightArray[4] = 0;
				highlightArray[1] = DASH_OFFSET;
				highlightArray[3] = DASH_OFFSET;
				highlightArray[2] = lineLength - 2 * DASH_OFFSET;
				g2.setStroke(new BasicStroke(HIGHTLIGHT_WIDTH,
						BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.f, highlightArray, 0));
				g2.draw(line);
			}

			g2.setColor(path.getPathColor());
			g2.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_ROUND, 10.0f, dashArray, 0));
			g2.draw(line);
		}

		for (Node node : nodeArray) {
			g2.setColor(node.getNodeColor());
			g2.setStroke(new BasicStroke(PLANET_WIDTH, BasicStroke.CAP_BUTT,
					BasicStroke.JOIN_ROUND));
			double xCenter = node.getNodePoint().getX();
			double yCenter = node.getNodePoint().getY();
			Ellipse2D.Double planet = new Ellipse2D.Double(xCenter
					- this.PLANET_RADIUS, yCenter - this.PLANET_RADIUS,
					2 * this.PLANET_RADIUS, 2 * this.PLANET_RADIUS);
			g2.draw(planet);
		}
	}

	public void highlightCLicked(int xMouse, int yMouse) {
		float xBox = xMouse - LINE_WIDTH;
		float yBox = yMouse - LINE_WIDTH;
		for (Path p : pathArray) {
			Line2D.Double pathLine = p.getLine();
			if (pathLine.intersects(xBox, yBox, 2 * LINE_WIDTH, 2 * LINE_WIDTH)) {
				if (p.getClicked()) {
					p.setClicked(false);
				} else {
					p.setClicked(true);
					gameboard.setPurchasing(p, this);
				}
				this.repaint();
			}
		}
	}

	public void checkHighlight(int xMouse, int yMouse) {
		float xBox = xMouse - LINE_WIDTH;
		float yBox = yMouse - LINE_WIDTH;
		for (Path p : pathArray) {
			Line2D.Double pathLine = p.getLine();
			if (pathLine.intersects(xBox, yBox, 2 * LINE_WIDTH, 2 * LINE_WIDTH)) {
				p.setHighlighted(true);
				this.repaint();
			} else {
				boolean tempHighlight = p.getHighlighted();
				p.setHighlighted(false);
				if (tempHighlight == true) {
					this.repaint();
				}
			}
		}
	}

	/*
	 * For when the route is clicked on.
	 */
	public void highlightCLicked() {
		// no longer used, here for tests
	}

	/*
	 * For when the mouse moves off of the route and it was not clicked.
	 */
	public void unhighlight() {
		// no longer used, here for tests
	}

	public void highlightHover() {
		// no longer used, here for tests
	}

}
