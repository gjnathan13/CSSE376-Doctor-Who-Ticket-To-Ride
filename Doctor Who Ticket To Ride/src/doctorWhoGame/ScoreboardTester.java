package doctorWhoGame;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import javax.swing.JFrame;

import org.easymock.EasyMock;
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
	public void testThatDrawingScoreboardCallsForPlayerColorScoreTrainsAndName() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException{
		Player mock = createMock(Player.class);
		
		EasyMock.expect(mock.getName()).andReturn("");
		EasyMock.expect(mock.getColor()).andReturn(PlayerColor.Blue);
		EasyMock.expect(mock.getTrainCount()).andReturn(0);
		EasyMock.expect(mock.getScore()).andReturn(0);
		
		EasyMock.replay(mock);
		
		Player[] playerList = {mock};
		Scoreboard scores = new Scoreboard(playerList);
		Method m = Scoreboard.class.getDeclaredMethod("displayPlayerInformation", Player.class);
		m.setAccessible(true);
		m.invoke(scores, playerList[0]);
		
		verify(mock);
	}
}
