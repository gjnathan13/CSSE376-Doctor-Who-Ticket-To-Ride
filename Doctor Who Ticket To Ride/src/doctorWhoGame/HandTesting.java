package doctorWhoGame;

import static org.junit.Assert.*;

import java.lang.reflect.Field;
import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

/**
 * Tests the Hand class
 * 
 * @author wrightsd and whitehts
 *
 */
public class HandTesting {
	private ArrayList<ArrayList<String>> trainCardList;
	private Hand newHand;
	private ArrayList<RouteCard> uncompletedRouteCards;
	private ArrayList<RouteCard> completedRouteCards;
	private ArrayList<ActionCard> actionCardList;
	private ArrayList<ArrayList<Integer>> nodeConnectionMatrix;
	private ArrayList<ArrayList<Integer>> nodeNeighborMatrix;
	private int[][] lengthsMatrix;

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

		Field nodeConnectionMatrix = Hand.class
				.getDeclaredField("nodeConnectionMatrix");
		nodeConnectionMatrix.setAccessible(true);
		this.nodeConnectionMatrix = (ArrayList<ArrayList<Integer>>) nodeConnectionMatrix
				.get(newHand);
		
		Field nodeNeighborMatrix = Hand.class
				.getDeclaredField("nodeNeighborMatrix");
		nodeNeighborMatrix.setAccessible(true);
		this.nodeNeighborMatrix = (ArrayList<ArrayList<Integer>>) nodeNeighborMatrix
				.get(newHand);
		
		Field lengthsMatrix = Hand.class
				.getDeclaredField("lengthsMatrix");
		lengthsMatrix.setAccessible(true);
		this.lengthsMatrix = (int[][]) lengthsMatrix
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
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2);
		
		newHand.addRouteCard(firstTestRouteCard);
		assertEquals(1, uncompletedRouteCards.size());
		assertEquals(firstTestRouteCard, uncompletedRouteCards.get(0));
	}

	/**
	 * Adds many RouteCard objects to the hand and checks that it worked
	 */
	@Test
	public void testAddManyRouteCardsToHand() {
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2);
		RouteCard nextRouteCard = new RouteCard(11, n1, n2);
		RouteCard thirdRouteCard = new RouteCard(42, n1, n2);
		RouteCard fourthRouteCard = new RouteCard(137, n1, n2);
		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);
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
	 * Tests that getGetCompletedRouteCards returns an empty ArrayList when it
	 * has no Route Cards
	 */
	@Test
	public void testGetEmptyListOfRouteCards() {
		ArrayList<RouteCard> testList = new ArrayList<RouteCard>();

		assertEquals(testList, newHand.getCompletedRouteCards());
		assertEquals(testList, newHand.getUncompletedRouteCards());

		assertEquals(0, newHand.getUncompletedRouteCards().size());
		assertEquals(0, newHand.getCompletedRouteCards().size());
	}

	/* */

	/**
	 *
	 * Tests that getRouteCardsList returns the list of Route Card objects in
	 * the proper order
	 */
	@Test
	public void testGetListOfManyRouteCards() {
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2);
		RouteCard nextRouteCard = new RouteCard(11, n1, n2);
		RouteCard thirdRouteCard = new RouteCard(42, n1, n2);
		RouteCard fourthRouteCard = new RouteCard(137, n1, n2);

		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);

		assertEquals(4, uncompletedRouteCards.size());

		ArrayList<RouteCard> testList = new ArrayList<RouteCard>();
		testList.add(firstTestRouteCard);
		testList.add(nextRouteCard);
		testList.add(thirdRouteCard);
		testList.add(fourthRouteCard);

		assertEquals(testList, newHand.getUncompletedRouteCards());

		assertEquals(new ArrayList<RouteCard>(),
				newHand.getCompletedRouteCards());
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
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2,12);
		RouteCard nextRouteCard = new RouteCard(11, n1, n2,14);
		RouteCard thirdRouteCard = new RouteCard(42, n1, n2,16);
		RouteCard fourthRouteCard = new RouteCard(137, n1, n2,17);

		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);

		assertEquals(4, uncompletedRouteCards.size());

		newHand.switchRouteToCompleted(thirdRouteCard);

		assertEquals(3, uncompletedRouteCards.size());
		
		assertEquals(16,newHand.getCompletedRouteScore());

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
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2);
		RouteCard nextRouteCard = new RouteCard(11, n1, n2);
		RouteCard thirdRouteCard = new RouteCard(42, n1, n2);
		RouteCard fourthRouteCard = new RouteCard(137, n1, n2);
		RouteCard fifthRouteCard = new RouteCard(167, n1, n2);
		RouteCard sixthRouteCard = new RouteCard(17, n1, n2);

		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);
		newHand.addRouteCard(fifthRouteCard);
		newHand.addRouteCard(sixthRouteCard);

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
	 * Tests that the getCompletedRouteCards returns the proper ArrayList of
	 * RouteCards.
	 * 
	 */
	@Test
	public void testGetCompletedRouteCardListForManyRouteCards() {
		Node n1 = new Node(0);
		Node n2 = new Node(1);
		
		RouteCard firstTestRouteCard = new RouteCard(1, n1, n2);
		RouteCard nextRouteCard = new RouteCard(11, n1, n2);
		RouteCard thirdRouteCard = new RouteCard(42, n1, n2);
		RouteCard fourthRouteCard = new RouteCard(137, n1, n2);
		RouteCard fifthRouteCard = new RouteCard(167, n1, n2);
		RouteCard sixthRouteCard = new RouteCard(17, n1, n2);

		newHand.addRouteCard(firstTestRouteCard);
		newHand.addRouteCard(nextRouteCard);
		newHand.addRouteCard(thirdRouteCard);
		newHand.addRouteCard(fourthRouteCard);
		newHand.addRouteCard(fifthRouteCard);
		newHand.addRouteCard(sixthRouteCard);

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

	/**
	 * Test that the hand properly puts paths into its node connection matrix.
	 * Assumes a proper path was made
	 */
	@Test
	public void TestHandAddsPathToNodeConnectionMatrix() {
		Node node0 = new Node(0, 0, 0);
		Node node1 = new Node(1, 0, 0);

		Path testPath = new Path(node0, node1, TrainColor.Red, 0);

		// Give the new path to the hand to process
		newHand.addPath(testPath);

		// Make sure that each node knows it is connected to another node
		assertTrue(nodeConnectionMatrix.get(node0.getID()).size() == 1);
		assertTrue(nodeConnectionMatrix.get(node1.getID()).size() == 1);

		// Once we know they exist, check and make sure that the nodes are
		// connected to the proper nodes
		assertTrue(node1.getID() == nodeConnectionMatrix.get(node0.getID())
				.get(0));
		assertTrue(node0.getID() == nodeConnectionMatrix.get(node1.getID())
				.get(0));
	}

	/**
	 * Test to see if the paths can connect to one another
	 */
	@Test
	public void TestAddingConnectingPathsToTheNodeConnectionMatrix() {

		// Make new nodes
		Node n0 = new Node(0, 0, 0);
		Node n1 = new Node(1, 0, 0);
		Node n2 = new Node(2, 0, 0);

		// Make Paths so n0 and n2 are connected by n1
		Path p1 = new Path(n0, n1, TrainColor.Red, 0);
		Path p2 = new Path(n1, n2, TrainColor.Red, 0);

		// Add the paths to the hand
		newHand.addPath(p1);
		newHand.addPath(p2);
		
		// Make sure that each node has the proper number of connection
		assertTrue(nodeConnectionMatrix.get(n0.getID()).size() == 2);
		assertTrue(nodeConnectionMatrix.get(n1.getID()).size() == 2);
		assertTrue(nodeConnectionMatrix.get(n2.getID()).size() == 2);

		// Make sure the nodes were given the proper connections
		assertTrue(nodeConnectionMatrix.get(n0.getID()).contains(n1.getID())
				&& nodeConnectionMatrix.get(n0.getID()).contains(n2.getID()));
		
		assertTrue(nodeConnectionMatrix.get(n1.getID()).contains(n0.getID())
				&& nodeConnectionMatrix.get(n1.getID()).contains(n2.getID()));
		
		assertTrue(nodeConnectionMatrix.get(n2.getID()).contains(n0.getID())
				&& nodeConnectionMatrix.get(n2.getID()).contains(n1.getID()));
	}
	
	/**
	 * Test to make sure that, once you add paths, that they know they are connected 
	 */
	@Test
	public void TestHandKnowsPathsAreConnected(){
		Node n0 = new Node(0, 0, 0);
		Node n1 = new Node(1, 0, 0);
		Node n2 = new Node(2, 0, 0);
		Node n3 = new Node(3, 0, 0);
		Node n4 = new Node(4, 0, 0);
		Node n5 = new Node(5, 0, 0);
		Node n6 = new Node(6, 0, 0);

		// graph 1
		Path p1 = new Path(n0, n1, TrainColor.Red, 0);
		Path p2 = new Path(n1, n2, TrainColor.Red, 0);
		
		// graph 2
		Path p3 = new Path(n3, n4, TrainColor.Red, 0);
		Path p4 = new Path(n4, n5, TrainColor.Red, 0);
		Path p5 = new Path(n4, n6, TrainColor.Red, 0);
		
		// connection between the graphs
		Path p6 = new Path(n1, n3, TrainColor.Red, 0);

		// establish both graphs but not the connection
		newHand.addPath(p1);
		newHand.addPath(p2);
		newHand.addPath(p3);
		newHand.addPath(p4);
		newHand.addPath(p5);
		
		// make sure this fails, because they are in different graphs
		assertTrue(!newHand.nodesAreConnected(n0, n6));
		
		// establish the connection
		newHand.addPath(p6);
		
		// check if connection was made
		assertTrue(newHand.nodesAreConnected(n0, n6));
	}
	
	/**
	 * Test to make sure that adding paths to the hand updates whether routes have been completed or not
	 */
	@Test
	public void TestRoutesMoveFromUncompletedToCompleted(){
		Node n0 = new Node(0, 0, 0);
		Node n1 = new Node(1, 0, 0);
		Node n2 = new Node(2, 0, 0);
		Node n3 = new Node(3, 0, 0);
		Node n4 = new Node(4, 0, 0);
		Node n5 = new Node(5, 0, 0);
		Node n6 = new Node(6, 0, 0);

		// graph 1
		Path p1 = new Path(n0, n1, TrainColor.Red, 0);
		Path p2 = new Path(n1, n2, TrainColor.Red, 0);
		
		// graph 2
		Path p3 = new Path(n3, n4, TrainColor.Red, 0);
		Path p4 = new Path(n4, n5, TrainColor.Red, 0);
		Path p5 = new Path(n4, n6, TrainColor.Red, 0);
		
		// connection between the graphs
		Path p6 = new Path(n1, n3, TrainColor.Red, 0);

		// establish both graphs but not the connection
		newHand.addPath(p1);
		newHand.addPath(p2);
		newHand.addPath(p3);
		newHand.addPath(p4);
		newHand.addPath(p5);
		
		// Route that should be complete
		RouteCard route1 = new RouteCard(0, n0, n2);
		
		// Route that should be uncompleted
		RouteCard route2 = new RouteCard(0, n0, n6);
		
		// Add to uncompleted routes. The completed one should go to completedRouteCards automatically
		newHand.addRouteCard(route1);
		newHand.addRouteCard(route2);
		
		// Check that they have the appropriate number of routes in each
		assertEquals(1, uncompletedRouteCards.size());
		assertEquals(1, completedRouteCards.size());
		
		// Check that they are the correct routes
		assertEquals(route1, completedRouteCards.get(0));
		assertEquals(route2, uncompletedRouteCards.get(0));
		
		// Add the connecting path
		newHand.addPath(p6);
		
		// Check that they have the appropriate number of routes in each
		assertEquals(0, uncompletedRouteCards.size());
		assertEquals(2, completedRouteCards.size());
		
		// Check that they are the correct routes
		assertTrue(completedRouteCards.contains(route1));
		assertTrue(completedRouteCards.contains(route2));
	}
	
	/**
	 * Test that the pathLe
	 */
	@Test
	public void testPathLengthMatrixUpdates(){
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		
		int l = 7;
		Path p1 = new Path(n1, n2, TrainColor.Black, l);
		
		assertEquals(0, lengthsMatrix[n1.getID()][n2.getID()]);
		
		newHand.updateLengthsMatrixWithPath(p1);
		
		assertEquals(l, lengthsMatrix[n1.getID()][n2.getID()]);
	}
	
	@Test
	public void testGetLengthBetweenNodes(){
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		
		int l = 7;
		Path p1 = new Path(n1, n2, TrainColor.Black, l);
		
		assertEquals(0, newHand.getLengthBetweenNodes(n1, n2));
		
		newHand.updateLengthsMatrixWithPath(p1);
		
		assertEquals(l, newHand.getLengthBetweenNodes(n1, n2));
	}
	
	@Test
	public void testCheckingANodesNeighbors(){
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		
		Path p1 = new Path(n1, n2, TrainColor.Black, 1);
		Path p2 = new Path(n1, n3, TrainColor.Black, 1);
		Path p3 = new Path(n3, n4, TrainColor.Black, 1);
		Path p4 = new Path(n2, n4, TrainColor.Black, 1);
		Path p5 = new Path(n4, n5, TrainColor.Black, 1);
		
		assertEquals(new ArrayList<Integer>(), nodeNeighborMatrix.get(n1.getID()));
		
		newHand.updateNodeNeighborMatrixWithPath(p1);
		
		ArrayList<Integer> neighbors = new ArrayList<Integer>();
		neighbors.add(2);
		
		assertEquals(neighbors, nodeNeighborMatrix.get(n1.getID()));
		
		newHand.updateNodeNeighborMatrixWithPath(p2);
		
		neighbors.add(3);
		
		assertEquals(neighbors, nodeNeighborMatrix.get(n1.getID()));
	}

	@Test
	public void testGetLongestLength(){
		Node n1 = new Node(1);
		Node n2 = new Node(2);
		Node n3 = new Node(3);
		Node n4 = new Node(4);
		Node n5 = new Node(5);
		
		Path p1 = new Path(n1, n2, TrainColor.Black, 1);
		Path p2 = new Path(n1, n3, TrainColor.Black, 1);
		Path p3 = new Path(n3, n4, TrainColor.Black, 1);
		Path p4 = new Path(n2, n4, TrainColor.Black, 1);
		Path p5 = new Path(n4, n5, TrainColor.Black, 1);
		
		newHand.addPath(p1);
		newHand.addPath(p2);
		newHand.addPath(p3);
		newHand.addPath(p4);
		newHand.addPath(p5);
		
		assertEquals(4, newHand.getLongestLength());
	}

}
