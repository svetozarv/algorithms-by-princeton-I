/*  final version with NOT fixed backwash problem
-------------------------------------------------------------------------------------
Compilation:  PASSED
API:          PASSED

SpotBugs:     PASSED
PMD:          PASSED
Checkstyle:   PASSED

Correctness:  32/38 tests passed
Memory:       8/8 tests passed
Timing:       20/20 tests passed

Aggregate score: 90.53%
[ Compilation: 5%, API: 5%, Style: 0%, Correctness: 60%, Timing: 10%, Memory: 20% ]
-------------------------------------------------------------------------------------
*/

import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdRandom;


public class Percolation {
    private int size;
    private int numberOfOpenSites;
    private boolean[][] grid;
    private WeightedQuickUnionUF uf;

    // creates n-by-n grid, with all sites initially blocked
    public Percolation(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.numberOfOpenSites = 0;
        this.grid = new boolean[size][size];

        // fill the grid with zeros
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = false;
            }
        }

        this.uf = new WeightedQuickUnionUF(size*size + 2);
    }

    private void visualise() {
        System.out.println();
        for (boolean[] row : grid) {
            for (boolean i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    private int convertIndexes(int row, int col) {
        return size * row + col;
    }

    // opens the site (row, col) if it is not open already
    public void open(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        if (isOpen(row, col)) {
            return;
        }
        row--;
        col--;

        grid[row][col] = true;
        numberOfOpenSites++;

        int i = convertIndexes(row, col);
        
        if (row == 0) {
            uf.union(i, size*size);
        }
        if (row == size - 1) {
            uf.union(i, size*size + 1);
        }

        // Check on the left
        if (col != 0 && grid[row][col - 1]) {
            int j = convertIndexes(row, col - 1);
            uf.union(i, j);
        }

        // Check on the right
        if (col != size - 1 && grid[row][col + 1]) {
            int j = convertIndexes(row, col + 1);
            uf.union(i, j);
        }

        // Check above
        if (row != 0 && grid[row - 1][col]) {
            int j = convertIndexes(row - 1, col);
            uf.union(i, j);
        }

        // Check below
        if (row != size - 1 && grid[row + 1][col]) {
            int j = convertIndexes(row + 1, col);
            uf.union(i, j);
        }
    }

    // is the site (row, col) open?
    public boolean isOpen(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        row--;
        col--;

        return grid[row][col];
    }

    // is the site (row, col) full?
    public boolean isFull(int row, int col) {
        if (row < 1 || col < 1 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        row--;
        col--;

        int i = convertIndexes(row, col);
        if (uf.find(i) == uf.find(size*size)) {
            return true;
        }
        return false;
    }

    // returns the number of open sites
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }

    // does the system percolate?
    public boolean percolates() {
        if (uf.find(size*size + 1) == uf.find(size*size)) {
            return true;
        }
        return false;
    }

    public static void main(String[] args) {
        if (args.length != 1) {
            System.out.println("Usage: Percolation [n]");
            return;
        }
        int size = Integer.parseInt(args[0]);
        if (size < 0) {
            throw new java.lang.IllegalArgumentException();
        }

        Percolation perc = new Percolation(size);
        
        int[] randCoordinates = new int[size*size];
        for (int i = 0; i < size*size; i++) {
            randCoordinates[i] = i;
        }
        StdRandom.shuffle(randCoordinates);
        
        int i = 0;
        while (!perc.percolates()) {
            perc.open(randCoordinates[i] / size + 1, randCoordinates[i] % size + 1);
            i++;
        }
        
        System.out.println();
        perc.visualise();
        System.out.println(perc.numberOfOpenSites());
        System.out.println(size*size);
    }
}
