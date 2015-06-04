package kps.data;

import static org.junit.Assert.assertFalse;
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
			assertTrue(shortestPath.get(list).doubleValue() == Double.POSITIVE_INFINITY);
		}
	}

	@Test
	public void testIsValid(){
		RouteGraph g = readGraphFromXML();

		BasicRoute route = new BasicRoute("Wellington", "Suva");
		BasicRoute route1 = new BasicRoute("Wellington", "Sydney");
		BasicRoute route2 = new BasicRoute("Sydney", "Suva");
		BasicRoute route3 = new BasicRoute("Auckland", "Wellington");

				BasicRoute route4 = new BasicRoute("Wellington", "Auckland");
		BasicRoute route5 = new BasicRoute("Wellington", "Hamilton");
		BasicRoute route6 = new BasicRoute("Rome", "Wellington");
		BasicRoute route7 = new BasicRoute("Suva", "Wellington");



		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_LAND);
		Mail mail1 = new Mail(route1, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail2 = new Mail(route2, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail3 = new Mail(route3, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail4 = new Mail(route4, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail5 = new Mail(route5, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail6 = new Mail(route6, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		Mail mail7 = new Mail(route7, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);
		DijkstraSearch d = new DijkstraSearch(g);


		assertTrue(d.isValidMailDelivery(mail));
		assertTrue(d.isValidMailDelivery(mail1));
		assertFalse(d.isValidMailDelivery(mail2));
		assertFalse(d.isValidMailDelivery(mail3));

		assertFalse(d.isValidMailDelivery(mail4));
		assertFalse(d.isValidMailDelivery(mail5));
		assertFalse(d.isValidMailDelivery(mail6));
		assertFalse(d.isValidMailDelivery(mail7));
	}

	@Test
	public void testMaxWeightExceeded(){
		BasicRoute route = new BasicRoute("Rome", "Sydney");
		Mail mail = new Mail(route, Day.FRIDAY, 1000000, 5, Priority.DOMESTIC_LAND);

		RouteGraph g = readGraphFromXML();
		DijkstraSearch ds = new DijkstraSearch(g);

		assertFalse(ds.isValidMailDelivery(mail));

	}


	@Test
	public void testMaxVolumeExceeded(){
		BasicRoute route = new BasicRoute("Rome", "Sydney");
		Mail mail = new Mail(route, Day.FRIDAY, 2, 1000000000, Priority.DOMESTIC_LAND);

		RouteGraph g = readGraphFromXML();
		DijkstraSearch ds = new DijkstraSearch(g);

		assertFalse(ds.isValidMailDelivery(mail));
	}

	@Test
	public void bothMaxExceeded(){
		BasicRoute route = new BasicRoute("Rome", "Sydney");
		Mail mail = new Mail(route, Day.FRIDAY, 100000000, 1000000000, Priority.DOMESTIC_LAND);

		RouteGraph g = readGraphFromXML();
		DijkstraSearch ds = new DijkstraSearch(g);

		assertFalse(ds.isValidMailDelivery(mail));
	}
}
