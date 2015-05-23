package kps.ui.graph;

import java.awt.*;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;
import kps.data.Node;
import kps.data.Route;

public class DrawRoute {

	private DrawNode node1;
	private DrawNode node2;

	private ArrayList<Route> routes;

	/**
	 * Represents a connection between nodes and holds the routes between them
	 * */
	public DrawRoute(Route r, DrawNode node1, DrawNode node2){
		routes = new ArrayList<Route>();
		routes.add(r);
		this.node2 = node2;
		this.node1 = node1;
	}

	/**
	 * Returns whether a route should be added to a drawNode
	 * */
	public boolean addRouteCheck(Route r){
		return (r.getDest().equals(node2.getNode().getName()) || r.getDest().equals(node1.getNode().getName()))
			   &&
			   (r.getSrc().equals(node2.getNode().getName()) || r.getSrc().equals(node1.getNode().getName()));
	}
	
	public void addRoute(Route r){
		if(r == null)return;
		this.routes.add(r);
	}

	public void draw(Graphics2D g){
		boolean toNode2 = false;
		boolean toNode1 = false;

		//check whether the connection is bidirectional
		for(Route r : routes){
			if(r.getDest().equals(node2.getNode().getName()))toNode2 = true;
			if(r.getDest().equals(node1.getNode().getName()))toNode1 = true;
		}

		double node1X = node1.getX();
		double node1Y = node1.getY();

		double node2X = node2.getX();
		double node2Y = node2.getY();


		double nodeSize = node1.getSize()/2;
		
		if(node1 !=null && node2 != null)g.drawLine((int)(node1.getX()+nodeSize),(int) (node1.getY()+nodeSize), (int)(node2.getX()+nodeSize), (int)(node2.getY()+nodeSize));

		//check if there is a route going to both nodes
		if(toNode2 && toNode1){
			
		}
		else if(toNode2){//node 2 is the destination
			List<Point> points =  CircleLine.getCircleLineIntersectionPoint(new Point((int)(node1X+nodeSize),(int) (node1Y+nodeSize)),new Point((int)(node2X+nodeSize),(int) (node2Y+nodeSize)),new Point((int)(node2X+nodeSize), (int)(node2Y+nodeSize)) , nodeSize);
	
			g.fillOval(points.get(0).x-5,points.get(0).y-5, 15, 15);

		}
		else{//node one is the destination
			
		}
	}


	public ArrayList<Point> getShapePoints(Shape s){
		  FlatteningPathIterator iter;

	        ArrayList<Point> points;
	        int index=0;
	            iter=new FlatteningPathIterator(s.getPathIterator(new AffineTransform()), 1);
	            points=new ArrayList<Point>();
	            float[] coords=new float[6];
	            while (!iter.isDone()) {
	                iter.currentSegment(coords);
	                int x=(int)coords[0];
	                int y=(int)coords[1];
	                points.add(new Point(x,y));
	                iter.next();
	            }
	            return points;
	}

	public int connectionsTo(Node src, Node dest){
		int connections = 0;

		for(Route r : src.getRouteOut()){
			if(r.getDest().equals(dest.getName()))connections++;
		}
		for(Route r : dest.getRouteOut()){
			if(r.getDest().equals(src.getName()))connections++;
		}
		return connections;
	}


	/**
	 * Return if a point is inside the node
	 *
	 * @param point to test
	 * */
	public boolean containsPoint(Point p){
		return true;
	}
}
