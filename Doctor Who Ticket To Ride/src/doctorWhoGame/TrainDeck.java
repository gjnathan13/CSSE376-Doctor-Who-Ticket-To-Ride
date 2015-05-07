package doctorWhoGame;

import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck {
	private static ArrayList<TrainColor> discard = new ArrayList<TrainColor>();

	private static ArrayList<TrainColor> deck = getNewDeck();

	private static ArrayList<TrainColor> getNewDeck() {
		ArrayList<TrainColor> newDeck = new ArrayList<TrainColor>();

		for (int i = 0; i < 12; i++) {
			newDeck.add(TrainColor.Pink);
			newDeck.add(TrainColor.White);
			newDeck.add(TrainColor.Blue);
			newDeck.add(TrainColor.Yellow);
			newDeck.add(TrainColor.Orange);
			newDeck.add(TrainColor.Black);
			newDeck.add(TrainColor.Red);
			newDeck.add(TrainColor.Green);
		}
		for (int i = 0; i < 14; i++)
			newDeck.add(TrainColor.Rainbow);

		Collections.shuffle(newDeck);
		

		return newDeck;
	}

	
	public static void refillDeck() {
		if(discard!=new ArrayList<TrainColor>()){
		for(int i=discard.size()-1;i>-1;i--){
			deck.add(discard.remove(i));
		}
		shuffle();
		}
	}

	
	public static void discard(TrainColor discarded) {
		discard.add(discarded);
	}

	public static int size() {
		return deck.size();
	}

	public static TrainColor draw() {
		if(size()==0 && discard.size()!=0){
			refillDeck();
		}
		if(size()!=0){
			return deck.remove(deck.size() - 1);
		}
		return null;
		//Old code
//		if (size() >= 1) {
//			return deck.remove(deck.size() - 1);
//		}
//		return null;
	}

	public static ArrayList<TrainColor> getDeck() {
		return new ArrayList<TrainColor>(deck);
	}

	public static void shuffle() {
		Collections.shuffle(deck);
	}


	public static int discardSize() {
		return discard.size();
	}

}