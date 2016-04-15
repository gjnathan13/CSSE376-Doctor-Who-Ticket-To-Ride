package doctorWhoGame;

import static org.easymock.EasyMock.createMock;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import javax.swing.JLayeredPane;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

public class GameTest {

	private Player[] playerList;
	private Game testGame;
	private Player mockPlayer1;
	private Player mockPlayer2;
	private Gameboard mockGameboard;
	private Scoreboard mockScoreboard;
	private Routeboard mockRouteboard;
	private TurnShield mockTurnShield;
	private ArrayList<Color> faceUpList;
	private Field currentFaceField;
	private ArrayList<Player> players;
	private JLayeredPane mockPane;
	private EndGameComponent mockEnd;

	@Before
	public void testSetUp() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, NoSuchMethodException, InvocationTargetException {
		this.mockPlayer1 = new Player("testOne", Color.BLUE);
		this.mockPlayer2 = new Player("testTwo", Color.GREEN);
		this.players = new ArrayList<Player>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);
		this.playerList = players.toArray(new Player[players.size()]);
		this.mockGameboard = createMock(Gameboard.class);
		this.mockScoreboard = createMock(Scoreboard.class);
		this.mockRouteboard = createMock(Routeboard.class);
		this.mockTurnShield = createMock(TurnShield.class);
		this.mockPane = createMock(JLayeredPane.class);
		this.mockEnd = createMock(EndGameComponent.class);
		this.testGame = new Game(this.playerList, mockGameboard, mockScoreboard, mockRouteboard, mockPane, null,
				mockTurnShield, null,mockEnd);

		TrainDeck testDeck = new TrainDeck();
		Field deckField = TrainDeck.class.getDeclaredField("deck");
		deckField.setAccessible(true);

		Method renewDeckMethod = TrainDeck.class.getDeclaredMethod("getNewDeck", null);
		renewDeckMethod.setAccessible(true);
		ArrayList<Color> newDeck = (ArrayList<Color>) renewDeckMethod.invoke(testDeck, null);

		deckField.set(null, newDeck);
		
		this.currentFaceField = Game.class.getDeclaredField("currentFaceUpCards");
		this.currentFaceField.setAccessible(true);
		this.faceUpList = (ArrayList<Color>) this.currentFaceField.get(testGame);
		ArrayList<Color> newFaceUpList = new ArrayList<Color>();
		newFaceUpList.add(Color.RED);
		newFaceUpList.add(Color.GRAY);
		newFaceUpList.add(Color.GREEN);
		newFaceUpList.add(Color.GREEN);
		newFaceUpList.add(Color.GRAY);
		this.currentFaceField.set(this.testGame, newFaceUpList);

		this.faceUpList = (ArrayList<Color>) currentFaceField.get(testGame);

	}

	// This is an integration test
	@Test
	public void testPurchasePathIntegrationTest()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		ArrayList<ArrayList<Color>> testListOne = new ArrayList<ArrayList<Color>>();
		for (int i = 0; i < 9; i++) {
			testListOne.add(new ArrayList<Color>());
		}

		Field playerTrainCardList = Hand.class.getDeclaredField("trainCards");
		playerTrainCardList.setAccessible(true);
		playerTrainCardList.set(this.mockPlayer1.getHand(), testListOne);

		Player currentPlayer = Game.getCurrentPlayer();
		for (int i = 0; i < 8; i++) {
			currentPlayer.getHand().addTrainCard(Color.RED);
		}
		ArrayList<Color> removeList = new ArrayList<Color>();
		for (int i = 0; i < 6; i++) {
			removeList.add(Color.RED);
		}
		ArrayList<ArrayList<Color>> overallList = new ArrayList<ArrayList<Color>>();

		ArrayList<Color> finalList = new ArrayList<Color>();
		finalList.add(Color.RED);
		finalList.add(Color.RED);
		overallList.add(finalList);
		for (int i = 0; i < 8; i++) {
			overallList.add(new ArrayList<Color>());
		}
		TrainDeck testDeck = new TrainDeck();
		TrainDeck.refillDeck();

		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Path mockPath = new Path(n1, n2);

		Game.purchasePath(removeList, mockPath);
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<Color> discardList = (ArrayList<Color>) discardField.get(testDeck);

		Field trainCardField = Hand.class.getDeclaredField("trainCards");
		trainCardField.setAccessible(true);
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>> handTrainCardList = (ArrayList<ArrayList<String>>) trainCardField
				.get(currentPlayer.getHand());

		assertEquals(handTrainCardList, overallList);
		assertEquals(discardList.size(), removeList.size());
		assertEquals(15, Game.getCurrentPlayer().getScore());
		assertEquals(39, Game.getCurrentPlayer().getTrainCount());

		TrainDeck.refillDeck();

	}

	@Test
	public void testSwitchPlayer()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field isFirstTurnField = Game.class.getDeclaredField("isFirstTurn");
		isFirstTurnField.setAccessible(true);
		isFirstTurnField.set(this.testGame, false);

		Field canDrawRainbowField = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);

		Field replaceCountField = Game.class.getDeclaredField("replaceCount");
		replaceCountField.setAccessible(true);

		canDrawRainbowField.set(this.testGame, false);
		canDrawAgainField.set(this.testGame, false);
		hasDrawnOneField.set(this.testGame, true);
		replaceCountField.set(this.testGame, 2);

		assertEquals(this.testGame.getCurrentPlayer(), this.mockPlayer1);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(1);
		mockGameboard.resetOnNewPlayer();
		EasyMock.expectLastCall();
		EasyMock.replay(mockGameboard);
		Game.switchToNextPlayer();

		Field canDrawRainbowField2 = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField2.setAccessible(true);
		Boolean canDrawRainbowBoolean2 = (Boolean) canDrawRainbowField2.get(testGame);

		Field canDrawAgainField2 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField2.setAccessible(true);
		Boolean canDrawAgainBoolean2 = (Boolean) canDrawAgainField2.get(testGame);

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

		assertEquals(this.testGame.getCurrentPlayer(), this.mockPlayer2);

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testSwtichPlayerEndOfList()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field firstPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);
		firstPlayerTrainCount.set(this.mockPlayer1, 13);

		Field secondPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);
		secondPlayerTrainCount.set(this.mockPlayer2, 12);

		Field isFirstTurnField = Game.class.getDeclaredField("isFirstTurn");
		isFirstTurnField.setAccessible(true);
		isFirstTurnField.set(this.testGame, false);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(2);
		mockGameboard.resetOnNewPlayer();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);

		assertEquals(this.testGame.getCurrentPlayer(), this.mockPlayer1);
		this.testGame.switchToNextPlayer();
		this.testGame.switchToNextPlayer();
		assertEquals(this.testGame.getCurrentPlayer(), this.mockPlayer1);

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseTwoRegularFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field canDrawRainbowField = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(4);
		mockGameboard.removeAll();
		EasyMock.expectLastCall().times(2);
		mockGameboard.revalidate();
		EasyMock.expectLastCall().times(2);
		mockGameboard.repaint();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);
		assertTrue(Game.chooseFaceupCardToTake(0));

		Field canDrawRainbowField2 = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField2.setAccessible(true);
		Boolean canDrawRainbowBoolean2 = (Boolean) canDrawRainbowField2.get(testGame);

		Field canDrawAgainField2 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField2.setAccessible(true);
		Boolean canDrawAgainBoolean2 = (Boolean) canDrawAgainField2.get(testGame);

		Field hasDrawnOneField2 = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField2.setAccessible(true);
		Boolean hasDrawnOneBoolean2 = (Boolean) hasDrawnOneField2.get(testGame);

		assertFalse(canDrawRainbowBoolean2);
		assertTrue(canDrawAgainBoolean2);
		assertTrue(hasDrawnOneBoolean2);

		assertFalse(Game.chooseFaceupCardToTake(1));
		assertTrue(Game.chooseFaceupCardToTake(2));

		Field canDrawRainbowField3 = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField3.setAccessible(true);
		Boolean canDrawRainbowBoolean3 = (Boolean) canDrawRainbowField3.get(testGame);

		Field canDrawAgainField3 = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField3.setAccessible(true);
		Boolean canDrawAgainBoolean3 = (Boolean) canDrawAgainField3.get(testGame);

		Field hasDrawnOneField3 = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField3.setAccessible(true);

		assertFalse(canDrawRainbowBoolean3);
		assertFalse(canDrawAgainBoolean3);

		assertFalse(Game.chooseFaceupCardToTake(3));

		assertEquals(Color.GREEN, faceUpList.get(3));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseOneRaibowFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field canDrawRainbowField = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(3);
		mockGameboard.removeAll();
		EasyMock.expectLastCall();
		mockGameboard.revalidate();
		EasyMock.expectLastCall();
		mockGameboard.repaint();
		EasyMock.expectLastCall();
		EasyMock.replay(mockGameboard);
		assertTrue(Game.chooseFaceupCardToTake(1));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertFalse(canDrawAgainBoolean);
		assertTrue(hasDrawnOneBoolean);

		assertFalse(Game.chooseFaceupCardToTake(3));

		faceUpList = (ArrayList<Color>) currentFaceField.get(testGame);

		assertEquals(Color.GREEN, faceUpList.get(3));

		assertFalse(Game.chooseFaceupCardToTake(4));

		faceUpList = (ArrayList<Color>) currentFaceField.get(testGame);

		assertEquals(Color.GRAY, faceUpList.get(4));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testChooseFromDeckThenFaceUpCardToTake()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field canDrawRainbowField = Game.class.getDeclaredField("CanDrawRainbow");
		canDrawRainbowField.setAccessible(true);
		Boolean canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);

		Field canDrawAgainField = Game.class.getDeclaredField("CanDrawAgain");
		canDrawAgainField.setAccessible(true);
		Boolean canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);

		Field hasDrawnOneField = Game.class.getDeclaredField("hasDrawnOne");
		hasDrawnOneField.setAccessible(true);
		Boolean hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertTrue(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertFalse(hasDrawnOneBoolean);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(4);
		mockGameboard.removeAll();
		EasyMock.expectLastCall().times(2);
		mockGameboard.revalidate();
		EasyMock.expectLastCall().times(2);
		mockGameboard.repaint();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);
		assertTrue(Game.chooseFaceupCardToTake(-1));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertTrue(canDrawAgainBoolean);
		assertTrue(hasDrawnOneBoolean);

		assertFalse(Game.chooseFaceupCardToTake(1));
		assertTrue(Game.chooseFaceupCardToTake(2));

		canDrawRainbowBoolean = (Boolean) canDrawRainbowField.get(testGame);
		canDrawAgainBoolean = (Boolean) canDrawAgainField.get(testGame);
		hasDrawnOneBoolean = (Boolean) hasDrawnOneField.get(testGame);

		assertFalse(canDrawRainbowBoolean);
		assertFalse(canDrawAgainBoolean);

		assertFalse(Game.chooseFaceupCardToTake(3));

		faceUpList = (ArrayList<Color>) currentFaceField.get(testGame);

		assertEquals(Color.GREEN, faceUpList.get(3));

		EasyMock.verify(mockGameboard);
	}

	@Test
	public void testUpdateCurrentPlayerScore() throws NoSuchMethodException, SecurityException, IllegalAccessException,
			IllegalArgumentException, InvocationTargetException {

		Player testPlayer = this.testGame.getCurrentPlayer();
		
		Method addPoints = Game.class.getDeclaredMethod("updateCurrentPlayerScore", int.class);

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
	public void testRainbowSwitchCheckAndChangeFunction() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException, NoSuchMethodException, InvocationTargetException {
		
		ArrayList<Color> newFaceUpList = new ArrayList<Color>();
		newFaceUpList.add(Color.GRAY);
		newFaceUpList.add(Color.GRAY);
		newFaceUpList.add(Color.GREEN);
		newFaceUpList.add(Color.GREEN);
		newFaceUpList.add(Color.GRAY);
		ArrayList<Color> testList = new ArrayList<Color>();
		testList.add(Color.GRAY);
		testList.add(Color.GRAY);
		testList.add(Color.GREEN);
		testList.add(Color.GREEN);
		testList.add(Color.GRAY);
		currentFaceField.set(this.testGame, newFaceUpList);

		Method checkRainbowMethod = Game.class.getDeclaredMethod("checkIfThreeRainbowsAreUpAndChangeIfNeeded", null);
		checkRainbowMethod.setAccessible(true);
		checkRainbowMethod.invoke(this.testGame, null);

		ArrayList<Color> faceUpListTwo = (ArrayList<Color>) currentFaceField.get(testGame);
		assertNotEquals(faceUpListTwo, testList);

		Field replaceField = Game.class.getDeclaredField("replaceCount");
		replaceField.setAccessible(true);
		int replaceCount = (int) replaceField.get(testGame);

		assertTrue(replaceCount > 0);
	}

	@Test
	// 0,1,2 trains game ends
	public void testEndGamePlayerSwitch()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {

		Field firstPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);

		Field secondPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);

		firstPlayerTrainCount.set(this.mockPlayer1, 3);
		secondPlayerTrainCount.set(this.mockPlayer2, 2);

		Field gameCurrentPlayer = Game.class.getDeclaredField("currentPlayer");
		gameCurrentPlayer.setAccessible(true);
		gameCurrentPlayer.set(this.testGame, this.mockPlayer2);


		Field lastTurnOne = Game.class.getDeclaredField("lastTurn");
		lastTurnOne.setAccessible(true);
		Boolean lastTurnBooleanOne = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(false, lastTurnBooleanOne);

		Player currentPlayer = (Player) gameCurrentPlayer.get(this.testGame);
		assertEquals(2, currentPlayer.getTrainCount());

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(3);
		mockGameboard.resetOnNewPlayer();
		EasyMock.expectLastCall().times(3);
		EasyMock.replay(mockGameboard);
		this.testGame.switchToNextPlayer();

		Boolean lastTurnBooleanTwo = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(false, lastTurnBooleanTwo);

		Player currentPlayerTwo = (Player) gameCurrentPlayer.get(this.testGame);
		assertEquals(3, currentPlayerTwo.getTrainCount());

		this.testGame.switchToNextPlayer();

		Boolean lastTurnBooleanThree = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(2, currentPlayer.getTrainCount());

		assertEquals(true, lastTurnBooleanThree);

		this.testGame.switchToNextPlayer();

		Boolean lastTurnBooleanFour = (Boolean) lastTurnOne.get(this.testGame);

		assertEquals(true, lastTurnBooleanFour);

		EasyMock.verify(mockGameboard);
	}


	@Test
	public void testOfFinishGame()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		
		Field firstPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		firstPlayerTrainCount.setAccessible(true);

		Field secondPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		secondPlayerTrainCount.setAccessible(true);

		Field firstPlayerCompletedRoutes = Hand.class.getDeclaredField("completedRouteCards");
		firstPlayerCompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> firstTestCompletedList = new ArrayList<RouteCard>();
		firstTestCompletedList.add(new RouteCard(0, new Node(0), new Node(0), 24));
		firstPlayerCompletedRoutes.set(this.mockPlayer1.getHand(), firstTestCompletedList);

		Field secondPlayerCompletedRoutes = Hand.class.getDeclaredField("completedRouteCards");
		secondPlayerCompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> secondTestCompletedList = new ArrayList<RouteCard>();
		secondTestCompletedList.add(new RouteCard(0, new Node(0), new Node(0), 42));
		secondPlayerCompletedRoutes.set(this.mockPlayer2.getHand(), secondTestCompletedList);


		Field firstPlayerScore = Player.class.getDeclaredField("score");
		firstPlayerScore.setAccessible(true);
		firstPlayerScore.set(this.mockPlayer1, 0);

		Field secondPlayerScore = Player.class.getDeclaredField("score");
		secondPlayerScore.setAccessible(true);
		secondPlayerScore.set(this.mockPlayer2, 17);

		Field firstPlayerUncompletedRoutes = Hand.class.getDeclaredField("uncompletedRouteCards");
		firstPlayerUncompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> firstTestUncompletedList = new ArrayList<RouteCard>();
		firstTestUncompletedList.add(new RouteCard(0, new Node(0), new Node(1), 7));
		firstTestUncompletedList.add(new RouteCard(0, new Node(2), new Node(3), 2));

		firstPlayerUncompletedRoutes.set(this.mockPlayer1.getHand(), firstTestUncompletedList);

		Field secondPlayerUncompletedRoutes = Hand.class.getDeclaredField("uncompletedRouteCards");
		secondPlayerUncompletedRoutes.setAccessible(true);
		ArrayList<RouteCard> secondTestUncompletedList = new ArrayList<RouteCard>();

		secondPlayerUncompletedRoutes.set(this.mockPlayer2.getHand(), secondTestUncompletedList);

		firstPlayerTrainCount.set(this.mockPlayer1, 3);
		secondPlayerTrainCount.set(this.mockPlayer2, 2);

		Field gameCurrentPlayer = Game.class.getDeclaredField("currentPlayer");
		gameCurrentPlayer.setAccessible(true);
		gameCurrentPlayer.set(testGame, this.mockPlayer1);

		EasyMock.expect(mockGameboard.getPurchasing()).andReturn(false).times(2);
		mockGameboard.resetOnNewPlayer();
		EasyMock.expectLastCall().times(2);
		EasyMock.replay(mockGameboard);

		Field isFirstTurnField = Game.class.getDeclaredField("isFirstTurn");
		isFirstTurnField.setAccessible(true);
		isFirstTurnField.set(this.testGame, false);

		Game.switchToNextPlayer();
		Game.switchToNextPlayer();

		// Extra 10 for longest route
		assertEquals(25, this.mockPlayer1.getScore());

		// Also has longest route
		assertEquals(69, this.mockPlayer2.getScore());

		EasyMock.verify(mockGameboard);
		;

	}
}
