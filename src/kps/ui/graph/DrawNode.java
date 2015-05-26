package kps.ui.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import java.awt.*;
import java.awt.geom.*;
import java.awt.font.*;

import kps.data.Node;

public class DrawNode {
	private Node node;
	private boolean selected;
	private boolean routeSelected;
	private double size;

	private double x;
	private double y;

	//fields for pulsing color on the nodes
	private Color routeSelectedColor;
	private boolean upper;

	Map<DrawNode, Integer> connections;

	public DrawNode(Node n, double x, double y){
		this.size = 40;
		this.upper = true;
		this.routeSelectedColor = new Color(255,0,0);
		this.connections = new HashMap<DrawNode,Integer>();
		setSelected(false);
		this.setNode(n);
		this.setX(x);
		this.setY(y);
	}



	/**
	 * Draws the node on the graphics object
	 *
	 * @paran g Graphics object to draw on
	 * */
	public void draw(Graphics2D g){
		//draw outline black
		g.setColor(Color.WHITE);

		//if selected draw outline red
		if(isSelected()){
			g.setColor(Color.RED);
		}

		g.fillOval((int)getX(), (int)getY(), (int)getSize(), (int)getSize());

		//draw inside yellow
		g.setColor(Color.BLUE);
		if(routeSelected){
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

			g.setColor(routeSelectedColor);
		}
		g.fillOval((int)getX()+3, (int)getY()+3, (int)getSize()-6, (int)getSize()-6);

		//draw the name of the node
		g.setColor(Color.WHITE);
		g.setFont(new Font(g.getFont().getFamily(), Font.BOLD, g.getFont().getSize()));
		
		if(!selected)drawStringCentred(getNode().getName().substring(0, 3), g);
		else drawStringCentred(getNode().getName(), g);
	}
	
	public void drawStringCentred(String s, Graphics2D g){
		FontRenderContext context = g.getFontRenderContext();
		Font font =  g.getFont();
		
	    double width =  font.getStringBounds(s, context).getBounds().getWidth();
	    double height =  font.getStringBounds(s, context).getBounds().getHeight();
		
	    Point cCentre = new Point((int)(this.x + (getSize()/2)),(int)( this.y + (getSize()/2)));
	    
	    Point sCentre = new Point((int)(width/2),(int)(height/2));
	    	
	    
	    	
	    g.drawString(s,(int)( cCentre.getX()-sCentre.getX()),(int)( cCentre.getY()+sCentre.getY()/2));
	}

	public void addConnection(DrawNode destNode){
		if(connections.containsKey(destNode))connections.put(destNode, connections.get(destNode)+1);
		else connections.put(destNode, 1);
	}

	@Override
	public boolean equals(Object other){
		if(other instanceof DrawNode){
			DrawNode node = (DrawNode)other;
			return node.getNode().equals(this.getNode());
		}
		return false;

	}


	/**
	 * Return if a point is inside the node
	 *
	 * @param point to test
	 * */
	public boolean containsPoint(Point p){
		double dx = p.getX()- ( getX() + getSize()/2);
		double dy = p.getY()-(getY() + getSize()/2);

		return (dx*dx+dy*dy< getSize()*getSize()/4);
	}


	public boolean isSelected() {
		return selected;
	}


	public void setSelected(boolean selected) {
		this.selected = selected;
	}


	public double getSize() {
		if(selected)return size*2;
		return size;
	}


	public void setSize(double size) {
		this.size = size;
	}


	public double getX() {
		return x;
	}


	public void setX(double x) {
		this.x = x;
	}


	public double getY() {
		return y;
	}


	public void setY(double y) {
		this.y = y;}


	public Node getNode() {return node;}

	public void setRouteSelected(boolean selected){this.routeSelected = selected;}


	public void setNode(Node node) {this.node = node;}

}