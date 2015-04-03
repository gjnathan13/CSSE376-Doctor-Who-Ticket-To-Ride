package doctorWhoGame;

import java.util.LinkedList;
import java.util.Queue;

public class TrainDeck{

	public int size() {
		return 96;
	}
	
	public String draw() {
		return "Red";
	}

	Queue<String> deck = new LinkedList<String>(); 
	
	public Queue<String> getDeck() {
		return new LinkedList<String>();
	}

	public void shuffle() {
		
	}

}
