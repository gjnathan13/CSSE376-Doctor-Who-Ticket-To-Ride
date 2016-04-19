package doctorWhoGame;

import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

@SuppressWarnings("serial")
public class Routeboard extends GameComponent {

	private File routeBackFile = new File("gameImages\\RouteBoard.png");
	private BufferedImage routeBackImage;

	public Routeboard(PathComponent pathList) {
		try {
			this.routeBackImage = ImageIO.read(routeBackFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		if (pathList != null) {
			int routeBackImageWidth = (int) (routeBackImage.getWidth()*GameStarter.getWidthModifier());
			int routeBackImageHeight = (int) (routeBackImage.getHeight()*GameStarter.getHeightModifier());
			pathList.setPreferredSize(new Dimension(routeBackImageWidth, routeBackImageHeight));
			pathList.setBounds(0, 0, routeBackImage.getWidth(), routeBackImage.getHeight());
			this.add(pathList);
		}
	}

	@Override
	protected void showGraphics(){
		int routeBackImageWidth = (int) (routeBackImage.getWidth()*GameStarter.getWidthModifier());
		int routeBackImageHeight = (int) (routeBackImage.getHeight()*GameStarter.getHeightModifier());
		pen.drawImage(routeBackImage, 0, 0, routeBackImageWidth, routeBackImageHeight, null);
	}

	public int[] getRouteImageDimensions() {
		int routeBackImageWidth = (int) (routeBackImage.getWidth()*GameStarter.getWidthModifier());
		int routeBackImageHeight = (int) (routeBackImage.getHeight()*GameStarter.getHeightModifier());
		int[] imageDimensions = { routeBackImageWidth, routeBackImageHeight };
		return imageDimensions;
	}
}
