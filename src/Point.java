import java.util.Comparator;

import edu.princeton.cs.algs4.StdDraw;

public class Point implements Comparable<Point> {

 // compare points by slope

 // x coordinate
 private final int x;
 // y coordinate
 private final int y;

 private class PointComparator<Item> implements Comparator<Point> {
  
  @Override
  public int compare(Point p1, Point p2) {
   if (Point.this.slopeTo(p1) < Point.this.slopeTo(p2)) {
    return -1;
   }
   if (Point.this.slopeTo(p1) > Point.this.slopeTo(p2)) {
    return 1;
   }
   return 0;
  }
  
 }
 
 // create the point (x, y)
 public Point(int x, int y) {
  this.x = x;
  this.y = y;
 }
 
 // plot this point to standard drawing
 public void draw() {
  StdDraw.point(x, y);
  StdDraw.show();
 }
 
 // draw line between this point and that point to standard drawing
 public void drawTo(Point that) {
  StdDraw.line(this.x, this.y, that.x, that.y);
  StdDraw.show();
 }

 // slope between this point and that point
 public double slopeTo(Point that) {
  if (that.x == this.x) {
   if (that.y == this.y) {
    return Double.NEGATIVE_INFINITY;
   }
   return Double.POSITIVE_INFINITY;
  }
  if (that.y == this.y) { 
   if (that.x > this.x) {
    return +0;
   }
   return -0;
  }
  return ((double) (that.y - this.y))/(that.x - this.x);
 }

    // is this point lexicographically smaller than that one?
    // comparing y-coordinates and breaking ties by x-coordinates
 public int compareTo(Point that) {
  if (this.y < that.y) {
   return -1;
  }
  if (this.y > that.y) {
   return 1;
  }
  if (this.x < that.x) {
   return -1;
  }
  if (this.x > that.x) {
   return 1;
  }
  return 0;
 }
 
 // compare two points by slopes they make with this point
 public Comparator<Point> slopeOrder() {
  return new PointComparator<Point>();
 }

 // return string representation of this point
 @Override
 public String toString() {
  return "(" + x + ", " + y + ")";
 }

}