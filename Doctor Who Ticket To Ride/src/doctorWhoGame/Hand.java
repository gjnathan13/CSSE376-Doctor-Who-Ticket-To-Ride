package doctorWhoGame;

import java.util.ArrayList;

/**
 * The hand object which contains many sets of cards. Each set is a different
 * color train cards or it is the route cards the player has, or it is the
 * action cards the player has. The train card sets in the hand go in the order:
 * 0-Red 1-Pink 2- Orange 3- Yellow 4-Green 5-Blue 6-White 7-Black 8-Rainbow.
 * The Action Cards ArrayList is a single ArrayList of Action Cards. The Route
 * Card ArrayList of ArrayLists has the first slot in the ArrayList of
 * ArrayLists being the ArrayList of uncompleted RouteCard objects, and the
 * second slot being the ArrayList of completed RouteCard objects.
 * 
 * @author wrightsd
 * 
 */
public class Hand {

	private ArrayList<ArrayList<TrainColor>> trainCards;
	private ArrayList<ActionCard> actionCards;
	private ArrayList<RouteCard> uncompletedRouteCards;
	private ArrayList<RouteCard> completedRouteCards;

	/**
	 * The constructor for the hand object that initializes all the different
	 * sets of cards.
	 */

	public Hand() {
		this.trainCards = new ArrayList<ArrayList<TrainColor>>();
		for (int i = 0; i < 9; i++) {
			this.trainCards.add(new ArrayList<TrainColor>());
		}

		this.uncompletedRouteCards = new ArrayList<RouteCard>();
		this.completedRouteCards = new ArrayList<RouteCard>();

		this.actionCards = new ArrayList<ActionCard>();
	}

	/**
	 * Adds the given train card color string to the correct sub-ArrayList of
	 * the train colors ArrayList. Timothy Anderson gave me the idea to use a
	 * switching function.
	 * 
	 * @param drawnCard
	 *            String that is the color of the train card with the first
	 *            letter capitalized. The options are Red, Pink, Orange, Yellow,
	 *            Green, Blue, White, Black, and Rainbow.
	 */
	public void addTrainCard(TrainColor drawnCard) {
		if (drawnCard != null) {
			switch (drawnCard) {
			case Red: {
				this.trainCards.get(0).add(drawnCard);
				break;
			}
			case Pink: {
				this.trainCards.get(1).add(drawnCard);
				break;
			}
			case Orange: {
				this.trainCards.get(2).add(drawnCard);
				break;
			}
			case Yellow: {
				this.trainCards.get(3).add(drawnCard);
				break;
			}
			case Green: {
				this.trainCards.get(4).add(drawnCard);
				break;
			}
			case Blue: {
				this.trainCards.get(5).add(drawnCard);
				break;
			}
			case White: {
				this.trainCards.get(6).add(drawnCard);
				break;
			}
			case Black: {
				this.trainCards.get(7).add(drawnCard);
				break;
			}
			case Rainbow: {
				this.trainCards.get(8).add(drawnCard);
				break;
			}
			}
		}

	}

	/**
	 * Remove the given string from the list of train cards, or returns null if
	 * it is not able to do so.
	 * 
	 * @param trainCard
	 *            String giving the color of the card to be removed. The options
	 *            are Red, Pink, Orange, Yellow, Green, Blue, White, Black, and
	 *            Rainbow.
	 */
	public void removeTrainCard(TrainColor trainCard) {
		switch (trainCard) {
		case Red: {
			ArrayList<TrainColor> list = this.trainCards.get(0);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Pink: {
			ArrayList<TrainColor> list = this.trainCards.get(1);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Orange: {
			ArrayList<TrainColor> list = this.trainCards.get(2);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Yellow: {
			ArrayList<TrainColor> list = this.trainCards.get(3);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Green: {
			ArrayList<TrainColor> list = this.trainCards.get(4);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Blue: {
			ArrayList<TrainColor> list = this.trainCards.get(5);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case White: {
			ArrayList<TrainColor> list = this.trainCards.get(6);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Black: {
			ArrayList<TrainColor> list = this.trainCards.get(7);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case Rainbow: {
			ArrayList<TrainColor> list = this.trainCards.get(8);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		}
	}

	/**
	 * Returns the number of each color of train cards in the list.
	 * 
	 * @return ArrayList<Integer> of the numbers of each color of train cards in
	 *         the order Red, Pink, Orange, Yellow, Green, Blue, White, Black,
	 *         Rainbow
	 */
	public ArrayList<Integer> getNumberOfTrainCards() {
		ArrayList<Integer> numberOfCards = new ArrayList<Integer>();
		for (int i = 0; i < 9; i++) {
			numberOfCards.add(this.trainCards.get(i).size());
		}
		return numberOfCards;
	}

	/**
	 * Adds a RouteCard object to the uncompleted Route Card list in the Hand
	 * object (the first slot in the RouteCards ArrayList).
	 * 
	 * @param newRouteCard
	 *            RouteCard object that is the new route to be added to the hand
	 */
	public void addUncompletedRouteCard(RouteCard newRouteCard) {
		this.uncompletedRouteCards.add(newRouteCard);

	}

	/**
	 * Adds the action card to the list of action cards in the Hand.
	 * 
	 * @param newActionCard
	 *            ActionCard that is the card to add to the hand.
	 */
	public void addActionCard(ActionCard newActionCard) {
		this.actionCards.add(newActionCard);

	}

	/**
	 * Returns the list of Action Cards in the Hand
	 * 
	 * @return ArrayList<ActionCard> that is the list of Action Cards in the
	 *         Hand
	 */
	public ArrayList<ActionCard> getActionCardsList() {
		return this.actionCards;
	}

	/**
	 * Removes the given Action Card object from the Hand. Assumes the card is
	 * in the Hand.
	 * 
	 * @param actionCard
	 *            ActionCard object that is to be removed from the Hand
	 */
	public void removeActionCard(ActionCard actionCard) {
		this.actionCards.remove(actionCard);

	}

	/**
	 * Switches the given route from the uncompleted list (first slot in the
	 * RouteCard ArrayList of ArrayLists) to the completed list (the second slot
	 * in the RouteCard ArrayList of ArrayLists). Assumes the route has been
	 * checked elsewhere to make sure it is completed.
	 * 
	 * @param completedRouteCard
	 *            RouteCard that is to be switched from the uncompleted list to
	 *            the completed
	 */
	public void switchRouteToCompleted(RouteCard completedRouteCard) {
		this.uncompletedRouteCards.remove(completedRouteCard);
		this.completedRouteCards.add(completedRouteCard);

	}

	/**
	 * Returns the ArrayList of uncompleted RouteCards
	 * 
	 * @return ArrayList<RouteCard> the ArrayList of uncompleted routes
	 * 
	 */

	public ArrayList<RouteCard> getUncompletedRouteCards() {
		return new ArrayList<RouteCard>(uncompletedRouteCards);
	}
	
	/**
	 * Returns the ArrayList of completed RouteCards
	 * 
	 * @return ArrayList<RouteCard> the ArrayList of completed routes
	 * 
	 */
	public ArrayList<RouteCard> getCompletedRouteCards() {
		return new ArrayList<RouteCard>(completedRouteCards);
	}

}
