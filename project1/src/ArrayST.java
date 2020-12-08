import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;



public class ArrayST<Key, Value> {

    private static final int INIT_SIZE = 2;
    private Key[] keys;
    private Value[] values;
    private int N;

    public ArrayST() {
        keys = (Key[])   new Object[INIT_SIZE];
        values = (Value[]) new Object[INIT_SIZE];
    }

    // return the number of key-value pairs in the symbol table
    public int size() {
        return N;
    }

    // is the symbol table empty?
    public boolean isEmpty() {
        return size() == 0;
    }

    // resize the parallel arrays to the given capacity
    private void resize(int capacity) {
        Key[]   tempk = (Key[])   new Object[capacity];
        Value[] tempv = (Value[]) new Object[capacity];
        for (int i = 0; i < N; i++)
            tempk[i] = keys[i];
        for (int i = 0; i < N; i++)
            tempv[i] = values[i];
        keys = tempk;
        values = tempv;
    }

    // insert the key-value pair into the symbol table
    public void put(Key key, Value val) {

        // to deal with duplicates
        delete(key);

        // double size of arrays if necessary
        if (N >= values.length) resize(2* N);

        // add new key and value at the end of array
        values[N] = val;
        keys[N] = key;
        N++;
    }

    public Value get(Key key) {
        for (int i = 0; i < N; i++)
            if (keys[i].equals(key)) return values[i];
        return null;
    }

    public boolean contains(Key key){
        for (int i = 0; i < N; i++)
            if (keys[i].equals(key)) return true;
        return false;

    }

    public Iterable<Key> keys() {
        Queue<Key> queue = new Queue<>();
        for (int i = 0; i < N; i++)
            queue.enqueue(keys[i]);
        return queue;
    }

    // remove given key (and associated value)
    public void delete(Key key) {
        for (int i = 0; i < N; i++) {
            if (key.equals(keys[i])) {
                keys[i] = keys[N -1];
                values[i] = values[N -1];
                keys[N -1] = null;
                values[N -1] = null;
                N--;
                if (N > 0 && N == keys.length/4) resize(keys.length/2);
                return;
            }
        }
    }
    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        ArrayST<String, Integer> st = new ArrayST<String, Integer>();
        int count = 0;
        while (!StdIn.isEmpty()) {
            String s = StdIn.readString();
            st.put(s, ++count);
        }
        for (String s : args) {
            st.delete(s);
        }
        for (String s : st.keys()) {
            StdOut.println(s + " " + st.get(s));
        }
    }
}
