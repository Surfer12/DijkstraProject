package legacyalgo.generic.graphs;

import java.util.HashSet;
import java.util.Set;

public class GraphMatrix<T extends Integer> implements Graph<T> {
    private final int vertices;
    private final int[][] adjacencyMatrix;

    public GraphMatrix(int vertices) {
        this.vertices = vertices;
        adjacencyMatrix = new int[vertices][vertices];
    }

    public static void main(String[] args) {
        GraphMatrix<Integer> graph = new GraphMatrix<>(5);
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.printGraph();
    }

    @Override
    public void addVertex(T vertex) {
        // Implementation
    }

    @Override
    public void addEdge(T source, T destination) {
        // For an unweighted graph, simply mark the connection:
        adjacencyMatrix[source][destination] = 1;

        // For an undirected graph, add the reverse edge as well:
        adjacencyMatrix[destination][source] = 1;

        // For a weighted graph, add the weight instead of 1:
        // adjacencyMatrix[source][destination] = weight;
        // adjacencyMatrix[destination][source] = weight;
    }

    @Override
    public void removeVertex(T vertex) {
        // Implementation
    }

    @Override
    public void removeEdge(T source, T destination) {
        adjacencyMatrix[source][destination] = 0;
        adjacencyMatrix[destination][source] = 0; // For undirected graph
    }

    @Override
    public boolean hasEdge(T source, T destination) {
        return adjacencyMatrix[source][destination] != 0;
    }

    @Override
    public Set<T> getNeighbors(int vertex) {
        Set<T> neighbors = new HashSet<>();
        int v = vertex;
        for (int i = 0; i < vertices; i++) {
            if (adjacencyMatrix[v][i] != 0) {
                neighbors.add((T) Integer.valueOf(i));
            }
        }
        return neighbors;
    }

    @Override
    public void printGraph() {
        for (int i = 0; i < vertices; i++) {
            System.out.print("Vertex " + i + " is connected to: ");
            for (int j = 0; j < vertices; j++) {
                if (adjacencyMatrix[i][j] != 0) {
                    System.out.print(j + " ");
                }
            }
            System.out.println();
        }
    }
}
