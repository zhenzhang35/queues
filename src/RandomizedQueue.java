import java.util.Iterator;
import java.util.NoSuchElementException;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] a = (Item[]) new Object[1];
    private int size = 0;

    public RandomizedQueue(){}

    public boolean isEmpty(){
        return size == 0;
    }

    public int size(){
        return size;
    }

    public void enqueue(Item item){
        if (item == null){
            throw new IllegalArgumentException("Method called with a null argument.");
        }
        if (size == a.length){
            resize(2 * a.length);
        }
        a[size++] = item;
        randomize();
    }

    public Item dequeue(){
        if (isEmpty()){
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        Item item = a[--size];
        a[size] = null;
        if (size > 0 && size == a.length/4){
            resize(a.length/2);
        }
        return item;
    }

    public Item sample(){
        if (isEmpty()){
            throw new NoSuchElementException("The randomized queue is empty.");
        }
        int i = StdRandom.uniform(0, size);
        return a[i];
    }

    public Iterator<Item> iterator(){
        return new ListIterator();
    }

    private class ListIterator implements Iterator<Item>{
        private int i = 0;

        public boolean hasNext(){
            return i != size;
        }

        public Item next(){
            if (!hasNext()){
                throw new NoSuchElementException("There are no more items to return.");
            }
            return a[i++];
        }

        public void remove(){
            throw new UnsupportedOperationException("No remove method in the iterator.");
        }
    }

    private void resize(int capacity){
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < size; i++){
            copy[i] = a[i];
        }
        a = copy;
    }

    private void randomize(){
        int i = StdRandom.uniform(0, size);
        Item temp = a[i];
        a[i] = a[size - 1];
        a[size - 1] = temp;
    }

    public static void main(String[] args){
        RandomizedQueue<String> rq = new RandomizedQueue<String>();
        while (!StdIn.isEmpty()){
            String s = StdIn.readString();
            if (s.equals("-")){
                StdOut.println(rq.dequeue());
            } else if (s.equals("+")){
                Iterator<String> iterator = rq.iterator();
                while (iterator.hasNext()){
                    StdOut.println(iterator.next());
                }
            } else {
                rq.enqueue(s);
            }
        }
    }
}
