package kps.data;

import static org.junit.Assert.*;
import kps.enums.TransportType;
import org.junit.Test;

public class RouteGraphTests {
	
	
	public RouteGraph createSimpleGraph(){
		RouteGraph g = new RouteGraph();
		g.addRoute(new Route("Taupo","Hamilton", 5d, TransportType.Land));
		g.addRoute(new Route("Hamilton","Auckland", 5d, TransportType.Land));
		g.addRoute(new Route("Auckland","Northland", 5d, TransportType.Land));
		return g;
	}
	
	@Test 
	public void addingToGraphTest(){
		RouteGraph g = createSimpleGraph();
		
		g.setUpDFS();
		
		assertTrue(g.getSize() == 4);
		
	}
	
	@Test public void simplePathTest(){
		RouteGraph g = createSimpleGraph();
	}
	
	@Test public void nodeAddRouteTest(){
		Node node1 = new Node("Taupo");
		Route route1 = new Route("Taupo","Hamilton",5d,TransportType.Land);
		
		assertTrue(node1.addEdge(route1));
		assertTrue(node1.edgesOut.size() == 1);
		assertTrue(node1.getNeighbours().size() == 1);
		
	}
	
	@Test
	public void iterateAllTest(){
		
	}
	
	
}
