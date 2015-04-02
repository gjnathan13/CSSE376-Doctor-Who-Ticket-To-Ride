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
	public void testDeckDoesntStartEmpty() {
		assertTrue(deck.size() > 0);
	}

}
