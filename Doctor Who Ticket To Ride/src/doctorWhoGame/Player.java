package doctorWhoGame;

import java.util.ArrayList;

public class Player {

	private PlayerColor color;
	private String name;
	private int score;
	private int trainCount;
	private Hand hand;

	public Player(String playerName, PlayerColor playerColor){
		this.color=playerColor;
		this.name=playerName;
		this.score=20;
		//The player always starts with 45 trains
		this.trainCount=45;
		this.hand=new Hand();
	}
	
	public int getScore(){
		return this.score;
	}
	
	public String getName(){
		return this.name;
	}
	
	public int getTrainCount(){
		return this.trainCount;
	}
	
	public PlayerColor getColor(){
		return this.color;
	}

	public void addPoints(int points) {
		this.score=this.score+points;
		
	}

	public void removeTrainsFromPlayer(int numberOfTrainsToRemove) {
		this.trainCount=this.trainCount-numberOfTrainsToRemove;
		
	}
	
	public Hand getHand(){
		return this.hand;
	}
	
	public void addPath(Path givenPath){
		this.hand.addPath(givenPath);
	}

}
