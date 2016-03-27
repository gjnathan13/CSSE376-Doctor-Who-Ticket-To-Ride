package doctorWhoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class PathComponent extends JComponent {

	private static final int SHIFT = (int) (10.0*GameStarter.getDiagonalModifier());
	private final float LINE_WIDTH = (float) (10.0*GameStarter.getDiagonalModifier());
	private final float HIGHTLIGHT_WIDTH = (float) (15.0*GameStarter.getDiagonalModifier());
	private final float DASH_LENGTH = (float) (40.0*GameStarter.getDiagonalModifier());
	private final float DASH_OFFSET = (float) (30.0*GameStarter.getDiagonalModifier());
	private float PLANET_WIDTH = (float) (3.0*GameStarter.getDiagonalModifier());
	private final double PLANET_RADIUS = (float) (25.0*GameStarter.getDiagonalModifier());
	private Path pathOne;

	private boolean highlighted = false;
	private boolean selected = false;

	private Path[] pathArray;
	private Node[] nodeArray;
	private Gameboard gameboard;
	private boolean purchasing;
	private boolean routeGetting;

	public PathComponent(Path[] pathArray, Gameboard gameboard) {
		this(pathArray, null, gameboard);
	}

	public PathComponent(Path[] pArray, Node[] nodeArray, Gameboard gameboard) {
		this.pathArray = pArray;
		this.gameboard = gameboard;
		this.nodeArray = nodeArray;

		if (pArray != null) {
			for (Path path : pathArray) {
				Point2D pointOne = path.getNodes()[0].getNodePoint();
				Point2D pointTwo = path.getNodes()[1].getNodePoint();

				if (path.getShift() != 0) {
					Line2D.Double line = getShiftedLine(pointOne, pointTwo, path.getShift());
					path.setLine(line);
				} else {
					Line2D.Double line = new Line2D.Double(pointOne, pointTwo);
					path.setLine(line);
				}
			}
		}
	}

	private Line2D.Double getShiftedLine(Point2D pointOne, Point2D pointTwo, int shift) {
		Point2D.Double actualOne;
		Point2D.Double actualTwo;
		if (pointTwo.getX() == pointOne.getX()) {
			actualOne = new Point2D.Double(pointOne.getX() + SHIFT * shift, pointOne.getY());
			actualTwo = new Point2D.Double(pointTwo.getX() + SHIFT * shift, pointOne.getY());
			return new Line2D.Double(actualOne, actualTwo);
		} else if (pointTwo.getY() == pointOne.getY()) {
			actualOne = new Point2D.Double(pointOne.getX(), pointOne.getY() + SHIFT * shift);
			actualTwo = new Point2D.Double(pointTwo.getX(), pointOne.getY() + SHIFT * shift);
			return new Line2D.Double(actualOne, actualTwo);
		} else if (pointTwo.getX() > pointOne.getX()) {
			actualOne = (Point2D.Double) pointOne;
			actualTwo = (Point2D.Double) pointTwo;
		} else {
			actualOne = (Point2D.Double) pointTwo;
			actualTwo = (Point2D.Double) pointOne;
		}
		double x1 = actualOne.getX();
		double x2 = actualTwo.getX();
		double y1 = actualOne.getY();
		double y2 = actualTwo.getY();
		double m = (y2 - y1) / (x2 - x1);
		double xShift = shift * Math.sqrt(1 / (1 + 1 / Math.pow(m, 2)));
		double yShift = xShift / m;
		Point2D.Double returnOne = new Point2D.Double(actualOne.getX() + xShift * SHIFT,
				actualOne.getY() - yShift * SHIFT);
		Point2D.Double returnTwo = new Point2D.Double(actualTwo.getX() + xShift * SHIFT,
				actualTwo.getY() - yShift * SHIFT);
		return new Line2D.Double(returnOne, returnTwo);
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;

		for (Path path : pathArray) {
			drawPath(g2, path);
		}

		for (Node node : nodeArray) {
			drawNode(g2, node);
		}
	}

	private void drawNode(Graphics2D g2, Node node) {
		double xCenter = node.getNodePoint().getX();
		double yCenter = node.getNodePoint().getY();
		Ellipse2D.Double planet = new Ellipse2D.Double(xCenter - this.PLANET_RADIUS, yCenter - this.PLANET_RADIUS,
				2 * this.PLANET_RADIUS, 2 * this.PLANET_RADIUS);
		g2.setColor(Color.BLACK);
		g2.fill(planet);

		g2.setColor(node.getNodeColor());
		g2.setStroke(new BasicStroke(PLANET_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND));
		g2.draw(planet);
		JLabel planetLabel = new JLabel(node.getAbbreviation(), JLabel.CENTER);
		planetLabel.setBounds((int) (xCenter - this.PLANET_RADIUS), (int) (yCenter - this.PLANET_RADIUS),
				(int) (2 * this.PLANET_RADIUS), (int) (2 * this.PLANET_RADIUS));
		planetLabel.setForeground(Color.CYAN);
		
		//TODO: Font size needs messed with
		planetLabel.setFont(new Font("Arial", Font.PLAIN, 16));
		this.add(planetLabel);
	}

	private void drawPath(Graphics2D g2, Path path) {
		Line2D.Double line = path.getLine();

		if (path.getOwnedColor() == null) {
			if (path.getHighlighted() == true || path.getClicked() == true) {
				drawHighlightedPath(g2, Color.CYAN, line);
			}
		} else {
			drawHighlightedPath(g2, path.getOwnedColor(), line);
		}
		
		float[] dashArray;
		if (path.getPathLength() > 1) {
			float spacing = (getLineLength(line) - (DASH_OFFSET * 2) - (path.getPathLength() * DASH_LENGTH))
					/ (path.getPathLength() - 1);

			dashArray = new float[(2 * path.getPathLength() - 1) + 4];
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
		} else {
			float spacing = (getLineLength(line) - (DASH_OFFSET * 2) - DASH_LENGTH) / (2);

			dashArray = new float[5];
			dashArray[0] = 0;
			dashArray[1] = DASH_OFFSET + spacing;
			dashArray[dashArray.length - 1] = 0;
			dashArray[dashArray.length - 2] = DASH_OFFSET + spacing;
			dashArray[2] = DASH_LENGTH;

		}

		g2.setColor(Color.CYAN);
		g2.setStroke(
				new BasicStroke(LINE_WIDTH + 2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dashArray, 0));
		g2.draw(line);

		g2.setColor(path.getPathColor());
		g2.setStroke(
				new BasicStroke(LINE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dashArray, 0));
		g2.draw(line);
	}

	private void drawHighlightedPath(Graphics2D g2, Color highlightColor, Line2D.Double line) {
		g2.setColor(highlightColor);
		float[] highlightArray = new float[5];
		highlightArray[0] = 0;
		highlightArray[4] = 0;
		highlightArray[1] = DASH_OFFSET;
		highlightArray[3] = DASH_OFFSET;
		highlightArray[2] = getLineLength(line) - 2 * DASH_OFFSET;
		
		//TODO: Font size
		g2.setStroke(new BasicStroke(HIGHTLIGHT_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.f,
				highlightArray, 0));
		g2.draw(line);
	}

	private float getLineLength(Line2D.Double line) {
		int lineWidth = (int) Math.abs(line.getX2() - line.getX1());
		int lineHeight = (int) Math.abs(line.getY2() - line.getY1());

		return (float) Math.sqrt(Math.pow(lineWidth, 2) + Math.pow(lineHeight, 2));
	}

	public void highlightCLicked(int xMouse, int yMouse) {
		if (!purchasing && !routeGetting) {
			float xBox = xMouse - LINE_WIDTH;
			float yBox = yMouse - LINE_WIDTH;
			for (Path p : pathArray) {
				if (p.getOwnedColor() == null) {
					Line2D.Double pathLine = p.getLine();
					if (pathLine.intersects(xBox, yBox, 2 * LINE_WIDTH, 2 * LINE_WIDTH)) {
						if (p.getClicked()) {
							p.setClicked(false);
						} else {
							p.setClicked(true);
							gameboard.setPurchasing(p, this);
						}
						this.purchasing = true;
						removeRevalidateRepaint();
						break;
					}
				} else {
					p.setClicked(false);
				}
			}
		}
	}

	public void checkHighlight(int xMouse, int yMouse) {
		if (!purchasing && !routeGetting) {
			float xBox = xMouse - LINE_WIDTH;
			float yBox = yMouse - LINE_WIDTH;
			boolean found = false;
			for (Path p : pathArray) {
				if (p.getOwnedColor() == null && !Game.getCurrentPlayer().isPathOwned(p)) {
					Line2D.Double pathLine = p.getLine();
					if (pathLine.intersects(xBox, yBox, 2 * LINE_WIDTH, 2 * LINE_WIDTH)) {
						if (!found) {
							p.setHighlighted(true);
							removeRevalidateRepaint();
							found = true;
						} else {
							p.setHighlighted(false);
							removeRevalidateRepaint();
							break;
						}
					} else {
						boolean tempHighlight = p.getHighlighted();
						p.setHighlighted(false);
						if (tempHighlight == true) {
							removeRevalidateRepaint();
							break;
						}
					}
				} else {
					p.setHighlighted(false);
				}
			}
		}
	}

	public void removeRevalidateRepaint() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

	public void endPurchase() {
		this.purchasing = false;
	}

	public void setRouteGetting(boolean routeGetInProcess) {
		this.routeGetting = routeGetInProcess;
	}

}
