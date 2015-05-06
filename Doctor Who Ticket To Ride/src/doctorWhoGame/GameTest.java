package doctorWhoGame;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.easymock.EasyMock;
import org.junit.Before;
import org.junit.Test;

import static org.easymock.EasyMock.*;
import static org.junit.Assert.*;

public class GameTest {

	private Player[] playerList;
	private Game testGame;

	@Before
	public void testSetUp() {
		Player mockPlayer1 = createMock(Player.class);
		Player mockPlayer2 = createMock(Player.class);
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(mockPlayer1);
		players.add(mockPlayer2);
		this.playerList = players.toArray(new Player[players.size()]);
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		this.testGame = new Game(this.playerList, mockGameboard,
				mockScoreboard, mockRouteboard);
		EasyMock.replay(mockGameboard);
		EasyMock.replay(mockScoreboard);
		EasyMock.replay(mockRouteboard);

	}

	// This is an integration test
	@Test
	public void testPurchasePathIntegrationTest() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test",PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		EasyMock.replay(mockGameboard);
		EasyMock.replay(mockScoreboard);
		EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]), mockGameboard,
				mockScoreboard, mockRouteboard);
		Player currentPlayer=this.testGame.getCurrentPlayer();
		for(int i=0;i<8;i++){
			currentPlayer.getHand().addTrainCard(TrainColor.Red);
		}
		ArrayList<TrainColor> removeList=new ArrayList<TrainColor>();
		for(int i=0;i<6;i++){
			removeList.add(TrainColor.Red);
		}
		ArrayList<ArrayList<TrainColor>> overallList=new ArrayList<ArrayList<TrainColor>>();
		
		
		ArrayList<TrainColor> finalList=new ArrayList<TrainColor>();
		finalList.add(TrainColor.Red);
		finalList.add(TrainColor.Red);
		overallList.add(finalList);
		for(int i=0;i<8;i++){
			overallList.add(new ArrayList<TrainColor>());
		}
		
		this.testGame.purchasePath(removeList);
		TrainDeck testDeck=new TrainDeck();
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<TrainColor> discardList= (ArrayList<TrainColor>) discardField
				.get(testDeck);
		
		Field trainCardField = Hand.class.getDeclaredField("trainCards");
		trainCardField.setAccessible(true);
		@SuppressWarnings("unchecked")
		ArrayList<ArrayList<String>>handTrainCardList= (ArrayList<ArrayList<String>>) trainCardField
				.get(currentPlayer.getHand());
		
		assertEquals(handTrainCardList,overallList);
		assertTrue(discardList.size()==removeList.size());
		assertEquals(15,this.testGame.getCurrentPlayer().getScore());
		assertEquals(39,this.testGame.getCurrentPlayer().getTrainCount());
		
		TrainDeck.refillDeck();
				
	}
	
	@Test
	public void testSwitchPlayer(){
		EasyMock.replay(this.playerList[0]);
		EasyMock.replay(this.playerList[1]);
		assertEquals(this.testGame.getCurrentPlayer(),this.playerList[0]);
		this.testGame.switchToNextPlayer();
		assertEquals(this.testGame.getCurrentPlayer(),this.playerList[1]);
	}
	
	@Test
	public void testSwtichPlayerEndOfList(){
		EasyMock.replay(this.playerList[0]);
		EasyMock.replay(this.playerList[1]);
		assertEquals(this.testGame.getCurrentPlayer(),this.playerList[0]);
		this.testGame.switchToNextPlayer();
		this.testGame.switchToNextPlayer();
		assertEquals(this.testGame.getCurrentPlayer(),this.playerList[0]);
		
	}
	
	@Test
	public void testChooseFaceUpCardToTake(){
		
	}
	
	@Test
	public void testUpdateCurrentPlayerScore() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		ArrayList<Player> players = new ArrayList<Player>();
		players.add(new Player("test",PlayerColor.Green));
		Gameboard mockGameboard = createMock(Gameboard.class);
		Scoreboard mockScoreboard = createMock(Scoreboard.class);
		Routeboard mockRouteboard = createMock(Routeboard.class);
		EasyMock.replay(mockGameboard);
		EasyMock.replay(mockScoreboard);
		EasyMock.replay(mockRouteboard);
		this.testGame = new Game(players.toArray(new Player[players.size()]), mockGameboard,
				mockScoreboard, mockRouteboard);
		Player testPlayer=this.testGame.getCurrentPlayer();
		Method addPoints=Game.class.getDeclaredMethod("updateCurrenPlayerScore", int.class);
		addPoints.setAccessible(true);

		assertEquals(0, testPlayer.getScore());
		System.out.println(testPlayer.getScore());
		addPoints.invoke(testGame, 1);
		System.out.println(testPlayer.getScore());
		assertEquals(1,testPlayer.getScore());
		addPoints.invoke(testGame, 2);
		assertEquals(3,testPlayer.getScore());
		addPoints.invoke(testGame, 3);
		assertEquals(7,testPlayer.getScore());
		addPoints.invoke(testGame, 4);
		assertEquals(14,testPlayer.getScore());
		addPoints.invoke(testGame, 5);
		assertEquals(24,testPlayer.getScore());
		addPoints.invoke(testGame, 6);
		assertEquals(39,testPlayer.getScore());
		addPoints.invoke(testGame, -1);
		assertEquals(39,testPlayer.getScore());
		addPoints.invoke(testGame, 7);
		assertEquals(39,testPlayer.getScore());		
	}
}
