package doctorWhoGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JComponent;
import javax.swing.JLayeredPane;

public class Game {

	private static ArrayList<Player> playerList;
	private static Player currentPlayer;

	private static Gameboard gameboard;
	private static Scoreboard scoreboard;
	private static JLayeredPane layeredPane;
	private static RouteChoosingComponent routeBuyScreen;
	private static TurnShield blockScreen;
	private static ArrayList<Color> currentFaceUpCards;
	private static boolean CanDrawRainbow;
	private static boolean CanDrawAgain;
	private static boolean hasDrawnOne;
	private static int replaceCount;
	private static boolean lastTurn;
	private static EndGameComponent endGameScreen;
	private static RouteCardDeck routeCardDeck;
	private static HashMap<Integer, Integer> mapOfPoints;

	private static boolean isFirstTurn;
	private static Player firstPlayer;

	public Game(Player[] givenPlayerList, Gameboard givenGameboard, Scoreboard givenScoreBoard,
			Routeboard givenRouteboard, JLayeredPane givenLayeredPane, RouteChoosingComponent givenRouteBuyingScreen,
			TurnShield blockScreen, RouteCardDeck routes, EndGameComponent endGameScreen) {
		ArrayList<Player> playerArrayList = initializePlayerList(givenPlayerList);

		Game.playerList = playerArrayList;
		if (playerList != null) {
			Game.currentPlayer = Game.playerList.get(0);
			initializeCurrentPlayerTrainCardHand();
		}

		Game.gameboard = givenGameboard;
		Game.scoreboard = givenScoreBoard;
		Game.layeredPane = givenLayeredPane;
		Game.routeBuyScreen = givenRouteBuyingScreen;
		Game.blockScreen = blockScreen;
		Game.endGameScreen = endGameScreen;
		Game.replaceCount = 0;
		Game.hasDrawnOne = false;
		Game.CanDrawAgain = true;
		Game.CanDrawRainbow = true;
		Game.currentFaceUpCards = new ArrayList<Color>();
		for (int i = 0; i < 5; i++) {
			Game.currentFaceUpCards.add(TrainDeck.draw());
		}
		Game.mapOfPoints = new HashMap<Integer, Integer>();

		initializePointMap();
		lastTurn = false;

		Game.routeCardDeck = routes;

		Game.isFirstTurn = true;
		Game.firstPlayer = currentPlayer;
	}

	private ArrayList<Player> initializePlayerList(Player[] givenPlayerList) {
		if (givenPlayerList != null) {
			ArrayList<Player> playerArrayList = new ArrayList<Player>();
			for (int i = 0; i < givenPlayerList.length; i++) {
				initializePlayerTrainCount(givenPlayerList[i], givenPlayerList.length);
				playerArrayList.add(givenPlayerList[i]);
			}
			return playerArrayList;
		}
		return null;
	}

	private void initializePlayerTrainCount(Player player, int numberOfPlayers) {
		if (numberOfPlayers > 2) {
			player.setTrainCount(45 - (numberOfPlayers - 2) * 5);
		} else {
			player.setTrainCount(45);
		}
	}

	private static void initializeCurrentPlayerTrainCardHand() {
		for (int i = 0; i < 4; i++) {
			currentPlayer.getHand().addTrainCard(TrainDeck.draw());
		}
	}

	private void initializePointMap() {
		mapOfPoints.put(1, 1);
		mapOfPoints.put(2, 2);
		mapOfPoints.put(3, 4);
		mapOfPoints.put(4, 7);
		mapOfPoints.put(5, 10);
		mapOfPoints.put(6, 15);
	}

	public static Player getCurrentPlayer() {
		return currentPlayer;
	}

	public static void endRouteSelection() {
		routeBuyScreen.setPurchasing(false);
		layeredPane.setLayer(routeBuyScreen, -1);
		if (isFirstTurn) {
			switchToNextPlayer();
		}
	}

	public static void updateGivenJComponent(JComponent component) {
		component.removeAll();
		component.revalidate();
		component.repaint();
	}

	public static void purchasePath(ArrayList<Color> removeList, Path givenPath) {

		CanDrawAgain = false;

		removeTrainCardsFromHand(removeList);

		updateCurrentPlayerScore(removeList.size());
		currentPlayer.removeTrainsFromPlayer(removeList.size());
		currentPlayer.addPath(givenPath);

		updateGivenJComponent(gameboard);

	}

	private static void removeTrainCardsFromHand(ArrayList<Color> removeList) {
		for (int i = 0; i < removeList.size(); i++) {
			Color currentCard = removeList.get(i);
			currentPlayer.getHand().removeTrainCard(currentCard);
			TrainDeck.discard(currentCard);
		}
	}

	private static void updateCurrentPlayerScore(int pathLength) {
		if (pathLength > 0 && pathLength < 7) {
			int pointsToAdd = mapOfPoints.get(pathLength);
			currentPlayer.addPoints(pointsToAdd);

		}

		updateGivenJComponent(scoreboard);

	}

	public static void switchToNextPlayer() {
		if (!gameboard.getPurchasing()) {
			if (lastTurn == true) {
				finishGame();
			}
			gameboard.resetOnNewPlayer();
			int currentPlayerIndex = playerList.indexOf(currentPlayer);
			CanDrawAgain = true;
			CanDrawRainbow = true;
			hasDrawnOne = false;
			replaceCount = 0;
			checkIfThreeRainbowsAreUpAndChangeIfNeeded();

			if (TrainDeck.size() == 0 && TrainDeck.discardSize() > 0) {
				TrainDeck.refillDeck();
				refillFaceUpTrainCards();
			}

			if (currentPlayerIndex == playerList.size() - 1) {
				currentPlayerIndex = -1;
			}
			currentPlayer = playerList.get(currentPlayerIndex + 1);

			if (isFirstTurn) {
				if (currentPlayer != firstPlayer) {
					initializeCurrentPlayerTrainCardHand();
				} else if (currentPlayer == firstPlayer) {
					isFirstTurn = false;
				}
			}

			if (currentPlayer.getTrainCount() < 3) {
				lastTurn = true;
			}

			updateGivenJComponent(scoreboard);
			scoreboard.setRecent(null, -1);
			scoreboard.resetDrawCount();
			if (blockScreen != null) {
				blockScreen(true);
			}
			if (isFirstTurn) {
				startRoutePurchasing();
			}
		}
	}

	private static void refillFaceUpTrainCards() {
		for (int i = 0; i < currentFaceUpCards.size(); i++) {
			if (currentFaceUpCards.get(i) == null && TrainDeck.size() > 0) {
				currentFaceUpCards.set(i, TrainDeck.draw());
				checkIfThreeRainbowsAreUpAndChangeIfNeeded();
			}
		}
	}

	private static void finishGame() {
		int longestPath = 0;
		ArrayList<Integer> playersWithLongestPathIndex = new ArrayList<Integer>();
		for (int i = 0; i < playerList.size(); i++) {
			playerList.get(i).changeScoreFromRoutes();
			int longestPathScore = playerList.get(i).getLongestPath();
			if (longestPathScore > longestPath) {
				longestPath = longestPathScore;
				playersWithLongestPathIndex.clear();
				playersWithLongestPathIndex.add(i);
			} else if (longestPathScore == longestPath) {
				playersWithLongestPathIndex.add(i);
			}
		}
		for (int i = 0; i < playersWithLongestPathIndex.size(); i++) {
			playerList.get(i).addPoints(10);
		}

		endGameScreen.setGameOver();
		layeredPane.setLayer(endGameScreen, 4);

	}

	private static void checkIfThreeRainbowsAreUpAndChangeIfNeeded() {
		int countOfRainbows = 0;
		for (int i = 0; i < currentFaceUpCards.size(); i++) {
			if (currentFaceUpCards.get(i) == Color.GRAY) {
				countOfRainbows++;
			}
		}

		if (countOfRainbows >= 3 && TrainDeck.size() > 0 && replaceCount < 4) {
			replaceCount++;
			for (int i = 0; i < currentFaceUpCards.size(); i++) {
				Color currentCard = currentFaceUpCards.get(i);
				if (currentCard != null) {
					TrainDeck.discard(currentCard);
					currentFaceUpCards.set(i, TrainDeck.draw());
				}
			}
		}

		updateGivenJComponent(scoreboard);

	}

	public static ArrayList<Color> getCurrentFaceup() {
		return currentFaceUpCards;
	}

	public static boolean chooseFaceupCardToTake(int index) {
		if (!gameboard.getPurchasing()) {
			if (CanDrawAgain == false) {
				return false;
			}

			// Choose from deck
			if (index == -1 && CanDrawAgain == true && TrainDeck.size() > 0) {
				chooseCardFromDeck();
				scoreboard.setRecent(null, -1);

				return true;
			}

			// Choose one of the face up
			if (index == 0 || index == 1 || index == 2 || index == 3 || index == 4) {
				Color chosenCard = currentFaceUpCards.get(index);
				if (chosenCard != null && CanDrawAgain == true) {
					if (chosenCard == Color.GRAY && CanDrawRainbow == false) {
						return false;
					}

					chooseFaceupCard(chosenCard, index);
					scoreboard.increasedFaceUpCardDrawNumber();
					scoreboard.setRecent(chosenCard, index);
					return true;
				}
			}

			return false;
		}
		return false;
	}

	private static void chooseCardFromDeck() {
		currentPlayer.getHand().addTrainCard(TrainDeck.draw());
		if (hasDrawnOne == true) {
			CanDrawAgain = false;
		}
		if (hasDrawnOne == false) {
			hasDrawnOne = true;
			CanDrawRainbow = false;
		}
		updateGivenJComponent(gameboard);
	}

	private static void chooseFaceupCard(Color chosenCard, int indexOfSelectedCard) {
		currentPlayer.getHand().addTrainCard(chosenCard);
		// Fills slot
		if (TrainDeck.size() == 0) {
			TrainDeck.refillDeck();
			if (TrainDeck.size() == 0) {
				currentFaceUpCards.set(indexOfSelectedCard, null);
			}
		}
		if (TrainDeck.size() > 0) {
			currentFaceUpCards.set(indexOfSelectedCard, TrainDeck.draw());
			checkIfThreeRainbowsAreUpAndChangeIfNeeded();
		}
		if (hasDrawnOne == true) {
			CanDrawAgain = false;
		}
		if (hasDrawnOne == false) {
			hasDrawnOne = true;
			CanDrawRainbow = false;
			if (chosenCard == Color.GRAY) {
				CanDrawAgain = false;
			}
		}
		updateGivenJComponent(gameboard);
	}

	public static void startRoutePurchasing() {
		if (CanDrawAgain && !hasDrawnOne && routeCardDeck.size() > 0) {
			routeBuyScreen.setPurchasing(true);
			layeredPane.setLayer(routeBuyScreen, 1);
			scoreboard.setRecent(null, -2);
		}
	}

	public static void blockScreen(boolean blockScreenNow) {
		if (blockScreenNow) {
			layeredPane.setLayer(blockScreen, 2);
		} else {
			layeredPane.setLayer(blockScreen, -1);
		}
	}

	public static ArrayList<Player> getPlayerList() {
		return playerList;
	}

	public static boolean checkIfCanBuyPath() {
		if (!hasDrawnOne && CanDrawAgain) {
			return true;
		}
		return false;
	}

	public static void addRouteCardsToHand(ArrayList<RouteCard> selectedCards) {
		if (selectedCards.size() > 0) {
			CanDrawAgain = false;
			CanDrawRainbow = false;
			hasDrawnOne = true;
		}
		for (int i = 0; i < selectedCards.size(); i++) {
			currentPlayer.addRouteCard(selectedCards.get(i));
		}

	}

	public static boolean getIsItFirstTurn() {
		return isFirstTurn;
	}

	public static JComponent getGameBoard() {
		return gameboard;
	}

	public static boolean checkIfCanDrawAgain() {
		return CanDrawAgain;
	}

	public static boolean checkIfHasDrawnOne() {
		return hasDrawnOne;
	}
}
