package doctorWhoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class Scoreboard extends JComponent {
	
	private Player[] playerList;
	private final int DECK_SPACING = 300;
	
	public Scoreboard(Player[] playerList){
		this.playerList = playerList;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		if(playerList.length != 0){
		displayPlayerInformation(playerList[0]);
		}
		g2.setColor(new Color(0,0,25,255));
		g2.fillRect(0,0,400,this.getHeight());
		
	}

	private void displayPlayerInformation(Player player) {
		String playerName = player.getName();
		player.getColor();
		player.getTrainCount();
		player.getScore();
		JLabel playerInfo = new JLabel(playerName);
		playerInfo.setBounds(0, DECK_SPACING, 400, 400);
		this.add(playerInfo);
	}
	
}
