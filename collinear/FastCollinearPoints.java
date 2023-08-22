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
            Point currentPoint = points[i];
            ArrayList<Point> pointsSet = new ArrayList<>();
            for (int j = 0; j < points.length; j++) {
                if (i == j) { continue; }
                pointsSet.add(points[j]);
            }
            pointsSet.sort(currentPoint.slopeOrder());
            
            ArrayList<Point> collinearPoints = new ArrayList<>();
            int leftPointer = 0;
            int numOfDotsWithEqualSlopes = 0;
            int lastCollPointPointer = -1;
            for (int rightPointer = 1; rightPointer < pointsSet.size(); rightPointer++) {
                Point pLeft = pointsSet.get(leftPointer);
                Point pRight = pointsSet.get(rightPointer);
                double slopeLeft = currentPoint.slopeTo(pLeft);
                double slopeRight = currentPoint.slopeTo(pRight);
                if (slopeLeft == slopeRight) {
                    numOfDotsWithEqualSlopes = 2;
                    for (int j = rightPointer+1; j < pointsSet.size(); j++) {
                        Point p = pointsSet.get(j);
                        double slope = currentPoint.slopeTo(p);
                        if (slope == slopeRight) {
                            numOfDotsWithEqualSlopes++;
                        } else {
                            lastCollPointPointer = j-1;
                            break;
                        }
                    }
                    
                    for (int j = leftPointer; j <= lastCollPointPointer; j++) {
                        collinearPoints.add(pointsSet.get(j));
                    }
                    
                    leftPointer = lastCollPointPointer;
                    rightPointer = lastCollPointPointer + 1;

                    collinearPoints.add(currentPoint);
                    if (collinearPoints.size() > 4  &&  numOfDotsWithEqualSlopes > 3) {
                        collinearPoints.sort(null);
                        LineSegment segment = new LineSegment(collinearPoints.get(0), collinearPoints.get(collinearPoints.size() - 1));
                        segments.add(segment);
                        numberOfSegments++;
                    }

                } else {
                    leftPointer++;
                }
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
