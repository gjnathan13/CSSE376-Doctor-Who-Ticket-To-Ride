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
		this.pen.fillRect(0, 0, (int)(this.getWidth()*GameStarter.getWidthModifier()), (int)(GameStarter.getHeightModifier() *this.getHeight()));

		JLabel beginTurnInstruction = new JLabel(
				"Please pass computer to next player: " + Game.getCurrentPlayer().getName(), JLabel.CENTER);
		beginTurnInstruction.setFont(new Font("ISOCTEUR", Font.PLAIN, (int)(GameStarter.getDiagonalModifier()*20)));
		beginTurnInstruction.setForeground(Color.CYAN);
		beginTurnInstruction.setBounds((int)(GameStarter.getWidthModifier() * (this.getWidth() / 2 - 400)),
				(int)(GameStarter.getHeightModifier() * (this.getHeight() / 2 - 250)), (int)(GameStarter.getWidthModifier() * 800), 
				(int)(GameStarter.getHeightModifier() * 400));
		this.add(beginTurnInstruction);

		JButton beginTurn = new JButton("Allons-y");
		beginTurn.setBounds((int)(GameStarter.getWidthModifier() * (this.getWidth() / 2 - 50)),
				(int) (GameStarter.getHeightModifier() * (this.getHeight() / 2)), (int)(GameStarter.getWidthModifier() *100),
				(int) (GameStarter.getHeightModifier() *25));
		this.add(beginTurn);

		beginTurn.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.blockScreen(false);
			}
		});

	}
}
