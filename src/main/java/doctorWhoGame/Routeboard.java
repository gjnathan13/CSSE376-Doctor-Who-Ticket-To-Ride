package doctorWhoGame;

import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Routeboard extends GameComponent {

	private File routeboardBackgroundFile = new File("gameImages\\RouteBoard.png");
	private BufferedImage routeboardBackgroundImage;

	public Routeboard(PathComponent pathList) {
		try {
			this.routeboardBackgroundImage = ImageIO.read(routeboardBackgroundFile);
		} catch (IOException e) {
			if (GraphicsEnvironment.isHeadless()) {
				routeboardBackgroundImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_RGB);
			} else {
				e.printStackTrace();
			}
		}
		if (pathList != null) {
			int routeBackImageWidth = (int) (routeboardBackgroundImage.getWidth() * GameStarter.getWidthModifier());
			int routeBackImageHeight = (int) (routeboardBackgroundImage.getHeight() * GameStarter.getHeightModifier());
			pathList.setPreferredSize(new Dimension(routeBackImageWidth, routeBackImageHeight));
			pathList.setBounds(0, 0, routeboardBackgroundImage.getWidth(), routeboardBackgroundImage.getHeight());
			this.add(pathList);
		}
	}

	@Override
	protected void showGraphics() {
		int routeBackImageWidth = (int) (routeboardBackgroundImage.getWidth() * GameStarter.getWidthModifier());
		int routeBackImageHeight = (int) (routeboardBackgroundImage.getHeight() * GameStarter.getHeightModifier());
		pen.drawImage(routeboardBackgroundImage, 0, 0, routeBackImageWidth, routeBackImageHeight, null);
	}

	public int[] getRouteImageDimensions() {
		int routeBackImageWidth = (int) (routeboardBackgroundImage.getWidth() * GameStarter.getWidthModifier());
		int routeBackImageHeight = (int) (routeboardBackgroundImage.getHeight() * GameStarter.getHeightModifier());
		int[] imageDimensions = { routeBackImageWidth, routeBackImageHeight };
		return imageDimensions;
	}
}
