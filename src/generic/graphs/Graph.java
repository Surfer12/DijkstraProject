package legacyalgo.generic.graphs;

import java.util.Set;

public interface Graph<T> {
    void addVertex(T vertex);

    void addEdge(T source, T destination, int weight); // Updated method signature

    void addEdge(T source, T destination);

    void removeVertex(T vertex);

    void removeEdge(T source, T destination);

    Set<T> getNeighbors(int vertex);

    void printGraph();

    int getVertices();

    Object getAdjList();
}
