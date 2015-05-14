package doctorWhoGame;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;

public class ScoreVisual extends JComponent {
	
	private Graphics2D pen;

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		this.pen = (Graphics2D) g;
		this.pen.setColor(Color.GREEN);
		this.pen.fillRect(0, 0, 500,
				500);
		//get player list and scores and display
	

	}

}
