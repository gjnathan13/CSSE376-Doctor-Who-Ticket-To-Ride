package doctorWhoGame;

import java.util.ArrayList;

public class Game {

	private static ArrayList<Player> playerList;
	private static Player currentPlayer;
	private static Gameboard gameboard;
	private Scoreboard scoreboard;
	private Routeboard routeboard;

	public Game(Player[] givenPlayerList, Gameboard givenGameboard,
			Scoreboard givenScoreBoard, Routeboard givenRouteboard) {
		ArrayList<Player> playerArrayList = new ArrayList<Player>();
		for (int i = 0; i < givenPlayerList.length; i++) {
			playerArrayList.add(givenPlayerList[i]);
		}
		this.playerList = playerArrayList;
		this.currentPlayer = this.playerList.get(0);
		this.gameboard = givenGameboard;
		this.scoreboard = givenScoreBoard;
		this.routeboard = givenRouteboard;
	}

	public static void purchasePath(ArrayList<TrainColor> testList) {
		for (int i = 0; i < testList.size(); i++) {
			currentPlayer.getHand().removeTrainCard(testList.get(i));
		}

	}

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	public static void switchToNextPlayer() {
		int currentPlayerIndex = playerList.indexOf(currentPlayer);
		if (currentPlayerIndex == playerList.size() - 1) {
			currentPlayerIndex = -1;
		}
		currentPlayer = playerList.get(currentPlayerIndex + 1);
	}
	
	public static void updateGameboard(){
		gameboard.revalidate();
		gameboard.repaint();
	}

}
