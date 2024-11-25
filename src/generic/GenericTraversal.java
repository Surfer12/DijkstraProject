package legacyalgo.generic;

import java.util.*;

public class GenericTraversal<T> {

    // Method to perform Breadth-First Search (BFS) traversal using the Node class
    public List<T> bfs(GenericNode<T> startNode) {
        List<T> visitedNodes = new ArrayList<>();
        Queue<GenericNode<T>> queue = new LinkedList<>();
        Set<GenericNode<T>> visited = new HashSet<>();

        queue.offer(startNode);
        visited.add(startNode);

        while (!queue.isEmpty()) {
            GenericNode<T> currentNode = queue.poll();
            visitedNodes.add(currentNode.data);

            for (GenericNode<T> neighbor : currentNode.neighbors) {
                if (!visited.contains(neighbor)) {
                    visited.add(neighbor);
                    queue.offer(neighbor);
                }
            }
        }

        return visitedNodes;
    }

    // Method to perform Depth-First Search (DFS) traversal using the Node class
    public List<T> dfs(GenericNode<T> startNode) {
        List<T> visitedNodes = new ArrayList<>();
        Set<GenericNode<T>> visited = new HashSet<>();
        dfsRecursive(startNode, visited, visitedNodes);
        return visitedNodes;
    }

    private void dfsRecursive(GenericNode<T> node, Set<GenericNode<T>> visited, List<T> visitedNodes) {
        visited.add(node);
        visitedNodes.add(node.data);

        for (GenericNode<T> neighbor : node.neighbors) {
            if (!visited.contains(neighbor)) {
                dfsRecursive(neighbor, visited, visitedNodes);
            }
        }
    }

    // Method to perform in-order traversal using the Node class
    public List<T> inOrderTraversal(GenericNode<T> root) {
        List<T> result = new ArrayList<>();
        inOrderTraversalHelper(root, result);
        return result;
    }

    private void inOrderTraversalHelper(GenericNode<T> node, List<T> result) {
        if (node == null) {
            return;
        }
        if (node.left != null) {
            inOrderTraversalHelper(node.left, result);
        }
        result.add(node.data);
        if (node.right != null) {
            inOrderTraversalHelper(node.right, result);
        }
    }

    // Method to perform Dijkstra's algorithm using the Node class
    public Map<GenericNode<T>, Integer> dijkstra(Graph<T> graph, GenericNode<T> source) {
        Map<GenericNode<T>, Integer> distances = new HashMap<>();
        Set<GenericNode<T>> visited = new HashSet<>();
        PriorityQueue<NodeDistancePair> pq = new PriorityQueue<>(Comparator.comparingInt(pair -> pair.distance));

        for (GenericNode<T> node : graph.keySet()) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(source, 0);
        pq.offer(new NodeDistancePair(source, 0));

        while (!pq.isEmpty()) {
            NodeDistancePair currentPair = pq.poll();
            GenericNode<T> currentNode = currentPair.node;
            int currentDistance = currentPair.distance;

            if (visited.contains(currentNode)) continue;
            visited.add(currentNode);

            for (Map.Entry<GenericNode<T>, Integer> neighborEntry : graph.get(currentNode).entrySet()) {
                GenericNode<T> neighborNode = neighborEntry.getKey();
                int weight = neighborEntry.getValue();
                int newDistance = currentDistance + weight;

                if (newDistance < distances.get(neighborNode)) {
                    distances.put(neighborNode, newDistance);
                    pq.offer(new NodeDistancePair(neighborNode, newDistance));
                }
            }
        }

        return distances;
    }

    // Helper class to store node-distance pairs for Dijkstra's algorithm
    public class NodeDistancePair {
        GenericNode<T> node;
        int distance;

        NodeDistancePair(GenericNode<T> node, int distance) {
            this.node = node;
            this.distance = distance;
        }
    }
}

// New GenericNode class with left and right properties
public class GenericNode<T> {
    T data;
    GenericNode<T> left;
    GenericNode<T> right;
    Set<GenericNode<T>> neighbors;

    // ...existing constructors and methods...
}
