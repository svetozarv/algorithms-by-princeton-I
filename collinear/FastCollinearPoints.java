import java.util.ArrayList;
public class FastCollinearPoints {
    
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numberOfSegments = 0;
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("The argument to the constructor is null");
        }
        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("A point in the array is null");
            }
        }
        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    if (i == j) { continue; }
                    throw new java.lang.IllegalArgumentException("The argument to the constructor contains a repeated point: " + points[j]);
                }
            }
        }


        for (int i = 0; i < points.length; i++) {
            Point originPoint = points[i];
            ArrayList<Point> pointsSet = new ArrayList<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) { continue; }
                pointsSet.add(points[j]);
            }
            pointsSet.sort(originPoint.slopeOrder());
            
            ArrayList<Point> collinearPoints = new ArrayList<>();
            findAdjacentCollPoints(originPoint, pointsSet, collinearPoints);

            collinearPoints.add(originPoint);
            collinearPoints.sort(null);
            if (collinearPoints.size() >= 4  &&  originPoint.compareTo(collinearPoints.get(0)) == 0) {
                addLineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1));
            }
        }
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    // the line segments
    public LineSegment[] segments() {
        int size = segments.size();
        LineSegment[] segmentsArray = new LineSegment[size];
        for (int i = 0; i < size; i++) {
            segmentsArray[i] = segments.get(i);
        }
        return segmentsArray;
    }

    // simply create a line segment
    private void addLineSegment(Point a, Point b) {
        LineSegment segment = new LineSegment(a, b);
        segments.add(segment);
        numberOfSegments++;
    }

    // find points with equal slopes in pointsSet and store them in collinearPoints
    private void findAdjacentCollPoints(Point originPoint, ArrayList<Point> pointsSet, ArrayList<Point> collinearPoints) {
        boolean foundCollPoints = false;
        int lastAddedPointer = -1;
        int leftPointer = 0;
        for (int rightPointer = 1; rightPointer < pointsSet.size(); rightPointer++) {
            Point pLeft = pointsSet.get(leftPointer);
            Point pRight = pointsSet.get(rightPointer);
            double slopeLeft = originPoint.slopeTo(pLeft);
            double slopeRight = originPoint.slopeTo(pRight);
            if (slopeLeft == slopeRight) {
                if (lastAddedPointer != leftPointer) {
                    collinearPoints.add(pointsSet.get(leftPointer));
                }
                collinearPoints.add(pointsSet.get(rightPointer));
                lastAddedPointer = rightPointer;
                foundCollPoints = true;
            } else { 
                if (foundCollPoints) { break; }
            }
            leftPointer++;
        }
    }
}