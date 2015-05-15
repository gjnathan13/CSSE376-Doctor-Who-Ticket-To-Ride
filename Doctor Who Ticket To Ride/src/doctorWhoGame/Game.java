package doctorWhoGame;

import java.util.ArrayList;

import javax.swing.JLayeredPane;

public class Game {

	private static ArrayList<Player> playerList;
	private static Player currentPlayer;
	private static Boolean gameFinished;
	
	private static Gameboard gameboard;
	private static Scoreboard scoreboard;
	private static Routeboard routeboard;
	private static JLayeredPane layeredPane;
	private static RouteChoosingComponent routeBuyScreen;
	private static ArrayList<TrainColor> currentFaceUpCards;
	private static boolean CanDrawRainbow;
	private static boolean CanDrawAgain;
	private static boolean hasDrawnOne;
	private static int replaceCount;

	public Game(Player[] givenPlayerList, Gameboard givenGameboard,
			Scoreboard givenScoreBoard, Routeboard givenRouteboard) {
		this(givenPlayerList, givenGameboard, givenScoreBoard, givenRouteboard,
				null, null);
	}

	public Game(Player[] givenPlayerList, Gameboard givenGameboard,
			Scoreboard givenScoreBoard, Routeboard givenRouteboard,
			JLayeredPane givenLayeredPane, RouteChoosingComponent givenRouteBuyingScreen) {
		ArrayList<Player> playerArrayList = new ArrayList<Player>();
		for (int i = 0; i < givenPlayerList.length; i++) {
			playerArrayList.add(givenPlayerList[i]);
		}
		this.playerList = playerArrayList;
		this.currentPlayer = this.playerList.get(0);
		this.gameboard = givenGameboard;
		this.scoreboard = givenScoreBoard;
		this.routeboard = givenRouteboard;
		this.layeredPane = givenLayeredPane;
		this.routeBuyScreen = givenRouteBuyingScreen;
		this.replaceCount = 0;
		this.hasDrawnOne = false;
		this.CanDrawAgain = true;
		this.CanDrawRainbow = true;
		this.currentFaceUpCards = new ArrayList<TrainColor>();
		for (int i = 0; i < 5; i++) {
			this.currentFaceUpCards.add(TrainDeck.draw());
		}
		gameFinished=false;
	}

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}
	
	public static void endRouteSelection(){
		layeredPane.setLayer(routeBuyScreen, -1);
	}

	public static void updateGameboard() {
		gameboard.removeAll();
		gameboard.revalidate();
		gameboard.repaint();
	}

	public static void updateScoreboard() {
		scoreboard.removeAll();
		scoreboard.revalidate();
		scoreboard.repaint();
	}

	// TODO: Add Nodes to players map thinger
	public static void purchasePath(ArrayList<TrainColor> removeList,
			Path givenPath) {
		CanDrawAgain=false;
		
		for (int i = 0; i < removeList.size(); i++) {
			TrainColor currentCard = removeList.get(i);
			currentPlayer.getHand().removeTrainCard(currentCard);
			TrainDeck.discard(currentCard);
		}
		updateCurrentPlayerScore(removeList.size());
		currentPlayer.removeTrainsFromPlayer(removeList.size());
		currentPlayer.addPath(givenPath);

		updateGameboard();

	}

	private static void updateCurrentPlayerScore(int pathLength) {
		if (pathLength > 0 && pathLength < 7) {
			switch (pathLength) {
			case 1: {
				currentPlayer.addPoints(1);
				break;
			}
			case 2: {
				currentPlayer.addPoints(2);
				break;
			}
			case 3: {
				currentPlayer.addPoints(4);
				break;
			}
			case 4: {
				currentPlayer.addPoints(7);
				break;
			}
			case 5: {
				currentPlayer.addPoints(10);
				break;
			}
			case 6: {
				currentPlayer.addPoints(15);
				break;
			}
			}
		}

		updateScoreboard();

	}

	public static void switchToNextPlayer() {
		int currentPlayerIndex = playerList.indexOf(currentPlayer);
		CanDrawAgain = true;
		CanDrawRainbow = true;
		hasDrawnOne = false;
		replaceCount = 0;
		checkIfThreeRainbowsAreUpAndChangeIfNeeded();

		if (TrainDeck.size() == 0 && TrainDeck.discardSize() > 0) {
			TrainDeck.refillDeck();
			for (int i = 0; i < currentFaceUpCards.size(); i++) {
				if (currentFaceUpCards.get(i) == null && TrainDeck.size() > 0) {
					currentFaceUpCards.set(i, TrainDeck.draw());
					checkIfThreeRainbowsAreUpAndChangeIfNeeded();
				}
			}
		}

		if (currentPlayerIndex == playerList.size() - 1) {
			currentPlayerIndex = -1;
		}
		currentPlayer = playerList.get(currentPlayerIndex + 1);
		
		if(currentPlayer.getTrainCount()<3){
			gameFinished=true;
			finishGame();
		}

		updateScoreboard();
	}

	private static void finishGame() {
		// TODO Auto-generated method stub
		
	}

	private static void checkIfThreeRainbowsAreUpAndChangeIfNeeded() {
		int countOfRainbows = 0;
		for (int i = 0; i < currentFaceUpCards.size(); i++) {
			if (currentFaceUpCards.get(i) == TrainColor.Rainbow) {
				countOfRainbows++;
			}
		}

		if (countOfRainbows >= 3 && TrainDeck.size() > 0 && replaceCount < 4) {
			replaceCount++;
			for (int i = 0; i < currentFaceUpCards.size(); i++) {
				TrainColor currentCard = currentFaceUpCards.get(i);
				if (currentCard != null) {
					TrainDeck.discard(currentCard);
					currentFaceUpCards.set(i, TrainDeck.draw());
				}
			}
		}

		updateScoreboard();

	}

	public static ArrayList<TrainColor> getCurrentFaceup() {
		return currentFaceUpCards;
	}

	public static boolean chooseFaceupCardToTake(int index) {
		if(CanDrawAgain==false){
			return false;
		}
		
		// Choose from deck
		if (index == -1 && CanDrawAgain == true && TrainDeck.size() > 0) {
			currentPlayer.getHand().addTrainCard(TrainDeck.draw());
			if (hasDrawnOne == true) {
				CanDrawAgain = false;
			}
			if (hasDrawnOne == false) {
				hasDrawnOne = true;
				CanDrawRainbow = false;
			}
			updateGameboard();

			return true;
		}

		// Choose one of the face up
		if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
			TrainColor chosenCard = currentFaceUpCards.get(index);
			if (chosenCard != null && CanDrawAgain == true) {
				if (chosenCard == TrainColor.Rainbow && CanDrawRainbow == false) {
					return false;
				}
				currentPlayer.getHand().addTrainCard(chosenCard);
				// Fills slot
				if (TrainDeck.size() == 0) {
					TrainDeck.refillDeck();
					if (TrainDeck.size() == 0) {
						currentFaceUpCards.set(index, null);
					}
				}
				if (TrainDeck.size() > 0) {
					currentFaceUpCards.set(index, TrainDeck.draw());
					checkIfThreeRainbowsAreUpAndChangeIfNeeded();
				}
				// Change Booleans
				if (hasDrawnOne == true) {
					CanDrawAgain = false;
				}
				if (hasDrawnOne == false) {
					hasDrawnOne = true;
					CanDrawRainbow = false;
					if (chosenCard == TrainColor.Rainbow) {
						CanDrawAgain = false;
					}
				}
				updateGameboard();

				return true;
			}
		}

		return false;
	}

	public static void startRoutePurchasing() {
		layeredPane.setLayer(routeBuyScreen, 1);
	}

	public boolean checkIfCanBuyPath() {
		if(hasDrawnOne==false && CanDrawAgain==true){
			return true;
		}
		return false;
	}

}
