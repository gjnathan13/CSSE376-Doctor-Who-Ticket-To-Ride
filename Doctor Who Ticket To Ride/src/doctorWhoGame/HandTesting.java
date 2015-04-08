package doctorWhoGame;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Hand class
 * 
 * @author wrightsd
 *
 */
public class HandTesting {
	private ArrayList<ArrayList<String>> trainCardList;
	private Hand newHand;
	private ArrayList<RouteCard> routeCardList;
	private ArrayList<ActionCard> actionCardList;

	/**
	 * Sets up local variables
	 * 
	 * @throws NoSuchFieldException
	 * @throws SecurityException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 */
	@SuppressWarnings("unchecked")
	@Before
	public void testSetup() throws NoSuchFieldException, SecurityException,
			IllegalArgumentException, IllegalAccessException {
		this.newHand = new Hand();
		Field trainCardField = Hand.class.getDeclaredField("trainCards");
		trainCardField.setAccessible(true);
		this.trainCardList = (ArrayList<ArrayList<String>>) trainCardField
				.get(newHand);
		Field routeCardField = Hand.class.getDeclaredField("routeCards");
		routeCardField.setAccessible(true);
		this.routeCardList = (ArrayList<RouteCard>) routeCardField.get(newHand);
		Field actionCardField = Hand.class.getDeclaredField("actionCards");
		actionCardField.setAccessible(true);
		this.actionCardList = (ArrayList<ActionCard>) actionCardField
				.get(newHand);

	}

	/**
	 * Tests the constructor
	 */
	@Test
	public void testThatHandExists() {
		assertNotEquals(new Hand(), null);

	}

	/**
	 * Tests adding a red card to the hand
	 */
	@Test
	public void testAddRedTrainCard() {
		String firstTrainCard = "Red";
		newHand.addTrainCard(firstTrainCard);
		assertEquals(1, trainCardList.get(0).size());
		assertEquals(firstTrainCard, trainCardList.get(0).get(0));
		for (int i = 1; i < 9; i++) {
			assertEquals(0, trainCardList.get(i).size());
		}
	}

	/**
	 * Tests adding a pink card to the hand
	 */
	@Test
	public void testAddPinkTrainCard() {
		String firstTrainCard = "Pink";
		newHand.addTrainCard(firstTrainCard);
		assertEquals(1, trainCardList.get(1).size());
		assertEquals(firstTrainCard, trainCardList.get(1).get(0));
		assertEquals(0, trainCardList.get(0).size());
		for (int i = 2; i < 9; i++) {
			assertEquals(0, trainCardList.get(i).size());
		}
	}

	/**
	 * Tests adding many cards of different colors
	 */
	@Test
	public void testAddManyDifferentColorTrainCard() {
		String redTrainCard = "Red";
		String pinkTrainCard = "Pink";
		String orangeTrainCard = "Orange";
		String yellowTrainCard = "Yellow";
		String greenTrainCard = "Green";
		String blueTrainCard = "Blue";
		String whiteTrainCard = "White";
		String blackTrainCard = "Black";
		String rainbowTrainCard = "Rainbow";
		for (int i = 0; i < 9; i++) {
			newHand.addTrainCard(redTrainCard);
		}
		for (int i = 0; i < 8; i++) {
			newHand.addTrainCard(pinkTrainCard);
		}
		for (int i = 0; i < 7; i++) {
			newHand.addTrainCard(orangeTrainCard);
		}
		for (int i = 0; i < 6; i++) {
			newHand.addTrainCard(yellowTrainCard);
		}
		for (int i = 0; i < 5; i++) {
			newHand.addTrainCard(greenTrainCard);
		}
		for (int i = 0; i < 4; i++) {
			newHand.addTrainCard(blueTrainCard);
		}
		for (int i = 0; i < 3; i++) {
			newHand.addTrainCard(whiteTrainCard);
		}
		for (int i = 0; i < 2; i++) {
			newHand.addTrainCard(blackTrainCard);
		}
		newHand.addTrainCard(rainbowTrainCard);

		// Test that each list is the right length
		for (int i = 9; i > 0; i--) {
			int indexOfList = 9 - i;
			assertEquals(i, trainCardList.get(indexOfList).size());
		}

		// Test that each list contains the right things
		for (int i = 0; i < 9; i++) {
			assertEquals(redTrainCard, trainCardList.get(0).get(i));
		}
		for (int i = 0; i < 8; i++) {
			assertEquals(pinkTrainCard, trainCardList.get(1).get(i));
		}
		for (int i = 0; i < 7; i++) {
			assertEquals(orangeTrainCard, trainCardList.get(2).get(i));
		}
		for (int i = 0; i < 6; i++) {
			assertEquals(yellowTrainCard, trainCardList.get(3).get(i));
		}
		for (int i = 0; i < 5; i++) {
			assertEquals(greenTrainCard, trainCardList.get(4).get(i));
		}
		for (int i = 0; i < 4; i++) {
			assertEquals(blueTrainCard, trainCardList.get(5).get(i));
		}
		for (int i = 0; i < 3; i++) {
			assertEquals(whiteTrainCard, trainCardList.get(6).get(i));
		}
		for (int i = 0; i < 2; i++) {
			assertEquals(blackTrainCard, trainCardList.get(7).get(i));
		}
		assertEquals(rainbowTrainCard, trainCardList.get(8).get(0));

	}

	/**
	 * Tests the remove function when there is only one train card in the train
	 * card portion of the hand
	 */
	@Test
	public void testRemoveOnlyCard() {
		String redTrainCard = "Red";
		newHand.addTrainCard(redTrainCard);
		newHand.removeTrainCard(redTrainCard);
		for (int i = 0; i < 9; i++) {
			assertEquals(0, trainCardList.get(i).size());
		}
	}

	/**
	 * Tests removing lots of cards of different colors
	 */
	@Test
	public void testRemovalOfLotsOfDifferentCards() {
		String redTrainCard = "Red";
		String pinkTrainCard = "Pink";
		String orangeTrainCard = "Orange";
		String yellowTrainCard = "Yellow";
		String greenTrainCard = "Green";
		String blueTrainCard = "Blue";
		String whiteTrainCard = "White";
		String blackTrainCard = "Black";
		String rainbowTrainCard = "Rainbow";

		// Adds 10 cards of each color to the hand
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(redTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(pinkTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(orangeTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(yellowTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(greenTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(blueTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(whiteTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(blackTrainCard);
		}
		for (int i = 0; i < 10; i++) {
			newHand.addTrainCard(rainbowTrainCard);
		}

		// Removes a differing number of cards from each colored train stack
		newHand.removeTrainCard(redTrainCard);
		for (int i = 0; i < 2; i++) {
			newHand.removeTrainCard(pinkTrainCard);
		}
		for (int i = 0; i < 3; i++) {
			newHand.removeTrainCard(orangeTrainCard);
		}
		for (int i = 0; i < 4; i++) {
			newHand.removeTrainCard(yellowTrainCard);
		}
		for (int i = 0; i < 5; i++) {
			newHand.removeTrainCard(greenTrainCard);
		}
		for (int i = 0; i < 6; i++) {
			newHand.removeTrainCard(blueTrainCard);
		}
		for (int i = 0; i < 7; i++) {
			newHand.removeTrainCard(whiteTrainCard);
		}
		for (int i = 0; i < 8; i++) {
			newHand.removeTrainCard(blackTrainCard);
		}
		for (int i = 0; i < 9; i++) {
			newHand.removeTrainCard(rainbowTrainCard);
		}
		for (int i = 0; i < 8; i++) {
			int count = 9 - i;
			assertEquals(count, trainCardList.get(i).size());
		}
	}

	/**
	 * Tests that the getNumber function returns the correct number of cards
	 */
	@Test
	public void testGetNumberofCards() {
		String redTrainCard = "Red";
		String pinkTrainCard = "Pink";
		String orangeTrainCard = "Orange";
		String yellowTrainCard = "Yellow";
		String greenTrainCard = "Green";
		String blueTrainCard = "Blue";
		String whiteTrainCard = "White";
		String blackTrainCard = "Black";
		String rainbowTrainCard = "Rainbow";

		// Adds 10 cards of each color to the hand
		for (int i = 0; i < 9; i++) {
			newHand.addTrainCard(redTrainCard);
		}
		for (int i = 0; i < 8; i++) {
			newHand.addTrainCard(pinkTrainCard);
		}
		for (int i = 0; i < 7; i++) {
			newHand.addTrainCard(orangeTrainCard);
		}
		for (int i = 0; i < 6; i++) {
			newHand.addTrainCard(yellowTrainCard);
		}
		for (int i = 0; i < 5; i++) {
			newHand.addTrainCard(greenTrainCard);
		}
		for (int i = 0; i < 4; i++) {
			newHand.addTrainCard(blueTrainCard);
		}
		for (int i = 0; i < 3; i++) {
			newHand.addTrainCard(whiteTrainCard);
		}
		for (int i = 0; i < 2; i++) {
			newHand.addTrainCard(blackTrainCard);
		}
		newHand.addTrainCard(rainbowTrainCard);

		ArrayList<Integer> properReturn = new ArrayList<Integer>();
		for (int i = 9; i > 0; i--) {
			properReturn.add(i);
		}
		assertEquals(properReturn, newHand.getNumberOfTrainCards());

	}

	/**
	 * Tests that there is an ArrayList of Route Cards
	 */
	@Test
	public void testAddRouteCardToHand() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		newHand.addRouteCard(firstTestRouteCard);
		assertEquals(1, routeCardList.size());
		assertEquals(firstTestRouteCard, routeCardList.get(0));
	}

	/**
	 * Adds many RouteCard objects to the hand and checks that it worked
	 */
	@Test
	public void testAddManyRouteCardsToHand() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		RouteCard nextRouteCard = new RouteCard(11);
		RouteCard thirdRouteCard = new RouteCard(42);
		RouteCard fourthRouteCard = new RouteCard(137);
		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);
		assertEquals(4, routeCardList.size());
		assertEquals(firstTestRouteCard, routeCardList.get(0));
		assertEquals(nextRouteCard, routeCardList.get(1));
		assertEquals(thirdRouteCard, routeCardList.get(2));
		assertEquals(fourthRouteCard, routeCardList.get(3));
	}

	/**
	 * Tests that the ActionCard was successfully added to the Hand
	 */
	@Test
	public void testAddActionCardToHand() {
		ActionCard firstTestActionCard = new ActionCard(1);
		newHand.addActionCard(firstTestActionCard);
		assertEquals(1, actionCardList.size());
		assertEquals(firstTestActionCard, actionCardList.get(0));
	}
	
	@Test
	public void testAddManyActionCardsToHand(){
		
	}
	
	@Test
	public void testGetEmptyListOfRouteCards(){
		
	}
	
	@Test
	public void testGetListOfManyRouteCards(){
		
	}
	
	@Test
	public void testGetEmptyListOfActionCards(){
		
	}
	
	@Test
	public void testRemoveActionCard(){
		
	}
	
	@Test
	public void testRemoveManyActionCards(){
		
	}

}
