package doctorWhoGame;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck {
	private static ArrayList<Color> discard = new ArrayList<Color>();

	private static ArrayList<Color> deck = getNewDeck();

	private static ArrayList<Color> getNewDeck() {
		ArrayList<Color> newDeck = new ArrayList<Color>();
		int numOfColorCards = 12;
		for (int i = 0; i < numOfColorCards; i++) {
			newDeck.add(Color.PINK);
			newDeck.add(Color.WHITE);
			newDeck.add(Color.BLUE);
			newDeck.add(Color.YELLOW);
			newDeck.add(Color.ORANGE);
			newDeck.add(Color.BLACK);
			newDeck.add(Color.RED);
			newDeck.add(Color.GREEN);
		}
		int numOfGrayCards = 14;
		for (int i = 0; i < numOfGrayCards; i++)
			newDeck.add(Color.GRAY);

		Collections.shuffle(newDeck);

		return newDeck;
	}

	public static void refillDeck() {
		if (discard != new ArrayList<Color>()) {
			for (int i = discard.size() - 1; i > -1; i--) {
				deck.add(discard.remove(i));
			}
			shuffle();
		}
	}

	public static void discard(Color discarded) {
		discard.add(discarded);
	}

	public static int size() {
		return deck.size();
	}

	public static Color draw() {
		if (size() == 0 && discard.size() != 0) {
			refillDeck();
		}
		if (size() != 0) {
			return deck.remove(deck.size() - 1);
		}
		return null;
	}

	public static ArrayList<Color> getDeck() {
		return new ArrayList<Color>(deck);
	}

	public static void shuffle() {
		Collections.shuffle(deck);
	}

	public static int discardSize() {
		return discard.size();
	}

}