package kps.data.wrappers;

/**
 * The class is a wrapper for the origin and destination locations
 * for a Route.
 *
 * @author David
 *
 */
public class BasicRoute {

	// fields
	private String origin;
	private String destination;

	/**
	 * Constructs a new BasicRoute from the specified origin
	 * and destination.
	 *
	 * @param origin
	 * 		-- origin of mail
	 * @param destination
	 * 		-- destination of mail
	 */
	public BasicRoute(String origin, String destination){
		this.origin = origin;
		this.destination = destination;
	}

	/**
	 * Returns the origin location associated with this
	 * BasicRoute.
	 *
	 * @return origin
	 */
	public String getOrigin(){
		return origin;
	}

	/**
	 * Returns the destination location associated with
	 * this BasicRoute.
	 *
	 * @return destination
	 */
	public String getDestination(){
		return destination;
	}
}
