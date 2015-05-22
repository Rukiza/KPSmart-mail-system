package kps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
/**
 * @author Nicky van Hulst 300294657
 * */
public class RouteGraph implements Iterable<Node> {


	private List<Route> criticalRoutes;

	/**
	 * the nodes of the graph
	 */
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
    		if(n.getNeighbours().contains(route))n.updateRoute(route);
    		return true;
    	}
    	return false;
    }


    private void createSrc(Route route, Node destNode){
    	Node src = new Node(route.getSrc());
    	src.addEdge(route);
    	destNode.addEdge(route);
    	nodes.add(src);
    }

    private void createDest(Route route, Node srcNode){
    	Node dest = new Node(route.getDest());
    	dest.addEdge(route);
    	srcNode.addEdge(route);
    	nodes.add(dest);
    }

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
     * */
    public void addRoute(Route route) {
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
    	if(criticalRoutes.contains(route))return false;//cannot remove a critical route
    	boolean toReturn = false;

    	for(int i = 0; i < nodes.size(); i++){
    		if(nodes.get(i).getNeighbours().contains(route)){

    			nodes.get(i).removeRoute(route);
    			if(nodes.get(i).getNeighbours().size() == 0)nodes.remove(i);//remove the node as it no longer has any routes
    			toReturn = true;
    		}
    	}
    	return toReturn;
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

    List<SearchNode> visited;
    List<SearchNode> allnodes;

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
    	System.out.println("Starting Critical Route");

    	setUpDFS();

    	int origSise = visited.size();

    	System.out.println("Orig Size :" + origSise);
    	this.removeRoute(route);

    	setUpDFS();

    	int finalSize = visited.size();
    	this.addRoute(route);
    	System.out.println("Final Size : " + finalSize);
    	if(finalSize < origSise )return true;

    	return false;
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

}
