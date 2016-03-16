package doctorWhoGame;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class TurnShield extends JComponent {

	private Graphics2D pen;

	public TurnShield() {
		this.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
			}

		});
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.removeAll();
		this.pen = (Graphics2D) g;
		this.pen.setColor(Color.BLACK);
		this.pen.fillRect(0, 0, this.getWidth(), this.getHeight());

		JLabel beginTurnInstruction = new JLabel(
				"Please pass computer to next player: " + Game.getCurrentPlayer().getName(), JLabel.CENTER);
		beginTurnInstruction.setFont(new Font("ISOCTEUR", Font.PLAIN, 20));
		beginTurnInstruction.setForeground(Color.CYAN);
		beginTurnInstruction.setBounds(this.getWidth() / 2 - 400, this.getHeight() / 2 - 250, 800, 400);
		this.add(beginTurnInstruction);

		JButton beginTurn = new JButton("Allons-y");
		beginTurn.setBounds(this.getWidth() / 2 - 50, this.getHeight() / 2, 100, 25);
		this.add(beginTurn);

		beginTurn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.blockScreen(false);
			}
		});

	}
}
