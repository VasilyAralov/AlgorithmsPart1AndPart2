import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;

public class FastCollinearPoints {

 private Bag<LineSegment> bag;

 // finds all line segments containing 4 or more points
 public FastCollinearPoints(Point[] points) {
  Point[] ap = Arrays.copyOf(points, points.length);
  bag = new Bag<LineSegment>();
  Arrays.sort(ap);
  for (int i = 1; i < ap.length; i++) {
   if (ap[i].compareTo(ap[i - 1]) == 0) {
    throw new IllegalArgumentException();
   }
  }
  for (int i = 0; i < ap.length - 3; i++) {
   Point[] tap = Arrays.copyOf(ap, ap.length);
   Arrays.sort(tap, ap[i].slopeOrder());
   int j = 1;
   while (j < tap.length - 2) {
    int max = j + 2;
    while (
     (max < tap.length) && (ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[max]))
     ) {
     max++;
    }
    max--;
    if (max < j + 2) {
     j++;
     continue;
    }
    if (tap[j].compareTo(ap[i]) < 0) {
     j = ++max;
     continue;
    }
    bag.add(new LineSegment(ap[i], tap[max]));
    j = ++max;
   }
  }
 }
 
 //the number of line segments 
 public int numberOfSegments() {
  return bag.size();
 }
 
 public LineSegment[] segments() {
  LineSegment[] segments = new LineSegment[bag.size()];
  int i = 0;
  for (LineSegment segment : bag) {
   segments[i++] = segment;
  }
  return segments;
 }

}