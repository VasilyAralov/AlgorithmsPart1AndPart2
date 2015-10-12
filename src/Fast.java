import java.util.Arrays;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

public class Fast {

 public static void main(String[] args) {
  // rescale coordinates and turn on animation mode
  StdDraw.setXscale(0, 32768);
  StdDraw.setYscale(0, 32768);
  StdDraw.show(0);
  StdDraw.setPenRadius(0.01); // make the points a bit larger

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
  Arrays.sort(ap);
  for (int i = 0; i < ap.length - 3; i++) {
   Point[] tap = Arrays.copyOf(ap, ap.length);
   Arrays.sort(tap, ap[i].SLOPE_ORDER);
   int j = 1;
   while (j < tap.length - 2) {
    int max = j + 2;
    while ((max < tap.length) && (ap[i].slopeTo(tap[j]) == ap[i].slopeTo(tap[max]))) {
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
    StdOut.print(ap[i] + " -> ");
    for (int k = j; k < max; k++) {
     StdOut.print(tap[k] + " -> ");
    }
    StdOut.println(tap[max]);
    ap[i].drawTo(tap[max]);
    j = ++max;
   }
  }
 }
}
