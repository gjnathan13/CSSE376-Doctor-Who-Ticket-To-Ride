package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class RouteChoosingComponent extends JComponent{
	
	private Graphics2D pen;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		this.pen.fillRect(0,0,this.getWidth(),this.getHeight());
	}

}
