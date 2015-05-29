package kps.ui.graph;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.font.FontRenderContext;

import kps.data.Node;
/**
 * @author Nicky van Hulst
 * */
public class DrawNode {

	//node to draw
	private Node node;

	//selected by mouse
	private boolean selected;

	//part of the route selected
	private boolean routeSelected;

	//size of the node to draw
	private double size;

	//x location
	private double x;

	//y location
	private double y;

	//fields for pulsing color on the nodes
	private Color routeSelectedColor;

	//color pulsing
	private boolean upper;


	/**
	 * Constructor for DrawNode
	 * */
	public DrawNode(Node n, double x, double y){
		this.size = 40;
		this.upper = true;
		this.routeSelectedColor = new Color(255,0,0);
		this.selected = false;
		this.node = n;
		this.x = x;
		this.y = y;
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


	/**
	 * Draws the string centred in the node
	 *
	 * @param string to be drawn
	 *
	 * @param g graphics to draw it on
	 * */
	public void drawStringCentred(String s, Graphics2D g){
		FontRenderContext context = g.getFontRenderContext();
		Font font =  g.getFont();

	    double width =  font.getStringBounds(s, context).getBounds().getWidth();
	    double height =  font.getStringBounds(s, context).getBounds().getHeight();

	    Point cCentre = new Point((int)(this.x + (getSize()/2)),(int)( this.y + (getSize()/2)));

	    Point sCentre = new Point((int)(width/2),(int)(height/2));



	    g.drawString(s,(int)( cCentre.getX()-sCentre.getX()),(int)( cCentre.getY()+sCentre.getY()/2));
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


	/**
	 * Returns whether this node is selected
	 * */
	public boolean isSelected() {
		return selected;
	}

	/**
	 * Sets the node selected by the mouse
	 * */
	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	/**
	 * Resturns the size of the node double if selected
	 * */
	public double getSize() {
		if(selected)return size*2;
		return size;
	}

	/**
	 * Sets the size of the node
	 * */
	public void setSize(double size) {this.size = size;}

	/**
	 * Gets the x location
	 * */
	public double getX() {return x;}

	/**
	 * Sets the x Location
	 * */
	public void setX(double x) {this.x = x;}

	/**
	 * gets the y location
	 * */
	public double getY() {return y;}

	/**
	 * Sets the y location
	 * */
	public void setY(double y) {this.y = y;}

	/**
	 * gets the node
	 * */
	public Node getNode() {return node;}

	/**
	 * Sets the node as part of the route selected
	 * */
	public void setRouteSelected(boolean selected){this.routeSelected = selected;}





	@Override
	public boolean equals(Object other){
		if(other instanceof DrawNode){
			DrawNode node = (DrawNode)other;
			return node.getNode().equals(this.getNode());
		}
		return false;

	}
}