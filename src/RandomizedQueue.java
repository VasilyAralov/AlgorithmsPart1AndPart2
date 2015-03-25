import java.util.Iterator;
import java.util.NoSuchElementException;


public class RandomizedQueue<Item> implements Iterable<Item> {

 private class RQIterator implements Iterator<Item> {
  
  private Item[] iterator;
  private int size;
  
  private RQIterator() {
   iterator = (Item[]) new Object[size()];
   for (int i = 0; i < size(); i++) {
    iterator[i] = queue[i];
   }
   size = size();
  }
  
  @Override
  public boolean hasNext() {
   return size > 0;
  }

  @Override
  public Item next() {
   if (!hasNext()) {
    throw new NoSuchElementException();
   }
   int n = StdRandom.uniform(size);
   Item item = iterator[n];
   iterator[n] = iterator[size - 1];
   iterator[size - 1] = item;
   size--;
   return item;
  }
  
  @Override
  public void remove() {
   throw new UnsupportedOperationException();
  }
  
 }
 
 private int size = 0;
 private Item[] queue = (Item[]) new Object[32];
 
 public boolean isEmpty() {
  return size() == 0;
 }
 
 public int size() {
  return size;
 }
 
 public void enqueue(Item item) {
  if (item == null) {
   throw new NullPointerException();
  }
  if (queue.length <= size) {
   increase();
  }
  queue[size] = item;
  size++;
 }
 

 private void increase() {
  Item[] buf = (Item[]) new Object[queue.length * 2];
  for (int i = 0; i < size; i++) {
   buf[i] = queue[i];
  }
  queue = buf;
 }

 public Item dequeue() {
  if (size == 0) {
   throw new NoSuchElementException();
  }
  int n = StdRandom.uniform(size);
  Item item = queue[n];
  queue[n] = queue[size - 1];
  queue[size - 1] = null;
  size--;
  if ((size * 4 <= queue.length) && (queue.length > 32)) {
   decrease();
  }
  return item;
 }
 
 private void decrease() {
  Item[] buf = (Item[]) new Object[queue.length / 2];
  for (int i = 0; i < size; i++) {
   buf[i] = queue[i];
  }
  queue = buf;
 }

 public Item sample() {
  if (size == 0) {
   throw new NoSuchElementException();
  }
  return queue[StdRandom.uniform(size)];
 }
 
 @Override
 public Iterator<Item> iterator() {
  return new RQIterator();
 }
 
 public static void main(String[] args) {
 }
 
}