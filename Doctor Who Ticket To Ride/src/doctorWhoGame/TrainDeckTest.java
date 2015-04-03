package doctorWhoGame;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import org.junit.Before;
import org.junit.Test;

public class TrainDeckTest {

	TrainDeck deck;
	
	@Before
	public void initialize(){
		deck = new TrainDeck();
	}
	
	@Test
	public void testTrainDeckStartsWith96Cards() {
		assertTrue(deck.size() == 96);
	}
	
	@Test
	public void testDrawReturnsCard(){
		String result = deck.draw();
		assertTrue(result.equals("Blue") ||
				result.equals("Red") ||
				result.equals("Green") ||
				result.equals("Yellow") ||
				result.equals("Black") ||
				result.equals("Rainbow"));
	}
	
	@Test
	public void testGetDeckReturnsListOfCards(){
		assertTrue(deck.getDeck() instanceof ArrayList);
	}
	
	@Test
	public void testShuffleAltersDeck(){
		
		ArrayList<String> before = deck.getDeck();
		
		deck.shuffle();
		
		ArrayList<String> after = deck.getDeck();

		assertNotEquals(before, after);
	}
	
	@Test
	public void testDrawDecrementsSize(){
		int before = deck.size();
		
		deck.draw();
		
		int after = deck.size();
		
		assertTrue(before != after);
	}
}