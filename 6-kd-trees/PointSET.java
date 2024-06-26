import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import java.util.TreeSet;
import java.util.LinkedList;

public class PointSET {
    
    private TreeSet<Point2D> bst = new TreeSet<>();
    
    // construct an empty set of points 
    public PointSET() {
        // nothing to do
    }

    // is the set empty? 
    public boolean isEmpty() {
        return bst.isEmpty();
    }

    // number of points in the set 
    public int size() {
        return bst.size();
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        bst.add(p);
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        return bst.contains(p);
    }
    
    // draw all points to standard draw 
    public void draw() {
        for (Point2D point : bst) {
            point.draw();
        }
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }

        LinkedList<Point2D> list = new LinkedList<>();
        for (Point2D point : bst) {
            if (rect.contains(point)) {
                list.add(point);
            }
        }
        return list;
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        double minDist = Double.POSITIVE_INFINITY;
        Point2D nearestPoint = p;   // return the argument point if bst isEmpty

        for (Point2D point : bst) {
            double distance = p.distanceTo(point);
            if (distance < minDist) {
                minDist = distance;
                nearestPoint = point;
            }
        }
        return nearestPoint;
    }
 
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
 }
 