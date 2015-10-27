import java.util.Iterator;
import java.util.NoSuchElementException;

public class Deque<Item> implements Iterable<Item> {
 
 private class Node {
  
  private Item item = null;
  private Node next = null;
  private Node prev = null;
  
 }
 
 private class ItemIterator implements Iterator<Item> {

  private Node next = null;
  
  private ItemIterator() {
   next = first;
  }
  
  @Override
  public boolean hasNext() {
   return (next != null);
  }

  @Override
  public Item next() {
   if (!hasNext()) {
    throw new NoSuchElementException();
   }
   Item item = next.item;
   next = next.next;
   return item;
  }
  
  @Override
  public void remove() {
   throw new UnsupportedOperationException(); 
  }
  
 }

 private Node first;
 private Node last;
 private int size;

 public boolean isEmpty() {
  return size == 0;
 }
 
 public int size() {
  return size;
 }
 
 public void addFirst(Item item) {
  if (item == null) {
   throw new NullPointerException();
  }
  Node node = new Node();
  node.item = item;
  node.next = first;
  if (first != null) {
   first.prev = node;
  } else {
   last = node;
  }
  first = node;
  size++;
 }
 
 public void addLast(Item item) {
  if (item == null) {
   throw new NullPointerException();
  }
  Node node = new Node();
  node.item = item;
  node.prev = last;
  if (last != null) {
   last.next = node;
  } else {
   first = node;
  }
  last = node;
  size++;
 }
 
 public Item removeFirst() {
  if (isEmpty()) {
   throw new NoSuchElementException();
  }
  Item item = first.item;
  first.item = null;
  switch (size()) {
  case 1: 
   first = null;
   last = null;
   break;
  case 2:
   first = last;
   first.next = null;
   first.prev = null;
   break;
  default:
   first = first.next;
   first.prev = null;
  }
  size--;
  return item;
 }
 
 public Item removeLast() {
  if (isEmpty()) {
   throw new NoSuchElementException();
  }
  Item item = last.item;
  last.item = null;
  switch (size()) {
  case 1: 
   first = null;
   last = null;
   break;
  case 2:
   last = first;
   first.next = null;
   first.prev = null;
   break;
  default:
   last = last.prev;
   last.next = null;
  }
  size--;
  return item;
 }
 
 @Override
 public Iterator<Item> iterator() {
  return new ItemIterator();
 }

 public static void main(String[] args) {
 }
}
