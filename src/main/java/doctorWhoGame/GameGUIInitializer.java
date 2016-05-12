package doctorWhoGame;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JLayeredPane;

public class GameGUIInitializer {

	public static void setUpGameboard(Player[] playerList) {
		// instantiate, clean, and fill nodes, paths, and routes
		GameJSONParser.loadNodesPathsAndRoutesFromFile("otherFiles\\NodesAndPaths.json");

		Gameboard gameboard = new Gameboard();
		int[] gameboardImageDimensions = gameboard.getHandImageDimensions();
		final int gameboardImageWidth = gameboardImageDimensions[0];
		final int gameboardImageHeight = gameboardImageDimensions[1];

		Path[] pathArray = GameJSONParser.getPathArray();
		Node[] nodeArray = GameJSONParser.getNodeArray();
		RouteCardDeck routeDeck = GameJSONParser.getRoutes();

		PathComponent pathComponent = new PathComponent(pathArray, nodeArray, gameboard);

		Routeboard routeboard = new Routeboard(pathComponent);
		int[] routeImageDimensions = routeboard.getRouteImageDimensions();
		final int routeboardImageWidth = routeImageDimensions[0];
		final int routeboardImageHeight = routeImageDimensions[1];

		Scoreboard scoreboard = new Scoreboard(playerList);

		final JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setPreferredSize(new Dimension(gameboardImageWidth + (int) (400 * GameStarter.getWidthModifier()),
				gameboardImageHeight + routeboardImageHeight));

		gameboard.setPreferredSize(new Dimension(gameboardImageWidth, gameboardImageHeight));
		gameboard.setBounds(0, routeboardImageHeight, gameboardImageWidth, gameboardImageHeight);

		routeboard.setPreferredSize(new Dimension(routeboardImageWidth, routeboardImageHeight));
		routeboard.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

		int scoreboardWidth = (int) (400 * GameStarter.getWidthModifier());
		int scoreboardHeight = routeboardImageHeight + gameboardImageHeight;

		scoreboard.setPreferredSize(new Dimension(scoreboardWidth, scoreboardHeight));
		scoreboard.setBounds(routeboardImageWidth, 0, scoreboardWidth, scoreboardHeight);

		RouteChoosingComponent routeBuyingScreen = new RouteChoosingComponent();

		routeBuyingScreen.setPreferredSize(
				new Dimension(gameboardImageWidth + scoreboardWidth, gameboardImageHeight + routeboardImageHeight));
		routeBuyingScreen.setBounds(0, 0, gameboardImageWidth + scoreboardWidth,
				gameboardImageHeight + routeboardImageHeight);

		TurnShield turnShieldScreen = new TurnShield();
		turnShieldScreen.setPreferredSize(
				new Dimension(gameboardImageWidth + scoreboardWidth, gameboardImageHeight + routeboardImageHeight));
		turnShieldScreen.setBounds(0, 0, gameboardImageWidth + scoreboardWidth,
				gameboardImageHeight + routeboardImageHeight);

		ScoreVisual scoreVisualization = new ScoreVisual();
		scoreVisualization.setPreferredSize(new Dimension(routeboardImageWidth, routeboardImageHeight));
		scoreVisualization.setBounds(0, 0, routeboardImageWidth, routeboardImageHeight);

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
		layeredPane.add(turnShieldScreen, new Integer(-2));
		layeredPane.add(scoreVisualization, new Integer(1));
		layeredPane.add(endGameComponent, new Integer(-3));

		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);

		// Creates the game with the list of players
		Game newGame = new Game(playerList, gameboard, scoreboard, routeboard, layeredPane, routeBuyingScreen,
				turnShieldScreen, routeDeck, endGameComponent);
		PathSelectListener pathSelectListener = new PathSelectListener(pathComponent, newGame);
		pathComponent.addMouseListener(pathSelectListener);
		pathComponent.addMouseMotionListener(pathSelectListener);

		Game.startRoutePurchasing();
	}

}
