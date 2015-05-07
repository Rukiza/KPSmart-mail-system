package kps.data;

import java.util.HashSet;
import java.util.Set;
/**
 * @author Nicky van Hulst 300294657
 * */
public class Node  implements Comparable<Node>{
	
	private Node prev;
	private double minCost = Double.POSITIVE_INFINITY;
	
	
	//name of the node
	String name;
	
	//places you can get to from the node
	Set<Route> edgesOut;
	Set<Route> edgesIn;
	
	public Node(String name){
		this.edgesIn = new HashSet<Route>();
		this.edgesOut = new HashSet<Route>();
		this.name = name;
	}
	
	/**
	 * Adds an edge to the Node
	 * */
	public boolean addEdge(Route route){
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
	
	public void updateRoute(Route route){
		edgesIn.remove(route);
		edgesOut.remove(route);
		
		edgesIn.add(route);
		edgesOut.add(route);
	}
	
	public boolean removeRoute(Route route){
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
	
	
	public Set<Route> getNeighbours(){
		Set<Route> neighbours = new HashSet<Route>();
		neighbours.addAll(edgesIn);
		neighbours.addAll(edgesOut);
		
		return neighbours;
	}
	
	public Set<Route> getRouteOut(){
		return edgesOut;
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

	public Set<Route> getRouteIn(){
		return edgesIn;
	}
	
	public void resetSearch(){
		this.minCost = Double.POSITIVE_INFINITY;
		this.prev = null;
	}
	
	public void printRoutes(){
		System.out.println("Node " +this.name + " In size " + edgesIn.size() + " Out Size " + edgesOut.size()  );
		for(Route r : edgesIn){
			System.out.println(r);
		}
		for(Route r : edgesOut){
			System.out.println(r);
		}
	}

	
	@Override
	public String toString(){return this.name;}
	public String getName(){return this.name;}
	public Node getPrev(){return this.prev;}
	public void setPrev(Node n){this.prev = n;}
	public double getMinCost(){return this.minCost;}
	public void setMinCost(double minCost){this.minCost = minCost;}
	

	@Override
	public int compareTo(Node o) {return Double.compare(minCost, o.getMinCost());}

}
