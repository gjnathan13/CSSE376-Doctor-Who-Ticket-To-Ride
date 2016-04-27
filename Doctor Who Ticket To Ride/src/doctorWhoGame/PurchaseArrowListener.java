package doctorWhoGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseArrowListener implements ActionListener {

	private PurchaseLabel affectedLabel;
	private int upOrDown = 0;
	private int maxAllowed;
	private Gameboard gameBoard;

	PurchaseArrowListener(PurchaseLabel labelControlling, int upOrDown, int maxAllowed, Gameboard board) {
		this.affectedLabel = labelControlling;
		this.upOrDown = upOrDown;
		this.maxAllowed = maxAllowed;
		this.gameBoard = board;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int currentAmount = gameBoard.getPurchaseLabelAmounts().get(affectedLabel.getLabelColor());
		if (upOrDown == 1 && currentAmount < maxAllowed) {
			currentAmount++;
		} else if (upOrDown == -1 && currentAmount > 0) {
			currentAmount--;
		}
		gameBoard.getPurchaseLabelAmounts().put(affectedLabel.getLabelColor(), currentAmount);
		affectedLabel.setText(Integer.toString(currentAmount));
		gameBoard.removeRevalidateRepaint();
	}

}