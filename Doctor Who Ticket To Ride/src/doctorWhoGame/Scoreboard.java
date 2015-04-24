package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Scoreboard extends JComponent {
	
	private Player[] playerList;
	
	public Scoreboard(Player[] playerList){
		this.playerList = playerList;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		displayPlayerInformation(playerList[0]);
	}

	private void displayPlayerInformation(Player player) {
		player.getName();
		player.getColor();
		player.getTrainCount();
		player.getScore();
	}
	
}
