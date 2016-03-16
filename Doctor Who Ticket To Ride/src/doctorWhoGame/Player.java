package doctorWhoGame;

import java.util.ArrayList;

public class Player {

	private PlayerColor color;
	private String name;
	private int score;
	private int trainCount;
	private Hand hand;

	public Player(String playerName, PlayerColor playerColor) {
		this.color = playerColor;
		this.name = playerName;
		this.score = 0;
		this.hand = new Hand();
	}

	public int getScore() {
		return this.score;
	}

	public String getName() {
		return this.name;
	}

	public int getTrainCount() {
		return this.trainCount;
	}

	public PlayerColor getColor() {
		return this.color;
	}

	public void addPoints(int points) {
		this.score = this.score + points;

	}

	public void removeTrainsFromPlayer(int numberOfTrainsToRemove) {
		this.trainCount = this.trainCount - numberOfTrainsToRemove;

	}

	public Hand getHand() {
		return this.hand;
	}

	public void addPath(Path givenPath) {
		this.hand.addPath(givenPath);
	}

	public void changeScoreFromRoutes() {
		int subtractScore = 0;
		int addRouteScore = 0;
		ArrayList<RouteCard> uncompletedRoutes = this.hand.getUncompletedRouteCards();
		for (int i = 0; i < uncompletedRoutes.size(); i++) {
			subtractScore = subtractScore + uncompletedRoutes.get(i).getPoints();
		}
		this.score = this.score - subtractScore;
		this.score = this.score + this.hand.getCompletedRouteScore();
	}

	public void addRouteCard(RouteCard r) {
		this.hand.addRouteCard(r);
	}

	public int getLongestPath() {
		return this.hand.getLongestLength();
	}

	public boolean isPathOwned(Path p2) {
		return this.hand.isPathOwned(p2);
	}

	public void setTrainCount(int trains) {
		this.trainCount = trains;
	}

}
