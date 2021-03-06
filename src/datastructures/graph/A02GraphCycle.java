package datastructures.graph;

import datastructures.graph.types.DirectedGraphAdjacencyList;
import datastructures.graph.types.GraphEdgeArray;
import datastructures.graph.types.GraphEdgeArray.Edge;

import java.util.*;

public class A02GraphCycle {
    public static void main(String[] args) {
        detectCycleInDirectedGraphWithDFS();
        //detectCycleInDirectedGraphUsingDFS();
    }

    // Directed Graph
    public static void detectCycleInDirectedGraphWithDFS() {
        DirectedGraphAdjacencyList graph = new DirectedGraphAdjacencyList(4);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        graph.addEdge(1, 2);
        graph.addEdge(2, 0);
        graph.addEdge(2, 3);
        graph.addEdge(3, 3);

        boolean isCyclic = false;
        int V = graph.getV();

        Set<Integer> visited = new HashSet<>();         // Use visited only to skip a previously traversed path
        Set<Integer> recStack = new HashSet<>();        // Use recStack to keep track of vertices on recursion stack

        for (int i = 0; i < V; i++) {
            if (detectCycleInDirectedGraphWithDFSUtil(i, recStack, visited, graph.getAdj())) {
                isCyclic = true;
                break;
            }
        }

        System.out.println(isCyclic);
    }

    private static boolean detectCycleInDirectedGraphWithDFSUtil(int src, Set<Integer> recStack, Set<Integer> visited, ArrayList<Integer>[] adj) {
        if (recStack.contains(src)) {                                      // First check if present on recStack
            return true;
        }
        if (visited.contains(src)) {                                       // Else if already visited, don't visit again
            return false;                                                  // If previously no cycle was found, visiting it again won't give a cycle now
        }
        visited.add(src);
        recStack.add(src);
        for (int child : adj[src]) {
            if (detectCycleInDirectedGraphWithDFSUtil(child, recStack, visited, adj)) {           // Cant put '!visited.contains(child) &&' : otherwise it won't even go to vertices on the current stack
                return true;
            }
        }

        recStack.remove(src);                                               // Typical DFS Backtracking
        return false;
    }

    // Undirected Graph
    // Use this to check if a Graph is tree or not : No cycles and connected (all visited)
    public void detectCycleInUndirectedGraphUsingDFS(DirectedGraphAdjacencyList graph) {
        int v = graph.getV();
        List<Integer>[] adj = graph.getAdj();
        boolean[] visited = new boolean[v];

        boolean isCyclic = false;
        int parent = -1;
        for (int i = 0; i < v; i++) {
            if (!visited[i] && detectCycleInUndirectedGraphUsingDFSUtil(i, adj, visited, parent)) {
                isCyclic = true;
                break;
            }
        }

        System.out.println(isCyclic);

    }

    private boolean detectCycleInUndirectedGraphUsingDFSUtil(int src, List<Integer>[] adj, boolean[] visited,
                                                             int parent) {
        visited[src] = true;
        for (int child : adj[src]) {
            if (!visited[child]) {
                if (detectCycleInUndirectedGraphUsingDFSUtil(child, adj, visited, src)) {
                    return true;
                }
            } else if (child != parent) {
                // HINT: If an adjacent vertex is visited but not parent of current vertex, then
                // there is a cycle
                return true;
            }
        }
        return false;
    }

    /*public static void main(String[] args) {
        detectCycleInUndirectedGraphUsingDisjointSets();
    }*/

    public static void detectCycleInUndirectedGraphUsingDisjointSets() {
        // GFG : https://www.geeksforgeeks.org/union-find-algorithm-set-2-union-by-rank/
        // ABDUL BARI : https://www.youtube.com/watch?v=wU6udHRIkcc
        // SAMPLE INPUT FOR CYCLE PRESENT
        GraphEdgeArray gea = new GraphEdgeArray(8, 9);
        gea.edges[0].src = 0;
        gea.edges[0].dest = 1;
        gea.edges[0].wt = 0;

        gea.edges[1].src = 2;
        gea.edges[1].dest = 3;
        gea.edges[1].wt = 1;

        gea.edges[2].src = 4;
        gea.edges[2].dest = 5;
        gea.edges[2].wt = 2;

        gea.edges[3].src = 6;
        gea.edges[3].dest = 7;
        gea.edges[3].wt = 3;

        gea.edges[4].src = 1;
        gea.edges[4].dest = 3;
        gea.edges[4].wt = 4;

        gea.edges[5].src = 1;
        gea.edges[5].dest = 4;
        gea.edges[5].wt = 5;

        gea.edges[6].src = 0;
        gea.edges[6].dest = 2;
        gea.edges[6].wt = 6;

        gea.edges[7].src = 5;
        gea.edges[7].dest = 7;
        gea.edges[7].wt = 7;

        gea.edges[8].src = 4;
        gea.edges[8].dest = 6;
        gea.edges[8].wt = 8;

        // SAMPLE INPUT FOR CYCLE ABSENT
        // GraphEdgeArray gea = new GraphEdgeArray(8, 7);
        // gea.edges[0].src = 0;
        // gea.edges[0].dest = 1;
        // gea.edges[0].wt = 0;

        // gea.edges[1].src = 2;
        // gea.edges[1].dest = 3;
        // gea.edges[1].wt = 1;

        // gea.edges[2].src = 4;
        // gea.edges[2].dest = 5;
        // gea.edges[2].wt = 2;

        // gea.edges[3].src = 6;
        // gea.edges[3].dest = 7;
        // gea.edges[3].wt = 3;

        // gea.edges[4].src = 1;
        // gea.edges[4].dest = 4;
        // gea.edges[4].wt = 4;

        // gea.edges[5].src = 0;
        // gea.edges[5].dest = 2;
        // gea.edges[5].wt = 5;

        // gea.edges[6].src = 5;
        // gea.edges[6].dest = 7;
        // gea.edges[6].wt = 6;

        int V = gea.V;
        GraphEdgeArray.Edge[] edges = gea.edges;

        // PARENT ARRAY FOR DISJOINT SET
        int[] parent = new int[V];
        Arrays.fill(parent, -1);
        for (Edge e : edges) {
            int u = find(parent, e.src);
            int v = find(parent, e.dest);
            if (u == v) {
                System.out.println("CYCLE DETECTED");
                return;
            }
            union(parent, u, v);
        }
        System.out.println("NO CYCLE DETECTED");
    }

    private static int find(int parent[], int ind) {
        if (parent[ind] < 0) {
            return ind;
        }

        // PATH COMPRESSION DONE ALONG THE WAY
        parent[ind] = find(parent, parent[ind]);

        return parent[ind];
    }

    // Example of Union by weight / weighted union
    // Maintains wt at root level instead of in a separate array
    private static void union(int[] parent, int u, int v) {
        //parent[] will get negative values : -2, -3, etc
        // - means it is a parent, 2 means it has a weight of 2 (also known as rank)
        if (parent[u] <= parent[v]) {
            int temp = parent[v];
            parent[v] = u;
            parent[u] += temp;
        } else {
            int temp = parent[u];
            parent[u] = v;
            parent[v] += temp;
        }
    }
}