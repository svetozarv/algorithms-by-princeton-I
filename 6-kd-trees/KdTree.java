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
        return this.size;
    }

    private Node createNode(Point2D p, boolean dimension) {
        Node newNode = new Node();
        newNode.point = p;
        newNode.dimension = dimension;
        this.size++;
        return newNode; 
    }

    // add the point to the set (if it is not already in the set)
    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }

        Node currNode = this.root;
        while (currNode != null) {
            if (currNode.point.equals(p)) return;
            if (currNode.dimension == false) {
                if (p.x() >= currNode.point.x()) {
                    if (currNode.right == null) {
                        currNode.right = createNode(p, false);
                        return;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    if (currNode.left == null){
                        currNode.left = createNode(p, false);
                        return;
                    } else {
                        currNode = currNode.left;
                    }
                }
            } else if (currNode.dimension == true) {
                if (p.y() >= currNode.point.y()) {
                    if (currNode.right == null) {
                        currNode.right = createNode(p, true);
                        return;
                    } else {
                        currNode = currNode.right;
                    }
                } else {
                    if (currNode.left == null) {
                        currNode.left = createNode(p, true);
                        return;
                    } else {
                        currNode = currNode.left;
                    }
                }
            }
        }

        if (this.root == null) {
            this.root = createNode(p, false);
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
                if (p.x() >= currNode.point.x()) {
                    currNode = currNode.right;
                } else {
                    currNode = currNode.left;
                }
            } else if (currNode.dimension == true) {
                if (p.y() >= currNode.point.y()) {
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
            double node_point_x = node.point.x();       // caching
            double rect_xmin = rect.xmin();
            double rect_xmax = rect.xmax();
            
            if (rect_xmin <= node_point_x && node_point_x <= rect_xmax) {       // if line intersects the rect, search both
                recursiveRange(rect, list, node.left);
                recursiveRange(rect, list, node.right);
            } else if (rect_xmin <= node_point_x && rect_xmax <= node_point_x) {  // if rect is on the left
                recursiveRange(rect, list, node.left);      // go left
            } else if (rect_xmin >= node_point_x && rect_xmax >= node_point_x) {
                recursiveRange(rect, list, node.right);     // go right
            }
            
        } else {
            double node_point_y = node.point.y();
            double rect_ymin = rect.ymin();
            double rect_ymax = rect.ymax();

            // if line intersects the rect, search both
            if (rect_ymin <= node_point_y && node_point_y <= rect_ymax) {
                recursiveRange(rect, list, node.left);
                recursiveRange(rect, list, node.right);
            } else if (rect_ymin <= node_point_y && rect_ymax <= node_point_y) {  // if rect is below
                // go down
                recursiveRange(rect, list, node.left); 
            } else if (rect_ymin >= node_point_y && node_point_y <= rect_ymax) {
                // go up
                recursiveRange(rect, list, node.right);
            }
        }
    }

    // a nearest neighbor in the set to point p; null if the set is empty\
    // similar to contains, searches for query point
    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException();
        }
        if (isEmpty()) return null;

        Point2D[] point = new Point2D[1];
        double[] radius = new double[1]; 
        radius[0] = root.point.distanceSquaredTo(p);

        nearestRecursive(p, root, radius, point);
        return point[0];
    }

    private void nearestRecursive(Point2D p, Node node, double[] radius, Point2D[] point) {
        
        if (node == null) return;
        double dist = node.point.distanceSquaredTo(p);
        if (dist <= radius[0]) { 
            radius[0] = dist;
            point[0] = node.point;
        }
        

        if (node.dimension == false) {
            if (p.x() < node.point.x()) {
                nearestRecursive(p, node.left, radius, point);         // check left first
                if (radius[0] > Math.pow(p.x() - node.point.x(), 2)) {
                    nearestRecursive(p, node.right, radius, point);        // check right
                }
            } else {
                nearestRecursive(p, node.right, radius, point);         // check right first
                if (radius[0] > Math.pow(p.x() - node.point.x(), 2)) {
                    nearestRecursive(p, node.left, radius, point);        // check left
                }
            }
        } else if (node.dimension == true) {
            if (p.y() < node.point.y()) {
                nearestRecursive(p, node.left, radius, point);         // check lower first
                if (radius[0] > Math.pow(p.y() - node.point.y(), 2)) {
                    nearestRecursive(p, node.right, radius, point);        // check upper
                }
            } else {
                nearestRecursive(p, node.right, radius, point);        // check upper first
                if (radius[0] > Math.pow(p.y() - node.point.y(), 2)) {
                    nearestRecursive(p, node.left, radius, point);         // check lower
                }
            }
        }
    }
 
    // unit testing of the methods (optional) 
    public static void main(String[] args) {

    }
 }
 