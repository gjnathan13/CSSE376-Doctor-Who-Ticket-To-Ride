package doctorWhoGame;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;

import javax.swing.JComponent;

public class ScoreVisual extends JComponent {

	private Graphics2D pen;
	private ArrayList<Player> players;

	private int DOT_SPACING = (int) (3.0 * GameStarter.getDiagonalModifier());
	private int DOT_DIAMETER = (int) (10.0 * GameStarter.getDiagonalModifier());
	private double VERTICAL_SPACING = 33.3 * GameStarter.getHeightModifier();
	private double HORIZONTAL_SPACING = 41.4 * GameStarter.getWidthModifier();
	private int CORNER_OFFSET = (int) (10.0 * GameStarter.getDiagonalModifier());
	private int VERTICAL_OFFSET = (int) (120.0 * GameStarter.getHeightModifier());
	private int VERTICAL_EXTRA_RIGHT = (int) (6.0 * GameStarter.getHeightModifier());
	private int HORIZONTAL_OFFSET = (int) (90.0 * GameStarter.getWidthModifier());

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
			this.pen.setColor(players.get(i).getColor());

			createOval(playerScore, i);
		}

	}

	private int getWidth(int score, int playerNumber) {
		if (score < 21)
			return DOT_SPACING * (playerNumber + 1) + DOT_SPACING * playerNumber;
		else if (score < 50)
			return HORIZONTAL_OFFSET + (int) (HORIZONTAL_SPACING * (score - 21));
		else if (score < 71)
			return this.getWidth() - (DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * playerNumber);
		else
			return HORIZONTAL_OFFSET + (int) (HORIZONTAL_SPACING * (99 - score));
	}

	private int getHeight(int score, int playerNumber) {
		if (score < 20)
			return this.getHeight() - (int) ((score - 1) * VERTICAL_SPACING + VERTICAL_OFFSET);
		else if (score < 51)
			return DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * playerNumber;
		else if (score < 71)
			return this.getHeight() - (int) ((69 - score) * VERTICAL_SPACING + VERTICAL_OFFSET);
		else
			return this.getHeight() - (DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * (playerNumber + 1));

	}

	private void createOval(int score, int playerNumber) {
		int width = getWidth(score, playerNumber);
		int height = getHeight(score, playerNumber);

		this.pen.fillOval(width, height, DOT_DIAMETER, DOT_DIAMETER);
	}

}
