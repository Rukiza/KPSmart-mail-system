package kps.data;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import kps.Main;
import kps.data.wrappers.BasicRoute;
import kps.enums.Day;
import kps.enums.Priority;
import kps.events.BusinessEvent;
import kps.events.TransportCostUpdateEvent;
import kps.parser.KPSParser;
import kps.parser.ParserException;
/**
 * @author Nicky van Hulst 300294657
 * */
public class RouteGraphTests {
	List<BusinessEvent> events = new ArrayList<BusinessEvent>();

	public RouteGraph readGraphFromXML(){
		try {
			events = KPSParser.parseFile(Main.XML_FILE_PATH+"graph.xml");
		} catch (ParserException e) {
			e.printStackTrace();
		}

		RouteGraph g = new RouteGraph();
		
		for(BusinessEvent e : events){
			if(e instanceof TransportCostUpdateEvent){
				g.addRoute(new Route((TransportCostUpdateEvent)e));
			}
		}
		return g;
	}

	@Test
	public void testXMLGraphSize(){
		RouteGraph g = readGraphFromXML();

		assertTrue(g.getSize() == 6);
	}
	
	@Test
	public void testRemove(){
		RouteGraph g = readGraphFromXML();
		
		List<Node> nodes = g.getNodes();
		Node n = nodes.get(0);//just grab the first node in the graph
		Route testRoute = null;
		for(Route r : n.getNeighbours()){
			testRoute = r;
		}
		System.out.println(testRoute);
		g.removeRoute(testRoute);
		
		//go through graph checking if the route exists 
		for(Node ns : g.getNodes()){
			for(Route r : ns.getNeighbours()){
				assertTrue(r!=testRoute);
			}
		}
	}
	
	@Test
	public void testUpdate(){
		RouteGraph g = readGraphFromXML();
		
		
		List<Node> nodes = g.getNodes();
		Node n = nodes.get((int)(Math.random()*nodes.size()));//just grab a random node in the graph
		Route testRoute = null;
		for(Route r : n.getNeighbours()){
			testRoute = r;
		}
		
		g.removeRoute(testRoute);
		
		
		
		//go through graph checking if the route exists 
		for(Node ns : g.getNodes()){
			for(Route r : ns.getNeighbours()){
				assertTrue(r!=testRoute);
				System.out.println(r);
			}
		}
	}
	

	
	

	@Test
	public void testSimpleRoute(){
		BasicRoute route = new BasicRoute("Wellington", "Suva");
		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);

		RouteGraph g = readGraphFromXML();

		System.out.println("-------------------------------------------");
		
		for(Node n : g.getNodes())n.printRoutes();
		
		System.out.println("-------------------------------------------");

		
		DijkstraSearch ds = new DijkstraSearch(g);
		
		
		Map<List<Node>,Double> shortestPath = ds.getShortestPath(mail);

		//Expected shortest path
		String[] shortestPathNodes = {"Wellington","Sydney","Suva"};

		for(List<Node> list : shortestPath.keySet()){
			for(int i = 0; i < list.size(); i++){
				//check if the node in the list name meets the expected output
				assertTrue(list.get(i).getName().equals(shortestPathNodes[i]));
			}
			//Expected shortest path cost
			//assertTrue(shortestPath.get(list).doubleValue() == 50);
			assertTrue(shortestPath.get(list).doubleValue() == 515);//TODO check
		}

	}
	
	@Test
	public void testInvalidRoute(){
		BasicRoute route = new BasicRoute("Rome", "Suva");
		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);

		RouteGraph g = readGraphFromXML();
		DijkstraSearch ds = new DijkstraSearch(g);
		
		Map<List<Node>,Double> shortestPath = ds.getShortestPath(mail);

		//Expected shortest path
		String[] shortestPathNodes = {"Suva"};

		for(List<Node> list : shortestPath.keySet()){
			for(int i = 0; i < list.size(); i++){
				//check if the node in the list name meets the expected output
				System.out.println(shortestPathNodes[i]);
				System.out.println(list.get(i).getName());
				assertTrue(list.get(i).getName().equals(shortestPathNodes[i]));
			}
			//Expected shortest path cost
			assertTrue(shortestPath.get(list).doubleValue() == Double.POSITIVE_INFINITY);//TODO check
		}
	}
}
