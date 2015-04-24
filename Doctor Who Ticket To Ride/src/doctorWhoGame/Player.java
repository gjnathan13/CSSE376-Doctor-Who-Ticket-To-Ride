package doctorWhoGame;

public class Player {

	private PlayerColor color;
	private String name;
	private int score;
	private int trainCount;

	public Player(String playerName, PlayerColor playerColor){
		this.color=playerColor;
		this.name=playerName;
		this.score=0;
		//The player always starts with 45 trains
		this.trainCount=45;
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
	

}
