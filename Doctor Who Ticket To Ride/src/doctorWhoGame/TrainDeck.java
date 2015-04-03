package doctorWhoGame;

import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck{

	private static ArrayList<String> deck; 
	
	public TrainDeck(){
		deck = new ArrayList<String>();
		
		for (int i = 0; i < 12; i++){
			deck.add("Pink");
			deck.add("White");
			deck.add("Blue");
			deck.add("Yellow");
			deck.add("Orange");
			deck.add("Black");
			deck.add("Red");
			deck.add("green");
		}
		for (int i = 0; i < 14; i++)
			deck.add("Rainbow");
	}
	
	public static int size() {
		return deck.size();
	}
	
	public static String draw() {
		return deck.remove(deck.size()-1);
	}
	
	public ArrayList<String> getDeck() {
		return new ArrayList<String>(deck);
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

}