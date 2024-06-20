import java.util.ArrayList;

public class BruteCollinearPoints {
    
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private ArrayList<Point> segmentsTest = new ArrayList<>();
    private int numberOfSegments = 0;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        
        // brute force
        for (int p = 0; p < points.length; p++) {
            
            for (int q = 0; q < points.length; q++) {
                double pqSlope = points[p].slopeTo(points[q]);
                
                for (int r = 0; r < points.length; r++) {
                    double prSlope = points[p].slopeTo(points[r]);
                    if (pqSlope != prSlope) { continue; }

                    for (int s = 0; s < points.length; s++) {
                        if (p == q && q == r && r == s) { continue; }
                        double psSlope = points[p].slopeTo(points[s]);
                        if (prSlope != psSlope) { continue; }

                        if (pqSlope == prSlope && prSlope == psSlope) {
                            
                            // ensure uniqueness of a line segment
                            if (points[p].compareTo(points[q]) < 0 && points[q].compareTo(points[r]) < 0 && points[r].compareTo(points[s]) < 0) {
                                LineSegment segment = new LineSegment(points[p], points[s]);
                                segments.add(segment);

                                segmentsTest.add(points[p]);
                                segmentsTest.add(points[q]);
                                segmentsTest.add(points[r]);
                                segmentsTest.add(points[s]);
                                segmentsTest.add(new Point(0, 0));

                                numberOfSegments++;
                            }
                        }
                    }
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
