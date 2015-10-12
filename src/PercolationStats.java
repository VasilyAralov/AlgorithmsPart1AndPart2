import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class PercolationStats {
 private double[] results;
 private double mean = 0;
 private double stddev = 0;

 public PercolationStats(int N, int T) {
  if ((N <= 0) || (T <= 0)) {
   throw new IllegalArgumentException();
  }
  results = new double[T];
  for (int t = 0; t < T; t++) {
   Percolation perc = new Percolation(N);
   int count = 0;
 do {
    int i, j;
    do {
     i = StdRandom.uniform(N) + 1;
     j = StdRandom.uniform(N) + 1;
    } while (perc.isOpen(i, j));
    perc.open(i, j);
    count++;
   } while (!perc.percolates());
   double treshold = (double) count/(N*N);
   results[t] = treshold; 
   mean += treshold;
  }
  mean = mean/T;
  for (int i = 0; i < T; i++) {
   double x = results[i] - mean();
   stddev += x * x;
  }
  stddev = Math.sqrt(stddev/(T - 1));
 }
 
 // sample mean of percolation threshold
    public double mean() {
     return mean;
    }
    public double stddev() {
     return stddev;
    }
    public double confidenceLo() {
     return mean() - 1.96 * stddev() / Math.sqrt(results.length);
    }
    public double confidenceHi() {
     return mean() + 1.96 * stddev() / Math.sqrt(results.length);
    }
    
 public static void main(String[] args) {
  PercolationStats ps = new PercolationStats(Integer.parseInt(args[0]), Integer.parseInt(args[1]));
  StdOut.println("mean = " + ps.mean());
  StdOut.println("stddev = " + ps.stddev());
  StdOut.println("95% confidence interval = " + ps.confidenceLo() + ", " + ps.confidenceHi());
 }

}