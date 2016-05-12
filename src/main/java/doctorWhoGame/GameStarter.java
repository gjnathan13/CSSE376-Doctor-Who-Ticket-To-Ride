package doctorWhoGame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;

//import org.json.simple.JSONObject;

/**
 * Sets up gameplay.
 * 
 * @author nathangj
 * 
 */
public class GameStarter {

	private final static Color[] COLOR_ARRAY = { Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA, Color.YELLOW };
	protected static Player[] playerList;
	
	private final static int ORIGINAL_MONITOR_WIDTH = 1920;
	private final static int ORIGINAL_MONITOR_HEIGHT = 1080;

	/**
	 * Initializes game and sets up start screen GUI.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");

		BufferedImage startBackgroundImage = ImageIO.read(new File("GameImages\\TitleImage.png"));
		int startScreenWidth = (int) (startBackgroundImage.getWidth() * getWidthModifier());
		int startScreenHeight = (int) (startBackgroundImage.getHeight() * getHeightModifier());

		Image startBackgroundScaledImage = startBackgroundImage.getScaledInstance(startScreenWidth, startScreenHeight, Image.SCALE_DEFAULT);

		BufferedImage startButtonImage = ImageIO.read(new File("GameImages\\StartButtonImage.png"));
		Image startButtonScaledImage = startButtonImage.getScaledInstance(
				(int) (startButtonImage.getWidth() * getWidthModifier()),
				(int) (startButtonImage.getHeight() * getHeightModifier()), Image.SCALE_DEFAULT);
		JLayeredPane startScreen = new JLayeredPane();
		startScreen.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
		window.add(startScreen);
		JLabel startLabel = createStartLabel(startScreenWidth, startScreenHeight, startBackgroundScaledImage);
		JButton questionButton = createQuestionButton();
		JButton startButton = createStartButton(startButtonImage, startButtonScaledImage);
		startButton.addActionListener(new StartGameActionListener(window, startScreenWidth, startScreenHeight));
		startScreen.add(questionButton);
		startScreen.add(startButton);
		startScreen.add(startLabel);
		
		window.setResizable(false);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private static JButton createStartButton(BufferedImage startButtonImage, Image startButtonScaledImage) {
		JButton startButton = new JButton(new ImageIcon(startButtonScaledImage));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setBounds((int) (125 * getWidthModifier()), (int) (250 * getHeightModifier()),
				(int) (startButtonImage.getWidth() * getWidthModifier()),
				(int) (startButtonImage.getHeight() * getHeightModifier()));
		return startButton;
	}

	private static JButton createQuestionButton() {
		JButton questionButton = new JButton("?");
		questionButton.setBounds((int) (475 * getWidthModifier()), (int) (325 * getHeightModifier()),
				(int) (50 * getWidthModifier()), (int) (50 * getHeightModifier()));
		questionButton
				.setPreferredSize(new Dimension((int) (50 * getWidthModifier()), (int) (50 * getHeightModifier())));
		questionButton.setForeground(Color.CYAN);
		questionButton.setBackground(Color.BLACK);
		questionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				openPDFInstructions();

			}

		});
		return questionButton;
	}

	private static JLabel createStartLabel(int startScreenWidth, int startScreenHeight, Image startBackResize) {
		JLabel startLabel = new JLabel(new ImageIcon(startBackResize));
		startLabel.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
		startLabel.setBounds(0, 0, startScreenWidth, startScreenHeight);
		return startLabel;
	}

	public static void openPDFInstructions() {
		try {
			if (Desktop.isDesktopSupported()) {
				File pdf = new File("otherFiles/Ticket to Ride Rules.pdf");
				Desktop.getDesktop().open(pdf);
			} else {
				// can't open it
				System.err.println("Cannot open PDF file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double getHeightModifier() {
		try{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getHeight() / ORIGINAL_MONITOR_HEIGHT;
		}
		catch(Exception e){
			return 1;
		}
	}

	public static double getWidthModifier() {
		try{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getWidth() / ORIGINAL_MONITOR_WIDTH;
		}
		catch(Exception e){
			return 1;
		}
	}

	public static double getDiagonalModifier() {
		try{
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double newDiagonal = Math
				.sqrt(screenSize.getWidth() * screenSize.getWidth() + screenSize.getHeight() * screenSize.getHeight());
		double oldDiagonal = Math.sqrt(
				ORIGINAL_MONITOR_WIDTH * ORIGINAL_MONITOR_WIDTH + ORIGINAL_MONITOR_HEIGHT * ORIGINAL_MONITOR_HEIGHT);
		return newDiagonal / oldDiagonal;
		}
		catch(Exception e){
			return 1;
		}

	}

	public static Color[] getColorArray() {
		return COLOR_ARRAY;
	}
}