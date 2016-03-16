package doctorWhoGame;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Routeboard extends JPanel {

	private File routeBackFile = new File("GameImages\\RouteBoard.png");
	private BufferedImage routeBackImage;

	public Routeboard(PathComponent pathList) {
		try {
			this.routeBackImage = ImageIO.read(routeBackFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (pathList != null) {
			pathList.setPreferredSize(new Dimension(routeBackImage.getWidth(), routeBackImage.getHeight()));
			pathList.setBounds(0, 0, routeBackImage.getWidth(), routeBackImage.getHeight());
			this.add(pathList);
		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(routeBackImage, 0, 0, routeBackImage.getWidth(), routeBackImage.getHeight(), null);
	}

	public int[] getRouteImageDimensions() {
		int[] imageDimensions = { routeBackImage.getWidth(), routeBackImage.getHeight() };
		return imageDimensions;
	}
}
