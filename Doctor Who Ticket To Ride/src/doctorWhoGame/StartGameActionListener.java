package doctorWhoGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class StartGameActionListener implements ActionListener {

	private JFrame window;
	private int startScreenWidth;
	private int startScreenHeight;

	protected static Player[] playerList;

	public StartGameActionListener(JFrame window, int startScreenWidth, int startScreenHeight) {
		this.window = window;
		this.startScreenWidth = startScreenWidth;
		this.startScreenHeight = startScreenHeight;
	}

	@Override
	public void actionPerformed(ActionEvent arg0) {
		window.getContentPane().removeAll();
		window.getContentPane().repaint();
		JPanel contentPanel = new JPanel();
		contentPanel.setBackground(Color.BLACK);
		contentPanel.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
		contentPanel.setBounds(0, 0, startScreenWidth, startScreenHeight);
		window.add(contentPanel);

		createColorDrawer(contentPanel);

		final JTextField[] playerNames = new JTextField[5];
		Font nameFont = new Font("ISOCTEUR", Font.BOLD, (int) (24 * GameStarter.getHeightModifier()));

		createPlayerNameFields(contentPanel, playerNames, nameFont);
		formatWarningLabel(startScreenWidth, contentPanel, nameFont);

		JButton startButton = formatStartButton(nameFont);
		contentPanel.add(startButton);
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				ArrayList<Player> players = new ArrayList<Player>();
				for (int i = 0; i < 5; i++) {
					String nameString = playerNames[i].getText().trim();
					if (nameString.length() > 0) {
						Player p = new Player(nameString, GameStarter.getColorArray()[i]);
						players.add(p);
					}
				}
				if (players.size() >= 2) {

					window.dispose();
					playerList = players.toArray(new Player[players.size()]);
					GameGUIInitializer.setUpGameboard(playerList);
				}
			}

		});
	}

	private void createPlayerNameFields(JPanel contentPanel, final JTextField[] playerNames, Font nameFont) {
		for (int i = 0; i < 5; i++) {
			JTextField nameEntry = new JTextField(20);
			nameEntry.setForeground(Color.CYAN);
			nameEntry.setBackground(Color.BLACK);
			nameEntry.setBounds((int) (200 * GameStarter.getWidthModifier()),
					(int) ((25 * (i + 1) + 25 * i) * GameStarter.getHeightModifier()),
					(int) (300 * GameStarter.getWidthModifier()), (int) (40 * GameStarter.getHeightModifier()));
			nameEntry.setFont(nameFont);
			contentPanel.add(nameEntry);
			playerNames[i] = nameEntry;

			JLabel nameLabel = new JLabel("Player " + (i + 1));
			nameLabel.setFont(nameFont);
			nameLabel.setBounds((int) (20 * GameStarter.getWidthModifier()),
					(int) ((25 * (i + 1) + 25 * i) * GameStarter.getHeightModifier()),
					(int) (160 * GameStarter.getWidthModifier()), (int) (40 * GameStarter.getHeightModifier()));
			nameLabel.setForeground(Color.WHITE);
			contentPanel.add(nameLabel);
		}
	}

	private void formatWarningLabel(int startScreenWidth, JPanel contentPanel, Font nameFont) {
		final JLabel warning = new JLabel("Enter at least 2 players");
		warning.setFont(nameFont);
		warning.setBounds(0, (int) (280 * GameStarter.getHeightModifier()), startScreenWidth,
				(int) (40 * GameStarter.getHeightModifier()));
		warning.setForeground(Color.CYAN);
		warning.setHorizontalAlignment(SwingConstants.CENTER);
		contentPanel.add(warning);
	}

	private JButton formatStartButton(Font nameFont) {
		JButton startButton = new JButton("GERONIMO");
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setForeground(Color.CYAN);
		startButton.setBackground(Color.BLACK);
		startButton.setFont(nameFont);
		startButton.setBounds((int) (150 * GameStarter.getWidthModifier()),
				(int) (330 * GameStarter.getHeightModifier()), (int) (275 * GameStarter.getWidthModifier()),
				(int) (40 * GameStarter.getHeightModifier()));
		return startButton;
	}

	private void createColorDrawer(JPanel contentPanel) {
		@SuppressWarnings("serial")
		JComponent colorDrawer = new JComponent() {

			@Override
			protected void paintComponent(Graphics g) {
				super.paintComponent(g);
				Graphics2D g2 = (Graphics2D) g;
				for (int i = 0; i < 5; i++) {
					g2.setColor(GameStarter.getColorArray()[i]);
					g2.fillOval((int) (10 * GameStarter.getWidthModifier()),
							(int) ((25 * (i + 1) + 25 * i) * GameStarter.getHeightModifier()),
							(int) (25 * GameStarter.getWidthModifier()), (int) (25 * GameStarter.getHeightModifier()));

				}
			}
		};
		colorDrawer.setBounds((int) (500 * GameStarter.getWidthModifier()), 0,
				(int) (40 * GameStarter.getWidthModifier()), (int) (500 * GameStarter.getHeightModifier()));
		contentPanel.add(colorDrawer);
	}
}
