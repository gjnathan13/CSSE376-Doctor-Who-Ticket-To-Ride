package doctorWhoGame;

import java.util.ArrayList;

public class Game {

	private ArrayList<Player> playerList;
	private Player currentPlayer;
	private Gameboard gameboard;
	private Scoreboard scoreboard;
	private Routeboard routeboard;
	private ArrayList<TrainColor> currentFaceUpCards;
	private boolean CanDrawRainbow;
	private boolean CanDrawAgain;

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
		this.currentFaceUpCards = new ArrayList<TrainColor>();
		for (int i = 0; i < 5; i++) {
			this.currentFaceUpCards.add(TrainDeck.draw());
		}
	}

	// Add Nodes to players map thinger
	public void purchasePath(ArrayList<TrainColor> removeList) {
		for (int i = 0; i < removeList.size(); i++) {
			TrainColor currentCard = removeList.get(i);
			this.currentPlayer.getHand().removeTrainCard(currentCard);
			TrainDeck.discard(currentCard);
		}
		this.updateCurrenPlayerScore(removeList.size());
		this.currentPlayer.removeTrainsFromPlayer(removeList.size());

	}

	private void updateCurrenPlayerScore(int pathLength) {
		if (pathLength > 0 && pathLength < 7) {
			switch (pathLength) {
			case 1: {
				this.currentPlayer.addPoints(1);
				break;
			}
			case 2: {
				this.currentPlayer.addPoints(2);
				break;
			}
			case 3: {
				this.currentPlayer.addPoints(4);
				break;
			}
			case 4: {
				this.currentPlayer.addPoints(7);
				break;
			}
			case 5: {
				this.currentPlayer.addPoints(10);
				break;
			}
			case 6: {
				this.currentPlayer.addPoints(15);
				break;
			}
			}
		}

	}

	public Player getCurrentPlayer() {
		return this.currentPlayer;
	}

	public void switchToNextPlayer() {
		int currentPlayerIndex = this.playerList.indexOf(this.currentPlayer);
		if (currentPlayerIndex == this.playerList.size() - 1) {
			currentPlayerIndex = -1;
		}
		this.currentPlayer = this.playerList.get(currentPlayerIndex + 1);
	}

	public ArrayList<TrainColor> getCurrentFaceup() {
		return this.currentFaceUpCards;
	}

	 public boolean chooseFaceupCardToTake(int index){
	
		 return false;
	 }

}
