import java.util.LinkedList;

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
                        currNode.right = createNode(p, false);
                        this.size++;
                        return;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    if (currNode.left == null) {
                        currNode.left = createNode(p, false);
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
        LinkedList<Point2D> list = new LinkedList<>();
        recursiveRange(rect, list, root);
        return list;
    }
    
    private void recursiveRange(RectHV rect, LinkedList<Point2D> list, Node node) {
        if (node == null) return;
        if (rect.contains(node.point)) {
            list.add(node.point);
        }
        if (node.dimension == false) {

            // if line intersects the rect, search both
            if (rect.xmin() < node.point.x() && node.point.x() < rect.xmax()) {
                recursiveRange(rect, list, node.left);
                recursiveRange(rect, list, node.right);
            } else if (rect.xmax() < node.point.x()) {  // if rect is on the left
                // go left
                recursiveRange(rect, list, node.left); 
            } else if (rect.xmin() > node.point.x()) {
                // go right
                recursiveRange(rect, list, node.right);
            }
            
        } else {
            // if line intersects the rect, search both
            if (rect.ymin() < node.point.y() && node.point.y() < rect.ymax()) {
                recursiveRange(rect, list, node.left);
                recursiveRange(rect, list, node.right);
            } else if (rect.ymax() < node.point.y()) {  // if rect is below
                // go down
                recursiveRange(rect, list, node.left); 
            } else if (rect.ymin() > node.point.y()) {
                // go up
                recursiveRange(rect, list, node.right);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty\
    // similar to contains, searches for query point
    public Point2D nearest(Point2D p) {
        if (p == null || this.isEmpty()) {
            throw new IllegalArgumentException();
        }

        double minDist = root.point.distanceTo(p);
        Point2D[] array = new Point2D[1];

        nearestRecursive(p, root, minDist, array);
        return array[0];
    }


    private void nearestRecursive(Point2D p, Node node, double minDist, Point2D[] array) {
        
        if (node == null) return;
        double dist = node.point.distanceTo(p);
        if (dist <= minDist) { 
            minDist = dist;
            array[0] = node.point;
        }

        // 
        if (node.dimension == false) {
            if (p.x() < node.point.x()) {
                nearestRecursive(p, node.left, minDist, array);         // check left first
                nearestRecursive(p, node.right, minDist, array);        // check right
            }
        } else if (node.dimension == true) {
            if (p.y() < node.point.y()) {
                nearestRecursive(p, node.left, minDist, array);         // check lower first
                nearestRecursive(p, node.right, minDist, array);        // check upper
            } else {
                nearestRecursive(p, node.right, minDist, array);        // check upper first
                nearestRecursive(p, node.left, minDist, array);         // check lower
            }
        }
    }
 
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
 }
 