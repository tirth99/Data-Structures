// FrequencyCounter.java: Reads in a command-line integer and sequence of words
// from standard input and prints out all the words (whose length exceeds the
// threshold) that occur most frequently to standard output. It also prints out
// the number of words whose length exceeds the threshold and the number of
// distinct such words.

import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class FrequencyCounter {
    public static void main(String[] args) {
        int distinct = 0, words = 0, mostFrequent = 0;
        int threshold = Integer.parseInt(args[0]);
        ArrayST<String, Integer> st = new ArrayST<>();

        // compute frequency counts
        while (!StdIn.isEmpty()) {
            String key = StdIn.readString();
            if (key.length() < threshold) continue;
            words++;
            if (st.contains(key)) {
                st.put(key, st.get(key) + 1);
                if(mostFrequent < st.get(key) && key.length() > threshold)
                    mostFrequent = st.get(key);
            }
            else {
                st.put(key, 1);
                distinct++;
            }
        }

        // find a key with the highest frequency count
        String max = "";
        for (String word : st.keys()) {
            if (st.get(word) == mostFrequent && word.length() > threshold) {
                max = max + word + " ";
            }
        }

        StdOut.println(max + mostFrequent);
        StdOut.println("distinct = " + distinct);
        StdOut.println("words = " + words);
    }
}