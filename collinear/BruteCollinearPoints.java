import java.util.ArrayList;

public class BruteCollinearPoints {
    
    private ArrayList<LineSegment> segments = new ArrayList<>();
    private int numberOfSegments;

    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
        if (points == null) {
            throw new java.lang.IllegalArgumentException("the argument to the constructor is null");
        }

        for (int i = 0; i < points.length; i++) {
            if (points[i] == null) {
                throw new java.lang.IllegalArgumentException("a point in the array is null");
            }
            
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
                double qSlope = points[p].slopeTo(points[q]);
                
                for (int r = 0; r < points.length; r++) {
                    double rSlope = points[p].slopeTo(points[r]);
                    
                    for (int s = 0; s < points.length; s++) {
                        if (p == q && q == r && r == s) { continue; }
                        double sSlope = points[p].slopeTo(points[s]);
                        if (qSlope == rSlope && rSlope == sSlope) {
                            LineSegment segment = new LineSegment(points[p], points[s]);
                            segments.add(segment);
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
