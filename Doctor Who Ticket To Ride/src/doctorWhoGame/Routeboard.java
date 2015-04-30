package doctorWhoGame;

import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

public class Routeboard extends Panel {
	
	private File routeBackFile = new File("GameImages\\Routeboard.png");
	private BufferedImage routeBackImage;
	private Path[] pathList;
	
	Routeboard(Path[] pathList){
		try {
			this.routeBackImage = ImageIO.read(routeBackFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		ImageIcon routeIcon = new ImageIcon(this.routeBackImage);
		JLabel routeImage = new JLabel(routeIcon);
		this.add(routeImage);
	}

	public int[] getRouteImageDimensions() {
		int[] imageDimensions = {routeBackImage.getWidth(), routeBackImage.getHeight()};
		return imageDimensions;
	}
}
