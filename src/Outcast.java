import edu.princeton.cs.algs4.MaxPQ;

public class Outcast {
 
 private class OutcastWord implements Comparable<OutcastWord> {
  
  private String noun;
  private int maxLength;
  
  private OutcastWord(String noun, int maxLength) {
   this.noun = noun;
   this.maxLength = maxLength;
  }

  @Override
  public int compareTo(OutcastWord o) {
   if (this.maxLength > o.maxLength) {
    return 1;
   }
   if (this.maxLength < o.maxLength) {
    return -1;
   }
   return 0;
  }
  
 }
 
 private WordNet wordnet; 

 public Outcast(WordNet wordnet) {
  // constructor takes a WordNet object
  this.wordnet = wordnet;
 }

 public String outcast(String[] nouns) {
  // given an array of WordNet nouns, return an outcast
  MaxPQ<OutcastWord> outcast = new MaxPQ<OutcastWord>(); 
  for (int i = 0; i < nouns.length; i++) {
   int maxLength = 0;
   for (int j = 0; j < nouns.length; j++) {
    maxLength += wordnet.distance(nouns[i], nouns[j]);
   }
   outcast.insert(new OutcastWord(nouns[i], maxLength));
  }
  return outcast.delMax().noun;
 }

}