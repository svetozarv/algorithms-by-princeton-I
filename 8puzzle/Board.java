import java.util.ArrayList;
import edu.princeton.cs.algs4.In;

public class Board {
    private int[][] tiles;
    private int n;
    private int manhattan;

    // create a board from an n-by-n array of tiles,
    // where tiles[row][col] = tile at (row, col)
    public Board(int[][] tiles) {
        if (tiles == null) throw new IllegalArgumentException();
        
        this.n = tiles.length;
        this.tiles = createCopy(tiles);
        this.manhattan = this.computeManhattan();
    }

    private int[][] createCopy(int[][] tiles) {
        int[][] copy = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                copy[i][j] = tiles[i][j];
            }
        }
        return copy;
    }

    // string representation of this board
    public String toString() {
        StringBuilder stringBoard = new StringBuilder(Integer.toString(n));
        stringBoard.append("\n");
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                stringBoard.append(this.tiles[i][j] + " ");
            }
            stringBoard.append("\n");
        }
        return stringBoard.toString();
    }

    // board dimension n
    public int dimension() {
        return n;
    }

    // number of tiles out of place
    public int hamming() {
        int hamming = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (fromDoubleToOneIndex(row, col) + 1 != tiles[row][col]) {
                    hamming++;
                }
            }
        }
        return hamming - 1;
    }

    public int manhattan() {
        return manhattan;
    }

    // sum of Manhattan distances between tiles and goal
    private int computeManhattan() {
        int manhattanDistanses = 0;
        for (int row = 0; row < n; row++) {
            for (int col = 0; col < n; col++) {
                if (tiles[row][col] == 0) continue;
                if (tiles[row][col] - 1 != fromDoubleToOneIndex(row, col)) {
                    int[] indexes = fromOneToDoubleIndex(tiles[row][col] - 1);
                    manhattanDistanses += Math.abs(indexes[0] - row);
                    manhattanDistanses += Math.abs(indexes[1] - col);
                }
            }
        }
        return manhattanDistanses;
    }

    // is this board the goal board?
    public boolean isGoal() {
        if (this.hamming() == 0 && this.tiles[n-1][n-1] == 0) {
            return true;
        }
        return false;
    }

    // does this board equal y?
    public boolean equals(Object y) {
        if (y == null) return false;
        if (this == y) return true;
        if (!(y instanceof Board)) return false;
        
        Board board = (Board) y;
        if (board.dimension() != this.dimension()) return false;

        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != board.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }

    // all neighboring boards
    public Iterable<Board> neighbors() {
        ArrayList<Board> neighbors = new ArrayList<>();
        
        // find indexes of zero
        int[] zeroIndex = {-1, -1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == 0) {
                    zeroIndex[0] = i;
                    zeroIndex[1] = j;
                    break;
                }
            }
        }

        int temp = -1;
        int row = zeroIndex[0];
        int col = zeroIndex[1];
        
        
        // exchange 0 with the left tile
        if (col > 0) {
            temp = this.tiles[row][col-1];
            this.tiles[row][col] = temp;
            this.tiles[row][col-1] = 0;
            
            Board neighbor1 = new Board(tiles);
            neighbors.add(neighbor1);
            
            // revert changes
            this.tiles[row][col] = 0;
            this.tiles[row][col-1] = temp;
        }
        
        // exchange 0 with the right tile
        if (col < n - 1) {
            temp = this.tiles[row][col+1];
            this.tiles[row][col] = temp;
            this.tiles[row][col+1] = 0;
            
            Board neighbor2 = new Board(tiles);
            neighbors.add(neighbor2);
            
            // revert changes
            this.tiles[row][col] = 0;
            this.tiles[row][col+1] = temp;
        }
        
        // exchange 0 with the upper tile
        if (row > 0) {
            temp = this.tiles[row-1][col];
            this.tiles[row][col] = temp;
            this.tiles[row-1][col] = 0;
            
            Board neighbor3 = new Board(tiles);
            neighbors.add(neighbor3);

            // revert changes
            this.tiles[row][col] = 0;
            this.tiles[row-1][col] = temp;
        }
        
        // exchange 0 with the bottom tile
        if (row < n - 1) {
            temp = this.tiles[row+1][col];
            this.tiles[row][col] = temp;
            this.tiles[row+1][col] = 0;
            
            Board neighbor4 = new Board(tiles);
            neighbors.add(neighbor4);
            
            // revert changes
            this.tiles[row][col] = 0;
            this.tiles[row+1][col] = temp;
        }
        return neighbors;
    }

    // a board that is obtained by exchanging the last pair of tiles
    public Board twin() {
        int temp = -1;
        int[] lastElementIndexes = {-1, -1};
        int[] prelastElementIndexes = {-1, -1};
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] == n*n-1) {
                    lastElementIndexes[0] = i;
                    lastElementIndexes[1] = j;
                }
                if (this.tiles[i][j] == n*n-2) {
                    prelastElementIndexes[0] = i;
                    prelastElementIndexes[1] = j;
                }
            }
        }
        // swap elements in tiles
        temp = this.tiles[lastElementIndexes[0]][lastElementIndexes[1]];
        this.tiles[lastElementIndexes[0]][lastElementIndexes[1]] = this.tiles[prelastElementIndexes[0]][prelastElementIndexes[1]];
        this.tiles[prelastElementIndexes[0]][prelastElementIndexes[1]] = temp;
        
        Board twin = new Board(tiles);

        // revert changes
        this.tiles[prelastElementIndexes[0]][prelastElementIndexes[1]] = this.tiles[lastElementIndexes[0]][lastElementIndexes[1]];
        this.tiles[lastElementIndexes[0]][lastElementIndexes[1]] = temp;

        return twin;
    }

    /**
     * (i, j) -> i
     */
    private int fromDoubleToOneIndex(int row, int col) {
        return n*row + col;
    }

    /**
     * i -> [i, j]
     * @return an array, where a[0] = row, a[1] = col
     */
    private int[] fromOneToDoubleIndex(int i) {
        int[] a = {i / n, i % n};
        return a;
    }

    // unit testing (not graded)
    public static void main(String[] args) {
        // read from file
        In in = new In("test.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board board = new Board(tiles);

        // unit test
        System.out.println(board);
        System.out.println(board.hamming());
        System.out.println(board.manhattan());
        System.out.println("\nNeighbors: ");
        for (Board b : board.neighbors()) {
            System.out.println(b);
        }
        System.out.println("\nTwin: ");
        System.out.println(board.twin());
    }
}
