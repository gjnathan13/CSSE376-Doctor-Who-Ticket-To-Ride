package doctorWhoGame;

/**
 * The Route Card object that is a route the player must complete. It has a
 * point value, start planet, and end planet associated with it.
 * 
 * @author wrightsd and whitehts
 *
 */
public class RouteCard {
	
	private int routeNumber;

	private Node[] nodes;
	
	private int points;
	
	/**
	 * The constructor for the Route Card object.
	 * 
	 * @param i the route number
	 */
	public RouteCard(int i) {
		this(i, null, null, 0);
	}
	
	/**
	 * The constructor for the Route Card Object if you give it nodes
	 * 
	 * @param i the route number
	 * @param n1 one of the nodes
	 * @param n2 one of the nodes
	 */
	public RouteCard(int i, Node n1, Node n2){
		this(i, n1, n2, 0);
	}
	
	/**
	 * The constructor for the Route Card Object if you give it nodes and a point value
	 * 
	 * @param i the route number
	 * @param n1 one of the nodes
	 * @param n2 one of the nodes
	 * @param p the number of points the route has
	 */
	public RouteCard(int i, Node n1, Node n2, int p){
		this.routeNumber = i;
		this.nodes = new Node[]{n1, n2};
		this.points = p;
	}
	
	/**
	 * Get this route's routNumber
	 */
	public int getRouteNumber(){
		return this.routeNumber;
	}
	
	/**
	 *  Get this route's nodes
	 */
	public Node[] getNodes(){
		return this.nodes;
	}
	
	/**
	 * Gets this route's point value
	 */
	public int getPoints(){
		return this.points;
	}

}
