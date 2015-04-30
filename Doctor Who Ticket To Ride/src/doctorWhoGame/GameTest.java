package doctorWhoGame;

import java.util.ArrayList;

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

	}

	@Test
	public void testPurchasePath() {
		Hand currentHand = this.testGame.getCurrentPlayer().getHand();
		for (int i = 0; i < 8; i++) {
			currentHand.addTrainCard(TrainColor.Red);
		}
		ArrayList<TrainColor> testList=new ArrayList<TrainColor>();
		for(int i=0;i<6;i++){
			testList.add(TrainColor.Red);
		}
		this.testGame.purchasePath(testList);
		ArrayList<TrainColor> finalList=new ArrayList<TrainColor>();
		finalList.add(TrainColor.Red);
		finalList.add(TrainColor.Red);
		
		assertEquals(currentHand,finalList);
	}

}
