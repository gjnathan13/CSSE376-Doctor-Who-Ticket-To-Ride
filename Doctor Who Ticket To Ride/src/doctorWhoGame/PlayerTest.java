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
		testPlayer.addPoints(15);
		assertEquals(pointsToAdd, testPlayer.getScore());
	}
	
}
