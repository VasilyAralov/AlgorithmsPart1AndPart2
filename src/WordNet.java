import java.util.HashMap;

import edu.princeton.cs.algs4.Bag;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class WordNet {

 private HashMap<String, Bag<Integer>> data;
 private SAP sap;
 private String[] synsets;
 
 // constructor takes the name of the two input files
 public WordNet(String synsets, String hypernyms) {
  if ((synsets == null) || (hypernyms == null)) {
   throw new NullPointerException();
  }
  In synsetsFile = new In(synsets);
  In hypernymsFile = new In(hypernyms);
  String[] buf = synsetsFile.readAllLines();
  data = new HashMap<String, Bag<Integer>>();
  this.synsets = new String[buf.length];
  for (int i = 0; i < buf.length; i++) {
   String[] entry = buf[i].split(",");
   int id = Integer.parseInt(entry[0]);
   this.synsets[i] = entry[1];
   for (String noun : this.synsets[i].split(" ")) {
    if (data.containsKey(noun)) {
     data.get(noun).add(id);
    } else {
     Bag<Integer> bag = new Bag<Integer>();
     bag.add(id);
     data.put(noun, bag);
    }
   }
  }
  Digraph graph = new Digraph(buf.length);
  while (!hypernymsFile.isEmpty()) {
   String[] entry = hypernymsFile.readLine().split(",");
   int v = Integer.parseInt(entry[0]);
   for (int i = 1; i < entry.length; i++) {
    graph.addEdge(v, Integer.parseInt(entry[i]));
   }
  }
  int rootCount = 0;
  for (int i = 0; i < graph.V(); i++) {
   if (graph.outdegree(i) == 0) {
    if (rootCount == 1) {
     throw new IllegalArgumentException();
    }
    rootCount++;
   }
  }
  if (rootCount == 0) {
   throw new IllegalArgumentException();
  }
  sap = new SAP(graph);
 }

 // returns all WordNet nouns
 public Iterable<String> nouns() {
  return data.keySet();
 }

 // is the word a WordNet noun?
 public boolean isNoun(String word) {
  if (word == null) {
   throw new NullPointerException();
  }
  return data.containsKey(word);
 }

 // distance between nounA and nounB (defined below)
 public int distance(String nounA, String nounB) {
  return sap.length(getVertices(nounA), getVertices(nounB));
 }

 // a synset (second field of synsets.txt) that is the common ancestor of nounA
 // and nounB
 // in a shortest ancestral path (defined below)
 public String sap(String nounA, String nounB) {
  int ancestor = sap.ancestor(getVertices(nounA), getVertices(nounB));
  return this.synsets[ancestor];
 }
 
 private Iterable<Integer> getVertices(String noun) {
  if (noun == null) {
   throw new NullPointerException();
  }
  Iterable<Integer> vertices = data.get(noun);
  if (vertices == null) {
   throw new IllegalArgumentException();
  }
  return vertices;
 }

 // do unit testing of this class
 public static void main(String[] args) {
  WordNet net = new WordNet("A:\\Temp\\synsets.txt", "A:\\Temp\\hypernyms.txt");
  StdOut.print(net.sap("cyclooxygenase-2", "acetyl_group"));
 }
}
