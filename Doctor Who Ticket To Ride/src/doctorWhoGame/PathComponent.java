package doctorWhoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Line2D;
import java.awt.geom.Rectangle2D;

import javax.swing.JComponent;

public class PathComponent extends JComponent {

	private final float LINE_WIDTH = 10;
	private final float HIGHTLIGHT_WIDTH = 15;
	private final float DASH_LENGTH = 50;
	private final float DASH_OFFSET = 10;
	private Path pathOne;

	private boolean highlighted = false;
	private boolean selected = false;

	private Path[] pathArray;
	private Gameboard gameboard;

	public PathComponent(Path[] pArray, Gameboard gameboard) {
		this.pathArray = pArray;
		this.gameboard = gameboard;

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
			this.setPreferredSize(new Dimension(lineWidth, lineHeight));
			int xSmall = Math.min((int) line.getX2(), (int) line.getX1());
			int ySmall = Math.min((int) line.getY2(), (int) line.getY1());
			this.setBounds(xSmall, ySmall, lineWidth + 75, lineHeight + 75);

			float lineLength = (float) Math.sqrt(Math.pow(lineWidth, 2)
					+ Math.pow(lineHeight, 2));
			float spacing = (lineLength - (DASH_OFFSET * 2) - path
					.getPathLength() * (DASH_LENGTH))
					/ (path.getPathLength() - 1);

			float[] dashArray = { 50, spacing };

			if (path.getHighlighted() == true || path.getClicked() == true) {
				g2.setColor(Color.CYAN);
				g2.setStroke(new BasicStroke(HIGHTLIGHT_WIDTH,
						BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
				g2.draw(line);
			}
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
					gameboard.setPurchasing(p);
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
