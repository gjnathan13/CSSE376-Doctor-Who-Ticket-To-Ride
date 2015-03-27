package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class Gameboard extends JComponent {
	private BufferedImage backgroundImage;

	public Gameboard(BufferedImage background) {
		this.backgroundImage = background;

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
		graphics2.drawImage(this.backgroundImage, 0, 0,
				this.backgroundImage.getWidth(),
				this.backgroundImage.getHeight(), null);
	}

}
