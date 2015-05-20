package doctorWhoGame;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

//import org.json.simple.JSONObject;

/**
 * Sets up gameplay.
 * 
 * @author nathangj
 * 
 */
public class GameStarter {

	private static Gameboard gameboard;
	private static TrainDeck trainDeck = new TrainDeck();
	private static Routeboard routeboard;
	private final static Color[] COLOR_ARRAY = { Color.GREEN, Color.RED,
			Color.BLUE, Color.MAGENTA, Color.YELLOW };
	private final static PlayerColor[] PLAYER_COLOR_ARRAY = {
			PlayerColor.Green, PlayerColor.Red, PlayerColor.Blue,
			PlayerColor.Magenta, PlayerColor.Yellow };
	protected static Player[] playerList;
	private static Scoreboard scoreboard;
	private static ArrayList<Path> paths;
	private static ArrayList<Node> nodes;
	private static ArrayDeque<RouteCard> routes;

	/**
	 * Initializes game and sets up start screen GUI.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");

		BufferedImage startBack = ImageIO.read(new File(
				"GameImages\\TitleImage.png"));
		BufferedImage startButtonImage = ImageIO.read(new File(
				"GameImages\\StartButtonImage.png"));

		JLayeredPane startScreen = new JLayeredPane();
		startScreen.setPreferredSize(new Dimension(startBack.getWidth(),
				startBack.getHeight()));

		window.add(startScreen);

		JLabel startLabel = new JLabel(new ImageIcon(startBack));
		startLabel.setPreferredSize(new Dimension(startBack.getWidth(),
				startBack.getHeight()));
		startLabel.setBounds(0, 0, startBack.getWidth(), startBack.getHeight());
		startScreen.add(startLabel);

		JButton questionButton = new JButton("?");
		questionButton.setBounds(475, 325, 50, 50);
		startScreen.add(questionButton);
		questionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				// TODO Auto-generated method stub

			}

		});

		JButton startButton = new JButton(new ImageIcon(startButtonImage));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setBounds(125, 250, startButtonImage.getWidth(),
				startButtonImage.getHeight());
		startScreen.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				window.getContentPane().removeAll();
				window.getContentPane().repaint();
				JPanel contentPanel = new JPanel();
				contentPanel.setBackground(Color.BLACK);

				final JTextField[] playerNames = new JTextField[5];

				Font nameFont = new Font("ISOCTEUR", Font.BOLD, 24);
				for (int i = 0; i < 5; i++) {
					JTextField nameEntry = new JTextField(20);
					nameEntry.setForeground(Color.CYAN);
					nameEntry.setBackground(Color.BLACK);
					nameEntry.setBounds(200, 25 * (i + 1) + 25 * i, 300, 40);
					nameEntry.setFont(nameFont);
					contentPanel.add(nameEntry);
					playerNames[i] = nameEntry;

					JLabel nameLabel = new JLabel("Player " + (i + 1));
					nameLabel.setFont(nameFont);
					nameLabel.setBounds(20, 25 * (i + 1) + 25 * i, 160, 40);
					nameLabel.setForeground(Color.WHITE);
					contentPanel.add(nameLabel);
				}

				JComponent colorDrawer = new JComponent() {

					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						for (int i = 0; i < 5; i++) {
							g2.setColor(COLOR_ARRAY[i]);
							g2.fillOval(10, 25 * (i + 1) + 25 * i, 25, 25);
						}
					}
				};
				colorDrawer.setBounds(500, 0, 40, 500);
				contentPanel.add(colorDrawer);

				JButton startButton = new JButton("GERONIMO");
				startButton.setBorder(BorderFactory.createEmptyBorder());
				startButton.setForeground(Color.CYAN);
				startButton.setBackground(Color.BLACK);
				startButton.setFont(nameFont);
				startButton.setBounds(150, 330, 275, 40);
				contentPanel.add(startButton);

				final JLabel warning = new JLabel("Enter at least 2 players");
				warning.setFont(nameFont);
				warning.setBounds(0, 280, window.getWidth(), 40);
				warning.setForeground(Color.CYAN);
				warning.setHorizontalAlignment(SwingConstants.CENTER);
				contentPanel.add(warning);

				contentPanel.setPreferredSize(new Dimension(window.getWidth(),
						window.getHeight()));
				contentPanel.setBounds(0, 0, window.getWidth(),
						window.getHeight());
				window.add(contentPanel);

				startButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						ArrayList<Player> players = new ArrayList<Player>();
						for (int i = 0; i < 5; i++) {
							String nameString = playerNames[i].getText().trim();
							if (nameString.length() > 0) {
								Player p = new Player(nameString,
										PLAYER_COLOR_ARRAY[i]);
								players.add(p);
							}
						}
						if (players.size() >= 2) {

							window.dispose();
							GameStarter.playerList = players
									.toArray(new Player[players.size()]);
							setUpGameboard();
						}
					}

				}

				);
			}
		});
		window.setResizable(false);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	/**
	 * Sets up GUI for game play.
	 */
	private static void setUpGameboard() {

		// instantiate, clean, and fill nodes, paths, and routes
		loadNodesPathsAndRoutesFromFile("otherFiles\\NodesAndPaths.json");

		gameboard = new Gameboard();
		int[] gameboardImageDimensions = gameboard.getHandImageDimensions();
		final int gameboardImageWidth = gameboardImageDimensions[0];
		final int gameboardImageHeight = gameboardImageDimensions[1];

		Path[] pathArray = new Path[paths.size()];
		for (int i = 0; i < paths.size(); i++) {
			pathArray[i] = paths.get(i);
		}
		Node[] nodeArray = new Node[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			nodeArray[i] = nodes.get(i);
		}
		PathComponent pComp = new PathComponent(pathArray, nodeArray, gameboard);

		routeboard = new Routeboard(pComp);
		int[] routeImageDimensions = routeboard.getRouteImageDimensions();
		final int routeboardImageWidth = routeImageDimensions[0];
		final int routeboardImageHeight = routeImageDimensions[1];

		scoreboard = new Scoreboard(playerList);

		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(gameboardImageWidth + 400,
				gameboardImageHeight + routeboardImageHeight));

		gameboard.setPreferredSize(new Dimension(gameboardImageWidth,
				gameboardImageHeight));
		gameboard.setBounds(0, routeboardImageHeight, gameboardImageWidth,
				gameboardImageHeight);

		routeboard.setPreferredSize(new Dimension(routeboardImageWidth,
				routeboardImageHeight));
		routeboard.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

		scoreboard.setPreferredSize(new Dimension(400, routeboardImageHeight
				+ gameboardImageHeight));
		scoreboard.setBounds(routeboardImageWidth, 0, 400,
				routeboardImageHeight + gameboardImageHeight);

		RouteChoosingComponent routeBuyingScreen = new RouteChoosingComponent(
				routes);
		routeBuyingScreen.setPreferredSize(new Dimension(gameboardImageWidth
				+ scoreboard.getWidth(), gameboardImageHeight
				+ routeboardImageHeight));
		routeBuyingScreen.setBounds(0, 0,
				gameboardImageWidth + scoreboard.getWidth(),
				gameboardImageHeight + routeboardImageHeight);

		TurnShield blockScreen = new TurnShield();
		blockScreen.setPreferredSize(new Dimension(gameboardImageWidth
				+ scoreboard.getWidth(), gameboardImageHeight
				+ routeboardImageHeight));
		blockScreen.setBounds(0, 0,
				gameboardImageWidth + scoreboard.getWidth(),
				gameboardImageHeight + routeboardImageHeight);

		ScoreVisual scoreDots = new ScoreVisual();
		scoreDots.setPreferredSize(new Dimension(routeboardImageWidth,
				routeboardImageHeight));
		scoreDots.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

		JFrame gameWindow = new JFrame();
		gameWindow.setResizable(false);
		gameWindow.setTitle("Good Luck!");
		gameWindow.add(layeredPane);
		layeredPane.add(gameboard);
		layeredPane.add(routeboard);
		layeredPane.add(scoreboard);
		layeredPane.add(routeBuyingScreen, new Integer(-1));
		layeredPane.add(blockScreen, new Integer(-2));
		layeredPane.add(scoreDots, new Integer(1));

		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);

		// Creates the game with the list of players
		Game newGame = new Game(playerList, gameboard, scoreboard, routeboard,
				layeredPane, routeBuyingScreen, blockScreen);
		PathSelectListener listen = new PathSelectListener(pComp, newGame);
		pComp.addMouseListener(listen);
		pComp.addMouseMotionListener(listen);

		Game.startRoutePurchasing();
	}

	/**
	 * Draws card from Deck and puts it in the specified hand.
	 */
	private static void getNewCardForHand() {
		TrainColor drawnCard = TrainDeck.draw();
		Game.getCurrentPlayer().getHand().addTrainCard(drawnCard);
	}

	/**
	 * Inserts a RouteCard back into the bottom of the routes deck
	 */
	private static void reinsertRouteCard(RouteCard r) {
		routes.offer(r);
	}

	/**
	 * 
	 * @param string
	 */
	private static boolean loadNodesPathsAndRoutesFromFile(String filePath) {

		// empty the arrays so we aren't redundant
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		routes = new ArrayDeque<RouteCard>();

		String json = "";
		BufferedReader br;
		try {
			br = new BufferedReader(new FileReader(filePath));
			StringBuilder sb = new StringBuilder();
			String line = br.readLine();

			while (line != null) {
				sb.append(line);
				sb.append("\n");
				line = br.readLine();
			}

			br.close();
			json = sb.toString();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}

		if (!json.equals(""))
			return loadNodesPathsAndRoutesFromString(json);

		return false;
	}

	/**
	 * 
	 * @param string
	 */

	private static boolean loadNodesPathsAndRoutesFromString(String json) {

		// Make Parser and JSONobject
		JSONParser jsonParser = new JSONParser();

		JSONObject wrapper;
		try {
			wrapper = (JSONObject) jsonParser.parse(json);
		} catch (ParseException e) {
			System.err.println("Could not parse JSON");
			e.printStackTrace();
			return false;
		}

		// get all of the nodes
		JSONArray jsonNodes = (JSONArray) wrapper.get("nodes");
		for (int i = 0; i < jsonNodes.size(); i++) {
			// get the node
			JSONObject jsonNode = (JSONObject) jsonNodes.get(i);

			// get the node's id and name
			int id = (int) (long) jsonNode.get("id");
			String name = (String) ((Object) jsonNode.get("name"));

			// get the positions
			int xPos = (int) (long) jsonNode.get("x");
			int yPos = (int) (long) jsonNode.get("y");

			// get abbreviation
			String abbr = (String) (Object) jsonNode.get("abbr");

			// get color
			Color color = Color.decode((String) (Object) jsonNode.get("color"));

			// add the new node
			nodes.add(new Node(id, xPos, yPos, name, abbr, color));
		}

		// get all of the paths
		JSONArray jsonPaths = (JSONArray) wrapper.get("paths");
		for (int i = 0; i < jsonPaths.size(); i++) {
			// get this path
			JSONObject jsonPath = (JSONObject) jsonPaths.get(i);

			// grab the nodes
			Node[] pathNodes = new Node[2];
			JSONArray jsonPathNodes = (JSONArray) jsonPath.get("nodes");
			for (int ii = 0; ii < 2; ii++) {
				int id = (int) ((long) jsonPathNodes.get(ii));

				// find the node and set it
				for (Node n : nodes) {
					if (n.getID() == id) {
						pathNodes[ii] = n;
						break;
					}
				}
			}

			String jsonColor = (String) (Object) jsonPath.get("color");
			TrainColor color = TrainColor.valueOf(jsonColor);

			// get length of the path
			int pathLength = (int) (long) jsonPath.get("length");

			// get shift
			int shift = (int) (long) jsonPath.get("shift");

			// add the path
			paths.add(new Path(pathNodes[0], pathNodes[1], color, pathLength,
					shift));
		}

		// load the routes
		JSONArray jsonRouteCards = (JSONArray) wrapper.get("routes");
		for (int i = 0; i < jsonRouteCards.size(); i++) {
			// get a routeCard
			JSONObject jsonRouteCard = (JSONObject) jsonRouteCards.get(i);

			// get the number, points
			int number = (int) (long) jsonRouteCard.get("number");

			int points = (int) (long) jsonRouteCard.get("points");

			// get the nodes
			JSONArray jsonRouteNodes = (JSONArray) jsonRouteCard.get("nodes");

			// grab them
			Node[] routeNodes = new Node[2];
			for (int ii = 0; ii < 2; ii++) {
				int id = (int) ((long) jsonRouteNodes.get(ii));

				// find the node and set it
				for (Node n : nodes) {
					if (n.getID() == id) {
						routeNodes[ii] = n;
						break;
					}
				}
			}

			// assemble/add route
			routes.add(new RouteCard(number, routeNodes[0], routeNodes[1],
					points));

		}
		return true;
	}
}