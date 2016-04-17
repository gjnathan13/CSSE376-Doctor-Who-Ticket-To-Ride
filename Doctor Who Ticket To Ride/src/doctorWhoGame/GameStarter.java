package doctorWhoGame;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

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
	private static Routeboard routeboard;
	private final static Color[] COLOR_ARRAY = { Color.GREEN, Color.RED, Color.BLUE, Color.MAGENTA, Color.YELLOW };
	protected static Player[] playerList;
	private static Scoreboard scoreboard;
	private static ArrayList<Path> paths;
	private static ArrayList<Node> nodes;
	private static ArrayList<RouteCard> routesTempList;
	private static ArrayDeque<RouteCard> routes;
	private final static int ORIGINAL_MONITOR_WIDTH = 1920;
	private final static int ORIGINAL_MONITOR_HEIGHT = 1080;
	private static RouteCardDeck routeDeck;

	/**
	 * Initializes game and sets up start screen GUI.
	 * 
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {

		final JFrame window = new JFrame();
		window.setTitle("Enter the player names");

		BufferedImage startBack = ImageIO.read(new File("GameImages\\TitleImage.png"));
		int startScreenWidth = (int) (startBack.getWidth() * getWidthModifier());
		int startScreenHeight = (int) (startBack.getHeight() * getHeightModifier());

		Image startBackResize = startBack.getScaledInstance(startScreenWidth, startScreenHeight, Image.SCALE_DEFAULT);

		BufferedImage startButtonImage = ImageIO.read(new File("GameImages\\StartButtonImage.png"));
		Image startButtonScaledImage = startButtonImage.getScaledInstance(
				(int) (startButtonImage.getWidth() * getWidthModifier()),
				(int) (startButtonImage.getHeight() * getHeightModifier()), Image.SCALE_DEFAULT);
		JLayeredPane startScreen = new JLayeredPane();
		startScreen.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
		window.add(startScreen);
		createStartLabel(startScreenWidth, startScreenHeight, startBackResize, startScreen);
		createQuestionButton(startScreen);
		createStartButton(window, startScreenWidth, startScreenHeight, startButtonImage, startButtonScaledImage,
				startScreen);
		window.setResizable(false);
		window.pack();
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}

	private static void createStartButton(final JFrame window, int startScreenWidth, int startScreenHeight,
			BufferedImage startButtonImage, Image startButtonScaledImage, JLayeredPane startScreen) {
		JButton startButton = addStartButton(startButtonImage, startButtonScaledImage, startScreen);
		startButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				window.getContentPane().removeAll();
				window.getContentPane().repaint();
				JPanel contentPanel = new JPanel();
				contentPanel.setBackground(Color.BLACK);

				final JTextField[] playerNames = new JTextField[5];

				Font nameFont = new Font("ISOCTEUR", Font.BOLD, (int) (24 * getHeightModifier()));
				for (int i = 0; i < 5; i++) {
					JTextField nameEntry = new JTextField(20);
					nameEntry.setForeground(Color.CYAN);
					nameEntry.setBackground(Color.BLACK);
					nameEntry.setBounds((int) (200 * getWidthModifier()),
							(int) ((25 * (i + 1) + 25 * i) * getHeightModifier()), (int) (300 * getWidthModifier()),
							(int) (40 * getHeightModifier()));
					nameEntry.setFont(nameFont);
					contentPanel.add(nameEntry);
					playerNames[i] = nameEntry;

					JLabel nameLabel = new JLabel("Player " + (i + 1));
					nameLabel.setFont(nameFont);
					nameLabel.setBounds((int) (20 * getWidthModifier()),
							(int) ((25 * (i + 1) + 25 * i) * getHeightModifier()), (int) (160 * getWidthModifier()),
							(int) (40 * getHeightModifier()));
					nameLabel.setForeground(Color.WHITE);
					contentPanel.add(nameLabel);
				}

				createColorDrawer(contentPanel);
				JButton startButton = formatStartButton(nameFont);
				contentPanel.add(startButton);
				formatWarningLabel(startScreenWidth, contentPanel, nameFont);
				contentPanel.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
				contentPanel.setBounds(0, 0, startScreenWidth, startScreenHeight);
				window.add(contentPanel);
				startButton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent arg0) {
						ArrayList<Player> players = new ArrayList<Player>();
						for (int i = 0; i < 5; i++) {
							String nameString = playerNames[i].getText().trim();
							if (nameString.length() > 0) {
								Player p = new Player(nameString, COLOR_ARRAY[i]);
								players.add(p);
							}
						}
						if (players.size() >= 2) {

							window.dispose();
							GameStarter.playerList = players.toArray(new Player[players.size()]);
							setUpGameboard();
						}
					}

				});
			}

			private void formatWarningLabel(int startScreenWidth, JPanel contentPanel, Font nameFont) {
				final JLabel warning = new JLabel("Enter at least 2 players");
				warning.setFont(nameFont);
				warning.setBounds(0, (int) (280 * getHeightModifier()), startScreenWidth,
						(int) (40 * getHeightModifier()));
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
				startButton.setBounds((int) (150 * getWidthModifier()), (int) (330 * getHeightModifier()),
						(int) (275 * getWidthModifier()), (int) (40 * getHeightModifier()));
				return startButton;
			}

			private void createColorDrawer(JPanel contentPanel) {
				JComponent colorDrawer = new JComponent() {

					@Override
					protected void paintComponent(Graphics g) {
						super.paintComponent(g);
						Graphics2D g2 = (Graphics2D) g;
						for (int i = 0; i < 5; i++) {
							g2.setColor(COLOR_ARRAY[i]);
							g2.fillOval((int) (10 * getWidthModifier()),
									(int) ((25 * (i + 1) + 25 * i) * getHeightModifier()),
									(int) (25 * getWidthModifier()), (int) (25 * getHeightModifier()));

						}
					}
				};
				colorDrawer.setBounds((int) (500 * getWidthModifier()), 0, (int) (40 * getWidthModifier()),
						(int) (500 * getHeightModifier()));
				contentPanel.add(colorDrawer);
			}
		});
	}

	private static JButton addStartButton(BufferedImage startButtonImage, Image startButtonScaledImage,
			JLayeredPane startScreen) {
		JButton startButton = new JButton(new ImageIcon(startButtonScaledImage));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setBounds((int) (125 * getWidthModifier()), (int) (250 * getHeightModifier()),
				(int) (startButtonImage.getWidth() * getWidthModifier()),
				(int) (startButtonImage.getHeight() * getHeightModifier()));
		startScreen.add(startButton);
		return startButton;
	}

	private static void createQuestionButton(JLayeredPane startScreen) {
		JButton questionButton = new JButton("?");
		questionButton.setBounds((int) (475 * getWidthModifier()), (int) (325 * getHeightModifier()),
				(int) (50 * getWidthModifier()), (int) (50 * getHeightModifier()));
		questionButton
				.setPreferredSize(new Dimension((int) (50 * getWidthModifier()), (int) (50 * getHeightModifier())));
		startScreen.add(questionButton);
		questionButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				openPDFInstructions();

			}

		});
		questionButton.setForeground(Color.CYAN);
		questionButton.setBackground(Color.BLACK);
	}

	private static void createStartLabel(int startScreenWidth, int startScreenHeight, Image startBackResize,
			JLayeredPane startScreen) {
		JLabel startLabel = new JLabel(new ImageIcon(startBackResize));
		startLabel.setPreferredSize(new Dimension(startScreenWidth, startScreenHeight));
		startLabel.setBounds(0, 0, startScreenWidth, startScreenHeight);
		startScreen.add(startLabel);
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
		layeredPane.setPreferredSize(new Dimension(gameboardImageWidth + (int) (400 * getWidthModifier()),
				gameboardImageHeight + routeboardImageHeight));

		gameboard.setPreferredSize(new Dimension(gameboardImageWidth, gameboardImageHeight));
		gameboard.setBounds(0, routeboardImageHeight, gameboardImageWidth, gameboardImageHeight);

		routeboard.setPreferredSize(new Dimension(routeboardImageWidth, routeboardImageHeight));
		routeboard.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

		int scoreboardWidth = (int) (400 * getWidthModifier());
		int scoreboardHeight = routeboardImageHeight + gameboardImageHeight;

		scoreboard.setPreferredSize(new Dimension(scoreboardWidth, scoreboardHeight));
		scoreboard.setBounds(routeboardImageWidth, 0, scoreboardWidth, scoreboardHeight);

		RouteChoosingComponent routeBuyingScreen = new RouteChoosingComponent();

		routeBuyingScreen.setPreferredSize(
				new Dimension(gameboardImageWidth + scoreboardWidth, gameboardImageHeight + routeboardImageHeight));
		routeBuyingScreen.setBounds(0, 0, gameboardImageWidth + scoreboardWidth,
				gameboardImageHeight + routeboardImageHeight);

		TurnShield blockScreen = new TurnShield();
		blockScreen.setPreferredSize(
				new Dimension(gameboardImageWidth + scoreboardWidth, gameboardImageHeight + routeboardImageHeight));
		blockScreen.setBounds(0, 0, gameboardImageWidth + scoreboardWidth,
				gameboardImageHeight + routeboardImageHeight);

		ScoreVisual scoreDots = new ScoreVisual();
		scoreDots.setPreferredSize(new Dimension(routeboardImageWidth, routeboardImageHeight));
		scoreDots.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

		EndGameComponent endGameComponent = new EndGameComponent();

		endGameComponent.setBounds(0, 0, gameboardImageWidth + scoreboardWidth,
				gameboardImageHeight + routeboardImageHeight);

		endGameComponent.setPreferredSize(
				new Dimension(gameboardImageWidth + scoreboardWidth, gameboardImageHeight + routeboardImageHeight));

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
		layeredPane.add(endGameComponent, new Integer(-3));

		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);

		// Creates the game with the list of players
		Game newGame = new Game(playerList, gameboard, scoreboard, routeboard, layeredPane, routeBuyingScreen,
				blockScreen, routeDeck, endGameComponent);
		PathSelectListener listen = new PathSelectListener(pComp, newGame);
		pComp.addMouseListener(listen);
		pComp.addMouseMotionListener(listen);

		Game.startRoutePurchasing();
	}

	/**
	 * 
	 * @param string
	 */
	private static boolean loadNodesPathsAndRoutesFromFile(String filePath) {

		// empty the arrays so we aren't redundant
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		routesTempList = new ArrayList<RouteCard>();
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
		getAllNodes(jsonNodes);

		// get all of the paths
		JSONArray jsonPaths = (JSONArray) wrapper.get("paths");
		getAllPaths(jsonPaths);

		// load the routes
		JSONArray jsonRouteCards = (JSONArray) wrapper.get("routes");
		getAllRoutes(jsonRouteCards);
		Collections.shuffle(routesTempList);
		for (int i = 0; i < routesTempList.size(); i++) {
			routes.push(routesTempList.get(i));
		}
		routeDeck = new RouteCardDeck(routes);
		routesTempList = null;
		return true;
	}

	private static void getAllRoutes(JSONArray jsonRouteCards) {
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
			routesTempList.add(new RouteCard(number, routeNodes[0], routeNodes[1], points));

		}
	}

	private static void getAllPaths(JSONArray jsonPaths) {
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
			jsonColor = jsonColor.toLowerCase();
			if (jsonColor.equals("rainbow")) {
				jsonColor = "gray";
			}
			Color color;
			try {
				Field field = Class.forName("java.awt.Color").getField(jsonColor);
				color = (Color) field.get(null);
			} catch (Exception e) {
				color = null;
				System.out.println("Error reading color from json");
			}

			// get length of the path
			int pathLength = (int) (long) jsonPath.get("length");

			// get shift
			int shift = (int) (long) jsonPath.get("shift");

			// add the path
			paths.add(new Path(pathNodes[0], pathNodes[1], color, pathLength, shift));
		}
	}

	private static void getAllNodes(JSONArray jsonNodes) {
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
			nodes.add(new Node(id, (int) (xPos * getWidthModifier()), (int) (yPos * getHeightModifier()), name, abbr,
					color));
		}
	}

	public static void openPDFInstructions() {
		try {
			if (Desktop.isDesktopSupported()) {
				File pdf = new File("otherFiles/Ticket to Ride Rules.pdf");
				Desktop.getDesktop().open(pdf);
			} else {
				// can't open it
				System.err.println("Cannot open PDF file");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static double getHeightModifier() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getHeight() / ORIGINAL_MONITOR_HEIGHT;
	}

	public static double getWidthModifier() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		return screenSize.getWidth() / ORIGINAL_MONITOR_WIDTH;
	}

	public static double getDiagonalModifier() {
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double newDiagonal = Math
				.sqrt(screenSize.getWidth() * screenSize.getWidth() + screenSize.getHeight() * screenSize.getHeight());
		double oldDiagonal = Math.sqrt(
				ORIGINAL_MONITOR_WIDTH * ORIGINAL_MONITOR_WIDTH + ORIGINAL_MONITOR_HEIGHT * ORIGINAL_MONITOR_HEIGHT);
		return newDiagonal / oldDiagonal;

	}
}