package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

@SuppressWarnings("serial")
public abstract class GameComponent extends JComponent {

	protected Graphics2D pen;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		showGraphics();
	}

	protected abstract void showGraphics();

	protected void removeRevalidateRepaint() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}
	
}
