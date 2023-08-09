// rand queue implementation using resising arrays
import java.util.Iterator;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    
    private Item[] arr;
    private int N = 0;      // num of items

    // construct an empty randomized queue
    public RandomizedQueue() {
        arr = (Item[]) new Object[1];
    }

    // is the randomized queue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // return the number of items on the randomized queue
    public int size() { return N; }

    // add the item
    public void enqueue(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        if (N == arr.length) { resize(2*arr.length); }
        arr[N++] = item;
    }

    private void resize(int capacity) {
        Item[] copy = (Item[]) new Object[capacity];
        for (int i = 0; i < N; i++) {
            copy[i] = arr[i];
        }
        arr = copy;
    }

    // remove and return a random item
    public Item dequeue() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randInt = StdRandom.uniformInt(0, N);
        Item item = arr[randInt];
        arr[randInt] = arr[N-1];
        arr[N-1] = null;
        --N;

        if (N > 0 && N == arr.length/4) {
            resize(arr.length/2);
        }
        return item;
    }

    // return a random item (but do not remove it)
    public Item sample() {
        if (isEmpty()) {
            throw new java.util.NoSuchElementException();
        }
        int randInt = StdRandom.uniformInt(0, N);
        return arr[randInt];
    }

    // return an independent iterator over items in random order
    public Iterator<Item> iterator() {
        StdRandom.shuffle(arr, 0, N);
        return new RandomizedArrayIterator();
    }

    private class RandomizedArrayIterator implements Iterator<Item> {
        private int i = N;

        public boolean hasNext() { return i > 0; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (i == 0) { throw new java.util.NoSuchElementException(); }
            return arr[--i];
        }
    }

    // unit testing (required)
    public static void main(String[] args) {

        RandomizedQueue<String> rq = new RandomizedQueue<>();
        rq.enqueue("orange");
        rq.enqueue("banana");
        rq.enqueue("apple");
        rq.enqueue("grapes");
        
        // System.out.println(rq.sample() + " chosen randomly");
        // System.out.println(rq.dequeue() + " removed");
        // System.out.println(rq.dequeue() + " removed");


        for (String s : rq) {
            System.out.print(s + " ");
        }
        System.out.println();
    }
}
