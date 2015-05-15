package doctorWhoGame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class GameTest {

	private Player[] playerList;
	private Game testGame;

	@Before
	public void testSetUp() throws IllegalArgumentException,
			IllegalAccessException, NoSuchFieldException, SecurityException,
			NoSuchMethodException, InvocationTargetException {
		Player mockPlayer1 = createMock(Player.class);
		Player mockPlayer2 = createMock(Player.class);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);
		this.playerList = players.toArray(new Player[players.size()]);
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		TurnShield mockTurnShield = createMock(TurnShield.class);
		JLayeredPane mockPane = createMock(JLayeredPane.class);
		this.testGame = new Game(this.playerList, mockGameboard,
				mockScoreboard, mockRouteboard, mockPane, null, mockTurnShield);
		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);

		TrainDeck testDeck = new TrainDeck();
		Field deckField = TrainDeck.class.getDeclaredField("deck");
		deckField.setAccessible(true);

		Method renewDeckMethod = TrainDeck.class.getDeclaredMethod(
				"getNewDeck", null);
		renewDeckMethod.setAccessible(true);
		ArrayList<TrainColor> newDeck = (ArrayList<TrainColor>) renewDeckMethod
				.invoke(testDeck, null);

		deckField.set(null, newDeck);

	}

	// This is an integration test
	@Test
	public void testPurchasePathIntegrationTest() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);
		Player currentPlayer = this.testGame.getCurrentPlayer();
		for (int i = 0; i < 8; i++) {
			currentPlayer.getHand().addTrainCard(TrainColor.Red);
		}
		ArrayList<TrainColor> removeList = new ArrayList<TrainColor>();
		for (int i = 0; i < 6; i++) {
			removeList.add(TrainColor.Red);
		}
		ArrayList<ArrayList<TrainColor>> overallList = new ArrayList<ArrayList<TrainColor>>();

		ArrayList<TrainColor> finalList = new ArrayList<TrainColor>();
		finalList.add(TrainColor.Red);
		finalList.add(TrainColor.Red);
		overallList.add(finalList);
		for (int i = 0; i < 8; i++) {
			overallList.add(new ArrayList<TrainColor>());
		}
		TrainDeck testDeck = new TrainDeck();
		testDeck.refillDeck();

		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Path mockPath = new Path(n1, n2);

		this.testGame.purchasePath(removeList, mockPath);
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<TrainColor> discardList = (ArrayList<TrainColor>) discardField
				.get(testDeck);

		Field trainCardField = Hand.class.getDeclaredField("trainCards");
		trainCardField.setAccessible(true);
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> handTrainCardList = (ArrayList<ArrayList<String>>) trainCardField
				.get(currentPlayer.getHand());

		assertEquals(handTrainCardList, overallList);
		assertEquals(discardList.size(), removeList.size());
		assertEquals(15, this.testGame.getCurrentPlayer().getScore());
		assertEquals(39, this.testGame.getCurrentPlayer().getTrainCount());

		TrainDeck.refillDeck();

	}

	@Test
	public void testSwitchPlayer() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		// EasyMock.replay(this.playerList[0]);
		// EasyMock.replay(this.playerList[1]);

		ArrayList<Player> players = new ArrayList<Player>();
		Player testFirstPlayer = new Player("testFirst", PlayerColor.Green);
		Player testSecondPlayer = new Player("testSecond", PlayerColor.Blue);
		players.add(testFirstPlayer);
		players.add(testSecondPlayer);

		Field firstPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);
		firstPlayerTrainCount.set(testFirstPlayer, 3);

		Field secondPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);
		secondPlayerTrainCount.set(testSecondPlayer, 2);

		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		Field canDrawRainbowField = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField
				.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		Field replaceCountField = Game.class.getDeclaredField("replaceCount");
		replaceCountField.setAccessible(true);
		int replaceCountInt = (int) replaceCountField.get(testGame);

		canDrawRainbowField.set(this.testGame, false);
		canDrawAgainField.set(this.testGame, false);
		hasDrawnOneField.set(this.testGame, true);
		replaceCountField.set(this.testGame, 2);

		assertEquals(this.testGame.getCurrentPlayer(), players.get(0));
		this.testGame.switchToNextPlayer();

		Field canDrawRainbowField2 = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField2.setAccessible(true);
		Boolean canDrawRainbowBoolean2 = (Boolean) canDrawRainbowField2
				.get(testGame);

		Field canDrawAgainField2 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField2.setAccessible(true);
		Boolean canDrawAgainBoolean2 = (Boolean) canDrawAgainField2
				.get(testGame);

		Field hasDrawnOneField2 = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField2.setAccessible(true);
		Boolean hasDrawnOneBoolean2 = (Boolean) hasDrawnOneField2.get(testGame);

		Field replaceCountField2 = Game.class.getDeclaredField("replaceCount");
		replaceCountField2.setAccessible(true);
		int replaceCountInt2 = (int) replaceCountField2.get(testGame);

		assertEquals(true, canDrawRainbowBoolean2);
		assertEquals(true, canDrawAgainBoolean2);
		assertEquals(false, hasDrawnOneBoolean2);
		assertEquals(0, replaceCountInt2);

		assertEquals(this.testGame.getCurrentPlayer(), players.get(1));
	}

	@Test
	public void testSwtichPlayerEndOfList() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		// EasyMock.replay(this.playerList[0]);
		// EasyMock.replay(this.playerList[1]);

		ArrayList<Player> players = new ArrayList<Player>();
		Player testFirstPlayer = new Player("testFirst", PlayerColor.Green);
		Player testSecondPlayer = new Player("testSecond", PlayerColor.Blue);
		players.add(testFirstPlayer);
		players.add(testSecondPlayer);

		Field firstPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);
		firstPlayerTrainCount.set(testFirstPlayer, 13);

		Field secondPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);
		secondPlayerTrainCount.set(testSecondPlayer, 12);

		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(2);
		EasyMock.replay(mockGameboard);
		assertEquals(this.testGame.getCurrentPlayer(), players.get(0));
		this.testGame.switchToNextPlayer();
		this.testGame.switchToNextPlayer();
		assertEquals(this.testGame.getCurrentPlayer(), players.get(0));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseTwoRegularFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);
		Player currentPlayer = this.testGame.getCurrentPlayer();

		Field currentFaceField = Game.class
				.getDeclaredField("currentFaceUpCards");
		currentFaceField.setAccessible(true);
		ArrayList<TrainColor> faceUpList = (ArrayList<TrainColor>) currentFaceField
				.get(testGame);
		ArrayList<TrainColor> newFaceUpList = new ArrayList<TrainColor>();
		newFaceUpList.add(TrainColor.Red);
		newFaceUpList.add(TrainColor.Rainbow);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Rainbow);
		currentFaceField.set(this.testGame, newFaceUpList);

		faceUpList = (ArrayList<TrainColor>) currentFaceField.get(testGame);

		Field canDrawRainbowField = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField
				.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(4);
		mockGameboard.removeAll();
		EasyMock.expectLastCall().times(2);
		mockGameboard.revalidate();
		EasyMock.expectLastCall().times(2);
		mockGameboard.repaint();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);
		assertTrue(this.testGame.chooseFaceupCardToTake(0));

		Field canDrawRainbowField2 = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField2.setAccessible(true);
		Boolean canDrawRainbowBoolean2 = (Boolean) canDrawRainbowField2
				.get(testGame);

		Field canDrawAgainField2 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField2.setAccessible(true);
		Boolean canDrawAgainBoolean2 = (Boolean) canDrawAgainField2
				.get(testGame);

		Field hasDrawnOneField2 = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField2.setAccessible(true);
		Boolean hasDrawnOneBoolean2 = (Boolean) hasDrawnOneField2.get(testGame);

		assertFalse(canDrawRainbowBoolean2);
		assertTrue(canDrawAgainBoolean2);
		assertTrue(hasDrawnOneBoolean2);

		assertFalse(this.testGame.chooseFaceupCardToTake(1));
		assertTrue(this.testGame.chooseFaceupCardToTake(2));

		Field canDrawRainbowField3 = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField3.setAccessible(true);
		Boolean canDrawRainbowBoolean3 = (Boolean) canDrawRainbowField3
				.get(testGame);

		Field canDrawAgainField3 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField3.setAccessible(true);
		Boolean canDrawAgainBoolean3 = (Boolean) canDrawAgainField3
				.get(testGame);

		Field hasDrawnOneField3 = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField3.setAccessible(true);
		Boolean hasDrawnOneBoolean3 = (Boolean) hasDrawnOneField3.get(testGame);

		assertFalse(canDrawRainbowBoolean3);
		assertFalse(canDrawAgainBoolean3);

		assertFalse(this.testGame.chooseFaceupCardToTake(3));

		assertEquals(TrainColor.Green, faceUpList.get(3));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseOneRaibowFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);
		Player currentPlayer = this.testGame.getCurrentPlayer();

		Field currentFaceField = Game.class
				.getDeclaredField("currentFaceUpCards");
		currentFaceField.setAccessible(true);
		ArrayList<TrainColor> newFaceUpList = new ArrayList<TrainColor>();
		newFaceUpList.add(TrainColor.Red);
		newFaceUpList.add(TrainColor.Rainbow);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Rainbow);
		currentFaceField.set(this.testGame, newFaceUpList);
		ArrayList<TrainColor> faceUpList = (ArrayList<TrainColor>) currentFaceField
				.get(testGame);

		Field canDrawRainbowField = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField
				.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(3);
		mockGameboard.removeAll();
		EasyMock.expectLastCall();
		mockGameboard.revalidate();
		EasyMock.expectLastCall();
		mockGameboard.repaint();
		EasyMock.expectLastCall();
		EasyMock.replay(mockGameboard);
		assertTrue(this.testGame.chooseFaceupCardToTake(1));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertFalse(canDrawAgainBoolean);
		assertTrue(hasDrawnOneBoolean);

		assertFalse(this.testGame.chooseFaceupCardToTake(3));

		faceUpList = (ArrayList<TrainColor>) currentFaceField.get(testGame);

		assertEquals(TrainColor.Green, faceUpList.get(3));

		assertFalse(this.testGame.chooseFaceupCardToTake(4));

		faceUpList = (ArrayList<TrainColor>) currentFaceField.get(testGame);

		assertEquals(TrainColor.Rainbow, faceUpList.get(4));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseFromDeckThenFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		Player currentPlayer = this.testGame.getCurrentPlayer();

		Field currentFaceField = Game.class
				.getDeclaredField("currentFaceUpCards");
		currentFaceField.setAccessible(true);
		ArrayList<TrainColor> faceUpList = (ArrayList<TrainColor>) currentFaceField
				.get(testGame);
		ArrayList<TrainColor> newFaceUpList = new ArrayList<TrainColor>();
		newFaceUpList.add(TrainColor.Red);
		newFaceUpList.add(TrainColor.Rainbow);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Rainbow);
		currentFaceField.set(this.testGame, newFaceUpList);

		Field canDrawRainbowField = Game.class
				.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField
				.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(4);
		mockGameboard.removeAll();
		EasyMock.expectLastCall().times(2);
		mockGameboard.revalidate();
		EasyMock.expectLastCall().times(2);
		mockGameboard.repaint();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);
		assertTrue(this.testGame.chooseFaceupCardToTake(-1));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertTrue(hasDrawnOneBoolean);

		assertFalse(this.testGame.chooseFaceupCardToTake(1));
		assertTrue(this.testGame.chooseFaceupCardToTake(2));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertFalse(canDrawAgainBoolean);

		assertFalse(this.testGame.chooseFaceupCardToTake(3));

		faceUpList = (ArrayList<TrainColor>) currentFaceField.get(testGame);

		assertEquals(TrainColor.Green, faceUpList.get(3));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testUpdateCurrentPlayerScore() throws NoSuchMethodException,
			SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);
		Player testPlayer = this.testGame.getCurrentPlayer();
		Method addPoints = Game.class.getDeclaredMethod(
				"updateCurrentPlayerScore", int.class);

		addPoints.setAccessible(true);

		assertEquals(0, testPlayer.getScore());
		addPoints.invoke(testGame, 1);
		assertEquals(1, testPlayer.getScore());
		addPoints.invoke(testGame, 2);
		assertEquals(3, testPlayer.getScore());
		addPoints.invoke(testGame, 3);
		assertEquals(7, testPlayer.getScore());
		addPoints.invoke(testGame, 4);
		assertEquals(14, testPlayer.getScore());
		addPoints.invoke(testGame, 5);
		assertEquals(24, testPlayer.getScore());
		addPoints.invoke(testGame, 6);
		assertEquals(39, testPlayer.getScore());
		addPoints.invoke(testGame, -1);
		assertEquals(39, testPlayer.getScore());
		addPoints.invoke(testGame, 7);
		assertEquals(39, testPlayer.getScore());
	}

	@Test
	public void testRainbowSwitchCheckAndChangeFunction()
			throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException,
			NoSuchMethodException, InvocationTargetException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test", PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		// EasyMock.replay(mockGameboard);
		// EasyMock.replay(mockScoreboard);
		// EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		Field currentFaceField = Game.class
				.getDeclaredField("currentFaceUpCards");
		currentFaceField.setAccessible(true);
		ArrayList<TrainColor> faceUpList = (ArrayList<TrainColor>) currentFaceField
				.get(testGame);
		ArrayList<TrainColor> newFaceUpList = new ArrayList<TrainColor>();
		newFaceUpList.add(TrainColor.Rainbow);
		newFaceUpList.add(TrainColor.Rainbow);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Green);
		newFaceUpList.add(TrainColor.Rainbow);
		ArrayList<TrainColor> testList = new ArrayList<TrainColor>();
		testList.add(TrainColor.Rainbow);
		testList.add(TrainColor.Rainbow);
		testList.add(TrainColor.Green);
		testList.add(TrainColor.Green);
		testList.add(TrainColor.Rainbow);
		currentFaceField.set(this.testGame, newFaceUpList);

		Method checkRainbowMethod = Game.class.getDeclaredMethod(
				"checkIfThreeRainbowsAreUpAndChangeIfNeeded", null);
		checkRainbowMethod.setAccessible(true);
		checkRainbowMethod.invoke(this.testGame, null);

		ArrayList<TrainColor> faceUpListTwo = (ArrayList<TrainColor>) currentFaceField
				.get(testGame);
		assertNotEquals(faceUpListTwo, testList);

		Field replaceField = Game.class.getDeclaredField("replaceCount");
		replaceField.setAccessible(true);
		int replaceCount = (int) replaceField.get(testGame);

		assertTrue(replaceCount > 0);
	}

	@Test
	// 0,1,2 trains game ends
	public void testEndGamePlayerSwitch() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		Player testFirstPlayer = new Player("testFirst", PlayerColor.Green);
		Player testSecondPlayer = new Player("testSecond", PlayerColor.Blue);
		players.add(testFirstPlayer);
		players.add(testSecondPlayer);

		Field firstPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);
		firstPlayerTrainCount.set(testFirstPlayer, 3);

		Field secondPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);
		secondPlayerTrainCount.set(testSecondPlayer, 2);

		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		Field gameCurrentPlayer = Game.class.getDeclaredField("currentPlayer");
		gameCurrentPlayer.setAccessible(true);
		gameCurrentPlayer.set(this.testGame, testSecondPlayer);

		Field gameDoneOne = Game.class.getDeclaredField("gameFinished");
		gameDoneOne.setAccessible(true);
		Boolean gameDoneBooleanOne = (Boolean) gameDoneOne.get(this.testGame);

		Field lastTurnOne = Game.class.getDeclaredField("lastTurn");
		lastTurnOne.setAccessible(true);
		Boolean lastTurnBooleanOne = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(false, gameDoneBooleanOne);
		assertEquals(false, lastTurnBooleanOne);

		Player currentPlayer = (Player) gameCurrentPlayer.get(this.testGame);
		assertEquals(2, currentPlayer.getTrainCount());

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(3);
		EasyMock.replay(mockGameboard);
		this.testGame.switchToNextPlayer();

		Boolean gameDoneBooleanTwo = (Boolean) gameDoneOne.get(this.testGame);
		Boolean lastTurnBooleanTwo = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(false, gameDoneBooleanTwo);
		assertEquals(false, lastTurnBooleanTwo);

		Player currentPlayerTwo = (Player) gameCurrentPlayer.get(this.testGame);
		assertEquals(3, currentPlayerTwo.getTrainCount());

		this.testGame.switchToNextPlayer();

		Boolean gameDoneBooleanThree = (Boolean) gameDoneOne.get(this.testGame);
		Boolean lastTurnBooleanThree = (Boolean) lastTurnOne.get(this.testGame);

		Player currentPlayerThree = (Player) gameCurrentPlayer
				.get(this.testGame);
		assertEquals(2, currentPlayer.getTrainCount());

		assertEquals(false, gameDoneBooleanThree);
		assertEquals(true, lastTurnBooleanThree);

		this.testGame.switchToNextPlayer();

		Boolean gameDoneBooleanFour = (Boolean) gameDoneOne.get(this.testGame);
		Boolean lastTurnBooleanFour = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(true, gameDoneBooleanFour);
		assertEquals(true, lastTurnBooleanFour);

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testOfFinishGame() throws NoSuchFieldException,
			SecurityException, IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		Player testFirstPlayer = new Player("testFirst", PlayerColor.Green);
		Player testSecondPlayer = new Player("testSecond", PlayerColor.Blue);
		players.add(testFirstPlayer);
		players.add(testSecondPlayer);

		Field firstPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);
		firstPlayerTrainCount.set(testFirstPlayer, 3);

		Field secondPlayerTrainCount = Player.class
				.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);
		secondPlayerTrainCount.set(testSecondPlayer, 2);

		Field firstPlayerRouteScore = Hand.class
				.getDeclaredField("completedRouteScore");
		firstPlayerRouteScore.setAccessible(true);
		firstPlayerRouteScore.set(testFirstPlayer.getHand(), 24);

		Field secondPlayerRouteScore = Hand.class
				.getDeclaredField("completedRouteScore");
		secondPlayerRouteScore.setAccessible(true);
		secondPlayerRouteScore.set(testSecondPlayer.getHand(), 42);

		Field firstPlayerScore = Player.class.getDeclaredField("score");
		firstPlayerScore.setAccessible(true);
		firstPlayerScore.set(testFirstPlayer, 0);

		Field secondPlayerScore = Player.class.getDeclaredField("score");
		secondPlayerScore.setAccessible(true);
		secondPlayerScore.set(testSecondPlayer, 17);

		Field firstPlayerUncompletedRoutes = Hand.class
				.getDeclaredField("uncompletedRouteCards");
		firstPlayerUncompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> firstTestUncompletedList = new ArrayList<RouteCard>();
		firstTestUncompletedList.add(new RouteCard(0, new Node(0), new Node(1),
				7));
		firstTestUncompletedList.add(new RouteCard(0, new Node(2), new Node(3),
				2));

		firstPlayerUncompletedRoutes.set(testFirstPlayer.getHand(),
				firstTestUncompletedList);

		Field secondPlayerUncompletedRoutes = Hand.class
				.getDeclaredField("uncompletedRouteCards");
		secondPlayerUncompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> secondTestUncompletedList = new ArrayList<RouteCard>();

		secondPlayerUncompletedRoutes.set(testSecondPlayer.getHand(),
				secondTestUncompletedList);

		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);

		this.testGame = new Game(players.toArray(new Player[players.size()]),
				mockGameboard, mockScoreboard, mockRouteboard);

		Field gameCurrentPlayer = Game.class.getDeclaredField("currentPlayer");
		gameCurrentPlayer.setAccessible(true);
		gameCurrentPlayer.set(testGame, testFirstPlayer);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false)
				.times(2);
		EasyMock.replay(mockGameboard);
		this.testGame.switchToNextPlayer();
		this.testGame.switchToNextPlayer();

		//Extra 10 for longest route
		assertEquals(25, testFirstPlayer.getScore());
		
		//Also has longest route
		assertEquals(69, testSecondPlayer.getScore());

		EasyMock.verify(mockGameboard);
		;

	}
}
