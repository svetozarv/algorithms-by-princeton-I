import java.util.ArrayList;
import java.util.List;

import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;


class Percolation {
    int size;
    private int num_of_open_sites;
    private int[][] grid;
    private WeightedQuickUnionUF uf;
    double threshold;

    public Percolation(int size) {
        if (size <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.num_of_open_sites = 0;
        this.grid = new int[size][size];

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                grid[i][j] = 0;
            }
        }

        this.uf = new WeightedQuickUnionUF(size * size + 2);
        for (int i = 0; i < this.size; i++) {
            this.uf.union(i, );
        }
        for (int i = this.size * this.size - this.size; i < this.size * this.size; i++) {
            this.uf.union(i,);
        }
    }

    public void visualise() {
        System.out.println();
        for (int[] row : grid) {
            for (int i : row) {
                System.out.print(i + " ");
            }
            System.out.println();
        }
        System.out.println();
    }

    public int convertIndexes(int row, int col) {
        return size * row + col;
    }

    public boolean connected(int p, int q) {
        return uf.find(p) == uf.find(q);
    }

    public void open(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException();
        }
        grid[row][col] = 1;
        num_of_open_sites++;

        // Check on the left
        if (col != 0 && grid[row][col - 1] == 1) {
            int i = convertIndexes(row, col);
            int j = convertIndexes(row, col - 1);
            uf.union(i, j);
        }

        // Check on the right
        if (col != size - 1 && grid[row][col + 1] == 1) {
            int i = convertIndexes(row, col);
            int j = convertIndexes(row, col + 1);
            uf.union(i, j);
        }

        // Check above
        if (row != 0 && grid[row - 1][col] == 1) {
            int i = convertIndexes(row, col);
            int j = convertIndexes(row - 1, col);
            uf.union(i, j);
        }

        // Check below
        if (row != size - 1 && grid[row + 1][col] == 1) {
            int i = convertIndexes(row, col);
            int j = convertIndexes(row + 1, col);
            uf.union(i, j);
        }
    }

    public boolean isOpen(int row, int col) {
        if (row < 0 || col < 0 || row >= size || col >= size) {
            throw new IllegalArgumentException();
        }
        return grid[row][col] == 1;
    }

    public boolean isFull(int row, int col) {
        if (row < 0 || col < 0 || row > size || col > size) {
            throw new IllegalArgumentException();
        }
        int i = convertIndexes(row, col);
        if (connected(i, size * size + 1)) {
            return true;
        }
        return false;
    }

    public int numberOfOpenSites() {
        return num_of_open_sites;
    }

    public boolean percolates() {
        if (isFull(size, 0)) {
            threshold = num_of_open_sites / Math.pow(size, 2);
            return true;
        }
        return false;
    }
}
