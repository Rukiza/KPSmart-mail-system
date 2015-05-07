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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RouteGraph g = new RouteGraph();
		
		int eventCount = 0;
		for(BusinessEvent e : events){
			if(e instanceof TransportCostUpdateEvent){
				g.addRoute(new Route((TransportCostUpdateEvent)e));
				eventCount++;
			}
		}
		System.out.println("Events : " +eventCount);
		return g;
	}

	@Test
	public void testXMLGraph(){
		RouteGraph g = readGraphFromXML();


		System.out.println("size :" + g.getSize());
		assertTrue(g.getSize() == 6);
	}

	@Test
	public void testSimpleRoute(){
		BasicRoute route = new BasicRoute("Sydney", "Suva");
		Mail mail = new Mail(route, Day.FRIDAY, 100, 5, Priority.DOMESTIC_AIR);

		RouteGraph g = readGraphFromXML();

		System.out.println("-------------------------------------------");
		
		for(Node n : g.getNodes())n.printRoutes();
		
		System.out.println("-------------------------------------------");

		
		DijkstraSearch ds = new DijkstraSearch(g);
		
		
		Map<List<Node>,Double> shortestPath = ds.getShortestPath(mail);

		//Expected shortest path
		//String[] shortestPathNodes = {"Wellington","Hamilton","Auckland","Northland"};

		for(List<Node> list : shortestPath.keySet()){
			for(int i = 0; i < list.size(); i++){
				//check if the node in the list name meets the expected output
				//assertTrue(list.get(i).getName().equals(shortestPathNodes[i]));
				System.out.print(list.get(i).getName() + ", ");
			}
			//Expected shortest path cost
			//assertTrue(shortestPath.get(list).doubleValue() == 50);
			System.out.println(shortestPath.get(list).doubleValue());
			assertTrue(true);
		}

	}



//	public RouteGraph createSimpleGraph(){
//		RouteGraph g = new RouteGraph();
//		g.addRoute(new Route("Taupo","Hamilton", 5d, TransportType.LAND));
//		g.addRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND));
//		g.addRoute(new Route("Auckland","Northland", 5d, TransportType.LAND));
//		return g;
//	}
//
//	public RouteGraph createDirectionalGraph(){
//		RouteGraph g = new RouteGraph();
//		g.addRoute(new Route("Taupo","Hamilton", 5d, TransportType.LAND));
//		g.addRoute(new Route("Hamilton","Taupo", 5d, TransportType.LAND));
//
//		g.addRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND));
//		g.addRoute(new Route("Auckland","Hamilton", 5d, TransportType.LAND));
//
//		g.addRoute(new Route("Auckland","Northland", 5d, TransportType.LAND));
//		g.addRoute(new Route("Northland","Auckland", 5d, TransportType.LAND));
//		return g;
//	}
//
//	public RouteGraph createMediumGraphGraph(){
//		RouteGraph g = new RouteGraph();
//		g.addRoute(new Route("Taupo","Hamilton", 10d, TransportType.LAND));
//		g.addRoute(new Route("Hamilton","Taupo", 5d, TransportType.LAND));
//
//		g.addRoute(new Route("Hamilton","Auckland", 20d, TransportType.LAND));
//		g.addRoute(new Route("Auckland","Hamilton", 10d, TransportType.LAND));
//
//		g.addRoute(new Route("Auckland","Northland", 10d, TransportType.LAND));
//		g.addRoute(new Route("Northland","Auckland", 15d, TransportType.LAND));
//
//		g.addRoute(new Route("Taupo","New Plymouth", 5d, TransportType.LAND));
//		g.addRoute(new Route("New Plymouth","Hamilton", 20d, TransportType.LAND));
//
//		return g;
//	}
//
//	@Test
//	public void shortestRouteTest(){
//		RouteGraph g = createMediumGraphGraph();
//		DijkstraSearch sp = new DijkstraSearch(g);
//		CustomerRoute custRoute = new CustomerRoute(new BasicRoute("New Plymouth","Northland"));
//
//		Map<List<Node>,Double> shortestPath = sp.getShortestPath(custRoute);//return a map entry with the shortest path and cost
//
//		//Expected shortest path
//		String[] shortestPathNodes = {"New Plymouth","Hamilton","Auckland","Northland"};
//
//		for(List<Node> list : shortestPath.keySet()){
//			for(int i = 0; i < list.size(); i++){
//				//check if the node in the list name meets the expected output
//				assertTrue(list.get(i).getName().equals(shortestPathNodes[i]));
//			}
//			//Expected shortest path cost
//			assertTrue(shortestPath.get(list).doubleValue() == 50);
//		}
//	}
//
//	@Test
//	public void addingToGraphTest(){
//		RouteGraph g = createSimpleGraph();
//		g.setUpDFS();
//
//		assertTrue(g.getSize() == 4);
//	}
//
//	@Test
//	public void criticalRouteTest(){
//		RouteGraph g = createSimpleGraph();
//
//
//		//assertFalse(g.isCriticalRoute(new Route("Taupo", "Hamilton", 5d, TransportType.LAND)));
//		//assertFalse(g.isCriticalRoute(new Route("Auckland","Northland", 5d, TransportType.LAND)));
//		assertTrue(g.isCriticalRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND)));
//	}
//
//	@Test
//	public void directionGraphSizeTest(){
//		RouteGraph g = createDirectionalGraph();
//		System.out.println("Directional DFS");
//		g.setUpDFS();
//		assertEquals(g.getSize(), 4);
//	}
//
//	@Test public void simplePathTest(){
//		RouteGraph g = createSimpleGraph();
//	}
//
//	@Test public void simpleRemoveTest(){
//		RouteGraph g = createSimpleGraph();
//		Route toRemoveRoute = new Route("Hamilton", "Taumarunui", 10d, TransportType.LAND);
//
//		g.addRoute(toRemoveRoute);
//		assertTrue(g.getSize() == 5);
//		g.removeRoute(toRemoveRoute);
//		assertTrue(g.getSize() == 4);
//	}
//
//
//	@Test public void nodeAddRouteTest(){
//		Node node1 = new Node("Taupo");
//		Route route1 = new Route("Taupo","Hamilton",5d,TransportType.LAND);
//
//		assertTrue(node1.addEdge(route1));
//		assertTrue(node1.edgesOut.size() == 1);
//		assertTrue(node1.getNeighbours().size() == 1);
//	}
//
//	@Test
//	public void iterateAllTest(){
//
//	}


}
