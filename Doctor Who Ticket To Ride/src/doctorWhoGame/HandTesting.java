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
	private ArrayList<RouteCard> uncompletedRouteCards;
	private ArrayList<RouteCard> completedRouteCards;
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

		Field uncompletedRouteCards = Hand.class
				.getDeclaredField("uncompletedRouteCards");
		uncompletedRouteCards.setAccessible(true);
		this.uncompletedRouteCards = (ArrayList<RouteCard>) uncompletedRouteCards
				.get(newHand);
		
		Field completedRouteCards = Hand.class
				.getDeclaredField("completedRouteCards");
		completedRouteCards.setAccessible(true);
		this.completedRouteCards = (ArrayList<RouteCard>) completedRouteCards
				.get(newHand);

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
		TrainColor firstTrainCard = TrainColor.Red;
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
		TrainColor firstTrainCard = TrainColor.Pink;
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
		TrainColor redTrainCard = TrainColor.Red;
		TrainColor pinkTrainCard = TrainColor.Pink;
		TrainColor orangeTrainCard = TrainColor.Orange;
		TrainColor yellowTrainCard = TrainColor.Yellow;
		TrainColor greenTrainCard = TrainColor.Green;
		TrainColor blueTrainCard = TrainColor.Blue;
		TrainColor whiteTrainCard = TrainColor.White;
		TrainColor blackTrainCard = TrainColor.Black;
		TrainColor rainbowTrainCard = TrainColor.Rainbow;
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
		TrainColor redTrainCard = TrainColor.Red;
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
		TrainColor redTrainCard = TrainColor.Red;
		TrainColor pinkTrainCard = TrainColor.Pink;
		TrainColor orangeTrainCard = TrainColor.Orange;
		TrainColor yellowTrainCard = TrainColor.Yellow;
		TrainColor greenTrainCard = TrainColor.Green;
		TrainColor blueTrainCard = TrainColor.Blue;
		TrainColor whiteTrainCard = TrainColor.White;
		TrainColor blackTrainCard = TrainColor.Black;
		TrainColor rainbowTrainCard = TrainColor.Rainbow;

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
		TrainColor redTrainCard = TrainColor.Red;
		TrainColor pinkTrainCard = TrainColor.Pink;
		TrainColor orangeTrainCard = TrainColor.Orange;
		TrainColor yellowTrainCard = TrainColor.Yellow;
		TrainColor greenTrainCard = TrainColor.Green;
		TrainColor blueTrainCard = TrainColor.Blue;
		TrainColor whiteTrainCard = TrainColor.White;
		TrainColor blackTrainCard = TrainColor.Black;
		TrainColor rainbowTrainCard = TrainColor.Rainbow;

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
	 * Tests that there is an ArrayList of Uncompleted Route Cards
	 */
	@Test
	public void testAddRouteCardToHand() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		assertEquals(1, uncompletedRouteCards.size());
		assertEquals(firstTestRouteCard, uncompletedRouteCards.get(0));
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
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		newHand.addUncompletedRouteCard(nextRouteCard);
		newHand.addUncompletedRouteCard(thirdRouteCard);
		newHand.addUncompletedRouteCard(fourthRouteCard);
		assertEquals(4, uncompletedRouteCards.size());
		assertEquals(firstTestRouteCard, uncompletedRouteCards.get(0));
		assertEquals(nextRouteCard, uncompletedRouteCards.get(1));
		assertEquals(thirdRouteCard, uncompletedRouteCards.get(2));
		assertEquals(fourthRouteCard, uncompletedRouteCards.get(3));
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

	/**
	 * Test of adding many Action Cards to the Hand.
	 */
	@Test
	public void testAddManyActionCardsToHand() {
		ActionCard firstActionCard = new ActionCard(1);
		ActionCard nextActionCard = new ActionCard(11);
		ActionCard thirdActionCard = new ActionCard(42);
		ActionCard fourthRouteCard = new ActionCard(137);
		
		newHand.addActionCard(firstActionCard);
		newHand.addActionCard(nextActionCard);
		newHand.addActionCard(thirdActionCard);
		newHand.addActionCard(fourthRouteCard);
		
		assertEquals(4, actionCardList.size());
		
		assertEquals(firstActionCard, actionCardList.get(0));
		assertEquals(nextActionCard, actionCardList.get(1));
		assertEquals(thirdActionCard, actionCardList.get(2));
		assertEquals(fourthRouteCard, actionCardList.get(3));
	}

	/**
	 *
	 * Tests that getGetCompletedRouteCards returns an empty ArrayList when it has no Route Cards
	 */
	@Test
	public void testGetEmptyListOfRouteCards() {
		ArrayList<RouteCard> testList=new ArrayList<RouteCard>();
		
		assertEquals(testList, newHand.getCompletedRouteCards());
		assertEquals(testList, newHand.getUncompletedRouteCards());
		
		assertEquals(0,newHand.getUncompletedRouteCards().size());
		assertEquals(0,newHand.getCompletedRouteCards().size());
	}
	/* */

	/**
	 *
	 * Tests that getRouteCardsList returns the list of Route Card objects in
	 * the proper order
	 */
	@Test
	public void testGetListOfManyRouteCards() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		RouteCard nextRouteCard = new RouteCard(11);
		RouteCard thirdRouteCard = new RouteCard(42);
		RouteCard fourthRouteCard = new RouteCard(137);
		
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		newHand.addUncompletedRouteCard(nextRouteCard);
		newHand.addUncompletedRouteCard(thirdRouteCard);
		newHand.addUncompletedRouteCard(fourthRouteCard);
		
		assertEquals(4, uncompletedRouteCards.size());
		
		ArrayList<RouteCard> testList = new ArrayList<RouteCard>();
		testList.add(firstTestRouteCard);
		testList.add(nextRouteCard);
		testList.add(thirdRouteCard);
		testList.add(fourthRouteCard);
		
		assertEquals(testList, newHand.getUncompletedRouteCards());
		
		assertEquals(new ArrayList<RouteCard>(), newHand.getCompletedRouteCards());
	}
	/* */

	/**
	 * Tests that an empty ActionCard list yields the proper result
	 */
	@Test
	public void testGetEmptyListOfActionCards() {
		assertEquals(new ArrayList<ActionCard>(), newHand.getActionCardsList());
		assertEquals(0, actionCardList.size());
	}

	/**
	 * Tests that an ActionCard list with some ActionCard objects in it returns
	 * the proper list
	 */
	@Test
	public void testGetListOfManyActionCards() {
		ActionCard firstActionCard = new ActionCard(1);
		ActionCard nextActionCard = new ActionCard(11);
		ActionCard thirdActionCard = new ActionCard(42);
		ActionCard fourthRouteCard = new ActionCard(137);
		newHand.addActionCard(firstActionCard);
		newHand.addActionCard(nextActionCard);
		newHand.addActionCard(thirdActionCard);
		newHand.addActionCard(fourthRouteCard);
		assertEquals(4, actionCardList.size());
		ArrayList<ActionCard> testList = new ArrayList<ActionCard>();
		testList.add(firstActionCard);
		testList.add(nextActionCard);
		testList.add(thirdActionCard);
		testList.add(fourthRouteCard);
		assertEquals(testList, newHand.getActionCardsList());
	}

	/**
	 * Test that only ActionCard in the list is removed correctly.
	 */
	@Test
	public void testRemoveOnlyActionCard() {
		ActionCard firstActionCard = new ActionCard(1);
		newHand.addActionCard(firstActionCard);
		assertEquals(1, actionCardList.size());
		newHand.removeActionCard(firstActionCard);
		assertEquals(0, actionCardList.size());
		assertEquals(new ArrayList<ActionCard>(), actionCardList);
	}

	/**
	 * Tests to make sure that Action Cards are removed from the list of Action
	 * Cards correctly.
	 */
	@Test
	public void testRemoveActionCard() {
		ActionCard firstActionCard = new ActionCard(1);
		ActionCard nextActionCard = new ActionCard(11);
		ActionCard thirdActionCard = new ActionCard(42);
		ActionCard fourthRouteCard = new ActionCard(137);
		newHand.addActionCard(firstActionCard);
		newHand.addActionCard(nextActionCard);
		newHand.addActionCard(thirdActionCard);
		newHand.addActionCard(fourthRouteCard);
		assertEquals(4, actionCardList.size());
		newHand.removeActionCard(thirdActionCard);
		ArrayList<ActionCard> testList = new ArrayList<ActionCard>();
		testList.add(firstActionCard);
		testList.add(nextActionCard);
		testList.add(fourthRouteCard);
		assertEquals(testList, actionCardList);
		assertEquals(3, actionCardList.size());

	}

	/**
	 * Tests that removing many ActionCard objects is successful.
	 */
	@Test
	public void testRemoveManyActionCards() {
		ActionCard firstActionCard = new ActionCard(1);
		ActionCard nextActionCard = new ActionCard(11);
		ActionCard thirdActionCard = new ActionCard(42);
		ActionCard fourthRouteCard = new ActionCard(137);
		newHand.addActionCard(firstActionCard);
		newHand.addActionCard(nextActionCard);
		newHand.addActionCard(thirdActionCard);
		newHand.addActionCard(fourthRouteCard);
		assertEquals(4, actionCardList.size());
		newHand.removeActionCard(thirdActionCard);
		newHand.removeActionCard(firstActionCard);
		newHand.removeActionCard(fourthRouteCard);
		ArrayList<ActionCard> testList = new ArrayList<ActionCard>();
		testList.add(nextActionCard);
		assertEquals(testList, actionCardList);
		assertEquals(1, actionCardList.size());
	}

	/**
	 * Tests that removing an Action Card that is not in the list does nothing
	 */
	@Test
	public void testRemoveActionCardNotInHand() {
		ActionCard firstActionCard = new ActionCard(1);
		ActionCard nextActionCard = new ActionCard(11);
		ActionCard thirdActionCard = new ActionCard(42);
		ActionCard fourthRouteCard = new ActionCard(137);
		
		newHand.addActionCard(firstActionCard);
		newHand.addActionCard(nextActionCard);
		newHand.addActionCard(thirdActionCard);
		newHand.addActionCard(fourthRouteCard);
		
		assertEquals(4, actionCardList.size());
		
		newHand.removeActionCard(new ActionCard(6));
		
		assertEquals(4, actionCardList.size());
		
		ArrayList<ActionCard> testList = new ArrayList<ActionCard>();
		testList.add(firstActionCard);
		testList.add(nextActionCard);
		testList.add(thirdActionCard);
		testList.add(fourthRouteCard);
		
		assertEquals(testList, newHand.getActionCardsList());
	}

	/**
	 * Tests that completing a route switches it from uncompleted to completed.
	 */
	@Test
	public void testSwitchRouteCardFromUncompleteToComplete() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		RouteCard nextRouteCard = new RouteCard(11);
		RouteCard thirdRouteCard = new RouteCard(42);
		RouteCard fourthRouteCard = new RouteCard(137);
		
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		newHand.addUncompletedRouteCard(nextRouteCard);
		newHand.addUncompletedRouteCard(thirdRouteCard);
		newHand.addUncompletedRouteCard(fourthRouteCard);
		
		assertEquals(4, uncompletedRouteCards.size());
		
		newHand.switchRouteToCompleted(thirdRouteCard);
		
		assertEquals(3, uncompletedRouteCards.size());
		
		assertEquals(1, completedRouteCards.size());
		
		ArrayList<RouteCard> testUncompletedList = new ArrayList<RouteCard>();
		testUncompletedList.add(firstTestRouteCard);
		testUncompletedList.add(nextRouteCard);
		testUncompletedList.add(fourthRouteCard);
		
		ArrayList<RouteCard> testCompletedList = new ArrayList<RouteCard>();
		testCompletedList.add(thirdRouteCard);
		
		assertEquals(testUncompletedList, uncompletedRouteCards);
		assertEquals(testCompletedList, completedRouteCards);
	}

	/**
	 * Tests that many cards switching from uncompleted RouteCard list to
	 * completed RouteCard list.
	 */
	@Test
	public void testThatManyRouteCardSwitchFromUncompleteToComplete() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		RouteCard nextRouteCard = new RouteCard(11);
		RouteCard thirdRouteCard = new RouteCard(42);
		RouteCard fourthRouteCard = new RouteCard(137);
		RouteCard fifthRouteCard = new RouteCard(167);
		RouteCard sixthRouteCard = new RouteCard(17);
		
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		newHand.addUncompletedRouteCard(nextRouteCard);
		newHand.addUncompletedRouteCard(thirdRouteCard);
		newHand.addUncompletedRouteCard(fourthRouteCard);
		newHand.addUncompletedRouteCard(fifthRouteCard);
		newHand.addUncompletedRouteCard(sixthRouteCard);
		
		assertEquals(6, uncompletedRouteCards.size());
		
		ArrayList<RouteCard> testUncompletedList = new ArrayList<RouteCard>();
		ArrayList<RouteCard> testCompletedList = new ArrayList<RouteCard>();
		
		newHand.switchRouteToCompleted(firstTestRouteCard);
		testCompletedList.add(firstTestRouteCard);
		
		newHand.switchRouteToCompleted(sixthRouteCard);
		testCompletedList.add(sixthRouteCard);
		
		newHand.switchRouteToCompleted(thirdRouteCard);
		testCompletedList.add(thirdRouteCard);
		
		newHand.switchRouteToCompleted(fifthRouteCard);
		testCompletedList.add(fifthRouteCard);
		
		testUncompletedList.add(nextRouteCard);
		testUncompletedList.add(fourthRouteCard);
		
		assertEquals(2, uncompletedRouteCards.size());
		assertEquals(4, completedRouteCards.size());
		assertEquals(testUncompletedList, uncompletedRouteCards);
		assertEquals(testCompletedList, completedRouteCards);
	}

	/**
	 *
	 * Tests that the getCompletedRouteCards returns the proper ArrayList of RouteCards.
	 * 
	 */
	@Test
	public void testGeCompletedRouteCardListForManyRouteCards() {
		RouteCard firstTestRouteCard = new RouteCard(1);
		RouteCard nextRouteCard = new RouteCard(11);
		RouteCard thirdRouteCard = new RouteCard(42);
		RouteCard fourthRouteCard = new RouteCard(137);
		RouteCard fifthRouteCard = new RouteCard(167);
		RouteCard sixthRouteCard = new RouteCard(17);
		
		newHand.addUncompletedRouteCard(firstTestRouteCard);
		newHand.addUncompletedRouteCard(nextRouteCard);
		newHand.addUncompletedRouteCard(thirdRouteCard);
		newHand.addUncompletedRouteCard(fourthRouteCard);
		newHand.addUncompletedRouteCard(fifthRouteCard);
		newHand.addUncompletedRouteCard(sixthRouteCard);
		
		assertEquals(6, uncompletedRouteCards.size());
		
		ArrayList<RouteCard> testUncompletedList = new ArrayList<RouteCard>();
		ArrayList<RouteCard> testCompletedList = new ArrayList<RouteCard>();
		
		newHand.switchRouteToCompleted(firstTestRouteCard);
		testCompletedList.add(firstTestRouteCard);
		
		newHand.switchRouteToCompleted(sixthRouteCard);
		testCompletedList.add(sixthRouteCard);
		
		newHand.switchRouteToCompleted(thirdRouteCard);
		testCompletedList.add(thirdRouteCard);
		
		newHand.switchRouteToCompleted(fifthRouteCard);
		testCompletedList.add(fifthRouteCard);
		
		testUncompletedList.add(nextRouteCard);
		testUncompletedList.add(fourthRouteCard);
		
		assertEquals(testUncompletedList, newHand.getUncompletedRouteCards());
		assertEquals(testCompletedList, newHand.getCompletedRouteCards());
	}
	/* */

}
