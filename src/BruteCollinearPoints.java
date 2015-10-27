import java.util.Arrays;

import edu.princeton.cs.algs4.Bag;

public class BruteCollinearPoints {

 private Bag<LineSegment> bag;
 
 // finds all line segments containing 4 points
 public BruteCollinearPoints(Point[] points) {
  if (points == null) {
   throw new NullPointerException();
  }
  Point[] ap = Arrays.copyOf(points, points.length);
  Arrays.sort(ap);
  if (ap[0] == null) {
   throw new NullPointerException();
  }
  for (int i = 1; i < ap.length; i++) {
   if (ap[i].compareTo(ap[i - 1]) == 0) {
    throw new IllegalArgumentException();
   }
  }
  bag = new Bag<LineSegment>();
  for (int i = 0; i < ap.length - 3; i++) {
   Point[] tap = Arrays.copyOfRange(ap, i + 1, ap.length);
   Arrays.sort(tap, ap[i].slopeOrder());
   for (int j = 0; j < tap.length - 2; j++) {
    for (int k = j + 1; k < tap.length - 1; k++) {
     for (int l = k + 1; l < tap.length; l++) {
      if (
       (ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[k])) && 
       (ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[l]))
       ) {
       Point[] line = new Point[]{ap[i], tap[j], tap[k], tap[l]};
       Arrays.sort(line);
       bag.add(new LineSegment(line[0], line[3]));
      }
     }
    }
   }
  } 
 }
 
 //the number of line segments
 public int numberOfSegments() {
  return bag.size();
 }
 
 //the line segments
 public LineSegment[] segments() {
  LineSegment[] segments = new LineSegment[bag.size()];
  int i = 0;
  for (LineSegment segment : bag) {
   segments[i++] = segment;
  }
  return segments;
 }
 
}