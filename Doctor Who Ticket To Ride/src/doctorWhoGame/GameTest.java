package doctorWhoGame;

import java.lang.reflect.Field;
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
}
