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
	
	public DrawRoute(Route r, DrawNode src, DrawNode dest){
		this.r = r;
		this.dest = dest;
		this.src = src;
	}
	
	public void draw(Graphics2D g){
		
		if(connectionsTo(src.getNode(), dest.getNode()) > 1){
			if(src !=null && dest != null){
				double deltaX = dest.getX() - src.getX();
				double deltaY = dest.getY() - src.getY();
				
				double width = Math.abs(dest.getX() - src.getX());
				
				double angle = Math.atan2(deltaY, deltaX);
				
				//QuadCurve2D qc = new QuadCurve2D(src.x,src.y,dest.x,dest.y,dest.x+deltaX,dest.y+deltaY);
				
				Path2D curve;
				curve = new Path2D.Double();
				curve.moveTo(src.getX()+src.getSize()/2, src.getY()+src.getSize()/2);
				curve.curveTo(src.getX()+40, src.getY()+40,  Math.abs(deltaY),Math.abs(deltaX), dest.getX()+40, dest.getY()+40);
				//curve.getPathIterator(arg0, arg1)
				
				g.draw(curve);
			}
		}
		else{
			if(src !=null && dest != null)g.drawLine((int)src.getX()+40,(int) src.getY()+40, (int)dest.getX()+40, (int)dest.getY()+40);
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
