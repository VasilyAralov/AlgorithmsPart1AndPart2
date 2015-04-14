import java.util.Comparator;

public class Solver {

 private class SolveNode {
  
  private Board board;
  private int moves;
  private SolveNode prev;
  
  private SolveNode(Board b, int m, SolveNode p) {
   board = b;
   moves = m;
   prev = p;
  }
  
 }

 private class ByManhattan implements Comparator<SolveNode> {

  @Override
  public int compare(SolveNode o1, SolveNode o2) {
   return o1.board.manhattan() + o1.moves - o2.board.manhattan() - o2.moves;
  }
  
 }

 private class ByHamming implements Comparator<SolveNode> {

  @Override
  public int compare(SolveNode o1, SolveNode o2) {
   return o1.board.hamming() - o2.board.hamming();
  }
  
 }
 
 private boolean solvable = false;
 private SolveNode goal;
 private MinPQ<SolveNode> pq;
 private MinPQ<SolveNode> twin;
 
 /**
  * find a solution to the initial board (using the A* algorithm)
  * 
  * @param initial
  */
 public Solver(Board initial) {
  if (initial == null) {
   throw new NullPointerException();
  }
  pq = new MinPQ<SolveNode>(new ByManhattan());
  twin = new MinPQ<SolveNode>(new ByHamming());
  goal = new SolveNode(initial, 0, null);
  pq.insert(goal);
  twin.insert(new SolveNode(goal.board.twin(), 0, null));
  boolean solve = false;
  while (!solve) {
   solve = solve();
  }
 }
 
 private boolean solve() {
  SolveNode current = pq.delMin();
  if (current.board.isGoal()) {
   solvable = true;
   goal = current;
   return true;
  }
  for (Board neighbor : current.board.neighbors()) {
   if ((current.prev != null) && (neighbor.equals(current.prev.board))) {
    continue;
   }
   SolveNode node = new SolveNode(neighbor, current.moves + 1, current);
   pq.insert(node);
  }
  SolveNode twinNode = twin.delMin();
  if (twinNode.board.isGoal()) {
   return true;
  }
  twin.insert(new SolveNode(current.board.twin(), current.moves + 1, null));
  return false;
 }
 
 /**
  * @return is the initial board solvable?
  */
 public boolean isSolvable() {
  return solvable;
 }

 /**
  * @return min number of moves to solve initial board; -1 if no solution
  */
 public int moves() {
  if (!solvable) {
   return -1;
  }
  return goal.moves;
 }

 /**
  * @return sequence of boards in a shortest solution; null if no solution
  */
 public Iterable<Board> solution() {
  if (!solvable) {
   return null;
  }
  Stack<Board> solution = new Stack<Board>();
  SolveNode solve = goal;
  do {
   solution.push(solve.board);
   solve = solve.prev;
  } while (solve != null);
  return solution;
 }
 
}