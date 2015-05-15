package doctorWhoGame;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
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

public class Scoreboard extends JComponent {

	private static final int FACE_UP_OFFSET_X = 25;
	private static final int FACE_UP_WIDTH = 50;
	private static final int FACE_UP_SPACING = 25;
	private static final int FACE_UP_OFFSET_Y = 200;
	private static final int FACE_UP_HEIGHT = 80;
	private static final int DECK_OFFSET_Y = 25;
	private static final int DECK_WIDTH = 200;
	private static final int DECK_HEIGHT = 150;
	private static final int BOUNDING_BOX_WIDTH = 10;
	private Player[] playerList;
	private final int DECK_SPACING = 300;
	private Graphics2D pen;
	private Rectangle[] faceUps = new Rectangle[5];
	private BufferedImage deckImage;
	private BufferedImage routesImage;
	private File deckFile = new File("GameImages\\TardisDeck.png");
	private File routesFile = new File("GameImages\\RouteCardDeck.png");
	protected boolean routeGetting;

	public Scoreboard(Player[] playerList) {
		try {
			this.deckImage = ImageIO.read(deckFile);
			this.routesImage = ImageIO.read(routesFile);
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
				if (this.faceUps[i].intersects(xBox, yBox, BOUNDING_BOX_WIDTH,
						BOUNDING_BOX_WIDTH)) {
					Game.chooseFaceupCardToTake(i);
				}
			}
		}

	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		this.pen = g2;
		g2.setColor(new Color(0, 0, 25, 255));
		g2.fillRect(0, 0, 400, this.getHeight());
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
				removeAll();
				revalidate();
				repaint();
				Game.updateGameboard();
			}

		});
		endTurnButton.setBounds(100, 900, 150, 50);
		this.add(endTurnButton);

		setUpDeckSpace();
	}

	private void setUpDeckSpace() {
		ArrayList<TrainColor> currentFaceUp = Game.getCurrentFaceup();
		for (int i = 0; i < currentFaceUp.size(); i++) {
			pen.setColor(covertTrainColor(currentFaceUp.get(i)));
			Rectangle card = new Rectangle(FACE_UP_OFFSET_X + FACE_UP_WIDTH
					* (i) + FACE_UP_SPACING * (i), FACE_UP_OFFSET_Y,
					FACE_UP_WIDTH, FACE_UP_HEIGHT);
			this.faceUps[i] = card;
			pen.fill(card);

			pen.setColor(Color.CYAN);
			pen.setStroke(new BasicStroke(5.0f));
			Ellipse2D decoration = new Ellipse2D.Double(FACE_UP_OFFSET_X
					+ FACE_UP_WIDTH * (i) + FACE_UP_SPACING * (i) + 10,
					FACE_UP_OFFSET_Y + (FACE_UP_HEIGHT - (FACE_UP_WIDTH - 20))
							/ 2.0, FACE_UP_WIDTH - 20, FACE_UP_WIDTH - 20);
			pen.draw(decoration);
		}

		JButton deckButton = new JButton(new ImageIcon(deckImage));
		deckButton.setBorder(BorderFactory.createEmptyBorder());
		deckButton.setBounds(FACE_UP_OFFSET_X, DECK_OFFSET_Y,
				deckImage.getWidth(), deckImage.getHeight());
		this.add(deckButton);
		deckButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				if (!routeGetting) {
					Game.chooseFaceupCardToTake(-1);
				}
			}

		});

		JButton routesButton = new JButton(new ImageIcon(routesImage));
		routesButton.setBorder(BorderFactory.createEmptyBorder());
		routesButton.setBounds(FACE_UP_OFFSET_X * 2 + deckButton.getWidth(),
				DECK_OFFSET_Y, routesImage.getWidth(), routesImage.getHeight());
		this.add(routesButton);

	}

	private Color covertTrainColor(TrainColor trainColor) {
		switch (trainColor) {
		case Red: {
			return Color.RED;
		}
		case Pink: {
			return Color.PINK;
		}
		case Orange: {
			return Color.ORANGE;
		}
		case Yellow: {
			return Color.YELLOW;
		}
		case Green: {
			return Color.GREEN;
		}
		case Blue: {
			return Color.BLUE;
		}
		case White: {
			return Color.WHITE;
		}
		case Black: {
			return Color.BLACK;
		}
		case Rainbow: {
			return Color.GRAY;
		}
		}
		return null;
	}

	private void displayPlayerInformation(Player player, int numberForSpacing) {
		String playerName = player.getName();
		PlayerColor playerColor = player.getColor();
		Color textColor = getTextColor(playerColor);
		int playerTrainCount = player.getTrainCount();
		int playerScore = player.getScore();

		Font infoFont = new Font("ISCOTEUR", Font.PLAIN, 24);

		if (player.equals(Game.getCurrentPlayer())) {
			playerName = playerName + "  <--------";
		}
		JLabel playerNameLabel = new JLabel(playerName);
		playerNameLabel.setForeground(textColor);
		playerNameLabel.setFont(infoFont);
		playerNameLabel.setBounds(0, DECK_SPACING + numberForSpacing * 100,
				400, 30);
		this.add(playerNameLabel);

		JLabel playerTrainCountLabel = new JLabel("Train Count: "
				+ Integer.toString(playerTrainCount));
		playerTrainCountLabel.setForeground(Color.WHITE);
		playerTrainCountLabel.setFont(infoFont);
		playerTrainCountLabel.setBounds(0, playerNameLabel.getY()
				+ playerNameLabel.getHeight(), 400, 30);
		this.add(playerTrainCountLabel);

		JLabel playerScoreLabel = new JLabel("Score: "
				+ Integer.toString(playerScore));
		playerScoreLabel.setForeground(Color.WHITE);
		playerScoreLabel.setFont(infoFont);
		playerScoreLabel.setBounds(0, playerTrainCountLabel.getY()
				+ playerTrainCountLabel.getHeight(), 400, 30);
		this.add(playerScoreLabel);
	}

	private Color getTextColor(PlayerColor playerColor) {
		switch (playerColor) {
		case Red: {
			return Color.RED;
		}
		case Green: {
			return Color.GREEN;
		}
		case Yellow: {
			return Color.YELLOW;
		}
		case Magenta: {
			return Color.MAGENTA;
		}
		case Blue: {
			return Color.BLUE;
		}
		}
		return null;
	}

}
