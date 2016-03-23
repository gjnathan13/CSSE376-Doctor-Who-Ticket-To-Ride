package doctorWhoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {

	private Player testPlayer;

	@Before
	public void testSetup()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		String name = "testPlayer";
		Color playerColor = Color.GREEN;
		this.testPlayer = new Player(name, playerColor);
		Field testPlayerTrainCount = Player.class.getDeclaredField("trainCount");
		testPlayerTrainCount.setAccessible(true);
		testPlayerTrainCount.set(testPlayer, 45);

	}

	@Test
	public void testAddPoints() {
		int pointsToAdd = 15;
		testPlayer.addPoints(pointsToAdd);
		assertEquals(pointsToAdd, testPlayer.getScore());
	}

	@Test
	public void testAddMorePoints() {
		int pointsToAdd = 7;
		int morePointsToAdd = 21;
		int evenMorePointsToAdd = 1;
		testPlayer.addPoints(pointsToAdd);
		assertEquals(pointsToAdd, testPlayer.getScore());
		testPlayer.addPoints(morePointsToAdd);
		assertEquals(pointsToAdd + morePointsToAdd, testPlayer.getScore());
		testPlayer.addPoints(evenMorePointsToAdd);
		assertEquals(pointsToAdd + morePointsToAdd + evenMorePointsToAdd, testPlayer.getScore());
	}

	@Test
	public void testRemoveTrains() {
		int numberOfTrainsToRemove = 3;
		int nextTrainsToRemove = 6;
		int otherNumberOfTrainsToRemove = 1;
		testPlayer.removeTrainsFromPlayer(numberOfTrainsToRemove);
		assertEquals(45 - numberOfTrainsToRemove, testPlayer.getTrainCount());
		testPlayer.removeTrainsFromPlayer(nextTrainsToRemove);
		assertEquals(45 - numberOfTrainsToRemove - nextTrainsToRemove, testPlayer.getTrainCount());
		testPlayer.removeTrainsFromPlayer(otherNumberOfTrainsToRemove);
		assertEquals(45 - numberOfTrainsToRemove - nextTrainsToRemove - otherNumberOfTrainsToRemove,
				testPlayer.getTrainCount());
	}

	@Test
	public void testAddRoute() {
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		RouteCard r = new RouteCard(0, n1, n2);

		// make sure both are empty
		assertEquals(0, testPlayer.getHand().getCompletedRouteCards().size());
		assertEquals(0, testPlayer.getHand().getUncompletedRouteCards().size());

		testPlayer.addRouteCard(r);

		// make sure both are filled properly
		assertEquals(0, testPlayer.getHand().getCompletedRouteCards().size());
		assertEquals(1, testPlayer.getHand().getUncompletedRouteCards().size());
	}

	@Test
	public void testIsPathOwned() {
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);

		Path p1 = new Path(n1, n2, TrainColor.Black, 1);
		Path p2 = new Path(n2, n3, TrainColor.Black, 1);
		Path p3 = new Path(n3, n4, TrainColor.Black, 1);

		testPlayer.addPath(p1);

		// owned path
		assertTrue(testPlayer.isPathOwned(p1));

		// entirely un-owned path
		assertFalse(testPlayer.isPathOwned(p3));

		testPlayer.addPath(p3);

		// own nodes, not path
		assertFalse(testPlayer.isPathOwned(p2));

		testPlayer.addPath(p2);

		// newly owned path
		assertTrue(testPlayer.isPathOwned(p2));
	}
}
