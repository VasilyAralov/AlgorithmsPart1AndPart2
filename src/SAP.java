public class SAP {
 
 private class Ancestor implements Comparable<Ancestor> {
  private int v;
  private int dist;
  
  private Ancestor(BreadthFirstDirectedPaths v, BreadthFirstDirectedPaths w, int a) {
   this.v = a;
   dist = v.distTo(a) + w.distTo(a);
  }

  public Ancestor() {
   v = -1;
   dist = -1;
  }

  @Override
  public int compareTo(Ancestor o) {
   if (this.dist > o.dist) {
    return 1;
   }
   if (this.dist < o.dist) {
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
  return getMinEntry(v, w).dist;
 }

 private Ancestor getMinEntry(int v, int w) {
  SET<Integer> parentV = new SET<Integer>();
  getParents(parentV, v);
  SET<Integer> parentW = new SET<Integer>();
  getParents(parentW, w);
  BreadthFirstDirectedPaths fromV = new BreadthFirstDirectedPaths(graph, v);
  BreadthFirstDirectedPaths fromW = new BreadthFirstDirectedPaths(graph, w);
  return getMinAncestor(parentV, parentW, fromV, fromW); 
 }
 
 private Ancestor getMinAncestor(SET<Integer> parentV, SET<Integer> parentW, BreadthFirstDirectedPaths fromV, BreadthFirstDirectedPaths fromW) {
  MinPQ<Ancestor> min = new MinPQ<Ancestor>();
  for (int entry : parentV) {
   if (parentW.contains(entry)) {
    Ancestor ancestor = new Ancestor(fromV, fromW, entry);
    min.insert(ancestor);
   }
  }
  if (min.isEmpty()) {
   return new Ancestor();
  }
  return min.delMin(); 
 }
 
 private Ancestor getMinEntry(Iterable<Integer> v, Iterable<Integer> w) {
  SET<Integer> parentV = new SET<Integer>();
  getParents(parentV, v);
  SET<Integer> parentW = new SET<Integer>();
  getParents(parentW, w);
  BreadthFirstDirectedPaths fromV = new BreadthFirstDirectedPaths(graph, v);
  BreadthFirstDirectedPaths fromW = new BreadthFirstDirectedPaths(graph, w);
  return getMinAncestor(parentV, parentW, fromV, fromW);
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
  return getMinEntry(v, w).v;
 }

 // length of shortest ancestral path between any vertex in v and any vertex in
 // w; -1 if no such path
 public int length(Iterable<Integer> v, Iterable<Integer> w) {
  checkNull(v);
  checkNull(w);
  return getMinEntry(v, w).dist;
 }

 // a common ancestor that participates in shortest ancestral path; -1 if no
 // such path
 public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
  checkNull(v);
  checkNull(w);
  Ancestor ancestor = getMinEntry(v, w);
  return ancestor.v;
 }

 // do unit testing of this class
 public static void main(String[] args) {
  Digraph graph = new Digraph(new In("A://Temp//digraph2.txt"));
  SAP sap = new SAP(graph);
  /*for (int i = 0; i < 13; i++) {
   for (int j = 0; j < 13; j++) {
    StdOut.println(sap.length(i, j));    
   }
  }*/
  StdOut.println(sap.length(2, 3));
  StdOut.println(sap.ancestor(2, 3));
  StdOut.println(sap.length(2, 3));
  StdOut.println(sap.ancestor(2, 3));
 }
}
