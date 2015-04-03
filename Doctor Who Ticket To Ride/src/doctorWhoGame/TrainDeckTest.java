package doctorWhoGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class TrainDeckTest {

	TrainDeck deck;
	
	@Before
	public void initialize(){
		deck = new TrainDeck();
	}
	
	@Test
	public void testTrainDeckDoesntStartEmpty() {
		assertTrue(deck.size() > 0);
	}
	
	@Test
	public void testTrainDeckStartsWith96Cards() {
		assertTrue(deck.size() == 96);
	}

}
