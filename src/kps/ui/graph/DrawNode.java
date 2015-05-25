package kps.ui.graph;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import kps.data.Node;

public class DrawNode {
	private Node node;
	private boolean selected;
	private boolean routeSelected;
	private double size;

	private double x;
	private double y;

	Map<DrawNode, Integer> connections;

	public DrawNode(Node n, double x, double y){
		connections = new HashMap<DrawNode,Integer>();
		setSelected(false);
		this.setNode(n);
		setSize(80);
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
		if(isSelected())g.setColor(Color.RED);
		g.fillOval((int)getX(), (int)getY(), (int)getSize(), (int)getSize());

		//draw inside yellow
		g.setColor(Color.BLUE);
		if(routeSelected)g.setColor(Color.RED);
		g.fillOval((int)getX()+3, (int)getY()+3, (int)getSize()-6, (int)getSize()-6);

		//draw the name of the node
		g.setColor(Color.WHITE);
		g.drawString(getNode().getName(), (int)getX()+20, (int)(getY()+getSize()/2));
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
		this.y = y;
	}


	public Node getNode() {
		return node;
	}

	public void setRouteSelected(boolean selected){
		this.routeSelected = selected;
	}


	public void setNode(Node node) {
		this.node = node;
	}
}