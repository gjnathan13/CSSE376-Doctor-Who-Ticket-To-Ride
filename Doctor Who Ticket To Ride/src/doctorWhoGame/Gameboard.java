package doctorWhoGame;

import java.awt.Color;
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
import java.util.HashMap;

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

	// for routes
	private static final int OFFSET_END_X = 400;
	private static final int ROUTE_BACK_HEIGHT = 100;
	private static final int ROUTE_BACK_WIDTH = 250;
	private static final int ROUTE_BACK_OFFSET_Y = 35;
	private static final int ROUTE_SPACING = 100;
	private static final int INITIAL_ROUTE_BACK_OFFSET_X = 250;

	private Color colorBeingBought;
	private boolean purchasing = false;
	private Path purchasePath;
	private PathComponent paths;
	private final Color[] TRAIN_COLOR_LIST = { Color.RED, Color.PINK, Color.ORANGE,
			Color.YELLOW, Color.GREEN, Color.BLUE, Color.WHITE, Color.BLACK, Color.GRAY };

	private final Font PURCHASE_FONT = new Font("ISOCTEUR", Font.BOLD, 24);
	private HashMap<Color, Integer> purchaseLabelAmounts = new HashMap<Color, Integer>();
	private boolean routeGetting;
	private boolean showRoutes = false;
	private boolean showCompletedRoutes = false;
	private int startingRouteIndex = 0;

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
		pen.drawImage(handAreaImage, 0, 0, handImageWidth, handImageHeight, null);
		if (showRoutes && !purchasing) {
			routesDisplay();
		} else {
			this.currentHand = Game.getCurrentPlayer().getHand();
			if (currentHand != null) {
				updateHandAreaImage();
			}
			if (purchasing) {
				purchaseGraphics(colorBeingBought);
			}
		}
	}

	private void routesDisplay() {
		JButton switchToRoutes = new JButton("View Trains");
		switchToRoutes.setBounds(this.getWidth() - 200, 0, 200, 20);
		this.add(switchToRoutes);
		switchToRoutes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRoutes = false;
				showCompletedRoutes = false;
				removeRevalidateRepaint();
			}

		});

		ArrayList<RouteCard> routesToShow;
		Color backColor;
		if (showCompletedRoutes) {
			routesToShow = Game.getCurrentPlayer().getHand().getCompletedRouteCards();
			backColor = Color.GREEN;

			addOppositeRouteCardAccessButton("View Uncompleted Routes", true);
		} else {
			routesToShow = Game.getCurrentPlayer().getHand().getUncompletedRouteCards();
			backColor = Color.RED;

			addOppositeRouteCardAccessButton("View Completed Routes", false);
		}
		if (startingRouteIndex > 2) {
			Rectangle previousButtonRectangle = new Rectangle(10, this.getHeight() / 2 - 25, 50, 50);
			addChangeDisplayedRoutesButton("<",-3, previousButtonRectangle);
		}
		if (startingRouteIndex < routesToShow.size() - 3) {
			Rectangle nextButtonRectangle = new Rectangle(this.getWidth() - 60, this.getHeight() / 2 - 25, 50, 50);
			addChangeDisplayedRoutesButton(">",3, nextButtonRectangle);
		}

		for (int i = startingRouteIndex; i < startingRouteIndex + 3; i++) {

			if (i < routesToShow.size() && routesToShow.get(i) != null) {
				int x = i - startingRouteIndex;
				Rectangle routeCardBackHighlight = new Rectangle(
						INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (x) + ROUTE_SPACING * (x) - 10,
						+ROUTE_BACK_OFFSET_Y - 10, ROUTE_BACK_WIDTH + 20, ROUTE_BACK_HEIGHT + 20);
				this.pen.setColor(backColor);
				this.pen.fill(routeCardBackHighlight);

				Rectangle routeCardBack = new Rectangle(
						INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (x) + ROUTE_SPACING * (x),
						+ROUTE_BACK_OFFSET_Y, ROUTE_BACK_WIDTH, ROUTE_BACK_HEIGHT);
				this.pen.setColor(Color.BLACK);
				this.pen.fill(routeCardBack);

				Node[] nodesToLabel = routesToShow.get(i).getNodes();
				String nodeName1 = nodesToLabel[0].getName();
				String nodeName2 = nodesToLabel[1].getName();
				String nodeAbbrv1 = nodesToLabel[0].getAbbreviation();
				String nodeAbbrv2 = nodesToLabel[1].getAbbreviation();

				String nodeInfo1 = "<html><div style=\"text-align: center;\">" + nodeName1 + "<br>(" + nodeAbbrv1
						+ ")<br>V<br>" + nodeName2 + "<br>(" + nodeAbbrv2 + ")</html>";

				JLabel node1Label = new JLabel(nodeInfo1, JLabel.CENTER);
				node1Label.setForeground(Color.WHITE);
				node1Label.setBounds((int) routeCardBack.getX(), (int) routeCardBack.getY(),
						(int) routeCardBack.getWidth(), (int) routeCardBack.getHeight());
				this.add(node1Label);

				JLabel routeScoreLabel = new JLabel(Integer.toString(routesToShow.get(i).getPoints()));
				routeScoreLabel.setForeground(Color.CYAN);
				routeScoreLabel.setBounds((int) (routeCardBack.getX() + routeCardBack.getWidth() * (7.0 / 8)),
						(int) (routeCardBack.getY() + routeCardBack.getHeight() * (2.0 / 3)),
						(int) (routeCardBack.getWidth() * (1.0 / 8)), (int) (routeCardBack.getHeight() * (1.0 / 3)));
				this.add(routeScoreLabel);
			}
		}
	}

	private void addChangeDisplayedRoutesButton(String changeButtonLabel, int routeIndexShift, Rectangle buttonBounds) {
		JButton newThree = new JButton(changeButtonLabel);
		newThree.setBounds((int) buttonBounds.getX(), (int) buttonBounds.getY(), (int) buttonBounds.getWidth(), (int) buttonBounds.getHeight());
		this.add(newThree);
		newThree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startingRouteIndex = startingRouteIndex + (routeIndexShift);
				removeRevalidateRepaint();
			}
		});
	}
	
	private void addOppositeRouteCardAccessButton(String buttonLabel, boolean showCompletedRoutesCheck){
		JButton switchToOtherRoutes = new JButton(buttonLabel);
		switchToOtherRoutes.setBounds(this.getWidth() - 450, 0, 250, 20);
		this.add(switchToOtherRoutes);
		switchToOtherRoutes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showCompletedRoutes = showCompletedRoutesCheck;
				startingRouteIndex = 0;
				removeRevalidateRepaint();
			}

		});
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
			Color[] colorArray = { Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.BLUE,
					Color.WHITE, Color.BLACK, Color.GRAY };
			this.colorArray = colorArray;

			for (int i = 0; i < colorArray.length; i++) {
				pen.setColor(colorArray[i]);
				pen.fillRect(CARD_SPACING_SIDE * (i + 1) + CARD_SPACE_WIDTH * (i), CARD_SPACING_TOP, CARD_SPACE_WIDTH,
						CARD_SPACE_HEIGHT);
				String cardAmountForThisColor = cardColorAmounts.get(i).toString();
				JLabel colorCards = new JLabel(cardAmountForThisColor, JLabel.CENTER);
				colorCards.setFont(new Font(CARD_AMOUNT_FONT, Font.BOLD, CARD_AMOUNT_TEXT_SIZE));
				if (!colorArray[i].equals(Color.WHITE) && !colorArray[i].equals(Color.YELLOW)) {
					colorCards.setForeground(Color.WHITE);
				}
				colorCards.setBounds(CARD_SPACE_WIDTH * (i + 1) + CARD_SPACING_SIDE * (i), CARD_SPACING_TOP,
						CARD_SPACING_SIDE, CARD_SPACING_SIDE);
				this.add(colorCards);
			}
		}

		if (!purchasing) {
			JButton switchToUncompletedRoutes = new JButton("View Uncompleted Routes");
			switchToUncompletedRoutes.setBounds(this.getWidth() - 250, 0, 250, 20);
			this.add(switchToUncompletedRoutes);
			switchToUncompletedRoutes.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					showRoutes = true;
					showCompletedRoutes = false;
					startingRouteIndex = 0;
					removeRevalidateRepaint();
				}

			});

			JButton switchToCompletedRoutes = new JButton("View Completed Routes");
			switchToCompletedRoutes.setBounds(this.getWidth() - 500, 0, 250, 20);
			this.add(switchToCompletedRoutes);
			switchToCompletedRoutes.addActionListener(new ActionListener() {

				@Override
				public void actionPerformed(ActionEvent arg0) {
					showRoutes = true;
					showCompletedRoutes = true;
					startingRouteIndex = 0;
					removeRevalidateRepaint();
				}

			});
		}

	}

	/**
	 * Puts purchase button on screen as well as arrows on applicable colors.
	 */
	private void purchaseGraphics(Color colorBeingBought) {
		ArrayList<Integer> currentCardNumbers = this.currentHand.getNumberOfTrainCards();
		ImageIcon upA = new ImageIcon(
				upArrowImage.getScaledInstance(CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3, Image.SCALE_SMOOTH));
		ImageIcon downA = new ImageIcon(
				downArrowImage.getScaledInstance(CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3, Image.SCALE_SMOOTH));

		JButton purchaseButton = new JButton("Purchase");
		purchaseButton.setBounds(0, 0, 100, 20);
		this.add(purchaseButton);
		purchaseButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				int totalCost = 0;
				int purchaseSpot = -1;
				boolean allowPurchase = true;
				if (Game.getCurrentPlayer().getTrainCount() < purchasePath.getPathLength()) {
					allowPurchase = false;
				} else {
					for (int i = 0; i < TRAIN_COLOR_LIST.length; i++) {
						int costToAdd = purchaseLabelAmounts.get(TRAIN_COLOR_LIST[i]);
						totalCost += costToAdd;
						if (costToAdd > 0) {
							if (purchaseSpot == -1) {
								purchaseSpot = i;
							} else if (i < TRAIN_COLOR_LIST.length - 1) {
								allowPurchase = false;
								break;
							}
						}
					}
				}
				if (totalCost == purchasePath.getPathLength() && allowPurchase) {
					purchasing = false;
					paths.endPurchase();
					purchasePath.setClicked(false);
					purchasePath.setHighlighted(false);
					purchasePath.setOwnedColor(Game.getCurrentPlayer().getColor());
					ArrayList<Color> removeList = new ArrayList<Color>();
					for (int i = 0; i < TRAIN_COLOR_LIST.length; i++) {
						int amountToRemove = purchaseLabelAmounts.get(TRAIN_COLOR_LIST[i]);
						for (int j = 0; j < amountToRemove; j++) {
							removeList.add(TRAIN_COLOR_LIST[i]);
						}
					}
					Game.purchasePath(removeList, purchasePath);
					removeRevalidateRepaint();
					paths.removeAll();
					paths.revalidate();
					paths.repaint();
				}
			}

		});

		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds(100, 0, 100, 20);
		this.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				purchasing = false;
				paths.endPurchase();
				purchasePath.setClicked(false);
				purchasePath.setHighlighted(false);
				removeRevalidateRepaint();
				paths.removeAll();
				paths.revalidate();
				paths.repaint();
			}

		});

		int placement = 0;
		for (int i = 0; i < this.colorArray.length; i++) {
			if (colorBeingBought.equals(this.colorArray[i])) {
				placement = i;
			}
		}
		if (placement == (colorArray.length - 1)) {
			for (int i = 0; i < this.colorArray.length - 1; i++) {
				JButton upArrowButton = new JButton(upA);
				upArrowButton.setBorder(BorderFactory.createEmptyBorder());
				upArrowButton.setBackground(colorArray[i]);
				upArrowButton.setBounds(CARD_SPACE_WIDTH * (i) + CARD_SPACING_SIDE * (i + 1) + CARD_SPACE_WIDTH / 3,
						CARD_SPACING_TOP, CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3);
				this.add(upArrowButton);

				JButton downArrowButton = new JButton(downA);
				downArrowButton.setBorder(BorderFactory.createEmptyBorder());
				downArrowButton.setBackground(colorArray[i]);
				downArrowButton.setBounds(CARD_SPACE_WIDTH * (i) + CARD_SPACING_SIDE * (i + 1) + CARD_SPACE_WIDTH / 3,
						(int) (CARD_SPACING_TOP + (2.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3,
						CARD_SPACE_WIDTH / 3);
				this.add(downArrowButton);

				int maxAllowed = currentCardNumbers.get(i);
				Color colorWatching = TRAIN_COLOR_LIST[i];

				PurchaseLabel purchaseCount = new PurchaseLabel(colorWatching);
				if (!colorArray[i].equals(Color.WHITE) && !colorArray[i].equals(Color.YELLOW)) {
					purchaseCount.setForeground(Color.WHITE);
				}
				purchaseCount.setBounds(CARD_SPACE_WIDTH * (i) + CARD_SPACING_SIDE * (i + 1) + CARD_SPACE_WIDTH / 3,
						(int) (CARD_SPACING_TOP + (1.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3,
						CARD_SPACE_WIDTH / 3);
				this.add(purchaseCount);

				upArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, 1, maxAllowed));
				downArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, -1, maxAllowed));

			}
		} else {
			JButton upArrowButton = new JButton(upA);
			upArrowButton.setBorder(BorderFactory.createEmptyBorder());
			upArrowButton.setBackground(colorArray[placement]);
			upArrowButton.setBounds(
					CARD_SPACE_WIDTH * (placement) + CARD_SPACING_SIDE * (placement + 1) + CARD_SPACE_WIDTH / 3,
					CARD_SPACING_TOP, CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3);
			this.add(upArrowButton);

			JButton downArrowButton = new JButton(downA);
			downArrowButton.setBorder(BorderFactory.createEmptyBorder());
			downArrowButton.setBackground(colorArray[placement]);
			downArrowButton.setBounds(
					CARD_SPACE_WIDTH * (placement) + CARD_SPACING_SIDE * (placement + 1) + CARD_SPACE_WIDTH / 3,
					(int) (CARD_SPACING_TOP + (2.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3,
					CARD_SPACE_WIDTH / 3);
			this.add(downArrowButton);

			int maxAllowed = currentCardNumbers.get(placement);
			Color colorWatching = TRAIN_COLOR_LIST[placement];

			PurchaseLabel purchaseCount = new PurchaseLabel(colorWatching);
			if (!colorArray[placement].equals(Color.WHITE) && !colorArray[placement].equals(Color.YELLOW)) {
				purchaseCount.setForeground(Color.WHITE);
			}
			purchaseCount.setBounds(
					CARD_SPACE_WIDTH * (placement) + CARD_SPACING_SIDE * (placement + 1) + CARD_SPACE_WIDTH / 3,
					(int) (CARD_SPACING_TOP + (1.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3,
					CARD_SPACE_WIDTH / 3);
			this.add(purchaseCount);

			upArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, 1, maxAllowed));
			downArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, -1, maxAllowed));
		}

		JButton upArrowButton = new JButton(upA);
		upArrowButton.setBorder(BorderFactory.createEmptyBorder());
		upArrowButton.setBackground(Color.GRAY);
		upArrowButton.setBounds(CARD_SPACE_WIDTH * (this.colorArray.length - 1)
				+ CARD_SPACING_SIDE * (this.colorArray.length) + CARD_SPACE_WIDTH / 3, CARD_SPACING_TOP,
				CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3);
		this.add(upArrowButton);

		JButton downArrowButton = new JButton(downA);
		downArrowButton.setBorder(BorderFactory.createEmptyBorder());
		downArrowButton.setBackground(Color.GRAY);
		downArrowButton.setBounds(
				CARD_SPACE_WIDTH * (this.colorArray.length - 1) + CARD_SPACING_SIDE * (this.colorArray.length)
						+ CARD_SPACE_WIDTH / 3,
				(int) (CARD_SPACING_TOP + (2.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3);
		this.add(downArrowButton);

		int maxAllowed = currentCardNumbers.get(this.colorArray.length - 1);
		Color colorWatching = TRAIN_COLOR_LIST[this.colorArray.length - 1];

		PurchaseLabel purchaseCount = new PurchaseLabel(colorWatching);
		if (!colorArray[this.colorArray.length - 1].equals(Color.WHITE)
				&& !colorArray[this.colorArray.length - 1].equals(Color.YELLOW)) {
			purchaseCount.setForeground(Color.WHITE);
		}
		purchaseCount.setBounds(
				CARD_SPACE_WIDTH * (this.colorArray.length - 1) + CARD_SPACING_SIDE * (this.colorArray.length)
						+ CARD_SPACE_WIDTH / 3,
				(int) (CARD_SPACING_TOP + (1.0 / 3) * CARD_SPACE_HEIGHT), CARD_SPACE_WIDTH / 3, CARD_SPACE_WIDTH / 3);
		this.add(purchaseCount);

		upArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, 1, maxAllowed));
		downArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, -1, maxAllowed));

	}

	/**
	 * Puts gameboard in purchasing state
	 * 
	 * @param pathComponent
	 */
	public void setPurchasing(Path p, PathComponent pathComponent) {
		this.colorBeingBought = p.getPathColor();
		this.purchasePath = p;
		this.purchasing = true;
		this.paths = pathComponent;
		for (int i = 0; i < this.TRAIN_COLOR_LIST.length; i++) {
			this.purchaseLabelAmounts.put(TRAIN_COLOR_LIST[i], 0);
		}
		removeRevalidateRepaint();
	}

	private void removeRevalidateRepaint() {
		this.removeAll();
		this.revalidate();
		this.repaint();
	}

	private class PurchaseLabel extends JLabel {

		private Color trainColor;

		PurchaseLabel(Color t) {
			this.trainColor = t;
			this.setText(Integer.toString(purchaseLabelAmounts.get(this.trainColor)));
			this.setHorizontalAlignment(JLabel.CENTER);
			this.setFont(PURCHASE_FONT);
		}

		public Color getLabelColor() {
			return this.trainColor;
		}
	}

	private class PurchaseArrowListener implements ActionListener {

		private PurchaseLabel labelControlling;
		private int upOrDown = 0;
		private int maxAllowed;

		PurchaseArrowListener(PurchaseLabel labelControlling, int upOrDown, int maxAllowed) {
			this.labelControlling = labelControlling;
			this.upOrDown = upOrDown;
			this.maxAllowed = maxAllowed;
		}

		@Override
		public void actionPerformed(ActionEvent arg0) {
			int currentAmount = purchaseLabelAmounts.get(labelControlling.getLabelColor());
			if (upOrDown == 1 && currentAmount < maxAllowed) {
				currentAmount++;
			} else if (upOrDown == -1 && currentAmount > 0) {
				currentAmount--;
			}
			purchaseLabelAmounts.put(labelControlling.getLabelColor(), currentAmount);
			labelControlling.setText(Integer.toString(currentAmount));
			removeRevalidateRepaint();
		}

	}

	public boolean getPurchasing() {
		return this.purchasing;
	}

	public void resetOnNewPlayer() {
		this.showRoutes = false;
		this.showCompletedRoutes = false;
		this.startingRouteIndex = 0;
	}

}
