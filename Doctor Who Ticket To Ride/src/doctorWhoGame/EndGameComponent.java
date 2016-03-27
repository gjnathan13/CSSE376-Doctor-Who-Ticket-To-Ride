package doctorWhoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLabel;

public class EndGameComponent extends JComponent {

	private Graphics2D pen;
	private boolean gameOver = false;
	private final int OFFSET_X = (int)(GameStarter.getWidthModifier()*250);
	private final int LABEL_WIDTH = (int)(GameStarter.getWidthModifier()*750);
	private final int LABEL_HEIGHT = (int)(GameStarter.getHeightModifier()*75);
	private int labelSpacing;
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		pen.setColor(Color.BLACK);
		pen.fillRect(0, 0, (int)(GameStarter.getWidthModifier()*this.getWidth()), (int)(GameStarter.getHeightModifier()*this.getHeight()));
		if (this.gameOver) {
			ArrayList<Player> players = Game.getPlayerList();
			Font playerFont = new Font("ISOCTEUR", Font.PLAIN, (int)(GameStarter.getDiagonalModifier()*36));
			labelSpacing = (int)(GameStarter.getHeightModifier() * (this.getHeight() - players.size() * LABEL_HEIGHT) / (players.size() + 1));
			for (int i = 0; i < players.size(); i++) {
				JLabel playerDisplay = new JLabel(players.get(i).getName() + ": " + players.get(i).getScore(),
						JLabel.CENTER);
				playerDisplay.setBounds(OFFSET_X, LABEL_HEIGHT * i + labelSpacing * (i + 1), LABEL_WIDTH, LABEL_HEIGHT);
				playerDisplay.setFont(playerFont);
				playerDisplay.setForeground(players.get(i).getColor());
				this.add(playerDisplay);
			}

		}
	}

	public void setGameOver() {
		this.gameOver = true;
		removeAll();
		revalidate();
		repaint();
	}

}
