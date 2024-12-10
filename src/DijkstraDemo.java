
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

// 1. First, let's demonstrate using the generic implementation
public class DijkstraDemo {
    public static void main(final String[] args) {
        // Create the graph using generic implementation
        final Map<GenericNode<Integer>, Map<GenericNode<Integer>, Integer>> graph = new HashMap<>();

        // Create nodes
        final GenericNode<Integer> node0 = new GenericNode<>(0);
        final GenericNode<Integer> node1 = new GenericNode<>(1);
        final GenericNode<Integer> node2 = new GenericNode<>(2);
        final GenericNode<Integer> node3 = new GenericNode<>(3);
        final GenericNode<Integer> node4 = new GenericNode<>(4);
        final GenericNode<Integer> node5 = new GenericNode<>(5);
        final GenericNode<Integer> node6 = new GenericNode<>(6);
        final GenericNode<Integer> node7 = new GenericNode<>(7);
        final GenericNode<Integer> node8 = new GenericNode<>(8);

        // Initialize adjacency maps for each node
        graph.put(node0, new HashMap<>());
        graph.put(node1, new HashMap<>());
        graph.put(node2, new HashMap<>());
        graph.put(node3, new HashMap<>());
        graph.put(node4, new HashMap<>());
        graph.put(node5, new HashMap<>());
        graph.put(node6, new HashMap<>());
        graph.put(node7, new HashMap<>());
        graph.put(node8, new HashMap<>());

        // Add edges (both directions for undirected graph)
        DijkstraDemo.addEdge(graph, node0, node1, 4);
        DijkstraDemo.addEdge(graph, node0, node6, 7);
        DijkstraDemo.addEdge(graph, node1, node2, 9);
        DijkstraDemo.addEdge(graph, node1, node6, 11);
        DijkstraDemo.addEdge(graph, node1, node7, 20);
        DijkstraDemo.addEdge(graph, node2, node3, 6);
        DijkstraDemo.addEdge(graph, node2, node4, 2);
        DijkstraDemo.addEdge(graph, node3, node5, 5);
        DijkstraDemo.addEdge(graph, node3, node4, 10);
        DijkstraDemo.addEdge(graph, node4, node5, 15);
        DijkstraDemo.addEdge(graph, node4, node7, 1);
        DijkstraDemo.addEdge(graph, node4, node8, 5);
        DijkstraDemo.addEdge(graph, node5, node8, 12);
        DijkstraDemo.addEdge(graph, node6, node7, 1);
        DijkstraDemo.addEdge(graph, node7, node8, 3);

        // Run Dijkstra's algorithm using generic implementation
        System.out.println("Generic Implementation Results:");
        final Map<GenericNode<Integer>, Integer> distances = DijkstraGenericCopy.dijkstra(graph, node0, node5);

        // Print distances
        DijkstraDemo.printGenericDistances(distances);
    }

    // Helper method to add undirected edges in generic graph
    private static <T> void addEdge(
            final Map<GenericNode<T>, Map<GenericNode<T>, Integer>> graph,
            final GenericNode<T> source,
            final GenericNode<T> dest,
            final int weight) {
        graph.get(source).put(dest, weight);
        graph.get(dest).put(source, weight);
    }

    // Helper method to print distances from generic implementation
    private static <T> void printGenericDistances(final Map<GenericNode<T>, Integer> distances) {
        for (final Map.Entry<GenericNode<T>, Integer> entry : distances.entrySet()) {
            System.out.println("Distance to node " + entry.getKey().getData() +
                    ": " + entry.getValue());
        }
    }
}

// 2. Now let's implement the non-generic version for comparison
class NonGenericDijkstra {
    private final int vertices;
    private final List<List<Node>> adjacencyList;

    public NonGenericDijkstra(final int vertices) {
        this.vertices = vertices;
        this.adjacencyList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            this.adjacencyList.add(new ArrayList<>());
        }
    }

    public void addEdge(final int source, final int destination, final int weight) {
        this.adjacencyList.get(source).add(new Node(destination, weight));
        this.adjacencyList.get(destination).add(new Node(source, weight));
    }

    public int[] findShortestPaths(final int startVertex) {
        final int[] distances = new int[this.vertices];
        final boolean[] visited = new boolean[this.vertices];
        final int[] previous = new int[this.vertices];

        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1);
        distances[startVertex] = 0;

        final PriorityQueue<Node> pq = new PriorityQueue<>();
        pq.offer(new Node(startVertex, 0));

        while (!pq.isEmpty()) {
            final Node current = pq.poll();
            final int currentVertex = current.vertex;

            if (visited[currentVertex])
                continue;
            visited[currentVertex] = true;

            for (final Node neighbor : this.adjacencyList.get(currentVertex)) {
                final int newDist = distances[currentVertex] + neighbor.distance;

                if (newDist < distances[neighbor.vertex]) {
                    distances[neighbor.vertex] = newDist;
                    previous[neighbor.vertex] = currentVertex;
                    pq.offer(new Node(neighbor.vertex, newDist));
                }
            }
        }

        this.printPath(startVertex, 5, previous, distances);
        return distances;
    }

    private void printPath(final int start, final int end, final int[] previous, final int[] distances) {
        final List<Integer> path = new ArrayList<>();
        int current = end;

        while (current != -1) {
            path.add(0, current);
            current = previous[current];
        }

        System.out.println("\nNon-Generic Implementation Results:");
        System.out.print("Path: ");
        for (final int vertex : path) {
            System.out.print(vertex + " ");
        }
        System.out.println("\nTotal distance: " + distances[end]);
    }

    // Usage example
    public static void main(final String[] args) {
        final NonGenericDijkstra graph = new NonGenericDijkstra(9);

        // Add same edges as in generic implementation
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 6, 7);
        graph.addEdge(1, 2, 9);
        graph.addEdge(1, 6, 11);
        graph.addEdge(1, 7, 20);
        graph.addEdge(2, 3, 6);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(3, 4, 10);
        graph.addEdge(4, 5, 15);
        graph.addEdge(4, 7, 1);
        graph.addEdge(4, 8, 5);
        graph.addEdge(5, 8, 12);
        graph.addEdge(6, 7, 1);
        graph.addEdge(7, 8, 3);

        final int[] distances = graph.findShortestPaths(0);
    }
}
