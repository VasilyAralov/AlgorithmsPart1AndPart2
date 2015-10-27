import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

 private class RQIterator implements Iterator<Item> {
  
  private Item[] iterator;
  private int n;
  
  private RQIterator() {
   iterator = (Item[]) new Object[size()];
   System.arraycopy(queue, 0, iterator, 0, size());
   StdRandom.shuffle(iterator);
   n = 0;
  }
  
  @Override
  public boolean hasNext() {
   return n < iterator.length;
  }

  @Override
  public Item next() {
   if (!hasNext()) {
    throw new NoSuchElementException();
   }
   Item item = iterator[n];
   iterator[n] = null;
   n++;
   return item;
  }
  
  @Override
  public void remove() {
   throw new UnsupportedOperationException();
  }
  
 }
 
 private int size = 0;
 private Item[] queue = (Item[]) new Object[2];
 
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
   resize(queue.length * 2);
  }
  queue[size] = item;
  size++;
 }
 

 private void resize(int n) {
  Item[] buf = (Item[]) new Object[n];
  System.arraycopy(queue, 0, buf, 0, size);
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
  if ((size * 4 < queue.length) && (queue.length > 1)) {
   resize(queue.length / 2);
  }
  return item;
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
 
}