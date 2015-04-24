package doctorWhoGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javafx.scene.shape.Circle;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

/**
 * Sets up gameplay.
 * 
 * @author nathangj
 * 
 */
public class GameStarter {

	private static Hand currentHand;
	private static Gameboard gameboard;
	private static TrainDeck trainDeck = new TrainDeck();
	private static Routeboard routeboard;
	private final static Color[] COLOR_ARRAY = { Color.GREEN, Color.RED,
			Color.BLUE, Color.MAGENTA, Color.YELLOW };
	protected static Player[] playerList;
	private static Scoreboard scoreboard;

	/**
	 * Initializes game and sets up start screen GUI.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");

		BufferedImage startBack = ImageIO.read(new File(
				"GameImages\\TitleImage.png"));
		BufferedImage startButtonImage = ImageIO.read(new File(
				"GameImages\\StartButtonImage.png"));

		JLayeredPane startScreen = new JLayeredPane();
		startScreen.setPreferredSize(new Dimension(startBack.getWidth(),
				startBack.getHeight()));

		window.add(startScreen);

		JLabel startLabel = new JLabel(new ImageIcon(startBack));
		startLabel.setPreferredSize(new Dimension(startBack.getWidth(),
				startBack.getHeight()));
		startLabel.setBounds(0, 0, startBack.getWidth(), startBack.getHeight());
		startScreen.add(startLabel);

		JButton startButton = new JButton(new ImageIcon(startButtonImage));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setBounds(125, 250, startButtonImage.getWidth(),
				startButtonImage.getHeight());
		startScreen.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				window.getContentPane().removeAll();
				window.getContentPane().repaint();
				window.getContentPane().setBackground(Color.BLACK);

				final JTextField[] playerNames = new JTextField[5];

				Font nameFont = new Font("ISOCTEUR", Font.BOLD, 24);
				for (int i = 0; i < 5; i++) {
					JTextField nameEntry = new JTextField(20);
					nameEntry.setForeground(Color.CYAN);
					nameEntry.setBackground(Color.BLACK);
					nameEntry.setBounds(200, 25 * (i + 1) + 25 * i, 300, 40);
					nameEntry.setFont(nameFont);
					window.add(nameEntry);
					playerNames[i] = nameEntry;

					JLabel nameLabel = new JLabel("Player " + (i + 1));
					nameLabel.setFont(nameFont);
					nameLabel.setBounds(20, 25 * (i + 1) + 25 * i, 160, 40);
					nameLabel.setForeground(Color.WHITE);
					window.add(nameLabel);
				}

				JComponent colorDrawer = new JComponent() {

					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						for (int i = 0; i < 5; i++) {
							g2.setColor(COLOR_ARRAY[i]);
							g2.fillOval(10, 25 * (i + 1) + 25 * i, 25, 25);
						}
					}
				};
				colorDrawer.setBounds(500, 0, 40, 500);
				window.add(colorDrawer);

				JButton startButton = new JButton("GERONIMO");
				startButton.setBorder(BorderFactory.createEmptyBorder());
				startButton.setForeground(Color.CYAN);
				startButton.setBackground(Color.BLACK);
				startButton.setFont(nameFont);
				startButton.setBounds(150, 310, 275, 40);
				window.add(startButton);
				startButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						window.dispose();
						ArrayList<Player> players = new ArrayList<Player>();
						for (int i = 0; i < 5; i++) {
							String nameString = playerNames[i].getText().trim();
							if (nameString.length() > 0) {
								Player p = new Player(nameString,
										COLOR_ARRAY[i]);
							}
						}
						GameStarter.playerList = players.toArray(new Player[players.size()]);
						setUpGameboard();
					}

				}

				);
			}
		});
		window.setResizable(false);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Sets up GUI for game play.
	 */
	private static void setUpGameboard() {
		gameboard = new Gameboard();
		int[] gameboardImageDimensions = gameboard.getHandImageDimensions();
		final int gameboardImageWidth = gameboardImageDimensions[0];
		final int gameboardImageHeight = gameboardImageDimensions[1];

		routeboard = new Routeboard();
		int[] routeImageDimensions = routeboard.getRouteImageDimensions();
		final int routeboardImageWidth = routeImageDimensions[0];
		final int routeboardImageHeight = routeImageDimensions[1];

		scoreboard = new Scoreboard(playerList);

		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(gameboardImageWidth + 400,
				gameboardImageHeight + routeboardImageHeight));

		gameboard.setPreferredSize(new Dimension(gameboardImageWidth,
				gameboardImageHeight));
		gameboard.setBounds(0, routeboardImageHeight, gameboardImageWidth,
				gameboardImageHeight);

		routeboard.setPreferredSize(new Dimension(routeboardImageWidth,
				routeboardImageHeight));
		routeboard.setBounds(0, -5, routeboardImageWidth,
				routeboardImageHeight + 5);
		
		scoreboard.setPreferredSize(new Dimension(400,
				routeboardImageHeight + gameboardImageHeight));
		scoreboard.setBounds(routeboardImageWidth, 0, 400,
				routeboardImageHeight + gameboardImageHeight);

		JFrame gameWindow = new JFrame();
		gameWindow.setResizable(false);
		gameWindow.setTitle("Good Luck!");
		gameWindow.add(layeredPane);
		layeredPane.add(gameboard);
		layeredPane.add(routeboard);
		layeredPane.add(scoreboard);
		JButton drawButton = new JButton("Draw a card");
		drawButton.setBounds(gameboardImageWidth - 150, routeboardImageHeight,
				150, 20);
		layeredPane.add(drawButton);
		currentHand = new Hand();
		gameboard.setHand(currentHand);
		drawButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				getNewCardForHand();
				gameboard.repaint();
			}
		});

		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
	}

	/**
	 * Draws card from Deck and puts it in the specified hand.
	 */
	private static void getNewCardForHand() {
		TrainColor drawnCard = TrainDeck.draw();
		currentHand.addTrainCard(drawnCard);
	}
}