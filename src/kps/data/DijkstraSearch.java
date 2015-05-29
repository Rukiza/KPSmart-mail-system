package kps.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;
import kps.enums.Priority;
import kps.enums.TransportType;
/**
 * @author Nicky van Hulst 300294657
 * */
public class DijkstraSearch  {

	 //Nodes of the graph
	 private List<Node> dijkNodesList;

	 //the mail to be delivered
	 private Mail mail;


	/**
	 * Constructor for DijkstraSearch
	 *
	 * @param graph to be searched
	 * */
	public DijkstraSearch(RouteGraph g){
		dijkNodesList = g.getNodes();
	}


	/**
	 * searching the graph and return if the mail can
	 * be sent or not
	 *
	 * @param Mail to be delivered
	 *
	 * @return whether or not the mail can be sent
	 * */
	public boolean isValidMailDelivery(Mail mail){
		return !(getShortestPath(mail).isEmpty());
	}


	/**
	 * Finds the shortest path using the mail source and destination.
	 *
	 *
	 * @param the route of the customer
	 *
	 * @return a map containing the shortest path and a cost
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
						if(path.size()>1){//greater than one becuase list contains src
							returnMap.put(path, dijkNodesList.get(i).getMinCost());
						}
						return returnMap;
					}
				}
		return null;
	}


	/**
	 * Computes the paths from the source
	 * to all other nodes
	 *
	 * @param Node to search from
	 * */
	private  void computePaths(Node source){
		 	boolean isFlight = Priority.isAirPriority(mail.getPriority());

	        source.setMinCost(0);

	        PriorityQueue<Node> nodeQueue = new PriorityQueue<Node>();
	      	nodeQueue.add(source);

		while (!nodeQueue.isEmpty()) {
			Node dn = nodeQueue.poll();
	            for (Route r : dn.getRouteOut()){

	            	//checks the flying condition and max weight and volume
	            	if((isFlight && !(r.getType().equals(TransportType.AIR))) || r.maxWeight() < mail.getWeight() || r.maxVolume() < mail.getVolume() )continue;

	            	Node d = null;
	            	for(int i =0; i < dijkNodesList.size(); i++){
	            		if(dijkNodesList.get(i).getName().equals(r.getDest()))d = dijkNodesList.get(i);
	            	}

	                double cost = r.calculateCost(mail.getVolume(),mail.getWeight());

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


	 /**
	  * Finds the shortest path to the target
	  *
	  * @param the target node
	  *
	  * @return The list of nodes representing the shortest path
	  * */
	 private  List<Node> getShortestPathTo(Node target)
	    {
	        List<Node> path = new ArrayList<Node>();
	        for (Node n = target; n != null; n = n.getPrev())
	            path.add(n);
	        Collections.reverse(path);
	        return path;
	    }
}

