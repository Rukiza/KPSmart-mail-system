package kps.ui.graph;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import kps.data.Route;

import com.sun.javafx.geom.Line2D;

/**
 * @author Nicky van Hulst
 * */
public class DrawRoute {

	//one of the nodes of the route
	private DrawNode node1;

	//the other node of the route
	private DrawNode node2;

	//whether the route is taken
	private boolean routeTaken;

	//whether the route is selected by the mouse
	private boolean selected;

	//the color when the route is selected
	private Color routeSelectedColor;

	//the list of routes between the two nodes
	private ArrayList<Route> routes;

	//the set of routes between the two nodes
	private Set<Route> routeSet;

	//color pulsing up
	private boolean upper = true;


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

	/**
	 * Converts the list of routes to a set of routes
	 * */
	private void routesToSet(){
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
			   (r.getSrc().equals(node2.getNode().getName())  || r.getSrc().equals(node1.getNode().getName()));
	}


	/**
	 * Adds a route to the list of routes
	 * */
	public void addRoute(Route r){
		if(r == null)return;
		this.routes.add(r);
		routesToSet();//terrible idea
	}


	/**
	 * Draws the route on the graphics object
	 *
	 * @param g the graphics to draw on
	 * */
	public void draw(Graphics2D g){
		if(routeTaken){
			g.setColor(routeSelectedColor);
			int change = 5;

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


		double nodeSize1 = node1.getSize()/2;
		double nodeSize2 = node2.getSize()/2;

		g.setStroke(new BasicStroke(5));
		if(selected)g.setColor(Color.RED);



		if(node1 !=null && node2 != null)g.drawLine((int)(node1.getX()+nodeSize1),(int) (node1.getY()+nodeSize1), (int)(node2.getX()+nodeSize2), (int)(node2.getY()+nodeSize2));

		//check if there is a route going to both nodes
		if(toNode1){
			List<Point> points =  CircleLine.getCircleLineIntersectionPoint(new Point((int)(node2X+nodeSize2),(int) (node2Y+nodeSize2)),
																			new Point((int)(node1X+nodeSize1),(int) (node1Y+nodeSize1)),
																			new Point((int)(node1X+nodeSize1),(int) (node1Y+nodeSize1)),nodeSize1);


			g.setColor(Color.BLACK);
			g.drawOval(points.get(0).x-10,points.get(0).y-10, 20, 20);
			g.setColor(Color.WHITE);
			g.fillOval(points.get(0).x-10,points.get(0).y-10, 20, 20);
		}
		if(toNode2){//node 2 is the destination
			List<Point> points =  CircleLine.getCircleLineIntersectionPoint(new Point((int)(node1X+nodeSize1),(int) (node1Y+nodeSize1)),
																			new Point((int)(node2X+nodeSize2),(int) (node2Y+nodeSize2)),
																			new Point((int)(node2X+nodeSize2), (int)(node2Y+nodeSize2)),nodeSize2);

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
	private void drawSelectedBox(Graphics2D g){
		int stringgap = 10;
		g.setColor(Color.BLACK);

		double stringwidth = widthOfBox(routeToStringList(routeSet),g);
		double stringheight = heightOfBox(routeToStringList(routeSet),g);
		double extragap = routeSet.size()*stringgap ; //+ endanStartBuf;

		if(routeSet.size() > 2)extragap -=2*stringgap;

		int boxWidth = (int)(stringwidth + 15);
		int boxHeight = (int)(stringheight + extragap);

		g.setColor(Color.BLACK);

		g.fillRect(20,20,boxWidth + 5,boxHeight + 5);
		g.setColor(Color.WHITE);
		g.fillRect(25,25,boxWidth-10+5,boxHeight-10+5);


		g.setColor(Color.BLACK);

		int y = 30;
		for(Route r : routeSet){
			g.drawString(r.toString(), 30, y+10);
			y+=20;
		}
	}


	/**
	 *Converts a set of routes to a list of strings
	 * */
	private List<String> routeToStringList(Set<Route> routes){
		List<String> strings = new ArrayList<String>();
		for(Route r : routes)strings.add(r.toString());
		return strings;
	}


	/**
	 * Figure out the height the box should be to hold the strings
	 * */
	private double heightOfBox(List<String> strings, Graphics2D g){
		double totalHeight = 0;

		FontRenderContext context = g.getFontRenderContext();
		Font font =  g.getFont();

		for(String s : strings){
			totalHeight += font.getStringBounds(s, context).getBounds().getHeight();
		}
		return totalHeight+ strings.size();
	}


	/**
	 * Figures out the width of the box to hold all the strings
	 * */
	private double widthOfBox(List<String> strings, Graphics2D g){
		double maxWidth = 0;

		FontRenderContext context = g.getFontRenderContext();
		Font font =  g.getFont();

		for(String s : strings){

			double width =  font.getStringBounds(s, context).getBounds().getWidth();
			if(maxWidth < width)maxWidth = width;
			}
		return maxWidth;
	}


	/**
	 * Sets the route taken by the mail
	 * */
	public void setTaken(boolean taken){
		this.routeTaken = taken;
	}


	/**
	 * returns the name of node1
	 * */
	public String getNode1Name(){
		return this.node1.getNode().getName();
	}


	/**
	 * Returns the name of node 2
	 * */
	public String getNode2Name(){
		return this.node2.getNode().getName();
	}


	/**
	 * Sets the route to be selected by the mouse
	 * */
	public void setSelected(boolean selected){
		this.selected = selected;
	}


	/**
	 * Return if a point is inside the node
	 *
	 * @param point to test
	 * */
	public boolean containsPoint(double x, double y){
		double nodeSize1 = node1.getSize();
		double nodeSize2 = node2.getSize();

		int boxX = (int) x - 5;
		int boxY = (int) y - 5;

		Line2D line = new Line2D((float)(node1.getX()+nodeSize1/2), (float)(node1.getY()+nodeSize1/2),(float)(node2.getX()+nodeSize2/2),(float)( node2.getY()+nodeSize2/2));

		return line.intersects(boxX,boxY,10,10);
	}
}
