package doctorWhoGame;

import java.util.ArrayList;

/**
 * The hand object which contains many sets of cards. Each set is a different
 * color or it is the route cards the player has, or it is the action cards the
 * player has. The train card sets in the hand go in the order: 0-RED 1-PINK 2-
 * ORANGE 3- YELLOW 4-GREEN 5-BLUE 6-WHITE 7-BLACK 8-RAINBOW
 * @author wrightsd
 *
 */
public class Hand {

	private ArrayList<ArrayList<String>> trainCards;

	/**
	 * The constructor for the hand object that initializes all the different
	 * sets of cards.
	 */

	public Hand() {
		this.trainCards = new ArrayList<ArrayList<String>>();
		for (int i = 0; i < 9; i++) {
			this.trainCards.add(new ArrayList<String>());
		}
	}

	public void addTrainCard(String trainCard) {
		this.trainCards.get(0).add(trainCard);
		
	}

}
