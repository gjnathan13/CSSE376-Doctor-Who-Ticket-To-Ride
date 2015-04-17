package doctorWhoGame;

import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck{

	private static ArrayList<String> deck = getNewDeck(); 
	
	private static ArrayList<String> getNewDeck(){
		ArrayList<String> newDeck = new ArrayList<String>();
		
		for (int i = 0; i < 12; i++){
			newDeck.add("Pink");
			newDeck.add("White");
			newDeck.add("Blue");
			newDeck.add("Yellow");
			newDeck.add("Orange");
			newDeck.add("Black");
			newDeck.add("Red");
			newDeck.add("Green");
		}
		for (int i = 0; i < 14; i++)
			newDeck.add("Rainbow");
		
		Collections.shuffle(newDeck);
		
		return newDeck;
	}
	
	public static void refillDeck(){
		deck = getNewDeck();
	}
	
	public static int size() {
		return deck.size();
	}
	
	public static String draw() {
		if(size() >= 1){
		return deck.remove(deck.size()-1);
		}
		return null;
	}
	
	public static ArrayList<String> getDeck() {
		return new ArrayList<String>(deck);
	}

	public static void shuffle() {
		Collections.shuffle(deck);
	}

}