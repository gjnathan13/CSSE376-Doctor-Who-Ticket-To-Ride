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

	/**
	 * The constructor for the Route Card object.
	 * 
	 * @param i
	 */
	public RouteCard(int i) {
		this.routeNumber = i;
	}
	
	/**
	 * Get this route's routNumber
	 */
	public int getRouteNumber(){
		return this.routeNumber;
	}
			

}
