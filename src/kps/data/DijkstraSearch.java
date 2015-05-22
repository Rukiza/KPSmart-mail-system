package kps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;
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
	private Mail mail;

	/**
	 * Return the route and cost of the cheapest path as an entry in the map
	 *
	 * @param the route of the customer
	 * */
	public Map<List<Node>,Double> getShortestPath(Mail mail){
		this.mail = mail;
		for(Node n : dijkNodesList){n.resetSearch();}//reset the node

				//look for source node
				for(int i = 0; i < dijkNodesList.size(); i++){
					//compute all the paths to the source node
					if(dijkNodesList.get(i).getName().equals(mail.getOrigin()))computePaths(dijkNodesList.get(i));
				}

				List<Node> path = null;
				for(int i = 0; i < dijkNodesList.size(); i++){
					if(dijkNodesList.get(i).getName().equals(mail.getDestination())){
						//get the shortest path the the destination
						path = getShortestPathTo(dijkNodesList.get(i));
						Map<List<Node>,Double> returnMap = new HashMap<List<Node>,Double>();
						//put the path and cost in the map
						returnMap.put(path, dijkNodesList.get(i).getMinCost());
						return returnMap;
					}
				}
		return null;
	}


	 private  void computePaths(Node source){
	        source.setMinCost(0);

	        PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
	      	nodeQueue.add(source);

		while (!nodeQueue.isEmpty()) {
			Node dn = nodeQueue.poll();

	            for (Route r : dn.getRouteOut()){

	            	Node d = null;
	            	for(int i =0; i < dijkNodesList.size(); i++){
	            		if(dijkNodesList.get(i).getName().equals(r.getDest()))d = dijkNodesList.get(i);
	            	}
	            	
	                double cost = r.calculateCost(mail.getVolume(),mail.getWeight());
	                
	                System.out.println("Cost : "+ cost);
	                double distanceThroughR = dn.getMinCost() + cost;
			if (distanceThroughR < d.getMinCost()) {
			    nodeQueue.remove(d);
			    d.setMinCost(distanceThroughR);
			    d.setPrev(dn);
			    nodeQueue.add(d);
			}
	            }
	        }
	    }

	 private  List<Node> getShortestPathTo(Node target)
	    {
	        List<Node> path = new ArrayList<Node>();
	        for (Node n = target; n != null; n = n.getPrev())
	            path.add(n);
	        	System.out.println("adding to path");
	        Collections.reverse(path);
	        return path;
	    }
}

