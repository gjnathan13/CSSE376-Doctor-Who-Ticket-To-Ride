package doctorWhoGame;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.FileReader;
import java.lang.reflect.Field;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class GameJSONParser {

	private static ArrayList<Path> paths;
	private static ArrayList<Node> nodes;
	private static ArrayList<RouteCard> routesTempList;
	private static ArrayDeque<RouteCard> routes;

	/**
	 * 
	 * @param string
	 */
	public static boolean loadNodesPathsAndRoutesFromFile(String filePath) {

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
			for (int j = 0; j < 2; j++) {
				int id = (int) ((long) jsonRouteNodes.get(j));

				// find the node and set it
				for (Node n : nodes) {
					if (n.getID() == id) {
						routeNodes[j] = n;
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
			for (int j = 0; j < 2; j++) {
				int id = (int) ((long) jsonPathNodes.get(j));

				// find the node and set it
				for (Node n : nodes) {
					if (n.getID() == id) {
						pathNodes[j] = n;
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
			nodes.add(new Node(id, (int) (xPos * GameStarter.getWidthModifier()),
					(int) (yPos * GameStarter.getHeightModifier()), name, abbr, color));
		}
	}

	public static Path[] getPathArray() {
		Path[] pathArray = new Path[paths.size()];
		for (int i = 0; i < paths.size(); i++) {
			pathArray[i] = paths.get(i);
		}
		return pathArray;
	}

	public static Node[] getNodeArray() {
		Node[] nodeArray = new Node[nodes.size()];
		for (int i = 0; i < nodes.size(); i++) {
			nodeArray[i] = nodes.get(i);
		}
		return nodeArray;
	}

	public static RouteCardDeck getRoutes() {
		return new RouteCardDeck(routes);
		
	}
}
