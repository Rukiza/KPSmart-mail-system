package kps.data;

import static org.junit.Assert.*;

import java.util.List;
import java.util.Map;

import kps.data.wrappers.BasicRoute;
import kps.enums.TransportType;

import org.junit.Test;
/**
 * @author Nicky van Hulst 300294657
 * */
public class RouteGraphTests {
	
	
	public RouteGraph createSimpleGraph(){
		RouteGraph g = new RouteGraph();
		g.addRoute(new Route("Taupo","Hamilton", 5d, TransportType.LAND));
		g.addRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND));
		g.addRoute(new Route("Auckland","Northland", 5d, TransportType.LAND));
		return g;
	}
	
	public RouteGraph createDirectionalGraph(){
		RouteGraph g = new RouteGraph();
		g.addRoute(new Route("Taupo","Hamilton", 5d, TransportType.LAND));
		g.addRoute(new Route("Hamilton","Taupo", 5d, TransportType.LAND));

		g.addRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND));
		g.addRoute(new Route("Auckland","Hamilton", 5d, TransportType.LAND));
		
		g.addRoute(new Route("Auckland","Northland", 5d, TransportType.LAND));
		g.addRoute(new Route("Northland","Auckland", 5d, TransportType.LAND));
		return g;
	}
	
	public RouteGraph createMediumGraphGraph(){
		RouteGraph g = new RouteGraph();
		g.addRoute(new Route("Taupo","Hamilton", 10d, TransportType.LAND));
		g.addRoute(new Route("Hamilton","Taupo", 5d, TransportType.LAND));

		g.addRoute(new Route("Hamilton","Auckland", 20d, TransportType.LAND));
		g.addRoute(new Route("Auckland","Hamilton", 10d, TransportType.LAND));
		
		g.addRoute(new Route("Auckland","Northland", 10d, TransportType.LAND));
		g.addRoute(new Route("Northland","Auckland", 15d, TransportType.LAND));
		
		g.addRoute(new Route("Taupo","New Plymouth", 5d, TransportType.LAND));
		g.addRoute(new Route("New Plymouth","Hamilton", 20d, TransportType.LAND));
		
		return g;
	}
	
	@Test
	public void shortestRouteTest(){
		RouteGraph g = createMediumGraphGraph();
		DijkstraSearch sp = new DijkstraSearch(g);
		CustomerRoute custRoute = new CustomerRoute(new BasicRoute("New Plymouth","Northland"));
		
		Map<List<Node>,Double> shortestPath = sp.getShortestPath(custRoute);//return a map entry with the shortest path and cost
		
		//Expected shortest path
		String[] shortestPathNodes = {"New Plymouth","Hamilton","Auckland","Northland"};
		
		for(List<Node> list : shortestPath.keySet()){
			for(int i = 0; i < list.size(); i++){
				//check if the node in the list name meets the expected output
				assertTrue(list.get(i).getName().equals(shortestPathNodes[i]));
			}
			//Expected shortest path cost
			assertTrue(shortestPath.get(list).doubleValue() == 50);
		}
	}
	
	@Test 
	public void addingToGraphTest(){
		RouteGraph g = createSimpleGraph();
		g.setUpDFS();
		
		assertTrue(g.getSize() == 4);
	}
	
	@Test 
	public void criticalRouteTest(){
		RouteGraph g = createSimpleGraph();
		
		
		//assertFalse(g.isCriticalRoute(new Route("Taupo", "Hamilton", 5d, TransportType.LAND)));
		//assertFalse(g.isCriticalRoute(new Route("Auckland","Northland", 5d, TransportType.LAND)));
		assertTrue(g.isCriticalRoute(new Route("Hamilton","Auckland", 5d, TransportType.LAND)));
	}
	
	@Test 
	public void directionGraphSizeTest(){
		RouteGraph g = createDirectionalGraph();
		System.out.println("Directional DFS");
		g.setUpDFS();
		assertEquals(g.getSize(), 4);
	}
	
	@Test public void simplePathTest(){
		RouteGraph g = createSimpleGraph();
	}
	
	@Test public void simpleRemoveTest(){
		RouteGraph g = createSimpleGraph();
		Route toRemoveRoute = new Route("Hamilton", "Taumarunui", 10d, TransportType.LAND);
		
		g.addRoute(toRemoveRoute);
		assertTrue(g.getSize() == 5);
		g.removeRoute(toRemoveRoute);
		assertTrue(g.getSize() == 4);	
	}
	
	
	@Test public void nodeAddRouteTest(){
		Node node1 = new Node("Taupo");
		Route route1 = new Route("Taupo","Hamilton",5d,TransportType.LAND);
		
		assertTrue(node1.addEdge(route1));
		assertTrue(node1.edgesOut.size() == 1);
		assertTrue(node1.getNeighbours().size() == 1);	
	}
	
	@Test
	public void iterateAllTest(){
		
	}
	
	
}
