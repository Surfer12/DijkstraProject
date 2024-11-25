package legacyalgo.generic.graphs;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Collections;

// The graph shows:

// 6 vertices (1-6)
// 5 directed edges with weights:
// 1→2 (weight 4)
// 1→3 (weight 2)
//  2→4 (weight 5)
// 2→5 (weight 1)
// 3→6 (weight 3)

// Vertices 4, 5, and 6 have no outgoing edges

// NB: It is a weighted graph

public class WeightedGraph implements Graph<Integer> {
    // Use a HashMap to store the graph structure
    private Map<Integer, Map<Integer, Integer>> graph;

    // Constructor to initialize the graph
    public WeightedGraph() {
        this.graph = new HashMap<>();
    }

    @Override
    public void addVertex(Integer vertex) {
        // Only add the vertex if it doesn't already exist
        if (!graph.containsKey(vertex)) {
            graph.put(vertex, new HashMap<>());
        }
    }

    @Override
    public void addEdge(Integer source, Integer destination) {
        // Default weight of 1 if not specified
        addEdge(source, destination, 1);
    }

    @Override
    public void addEdge(Integer source, Integer destination, int weight) {
        // Ensure source vertex exists
        addVertex(source);
        // Ensure destination vertex exists
        addVertex(destination);
        
        // Add the edge with its weight
        graph.get(source).put(destination, weight);
    }

    @Override
    public void removeVertex(Integer vertex) {
        // Remove the vertex and all edges pointing to it
        if (graph.containsKey(vertex)) {
            graph.remove(vertex);
            
            // Remove edges pointing to this vertex from other vertices
            for (Map<Integer, Integer> edges : graph.values()) {
                edges.remove(vertex);
            }
        }
    }

    @Override
    public void removeEdge(Integer source, Integer destination) {
        // Remove the specific edge if it exists
        if (graph.containsKey(source)) {
            graph.get(source).remove(destination);
        }
    }

    @Override
    public Set<Integer> getNeighbors(Integer vertex) {
        // Return the set of neighboring vertices
        if (graph.containsKey(vertex)) {
            return graph.get(vertex).keySet();
        }
        return Collections.emptySet();
    }

    @Override
    public void printGraph() {
        // Print the graph structure
        for (var entry : graph.entrySet()) {
            System.out.println("Node " + entry.getKey() + " connected to: " + entry.getValue());
        }
    }

    @Override
    public int getVertices() {
        // Return the number of vertices
        return graph.size();
    }

    @Override
    public Object getAdjList() {
        // Return the entire graph structure
        return graph;
    }

    public static void main(String[] args) {
        Map<Integer, Map<Integer, Integer>> graph = new HashMap<>();
                
        // Add edges as per the given graph
        graph.put(1, new HashMap<>(Map.of(2, 4, 3, 2))); // Node 1 connected to 2 (weight 4) and 3 (weight 2)
        graph.put(2, new HashMap<>(Map.of(4, 5, 5, 1))); // Node 2 connected to 4 (weight 5) and 5 (weight 1)
        graph.put(3, new HashMap<>(Map.of(6, 3))); // Node 3 connected to 6 (weight 3)
        graph.put(4, new HashMap<>()); // Node 4 has no outgoing edges
        graph.put(5, new HashMap<>()); // Node 5 has no outgoing edges
        graph.put(6, new HashMap<>()); // Node 6 has no outgoing edges
        
        // Print the graph
        System.out.println("Graph representation (Adjacency Map):");
        for (var entry : graph.entrySet()) {
            System.out.println("Node " + entry.getKey() + " connected to: " + entry.getValue());
        }
    }

    public static class Node {

        public int vertex;
        public int weight;

        public Node() {
        }

        public Node(int startVertex, int i) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
