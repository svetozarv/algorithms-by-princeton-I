import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PercolationStats {
    private int size;
    private int trials;
    private List<Double> thresholds;

    // perform independent trials on an n-by-n grid
    public PercolationStats(int size, int trials) {
        if (size <= 0 || trials <= 0) {
            throw new IllegalArgumentException();
        }
        this.size = size;
        this.trials = trials;
        this.thresholds = new ArrayList<>();
        for (int i = 0; i < trials; i++) {
            Percolation perc = new Percolation(size);
            List<Integer> random_coordinates = new ArrayList<>();
            for (int k = 0; k < size*size; k++) {
                random_coordinates.add(k);
            }
            Random rand = new Random();
            for (int k = random_coordinates.size() - 1; k > 0; k--) {
                int index = rand.nextInt(k + 1);
                int temp = random_coordinates.get(index);
                random_coordinates.set(index, random_coordinates.get(k));
                random_coordinates.set(k, temp);
            }
            int index = 0;
            while (!perc.percolates()) {
                perc.open(random_coordinates.get(index) / size, random_coordinates.get(index) % size);
                index++;
            }
            this.thresholds.add(perc.threshold);
        }
    }

    // sample mean of percolation threshold
    public double mean() {
        double thresholds_sum = 0;
        for (double threshold : this.thresholds) {
            thresholds_sum += threshold;
        }
        return thresholds_sum / this.trials;
    }

    // sample standard deviation of percolation threshold
    public double stddev() {
        double mean = this.mean();
        double numerator = 0;
        for (double threshold : this.thresholds) {
            numerator += Math.pow(threshold - mean, 2);
        }
        return Math.sqrt(numerator / (this.trials - 1));
    }

    // low endpoint of 95% confidence interval
    public double confidenceLo() {
        return this.mean() - (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    // high endpoint of 95% confidence interval
    public double confidenceHi() {
        return this.mean() + (1.96 * this.stddev() / Math.sqrt(this.trials));
    }

    // test client
    public static void main(String[] args) {

    }
}
