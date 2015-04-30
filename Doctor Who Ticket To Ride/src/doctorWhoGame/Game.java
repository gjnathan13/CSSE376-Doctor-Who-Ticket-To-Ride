package doctorWhoGame;

import java.util.ArrayList;

public class Game {
	
	private ArrayList<Player> playerList;
	private Player currentPlayer;
	private Gameboard gameboard;
	private Scoreboard scoreboard;
	private Routeboard routeboard; 

	public Game(Player[] givenPlayerList,Gameboard givenGameboard, Scoreboard givenScoreBoard, Routeboard givenRouteboard){
		ArrayList<Player> playerArrayList=new ArrayList<Player>();
		for(int i=0;i<givenPlayerList.length;i++){
			playerArrayList.add(givenPlayerList[i]);
		}
		this.playerList=playerArrayList;
		this.currentPlayer=this.playerList.get(0);
		this.gameboard=givenGameboard;
		this.scoreboard=givenScoreBoard;
		this.routeboard=givenRouteboard;
	}

	public void purchasePath(ArrayList<TrainColor> testList) {
		for(int i=0;i<testList.size();i++){
			this.currentPlayer.getHand().removeTrainCard(testList.get(i));
		}
		
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer; 
	}
	
	public void switchToNextPlayer(){
		int currentPlayerIndex=this.playerList.indexOf(this.currentPlayer);
		this.currentPlayer=this.playerList.get(currentPlayerIndex+1);
	}


	

}
