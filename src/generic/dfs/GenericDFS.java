package legacyalgo.generic.dfs;
import java.util.*;
import legacyalgo.generic.Graph;

public class GenericDFS<T> implements Graph<T> {
    private Map<T, Set<T>> adjacencyList = new HashMap<>();
    
    @Override
    public void dfs(T node, Set<T> visited, Graph<T> graph) {
        if (node == null) return;

        visited.add(node);
        System.out.print(node + " ");

        for (T neighbor : graph.getNeighbors(node)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, visited, graph);
            }
        }
    }

    @Override
    public void addVertex(T vertex) {
        if (!adjacencyList.containsKey(vertex)) {
            adjacencyList.put(vertex, new HashSet<>());
            System.out.println("Vertex " + vertex + " added");
        }
    }

    @Override
    public void addEdge(T source, T destination) {
        adjacencyList.putIfAbsent(source, new HashSet<>());
        adjacencyList.putIfAbsent(destination, new HashSet<>());
        adjacencyList.get(source).add(destination);
    }

    @Override
    public void addEdge(T source, T destination, int weight) {
        // Implementation for adding weighted edges
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
    }

    @Override
    public Set<T> getNeighbors(T node) {
        return adjacencyList.getOrDefault(node, new HashSet<>());
    }

    @Override
    public void printGraph() {
        for (Map.Entry<T, Set<T>> entry : adjacencyList.entrySet()) {
            System.out.print(entry.getKey() + " -> ");
            System.out.println(entry.getValue());
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericDFS)) return false;
        GenericDFS<?> that = (GenericDFS<?>) o;
        return Objects.equals(adjacencyList, that.adjacencyList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(adjacencyList);
    }
}
