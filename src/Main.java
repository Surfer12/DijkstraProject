
import legacyalgo.DijkstraGeneric;
import legacyalgo.DijkstraAlgo;
import legacyalgo.generic.graphs.WeightedGraph;

public class Main {
    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();
        graph.addEdge(0, 1, 7);
        graph.addEdge(0, 2, 9);
        graph.addEdge(0, 5, 14);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 11);
        graph.addEdge(2, 5, 2);
        graph.addEdge(3, 4, 6);
        graph.addEdge(4, 5, 9);

        int[] distances = DijkstraAlgo.dijkstra(graph, 0);

        System.out.println("The shortest path from node 0 to node 5 is " + distances[5]);
    }
}