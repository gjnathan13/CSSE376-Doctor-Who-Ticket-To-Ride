package doctorWhoGame;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

public class GameStarter {

	public static void main(String[] args){
		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");
		window.setPreferredSize(new Dimension(500,500));
		final JPanel startScreen = new JPanel();
		JButton startButton = new JButton("Start Game");
		
		BufferedImage backgroundImage = null;
		
		final Gameboard gameboard = new Gameboard();
		int[] imageDimensions = gameboard.getHandImageDimensions();
		final int imageWidth = imageDimensions[0];
		final int imageHeight = imageDimensions[1];
		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(imageWidth, imageHeight));
		window.add(startScreen);
		startScreen.add(startButton);
		gameboard.setPreferredSize(new Dimension(imageWidth, imageHeight));
		gameboard.setBounds(0,0,imageWidth,imageHeight);
		
		startButton.addActionListener(new ActionListener(){
			
			public void actionPerformed(ActionEvent arg0) {
				window.dispose();
				startScreen.removeAll();
				JFrame gameWindow = new JFrame();
				gameWindow.setTitle("Good Luck!");
				gameWindow.add(layeredPane);
				layeredPane.add(gameboard, BorderLayout.WEST);
				JButton drawButton = new JButton("Draw a card");
				drawButton.setBounds(imageWidth - 150, 0, 150, 20);
				layeredPane.add(drawButton, BorderLayout.EAST);
				gameWindow.pack();
				gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				gameWindow.setVisible(true);
				
				gameboard.setHand(new Hand());
			}
		});
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
