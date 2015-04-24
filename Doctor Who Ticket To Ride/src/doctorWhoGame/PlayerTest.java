package doctorWhoGame;

import static org.junit.Assert.*;
import junit.framework.Assert;

import org.junit.Before;
import org.junit.Test;

public class PlayerTest {
	
	private Player testPlayer;

	@Before
	public void testSetup(){
		String name="testPlayer";
		PlayerColor playerColor=PlayerColor.Green;
		this.testPlayer=new Player(name,playerColor);
	}
	
	@Test
	public void testAddPoints(){
		int pointsToAdd=15;
		testPlayer.addPoints(pointsToAdd);
		assertEquals(pointsToAdd, testPlayer.getScore());
	}
	
	@Test
	public void testAddMorePoints(){
		int pointsToAdd=7;
		int morePointsToAdd=21;
		int evenMorePointsToAdd=1;
		testPlayer.addPoints(pointsToAdd);
		assertEquals(pointsToAdd,testPlayer.getScore());
		testPlayer.addPoints(morePointsToAdd);
		assertEquals(pointsToAdd+morePointsToAdd, testPlayer.getScore());
		testPlayer.addPoints(evenMorePointsToAdd);
		assertEquals(pointsToAdd+morePointsToAdd+evenMorePointsToAdd, testPlayer.getScore());
	}
	
	@Test
	public void testRemoveTrains(){
		int numberOfTrainsToRemove=3;
		int nextTrainsToRemove=6;
		int otherNumberOfTrainsToRemove=1;
		testPlayer.removeTrainsFromPlayer(numberOfTrainsToRemove);
		assertEquals(45-numberOfTrainsToRemove, testPlayer.getTrainCount());
		testPlayer.removeTrainsFromPlayer(nextTrainsToRemove);
		assertEquals(45-numberOfTrainsToRemove-nextTrainsToRemove, testPlayer.getTrainCount());
		testPlayer.removeTrainsFromPlayer(otherNumberOfTrainsToRemove);
		assertEquals(45-numberOfTrainsToRemove-nextTrainsToRemove-otherNumberOfTrainsToRemove, testPlayer.getTrainCount());
	}
}
