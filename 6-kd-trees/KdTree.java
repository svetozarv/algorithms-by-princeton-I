import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;

public class KdTree {
    
    private Node root;
    private int size = 0;

    private class Node {
        Point2D point;
        boolean dimension;  // false for x, true for y
        Node left;
        Node right;
    }

    // construct an empty set of points 
    public KdTree() {
        // nothing to do
    }

    // is the set empty? 
    public boolean isEmpty() {
        return root == null;
    }

    // number of points in the set 
    public int size() {
        return size;
    }


    private Node createNode(Point2D p, boolean dimension) {
        Node newNode = new Node();
        newNode.point = p;
        newNode.dimension = dimension;
        return newNode; 
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node currNode = this.root;
        while (currNode != null) {   
            if (currNode.dimension == false) {
                if (p.x() > currNode.point.x()) {
                    if (currNode.right == null) {
                        currNode.right = createNode(p, true);
                        this.size++;
                        return;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    if (currNode.left == null){
                        currNode.left = createNode(p, true);
                        this.size++;
                        return;
                    } else {
                        currNode = currNode.left;
                    }
                }
                
            } else if (currNode.dimension == true) {
                if (p.y() > currNode.point.y()) {
                    if (currNode.right == null) {
                        currNode.right = createNode(p, true);
                        this.size++;
                        return;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    if (currNode.left == null) {
                        currNode.left = createNode(p, true);
                        this.size++;
                        return;
                    } else {
                        currNode = currNode.left;
                    }
                }
            }
        }
    }

    // does the set contain point p? 
    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node currNode = root;
        while (currNode != null) {   
            if (p.compareTo(currNode.point) == 0) return true;
            if (currNode.dimension == false) {
                if (p.x() > currNode.point.x()) {
                    currNode = currNode.right;
                } else {
                    currNode = currNode.left;
                }
            } else if (currNode.dimension == true) {
                if (p.y() > currNode.point.y()) {
                    currNode = currNode.right;
                } else {
                    currNode = currNode.left;
                }
            }
        }
        return false;
    }
    
    private void drawChilds(Node node) {
        if (node.left != null) {
            node.left.point.draw();
            drawChilds(node.left);
        }
        if (node.right != null) {
            node.right.point.draw();
            drawChilds(node.right);
        }
    }
    
    // draw all points to standard draw 
    public void draw() {
        root.point.draw();
        drawChilds(root);
    }
    
    // all points that are inside the rectangle (or on the boundary) 
    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException();
        }
    }
    
    // a nearest neighbor in the set to point p; null if the set is empty 
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
    }
 
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
 }
 