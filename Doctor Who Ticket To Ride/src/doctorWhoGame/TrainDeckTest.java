package doctorWhoGame;

import static org.junit.Assert.*;

import java.util.ArrayList;

import junit.framework.Assert;

import org.junit.Test;

public class TrainDeckTest {
	
	@Test
	public void testTrainDeckStartsWith110Cards() {
		TrainDeck.refillDeck();
		assertEquals(TrainDeck.size(), 110);
	}
	
	@Test
	public void testDrawReturnsCard(){
		String result = TrainDeck.draw();
		assertTrue(
				result.equals("Pink") ||
				result.equals("White") ||
				result.equals("Blue") ||
				result.equals("Yellow") ||
				result.equals("Orange") ||
				result.equals("Black") ||
				result.equals("Red") ||
				result.equals("Green") ||
				result.equals("Rainbow")
		);
	}
	
	@Test
	public void testGetDeckReturnsListOfCards(){
		assertTrue(TrainDeck.getDeck() instanceof ArrayList);
	}
	
	@Test
	public void testShuffleAltersDeck(){
		
		ArrayList<String> before = TrainDeck.getDeck();
		
		TrainDeck.shuffle();
		
		ArrayList<String> after = TrainDeck.getDeck();

		assertNotEquals(before, after);
	}
	
	@Test
	public void testDrawDecrementsSize(){
		int before = TrainDeck.size();
		
		TrainDeck.draw();
		
		int after = TrainDeck.size();
		
		assertTrue(before - 1 == after);
	}
}