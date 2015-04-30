package kps.data;

import java.util.HashSet;
import java.util.Set;

public class Node {
	
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
		if(!(route.getSrc().equals(name) || route.getDest().equals(name)))return false;
		
		if(route.getDest().equals(name))edgesIn.add(route);
		else{edgesOut.add(route);}
		return true;
	}
	
	
	public boolean removeEdge(Route route){
		if(edgesOut.contains(route)){
			edgesOut.remove(route);
			return true;
		}
		
		if(edgesIn.contains(route)){
			edgesIn.remove(route);
			return true;
		}
		return false;
	}
	
	
	public Set<Route> getNeighbours(){
		
		Set<Route> neighbours = new HashSet<Route>();
		neighbours.addAll(edgesIn);
		neighbours.addAll(edgesOut);
		
		return neighbours;
	}  
	
	public String getName(){return this.name;}

}
