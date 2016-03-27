package doctorWhoGame;

import java.util.ArrayDeque;

public class RouteCardDeck {
	
	private static ArrayDeque<RouteCard> deck;
	
	public RouteCardDeck(ArrayDeque<RouteCard> routes) {
		RouteCardDeck.deck = routes;
	}

	public int size() {
		return deck.size();
	}
	
	/**
	 * Inserts a RouteCard back into the bottom of the routes deck
	 */
	public static void reinsertRouteCard(RouteCard r) {
		deck.offer(r);
	}

	public static RouteCard drawRouteCard() {

		if (!deck.isEmpty() && Game.checkIfCanDrawAgain() && !Game.checkIfHasDrawnOne()) {
			return deck.poll();
		}
		return null;
	}

	

}
