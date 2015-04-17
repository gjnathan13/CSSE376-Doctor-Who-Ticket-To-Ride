package doctorWhoGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

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

	/**
	 * Initializes game and sets up start screen GUI.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");
		window.setPreferredSize(new Dimension(500, 500));
		final JPanel startScreen = new JPanel();
		JButton startButton = new JButton("Start Game");

		window.add(startScreen);
		startScreen.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				startScreen.removeAll();
				setUpGameboard();
			}
		});
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
		
		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(gameboardImageWidth, gameboardImageHeight + routeboardImageHeight));
		gameboard.setPreferredSize(new Dimension(gameboardImageWidth, gameboardImageHeight));
		gameboard.setBounds(0, routeboardImageHeight, gameboardImageWidth, gameboardImageHeight);
		
		routeboard.setPreferredSize(new Dimension(routeboardImageWidth, routeboardImageHeight));
		routeboard.setBounds(0,-5,routeboardImageWidth, routeboardImageHeight + 5);
		
		JFrame gameWindow = new JFrame();
		gameWindow.setResizable(false);
		gameWindow.setTitle("Good Luck!");
		gameWindow.add(layeredPane);
		layeredPane.add(gameboard, BorderLayout.WEST);
		layeredPane.add(routeboard, BorderLayout.WEST);
		JButton drawButton = new JButton("Draw a card");
		drawButton.setBounds(gameboardImageWidth - 150, routeboardImageHeight, 150, 20);
		layeredPane.add(drawButton, BorderLayout.EAST);
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

