package doctorWhoGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

public class PurchasePathButtonListener implements ActionListener{
	
	private Player currentPlayer;
	private Hand currentHand;
	private Game currentGame;
	private Gameboard gameboard;

	public PurchasePathButtonListener(Game givenGame,Gameboard hostGameboard){
		this.currentGame=givenGame;
		this.currentPlayer=this.currentGame.getCurrentPlayer();
		this.currentHand=this.currentPlayer.getHand();
		this.gameboard=hostGameboard;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		//Ideas for what this should do later. Not tested
		//ArrayList<TrainColor> listOfSelectedCards=new ArrayList<TrainColor>();
		//this.currentGame.purchasePath(listOfSelectedCards);
		
	}

}
