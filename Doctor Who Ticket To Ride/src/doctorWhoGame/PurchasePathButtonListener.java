package doctorWhoGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.JLabel;

public class PurchasePathButtonListener implements ActionListener{
	
	private Player currentPlayer;
	private Hand currentHand;
	private ArrayList<JLabel> labelList;

	public PurchasePathButtonListener(Player givenPlayer,ArrayList<JLabel> givenLabelList){
		this.currentPlayer=givenPlayer;
		this.currentHand=this.currentPlayer.getHand();
		this.labelList=givenLabelList;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		// TODO Auto-generated method stub
		
	}

}
