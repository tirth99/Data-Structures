import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import java.util.Arrays;
import java.util.Comparator;

// A data type that provides autocomplete functionality for a given set of
// string and weights, using Term and BinarySearchDeluxe.
public class Autocomplete {
    private final Term[] terms;

    // Initialize the data structure from the given array of terms.
    public Autocomplete(Term[] terms) {
        if (terms == null)
            throw new NullPointerException("Terms must not be null");
        this.terms = new Term[terms.length];
        for (int i = 0; i < terms.length; i++) {
            if(terms[i] == null)
                throw new NullPointerException("Null term found");
            this.terms[i] = terms[i];
        }
        Arrays.sort(this.terms);
    }

    // All terms that start with the given prefix, in descending order of
    // weight.
    public Term[] allMatches(String prefix) {
        if (prefix == null)
            throw new NullPointerException("Prefix must not be null");

        int firstIndex = BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        if (firstIndex == -1)
            return new Term[0];
        int lastIndex  = BinarySearchDeluxe.lastIndexOf (terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
        Term[] matches = new Term[1 + lastIndex - firstIndex];

        for (int i = 0; i < matches.length; i++)
            matches[i] = terms[firstIndex++];

        Arrays.sort(matches, Term.byReverseWeightOrder());

        return matches;
    }

    // The number of terms that start with the given prefix.
    public int numberOfMatches(String prefix) {
        if (prefix == null)
            throw new NullPointerException("Prefix must not be null");
        return 1 +
                BinarySearchDeluxe.lastIndexOf (terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length())) -
                BinarySearchDeluxe.firstIndexOf(terms, new Term(prefix, 0), Term.byPrefixOrder(prefix.length()));
    }

    // Entry point. [DO NOT EDIT]
    public static void main(String[] args) {
        String filename = args[0];
        In in = new In(filename);
        int N = in.readInt();
        Term[] terms = new Term[N];
        for (int i = 0; i < N; i++) {
            long weight = in.readLong();
            in.readChar();
            String query = in.readLine();
            terms[i] = new Term(query.trim(), weight);
        }
        int k = Integer.parseInt(args[1]);
        Autocomplete autocomplete = new Autocomplete(terms);
        while (StdIn.hasNextLine()) {
            String prefix = StdIn.readLine();
            Term[] results = autocomplete.allMatches(prefix);
            for (int i = 0; i < Math.min(k, results.length); i++) {
                StdOut.println(results[i]);
            }
        }
    }
}
