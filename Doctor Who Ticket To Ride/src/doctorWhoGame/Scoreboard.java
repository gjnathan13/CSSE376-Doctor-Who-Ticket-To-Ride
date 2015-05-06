package doctorWhoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class Scoreboard extends JComponent {

	private Player[] playerList;
	private final int DECK_SPACING = 300;

	public Scoreboard(Player[] playerList) {
		this.playerList = playerList;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2 = (Graphics2D) g;
		g2.setColor(new Color(0, 0, 25, 255));
		g2.fillRect(0, 0, 400, this.getHeight());
		if (playerList.length != 0) {
			for (int i = 0; i < playerList.length; i++) {
				displayPlayerInformation(playerList[i], i);
			}
		}
		JButton endTurnButton = new JButton("End Turn");
		endTurnButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.switchToNextPlayer();
				removeAll();
				revalidate();
				repaint();
				Game.updateGameboard();
			}

		});
		endTurnButton.setBounds(100, 900, 150, 50);
		this.add(endTurnButton);
	}

	private void displayPlayerInformation(Player player, int numberForSpacing) {
		String playerName = player.getName();
		PlayerColor playerColor = player.getColor();
		Color textColor = getTextColor(playerColor);
		int playerTrainCount = player.getTrainCount();
		int playerScore = player.getScore();

		Font infoFont = new Font("ISCOTEUR", Font.PLAIN, 24);

		if (player.equals(Game.getCurrentPlayer())) {
			playerName = playerName + "  <--------";
		}
		JLabel playerNameLabel = new JLabel(playerName);
		playerNameLabel.setForeground(textColor);
		playerNameLabel.setFont(infoFont);
		playerNameLabel.setBounds(0, DECK_SPACING + numberForSpacing * 100, 400, 30);
		this.add(playerNameLabel);

		JLabel playerTrainCountLabel = new JLabel("Train Count: "
				+ Integer.toString(playerTrainCount));
		playerTrainCountLabel.setForeground(Color.WHITE);
		playerTrainCountLabel.setFont(infoFont);
		playerTrainCountLabel.setBounds(0, playerNameLabel.getY()
				+ playerNameLabel.getHeight(), 400, 30);
		this.add(playerTrainCountLabel);

		JLabel playerScoreLabel = new JLabel("Score: "
				+ Integer.toString(playerScore));
		playerScoreLabel.setForeground(Color.WHITE);
		playerScoreLabel.setFont(infoFont);
		playerScoreLabel.setBounds(0, playerTrainCountLabel.getY()
				+ playerTrainCountLabel.getHeight(), 400, 30);
		this.add(playerScoreLabel);
	}

	private Color getTextColor(PlayerColor playerColor) {
		switch (playerColor) {
		case Red: {
			return Color.RED;
		}
		case Green: {
			return Color.GREEN;
		}
		case Yellow: {
			return Color.YELLOW;
		}
		case Magenta: {
			return Color.MAGENTA;
		}
		case Blue: {
			return Color.BLUE;
		}
		}
		return null;
	}

}
