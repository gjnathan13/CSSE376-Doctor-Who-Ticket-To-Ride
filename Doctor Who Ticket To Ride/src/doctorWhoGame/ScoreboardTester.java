package doctorWhoGame;

import java.io.File;
import java.lang.reflect.Field;

import org.junit.*;

import static org.easymock.EasyMock.*;


public class ScoreboardTester {

	@Test
	public void testThatScoreboardGetsPlayerList() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException{
		Player mock = createMock(Player.class);
		Player[] playerList = {mock};
		Scoreboard scores = new Scoreboard(playerList);
		
		Field privatePlayerList = Scoreboard.class
				.getDeclaredField("playerList");

		privatePlayerList.setAccessible(true);
		Player[] playerCheck = (Player[]) privatePlayerList.get(scores);
		
		Assert.assertArrayEquals(playerList, playerCheck);
	}
	
	@Test
	public void testThatDrawingScoreboardCallsForPlayerColorScoreTrainsAndName(){
		Player mock = createMock(Player.class);
		
		mock.getName();
		mock.getColor();
		mock.getTrainCount();
		mock.getScore();
		
		replay(mock);
		
		Player[] playerList = {mock};
		Scoreboard scores = new Scoreboard(playerList);
		
		verify(mock);
	}
}
