package kps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
/**
 * @author Nicky van Hulst 300294657
 * */
public class DijkstraSearch  {
	 
	 
	//The graph to be searched
	private  RouteGraph graph;
	
	//Nodes of the graph
	static List<Node> dijkNodesList;

	/**
	 * Constructor for DijkstraSearch
	 * 
	 * @param graph to be searched
	 * */
	public DijkstraSearch(RouteGraph g){
		this.graph = g;
		dijkNodesList = g.getNodes();
	}
	
	
	/**
	 * Return the route and cost of the cheapest path as an entry in the map
	 * 
	 * @param the route of the customer
	 * */
	public Map<List<Node>,Double> getShortestPath(CustomerRoute route){
		for(Node n : dijkNodesList){n.resetSearch();}//reset the node
		
				//look for source node 
				for(int i = 0; i < dijkNodesList.size(); i++){
					//compute all the paths to the source node
					if(dijkNodesList.get(i).getName().equals(route.getOrigin()))computePaths(dijkNodesList.get(i));
				}
				
				List<Node> path = null;
				for(int i = 0; i < dijkNodesList.size(); i++){
					if(dijkNodesList.get(i).getName().equals(route.getDestination())){
						//get the shortest path the the destination
						path = getShortestPathTo(dijkNodesList.get(i));
						Map<List<Node>,Double> returnMap = new HashMap<List<Node>,Double>();
						//put the path and cost in the map
						returnMap.put(path, dijkNodesList.get(i).getMindCost());
						return returnMap;
					}
				}
		return null;
	}
	
	@Deprecated
	public List<Node> getPathList(CustomerRoute route){
		
		//look for source node 
		for(int i = 0; i < dijkNodesList.size(); i++){
			if(dijkNodesList.get(i).getName().equals(route.getOrigin()))computePaths(dijkNodesList.get(i));
		}
		
		
		List<Node> path = null;
		for(int i = 0; i < dijkNodesList.size(); i++){
			if(dijkNodesList.get(i).getName().equals(route.getDestination())){
				path = getShortestPathTo(dijkNodesList.get(i));
			}
			
		}
		
		
		return path;
	}
	@Deprecated
	public double getCost(CustomerRoute route){
		//look for source node 
		for(int i = 0; i < dijkNodesList.size(); i++){
			if(dijkNodesList.get(i).getName().equals(route.getOrigin())){
				computePaths(dijkNodesList.get(i));
			}
		}
		
		for(int i = 0; i < dijkNodesList.size(); i++){
			if(dijkNodesList.get(i).getName().equals(route.getDestination()))return dijkNodesList.get(i).getMindCost();
		}
		return -1;
	}
	
	
	 private  void computePaths(Node source){
	        source.setMindCost(0); 
	        
	        PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
	      	nodeQueue.add(source);

		while (!nodeQueue.isEmpty()) {
			Node dn = nodeQueue.poll();
		
	            for (Route r : dn.getRouteOut()){	            	
	            	
	            	Node d = null;
	            	for(int i =0; i < dijkNodesList.size(); i++){
	            		if(dijkNodesList.get(i).getName().equals(r.getDest()))d = dijkNodesList.get(i);
	            	}
	            	
	                double cost = r.getCost();
	                double distanceThroughR = dn.getMindCost() + cost;
			if (distanceThroughR < d.getMindCost()) {
			    nodeQueue.remove(d);
			    d.setMindCost(distanceThroughR);
			    d.setPrev(dn);
			    nodeQueue.add(d);
			}
	            }
	        }
	    }
	 
	 private  List<Node> getShortestPathTo(Node target)
	    {
	        List<Node> path = new ArrayList<Node>();
	        for (Node vertex = target; vertex != null; vertex = vertex.getPrev())
	            path.add(vertex);
	        Collections.reverse(path);
	        return path;
	    }
}
@Deprecated
class DijkNode implements Comparable<DijkNode>{
	public Node node;
	double minDist = Double.POSITIVE_INFINITY;
	DijkNode prev;
	
	public DijkNode(Node node) {this.node = node;}

	@Override
	public int compareTo(DijkNode o) {return Double.compare(minDist, o.minDist);}
	
	@Override
	public String toString(){return node.toString();}
}
