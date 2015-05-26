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

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result
				+ ((destination == null) ? 0 : destination.hashCode());
		result = prime * result + ((origin == null) ? 0 : origin.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasicRoute other = (BasicRoute) obj;
		if (destination == null) {
			if (other.destination != null)
				return false;
		} else if (!destination.equals(other.destination))
			return false;
		if (origin == null) {
			if (other.origin != null)
				return false;
		} else if (!origin.equals(other.origin))
			return false;
		return true;
	}

	public String toString(){
		return origin + " ---> " + destination;
	}
}
