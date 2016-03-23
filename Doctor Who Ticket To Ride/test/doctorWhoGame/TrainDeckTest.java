package doctorWhoGame;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.Color;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class TrainDeckTest {

	@Before
	public void testSetUp() throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException,
			SecurityException, NoSuchMethodException, InvocationTargetException {

		TrainDeck testDeck = new TrainDeck();
		Field deckField = TrainDeck.class.getDeclaredField("deck");
		deckField.setAccessible(true);

		Method renewDeckMethod = TrainDeck.class.getDeclaredMethod("getNewDeck", null);
		renewDeckMethod.setAccessible(true);
		ArrayList<Color> newDeck = (ArrayList<Color>) renewDeckMethod.invoke(testDeck, null);

		deckField.set(null, newDeck);
	}

	@Test
	public void testTrainDeckStartsWith110Cards() {
		TrainDeck.refillDeck();
		assertEquals(110, TrainDeck.size());
	}

	@Test
	public void testDrawReturnsCard() {
		Color result = TrainDeck.draw();
		TrainDeck.discard(result);
		TrainDeck.refillDeck();

		assertNotNull(result);
	}

	@Test
	public void testDrawAllCardsAndRefill()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		for (int i = 0; i < 110; i++) {
			TrainDeck.discard(TrainDeck.draw());
		}
		TrainDeck testDeck = new TrainDeck();
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<Color> discardList = (ArrayList<Color>) discardField.get(testDeck);
		Field deckField = TrainDeck.class.getDeclaredField("deck");
		deckField.setAccessible(true);
		ArrayList<Color> deckList = (ArrayList<Color>) deckField.get(testDeck);
		assertTrue(deckList.size() == 0);
		assertTrue(discardList.size() == 110);

		TrainDeck.discard(TrainDeck.draw());
		assertTrue(deckList.size() == 109);
		assertTrue(discardList.size() == 1);
		TrainDeck.refillDeck();

	}

	@Test
	public void testGetDeckReturnsListOfCards() {
		assertTrue(TrainDeck.getDeck() instanceof ArrayList);
	}

	@Test
	public void testShuffleAltersDeck() {

		ArrayList<Color> before = TrainDeck.getDeck();

		TrainDeck.shuffle();

		ArrayList<Color> after = TrainDeck.getDeck();

		assertNotEquals(before, after);
	}

	@Test
	public void testDrawDecrementsSize() {
		int before = TrainDeck.size();

		Color test = TrainDeck.draw();
		int after = TrainDeck.size();
		TrainDeck.discard(test);
		TrainDeck.refillDeck();

		assertTrue(before - 1 == after);
	}

	@Test
	public void testDiscard()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TrainDeck testDeck = new TrainDeck();
		Field discardField = TrainDeck.class.getDeclaredField("discard");
		discardField.setAccessible(true);
		ArrayList<Color> discardList = (ArrayList<Color>) discardField.get(testDeck);
		ArrayList<Color> testDiscardList = new ArrayList<Color>();
		for (int i = 0; i < 5; i++) {
			Color drawn = TrainDeck.draw();
			TrainDeck.discard(drawn);
			testDiscardList.add(drawn);
		}
		assertEquals(discardList, testDiscardList);
		TrainDeck.refillDeck();
	}

	@Test
	public void testRefillDeck()
			throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		TrainDeck testDeckTwo = new TrainDeck();
		Field discardFieldTwo = TrainDeck.class.getDeclaredField("discard");
		discardFieldTwo.setAccessible(true);
		ArrayList<Color> discardList = (ArrayList<Color>) discardFieldTwo.get(testDeckTwo);
		for (int i = 0; i < 5; i++) {
			Color drawn = TrainDeck.draw();
			TrainDeck.discard(drawn);

		}
		TrainDeck.refillDeck();
		assertEquals(110, TrainDeck.size());
		assertEquals(discardList, new ArrayList<Color>());

	}
}