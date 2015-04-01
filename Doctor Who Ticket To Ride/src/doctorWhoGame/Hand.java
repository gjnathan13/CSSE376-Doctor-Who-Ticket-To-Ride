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
