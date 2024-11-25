package legacyalgo.generic.graphs;

import legacyalgo.generic.Graph;
import java.util.*;


public class GraphList<T> implements Graph<T> {
    private Map<T, Set<T>> adjacencyList;

    public GraphList() {
        adjacencyList = new HashMap<>();
    }

    @Override
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<>());
            System.out.println("Vertex " + vertex + " added");
        }
    }

    // Remove @Override since this method does not exist in the Graph interface
    public void addEdge(T source, T destination) {
        adjacencyList.putIfAbsent(source, new HashSet<>());
        adjacencyList.putIfAbsent(destination, new HashSet<>());
        adjacencyList.get(source).add(destination);
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        // Implementation for weighted graph can be added here
        // For now, we'll ignore the weight and call the existing addEdge
        addEdge(source, destination);
    }

    @Override
    public void removeVertex(T vertex) {
        adjacencyList.remove(vertex);
        for (T neighbor : adjacencyList.keySet()) {
            adjacencyList.get(neighbor).remove(vertex);   
            if (adjacencyList.get(neighbor).isEmpty()) {
                adjacencyList.remove(neighbor);
            }
        }
    }

    @Override
    public void removeEdge(T source, T destination) {        
        adjacencyList.get(source).remove(destination);
        adjacencyList.get(destination).remove(source);     
        // Removed the following lines to prevent unintended vertex removal
        // if (adjacencyList.get(source).isEmpty()) {
        //     adjacencyList.remove(source);   
        //     if (adjacencyList.get(destination).isEmpty()) {
        //         adjacencyList.remove(destination);
        //     }
        // }
    }

    @Override
    public Set<T> getNeighbors(T node) {
        return adjacencyList.getOrDefault(node, new HashSet<>());
    }

    @Override
    public int getVertices() {
        return adjacencyList.size();
    }

    @Override
    public void printGraph() {
        for (Map.Entry<T, Set<T>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            System.out.println(entry.getValue());
        }
    }

    public static void main(String[] args) {
        GraphList<Integer> graph = new GraphList<>();
        graph.addEdge(0, 1);
        graph.addEdge(0, 4);
        graph.addEdge(1, 2);
        graph.addEdge(1, 3);
        graph.addEdge(1, 4);
        graph.addEdge(2, 3);
        graph.addEdge(3, 4);

        graph.printGraph();
    }
}
