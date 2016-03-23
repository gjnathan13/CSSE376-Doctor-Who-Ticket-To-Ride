package doctorWhoGame;

import java.awt.Color;
import java.util.ArrayList;

public class Player {

	private Color color;
	private String name;
	private int score;
	private int trainCount;
	private Hand hand;

	public Player(String playerName, Color playerColor) {
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

	public Color getColor() {
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
		int routeScore = 0;
		ArrayList<RouteCard> uncompletedRoutes = this.hand.getUncompletedRouteCards();
		for (RouteCard card : uncompletedRoutes) {
			routeScore -= card.getPoints();
		}
		ArrayList<RouteCard> completedRoutes = this.hand.getCompletedRouteCards();
		for (RouteCard card : completedRoutes) {
			routeScore += card.getPoints();
		}
		this.score = this.score + routeScore;
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
