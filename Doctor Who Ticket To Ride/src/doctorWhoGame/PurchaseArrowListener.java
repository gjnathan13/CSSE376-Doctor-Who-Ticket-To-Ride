package doctorWhoGame;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class PurchaseArrowListener implements ActionListener {

	private PurchaseLabel labelControlling;
	private int upOrDown = 0;
	private int maxAllowed;
	private Gameboard gameBoard;

	PurchaseArrowListener(PurchaseLabel labelControlling, int upOrDown, int maxAllowed, Gameboard board) {
		this.labelControlling = labelControlling;
		this.upOrDown = upOrDown;
		this.maxAllowed = maxAllowed;
		this.gameBoard = board;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		int currentAmount = gameBoard.getPurchaseLabelAmounts().get(labelControlling.getLabelColor());
		if (upOrDown == 1 && currentAmount < maxAllowed) {
			currentAmount++;
		} else if (upOrDown == -1 && currentAmount > 0) {
			currentAmount--;
		}
		gameBoard.getPurchaseLabelAmounts().put(labelControlling.getLabelColor(), currentAmount);
		labelControlling.setText(Integer.toString(currentAmount));
		gameBoard.removeRevalidateRepaint();
	}

}