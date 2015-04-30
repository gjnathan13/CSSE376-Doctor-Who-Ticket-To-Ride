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
	private final float DASH_LENGTH = 50;
	private final float DASH_OFFSET = 10;
	private Path path;
	
	public PathComponent(Path p){
		this.path = p;
	}
	
	@Override
	protected void paintComponent(Graphics g){
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(path.getPathColor());
		Line2D.Double line = new Line2D.Double(path.getNodes()[0].getNodePoint(), path.getNodes()[1].getNodePoint());
		int lineWidth = (int) Math.abs(line.getX2() - line.getX1());
		int lineHeight = (int) Math.abs(line.getY2() - line.getY1());
		this.setPreferredSize(new Dimension(lineWidth, lineHeight));
		int xSmall = Math.min((int) line.getX2(), (int) line.getX1());
		int ySmall = Math.min((int) line.getY2(), (int) line.getY1());
		this.setBounds(xSmall, ySmall, lineWidth+75, lineHeight+75);
		
		float lineLength = (float) Math.sqrt(Math.pow(lineWidth, 2) + Math.pow(lineHeight, 2));
		float spacing = (lineLength - (DASH_OFFSET*2) - path.getPathLength()*(DASH_LENGTH))/(path.getPathLength()-1);
		
		float[] dashArray = {50, spacing};
		g2.setStroke(new BasicStroke(LINE_WIDTH, BasicStroke.CAP_BUTT, BasicStroke.JOIN_ROUND, 10.0f, dashArray, 10.0f));
		
		g2.draw(line);
		
	}
	
	public void highlightHover() {
		// TODO Auto-generated method stub
		
	}

	public void highlightCLicked() {
		// TODO Auto-generated method stub
		
	}

	public void unhighlight() {
		// TODO Auto-generated method stub
		
	}

}
