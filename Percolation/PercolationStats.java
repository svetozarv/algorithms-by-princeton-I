import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;


public class PercolationStats {
    private static final double CONFIDENCE_95 = 1.96;
    private int trials;
    private double[] thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int size, int trials) {
        if (size <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.trials = trials;
        this.thresholds = new double[trials];

        for (int j = 0; j < trials; j++) {
            Percolation perc = new Percolation(size);
        
            while (!perc.percolates()) {
                int randCoordinates = StdRandom.uniformInt(size*size);
                perc.open(randCoordinates / size + 1, randCoordinates % size + 1);
            }
            double numOfOpenSites = perc.numberOfOpenSites();
            this.thresholds[j] = numOfOpenSites / (size*size);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        return StdStats.mean(thresholds);
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        return Math.sqrt(StdStats.stddev(thresholds));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (CONFIDENCE_95 * this.stddev() / Math.sqrt(this.trials));
    }

    // test client
    public static void main(String[] args) {
        if (args.length != 2) {
            System.out.println("Usage: PercolationStats [n] [T]");
            return;
        }

        int n = Integer.parseInt(args[0]);
        int t = Integer.parseInt(args[1]);
        if (n < 0 || t < 0) {
            throw new java.lang.IllegalArgumentException();
        }

        PercolationStats prcSts = new PercolationStats(n, t);
        System.out.println("mean                    = " + prcSts.mean());
        System.out.println("stddev                  = " + prcSts.stddev());
        System.out.println("95% confidence interval = [" + prcSts.confidenceLo() + "," + prcSts.confidenceHi() + "]");
    }
}
