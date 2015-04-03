package doctorWhoGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

/**
 * Sets up gameplay.
 * 
 * @author nathangj
 *
 */
public class GameStarter {

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
		final Gameboard gameboard = new Gameboard();
		int[] imageDimensions = gameboard.getHandImageDimensions();
		final int imageWidth = imageDimensions[0];
		final int imageHeight = imageDimensions[1];
		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(imageWidth, imageHeight));
		gameboard.setPreferredSize(new Dimension(imageWidth, imageHeight));
		gameboard.setBounds(0, 0, imageWidth, imageHeight);
		JFrame gameWindow = new JFrame();
		gameWindow.setTitle("Good Luck!");
		gameWindow.add(layeredPane);
		layeredPane.add(gameboard, BorderLayout.WEST);
		JButton drawButton = new JButton("Draw a card");
		drawButton.setBounds(imageWidth - 150, 0, 150, 20);
		layeredPane.add(drawButton, BorderLayout.EAST);
		gameboard.setHand(new Hand());
		drawButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				getNewCardForHand();
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
		
	}
}
