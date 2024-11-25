package legacyalgo;

import legacyalgo.generic.graphs.Graph;
import java.util.ArrayList;
import java.util.List;

public class WeightedGraph implements Graph<Integer> {
    private final int vertices;
    private final List<List<Node>> adjList;

    public WeightedGraph(int vertices) {
        this.vertices = vertices;
        adjList = new ArrayList<>(vertices);
        for (int i = 0; i < vertices; i++) {
            adjList.add(new ArrayList<>());
        }
    }

    public void addEdge(int source, int destination, int weight) {
        adjList.get(source).add(new Node(destination, weight));
        adjList.get(destination).add(new Node(source, weight)); // For undirected graph
    }

    public int getVertices() {
        return vertices;
    }

    public List<List<Node>> getAdjList() {
        return adjList;
    }

    static class Node implements Comparable<Node> {
        int vertex;
        int weight;

        public Node(int vertex, int weight) {
            this.vertex = vertex;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node other) {
            return Integer.compare(this.weight, other.weight);
        }
    }
}