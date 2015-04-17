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
		TrainColor result = TrainDeck.draw();
		
		assertNotNull(result);
	}
	
	@Test
	public void testGetDeckReturnsListOfCards(){
		assertTrue(TrainDeck.getDeck() instanceof ArrayList);
	}
	
	@Test
	public void testShuffleAltersDeck(){
		
		ArrayList<TrainColor> before = TrainDeck.getDeck();
		
		TrainDeck.shuffle();
		
		ArrayList<TrainColor> after = TrainDeck.getDeck();

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