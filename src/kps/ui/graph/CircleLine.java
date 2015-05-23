package kps.ui.graph;

import java.awt.Point;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;


/**
 * http://stackoverflow.com/questions/13053061/circle-line-intersection-points
 * */
public class CircleLine {

    public static List<Point> getCircleLineIntersectionPoint(Point pointA,Point pointB, Point center, double radius) {
        double baX = pointB.x - pointA.x;
        double baY = pointB.y - pointA.y;
        double caX = center.x - pointA.x;
        double caY = center.y - pointA.y;

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - radius * radius;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        if (disc < 0) {
            return Collections.emptyList();
        }

        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        
        
        Point p1 = new Point((int)(pointA.x - baX * abScalingFactor1),(int)( pointA.y- baY * abScalingFactor1));
        
        if (disc == 0) {
            return Collections.singletonList(p1);
        }
        Point p2 = new Point((int)(pointA.x - baX * abScalingFactor2),(int)( pointA.y- baY * abScalingFactor2));
        return Arrays.asList(p1, p2);
    }
}