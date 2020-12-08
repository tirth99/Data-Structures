// Spell.java: Takes a command-line argument specifying the name of the file
// containing common misspellings (a line-oriented file with each
// comma-separated line containing a misspelled word and the correct spelling),
// then reads text from standard input and prints out the misspelled words in
// the text along with the line numbers where they occurred and their correct
// spellings.

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.SeparateChainingHashST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;


@SuppressWarnings({"wartypes", "unchecked"})
public class Spell {
    public static void main(String[] args) {
        SeparateChainingHashST corrector = new SeparateChainingHashST();

        In file1 = new In(args[0]);
        while (file1.hasNextLine()) {
            String[] correction = file1.readLine().split(",");
            corrector.put(correction[0], correction[1]);
        }

        In file2 = new In(args[1]);
        int lineNumber = 1;
        while (file2.hasNextLine()) {
            String line = file2.readLine().trim();
            String[] words = line.split("\\W"); // Also, if you want output exactly like the pdf, String[] words = line.split("\\s+");
            for (int i = 0; i < words.length; i++) {
                if (corrector.contains(words[i])) {
                    StdOut.println(words[i] + ":" + lineNumber + " -> " + corrector.get(words[i]));
                }
            }
            lineNumber++;
        }
    }
}
