// deque implementation with linked lists
import java.util.Iterator;


public class Deque<Item> implements Iterable<Item> {

    private Node first, last;
    private int size;

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    // construct an empty deque
    public Deque() {
        // doesn't do anything
    }

    // is the deque empty?
    public boolean isEmpty() {
        return first == null;
    }

    // return the number of items on the deque
    public int size() { return size; }

    // add the item to the front
    public void addFirst(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldfirst = first;
        first = new Node();
        first.item = item;
        first.next = oldfirst;

        if (last == null) {
            last = first;
        } else {
            oldfirst.prev = first;
        }
    }

    // add the item to the back
    public void addLast(Item item) {
        if (item == null) {
            throw new IllegalArgumentException();
        }
        Node oldlast = last;
        last = new Node();
        last.item = item;
        last.next = null;
        
        if (isEmpty()) {
            first = last;
        } else {
            oldlast.next = last;
        }
        last.prev = oldlast;
    }

    // remove and return the item from the front
    public Item removeFirst() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Item item = first.item;
        first = first.next;
        return item;
    }

    // remove and return the item from the back
    public Item removeLast() {
        if (isEmpty()) { throw new java.util.NoSuchElementException(); }
        Item item = last.item;
        last.prev.next = null;
        last = last.prev;
        return item;
    }

    // return an iterator over items in order from front to back
    public Iterator<Item> iterator() { return new ListIterator(); }

    private class ListIterator implements Iterator<Item> {
        private Node current = first;

        public boolean hasNext() { return current != null; }
        public void remove() { throw new UnsupportedOperationException(); }
        public Item next() {
            if (current == null) { throw new java.util.NoSuchElementException(); }
            Item item = current.item;
            current = current.next;
            return item;
        }
    }


    // unit testing (required)
    public static void main(String[] args) {

        Deque<String> deque = new Deque<>();

        deque.addLast("ab");
        deque.addLast("bc");
        deque.addLast("cd");

        System.out.print("The initial array is: ");
        for (String s : deque) {
            System.out.print(s + " ");
        }

        System.out.println("\n" + deque.removeLast());
    
        System.out.print("The final array is: ");
        for (String s : deque) {
            System.out.print(s + " ");
        }
    }
}
