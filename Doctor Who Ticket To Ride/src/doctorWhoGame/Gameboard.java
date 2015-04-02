package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;

public class Gameboard extends JComponent {
	private File handAreaFile = new File("GameImages\\CardLaySpace.png") ;
	private BufferedImage handAreaImage;
	private int handImageWidth;
	private int handImageHeight;
	private Hand currentHand;
	private Graphics2D pen;

	public Gameboard() {
		try {
			this.handAreaImage = ImageIO.read(handAreaFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
		this.handImageWidth = this.handAreaImage.getWidth();
		this.handImageHeight = this.handAreaImage.getHeight();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		pen.drawImage(handAreaImage, 0, 0, handImageWidth, handImageHeight, null);
	}

	public int[] getHandImageDimensions() {
		int[] handImageDimensions = {this.handImageWidth, this.handImageHeight};
		return handImageDimensions;
	}

	public void setHand(Hand newHand) {
		this.currentHand = newHand;
	}
	
	public void updateHandAreaImage(){
		currentHand.getNumberOfEachColor();
	}

}
