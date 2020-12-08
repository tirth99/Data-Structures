import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdOut;

// Models a board in the 8-puzzle game or its generalization.
public class Board {
    private int[][] tiles;
    private int N;
    private int hamming;
    private int manhattan;

    // Construct a board from an N-by-N array of tiles, where
    // tiles[i][j] = tile at row i and column j, and 0 represents the blank
    // square.
    public Board(int[][] tiles) {
        this.N = tiles.length;
        this.hamming = 0;
        this.manhattan = 0;
        int tmp = 1;
        this.tiles = new int[N][N];
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                this.tiles[i][j] = tiles[i][j];

                if (this.tiles[i][j] != N * i + j + 1 && this.tiles[i][j]!= 0)
                    hamming += 1;

                int currentNumInThePosition = this.tiles[i][j];
                if(currentNumInThePosition == 0){
                    continue;
                }else{
                    int expectedRow = (currentNumInThePosition - 1) / N;
                    int expectedCol = (currentNumInThePosition - 1) % N;
                    manhattan += (Math.abs(i - expectedRow) + Math.abs(j - expectedCol));
                }
            }
        }
    }

    // Tile at row i and column j.
    public int tileAt(int i, int j) {
        return this.tiles[i][j];
    }

    // Size of this board.
    public int size() {
        return this.N;
    }

    // Number of tiles out of place.
    public int hamming() {
        return hamming;
    }

    // Sum of Manhattan distances between tiles and goal.
    public int manhattan() {
        return manhattan;
    }

    // Is this board the goal board?
    public boolean isGoal() {
        return hamming() == 0;
    }

    // Is this board solvable?
    public boolean isSolvable() {
        return inversions() % 2 == 0;
    }

    // Does this board equal that?
    public boolean equals(Board that) {
        if(this.N != that.N)
            return false;
        for(int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                if (this.tiles[i][j] != that.tiles[i][j])
                    return false;
            }
        }
        return true;
    }

    // All neighboring boards.
    public Iterable<Board> neighbors() {
        int pos = blankPos() - 1;
        int x = pos / N;
        int y = pos % N;
        int[][] a;
        LinkedQueue<Board> list = new LinkedQueue<>();
        if(x > 0){
            a = cloneTiles();
            int t = a[x][y];
            a[x][y] = a[x-1][y];
            a[x-1][y] = t;
            list.enqueue(new Board(a));
        }
        if(x < N - 1){
            a = cloneTiles();
            int t = a[x][y];
            a[x][y] = a[x+1][y];
            a[x+1][y] = t;
            list.enqueue(new Board(a));
        }
        if(y > 0){
            a = cloneTiles();
            int t = a[x][y];
            a[x][y] = a[x][y-1];
            a[x][y-1] = t;
            list.enqueue(new Board(a));
        }
        if(y < N - 1){
            a = cloneTiles();
            int t = a[x][y];
            a[x][y] = a[x][y+1];
            a[x][y+1] = t;
            list.enqueue(new Board(a));
        }
        return list;
    }

    // String representation of this board.
    public String toString() {
        String s = N + "\n";
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s += String.format("%2d", tiles[i][j]);
                if (j < N - 1) {
                    s += " ";
                }
            }
            if (i < N - 1) {
                s += "\n";
            }
        }
        return s;
    }

    // Helper method that returns the position (in row-major order) of the
    // blank (zero) tile.
    private int blankPos() {
        int blankPos = 0;
        for(int i = 0; i < N; i++) {
            for(int j = 0; j < N; j++) {
                if(tiles[i][j] == 0)
                    blankPos = i * N + j + 1;
            }
        }
        return blankPos;
    }

    // Helper method that returns the number of inversions.
    private int inversions() {
        int inversions = 0;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                for(int k = 0;k < N; k++){
                    for(int l = 0;l < N; l++){
                        if ((k * N + l) > (i * N + j) && tiles[i][j] != 0
                                && tiles[k][l] != 0 && tiles[i][j] > tiles[k][l])
                            inversions++;
                    }
                }
            }
        }
        return inversions;
    }

    // Helper method that clones the tiles[][] array in this board and
    // returns it.
    private int[][] cloneTiles() {
        int[][] a = new int[N][N];
        for(int i = 0; i < N; i++){
            System.arraycopy(tiles[i], 0, a[i], 0, N);
        }
        return a;
    }

    // Test client. [DO NOT EDIT]
    public static void main(String[] args) {
        In in = new In(args[0]);
        int N = in.readInt();
        int[][] tiles = new int[N][N];
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                tiles[i][j] = in.readInt();
            }
        }
        Board board = new Board(tiles);
        StdOut.println(board.hamming());
        StdOut.println(board.manhattan());
        StdOut.println(board.isGoal());
        StdOut.println(board.isSolvable());
        for (Board neighbor : board.neighbors()) {
            StdOut.println(neighbor);
        }
    }
}

