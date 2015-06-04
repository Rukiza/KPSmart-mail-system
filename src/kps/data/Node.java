package kps.data;

import java.util.HashSet;
import java.util.Set;
/**
 * @author Nicky van Hulst 300294657
 * */
public class Node  implements Comparable<Node>{

	//used for searching
	private Node prev;

	//cost used for searching
	private double minCost = Double.POSITIVE_INFINITY;
	private Route routeTaken = null;

	//name of the node
	private String name;

	//Neighbors of  the node
	Set<Route> edgesOut;
	Set<Route> edgesIn;


	/**
	 * Constructor for the node object
	 *
	 * @param the name of the node
	 * */
	public Node(String name){
		this.edgesIn = new HashSet<Route>();
		this.edgesOut = new HashSet<Route>();
		this.name = name;
	}


	/**
	 * Adds an edge to the Node
	 * */
	public boolean addEdge(Route route){
		if(route == null)return false;

		if(!(route.getSrc().equals(name) || route.getDest().equals(name))){
			return false;
		}

		if(route.getDest().equals(name)){
			edgesIn.add(route);
		}

		else if(route.getSrc().equals(name)){
						edgesOut.add(route);
			}
		return true;
	}


	/**
	 * Updates the route in the set with the passed in route
	 *
	 * @param route to be updated
	 * */
	public void updateRoute(Route route){
		if(route == null)return;

		edgesIn.remove(route);
		edgesOut.remove(route);

		edgesIn.add(route);
		edgesOut.add(route);
	}


	/**
	 * Removes a route from the node
	 *
	 * @param route to be removed
	 *
	 * @return whether the route was removed
	 * */
	public boolean removeRoute(Route route){
		if(route == null)return false;

		boolean removed = false;

		if(edgesOut.contains(route)){
			edgesOut.remove(route);
			removed = true;
		}

		if(edgesIn.contains(route)){
			edgesIn.remove(route);
			removed = true;
		}
		return removed;
	}


	/**
	 * returns all of the connection in and out of the node
	 *
	 * @return the neighbors of the node
	 * */
	public Set<Route> getNeighbours(){
		Set<Route> neighbours = new HashSet<Route>();
		neighbours.addAll(edgesIn);
		neighbours.addAll(edgesOut);

		return neighbours;
	}

	/**
	 * returns the connections out of the node
	 *
	 * @return set of outgoing connections
	 * */
	public Set<Route> getRouteOut(){
		return edgesOut;
	}


	/**
	 * returns the connections into of the node
	 *
	 * @return set of incoming connections
	 * */
	public Set<Route> getRouteIn(){
		return edgesIn;
	}


	/**
	 * Prints the routes information used for debugging
	 * */
	public void printRoutes(){
		System.out.println("Node " +this.name + " In size " + edgesIn.size() + " Out Size " + edgesOut.size()  );
		for(Route r : edgesIn){
			System.out.println(r);
		}
		for(Route r : edgesOut){
			System.out.println(r);
		}
	}


	/**
	 * Resets the node for searching
	 * */
	public void resetSearch(){
		this.minCost = Double.POSITIVE_INFINITY;
		this.prev = null;
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((name == null) ? 0 : name.hashCode());
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
		Node other = (Node) obj;
		if (name == null) {
			if (other.name != null)
				return false;
		} else if (!name.equals(other.name))
			return false;
		return true;
	}


	@Override
	public String toString(){return this.name;}
	public String getName(){return this.name;}
	public Node getPrev(){return this.prev;}
	public void setPrev(Node n){this.prev = n;}
	public double getMinCost(){return this.minCost;}
	public void setMinCost(double minCost){this.minCost = minCost;}
	public void setRouteTaken(Route r){this.routeTaken = r;}
	public Route getRouteTaken(){return this.routeTaken;}


	@Override
	public int compareTo(Node o) {return Double.compare(minCost, o.getMinCost());}

}
