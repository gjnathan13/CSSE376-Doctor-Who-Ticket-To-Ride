package doctorWhoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JLabel;


public class Gameboard extends JComponent {
	private File handAreaFile = new File("GameImages\\CardLaySpace.png") ;
	private BufferedImage handAreaImage;
	private int handImageWidth;
	private int handImageHeight;
	private Hand currentHand;
	private Graphics2D pen;
	
	final private int CARD_SPACE_WIDTH = 140;
	final private int CARD_SPACE_HEIGHT = 250;
	final private int CARD_SPACING_SIDE = 30;
	final private int CARD_SPACING_TOP = 75;
	final private String CARD_AMOUNT_FONT = "Arial";
	final private int CARD_AMOUNT_TEXT_SIZE = 20;

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
		if(this.currentHand != null){
			updateHandAreaImage();
		}
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
		Color[] colorArray = {Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE, Color.BLACK, Color.GRAY};
		
		for(int i=0; i < colorArray.length; i++){
			pen.setColor(colorArray[i]);
			pen.fillRect(CARD_SPACING_SIDE*(i+1) + CARD_SPACE_WIDTH*(i),CARD_SPACING_TOP,CARD_SPACE_WIDTH,CARD_SPACE_HEIGHT);
			JLabel colorCards = new JLabel("1", JLabel.CENTER);
			colorCards.setFont(new Font(CARD_AMOUNT_FONT, Font.BOLD, CARD_AMOUNT_TEXT_SIZE));
			if(!colorArray[i].equals(Color.WHITE) && !colorArray[i].equals(Color.YELLOW)){
				colorCards.setForeground(Color.WHITE);
			}
			colorCards.setBounds(CARD_SPACE_WIDTH*(i+1) + CARD_SPACING_SIDE*(i), CARD_SPACING_TOP, CARD_SPACING_SIDE, CARD_SPACING_SIDE);
			this.add(colorCards);
		}
	}

}
