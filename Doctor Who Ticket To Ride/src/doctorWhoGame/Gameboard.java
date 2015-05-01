package doctorWhoGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * Main space in which game play occurs.
 * 
 * @author nathangj
 * 
 */
@SuppressWarnings("serial")
public class Gameboard extends JComponent {
	private File handAreaFile = new File("GameImages\\CardLaySpace.png");
	private File upArrowFile = new File("GameImages\\upArrow.png");
	private File downArrowFile = new File("GameImages\\downArrow.png");
	
	private BufferedImage handAreaImage;
	private BufferedImage upArrowImage;
	private BufferedImage downArrowImage;
	
	private int handImageWidth;
	private int handImageHeight;
	private Hand currentHand;
	private Graphics2D pen;

	final private int CARD_SPACE_WIDTH = 110;
	final private int CARD_SPACE_HEIGHT = 110;
	final private int CARD_SPACING_SIDE = 30;
	final private int CARD_SPACING_TOP = 25;
	final private String CARD_AMOUNT_FONT = "Arial";
	final private int CARD_AMOUNT_TEXT_SIZE = 20;
	private Color[] colorArray;
	
	private Color colorBeingBought;
	private boolean purchasing;
	private Path purchasePath;
	

	/**
	 * Default constructor, reads the designated files to generate the correct
	 * images for the board. Stores the height and width of these images for use
	 * in creating a properly sized GUI.
	 */
	public Gameboard() {
		try {
			this.handAreaImage = ImageIO.read(handAreaFile);
			this.upArrowImage = ImageIO.read(upArrowFile);
			this.downArrowImage = ImageIO.read(downArrowFile);
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
		pen.drawImage(handAreaImage, 0, 0, handImageWidth, handImageHeight,
				null);
		if (this.currentHand != null) {
			updateHandAreaImage();
		}
		if(purchasing){
			purchaseGraphics(colorBeingBought);
		}
	}

	/**
	 * @return and int[] with the dimensions of the image on which the hand is
	 *         displayed.
	 */
	public int[] getHandImageDimensions() {
		int[] handImageDimensions = { this.handImageWidth, this.handImageHeight };
		return handImageDimensions;
	}

	/**
	 * Set the currentHand being played/ to be displayed.
	 * 
	 * @param newHand
	 *            , hand to set as current
	 */
	public void setHand(Hand newHand) {
		this.currentHand = newHand;
	}

	/**
	 * Updates the hand display with current amounts of each color card.
	 */
	public void updateHandAreaImage() {
		this.removeAll();
		ArrayList<Integer> cardColorAmounts = currentHand.getNumberOfTrainCards();
		if (pen != null) {
			Color[] colorArray = { Color.RED, Color.PINK, Color.ORANGE,
					Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE,
					Color.BLACK, Color.GRAY };
			this.colorArray = colorArray;

			for (int i = 0; i < colorArray.length; i++) {
				pen.setColor(colorArray[i]);
				pen.fillRect(CARD_SPACING_SIDE * (i + 1) + CARD_SPACE_WIDTH
						* (i), CARD_SPACING_TOP, CARD_SPACE_WIDTH,
						CARD_SPACE_HEIGHT);
				String cardAmountForThisColor = cardColorAmounts.get(i).toString();
				JLabel colorCards = new JLabel(cardAmountForThisColor, JLabel.CENTER);
				colorCards.setFont(new Font(CARD_AMOUNT_FONT, Font.BOLD,
						CARD_AMOUNT_TEXT_SIZE));
				if (!colorArray[i].equals(Color.WHITE)
						&& !colorArray[i].equals(Color.YELLOW)) {
					colorCards.setForeground(Color.WHITE);
				}
				colorCards.setBounds(CARD_SPACE_WIDTH * (i + 1)
						+ CARD_SPACING_SIDE * (i), CARD_SPACING_TOP,
						CARD_SPACING_SIDE, CARD_SPACING_SIDE);
				this.add(colorCards);
			}
		}
	}
	
	/**
	 * Puts purchase button on screen as well as arrows on applicable colors.
	 */
	private void purchaseGraphics(Color colorBeingBought){
		ImageIcon upA = new ImageIcon(upArrowImage.getScaledInstance(CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3, Image.SCALE_SMOOTH));
		ImageIcon downA = new ImageIcon(downArrowImage.getScaledInstance(CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3, Image.SCALE_SMOOTH));
		
		JButton purchaseButton = new JButton("Purchase");
		purchaseButton.setBounds(0, 0, 100, 20);
		this.add(purchaseButton);
		purchaseButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				purchasing = false;
				purchasePath.setClicked(false);
				repaint();
			}
			
		});
		
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100,0,100,20);
		this.add(cancelButton);
		cancelButton.addActionListener(new ActionListener(){

			@Override
			public void actionPerformed(ActionEvent arg0) {
				purchasing = false;
				purchasePath.setClicked(false);
				repaint();
			}
			
		});
		
		
		int placement = 0;
		for(int i=0; i < this.colorArray.length; i++){
			if(colorBeingBought.equals(this.colorArray[i])){
				placement = i;
			}
		}
		if(placement == (colorArray.length-1)){
			for(int i=0; i < this.colorArray.length-1; i++){
				JButton upArrowButton = new JButton(upA);
				upArrowButton.setBorder(BorderFactory.createEmptyBorder());
				upArrowButton.setBackground(colorArray[i]);
				upArrowButton.setBounds(CARD_SPACE_WIDTH * (i)
							+ CARD_SPACING_SIDE * (i+1) + CARD_SPACE_WIDTH/3, CARD_SPACING_TOP,
							CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
				this.add(upArrowButton);
				
				JButton downArrowButton = new JButton(downA);
				downArrowButton.setBorder(BorderFactory.createEmptyBorder());
				downArrowButton.setBackground(colorArray[i]);
				downArrowButton.setBounds(CARD_SPACE_WIDTH * (i)
							+ CARD_SPACING_SIDE * (i+1) + CARD_SPACE_WIDTH/3, (int)(CARD_SPACING_TOP+(2.0/3)*CARD_SPACE_HEIGHT),
							CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
				this.add(downArrowButton);
			}
		}
		else{
			JButton upArrowButton = new JButton(upA);
			upArrowButton.setBorder(BorderFactory.createEmptyBorder());
			upArrowButton.setBackground(colorArray[placement]);
			upArrowButton.setBounds(CARD_SPACE_WIDTH * (placement)
						+ CARD_SPACING_SIDE * (placement+1) + CARD_SPACE_WIDTH/3, CARD_SPACING_TOP,
						CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
			this.add(upArrowButton);
			
			JButton downArrowButton = new JButton(downA);
			downArrowButton.setBorder(BorderFactory.createEmptyBorder());
			downArrowButton.setBackground(colorArray[placement]);
			downArrowButton.setBounds(CARD_SPACE_WIDTH * (placement)
						+ CARD_SPACING_SIDE * (placement+1) + CARD_SPACE_WIDTH/3, (int)(CARD_SPACING_TOP+(2.0/3)*CARD_SPACE_HEIGHT),
						CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
			this.add(downArrowButton);
		}
		
		JButton upArrowButton = new JButton(upA);
		upArrowButton.setBorder(BorderFactory.createEmptyBorder());
		upArrowButton.setBackground(Color.GRAY);
		upArrowButton.setBounds(CARD_SPACE_WIDTH * (this.colorArray.length-1)
					+ CARD_SPACING_SIDE * (this.colorArray.length) + CARD_SPACE_WIDTH/3, CARD_SPACING_TOP,
					CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
		this.add(upArrowButton);
		
		JButton downArrowButton = new JButton(downA);
		downArrowButton.setBorder(BorderFactory.createEmptyBorder());
		downArrowButton.setBackground(Color.GRAY);
		downArrowButton.setBounds(CARD_SPACE_WIDTH * (this.colorArray.length-1)
					+ CARD_SPACING_SIDE * (this.colorArray.length) + CARD_SPACE_WIDTH/3, (int)(CARD_SPACING_TOP+(2.0/3)*CARD_SPACE_HEIGHT),
					CARD_SPACE_WIDTH/3, CARD_SPACE_WIDTH/3);
		this.add(downArrowButton);
	}
	
	/**
	 * Puts gameboard in purchasing state
	 */
	public void setPurchasing(Path p){
		this.colorBeingBought = p.getPathColor();
		this.purchasePath = p;
		this.purchasing = true;
		this.repaint();
	}

}
