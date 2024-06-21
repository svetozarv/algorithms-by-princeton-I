## Overview
`Percolation.java` is a simulation, where sites are randomly opened until water can flow from top to bottom. It takes `n` as command-line argument and creates an n-by-n grid. In the end of a program it outputs total number of sites and number of sites opened.

`PercolationStats.java` performs series of such simulations, calculates probability when the system almost always percolates. It takes `n` and `trials` as command-line argaments, where `n` is grid size and `trials` is number of such simulations. The program returns mean, standart deviation and confidence interval.

### [Detailed specification](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)

## How to run the assignment

### For Windows:
If you have downloaded the library in, say `C:\Users\username\algs4\algs4.jar`.
```
javac -cp .;C:\Users\username\algs4\algs4.jar Percolation.java, PercolationStats.java
```
```
java -cp .;C:\Users\username\algs4\algs4.jar Percolation [n]
```
```
java -cp .;C:\Users\username\algs4\algs4.jar PercolationStats [n] [trials] 
```

### For Linux and Mac OS
Use `:` instead of `;`, then specify full path to the file:
```
javac -cp .:~/algs4/algs4.jar Percolation.java, PercolationStats.java
```
```
java -cp .:~/algs4/algs4.jar Percolation [n]
```
```
java -cp .:~/algs4/algs4.jar PercolationStats [n] [trials] 
```
