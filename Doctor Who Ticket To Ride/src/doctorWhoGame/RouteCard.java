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
	
	/**
	 * The constructor for the Route Card object.
	 * 
	 * @param i
	 */
	public RouteCard(int i) {
		this.routeNumber = i;
	}
	
	/**
	 * The constructor for the Route Card Object if you give it nodes
	 * 
	 * @param i
	 * @param n1
	 * @param n2
	 */
	public RouteCard(int i, Node n1, Node n2){
		this.routeNumber = i;
		this.nodes = new Node[]{n1, n2};
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

}
