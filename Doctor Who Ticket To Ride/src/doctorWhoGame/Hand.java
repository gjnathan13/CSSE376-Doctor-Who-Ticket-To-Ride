package doctorWhoGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;

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

	private ArrayList<ArrayList<Color>> trainCards;
	private ArrayList<RouteCard> uncompletedRouteCards;
	private ArrayList<RouteCard> completedRouteCards;

	private ArrayList<ArrayList<Integer>> nodeConnectionMatrix;
	private ArrayList<ArrayList<Integer>> nodeNeighborMatrix;
	private int[][] lengthsMatrix;
	private ArrayList<Integer> allNodeIDs;
	
	private static HashMap<Color, Integer> ColorMap = new HashMap<Color, Integer>();
	
	static {
		ColorMap.put(Color.RED, 0);
		ColorMap.put(Color.PINK, 1);
		ColorMap.put(Color.ORANGE, 2);
		ColorMap.put(Color.YELLOW, 3);
		ColorMap.put(Color.GREEN, 4);
		ColorMap.put(Color.BLUE, 5);
		ColorMap.put(Color.WHITE, 6);
		ColorMap.put(Color.BLACK, 7);
		ColorMap.put(Color.GRAY, 8);
	}

	/**
	 * The constructor for the hand object that initializes all the different
	 * sets of cards.
	 */

	public Hand() {
		this.trainCards = new ArrayList<ArrayList<Color>>();
		for (int i = 0; i < 9; i++) {
			this.trainCards.add(new ArrayList<Color>());
		}

		this.uncompletedRouteCards = new ArrayList<RouteCard>();
		this.completedRouteCards = new ArrayList<RouteCard>();

		// Contains list of ALL nodes this node is connected to
		this.nodeConnectionMatrix = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 50; i++) {
			this.nodeConnectionMatrix.add(new ArrayList<Integer>());
		}

		// Contains list of immediate neighbors ONLY
		this.nodeNeighborMatrix = new ArrayList<ArrayList<Integer>>();
		for (int i = 0; i < 50; i++) {
			this.nodeNeighborMatrix.add(new ArrayList<Integer>());
		}

		// Only contains the length of this path, stored this way for easy look
		// ups
		this.lengthsMatrix = new int[40][40];

		// contains the IDs of all of the nodes that paths touch
		this.allNodeIDs = new ArrayList<Integer>();
	}

	/**
	 * Adds the given train card color string to the correct sub-ArrayList of
	 * the train colors ArrayList. Timothy Anderson gave me the idea to use a
	 * switching function.
	 * 
	 * Refactored by Emily Richardson using HashMap
	 * 
	 * @param drawnCard
	 *            String that is the color of the train card with the first
	 *            letter capitalized. The options are Red, Pink, Orange, Yellow,
	 *            Green, Blue, White, Black, and Rainbow.
	 */
	public void addTrainCard(Color drawnCard) {
		if (drawnCard != null) {
			this.trainCards.get(ColorMap.get(drawnCard)).add(drawnCard);
		}

	}

	/**
	 * Remove the given string from the list of train cards, or returns null if
	 * it is not able to do so.
	 * 
	 * Refactored by Emily Richardson using HashMap
	 * 
	 * @param trainCard
	 *            String giving the color of the card to be removed. The options
	 *            are Red, Pink, Orange, Yellow, Green, Blue, White, Black, and
	 *            Rainbow.
	 */
	public void removeTrainCard(Color trainCard) {
		ArrayList<Color> list = this.trainCards.get(ColorMap.get(trainCard));
		removeCard(list);
	}

	private void removeCard(ArrayList<Color> list) {
		int size = list.size();
		if (size != 0) {
			list.remove(size - 1);
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
	public void addRouteCard(RouteCard newRouteCard) {
		Node[] nodes = newRouteCard.getNodes();
		if (nodes != null && nodesAreConnected(nodes[0], nodes[1])) {
			this.completedRouteCards.add(newRouteCard);
		} else {
			this.uncompletedRouteCards.add(newRouteCard);
		}

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
	 * Adds a path into the nodeConnectionMatrix as well as the lengthsMatrix
	 * 
	 * @param newPath
	 */
	public void addPath(Path newPath) {
		this.updateNodeConnectionMatrixWithPath(newPath);
		this.updateLengthsMatrixWithPath(newPath);
		this.updateNodeNeighborMatrixWithPath(newPath);
		this.updateAllNodeIDs(newPath);
	}

	/**
	 * Adds the node IDs to the allNodeIDs list
	 * 
	 * @param newPath
	 *            , the path to add
	 */
	private void updateAllNodeIDs(Path newPath) {
		Node[] nodes = newPath.getNodes();
		int n1id = nodes[0].getID();
		int n2id = nodes[1].getID();

		// if the nodes aren't already in, add them
		if (!this.allNodeIDs.contains(n1id))
			this.allNodeIDs.add(n1id);
		if (!this.allNodeIDs.contains(n2id))
			this.allNodeIDs.add(n2id);
	}

	/**
	 * Adds a path's length into the lengthsMatrix so we can check route lengths
	 * quickly
	 * 
	 * @param newPath
	 *            , the path to add into the lengthsMatrix
	 */
	public void updateLengthsMatrixWithPath(Path newPath) {
		Node[] nodes = newPath.getNodes();

		int n1id = nodes[0].getID();
		int n2id = nodes[1].getID();

		lengthsMatrix[n1id][n2id] = newPath.getPathLength();
		lengthsMatrix[n2id][n1id] = newPath.getPathLength();
	}

	/**
	 * Adds a path into the nodeConnectionMatrix so we can check if routes have
	 * been completed
	 * 
	 * @param newPath
	 *            the path to be added into the connection matrix
	 */
	public void updateNodeConnectionMatrixWithPath(Path newPath) {
		// grab the nodes from the path
		Node[] nodes = newPath.getNodes();

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
		for (RouteCard r : toRemove) {
			switchRouteToCompleted(r);
		}
		/* */
	}

	/**
	 * Update the nodeNeighborMatrix for the nodes in the given path
	 * 
	 * @param p1
	 */
	public void updateNodeNeighborMatrixWithPath(Path p1) {
		Node[] nodes = p1.getNodes();
		int n1id = nodes[0].getID();
		int n2id = nodes[1].getID();

		nodeNeighborMatrix.get(n1id).add(n2id);
		nodeNeighborMatrix.get(n2id).add(n1id);
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

	/**
	 * Returns the length of the path between nodes
	 * 
	 * @param n1
	 *            , the first node
	 * @param n2
	 *            , the second node
	 * @return the integer length of the path
	 */
	public int getLengthBetweenNodes(Node n1, Node n2) {
		return lengthsMatrix[n1.getID()][n2.getID()];
	}

	/**
	 * Returns the length of the path between nodes of the given ids
	 * 
	 * @param n1id
	 *            , the first node's id
	 * @param n2id
	 *            , the second node's id
	 * @return the integer length of the path
	 */
	public int getLengthBetweenNodes(int n1id, int n2id) {
		return lengthsMatrix[n1id][n2id];
	}

	/**
	 * Get all of the nodes that are directly adjacent to the given node
	 * 
	 * @param n1
	 *            , node to find the neighbors of
	 * @return the ArrayList of the neighbors
	 */
	public ArrayList<Integer> getNeighborsOfNode(Node n1) {
		return nodeNeighborMatrix.get(n1.getID());
	}

	/**
	 * Get all of the nodes that are directly adjacent to the given node
	 * 
	 * @param n1id
	 *            , the id of the node
	 * @return the ArrayList of the neighbors' IDs
	 */
	public ArrayList<Integer> getNeighborsOfNode(int n1id) {
		return nodeNeighborMatrix.get(n1id);
	}

	public int getLongestLength() {
		int longest = 0;

		// start at each node and search for their longest path
		for (int i : this.allNodeIDs) {

			// Saying this node is the current/previous node will add 0 to the
			// overall length
			// It will also not error out
			int l = this.getLongestBranchOf(i, new ArrayList<Integer>(), i);

			longest = l > longest ? l : longest;
		}

		return longest;
	}

	public int getLongestBranchOf(int currentNode, ArrayList<Integer> visited, int previous) {
		// you have visited this node
		visited.add(currentNode);

		int longest = 0;

		// for each of the neighbors
		for (int i : this.getNeighborsOfNode(currentNode)) {

			// if they haven't been visited in this branch already
			if (!visited.contains(i)) {

				// get the length of the longest branch of that neighbor
				int l = this.getLongestBranchOf(i, new ArrayList<Integer>(visited), currentNode);

				// if it is longer than the longest recorded branch so far,
				// record it
				longest = l > longest ? l : longest;
			}
		}

		// return the longest branch of this node added to the length between it
		// and the previous node
		return longest + this.getLengthBetweenNodes(currentNode, previous);
	}

	public boolean isPathOwned(Path p) {
		Node[] nodes = p.getNodes();
		return this.getNeighborsOfNode(nodes[0]).contains(nodes[1].getID());
	}

}
