package doctorWhoGame;

import java.util.ArrayList;

@SuppressWarnings("serial")
public class ScoreVisual extends GameComponent {

	private ArrayList<Player> players;

	private int DOT_SPACING = (int) (3.0 * GameStarter.getDiagonalModifier());
	private int DOT_DIAMETER = (int) (10.0 * GameStarter.getDiagonalModifier());
	private double VERTICAL_SPACING = 33.3 * GameStarter.getHeightModifier();
	private double HORIZONTAL_SPACING = 41.4 * GameStarter.getWidthModifier();
	private int VERTICAL_OFFSET = (int) (120.0 * GameStarter.getHeightModifier());
	private int HORIZONTAL_OFFSET = (int) (90.0 * GameStarter.getWidthModifier());

	private final int TOP_LEFT_SCORE_FOR_SCOREBOARD = 20;
	private final int TOP_RIGHT_SCORE_FOR_SCOREBOARD = 50;
	private final int BOTTOM_RIGHT_SCORE_FOR_SCOREBOARD = 70;
	private final int BOTTOM_LEFT_END_SCORE_FOR_SCOREBOARD = 100;

	@Override
	protected void showGraphics(){
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
		if (score < TOP_LEFT_SCORE_FOR_SCOREBOARD + 1)
			return DOT_SPACING * (playerNumber + 1) + DOT_SPACING * playerNumber;
		else if (score < TOP_RIGHT_SCORE_FOR_SCOREBOARD)
			return HORIZONTAL_OFFSET + (int) (HORIZONTAL_SPACING * (score - 21));
		else if (score < BOTTOM_RIGHT_SCORE_FOR_SCOREBOARD + 1)
			return this.getWidth() - (DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * playerNumber);
		else
			return HORIZONTAL_OFFSET + (int) (HORIZONTAL_SPACING * (BOTTOM_LEFT_END_SCORE_FOR_SCOREBOARD - 1 - score));
	}

	private int getHeight(int score, int playerNumber) {
		if (score < TOP_LEFT_SCORE_FOR_SCOREBOARD)
			return this.getHeight() - (int) ((score - 1) * VERTICAL_SPACING + VERTICAL_OFFSET);
		else if (score < TOP_RIGHT_SCORE_FOR_SCOREBOARD + 1)
			return DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * playerNumber;
		else if (score < BOTTOM_RIGHT_SCORE_FOR_SCOREBOARD + 1)
			return this.getHeight()
					- (int) ((BOTTOM_RIGHT_SCORE_FOR_SCOREBOARD - 1 - score) * VERTICAL_SPACING + VERTICAL_OFFSET);
		else
			return this.getHeight() - (DOT_SPACING * (playerNumber + 1) + DOT_DIAMETER * (playerNumber + 1));

	}

	private void createOval(int score, int playerNumber) {
		int width = getWidth(score, playerNumber);
		int height = getHeight(score, playerNumber);

		this.pen.fillOval(width, height, DOT_DIAMETER, DOT_DIAMETER);
	}

}
