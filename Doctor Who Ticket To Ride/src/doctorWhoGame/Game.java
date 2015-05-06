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
	private boolean hasDrawnOne;

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
		this.hasDrawnOne=false;
		this.CanDrawAgain=true;
		this.CanDrawRainbow=true;
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

	// refill deck if empty and add cards back to face up if missing.
	public void switchToNextPlayer() {
		int currentPlayerIndex = this.playerList.indexOf(this.currentPlayer);
		this.CanDrawAgain = true;
		this.CanDrawRainbow = true;
		this.hasDrawnOne = false;
		if (currentPlayerIndex == this.playerList.size() - 1) {
			currentPlayerIndex = -1;
		}
		this.currentPlayer = this.playerList.get(currentPlayerIndex + 1);
	}

	public ArrayList<TrainColor> getCurrentFaceup() {
		return this.currentFaceUpCards;
	}

	public boolean chooseFaceupCardToTake(int index) {
		//Choose from deck
		if(index==-1 && this.CanDrawAgain==true && TrainDeck.size()>0){
			this.currentPlayer.getHand().addTrainCard(TrainDeck.draw());
			if(this.hasDrawnOne==true){
				this.CanDrawAgain=false;
			}
			if(this.hasDrawnOne==false){
				this.hasDrawnOne=true;
				this.CanDrawRainbow=false;
			}
			return true;
		}
		
		//Choose one of the face up
		if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
			TrainColor chosenCard = this.currentFaceUpCards.get(index);
			if (chosenCard != null && this.CanDrawAgain == true) {
				if(chosenCard==TrainColor.Rainbow && this.CanDrawRainbow==false){
					return false;
				}
				this.currentPlayer.getHand().addTrainCard(chosenCard);
				//Fills slot
				if(TrainDeck.size()==0){
					this.currentFaceUpCards.set(index, null);
				}
				if(TrainDeck.size()>0){
					this.currentFaceUpCards.set(index,TrainDeck.draw());
				}
				//Change Booleans
				if(this.hasDrawnOne==true){
					this.CanDrawAgain=false;
				}
				if(this.hasDrawnOne==false){
					this.hasDrawnOne=true;
					this.CanDrawRainbow=false;
				}
				return true;
			}
		}

		return false;
	}

}
