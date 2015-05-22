package kps.ui.graph;

import java.awt.*;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.AffineTransform;
import java.awt.geom.*;
import java.util.ArrayList;


import kps.data.Node;
import kps.data.Route;

public class DrawRoute {
	
	private Route r;
	private DrawNode src;
	private DrawNode dest;
	
	private ArrayList<Route> routes; 
	
	public DrawRoute(Route r, DrawNode src, DrawNode dest){
		routes = new ArrayList<Route>();
		this.r = r;
		this.dest = dest;
		this.src = src;
	}
	
	/**
	 * Returns whether a route should be added to a drawNode
	 * */
	public boolean shouldAddRoute(Route r){
		return (r.getDest().equals(dest.getNode().getName()) || r.getDest().equals(src.getNode().getName()))
			   &&
			   (r.getSrc().equals(dest.getNode().getName()) || r.getSrc().equals(src.getNode().getName()));
			   
	}
	
	public void addRoute(Route r){
		this.routes.add(r);
	}
	
	public void draw(Graphics2D g){
		
		//new start 
		boolean toDest = false;
		boolean toSrc = false;
		for(Route r : routes){
			if(r.getDest().equals(dest.getNode().getName()))toDest = true;
			if(r.getDest().equals(src.getNode().getName()))toSrc = true;
		}
		
		double srcX = src.getX();
		double srcY = src.getY();
		
		double destX = dest.getX();
		double destY = dest.getY();
		
		
		double nodeSize = 80/2;
		double gradiant = ( src.getY()+nodeSize-dest.getY()+nodeSize)/(src.getX()+nodeSize - dest.getX()+nodeSize);
		double distance =  Math.sqrt((src.getX()+nodeSize-dest.getX()+nodeSize)*(src.getX()+nodeSize-(int)dest.getX()+nodeSize) + ( src.getY()+nodeSize-dest.getY()+nodeSize)*( src.getY()+nodeSize-(int)dest.getY()+nodeSize));
		
		double cpp = gradiant/distance;
		
		if(src !=null && dest != null)g.drawLine((int)src.getX()+40,(int) src.getY()+40, (int)dest.getX()+40, (int)dest.getY()+40);
		//bidirectional
		if(toDest && toSrc){
			g.fillRect((int)src.getX()-50,(int)src.getY()-40, 10, 10);
			g.fillRect((int)dest.getX()-50,(int)dest.getY()-40, 10, 10);
		}
		else if(toDest){
			System.out.println("To Dest");
			double moveX = nodeSize;
			double moveY = nodeSize*cpp;
			
			if(srcX < destX)moveX *= -1;
			if(srcY < destY)moveY *= -1;
			
			
			g.fillRect((int)(dest.getX()+moveX),(int)(dest.getY()+moveY), 10, 10);
		}
		else{
			double moveX = nodeSize;
			double moveY = nodeSize*cpp;
			
			if(destX < srcX)moveX *= -1;
			if(destY < srcY)moveY *= -1;
			
			
			g.fillRect((int)(src.getX()+moveX),(int)(src.getY()+moveY), 10, 10);
		}
		
		
		//new end 
		
		
//		
//		
//		
//		if(connectionsTo(src.getNode(), dest.getNode()) > 1){
//			if(src !=null && dest != null){
//				double deltaX = dest.getX() - src.getX();
//				double deltaY = dest.getY() - src.getY();
//				
//				double width = Math.abs(dest.getX() - src.getX());
//				
//				double angle = Math.atan2(deltaY, deltaX);
//				
//				//QuadCurve2D qc = new QuadCurve2D(src.x,src.y,dest.x,dest.y,dest.x+deltaX,dest.y+deltaY);
//				
//				Path2D curve;
//				curve = new Path2D.Double();
//				curve.moveTo(src.getX()+src.getSize()/2, src.getY()+src.getSize()/2);
//				curve.curveTo(src.getX()+40, src.getY()+40,  Math.abs(deltaY),Math.abs(deltaX), dest.getX()+40, dest.getY()+40);
//				//curve.getPathIterator(arg0, arg1)
//				
//				g.draw(curve);
//			}
//		}
//		else{
//			if(src !=null && dest != null)g.drawLine((int)src.getX()+40,(int) src.getY()+40, (int)dest.getX()+40, (int)dest.getY()+40);
//		}	
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
