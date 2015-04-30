package kps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class RouteGraph {

	// fields
	//private Route root; // root node for the route graph (not sure how you want to implement this.
	private List<Route> criticalRoutes;
	private Map<Route, Integer> averageDeliveryTimes;
	
	//
	Node root;
	
	Set<Node> nodes; 

	public RouteGraph(){
		nodes = new HashSet<Node>();
	}
	
	
	/**
	 * Updates a route
	 * */
    public boolean updateRoute(Route route){
    	if(route == null || !nodes.contains(route))return false;
    	return true;
    	
    	
    }
    
    /**
     * Adds a route to the graph
     * */
    public void addRoute(Route route) {
    	boolean routeAdded = false;
    	Node dest;
    	
    	
    	for(Node n : nodes){
    		if(n.getName().equals(route.getSrc())){
    			//a node already exists in the graph with the src name of the route
    			n.addEdge(route); 
    			routeAdded = true;
    		}
    	}
    	
    	//if a route is added need to create the destination node for the graph
    	if(routeAdded){
    		for(Node n : nodes){//check if a node exists with the destination
    			if(n.getName().equals(route.getDest())){
    				n.addEdge(route);
    				return;
    			}
    		}
    		dest = new Node(route.getDest());
    		dest.addEdge(route);
    		nodes.add(dest);//the route was added 
    	}
    	
    	//no graph exists create new node set route source as the root name
    	if(root == null){
    		root = new Node(route.getSrc());
    		dest = new Node(route.getDest());
    		dest.addEdge(route);
    		nodes.add(dest);//the route was added
    		nodes.add(root);
    	}
    	
    }
    
    class SearchNode{
    	boolean visited;
    	Node node;
    	public SearchNode(Node n){
    		node = n;
    		visited = false;
    	}
    	
    	public Node getNode(){return this.node;}
    	
    	@Override
    	public boolean equals(Object other){
    		if(other instanceof SearchNode){
    			SearchNode sn = (SearchNode)other;
    			return sn.getNode().getName().equals(node.getName());
    		}
    		return false;
    	}
    }
    
    List<SearchNode> visited = new ArrayList<SearchNode>();
    List<SearchNode> allnodes = new ArrayList<SearchNode>();
    
    public void setUpDFS(){
    	for(Node n : nodes){
    		allnodes.add(new SearchNode(n));
    	}


    	DFS(this,allnodes.get(0));
    	System.out.println("Visited " + visited.size() );
    }
    
    public void DFS(RouteGraph g, SearchNode n){
    	n.visited = true;
    	visited.add(n);


    	for(Route r : n.getNode().getNeighbours()){
    		for(SearchNode an : allnodes){
    			if((an.getNode().getName().equals(r.getDest()) || an.getNode().getName().equals(r.getSrc()))  && an.visited == false){//does visit them all but might want to remove it so its only the destination node
    				System.out.println("Name :"+an.getNode().getName());
    				DFS(g,an);
    			}
    		}
    	}
    }
    
    public int getSize(){return nodes.size();}

}
