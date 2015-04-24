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
 * @author wrightsd and whitehts
 * 
 */
public class Hand {

	private ArrayList<ArrayList<TrainColor>> trainCards;
	private ArrayList<ActionCard> actionCards;
	private ArrayList<RouteCard> uncompletedRouteCards;
	private ArrayList<RouteCard> completedRouteCards;

	private ArrayList<ArrayList<Integer>> nodeConnectionMatrix;

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

		this.nodeConnectionMatrix = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 50; i++) {
			this.nodeConnectionMatrix.add(new ArrayList<Integer>());
		}
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
		Node[] nodes = newRouteCard.getNodes();
		if (nodes != null && nodesAreConnected(nodes[0], nodes[1])){
			this.completedRouteCards.add(newRouteCard);
		} else {
			this.uncompletedRouteCards.add(newRouteCard);
		}

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
		return new ArrayList<RouteCard>(this.uncompletedRouteCards);
	}

	/**
	 * Returns the ArrayList of completed RouteCards
	 * 
	 * @return ArrayList<RouteCard> the ArrayList of completed routes
	 * 
	 */
	public ArrayList<RouteCard> getCompletedRouteCards() {
		return new ArrayList<RouteCard>(this.completedRouteCards);
	}

	/**
	 * Adds a path into the nodeConnectionMatrix so we can check if routes have
	 * been completed
	 * 
	 * @param testPath
	 *            the path to be added into the connection matrix
	 */
	public void addPath(Path testPath) {
		// grab the nodes from the path
		Node[] nodes = testPath.getNodes();

		// get their IDs
		int n1ID = nodes[0].getID();
		int n2ID = nodes[1].getID();

		// If they are already connected, we can assume we don't need to do
		// this, so just return
		if (this.nodeConnectionMatrix.get(n1ID).contains(n2ID))
			return;

		// Make a reference to their connections for brevity and readability
		ArrayList<Integer> n1Connections = this.nodeConnectionMatrix.get(n1ID);
		ArrayList<Integer> n2Connections = this.nodeConnectionMatrix.get(n2ID);

		// Give your connections the other node and it's connections
		for (Integer connection : n1Connections) {
			this.nodeConnectionMatrix.get(connection).addAll(n2Connections);
			this.nodeConnectionMatrix.get(connection).add(n2ID);

		}
		for (Integer connection : n2Connections) {
			this.nodeConnectionMatrix.get(connection).addAll(n1Connections);
			this.nodeConnectionMatrix.get(connection).add(n1ID);
		}

		// Give the other node your connections
		this.nodeConnectionMatrix.get(n1ID).addAll(n2Connections);
		this.nodeConnectionMatrix.get(n2ID).addAll(n1Connections);

		// Connect to the other node
		this.nodeConnectionMatrix.get(n1ID).add(n2ID);
		this.nodeConnectionMatrix.get(n2ID).add(n1ID);

		
		/* */
		// Check if that completed any routes
		ArrayList<RouteCard> toRemove = new ArrayList<RouteCard>();
		for (RouteCard r : uncompletedRouteCards) {
			Node[] n = r.getNodes();
			if (nodesAreConnected(n[0], n[1]))
				toRemove.add(r);
		}
		for (RouteCard r : toRemove){
			switchRouteToCompleted(r);
		}
		/* */
	}

	/**
	 * Check whether the two nodes are connected
	 * 
	 * @param n1
	 * @param n2
	 * @return true if connected, false if not
	 */
	public boolean nodesAreConnected(Node n1, Node n2) {
		return this.nodeConnectionMatrix.get(n1.getID()).contains(n2.getID());
	}

}
