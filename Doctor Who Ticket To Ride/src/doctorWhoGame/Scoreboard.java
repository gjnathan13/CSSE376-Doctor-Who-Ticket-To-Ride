package doctorWhoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

@SuppressWarnings("serial")
public class Scoreboard extends GameComponent {

	private static final int FACE_UP_OFFSET_X = (int) (25 * GameStarter.getWidthModifier());
	private static final int FACE_UP_WIDTH = (int) (50 * GameStarter.getWidthModifier());
	private static final int FACE_UP_SPACING = (int) (25 * GameStarter.getWidthModifier());
	private static final int FACE_UP_OFFSET_Y = (int) (200 * GameStarter.getHeightModifier());
	private static final int FACE_UP_HEIGHT = (int) (80 * GameStarter.getHeightModifier());
	private static final int DECK_OFFSET_Y = (int) (25 * GameStarter.getHeightModifier());
	private static final int BOUNDING_BOX_WIDTH = (int) (10 * GameStarter.getWidthModifier());
	private Player[] playerList;
	private static final int DECK_SPACING = 300;
	private Rectangle[] faceUps = new Rectangle[5];
	private BufferedImage deckImage;
	private BufferedImage routesImage;
	private File deckFile = new File("GameImages\\TardisDeck.png");
	private File routesFile = new File("GameImages\\RouteCardDeck.png");
	protected boolean routeGetting;
	private Image deckImageSized;
	private Image routesImageSized;
	private int recentlyDrawnIndex = -1;
	private Color recentColor;
	private int faceUpCardDrawNumber = 0;

	public Scoreboard(Player[] playerList) {
		try {
			this.deckImage = ImageIO.read(deckFile);
			this.deckImageSized = deckImage.getScaledInstance(
					(int) (deckImage.getWidth() * GameStarter.getWidthModifier()),
					(int) (deckImage.getHeight() * GameStarter.getHeightModifier()), Image.SCALE_DEFAULT);
			this.routesImage = ImageIO.read(routesFile);
			this.routesImageSized = routesImage.getScaledInstance(
					(int) (routesImage.getWidth() * GameStarter.getWidthModifier()),
					(int) (routesImage.getHeight() * GameStarter.getHeightModifier()), Image.SCALE_DEFAULT);
		} catch (IOException e) {
			e.printStackTrace();
		}

		this.playerList = playerList;
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				int xMouse = arg0.getX();
				int yMouse = arg0.getY();
				checkClickedFaceUp(xMouse, yMouse);
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

	private void checkClickedFaceUp(int xPos, int yPos) {
		if (!this.routeGetting) {
			float xBox = xPos - BOUNDING_BOX_WIDTH / 2;
			float yBox = yPos - BOUNDING_BOX_WIDTH / 2;
			for (int i = 0; i < this.faceUps.length; i++) {
				if (this.faceUps[i].intersects(xBox, yBox, BOUNDING_BOX_WIDTH, BOUNDING_BOX_WIDTH)) {
					Game.chooseFaceupCardToTake(i);
				}
			}
		}

	}

	@Override
	protected void showGraphics() {
		pen.setColor(new Color(0, 0, 25, 255));
		pen.fillRect(0, 0, (int) (400 * GameStarter.getWidthModifier()), this.getHeight());
		if (playerList.length != 0) {
			for (int i = 0; i < playerList.length; i++) {
				displayPlayerInformation(playerList[i], i);
			}
		}
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.switchToNextPlayer();

				Game.getGameBoard().removeRevalidateRepaint();
				removeRevalidateRepaint();
			}

		});
		endTurnButton.setBounds((int) (100 * GameStarter.getWidthModifier()),
				(int) (900 * GameStarter.getHeightModifier()), (int) (150 * GameStarter.getWidthModifier()),
				(int) (50 * GameStarter.getHeightModifier()));
		this.add(endTurnButton);

		JButton questionButton = new JButton("?");
		questionButton.setBounds((int) (340 * GameStarter.getWidthModifier()),
				(int) (GameStarter.getHeightModifier() * 935), (int) (50 * GameStarter.getWidthModifier()),
				(int) (50 * GameStarter.getHeightModifier()));
		this.add(questionButton);
		questionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				GameStarter.openPDFInstructions();

			}

		});

		setUpDeckSpace();
	}

	private void setUpDeckSpace() {
		ArrayList<Color> currentFaceUp = Game.getCurrentFaceup();
		for (int i = 0; i < currentFaceUp.size(); i++) {
			pen.setColor(currentFaceUp.get(i));
			Rectangle card = new Rectangle(FACE_UP_OFFSET_X + FACE_UP_WIDTH * (i) + FACE_UP_SPACING * (i),
					FACE_UP_OFFSET_Y, FACE_UP_WIDTH, FACE_UP_HEIGHT);
			this.faceUps[i] = card;
			pen.fill(card);

			if (recentlyDrawnIndex == i) {
				drawGlowCircleDecoration(i);
				if (recentColor.equals(currentFaceUp.get(i)) && faceUpCardDrawNumber == 2) {
					drawGlowCircleDoubleDecoration(i);
				}
			}
		}

		JButton deckButton = new JButton(new ImageIcon(deckImageSized));
		deckButton.setBorder(BorderFactory.createEmptyBorder());
		deckButton.setBounds(FACE_UP_OFFSET_X, DECK_OFFSET_Y,
				(int) (deckImage.getWidth() * GameStarter.getWidthModifier()),
				(int) (deckImage.getHeight() * GameStarter.getHeightModifier()));
		this.add(deckButton);
		deckButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!routeGetting) {
					Game.chooseFaceupCardToTake(-1);
					removeRevalidateRepaint();
				}
			}

		});

		JButton routesButton = new JButton(new ImageIcon(routesImageSized));
		routesButton.setBorder(BorderFactory.createEmptyBorder());
		routesButton.setBounds(FACE_UP_OFFSET_X * 2 + deckButton.getWidth(), DECK_OFFSET_Y,
				(int) (routesImage.getWidth() * GameStarter.getWidthModifier()),
				(int) (routesImage.getHeight() * GameStarter.getHeightModifier()));
		this.add(routesButton);
		routesButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.startRoutePurchasing();
				removeRevalidateRepaint();
			}

		});

	}

	private void drawGlowCircleDoubleDecoration(int i) {
		pen.setColor(Color.CYAN);
		pen.setStroke(new BasicStroke((float) (5.0f * GameStarter.getDiagonalModifier())));
		Ellipse2D decoration = new Ellipse2D.Double(
				FACE_UP_OFFSET_X + FACE_UP_WIDTH * (i) + FACE_UP_SPACING * (i) + 20 * GameStarter.getWidthModifier(),
				FACE_UP_OFFSET_Y + (FACE_UP_HEIGHT - (FACE_UP_WIDTH - 30 * GameStarter.getHeightModifier())) / 2.0,
				FACE_UP_WIDTH - 35 * GameStarter.getWidthModifier(),
				FACE_UP_WIDTH - 35 * GameStarter.getHeightModifier());
		pen.draw(decoration);
	}

	private void drawGlowCircleDecoration(int i) {
		pen.setColor(Color.CYAN);
		pen.setStroke(new BasicStroke((float) (5.0f * GameStarter.getDiagonalModifier())));
		Ellipse2D decoration = new Ellipse2D.Double(
				FACE_UP_OFFSET_X + FACE_UP_WIDTH * (i) + FACE_UP_SPACING * (i) + 10 * GameStarter.getWidthModifier(),
				FACE_UP_OFFSET_Y + (FACE_UP_HEIGHT - (FACE_UP_WIDTH - 20 * GameStarter.getHeightModifier())) / 2.0,
				FACE_UP_WIDTH - 20 * GameStarter.getWidthModifier(),
				FACE_UP_WIDTH - 20 * GameStarter.getHeightModifier());
		pen.draw(decoration);
	}

	private void displayPlayerInformation(Player player, int numberForSpacing) {
		if (!Game.getIsItFirstTurn()) {
			JLabel instructions = new JLabel();
			instructions.setText(
					"<html>Draw two faceup cards or build one path.<br>Note that only one gray faceup card can be chosen.</html>");
			instructions.setBounds((int) (30 * GameStarter.getWidthModifier()),
					(int) (200 * GameStarter.getHeightModifier()), (int) (400 * GameStarter.getWidthModifier()),
					(int) (200 * GameStarter.getHeightModifier()));
			instructions.setForeground(Color.WHITE);
			this.add(instructions);
		}

		String playerName = player.getName();
		Color textColor = player.getColor();
		int playerTrainCount = player.getTrainCount();
		int playerScore = player.getScore();

		Font infoFont = new Font("ISCOTEUR", Font.PLAIN, (int) (24 * GameStarter.getDiagonalModifier()));

		if (player.equals(Game.getCurrentPlayer())) {
			playerName = playerName + "  <--------";
		}
		JLabel playerNameLabel = new JLabel(playerName);
		playerNameLabel.setForeground(textColor);
		playerNameLabel.setFont(infoFont);
		playerNameLabel.setBounds(0,
				(int) ((DECK_SPACING + 50 + (numberForSpacing * 100)) * GameStarter.getHeightModifier()),
				(int) (400 * GameStarter.getWidthModifier()), (int) (30 * GameStarter.getHeightModifier()));
		this.add(playerNameLabel);

		JLabel playerTrainCountLabel = new JLabel("Train Count: " + Integer.toString(playerTrainCount));
		playerTrainCountLabel.setForeground(Color.WHITE);
		playerTrainCountLabel.setFont(infoFont);
		playerTrainCountLabel.setBounds(0, playerNameLabel.getY() + playerNameLabel.getHeight(),
				(int) (400 * GameStarter.getWidthModifier()), (int) (30 * GameStarter.getHeightModifier()));
		this.add(playerTrainCountLabel);

		JLabel playerScoreLabel = new JLabel("Score: " + Integer.toString(playerScore));
		playerScoreLabel.setForeground(Color.WHITE);
		playerScoreLabel.setFont(infoFont);
		playerScoreLabel.setBounds(0, playerTrainCountLabel.getY() + playerTrainCountLabel.getHeight(),
				(int) (400 * GameStarter.getWidthModifier()), (int) (30 * GameStarter.getHeightModifier()));
		this.add(playerScoreLabel);
	}

	public void setRecent(Color chosenCard, int index) {
		this.recentColor = chosenCard;
		this.recentlyDrawnIndex = index;
	}

	public void increasedFaceUpCardDrawNumber() {
		this.faceUpCardDrawNumber++;
	}

	public void resetDrawCount() {
		this.faceUpCardDrawNumber = 0;
	}

}
