### [Specification](https://coursera.cs.princeton.edu/algs4/assignments/percolation/specification.php)
`Percolation.java` is a simulation, where sites are opened util water can flow from top to bottom. It takes `n` as command-line argument and creates an n-by-n grid. In the end of a program it outputs total number of sites and number of sites opened.
`PercolationStats.java` performs a series of 
# How to run the assignment

### For Windows:
If you have downloaded the library in, say `C:\Users\username\algs4\algs4.jar`.
```
javac -cp .;C:\Users\username\algs4\algs4.jar Percolation.java, PercolationStats.java

java -cp .;C:\Users\username\algs4\algs4.jar Percolation [n]  -  where n is a grid size (n-by-n)

java -cp .;C:\Users\username\algs4\algs4.jar PercolationStats [n] [trials] 
```

### For Linux and Mac OS
Use `:` instead of `;`, then specify full path to the file:
```
javac -cp .:~/algs4/algs4.jar Percolation.java, PercolationStats.java

java -cp .:~/algs4/algs4.jar Percolation [n]  -  where n is a grid size (n-by-n)

java -cp .:~/algs4/algs4.jar PercolationStats [n] [trials] 
```
