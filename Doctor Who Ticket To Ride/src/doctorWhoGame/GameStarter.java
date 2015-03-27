package doctorWhoGame;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class GameStarter {

	public static void main(String[] args){
		JFrame window = new JFrame();
		JPanel startScreen = new JPanel();
		JButton startButton = new JButton("Start Game");
		window.add(startScreen);
		startScreen.add(startButton);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
