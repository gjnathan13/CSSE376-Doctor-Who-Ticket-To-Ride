package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Routeboard extends JPanel {

	private File routeBackFile = new File("GameImages\\Routeboard.png");
	private BufferedImage routeBackImage;
	private PathComponent[] pathList;

	Routeboard(PathComponent[] pathList) {
		try {
			this.routeBackImage = ImageIO.read(routeBackFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (PathComponent p : pathList) {
			this.add(p);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(routeBackImage, 0, 0, routeBackImage.getWidth(),
				routeBackImage.getHeight(), null);
	}

	public int[] getRouteImageDimensions() {
		int[] imageDimensions = { routeBackImage.getWidth(),
				routeBackImage.getHeight() };
		return imageDimensions;
	}
}
