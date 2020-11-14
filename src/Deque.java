import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private int size = 0;
    private Node first = null;
    private Node last = null;

    public Deque(){}

    private class Node{
        Item item;
        Node next;
        Node prev;
    }

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void addFirst(Item item){
        if (item == null){
            throw new IllegalArgumentException("Method called with a null argument.");
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;
        first.prev = null;
        if (isEmpty()){
            last = first;
        } else {
            oldfirst.prev = first;
        }
        size++;
    }

    public void addLast(Item item){
        if (item == null){
            throw new IllegalArgumentException("Method called with a null argument.");
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        last.prev = oldlast;
        if (isEmpty()){
            first = last;
        } else {
            oldlast.next = last;
        }
        size++;
    }

    public Item removeFirst(){
        if (isEmpty()){
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = first.item;
        size--;
        if (isEmpty()){
            first = null;
            last = null;
        } else {
            first = first.next;
            first.prev = null;
        }
        return item;
    }

    public Item removeLast(){
        if (isEmpty()){
            throw new NoSuchElementException("The deque is empty.");
        }
        Item item = last.item;
        size--;
        if (isEmpty()){
            first = null;
            last = null;
        } else {            
            last = last.prev;
            last.next = null;
        }
        return item;
    }

    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private Node current = first;
        public boolean hasNext(){
            return current != null;
        }
        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException("There are no more items to return.");
            }
            Item item = current.item;
            current = current.next;
            return item;
        }
        public void remove(){
            throw new UnsupportedOperationException("No remove method in the iterator.");
        }
    }
    
    public static void main(String[] args){
        Deque<String> deque = new Deque<String>();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if (s.equals("-")){
                StdOut.println(deque.removeFirst());
            } else if (s.equals("+")){
                Iterator<String> iterator = deque.iterator();
                while (iterator.hasNext()){
                    StdOut.println(iterator.next());
                }
            } else {
                deque.addLast(s);
            }
        }
    }
}