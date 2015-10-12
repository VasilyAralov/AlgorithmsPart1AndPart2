import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;

public class KdTree {

 private static class Node {

  private Point2D p; // the point
  private RectHV rect; // the axis-aligned rectangle corresponding to this node
  private boolean orientation;
  private Node lb; // the left/bottom subtree
  private Node rt; // the right/top subtree
  private Node parent;

 }
 
 private class NearestNode {
 
  private Point2D original;
  private double minDist;
  private Point2D minPoint;
  
 }
 
 private Node root;
 private int size;

 public KdTree() {
  // construct an empty set of points
  root = null;
  size = 0;
 }

 public boolean isEmpty() {
  // is the set empty?
  return size == 0;
 }

 public int size() {
  // number of points in the set
  return size;
 }

 public void insert(Point2D p) {
  // add the point to the set (if it is not already in the set)
  if (p == null) {
   throw new NullPointerException();
  }
  Node node = new Node();
  node.p = p;
  node.parent = getParent(root, p);
  size++;
  if (node.parent == null) {
   root = node;
   node.rect = new RectHV(0, 0, 1, 1);
   node.orientation = false;
   return;
  }
  if (node.parent.p.compareTo(p) == 0) {
   size--;
   return;
  }
  node.orientation = !node.parent.orientation;
  if (node.parent.orientation) {
   if (chooseBottomUp(node.parent, p)) {
    node.rect = new RectHV(node.parent.rect.xmin(), node.parent.rect.ymin(), node.parent.rect.xmax(), node.parent.p.y());
   } else {
    node.rect = new RectHV(node.parent.rect.xmin(), node.parent.p.y(), node.parent.rect.xmax(), node.parent.rect.ymax());
   }
  } else {
   if (chooseLeftRight(node.parent, p)) {
    node.rect = new RectHV(node.parent.rect.xmin(), node.parent.rect.ymin(), node.parent.p.x(), node.parent.rect.ymax());
   } else {
    node.rect = new RectHV(node.parent.p.x(), node.parent.rect.ymin(), node.parent.rect.xmax(), node.parent.rect.ymax());
   }
  }
  if (getSide(node.parent, p)) {
   node.parent.lb = node;
  } else {
   node.parent.rt = node;
  }
 }
 
 //Left/Bottom - true
 //Right/Top - false
 private boolean getSide(Node node, Point2D p) {
  if (node.orientation) {
   return chooseBottomUp(node, p);
  } 
  return chooseLeftRight(node, p);
 }
 
 //Bottom - true
 //Top - false
 private boolean chooseBottomUp(Node node, Point2D p) {
  if (p.y() <= node.p.y()) {
   return true;
  }
  return false;
 }
 
 //Left - true
 //Right - false
 private boolean chooseLeftRight(Node node, Point2D p) {
  if (node.p.x() >= p.x()) {
   return true;
  }
  return false;
 }
 
 private Node getParent(Node node, Point2D p) {
  if (node == null) {
   return null;
  }
  if (node.p.compareTo((p)) == 0) {
   return node;
  }
  Node newNode;
  if (getSide(node, p)) {
   newNode = getParent(node.lb, p); 
   if (newNode == null) {
    return node;
   } else 
   return newNode;
  }
  newNode = getParent(node.rt, p);
  if (newNode == null) {
   return node;
  }
  return newNode;
 }
 

 public boolean contains(Point2D p) {
  // does the set contain point p?
  if (p == null) {
   throw new NullPointerException();
  }
  if (root == null) {
   return false;
  }
  return p.compareTo(getParent(root, p).p) == 0;
 }

 public void draw() {
  // draw all points to standard draw
  for (Point2D point : range(new RectHV(0, 0, 1, 1))) {
   point.draw();
  }
 }

 public Iterable<Point2D> range(RectHV rect) {
  // all points that are inside the rectangle
  return getPoints(root, rect);
 }

 private SET<Point2D> getPoints(Node node, RectHV rect) {
  SET<Point2D> set = new SET<Point2D>();
  if (node == null) {
   return set;
  }
  if (rect.contains(node.p)) {
   set.add(node.p);
  }
  if (!node.orientation) {
   if (rect.xmin() <= node.p.x()) {
    for (Point2D point : getPoints(node.lb, rect)) {
     set.add(point);
    }
   }
   if (rect.xmax() >= node.p.x()) {
    for (Point2D point : getPoints(node.rt, rect)) {
     set.add(point);
    }
   }
  } else {
   if (rect.ymin() <= node.p.y()) {
    for (Point2D point : getPoints(node.lb, rect)) {
     set.add(point);
    }
   }
   if (rect.ymax() >= node.p.y()) {
    for (Point2D point : getPoints(node.rt, rect)) {
     set.add(point);
    }
   }
  }
  return set;
 }

 public Point2D nearest(Point2D p) {
  // a nearest neighbor in the set to point p; null if the set is empty
  if (p == null) {
   throw new NullPointerException();
  }
  if (root == null) {
   return null;
  }
  NearestNode nearest = new NearestNode();
  nearest.original = p;
  nearest.minPoint = root.p;
  nearest.minDist = p.distanceSquaredTo(root.p);
  getNearest(nearest, root);
  return nearest.minPoint;
 }
 
 private void getNearest(NearestNode nearest, Node node) {
  if ((node == null) || (node.rect.distanceSquaredTo(nearest.original) > nearest.minDist)) {
   return;
  }
  double dist = node.p.distanceSquaredTo(nearest.original);
  if (dist < nearest.minDist) {
   nearest.minDist = dist;
   nearest.minPoint = node.p; 
  }
  if (getSide(node, nearest.original)) {
   getNearest(nearest, node.lb);
   getNearest(nearest, node.rt);
  } else {
   getNearest(nearest, node.rt);
   getNearest(nearest, node.lb);
  }
  return;
 }
 
}
