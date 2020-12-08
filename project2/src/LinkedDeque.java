import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;
import java.util.Iterator;
import java.util.NoSuchElementException;

// Deque implementation using a linked list.
public class LinkedDeque<Item> implements Iterable<Item> {
    private Node first;
    private Node last;
    private int N;

    // Helper doubly-linked list class.
    private class Node {
        private Item item;
        private Node next;
        private Node prev;
    }

    // Construct an empty deque.
    public LinkedDeque() {
        this.first = null;
        this.last = null;
        this.N = 0;
    }

    // Is the dequeue empty?
    public boolean isEmpty() {
        return N == 0;
    }

    // The number of items on the deque.
    public int size() {
        return N;
    }

    // Add item to the front of the deque.
    public void addFirst(Item item) {
        if(item == null)
            throw new NullPointerException("Cannot add null item");

        Node newFirst = new Node();
        newFirst.item = item;
        newFirst.next = first;
        newFirst.prev = null;
        if(isEmpty()) {
            first = newFirst;
            last = first;
        }
        else {
            first.prev = newFirst;
            first = newFirst;
        }
        N++;
    }

    // Add item to the end of the deque.
    public void addLast(Item item) {
        if(item == null)
            throw new NullPointerException("Cannot add null item");

        Node newLast = new Node();
        newLast.item = item;
        newLast.next = null;
        newLast.prev = last;

        if(isEmpty()) {
            last = newLast;
            first = last;
        }
        else {
            last.next = newLast;
            last = newLast;
        }
        N++;
    }

    // Remove and return item from the front of the deque.
    public Item removeFirst() {
        if(isEmpty())
            throw new NoSuchElementException();
        Item removedFirst = first.item;
        first = first.next;
        if(N == 1)
            first = null;
        else
            first.prev = null;
        N--;
        return removedFirst;
    }

    // Remove and return item from the end of the deque.
    public Item removeLast() {
        if(isEmpty())
            throw new NoSuchElementException();
        Item removedLast = last.item;
        last = last.prev;

        if(N == 1)
            last = null;
        else
            last.next = null;
        N--;
        return removedLast;
    }

    // An iterator over items in the queue in order from front to end.
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    // An iterator, doesn't implement remove() since it's optional.
    private class DequeIterator implements Iterator<Item> {
        private Node current;

        DequeIterator() {
            current = first;
        }

        public boolean hasNext()  { return current != null; }

        public void remove() { throw new UnsupportedOperationException(); }

        public Item next() {
            if(!hasNext())
                throw new
                        NoSuchElementException();

            Item item = current.item;
            current = current.next;
            return item;
        }
    }

    // A string representation of the deque.
    public String toString() {
        StringBuilder s = new StringBuilder();
        for (Item item : this) {
            s.append(item + " ");
        }
        return s.toString().substring(0, s.length() - 1);
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        LinkedDeque<Character> deque = new LinkedDeque<Character>();
        String quote = "There is grandeur in this view of life, with its "
                + "several powers, having been originally breathed into a few "
                + "forms or into one; and that, whilst this planet has gone "
                + "cycling on according to the fixed law of gravity, from so "
                + "simple a beginning endless forms most beautiful and most "
                + "wonderful have been, and are being, evolved. ~ "
                + "Charles Darwin, The Origin of Species";
        int r = StdRandom.uniform(0, quote.length());
        for (int i = quote.substring(0, r).length() - 1; i >= 0; i--) {
            deque.addFirst(quote.charAt(i));
        }
        for (int i = 0; i < quote.substring(r).length(); i++) {
            deque.addLast(quote.charAt(r + i));
        }
        StdOut.println(deque.isEmpty());
        StdOut.printf("(%d characters) ", deque.size());
        for (char c : deque) {
            StdOut.print(c);
        }
        StdOut.println();
        double s = StdRandom.uniform();
        for (int i = 0; i < quote.length(); i++) {
            if (StdRandom.bernoulli(s)) {
                deque.removeFirst();
            }
            else {
                deque.removeLast();
            }
        }
        StdOut.println(deque.isEmpty());
    }
}