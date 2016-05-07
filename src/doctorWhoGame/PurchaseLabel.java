package doctorWhoGame;

import java.awt.Color;

import javax.swing.JLabel;

public class PurchaseLabel extends JLabel {

	private Color trainColor;

	public PurchaseLabel(Color t, Gameboard  board) {
		this.trainColor = t;
		this.setText(Integer.toString(board.getPurchaseLabelAmounts().get(this.trainColor)));
		this.setHorizontalAlignment(JLabel.CENTER);
		this.setFont(board.getPurchaseFont());
	}

	public Color getLabelColor() {
		return this.trainColor;
	}
}