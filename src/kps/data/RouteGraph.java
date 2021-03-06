package kps.data;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import kps.parser.KPSParser;
/**
 * @author Nicky van Hulst 300294657
 * */
public class RouteGraph implements Iterable<Node> {

	//the list of the critical routes in the graph
	private List<Route> criticalRoutes;

	//the nodes of the graph
	private List<Node> nodes;


	/**
	 * Constructor for RouteGraph
	 * */
	public RouteGraph(){
		nodes = new ArrayList<Node>();
		criticalRoutes = new ArrayList<Route>();
	}


	/**
	 * Updates a route
	 * */
    public boolean updateRoute(Route route){
    	if(route == null)return false;

    	for(Node n : nodes){
    		for(Route r : n.getNeighbours()){
    			if(route.equals(r)){
    				n.updateRoute(route);
    				return true;
    			}
    		}
    	}
    	return false;
    }


    /**
     * Creates a source node
     * */
    private void createSrc(Route route, Node destNode){
    	Node src = new Node(route.getSrc());
    	src.addEdge(route);
    	destNode.addEdge(route);
    	nodes.add(src);
    }


    /**
     * Creates a destination node
     *
     * @param route holding dest node
     *
     * @param node to add route to
     * */
    private void createDest(Route route, Node srcNode){
    	Node dest = new Node(route.getDest());
    	dest.addEdge(route);
    	srcNode.addEdge(route);
    	nodes.add(dest);
    }


    /**
     * Creates a source and destination node
     *
     * @param route holding src and dest
     * */
    private void createSrcAndDest(Route route){
    	Node dest = new Node(route.getDest());
    	Node  src= new Node(route.getSrc());
		dest.addEdge(route);
		src.addEdge(route);
		nodes.add(dest);//the route was added
		nodes.add(src);
    }


    /**
     * Adds a route to the graph
     *
     * @param route to be added
     * */
    public void addRoute(Route route) {
    	if(route == null)return;

    	Node srcNode = null;
    	Node destNode = null;

    	for(Node n : nodes){
    		if(n.getName().equals(route.getSrc()))srcNode = n;
    		if(n.getName().equals(route.getDest()))destNode = n;
    	}


    	if( srcNode == null && destNode == null){
    		createSrcAndDest(route);
    		return;
    	}

    	if(srcNode !=null && destNode !=null){
    		srcNode.addEdge(route);
    		destNode.addEdge(route);
    		return;
    	}

    	if(srcNode == null && destNode !=null){
    		createSrc(route, destNode);
    		return;
    	}
    	if(destNode == null && srcNode != null){
    		createDest(route, srcNode);
    		return;
    	}
    }


    /**
     * Removes route from graph
     *
     * @param route to be removed
     * */
    public boolean removeRoute(Route route){
		if(route == null)return false;

    	boolean toReturn = false;

    	for(Node n : nodes){

    	}

    	for(int i = 0; i < nodes.size(); i++){
    		for(Route r : nodes.get(i).getNeighbours()){
    			if(r.equals(route)){
    				nodes.get(i).removeRoute(route);
        			if(nodes.get(i).getNeighbours().size() == 0){nodes.remove(i);//remove the node as it no longer has any routes
        			}
        			toReturn = true;
    			}
    		}
    	}
    	return toReturn;
    }

    /**
     * return whether or not the graph contains the route
     * */
    public boolean containsRoute(Route route){
    	for(Route r : getAllRoutes()){
    		if(route.equals(r)){
    			return true;
    		}
    	}
    	return false;
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

    //for Depth First Search
    private List<SearchNode> visited;
    private List<SearchNode> allnodes;

    public void setUpDFS(){
    	allnodes =  new ArrayList<SearchNode>();//resets searchNode list
    	visited = new ArrayList<SearchNode>();//reset visited list

    	for(Node n : nodes){
    		allnodes.add(new SearchNode(n));
    	}
    	if(!nodes.isEmpty())DFS(this,allnodes.get(0));
    }

    public void DFS(RouteGraph g, SearchNode n){
    	n.visited = true;
    	visited.add(n);


    	for(Route r : n.getNode().getNeighbours()){
    		for(SearchNode an : allnodes){
    			if((an.getNode().getName().equals(r.getDest()) || an.getNode().getName().equals(r.getSrc()))  && an.visited == false){//does visit them all but might want to remove it so its only the destination node
    				DFS(g,an);
    			}
    		}
    	}
    }

    public boolean isCriticalRoute(Route route){
    	setUpDFS();

    	int origSise = visited.size();

    	this.removeRoute(route);

    	setUpDFS();

    	int finalSize = visited.size();
    	this.addRoute(route);
    	if(finalSize < origSise )return true;

    	return false;
    }

    /**
     * return the routes that matches the source destination and company
     * */
    public Set<Route> getRoutes(String company, String src, String dest){
    	Set<Route> routes = new HashSet<>();
    	 for(Node n : nodes){
    		 for(Route r : n.getNeighbours()){
    			 if(r.getDest().equals(dest) && r.getSrc().equals(src) && r.getCompany().equals(company))
    				 routes.add(r);
    		 }
    	 }
    	return routes;
    }

    /**
     * return the routes that matches the source destination
     * */
    public Set<Route> getRoutes(String src, String dest){
    	Set<Route> routes = new HashSet<>();
    	 for(Node n : nodes){
    		 for(Route r : n.getNeighbours()){
    			 if(r.getDest().equals(dest) && r.getSrc().equals(src))
    				 routes.add(r);
    		 }
    	 }
    	return routes;
    }

    /**
     * Returns all of the routes in the graph
     * */
    public Set<Route> getAllRoutes(){
    	Set<Route> routes = new HashSet<Route>();

    	for(Node n: nodes){
    		for(Route r : n.getNeighbours()){
    			routes.add(r);
    		}
    	}
    	return routes;
    }

    public Set<String> sourcesFromDest(String dest){
    	Set<Route> routes = getAllRoutes();
    	Set<String> sources = new HashSet<>();

    	for (Route route : routes){
    		if (route.getDest().equals(dest)){
    			sources.add(route.getSrc());
    		}
    	}
    	return sources;
    }

    public Set<String> destsFromSource(String src){
    	Set<Route> routes = getAllRoutes();
    	Set<String> dests = new HashSet<>();

    	for (Route route : routes){
    		if (route.getSrc().equals(src)){
    			dests.add(route.getDest());
    		}
    	}
    	return dests;
    }

    public int getSize(){return nodes.size();}


	@Override
	public Iterator<Node> iterator() {
		return nodes.iterator();
	}

	public List<Node> getNodes(){return this.nodes;}

	public Node getNode(String name){
		for(int i =0; i < nodes.size(); i++){
			if(nodes.get(i).getName().equals(name))return nodes.get(i);
		}
		return null;
	}

	public String toXML(){
		String xml = "<"+KPSParser.GRAPH_FILE_TAG+">\n";
		for(Route route : getAllRoutes()){
			xml += route.toXML();
		}
		xml += "</"+KPSParser.GRAPH_FILE_TAG+">\n";
		return xml;
	}
}
