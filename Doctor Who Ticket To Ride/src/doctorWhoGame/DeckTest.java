package doctorWhoGame;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class DeckTest {

	Deck deck;
	
	@Before
	public void initialize(){
		deck = new Deck();
	}
	
	@Test
	public void testDeckStartsEmpty() {
		assertEquals(0, deck.size());
	}

}
