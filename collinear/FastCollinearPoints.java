import java.util.ArrayList;

public class FastCollinearPoints {
    
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numberOfSegments = 0;
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("the argument to the constructor is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("a point in the array is null");
            }
        }

        for (int i = 0; i < points.length; i++) {
            for (int j = 0; j < points.length; j++) {
                if (points[i].compareTo(points[j]) == 0) {
                    if (i == j) { continue; }
                    throw new java.lang.IllegalArgumentException("the argument to the constructor contains a repeated point: " + points[j]);
                }
            }
        }

        for (int i = 0; i < points.length; i++) {
            ArrayList<Point> pointsSet = new ArrayList<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) { continue; }
                pointsSet.add(points[j]);
            }
            pointsSet.sort(points[i].slopeOrder());
            
            ArrayList<Point> collinearPoints = new ArrayList<>();
            int lastAddedPointer = -1;
            int leftPointer = 0;
            for (int rightPointer = 1; rightPointer < pointsSet.size(); rightPointer++) {
                Point pLeft = pointsSet.get(leftPointer);
                Point pRight = pointsSet.get(rightPointer);
                double slopeLeft = points[i].slopeTo(pLeft);
                double slopeRight = points[i].slopeTo(pRight);
                if (slopeLeft == slopeRight) {
                    if (lastAddedPointer != leftPointer) {
                        collinearPoints.add(pointsSet.get(leftPointer));
                    }
                    collinearPoints.add(pointsSet.get(rightPointer));
                    lastAddedPointer = rightPointer;
                }
                leftPointer++;
            }

            collinearPoints.add(points[i]);
            if (collinearPoints.size() > 4) {
                collinearPoints.sort(null);
                LineSegment segment = new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1));
                segments.add(segment);
                numberOfSegments++;
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
}
