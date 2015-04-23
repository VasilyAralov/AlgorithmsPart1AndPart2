public class SAP {
 
 private class Ancestor implements Comparable<Ancestor> {

  private int v;
  private int dist;
  
  private Ancestor(int v, Iterable<Integer> V, Iterable<Integer> W) {
   this.v = v;
   dist = new BreadthFirstDirectedPaths(graph, V).distTo(v) + new BreadthFirstDirectedPaths(graph, W).distTo(v);
  }
  
  private Ancestor(int v, int i, int j) {
   this.v = v;
   dist = new BreadthFirstDirectedPaths(graph, i).distTo(v) + new BreadthFirstDirectedPaths(graph, j).distTo(v);
  }

  @Override
  public int compareTo(Ancestor o) {
   if (dist > o.dist) {
    return 1;
   }
   if (dist < o.dist) {
    return -1;
   }
   return 0;
  }
  
 }
 
 private Digraph graph;
 
 // constructor takes a digraph (not necessarily a DAG)
 public SAP(Digraph G) {
  this.graph = new Digraph(G);
 }

 // length of shortest ancestral path between v and w; -1 if no such path
 public int length(int v, int w) {
  checkBounds(v);
  checkBounds(w);
  if (v == w) {
   return 0;
  }
  MinPQ<Ancestor> min = getMinEntry(v, w);
  if (min.isEmpty()) {
   return -1;
  }
  return min.delMin().dist;
 }

 private MinPQ<Ancestor> getMinEntry(int i, int j) {
  SET<Integer> parentV = new SET<Integer>();
  getParents(parentV, i);
  SET<Integer> parentW = new SET<Integer>();
  getParents(parentW, j);
  MinPQ<Ancestor> min = new MinPQ<Ancestor>();
  for (int entry : parentV) {
   if (parentW.contains(entry)) {
    Ancestor ancestor = new Ancestor(entry, i, j);
    min.insert(ancestor);
   }
  }
  return min;
  
 }
 
 private MinPQ<Ancestor> getMinEntry(Iterable<Integer> v, Iterable<Integer> w) {
  SET<Integer> parentV = new SET<Integer>();
  getParents(parentV, v);
  SET<Integer> parentW = new SET<Integer>();
  getParents(parentW, w);
  MinPQ<Ancestor> min = new MinPQ<Ancestor>();
  for (int entry : parentV) {
   if (parentW.contains(entry)) {
    Ancestor ancestor = new Ancestor(entry, v, w);
    min.insert(ancestor);
   }
  }
  return min;
 }
 
 private void getParents(SET<Integer> parents, int v) {
  if (parents.contains(v)) {
   return;
  }
  parents.add(v);
  for (int i : graph.adj(v)) {
   getParents(parents, i);
  }
 }

 private void getParents(SET<Integer> parents, Iterable<Integer> v) {
  for (int i : v) {
   getParents(parents, i);
  }
 }
 
 private void checkBounds(int v) {
  if ((v >= graph.V()) || (v < 0)) {
   throw new IndexOutOfBoundsException();
  }
 }
 
 private void checkNull(Object v) {
  if (v == null) {
   throw new NullPointerException();
  }
 }

 // a common ancestor of v and w that participates in a shortest ancestral path;
 // -1 if no such path
 public int ancestor(int v, int w) {
  checkBounds(v);
  checkBounds(w);
  MinPQ<Ancestor> min = getMinEntry(v, w);
  if (min.isEmpty()) {
   return -1;
  } 
  return min.delMin().v;
 }

 // length of shortest ancestral path between any vertex in v and any vertex in
 // w; -1 if no such path
 public int length(Iterable<Integer> v, Iterable<Integer> w) {
  checkNull(v);
  checkNull(w);
  MinPQ<Ancestor> min = getMinEntry(v, w);
  if (min.isEmpty()) {
   return -1;
  }
  return min.delMin().dist;
 }

 // a common ancestor that participates in shortest ancestral path; -1 if no
 // such path
 public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
  checkNull(v);
  checkNull(w);
  MinPQ<Ancestor> min = getMinEntry(v, w);
  if (min.isEmpty()) {
   return -1;
  }
  return min.delMin().v;
 }

 // do unit testing of this class
 public static void main(String[] args) {
  Digraph graph = new Digraph(new In("A://Temp//digraph5.txt"));
  SAP sap = new SAP(graph);
  /*for (int i = 0; i < 13; i++) {
   for (int j = 0; j < 13; j++) {
    StdOut.println(sap.length(i, j));    
   }
  }*/
  StdOut.println(sap.length(7, 12));
  StdOut.println(sap.ancestor(7, 12));
 }
}
