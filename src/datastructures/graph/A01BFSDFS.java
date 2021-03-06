package datastructures.graph;

import datastructures.graph.types.DirectedGraphAdjacencyList;

import java.util.*;

public class A01BFSDFS {
    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // DFS BFS Iterative and Recursive
    public void dfs(DirectedGraphAdjacencyList graph) {
        // O(V + E)
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] vis = new boolean[v];

        for (int i = 0; i < v; i++) {
            if (!vis[i]) {                          // To iterate over non connected components
                dfsUtil(i, adj, vis);
            }
        }
    }

    private void dfsUtil(int src, List<Integer>[] adj, boolean[] vis) {
        vis[src] = true;
        System.out.print(src + " ");
        for (int childInd : adj[src]) {
            if (!vis[childInd]) {
                dfsUtil(childInd, adj, vis);
            }
        }
    }

    public void dfsIterative(DirectedGraphAdjacencyList graph) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];

        for (int i = 0; i < v; i++) {
            if (!visited[i]) {                      // To iterate over non connected components
                dfsIterativeUtil(i, adj, visited);
            }
        }
    }

    private void dfsIterativeUtil(int source, List<Integer>[] adj, boolean[] visited) {
        Stack<Integer> st = new Stack<>();
        visited[source] = true;
        st.add(source);
        while (!st.isEmpty()) {
            // HINT: Popped element is already marked as visited
            Integer popped = st.pop();
            List<Integer> children = adj[popped];
            System.out.print(popped + " ");
            for (int i = children.size() - 1; i >= 0; i--) {
                int child = children.get(i);
                if (!visited[child]) {
                    // HINT: Set visited[child] = true when adding it to the stack
                    visited[child] = true;
                    st.add(child);
                }
            }
        }
    }

    public void bfs(DirectedGraphAdjacencyList graph, int source) {
        // SAMPLE INPUT
        /*
         * DirectedGraphAdjacencyList g = new DirectedGraphAdjacencyList(11);
         * g.addEdge(0, 1); g.addEdge(0, 2); g.addEdge(1, 4); g.addEdge(2, 3);
         * g.addEdge(3, 1); g.addEdge(3, 4); g.addEdge(4, 5);
         *
         * g.addEdge(6, 7); g.addEdge(7, 8); g.addEdge(7, 9); g.addEdge(8, 6);
         * g.addEdge(8, 9); g.addEdge(8, 10);
         */

        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];
        Queue<Integer> que = new LinkedList<>();
        bfsUtil(source, que, adj, visited);

        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                bfsUtil(i, que, adj, visited);
                System.out.println();
            }
        }
    }

    private void bfsUtil(int source, Queue<Integer> que, List<Integer>[] adj, boolean[] visited) {
        visited[source] = true;
        que.add(source);
        while (!que.isEmpty()) {
            // HINT: Popped element is already marked as visited
            Integer popped = que.poll();
            System.out.print(popped + " ");
            for (int child : adj[popped]) {
                if (!visited[child]) {
                    // HINT: Set visited[child] = true when adding it to the queue
                    visited[child] = true;
                    que.add(child);
                }
            }
        }
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Mother vertex
    public void findMotherVertexBruteForce(DirectedGraphAdjacencyList graph) {
        // Time : O(V (V + E))
        // Space : O(V)
        // EXPLANATION: DFS on each vertex. First vertex for which all visited are true
        // after a DFS is the one of the mother vertices

        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();

        int mother = -1;
        for (int i = 0; i < v; i++) {
            boolean[] vis = new boolean[v];
            if (!vis[i]) {
                findMotherVertexBruteForceUtil(i, adj, vis);
            }
            if (allVisited(vis)) {
                mother = i;
            }
        }
        if (mother != -1) {
            System.out.println("Mother vertex is : " + mother);
        } else {
            System.out.println("Mother vertex is not found ");
        }
    }

    private void findMotherVertexBruteForceUtil(int ind, List<Integer>[] adj, boolean[] vis) {
        vis[ind] = true;
        for (int childInd : adj[ind]) {
            if (!vis[childInd]) {
                findMotherVertexBruteForceUtil(childInd, adj, vis);
            }
        }
    }

    private boolean allVisited(boolean[] vis) {
        for (boolean b : vis) {
            if (!b) {
                return false;
            }
        }
        return true;
    }

    List<Integer> finished = new ArrayList<>();

    // Kosarajus Strongly connected component algo
    public void findMotherVertexKosaraju(DirectedGraphAdjacencyList graph) {
        // O(V + E)
        // Also called Kosarajus Strongly connected component algo
        // EXPLANATION: If there exists a mother vertex (or vertices),
        // then one of the mother vertices is the last finished vertex in DFS.
        // (Or a mother vertex has the maximum finish time in DFS traversal)

        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();

        boolean[] visited = new boolean[v];
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                findMotherVertexKosarajuUtil(i, adj, visited);
            }
        }

        int lastFin = finished.get(finished.size() - 1);
        visited = new boolean[v];
        findMotherVertexKosarajuUtil(lastFin, adj, visited);
        if (allVisited(visited)) {
            System.out.println("Mother vertex is : " + lastFin);
        } else {
            System.out.println("Mother vertex is not found ");
        }
    }

    private void findMotherVertexKosarajuUtil(int src, List<Integer>[] adj, boolean[] visited) {
        visited[src] = true;
        for (int child : adj[src]) {
            if (!visited[child]) {
                findMotherVertexKosarajuUtil(child, adj, visited);
            }
        }
        finished.add(src);
    }

    public void printTransitiveClosure(DirectedGraphAdjacencyList graph) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();

        int[][] mat = new int[v][v];
        for (int i = 0; i < v; i++) {
            printTransitiveClosureUtil(i, adj, mat[i]);
        }

        for (int i = 0; i < v; i++) {
            System.out.println(Arrays.toString(mat[i]));
        }
    }

    private void printTransitiveClosureUtil(int i, List<Integer>[] adj, int[] vis) {
        vis[i] = 1;
        for (int child : adj[i]) {
            if (vis[child] == 0) {
                printTransitiveClosureUtil(child, adj, vis);
            }
        }
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Count numbers

    public void countNumberOfNodesAtGivenLevel(DirectedGraphAdjacencyList graph, int level) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();

        int res = countNumberOfNodesAtGivenLevelUtil(v, adj, level);
        System.out.println(res);
    }

    private int countNumberOfNodesAtGivenLevelUtil(int vertices, List<Integer>[] adj, int targetLevel) {
        boolean[] visited = new boolean[vertices];
        Queue<Integer> que = new LinkedList<>();
        que.add(0);
        que.add(null);
        visited[0] = true;

        int currLevel = 0;
        int size = 0;
        while (!que.isEmpty()) {
            Integer popped = que.poll();
            if (popped == null) {
                if (currLevel == targetLevel) {
                    return size;
                }
                que.add(null);
                currLevel++;
                size = 0;
            } else {
                size++;
                for (int child : adj[popped]) {
                    if (!visited[child]) {
                        // HINT: MARK VISITED TRUE WHEN ADDING TO QUEUE
                        visited[child] = true;
                        que.add(child);
                    }
                }
            }
        }
        return -1;
    }

    /*
     * public void waterJugProblem(int m, int n, int d) { if (d >= n) {
     * System.out.println("Invalid input"); return; } int i = 0, j = 0; while (!((i
     * == 0 && j == d) || (i == d && j == 0))) { i = m; while (i > 0 && j < n) {
     * i--; j++; } j = 0; while (i > 0 && j < n) { i--; j++; } if ((i == 0 && j ==
     * d) || (i == d && j == 0)) { break; } } System.out.println(" i = " + i +
     * " j = " + j); }
     */

    public void countNumberOfTreesInAForest(DirectedGraphAdjacencyList graph) {
        // SAMPLE INPUT
        /*
         * DirectedGraphAdjacencyList g = new DirectedGraphAdjacencyList(5);
         * g.addEdge(0, 1); g.addEdge(1, 3); g.addEdge(2, 0); g.addEdge(2, 1);
         * g.addEdge(3, 4);
         */

        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];

        int forests = 0;
        for (int i = 0; i < v; i++) {
            if (!visited[i]) {
                countNumberOfTreesInAForestUtil(i, visited, adj);
                forests++;
            }
        }
        System.out.println("Number of forests = " + forests);
    }

    private void countNumberOfTreesInAForestUtil(int source, boolean[] visited, List<Integer>[] adj) {
        visited[source] = true;
        for (int child : adj[source]) {
            if (!visited[child]) {
                countNumberOfTreesInAForestUtil(child, visited, adj);
            }
        }
    }

    public void levelOfEachNodeUsingBFS(DirectedGraphAdjacencyList graph, int source) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];
        int[] level = new int[v];
        Queue<Integer> que = new LinkedList<>();
        que.add(source);
        visited[source] = true;
        level[source] = 0;

        while (!que.isEmpty()) {
            Integer popped = que.poll();
            for (int child : adj[popped]) {
                if (!visited[child]) {
                    visited[child] = true;
                    level[child] = 1 + level[popped];               // Set level for each vertex
                    que.add(child);
                }
            }
        }

        for (int i = 0; i < level.length; i++) {
            System.out.println(i + "  " + level[i]);
        }
    }

    //https://www.geeksforgeeks.org/transpose-graph/
    public void transposeGraph(DirectedGraphAdjacencyList graph) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        System.out.println("ORIGINAL GRAPH IS : ");
        for (int i = 0; i < v; i++) {
            System.out.println(i + " " + adj[i]);
        }
        System.out.println();

        DirectedGraphAdjacencyList transpose = new DirectedGraphAdjacencyList(v);

        for (int i = 0; i < v; i++) {
            for (int child : adj[i]) {
                transpose.addEdge(child, i);
            }
        }

        List<Integer>[] adjTr = transpose.getAdj();
        System.out.println("TRANSPOSE GRAPH IS : ");
        for (int i = 0; i < v; i++) {
            System.out.println(i + " " + adjTr[i]);
        }
    }

    // THIS IS BFS TECHNIQUE TO CHECK 2-COLORING
    // ALSO CHECK mColoringDecisionProblem FOR DFS TECHNIQUE
    // Graph is Bipartite if it is possible to colour its vertices with 2 colours
    public void checkWhetherGraphIsBipartite(DirectedGraphAdjacencyList graph) {
        // SAMPLE INPUT
        /*
         * DirectedGraphAdjacencyList g = new DirectedGraphAdjacencyList(4);
         * g.addEdge(0, 1); g.addEdge(0, 3); g.addEdge(1, 0); g.addEdge(1, 2);
         * g.addEdge(2, 1); g.addEdge(2, 3); g.addEdge(3, 0); g.addEdge(3, 2);
         */

        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();

        boolean res = checkWhetherGraphIsBipartiteUtil(0, v, adj);
        System.out.println(res);
    }

    private boolean checkWhetherGraphIsBipartiteUtil(int source, int v, List<Integer>[] adj) {
        boolean[] visited = new boolean[v];
        int[] color = new int[v];
        Arrays.fill(color, -1);
        Queue<Integer> que = new LinkedList<>();
        visited[source] = true;
        color[source] = 1;
        que.add(source);

        while (!que.isEmpty()) {
            Integer popped = que.poll();
            for (int child : adj[popped]) {
                if (child == popped) {                          // If self loop, then return false
                    return false;
                }
                if (!visited[child]) {
                    if (color[child] == -1) {                   // If color unassigned to child, then assign color
                        color[child] = 1 - color[popped];       // 1-0 = 1 and 1-1 = 0
                    } else if (color[child] == color[popped]) { // If color assigned but is equal to that of popped, return false
                        return false;
                    }
                    visited[child] = true;
                    que.add(child);
                }
            }
        }
        return true;
    }

    public void printAllPathsFromGivenSourceToDestinationUsingDFS(DirectedGraphAdjacencyList graph, int src, int dest) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];
        int path[] = new int[v];
        printAllPathsFromGivenSourceToDestinationUsingDFSUtil(src, dest, adj, visited, path, 0);
    }

    private void printAllPathsFromGivenSourceToDestinationUsingDFSUtil(int src, int dest, List<Integer>[] adj,
                                                                       boolean[] visited, int[] path, int index) {
        visited[src] = true;
        if (src == dest) {
            path[index] = src;
            for (int i = 0; i <= index; i++) {
                System.out.print(path[i] + " ");
            }
            System.out.println();
            return;
        }
        path[index] = src;
        for (int child : adj[src]) {
            if (!visited[child]) {
                printAllPathsFromGivenSourceToDestinationUsingDFSUtil(child, dest, adj, visited, path, index + 1);
                visited[child] = false;     // Typical DFS Backtracking
            }
        }
    }

    public void minimumNumberOfEdgesBetweenTwoVertices(DirectedGraphAdjacencyList graph, int src, int dest) {
        // RULE : BFS between src and dest
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];
        int[] level = new int[v];
        Arrays.fill(level, -1);
        Queue<Integer> que = new LinkedList<>();
        que.add(src);                                           // Start BFS from src and end BFS when level of dest is set
        visited[src] = true;
        level[src] = 0;

        int res = -1;
        while (!que.isEmpty()) {
            Integer popped = que.poll();
            for (int child : adj[popped]) {
                if (!visited[child]) {
                    level[child] = 1 + level[popped];           // Same as levelOfEachNodeUsingBFS
                    if (level[dest] != -1) {                    // when level of dest is set
                        res = level[dest];
                        break;
                    }
                    visited[child] = true;                      // Same as regular BFS : Mark as visited before adding to queue
                    que.add(child);
                }
            }
        }
        System.out.println("Minimum distance between " + src + " and " + dest + " = " + res);
    }

    static class Node {
        int x;
        int y;

        Node() {

        }

        Node(int i, int j) {
            this.x = i;
            this.y = j;
        }

        @Override
        public int hashCode() {
            final int prime = 31;
            int result = 1;
            result = prime * result + x;
            result = prime * result + y;
            return result;
        }

        @Override
        public boolean equals(Object obj) {
            if (this == obj)
                return true;
            if (obj == null)
                return false;
            if (getClass() != obj.getClass())
                return false;
            Node other = (Node) obj;
            if (x != other.x)
                return false;
            if (y != other.y)
                return false;
            return true;
        }
    }

    // RAKESH: visited ka panga : Backtracking mein revert krna hota hai as realized
    static int moves = Integer.MAX_VALUE;
    static int[][] nextMove = new int[][]{{0, -1, 0, 1}, {-1, 0, 1, 0}};

    // https://www.geeksforgeeks.org/find-minimum-numbers-moves-needed-move-one-cell-matrix-another/
    public static void findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingDFS() {
        int[][] mat = {{3, 3, 1, 0},
                {3, 0, 3, 3},
                {2, 3, 0, 3},
                {0, 3, 3, 3}
        };
        Node src = new Node();
        Node dest = new Node();
        retrieveSrcDest(src, dest, mat);            //Find node with 1, 2, 3 as src or dest
        HashSet<Node> visited = new HashSet<>();
        findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingDFSUtil(src, dest, visited, 0, mat);
        System.out.println("Minimum number of moves = " + moves);
    }

    private static void findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingDFSUtil(Node src, Node dest,
                                                                                                        HashSet<Node> visited, int movesTillHere, int[][] mat) {
        if (src.x == dest.x && src.y == dest.y) {
            moves = Math.min(moves, movesTillHere);
            return;
        }
        visited.add(src);                                                       // Typical DFS
        List<Node> neighbours = findNeighbours(src);                            // Up, down, left, right
        for (Node neighbour : neighbours) {
            if (isValidNeighbour(neighbour, visited, mat)) {
                findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingDFSUtil(neighbour, dest,
                        visited, movesTillHere + 1, mat);           // movesTillHere + 1
                visited.remove(neighbour);                                      // Typical DFS Backtracking inside for loop
            }
        }
        // visited.remove(src);        // Typical DFS Backtracking outside for loop : EITHER THIS OR inside for loop
    }

    private static boolean isValidNeighbour(Node neighbour, HashSet<Node> visited, int[][] mat) {
        if (visited.contains(neighbour)
                || (neighbour.x < 0 || neighbour.x >= mat.length || neighbour.y < 0 || neighbour.y >= mat[0].length)
                || mat[neighbour.x][neighbour.y] == 0) {        // Blocked at 0
            return false;
        }
        return true;
    }

    private static List<Node> findNeighbours(Node src) {
        List<Node> neighbours = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            Node neighbour = new Node(src.x + nextMove[0][i], src.y + nextMove[1][i]);
            neighbours.add(neighbour);
        }
        return neighbours;
    }

    private static void retrieveSrcDest(Node src, Node dest, int[][] mat) {
        int row = mat.length;
        int col = mat[0].length;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                if (mat[i][j] == 1) {
                    src.x = i;
                    src.y = j;
                } else if (mat[i][j] == 2) {
                    dest.x = i;
                    dest.y = j;
                }
            }
        }
    }

    public void findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingGraphAndBFS(int[][] mat) {
        DirectedGraphAdjacencyList graph = new DirectedGraphAdjacencyList(mat.length * mat[0].length + 1);
        int k = 1;
        int src = -1;
        int dest = -1;
        int colSize = mat[0].length;

        for (int i = 0; i < mat.length; i++) {
            for (int j = 0; j < mat[0].length; j++) {
                if (mat[i][j] != 0) {
                    if (isValidNeighbour(i, j - 1, mat)) {
                        graph.addEdge(k, k - 1);
                    }
                    if (isValidNeighbour(i, j + 1, mat)) {
                        graph.addEdge(k, k + 1);
                    }
                    if (isValidNeighbour(i - 1, j, mat)) {
                        graph.addEdge(k, k - colSize);
                    }
                    if (isValidNeighbour(i + 1, j, mat)) {
                        graph.addEdge(k, k + colSize);
                    }
                }
                if (mat[i][j] == 1) {
                    src = k;
                } else if (mat[i][j] == 2) {
                    dest = k;
                }
                k++;
            }
        }

        int moves = findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingGraphAndBFSUtil(graph, src,
                dest, mat.length * mat[0].length + 1);
        System.out.println("Minimum number of moves = " + moves);
    }

    private boolean isValidNeighbour(int i, int j, int[][] mat) {
        if (i < 0 || i >= mat.length || j < 0 || j >= mat[0].length || mat[i][j] == 0) {
            return false;
        }
        return true;
    }

    // Similar to minimumNumberOfEdgesBetweenTwoVertices
    private int findTheMinimumNumberOfMovesNeededToMoveFromOneCellOfMatrixToAnotherUsingGraphAndBFSUtil(
            DirectedGraphAdjacencyList graph, int src, int dest, int vertices) {
        List<Integer>[] adj = graph.getAdj();
        int[] level = new int[vertices];
        Arrays.fill(level, -1);
        Queue<Integer> que = new LinkedList<>();

        que.add(src);
        level[src] = 0;                                                         // Used as visited

        while (!que.isEmpty()) {
            Integer popped = que.poll();
            for (int child : adj[popped]) {
                if (level[child] < 0 || level[child] > level[popped] + 1) {     // level[child] < 0 : NOT VISITED
                    que.add(child);
                    level[child] = level[popped] + 1;
                }
            }
        }
        return level[dest];
    }
}