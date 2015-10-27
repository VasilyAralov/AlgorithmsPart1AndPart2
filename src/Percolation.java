import edu.princeton.cs.algs4.WeightedQuickUnionUF;

public class Percolation {

 private boolean[][] grid;
 private WeightedQuickUnionUF ufperc;
 private WeightedQuickUnionUF uffull;
 
 public Percolation(int N) {
  if (N <= 0) {
   throw new IllegalArgumentException();
  }
//Create Percolation Grid NxN  
  grid = new boolean[N][N];
//Fill grid  
  for (int i = 0; i < N; i++) {
   for (int j = 0; j < N; j++) {
    grid[i][j] = false;
   }
  }
//Create Weighted Quick union find NxN + 2 virtual points to grid   
  ufperc = new WeightedQuickUnionUF(N * N + 2);
  uffull = new WeightedQuickUnionUF(N * N + 1);
//Connect top and bottom rows to virtual points
  for (int j = 1; j <= N; j++) {
   ufperc.union(0, j);
   uffull.union(0, j);
   ufperc.union(N * N + 1, N * N + 1 - j);
  }
 }
   
 public void open(int i, int j) {
  checkRange(i, j);
  int ufN = getUfN(i, j);
  if (grid[i - 1][j - 1]) {
   return;
  }
//Open point  
  grid[i - 1][j - 1] = true;
//Check open/close upper point
  if ((i != 1) && (isOpen(i - 1, j))) {
   ufperc.union(ufN, (i - 2) * getSize() + j);
   uffull.union(ufN, (i - 2) * getSize() + j);
  }
//Check open/close under point  
  if ((i != getSize()) && (isOpen(i + 1, j))) {
   ufperc.union(ufN, i * getSize() + j);  
   uffull.union(ufN, i * getSize() + j);
  }
//Check open/close left point
  if ((j != 1) && (isOpen(i, j - 1))) {
   ufperc.union(ufN, (i - 1) * getSize() + j - 1);
   uffull.union(ufN, (i - 1) * getSize() + j - 1);
  }
//Check open/close right point  
  if ((j != getSize()) && (isOpen(i, j + 1))) {
   ufperc.union(ufN, (i - 1) * getSize() + j + 1);
   uffull.union(ufN, (i - 1) * getSize() + j + 1);
  }
 }
   
 public boolean isOpen(int i, int j) {
  checkRange(i, j);
  return grid[i - 1][j - 1];
 }
   
 public boolean isFull(int i, int j) {
  checkRange(i, j);
  return (uffull.connected(0, getUfN(i, j)) && isOpen(i, j));
 }
   
 public boolean percolates() 
 {
  if (getSize() == 1) {
   return grid[0][0];
  }
  return ufperc.connected(0, getSize() * getSize() + 1);
 }

 private int getUfN(int i, int j) {
  return (i - 1) * getSize() + j;
 }
 
 private void checkRange(int i, int j) {
  if ((i <= 0) || (i > getSize()) || (j <= 0) || (j > getSize())) {
   throw new IndexOutOfBoundsException();
  }
 }
 
 private int getSize() {
  return grid.length;
 }

}