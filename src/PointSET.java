import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.SET;
import edu.princeton.cs.algs4.StdDraw;

public class PointSET {

 private SET<Point2D> pointSet;

 public PointSET() {
  // construct an empty set of points
  pointSet = new SET<Point2D>();
 }

 public boolean isEmpty() {
  // is the set empty?
  return pointSet.size() == 0;
 }

 public int size() {
  // number of points in the set
  return pointSet.size();
 }

 public void insert(Point2D p) {
  // add the point to the set (if it is not already in the set)
  if (p == null) {
   throw new NullPointerException();
  }
  pointSet.add(p);
 }

 public boolean contains(Point2D p) {
  // does the set contain point p?
  if (p == null) {
   throw new NullPointerException();
  }
  return pointSet.contains(p);
 }

 public void draw() {
  // draw all points to standard draw
  for (Point2D point : pointSet) {
   StdDraw.point(point.x(), point.y());
  }
  StdDraw.show();
 }

 public Iterable<Point2D> range(RectHV rect) {
  // all points that are inside the rectangle
  if (rect == null) {
   throw new NullPointerException();
  }
  SET<Point2D> rangeSet = new SET<Point2D>();
  for (Point2D point : pointSet) {
   if (rect.contains(point)) {
    rangeSet.add(point);
   }
  }
  return rangeSet;
 }

 public Point2D nearest(Point2D p) {
  // a nearest neighbor in the set to point p; null if the set is empty
  if (pointSet.isEmpty()) {
   return null;
  }
  Point2D min = null;
  double minDistance = Double.POSITIVE_INFINITY;
  for (Point2D point : pointSet) {
   double distance = p.distanceSquaredTo(point);
   if (distance < minDistance) {
    min = point;
    minDistance = distance;
   }
  }
  return min;
 }

}
