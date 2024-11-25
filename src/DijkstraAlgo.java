import java.util.Arrays;
import java.util.PriorityQueue;

import generic.graphs.Graph;
import generic.graphs.WeightedGraph;

public class DijkstraAlgo {

    public static <T> int[] dijkstra(Graph<T> graph, int startVertex) {
        int vertices = graph.getVertices();
        int[] distances = new int[vertices];
        boolean[] visited = new boolean[vertices];
        int[] previous = new int[vertices]; // Add previous array to track path

        PriorityQueue<WeightedGraph.Node> pq = new PriorityQueue<>();
        Arrays.fill(distances, Integer.MAX_VALUE);
        Arrays.fill(previous, -1); // Initialize previous array
        distances[startVertex] = 0;
        pq.add(new WeightedGraph.Node(startVertex, 0));

        while (!pq.isEmpty()) {
            int currentVertex = pq.poll().vertex;

            if (visited[currentVertex]) continue;
            visited[currentVertex] = true;

            List<WeightedGraph.Node> neighbors = graph.getAdjList().get(currentVertex);
            for (WeightedGraph.Node neighbor : neighbors) {
                int newDist = distances[currentVertex] + neighbor.weight;

                if (newDist < distances[neighbor.vertex]) {
                    distances[neighbor.vertex] = newDist;
                    previous[neighbor.vertex] = currentVertex; // Track the path
                    pq.add(new WeightedGraph.Node(neighbor.vertex, newDist));
                }
            }
        }

        printPath(startVertex, vertices - 1, previous, distances); // Print path from start to end
        return distances;
    }

    private static void printPath(int start, int end, int[] previous, int[] distances) {
        List<Integer> path = new ArrayList<>();
        int current = end;
        
        while (current != -1) {
            path.add(0, current);
            current = previous[current];
        }

        System.out.print("Path: ");
        for (int vertex : path) {
            System.out.print(vertex + " ");
        }
        System.out.println("\nTotal distance: " + distances[end]);
    }
}