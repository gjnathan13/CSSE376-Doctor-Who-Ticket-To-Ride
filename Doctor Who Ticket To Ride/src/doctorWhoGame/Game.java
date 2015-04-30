package doctorWhoGame;

import java.util.ArrayList;

public class Game {
	
	private Player[] playerList;
	private Player currentPlayer;
	private Gameboard gameboard;
	private Scoreboard scoreboard;
	private Routeboard routeboard; 

	public Game(Player[] givenPlayerList,Gameboard givenGameboard, Scoreboard givenScoreBoard, Routeboard givenRouteboard){
		this.playerList=givenPlayerList;
		this.currentPlayer=playerList[0];
		this.gameboard=givenGameboard;
		this.scoreboard=givenScoreBoard;
		this.routeboard=givenRouteboard;
	}

	public void purchasePath(ArrayList<TrainColor> testList) {
		
		
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer; 
	}

	

}
