package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Gameboard extends JComponent {
	private File handAreaFile = new File("") ;
	private BufferedImage handAreaImage = new BufferedImage(1, 1, 1);

	public Gameboard() {
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D graphics2 = (Graphics2D) g;
	}

}
