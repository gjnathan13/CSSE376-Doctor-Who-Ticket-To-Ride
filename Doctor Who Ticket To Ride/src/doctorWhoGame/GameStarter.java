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
import java.util.ArrayList;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JTextField;

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

	private static Hand currentHand;
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

		JButton startButton = new JButton(new ImageIcon(startButtonImage));
		startButton.setBorder(BorderFactory.createEmptyBorder());
		startButton.setBounds(125, 250, startButtonImage.getWidth(),
				startButtonImage.getHeight());
		startScreen.add(startButton);
		startButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				window.getContentPane().removeAll();
				window.getContentPane().repaint();
				window.getContentPane().setBackground(Color.BLACK);

				final JTextField[] playerNames = new JTextField[5];

				Font nameFont = new Font("ISOCTEUR", Font.BOLD, 24);
				for (int i = 0; i < 5; i++) {
					JTextField nameEntry = new JTextField(20);
					nameEntry.setForeground(Color.CYAN);
					nameEntry.setBackground(Color.BLACK);
					nameEntry.setBounds(200, 25 * (i + 1) + 25 * i, 300, 40);
					nameEntry.setFont(nameFont);
					window.add(nameEntry);
					playerNames[i] = nameEntry;

					JLabel nameLabel = new JLabel("Player " + (i + 1));
					nameLabel.setFont(nameFont);
					nameLabel.setBounds(20, 25 * (i + 1) + 25 * i, 160, 40);
					nameLabel.setForeground(Color.WHITE);
					window.add(nameLabel);
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
				window.add(colorDrawer);

				JButton startButton = new JButton("GERONIMO");
				startButton.setBorder(BorderFactory.createEmptyBorder());
				startButton.setForeground(Color.CYAN);
				startButton.setBackground(Color.BLACK);
				startButton.setFont(nameFont);
				startButton.setBounds(150, 310, 275, 40);
				window.add(startButton);
				startButton.addActionListener(new ActionListener() {

					@Override
					public void actionPerformed(ActionEvent arg0) {
						window.dispose();
						ArrayList<Player> players = new ArrayList<Player>();
						for (int i = 0; i < 5; i++) {
							String nameString = playerNames[i].getText().trim();
							if (nameString.length() > 0) {
								Player p = new Player(nameString,
										PLAYER_COLOR_ARRAY[i]);
								players.add(p);
							}
						}
						GameStarter.playerList = players
								.toArray(new Player[players.size()]);
						setUpGameboard();
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
		gameboard = new Gameboard();
		int[] gameboardImageDimensions = gameboard.getHandImageDimensions();
		final int gameboardImageWidth = gameboardImageDimensions[0];
		final int gameboardImageHeight = gameboardImageDimensions[1];

		routeboard = new Routeboard();
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
		routeboard.setBounds(0, -5, routeboardImageWidth,
				routeboardImageHeight + 5);

		scoreboard.setPreferredSize(new Dimension(400, routeboardImageHeight
				+ gameboardImageHeight));
		scoreboard.setBounds(routeboardImageWidth, 0, 400,
				routeboardImageHeight + gameboardImageHeight);

		JFrame gameWindow = new JFrame();
		gameWindow.setResizable(false);
		gameWindow.setTitle("Good Luck!");
		gameWindow.add(layeredPane);
		layeredPane.add(gameboard);
		layeredPane.add(routeboard);
		layeredPane.add(scoreboard);
		JButton drawButton = new JButton("Draw a card");
		drawButton.setBounds(gameboardImageWidth - 150, routeboardImageHeight,
				150, 20);
		layeredPane.add(drawButton);
		currentHand = new Hand();
		gameboard.setHand(currentHand);
		drawButton.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent arg0) {
				getNewCardForHand();
				gameboard.repaint();
			}
		});

		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);
		
		
		
		// load in the nodes and paths
		nodes = new ArrayList<Node>();
		paths = new ArrayList<Path>();
		
		loadNodesAndPathsFromFile("otherFiles\\NodesAndPaths.json");
		
		//Creates the game with the list of players
		Game newGame=new Game(playerList,gameboard,scoreboard,routeboard);
	}

	/**
	 * Draws card from Deck and puts it in the specified hand.
	 */
	private static void getNewCardForHand() {
		TrainColor drawnCard = TrainDeck.draw();
		currentHand.addTrainCard(drawnCard);
	}
	

	/**
	 * 
	 * @param string
	 */
	private static boolean loadNodesAndPathsFromFile(String filePath) {
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
	    
	    if (!json.equals("")) return loadNodesAndPathsFromString(json);
	    
	    return false;
	}
	
	/**
	 * 
	 * @param string
	 */
	private static boolean loadNodesAndPathsFromString(String json) {
		
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
		for (int i = 0; i < jsonNodes.size(); i++){
			// get the node
			JSONObject jsonNode = (JSONObject) jsonNodes.get(i);
			
			// get the node's id and name
			long l = (long) jsonNode.get("id");
			int id = (int) l;
			String name = (String) ((Object) jsonNode.get("name"));
			
			// add the new node
			nodes.add(new Node(id, name));
		}
		
		
		// get all of the paths
		JSONArray jsonPaths = (JSONArray) wrapper.get("paths");
		for (int i = 0; i < jsonPaths.size(); i++){
			// get this path
			JSONObject jsonPath = (JSONObject) jsonPaths.get(i);
			
			// grab the nodes
			Node[] pathNodes = new Node[2];
			JSONArray jsonPathNodes = (JSONArray) jsonPath.get("nodes");
			for (int ii = 0; ii < 2; ii++){
				int id = (int) ((long) jsonPathNodes.get(ii));
				
				// find the node and set it
				for (Node n : nodes){
					if (n.getID() == id){
						pathNodes[ii] = n;
						break;
					}
				}
			}
			
			// get length of the path
			int pathLength = (int)(long) jsonPath.get("length");
			
			// add the path
			paths.add(new Path(pathNodes[0], pathNodes[1], pathLength));
		}
		
		return true;
	}
}