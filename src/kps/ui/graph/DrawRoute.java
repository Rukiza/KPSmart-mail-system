package kps.ui.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import java.awt.geom.FlatteningPathIterator;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kps.data.Node;
import kps.data.Route;

public class DrawRoute {

	private DrawNode node1;
	private DrawNode node2;
	private boolean routeTaken;
	private boolean selected;
	private double nodeRadius = 40;

	private Color routeSelectedColor;

	private ArrayList<Route> routes;
	private Set<Route> routeSet;

	/**
	 * Represents a connection between nodes and holds the routes between them
	 * */
	public DrawRoute(Route r, DrawNode node1, DrawNode node2){
		this.routeSelectedColor = new Color(254,0,0);
		this.routeSet = new HashSet<Route>();
		this.routeTaken = false;
		this.selected = false;
		this.routes = new ArrayList<Route>();
		this.routes.add(r);
		this.node2 = node2;
		this.node1 = node1;
	}

	public void routesToSet(){
		for(Route r : routes){
			routeSet.add(r);
		}
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
		routesToSet();//terrible idea
	}
	private boolean upper = true;

	public void draw(Graphics2D g){
		if(routeTaken){
			g.setColor(routeSelectedColor);
			int change = 5;
			int limit = 254;

			if(routeSelectedColor.getRed() >= 254 && upper){
				upper = false;
			}
			if(routeSelectedColor.getRed() <= 100 && !upper){
				upper = true;
			}

			if(upper)change = 3;
			else change = -3;

			routeSelectedColor = new Color(routeSelectedColor.getRed()+change,0,0);

		}
		else g.setColor(Color.BLACK);


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

		g.setStroke(new BasicStroke(5));
		if(selected)g.setColor(Color.RED);



		if(node1 !=null && node2 != null)g.drawLine((int)(node1.getX()+nodeSize),(int) (node1.getY()+nodeSize), (int)(node2.getX()+nodeSize), (int)(node2.getY()+nodeSize));

		//check if there is a route going to both nodes
		if(toNode1){
			List<Point> points =  CircleLine.getCircleLineIntersectionPoint(new Point((int)(node2X+nodeSize),(int) (node2Y+nodeSize)),
																			new Point((int)(node1X+nodeSize),(int) (node1Y+nodeSize)),
																			new Point((int)(node1X+nodeSize),(int) (node1Y+nodeSize)),nodeSize);


			g.setColor(Color.BLACK);
			g.drawOval(points.get(0).x-10,points.get(0).y-10, 20, 20);
			g.setColor(Color.WHITE);
			g.fillOval(points.get(0).x-10,points.get(0).y-10, 20, 20);
		}
		if(toNode2){//node 2 is the destination
			List<Point> points =  CircleLine.getCircleLineIntersectionPoint(new Point((int)(node1X+nodeSize),(int) (node1Y+nodeSize)),
																			new Point((int)(node2X+nodeSize),(int) (node2Y+nodeSize)),
																			new Point((int)(node2X+nodeSize), (int)(node2Y+nodeSize)),nodeSize);

			g.setColor(Color.BLACK);
			g.drawOval(points.get(0).x-10,points.get(0).y-10, 20, 20);
			g.setColor(Color.WHITE);
			g.fillOval(points.get(0).x-10,points.get(0).y-10, 20, 20);

		}

		if(selected)drawSelectedBox(g);
	}

	/**
	 * Draws the box that displays all the routes between the two nodes
	 * */
	public void drawSelectedBox(Graphics2D g){
		g.setColor(Color.BLACK);
		g.fillRect(20,20,600,100);
		g.setColor(Color.WHITE);
		g.fillRect(25,25,600-10,100-10);

		g.setColor(Color.BLACK);
		g.setFont(new Font(g.getFont().getFamily(), Font.BOLD, g.getFont().getSize()));

		int y = 30;
		for(Route r : routeSet){
			g.drawString(r.toString(), 30, y+10);
			y+=20;
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


	public void setTaken(boolean taken){
		this.routeTaken = taken;
	}

	public String getNode1Name(){
		return this.node1.getNode().getName();
	}

	public String getNode2Name(){
		return this.node2.getNode().getName();
	}

	public void setSelected(boolean selected){
		this.selected = selected;
	}

	/**
	 * Return if a point is inside the node
	 *
	 * @param point to test
	 * */
	public boolean containsPoint(double x, double y){
		return (int)pointToLineDistance(new Point((int)(node1.getX()+nodeRadius),(int)(node1.getY()+nodeRadius)),new Point((int)(node2.getX()+nodeRadius),(int)(node2.getY()+nodeRadius)), new Point((int)x,(int)y)) >= 0 &&
				(int)pointToLineDistance(new Point((int)(node1.getX()+nodeRadius),(int)(node1.getY()+nodeRadius)),new Point((int)(node2.getX()+nodeRadius),(int)(node2.getY()+nodeRadius)), new Point((int)x,(int)y)) <= 10;

	}

	 public double pointToLineDistance(Point A, Point B, Point P) {
		    double normalLength = Math.sqrt((B.x-A.x)*(B.x-A.x)+(B.y-A.y)*(B.y-A.y));
		    return Math.abs((P.x-A.x)*(B.y-A.y)-(P.y-A.y)*(B.x-A.x))/normalLength;
		  }
}
