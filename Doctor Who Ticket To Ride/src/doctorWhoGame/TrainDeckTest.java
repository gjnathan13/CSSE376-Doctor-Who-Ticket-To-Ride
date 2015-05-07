package doctorWhoGame;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Test;

public class TrainDeckTest {
	
	@Test
	public void testTrainDeckStartsWith110Cards() {
		TrainDeck.refillDeck();
		assertEquals(110, TrainDeck.size());
	}
	
	@Test
	public void testDrawReturnsCard(){
		TrainColor result = TrainDeck.draw();
		TrainDeck.discard(result);
		TrainDeck.refillDeck();
		
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
		
		TrainColor test=TrainDeck.draw();
		int after = TrainDeck.size();
		TrainDeck.discard(test);
		TrainDeck.refillDeck();
		
		assertTrue(before - 1 == after);
	}
	
	@Test
	public void testDiscard() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		TrainDeck testDeck=new TrainDeck();
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<TrainColor> discardList= (ArrayList<TrainColor>) discardField
				.get(testDeck);
		ArrayList<TrainColor> testDiscardList=new ArrayList<TrainColor>();
		for(int i=0;i<5;i++){
			TrainColor drawn=TrainDeck.draw();
			TrainDeck.discard(drawn);
			testDiscardList.add(drawn);
		}
		assertEquals(discardList, testDiscardList);
		TrainDeck.refillDeck();
	}
	
	@Test
	public void testRefillDeck() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException{
		TrainDeck testDeckTwo=new TrainDeck();
		Field discardFieldTwo = TrainDeck.class.getDeclaredField("discard");
		discardFieldTwo.setAccessible(true);
		ArrayList<TrainColor> discardList= (ArrayList<TrainColor>) discardFieldTwo
				.get(testDeckTwo);
		for(int i=0;i<5;i++){
			TrainColor drawn=TrainDeck.draw();
			TrainDeck.discard(drawn);
			
		}
		TrainDeck.refillDeck();
		assertEquals(110,TrainDeck.size());
		assertEquals(discardList,new ArrayList<TrainColor>());
		
	}
}