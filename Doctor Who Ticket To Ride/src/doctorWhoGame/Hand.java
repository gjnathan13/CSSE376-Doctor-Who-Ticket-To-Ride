package doctorWhoGame;

import java.util.ArrayList;

/**
 * The hand object which contains many sets of cards. Each set is a different
 * color or it is the route cards the player has, or it is the action cards the
 * player has. The train card sets in the hand go in the order: 0-Red 1-Pink 2-
 * Orange 3- Yellow 4-Green 5-Blue 6-White 7-Black 8-Rainbow
 * 
 * @author wrightsd
 * 
 */
public class Hand {

	private ArrayList<ArrayList<String>> trainCards;
	private ArrayList<RouteCard> routeCards;
	private ArrayList<ActionCard> actionCards;

	/**
	 * The constructor for the hand object that initializes all the different
	 * sets of cards.
	 */

	public Hand() {
		this.trainCards = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 9; i++) {
			this.trainCards.add(new ArrayList<String>());
		}
		this.routeCards = new ArrayList<RouteCard>();
		this.actionCards = new ArrayList<ActionCard>();
	}

	/**
	 * Adds the given train card color string to the correct sub-ArrayList of
	 * the train colors ArrayList. Timothy Anderson gave me the idea to use a
	 * switching function.
	 * 
	 * @param trainCard
	 *            String that is the color of the train card with the first
	 *            letter capitalized. The options are Red, Pink, Orange, Yellow,
	 *            Green, Blue, White, Black, and Rainbow.
	 */
	public void addTrainCard(String trainCard) {
		if (trainCard != null) {
			switch (trainCard) {
			case "Red": {
				this.trainCards.get(0).add(trainCard);
				break;
			}
			case "Pink": {
				this.trainCards.get(1).add(trainCard);
				break;
			}
			case "Orange": {
				this.trainCards.get(2).add(trainCard);
				break;
			}
			case "Yellow": {
				this.trainCards.get(3).add(trainCard);
				break;
			}
			case "Green": {
				this.trainCards.get(4).add(trainCard);
				break;
			}
			case "Blue": {
				this.trainCards.get(5).add(trainCard);
				break;
			}
			case "White": {
				this.trainCards.get(6).add(trainCard);
				break;
			}
			case "Black": {
				this.trainCards.get(7).add(trainCard);
				break;
			}
			case "Rainbow": {
				this.trainCards.get(8).add(trainCard);
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
	public void removeTrainCard(String trainCard) {
		switch (trainCard) {
		case "Red": {
			ArrayList<String> list = this.trainCards.get(0);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Pink": {
			ArrayList<String> list = this.trainCards.get(1);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Orange": {
			ArrayList<String> list = this.trainCards.get(2);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Yellow": {
			ArrayList<String> list = this.trainCards.get(3);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Green": {
			ArrayList<String> list = this.trainCards.get(4);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Blue": {
			ArrayList<String> list = this.trainCards.get(5);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "White": {
			ArrayList<String> list = this.trainCards.get(6);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Black": {
			ArrayList<String> list = this.trainCards.get(7);
			int size = list.size();
			if (size != 0) {
				list.remove(size - 1);
			}
			break;
		}
		case "Rainbow": {
			ArrayList<String> list = this.trainCards.get(8);
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
	 * Adds a RouteCard object to the appropriate spot in the Hand object.
	 * 
	 * @param newRouteCard
	 *            RouteCard object that is the new route to be added to the hand
	 */
	public void addRouteCard(RouteCard newRouteCard) {
		this.routeCards.add(newRouteCard);

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
	 * Returns the list of RouteCards in the Hand.
	 * 
	 * @return ArrayList<RouteCard> that is the list of all the RouteCard
	 *         objects in the Hand
	 */
	public ArrayList<RouteCard> getRouteCardsList() {
		return this.routeCards;
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

}
