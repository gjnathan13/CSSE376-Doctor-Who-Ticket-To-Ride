package doctorWhoGame;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.verify;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.easymock.EasyMock;
import org.junit.Assert;
import org.junit.Test;

public class ScoreboardTester {

	@Test
	public void testThatScoreboardGetsPlayerList()
			throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		Player[] playerList = {};
		Scoreboard scores = new Scoreboard(playerList);

		Field privatePlayerList = Scoreboard.class.getDeclaredField("playerList");

		privatePlayerList.setAccessible(true);
		Player[] playerCheck = (Player[]) privatePlayerList.get(scores);

		Assert.assertArrayEquals(playerList, playerCheck);
	}

	/**
	 * Code refactored so below test had to be rewritten
	 * 
	 * @throws NoSuchMethodException
	 * @throws SecurityException
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 * @throws InvocationTargetException
	 */
	// @Test
	// public void
	// testThatDrawingScoreboardCallsForPlayerColorScoreTrainsAndName() throws
	// NoSuchMethodException, SecurityException, IllegalAccessException,
	// IllegalArgumentException, InvocationTargetException{
	// Player mock = createMock(Player.class);
	//
	// EasyMock.expect(mock.getName()).andReturn("");
	// EasyMock.expect(mock.getColor()).andReturn(PlayerColor.Blue);
	// EasyMock.expect(mock.getTrainCount()).andReturn(0);
	// EasyMock.expect(mock.getScore()).andReturn(0);
	//
	// EasyMock.replay(mock);
	//
	// Player[] playerList = {mock};
	// Scoreboard scores = new Scoreboard(playerList);
	//
	// verify(mock);
	// }

	@Test
	public void testThatDrawingScoreboardCallsForPlayerColorScoreTrainsAndName() throws NoSuchMethodException,
			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Player mock = createMock(Player.class);

		EasyMock.expect(mock.getName()).andReturn("");
		EasyMock.expect(mock.getColor()).andReturn(PlayerColor.Blue);
		EasyMock.expect(mock.getTrainCount()).andReturn(0);
		EasyMock.expect(mock.getScore()).andReturn(0);

		EasyMock.replay(mock);

		Player[] playerList = { mock };
		Scoreboard scores = new Scoreboard(playerList);

		Class[] params = { Player.class, int.class };
		Method privatePlayerInfoMethod = Scoreboard.class.getDeclaredMethod("displayPlayerInformation", params);

		privatePlayerInfoMethod.setAccessible(true);

		privatePlayerInfoMethod.invoke(scores, mock, 1);

		verify(mock);
	}
}
