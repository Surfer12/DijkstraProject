package legacyalgo.generic;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

/**
 * A class that implements Breadth-First Search (BFS) algorithm for graph traversal.
 * BFS explores all vertices of a graph at the present depth before moving on to vertices at the next depth level.
 */

public class BFS<T> {

    // Method to perform Breadth-First Search on a graph
    public static <T> List<T> bfs(Map<T, List<T>> graph, T startNode) {
        List<T> visitedNodes = new ArrayList<>(); // List to store the order of visited nodes
        Queue<T> queue = new LinkedList<>(); // Queue to keep track of nodes to visit
        Set<T> visited = new HashSet<>(); // Set to keep track of visited nodes

        queue.offer(startNode); // Add the starting node to the queue
        visited.add(startNode); // Mark the starting node as visited

        while (!queue.isEmpty()) {
            T currentNode = queue.poll(); // Dequeue a node from the front of the queue
            visitedNodes.add(currentNode); // Add the dequeued node to the visited list

            // Iterate over the neighbors of the current node
            for (T neighbor : graph.get(currentNode)) {
                if (!visited.contains(neighbor)) { // If a neighbor hasn't been visited
                    visited.add(neighbor); // Mark the neighbor as visited
                    queue.offer(neighbor); // Enqueue the neighbor to visit it later
                }
            }
        }

        return visitedNodes; // Return the list of visited nodes in BFS order
    }

    public static void main(String[] args) {
        // Sample graph represented as an adjacency list
        Map<Integer, List<Integer>> graph = new HashMap<>();
        graph.put(0, Arrays.asList(1, 4, 6));
        graph.put(1, Arrays.asList(0, 2, 7));
        graph.put(2, Arrays.asList(1));
        graph.put(3, Arrays.asList(4, 5));
        graph.put(4, Arrays.asList(0, 3, 8));
        graph.put(5, Arrays.asList(3));
        graph.put(6, Arrays.asList(0, 7));
        graph.put(7, Arrays.asList(1, 6));
        graph.put(8, Arrays.asList(4));

        int startNode = 0; // Starting node for BFS traversal
        List<Integer> bfsTraversal = bfs(graph, startNode); // Perform BFS

        System.out.println("BFS Traversal: " + bfsTraversal); // Print the traversal order
    }
}
