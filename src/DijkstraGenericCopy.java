// Dijkstra's algorithm for shortest path
/* Task 1- Homework
• Use Dijkstra's algorithm to find the shortest path and its total distance
from Node 0 to Node 5 in the graph shown below. You are required to
submit a pdf document. */

package legacyalgo;

import generic.GenericNode;
import java.util.*;

public class DijkstraGenericCopy{
    public static <T> Map<GenericNode<T>, Integer> dijkstra(
            Map<GenericNode<T>, Map<GenericNode<T>, Integer>> graph, 
            GenericNode<T> source) {
        Map<GenericNode<T>, Integer> distances = new HashMap<>();
        PriorityQueue<GenericNode<T>> queue = new PriorityQueue<>(
            (a, b) -> distances.getOrDefault(a, Integer.MAX_VALUE)
                    - distances.getOrDefault(b, Integer.MAX_VALUE));
        
        // Initialize distances
        for (GenericNode<T> node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        queue.add(source);
        
        while (!queue.isEmpty()) {
            GenericNode<T> current = queue.poll();
            
            for (Map.Entry<GenericNode<T>, Integer> neighbor : graph.get(current).entrySet()) {
                GenericNode<T> next = neighbor.getKey();
                int newDist = distances.get(current) + neighbor.getValue();
                
                if (newDist < distances.get(next)) {
                    distances.put(next, newDist);
                    queue.add(next);
                }
            }
        }
        
        return distances;
    }

    public static void printShortestDistances(Map<GenericNode<?>, Integer> distances) {
        System.out.println("Shortest distances:");
        for (Map.Entry<GenericNode<?>, Integer> entry : distances.entrySet()) {
            System.out.println("To node " + entry.getKey().getData() + ": " + entry.getValue());
        }
    }
}

