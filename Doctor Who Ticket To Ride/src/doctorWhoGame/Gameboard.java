package doctorWhoGame;

import java.awt.Color;
import java.awt.Font;
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
import javax.swing.JLabel;

/**
 * Main space in which game play occurs.
 * 
 * @author nathangj
 * 
 */
@SuppressWarnings("serial")
public class Gameboard extends GameComponent {
	private File handAreaFile = new File("GameImages\\CardLaySpace.png");
	private File upArrowFile = new File("GameImages\\upArrow.png");
	private File downArrowFile = new File("GameImages\\downArrow.png");

	private BufferedImage handAreaImage;
	private BufferedImage upArrowImage;
	private BufferedImage downArrowImage;

	private int handImageWidth;
	private int handImageHeight;
	private Hand currentHand;

	final private int CARD_SPACE_WIDTH = (int) (110 * GameStarter.getWidthModifier());
	final private int CARD_SPACE_HEIGHT = (int) (110 * GameStarter.getHeightModifier());
	final private int CARD_SPACING_SIDE = (int) (30 * GameStarter.getWidthModifier());
	final private int CARD_SPACING_TOP = (int) (25 * GameStarter.getHeightModifier());
	final private String CARD_AMOUNT_FONT = "Arial";
	final private int CARD_AMOUNT_TEXT_SIZE = (int) (20 * GameStarter.getDiagonalModifier());
	private Color[] colorArray;

	// for routes
	private static final int ROUTE_BACK_HEIGHT = (int) (100 * GameStarter.getHeightModifier());
	private static final int ROUTE_BACK_WIDTH = (int) (250 * GameStarter.getWidthModifier());
	private static final int ROUTE_BACK_OFFSET_Y = (int) (35 * GameStarter.getHeightModifier());
	private static final int ROUTE_SPACING = (int) (100 * GameStarter.getWidthModifier());
	private static final int INITIAL_ROUTE_BACK_OFFSET_X = (int) (250 * GameStarter.getWidthModifier());

	private Color colorBeingBought;
	private boolean purchasing = false;
	private Path purchasePath;
	private PathComponent paths;
	private final Color[] TRAIN_COLOR_LIST = { Color.RED, Color.PINK, Color.ORANGE, Color.YELLOW, Color.GREEN,
			Color.BLUE, Color.WHITE, Color.BLACK, Color.GRAY };

	private final Font PURCHASE_FONT = new Font("ISOCTEUR", Font.BOLD, (int) (24 * GameStarter.getDiagonalModifier()));
	private HashMap<Color, Integer> purchaseLabelAmounts = new HashMap<Color, Integer>();
	private boolean showRoutes = false;
	private boolean showCompletedRoutes = false;
	private int startingRouteIndex = 0;
	private ArrayList<RouteCard> routesToShow = new ArrayList<RouteCard>();;
	private Color backColor;

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
		this.handImageWidth = (int) (this.handAreaImage.getWidth() * GameStarter.getWidthModifier());
		this.handImageHeight = (int) (this.handAreaImage.getHeight() * GameStarter.getHeightModifier());
	}

	@Override
	protected void showGraphics() {
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
	
	

	public void routesDisplay() {
		makeViewTrainsButton();
		if (showCompletedRoutes) {
			routesToShow = Game.getCurrentPlayer().getHand().getCompletedRouteCards();
			backColor = Color.GREEN;
			makeCompletedRoutes();
		} else {
			routesToShow = Game.getCurrentPlayer().getHand().getUncompletedRouteCards();
			backColor = Color.RED;
			makeUncompletedRoutes();
		}
		addPurchasePathInstructions();
		if (startingRouteIndex > 2) {
			makePreviousButtonRectangle();
		}
		if (startingRouteIndex < routesToShow.size() - 3) {
			makeNextButtonRectangle();
		}
		for (int i = startingRouteIndex; i < startingRouteIndex + 3; i++) {
			if (i < routesToShow.size() && routesToShow.get(i) != null) {
				int x = i - startingRouteIndex;
				makeRouteCardBackHighlight(backColor, x);
				Rectangle routeCardBack = makeRouteCardBack(x);
				Node[] nodesToLabel = routesToShow.get(i).getNodes();
				String nodeName1 = nodesToLabel[0].getName();
				String nodeName2 = nodesToLabel[1].getName();
				String nodeAbbrv1 = nodesToLabel[0].getAbbreviation();
				String nodeAbbrv2 = nodesToLabel[1].getAbbreviation();
				String nodeInfo1 = "<html><div style=\"text-align: center;\">" + nodeName1 + "<br>(" + nodeAbbrv1
						+ ")<br>V<br>" + nodeName2 + "<br>(" + nodeAbbrv2 + ")</html>";
				makeNodeLabel(routeCardBack, nodeInfo1);
				makeRouteScoreLabel(i, routeCardBack);
			}
		}
	}
	
	public boolean isShowCompletedRoutes() {
		return showCompletedRoutes;
	}

	public void setShowCompletedRoutes(boolean showCompletedRoutes) {
		this.showCompletedRoutes = showCompletedRoutes;
	}

	public Color getBackColor() {
		return backColor;
	}

	private void makeRouteScoreLabel(int i, Rectangle routeCardBack) {
		JLabel routeScoreLabel = new JLabel(Integer.toString(routesToShow.get(i).getPoints()));
		routeScoreLabel.setForeground(Color.CYAN);
		routeScoreLabel.setBounds((int) (routeCardBack.getX() + routeCardBack.getWidth() * (7.0 / 8)),
				(int) (routeCardBack.getY() + routeCardBack.getHeight() * (2.0 / 3)),
				(int) (routeCardBack.getWidth() * (1.0 / 8)), (int) (routeCardBack.getHeight() * (1.0 / 3)));
		this.add(routeScoreLabel);
	}

	private void makeNodeLabel(Rectangle routeCardBack, String nodeInfo1) {
		JLabel node1Label = new JLabel(nodeInfo1, JLabel.CENTER);
		node1Label.setForeground(Color.WHITE);
		node1Label.setBounds((int) routeCardBack.getX(), (int) routeCardBack.getY(),
				(int) routeCardBack.getWidth(), (int) routeCardBack.getHeight());
		this.add(node1Label);
	}

	private Rectangle makeRouteCardBack(int x) {
		Rectangle routeCardBack = new Rectangle(
				INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (x) + ROUTE_SPACING * (x),
				+ROUTE_BACK_OFFSET_Y, ROUTE_BACK_WIDTH, ROUTE_BACK_HEIGHT);
		this.pen.setColor(Color.BLACK);
		this.pen.fill(routeCardBack);
		return routeCardBack;
	}

	private void makeRouteCardBackHighlight(Color backColor, int x) {
		Rectangle routeCardBackHighlight = new Rectangle(
				INITIAL_ROUTE_BACK_OFFSET_X + ROUTE_BACK_WIDTH * (x) + ROUTE_SPACING * (x)
						- (int) (10 * GameStarter.getWidthModifier()),
				+ROUTE_BACK_OFFSET_Y - (int) (10 * GameStarter.getHeightModifier()),
				ROUTE_BACK_WIDTH + (int) (20 * GameStarter.getWidthModifier()),
				ROUTE_BACK_HEIGHT + (int) (20 * GameStarter.getHeightModifier()));
		this.pen.setColor(backColor);
		this.pen.fill(routeCardBackHighlight);
	}

	private void makeNextButtonRectangle() {
		Rectangle nextButtonRectangle = new Rectangle(
				this.getWidth() - (int) ((60) * (GameStarter.getWidthModifier())),
				this.getHeight() / 2 - (int) ((25) * GameStarter.getHeightModifier()),
				(int) (50 * GameStarter.getWidthModifier()), (int) (50 * GameStarter.getHeightModifier()));
		addChangeDisplayedRoutesButton(">", 3, nextButtonRectangle);
	}

	private void makePreviousButtonRectangle() {
		Rectangle previousButtonRectangle = new Rectangle((int) (10 * (GameStarter.getWidthModifier())),
				this.getHeight() / 2 - (int) ((25) * GameStarter.getHeightModifier()),
				(int) (50 * GameStarter.getWidthModifier()), (int) (50 * GameStarter.getHeightModifier()));
		addChangeDisplayedRoutesButton("<", -3, previousButtonRectangle);
	}

	private void makeViewTrainsButton() {
		Rectangle viewTrainsButtonRectangle = new Rectangle(
				this.getWidth() - (int) ((200) * (GameStarter.getWidthModifier())), 0,
				(int) (200 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		addRouteCardAccessButton("View Trains", false, false, viewTrainsButtonRectangle);
	}

	private void makeUncompletedRoutes() {
		Rectangle uncompletedRoutesButtonRectangle = new Rectangle(
				this.getWidth() - (int) ((450) * (GameStarter.getWidthModifier())), 0,
				(int) (250 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		addRouteCardAccessButton("View Completed Routes", true, true, uncompletedRoutesButtonRectangle);
	}

	private void makeCompletedRoutes() {
		Rectangle completedRoutesButtonRectangle = new Rectangle(
				this.getWidth() - (int) ((450) * (GameStarter.getWidthModifier())), 0,
				(int) (250 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		addRouteCardAccessButton("View Uncompleted Routes", true, false, completedRoutesButtonRectangle);
	}

	private void addChangeDisplayedRoutesButton(String changeButtonLabel, int routeIndexShift, Rectangle buttonBounds) {
		JButton newThree = new JButton(changeButtonLabel);
		newThree.setBounds((int) buttonBounds.getX(), (int) buttonBounds.getY(), (int) buttonBounds.getWidth(),
				(int) buttonBounds.getHeight());
		this.add(newThree);
		newThree.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				startingRouteIndex = startingRouteIndex + (routeIndexShift);
				removeRevalidateRepaint();
			}
		});
	}

	private void addRouteCardAccessButton(String buttonLabel, boolean showRoutesCheck, boolean showCompletedRoutesCheck,
			Rectangle boundingRectangle) {
		JButton switchToOtherRoutes = new JButton(buttonLabel);
		switchToOtherRoutes.setBounds((int) boundingRectangle.getX(), (int) boundingRectangle.getY(),
				(int) boundingRectangle.getWidth(), (int) boundingRectangle.getHeight());
		this.add(switchToOtherRoutes);
		switchToOtherRoutes.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				showRoutes = showRoutesCheck;
				showCompletedRoutes = showCompletedRoutesCheck;
				startingRouteIndex = 0;
				removeRevalidateRepaint();
			}

		});
	}

	/**
	 * Getter for the field showRoutes
	 * @return
	 */
	public boolean isShowRoutes() {
		return showRoutes;
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
			this.colorArray = TRAIN_COLOR_LIST;

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
			Rectangle completedRoutesButtonRectangle = new Rectangle(
					this.getWidth() - (int) ((250) * (GameStarter.getWidthModifier())), 0,
					(int) (250 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
			addRouteCardAccessButton("View Uncompleted Routes", true, false, completedRoutesButtonRectangle);

			Rectangle uncompletedRoutesButtonRectangle = new Rectangle(
					this.getWidth() - (int) ((500) * (GameStarter.getWidthModifier())), 0,
					(int) (250 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
			addRouteCardAccessButton("View Completed Routes", true, true, uncompletedRoutesButtonRectangle);
			
			addPurchasePathInstructions();
		}

	}

	
	
	/**
	 * Puts purchase button on screen as well as arrows on applicable colors.
	 */
	protected void purchaseGraphics(Color colorBeingBought) {
		int placement = 0;
		for (int i = 0; i < this.colorArray.length; i++) {
			if (colorBeingBought.equals(this.colorArray[i])) {
				placement = i;
			}
		}

		if (placement == (colorArray.length - 1)) {
			for (int i = 0; i < this.colorArray.length; i++) {
				addArrowButtons(i);
			}
		} else {
			addArrowButtons(placement);
			addArrowButtons(this.colorArray.length - 1);
		}

		addPurchaseButton();
		addCancelButton();
		addPurchasingInstructions();
	}

	public void setColorArray(Color[] colorArray) {
		this.colorArray = colorArray;
	}

	protected void addArrowButtons(int buyingIndex) {
		int upArrowVerticalPlacement = CARD_SPACING_TOP;
		int downArrowVerticalPlacement = (int) (CARD_SPACING_TOP + (2.0 / 3) * CARD_SPACE_HEIGHT);
		int purchaseWidth = CARD_SPACE_WIDTH / 3;

		ArrayList<Integer> currentCardNumbers = this.currentHand.getNumberOfTrainCards();
		ImageIcon upA = new ImageIcon(upArrowImage.getScaledInstance(purchaseWidth, purchaseWidth, Image.SCALE_SMOOTH));
		ImageIcon downA = new ImageIcon(
				downArrowImage.getScaledInstance(purchaseWidth, purchaseWidth, Image.SCALE_SMOOTH));

		Rectangle upArrowBoundingBox = new Rectangle(getHorizontalPurchasingPlacement(buyingIndex),
				upArrowVerticalPlacement, purchaseWidth, purchaseWidth);
		JButton upArrowButton = addArrowButton(upA, colorArray[buyingIndex], upArrowBoundingBox);

		Rectangle downArrowBoundingBox = new Rectangle(getHorizontalPurchasingPlacement(buyingIndex),
				downArrowVerticalPlacement, purchaseWidth, purchaseWidth);
		JButton downArrowButton = addArrowButton(downA, colorArray[buyingIndex], downArrowBoundingBox);

		PurchaseLabel purchaseCount = addPurchaseLabel(buyingIndex);

		int maxAllowed = currentCardNumbers.get(buyingIndex);

		upArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, 1, maxAllowed,this));
		downArrowButton.addActionListener(new PurchaseArrowListener(purchaseCount, -1, maxAllowed,this));
	}

	protected PurchaseLabel addPurchaseLabel(int colorIndex) {
		int purchasingCountVerticalPlacement = (int) (CARD_SPACING_TOP + (1.0 / 3) * CARD_SPACE_HEIGHT);
		int purchaseWidth = CARD_SPACE_WIDTH / 3;

		Color colorWatching = TRAIN_COLOR_LIST[colorIndex];

		PurchaseLabel purchaseCount = new PurchaseLabel(colorWatching, this);
		if (!colorArray[colorIndex].equals(Color.WHITE) && !colorArray[colorIndex].equals(Color.YELLOW)) {
			purchaseCount.setForeground(Color.WHITE);
		}
		purchaseCount.setBounds(getHorizontalPurchasingPlacement(colorIndex), purchasingCountVerticalPlacement,
				purchaseWidth, purchaseWidth);
		this.add(purchaseCount);

		return purchaseCount;
	}

	protected void addCancelButton() {
		JButton cancelButton = new JButton("Cancel");
		cancelButton.setBounds((int) (100 * GameStarter.getWidthModifier()), 0,
				(int) (100 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		this.add(cancelButton);
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				endPurchasing();
				removeRevalidateRepaint();
				paths.removeRevalidateRepaint();
			}

		});
	}

	protected void addPurchaseButton() {
		JButton purchaseButton = new JButton("Purchase");
		purchaseButton.setBounds(0, 0, (int) (100 * GameStarter.getWidthModifier()),
				(int) (20 * GameStarter.getHeightModifier()));
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
					endPurchasing();
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
					paths.removeRevalidateRepaint();
				}
			}

		});
	}

	protected void addPurchasingInstructions() {
		JLabel instructions = new JLabel(
				"Select the correct number of trains to use, then click 'Purchase'.  Click 'Cancel' to stop attempting to buy a path.");
		instructions.setBounds((int) (200 * GameStarter.getWidthModifier()), 0,
				(int) (800 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		this.add(instructions);
	}

	private void addPurchasePathInstructions() {
		JLabel instructions = new JLabel("Click on a path above to attempt to purchase it.");
		instructions.setBounds((int) (200 * GameStarter.getWidthModifier()), 0,
				(int) (400 * GameStarter.getWidthModifier()), (int) (20 * GameStarter.getHeightModifier()));
		this.add(instructions);

	}

	private int getHorizontalPurchasingPlacement(int locationIndex) {
		return (CARD_SPACE_WIDTH * (locationIndex) + CARD_SPACING_SIDE * (locationIndex + 1) + (CARD_SPACE_WIDTH / 3));
	}

	private JButton addArrowButton(ImageIcon arrowImage, Color backgroundColor, Rectangle arrowBounds) {
		JButton arrowButton = new JButton(arrowImage);
		arrowButton.setBorder(BorderFactory.createEmptyBorder());
		arrowButton.setBackground(backgroundColor);
		arrowButton.setBounds((int) arrowBounds.getX(), (int) arrowBounds.getY(), (int) arrowBounds.getWidth(),
				(int) arrowBounds.getHeight());
		this.add(arrowButton);
		return arrowButton;
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

	public boolean getPurchasing() {
		return this.purchasing;
	}

	public void resetOnNewPlayer() {
		this.showRoutes = false;
		this.showCompletedRoutes = false;
		this.startingRouteIndex = 0;
	}

	private void endPurchasing() {
		purchasing = false;
		paths.endPurchase();
		purchasePath.setClicked(false);
		purchasePath.setHighlighted(false);
	}
	
	public HashMap<Color, Integer> getPurchaseLabelAmounts(){
		return this.purchaseLabelAmounts;
	}

	public Font getPurchaseFont() {
		return this.PURCHASE_FONT;
	}

}
