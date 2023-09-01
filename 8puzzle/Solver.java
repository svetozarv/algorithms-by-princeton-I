import java.util.Comparator;
import java.util.LinkedList;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

    private SearchNode answerNode;
    private SearchNode initialRoot;


    private class SearchNode implements Comparable<SearchNode>{
        
        public Board board;
        public int moves = 0;
        public SearchNode prevSearchNode;

        public SearchNode(Board board, SearchNode prevSearchNode) {
            this.board = board;
            this.prevSearchNode = prevSearchNode;
        }

        public int priority() {
            return board.manhattan() + moves;
        }

        public int compareTo(SearchNode that) {
            if (this.priority() < that.priority()) return -1;
            if (this.priority() > that.priority()) return 1;
            return 0;
        }

        public Comparator<SearchNode> priorityComparator() {
            return new ByPriority();
        }

        private class ByPriority implements Comparator<SearchNode> {
            public int compare(SearchNode a, SearchNode b) {
                return a.compareTo(b);
            }
        }
    }

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        if (initial == null) throw new IllegalArgumentException();
        initialRoot = new SearchNode(initial, null);
    }

    // is the initial board solvable?
    public boolean isSolvable() {
        if (initialRoot.board.isGoal()) {
            answerNode = initialRoot;
            return true;
        }

        MinPQ<SearchNode> pqInitial = new MinPQ<>(initialRoot.priorityComparator());
        MinPQ<SearchNode> pqTwin = new MinPQ<>(initialRoot.priorityComparator());
        SearchNode twinRoot = new SearchNode(initialRoot.board.twin(), null);
        pqInitial.insert(initialRoot);
        pqTwin.insert(twinRoot);

        while (true) {
            SearchNode minPriorityInitialNode = pqInitial.delMin();
            SearchNode minPriorityTwinNode = pqTwin.delMin();
            
            if (minPriorityInitialNode.board.isGoal()) {
                answerNode = minPriorityInitialNode;
                return true;
            }
            if (minPriorityTwinNode.board.isGoal()) {
                answerNode = null;
                return false;
            }

            for (Board neighbor : minPriorityInitialNode.board.neighbors()) {
                SearchNode node = new SearchNode(neighbor, minPriorityInitialNode);
                if (minPriorityInitialNode.prevSearchNode != null) {
                    if (neighbor.equals(minPriorityInitialNode.prevSearchNode.board)) {
                        node.moves++;
                        pqInitial.insert(node);
                    }
                }
            }
            for (Board neighbor : minPriorityTwinNode.board.neighbors()) {
                SearchNode node = new SearchNode(neighbor, minPriorityTwinNode);
                if (minPriorityTwinNode.prevSearchNode != null) {
                    if (neighbor.equals(minPriorityTwinNode.prevSearchNode.board)) {
                        node.moves++;
                        pqTwin.insert(node);
                    }
                }
            }
        }
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return answerNode.moves;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        LinkedList<Board> solution = new LinkedList<>();
        SearchNode node = answerNode;
        while (node != null) {
            solution.addFirst(node.board);
            node = node.prevSearchNode;
        }
        return solution;
    }

    // test client 
    public static void main(String[] args) {
        // create initial board from file
        In in = new In("puzzle3x3-09.txt");
        int n = in.readInt();
        int[][] tiles = new int[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++)
                tiles[i][j] = in.readInt();
        Board initial = new Board(tiles);
        
        // solve the puzzle
        Solver solver = new Solver(initial);
        
        // print solution to standard output
        if (!solver.isSolvable())
            StdOut.println("No solution possible");
        else {
            StdOut.println("Minimum number of moves = " + solver.moves());
            for (Board board : solver.solution())
                StdOut.println(board);
        }
    }
}
