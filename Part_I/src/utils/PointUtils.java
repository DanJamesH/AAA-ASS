package utils;

import java.awt.Point;

public class PointUtils {
    
    // calculate the euclidean distance between points
    public static double distance( Point A, Point B ) {
        return Math.sqrt( Math.pow( A.x - B.x, 2 ) + Math.pow( A.y - B.y, 2 ) );
    }
    
}