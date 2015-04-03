package doctorWhoGame;

import java.util.ArrayList;
import java.util.Collections;

public class TrainDeck{

	private ArrayList<String> deck; 
	
	public TrainDeck(){
		deck = new ArrayList<String>();
		
		deck.add("Yellow");
		deck.add("Red");
		deck.add("Blue");
		deck.add("Green");
		deck.add("Black");
		deck.add("Rainbow");
	}
	
	public int size() {
		return 96;
	}
	
	public String draw() {
		return "Red";
	}
	
	public ArrayList<String> getDeck() {
		return new ArrayList<String>(deck);
	}

	public void shuffle() {
		Collections.shuffle(deck);
	}

}
