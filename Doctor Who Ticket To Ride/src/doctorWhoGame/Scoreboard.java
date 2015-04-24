package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JComponent;

public class Scoreboard extends JComponent {
	
	private Player[] playerList;
	
	public Scoreboard(Player[] playerList){
		
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		
	}
	
}
