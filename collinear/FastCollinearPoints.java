public class FastCollinearPoints {
    
    private LineSegment[] segments = new LineSegment[10];
    private int numberOfSegments;
    
    // finds all line segments containing 4 or more points
    public FastCollinearPoints(Point[] points) {
        // todo
    }
    
    // the number of line segments
    public int numberOfSegments() {
        return numberOfSegments;
    }
    
    // the line segments
    public LineSegment[] segments() {
        return segments;
    }
}
