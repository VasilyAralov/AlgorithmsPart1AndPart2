import java.util.Arrays;
import java.util.Iterator;

public class Board {

 private class BoardIterator<Item> implements Iterator<Board> {

  private class Node {
   private Board item;
   private Node next;
  }
  
  private Node start = null;
  private int emptyI;
  private int emptyJ;
  
  private BoardIterator() {
   int empty = -1;
   for (int i = 0; i < Board.this.blocks.length; i++) {
    if (Board.this.blocks[i] == 0) {
     empty = i;
     break;
    }
   }
   emptyI = empty / N;
   emptyJ = empty % N;
   if (emptyI != 0) {
    createNeighbor(-1, 0);
   }
   if (emptyI != N - 1) {
    createNeighbor(1, 0);
   }
   if (emptyJ != 0) {
    createNeighbor(0, -1);
   }
   if (emptyJ != N - 1) {
    createNeighbor(0, 1);
   }
  }
  
  private void createNeighbor(int shiftX, int shiftY) {
   int[][] neighbor = new int[N][N];
   for (int i = 0; i < N; i++) {
    for (int j = 0; j < N; j++) {
     neighbor[i][j] = Board.this.blocks[i * N + j];
    }
   }
   int swap = neighbor[emptyI][emptyJ];
   neighbor[emptyI][emptyJ] = neighbor[emptyI + shiftX][emptyJ + shiftY];
   neighbor[emptyI + shiftX][emptyJ + shiftY] = swap;
   Node node = new Node();
   node.item = new Board(neighbor);
   node.next = start;
   start = node;
  }
  
  @Override
  public boolean hasNext() {
   return start != null;
  }

  @Override
  public Board next() {
   Board next = start.item;
   start = start.next;
   return next;
  }
  
  @Override
  public void remove() {
   throw new UnsupportedOperationException();
  }
  
 }

 private class BoardIterable<Item> implements Iterable<Board> {

  @Override
  public Iterator<Board> iterator() {
   return new BoardIterator<Board>();
  }
  
 }
 
 private int N;
 private int[] blocks;
 
 // construct a board from an N-by-N array of blocks
 //(where blocks[i][j] = block in row i, column j)
 public Board(int[][] blocks) {
  N = blocks.length;
  this.blocks = new int[N * N];
  for (int i = 0; i < N; i++) {
   for (int j = 0; j < N; j++) {
    this.blocks[i * N + j] = blocks[i][j];
   }
  }
 }
 
 // board dimension N
 public int dimension() {
  return N;
 }
 
 //number of blocks out of place
 public int hamming() {
  int n = 0;
  for (int i = 0; i < N * N - 1; i++) {
   if (blocks[i] != i + 1) {
    n++;
   }
  }
  return n;
 }
 
 //sum of Manhattan distances between blocks and goal
 public int manhattan() {
  int n = 0;
  for (int i = 0; i < N * N; i++) {
   if (blocks[i] == 0) {
    continue;
   }
   n += Math.abs((i / N) - ((blocks[i] - 1) / N)) + Math.abs((i % N - (blocks[i] - 1) % N));
  }
  return n;
 }
 
 // is this board the goal board?
 public boolean isGoal() {
  return hamming() == 0;
 }
 
 // a board that is obtained by exchanging two adjacent blocks in the same row
 public Board twin() {
  int[][] twinBoard = new int[N][N];
  int emptyJ = 0;
  for (int i = 0; i < N; i++) {
   for (int j = 0; j < N; j++) {
    twinBoard[i][j] = blocks[i * N + j];
    if (twinBoard[i][j] == 0) {
     emptyJ = j;
    }
   }
  }
  int i = N - 1;
  while (twinBoard[i][emptyJ] == 0) {
   i--;
  }
  int swap = twinBoard[i][emptyJ];
  if (emptyJ + 1 == N) {
   twinBoard[i][emptyJ] = twinBoard[i][emptyJ - 1];
   twinBoard[i][emptyJ - 1] = swap;
  } else {
   twinBoard[i][emptyJ] = twinBoard[i][emptyJ + 1];
   twinBoard[i][emptyJ + 1] = swap;
  }
  return new Board(twinBoard);
 }
 
 @Override
 public boolean equals(Object obj) {
  if (this == obj)
   return true;
  if (obj == null)
   return false;
  if (getClass() != obj.getClass())
   return false;
  Board other = (Board) obj;
  if (!Arrays.equals(blocks, other.blocks))
   return false;
  return true;
 }
 
 // all neighboring boards
 public Iterable<Board> neighbors() {
  return new BoardIterable<Board>();
 }
 
 //string representation of this board (in the output format specified below)
 @Override
 public String toString() {
  String maxLen = "" + blocks.length;
  String board = "" + N;
  int maxLength = maxLen.length();
  board += "\n";
  for (int i = 0; i < N; i++) {
   for (int j = 0; j < N; j++) {
    String digit = "" + blocks[i * N + j];
    while (digit.length() < maxLength) {
     digit = " " + digit;
    }
    board += digit + " ";
   }
   board += "\n";
  }
  return board;
 }

}