public class Subset {

 public static void main(String[] args) {
  int k = Integer.parseInt(args[0]);
  RandomizedQueue<String> rQueue = new RandomizedQueue<String>();
  while (!StdIn.isEmpty()) {
   rQueue.enqueue(StdIn.readString());
  }
  if (rQueue.size() < k) {
   k = rQueue.size();
  }
  while (k > 0 && k <= rQueue.size()) {
   StdOut.println(rQueue.dequeue());
   k--;
  }
 }

}
