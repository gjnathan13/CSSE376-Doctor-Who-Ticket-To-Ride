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

	public void purchasePath(ArrayList<TrainColor> removeList) {
		for(int i=0;i<removeList.size();i++){
			TrainColor currentCard=removeList.get(i);
			this.currentPlayer.getHand().removeTrainCard(currentCard);
			TrainDeck.discard(currentCard);
		}
		
	}
	
	public Player getCurrentPlayer(){
		return this.currentPlayer; 
	}
	
	public void switchToNextPlayer(){
		int currentPlayerIndex=this.playerList.indexOf(this.currentPlayer);
		if(currentPlayerIndex==this.playerList.size()-1){
			currentPlayerIndex=-1;
		}
		this.currentPlayer=this.playerList.get(currentPlayerIndex+1);
	}


	

}
