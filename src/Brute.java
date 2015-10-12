import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Brute {
 
 public static void main(String[] args) {
  // rescale coordinates and turn on animation mode
  StdDraw.setXscale(0, 32768);
  StdDraw.setYscale(0, 32768);

  // read in the input
  // String filename = args[0];
  In in = new In(args[0]);
  int N = in.readInt();
  Point[] ap = new Point[N];
  for (int i = 0; i < N; i++) {
   int x = in.readInt();
   int y = in.readInt();
   Point p = new Point(x, y);
   ap[i] = p;
   p.draw();
  }
  if (N < 4) {
   return;
  }
  Arrays.sort(ap);
  for (int i = 0; i < N - 3; i++) {
   Point[] tap = Arrays.copyOfRange(ap, i + 1, ap.length);
   for (int j = 0; j < tap.length - 2; j++) {
    for (int k = j + 1; k < tap.length - 1; k++) {
     for (int l = k + 1; l < tap.length; l++) {
      if ((ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[k])) && (ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[l]))) {
       StdOut.println(ap[i] + " -> " + tap[j] + " -> " + tap[k] + " -> " + tap[l]);
       ap[i].drawTo(tap[l]);
      }
     }
    }
   }
  }
 }
 
}
