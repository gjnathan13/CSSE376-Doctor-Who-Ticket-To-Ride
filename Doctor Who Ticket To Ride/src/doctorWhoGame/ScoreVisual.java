package doctorWhoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ScoreVisual extends JComponent {

	private Graphics2D pen;
	private ArrayList<Player> players;

	private int DOT_SPACING = 3;
	private int DOT_DIAMETER = 10;
	private double VERTICAL_SPACING = 33.3;
	private double HORIZONTAL_SPACING = 41.4;
	private int CORNER_OFFSET = 10;
	private int VERTICAL_OFFSET = 120;
	private int VERTICAL_EXTRA_RIGHT = 6;
	private int HORIZONTAL_OFFSET = 90;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		if (this.players == null) {
			this.players = Game.getPlayerList();
		}

		// get player list and scores and display
		for (int i = 0; i < players.size(); i++) {
			int playerScore = players.get(i).getScore() % 100;
			this.pen.setColor(convertPlayerColor(players.get(i).getColor()));
			if (playerScore == 0) {
				this.pen.fillOval(CORNER_OFFSET * (i + 1), this.getHeight()
						- (CORNER_OFFSET * (i + 1) + DOT_DIAMETER),
						DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore > 0 && playerScore < 20) {
				this.pen.fillOval(
						DOT_SPACING * (i + 1) + DOT_DIAMETER * i,
						this.getHeight()
								- (int) ((playerScore - 1) * VERTICAL_SPACING + VERTICAL_OFFSET),
						DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore == 20) {
				this.pen.fillOval(CORNER_OFFSET * (i + 1), CORNER_OFFSET
						* (i + 1), DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore > 20 && playerScore < 50) {
				this.pen.fillOval(HORIZONTAL_OFFSET
						+ (int) ((playerScore - 21) * HORIZONTAL_SPACING),
						DOT_SPACING * (i + 1) + DOT_DIAMETER * i, DOT_DIAMETER,
						DOT_DIAMETER);
			} else if (playerScore == 50) {
				this.pen.fillOval(this.getWidth() - (CORNER_OFFSET * (i + 1)),
						CORNER_OFFSET * (i + 1), DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore > 50 && playerScore < 70) {
				this.pen.fillOval(
						this.getWidth()
								- (DOT_SPACING * (i + 1) + DOT_DIAMETER * i),
						this.getHeight()
								- ((int) ((69 - playerScore) * VERTICAL_SPACING
										+ VERTICAL_OFFSET + VERTICAL_EXTRA_RIGHT)),
						DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore == 70) {
				this.pen.fillOval(this.getWidth() - (CORNER_OFFSET * (i + 1)),
						this.getHeight()
								- (CORNER_OFFSET * (i + 1) + DOT_DIAMETER),
						DOT_DIAMETER, DOT_DIAMETER);
			} else if (playerScore > 70 && playerScore < 100) {
				this.pen.fillOval(HORIZONTAL_OFFSET
						+ (int) ((99 - playerScore) * HORIZONTAL_SPACING),
						this.getHeight()
								- (DOT_SPACING * (i + 1) + DOT_DIAMETER
										* (i + 1)), DOT_DIAMETER, DOT_DIAMETER);
			}

		}

	}

	private Color convertPlayerColor(PlayerColor playerColor) {
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
