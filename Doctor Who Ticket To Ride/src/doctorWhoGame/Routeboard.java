package doctorWhoGame;

import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JLabel;

public class Routeboard extends Panel {
	
	private File routeBackFile = new File("GameImages\\Routeboard.png");
	private BufferedImage routeBackImage;
	
	Routeboard(){
		try {
			this.routeBackImage = ImageIO.read(routeBackFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.add(new JLabel());
	}
}
