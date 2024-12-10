
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Set;

class Node implements Comparable<Node> {
    int vertex;
    int distance;

    public Node(final int vertex, final int distance) {
        this.vertex = vertex;
        this.distance = distance;
    }

    @Override
    public int compareTo(final Node other) {
        return Integer.compare(this.distance, other.distance);
    }
}

class Graph {
    private final int V; // number of vertices
    private final List<List<Node>> adj; // adjacency list

    public Graph(final int v) {
        this.V = v;
        this.adj = new ArrayList<>(v);
        for (int i = 0; i < v; i++) {
            this.adj.add(new ArrayList<>());
        }
    }

    public void addEdge(final int u, final int v, final int weight) {
        this.adj.get(u).add(new Node(v, weight));
        this.adj.get(v).add(new Node(u, weight)); // for undirected graph
    }

    public int[] dijkstra(final int start) {
        // Initialize distances array with infinity
        final int[] distances = new int[this.V];
        Arrays.fill(distances, Integer.MAX_VALUE);
        distances[start] = 0;

        // Initialize previous array for path tracking
        final int[] previous = new int[this.V];
        Arrays.fill(previous, -1);

        // Priority queue to store vertices and their current distances
        final PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.add(new Node(start, 0));

        // Set to keep track of processed vertices
        final Set<Integer> processed = new HashSet<>();

        while (!pq.isEmpty()) {
            final Node current = pq.poll();
            final int u = current.vertex;

            if (processed.contains(u))
                continue;
            processed.add(u);

            // Process all adjacent vertices
            for (final Node neighbor : this.adj.get(u)) {
                final int v = neighbor.vertex;
                final int weight = neighbor.distance;

                // Relaxation step
                if (!processed.contains(v) &&
                        distances[u] != Integer.MAX_VALUE &&
                        distances[u] + weight < distances[v]) {

                    distances[v] = distances[u] + weight;
                    previous[v] = u;
                    pq.add(new Node(v, distances[v]));
                }
            }
        }

        return distances;
    }

    // Method to print the path from start to end vertex
    public void printPath(final int[] previous, final int end) {
        if (previous[end] == -1) {
            System.out.print(end);
            return;
        }
        this.printPath(previous, previous[end]);
        System.out.print(" -> " + end);
    }

    // Example usage
    public static void main(final String[] args) {
        final Graph g = new Graph(9); // 0 to 8

        // Add edges from the diagram
        g.addEdge(0, 1, 4);
        g.addEdge(0, 6, 7);
        g.addEdge(1, 2, 9);
        g.addEdge(1, 4, 2);
        g.addEdge(6, 7, 1);
        g.addEdge(2, 3, 6);
        g.addEdge(7, 8, 3);
        g.addEdge(4, 5, 15);

        final int[] distances = g.dijkstra(0);

        // Print distances
        for (int i = 0; i < distances.length; i++) {
            System.out.println("Distance from 0 to " + i + ": " + distances[i]);
        }
    }
}
