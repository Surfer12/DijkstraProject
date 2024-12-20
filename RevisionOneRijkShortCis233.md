Dijkstra’s Algorithm with Case Study: Consider the following weighted graph:
![alt text](<Dijkstra Algorithm Image.jpg>)

```mermaid
graph TD
    subgraph Shortest to each
        DT0[0: **0**]
        DT1[1: **4**]
        DT2[2: **11**]
        DT3[3: **17**]
        DT4[4: **9**]
        DT5[5: **22**]
        DT6[6: **7**]
        DT7[7: **8**]
        DT8[8: **11**]
    end
```
Sample Path Analysis: 0 to Node Five
```mermaid
sequenceDiagram
    participant PQ as Priority Queue
    participant D as Distance Array

    Note over PQ: Initialize Algorithm
    Note over PQ: [(0,0)]
    Note over D: [0: **0**, 1: ∞, 2: ∞, 3: ∞, 4: ∞, 5: ∞, 6: ∞, 7: ∞, 8: ∞]

    rect rgb(200, 255, 200)
        Note over PQ: Process Node 0 (dist: 0)
        PQ->>D: Update neighbors of 0
        Note over PQ: [(1,4), (6,7)]
        Note over D: [0: **0**, 1: **4**, 2: ∞, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: ∞, 8: ∞]
    end

    rect rgb(200, 220, 255)
        Note over PQ: Process Node 1 (dist: 4)
        PQ->>D: Update neighbors of 1
        Note over PQ: [(6,7), (2,13), (7,24)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: 24, 8: ∞]
    end

    rect rgb(255, 220, 220)
        Note over PQ: Process Node 6 (dist: 7)
        PQ->>D: Update neighbors of 6
        Note over PQ: [(7,8), (2,13)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: 8, 8: ∞]
    end

    rect rgb(220, 255, 220)
        Note over PQ: Process Node 7 (dist: 1)
        PQ->>D: Update neighbors of 7
        Note over PQ: [(4,9), (2,13), (8,11)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: 9, 5: ∞, 6: **7**, 7: **8**, 8: 11]
    end

    rect rgb(220, 220, 255)
        Note over PQ: Process Node 4 (dist: 2)
        PQ->>D: Update neighbors of 4
        Note over PQ: [(2,11), (8,11), (3,15), (5,24)]
        Note over D: [0: **0**, 1: **4**, 2: 11, 3: 15, 4: **9**, 5: 24, 6: **7**, 7: **8**, 8: 11]
    end

    rect rgb(255, 255, 220)
        Note over PQ: Process Node 3 (dist: 6)
        PQ->>D: Update neighbors of 2
        Note over PQ: [(8,11), (3,17), (5,24)]
        Note over D: [0: **0**, 1: **4**, 2: **11**, 3: 17, 4: **9**, 5: 24, 6: **7**, 7: **8**, 8: 11]
    end
```


    Algorithm Processing Steps

```mermaid
sequenceDiagram
    participant PQ as Priority Queue
    participant D as Distance Array
    participant P as Path Tracker

    Note over PQ,P: Algorithm Initialization
    PQ->>D: Set initial distances (∞)
    D->>D: Set source distance (0)

    loop While Priority Queue Not Empty
        PQ->>D: Get node with min distance
        D->>P: Update shortest paths
        P->>PQ: Add unvisited neighbors
    end
```

```mermaid
graph TD
    subgraph Final Shortest Paths
        P5[0 → 6 → 7 → 4 → 2 → 3 → 5: 22 units]
    end
```

Alternative Paths Analysis for First Four Nodes
```mermaid
graph TD
    subgraph Alternative Routes
        P1[Path 1: 0→1→2→4→8<br/>Cost: 20]
        P2[Path 2: 0→6→7→8<br/>Cost: 11]
        P3[Path 3: 0→1→6→7→8<br/>Cost: 19]
    end
```

2. Algorithm Implementation for Example

```java
import java.util.*;

class Edge {
    int dest;
    int weight;

    Edge(int dest, int weight){
        this.dest = dest;
        this.weight = weight;
    }
}

class WeightedGraph {
    private Map<Integer, List<Edge>> adjList = new HashMap<>();

    public void addEdge(int src, int dest, int weight){
        adjList.computeIfAbsent(src, k -> new ArrayList<>()).add(new Edge(dest, weight));
    }

    public List<Edge> getEdges(int node){
        return adjList.getOrDefault(node, new ArrayList<>());
    }
}

class Node implements Comparable<Node>{
    int id;
    int distance;

    Node(int id, int distance){
        this.id = id;
        this.distance = distance;
    }

    @Override
    public int compareTo(Node other){
        return Integer.compare(this.distance, other.distance);
    }
}

public class DijkstraExample {
    public static Map<Integer, Integer> findShortestPath(WeightedGraph graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previousNodes = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Initialize distances
        for (int node = 0; node < 9; node++) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentNode = current.id;
            int currentDist = current.distance;

            // Skip if we've already found a better path
            if (currentDist > distances.get(currentNode)) continue;

            for (Edge edge : graph.getEdges(currentNode)) {
                int neighbor = edge.dest;
                int newDist = distances.get(currentNode) + edge.weight;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    pq.offer(new Node(neighbor, newDist));
                }
            }
        }
        return distances;
    }

    // Method to reconstruct the shortest path
    public static List<Integer> getPath(Map<Integer, Integer> previousNodes, int start, int end) {
        List<Integer> path = new ArrayList<>();
        Integer current = end;
        while (current != null && current != start) {
            path.add(current);
            current = previousNodes.get(current);
        }
        if (current == null) {
            return Collections.emptyList(); // No path found
        }
        path.add(start);
        Collections.reverse(path);
        return path;
    }

    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph();
        // Adding edges as per the example graph
        graph.addEdge(0, 1, 4);
        graph.addEdge(0, 6, 7);
        graph.addEdge(1, 2, 9);
        graph.addEdge(1, 6, 11);
        graph.addEdge(1, 7, 20);
        graph.addEdge(2, 3, 6);
        graph.addEdge(2, 4, 2);
        graph.addEdge(3, 5, 5);
        graph.addEdge(3, 4, 10);
        graph.addEdge(4, 5, 15);
        graph.addEdge(4, 7, 1);
        graph.addEdge(4, 8, 5);
        graph.addEdge(5, 8, 12);
        graph.addEdge(6, 7, 1);
        graph.addEdge(7, 8, 3);

        int startNode = 0;
        int endNode = 5;
        Map<Integer, Integer> distances = findShortestPath(graph, startNode);
        List<Integer> shortestPath = getPath(getPreviousNodes(graph, startNode), startNode, endNode);

        System.out.println("Shortest distances from node " + startNode + ":");
        for(Map.Entry<Integer, Integer> entry : distances.entrySet()){
            System.out.println("Node " + entry.getKey() + ": " + entry.getValue());
        }

        System.out.println("\nShortest path from node " + startNode + " to node " + endNode + ": " + shortestPath + " with total distance: " + distances.get(endNode));
    }

    // Helper method to retrieve previousNodes map
    public static Map<Integer, Integer> getPreviousNodes(WeightedGraph graph, int start) {
        Map<Integer, Integer> distances = new HashMap<>();
        Map<Integer, Integer> previousNodes = new HashMap<>();
        PriorityQueue<Node> pq = new PriorityQueue<>();

        // Initialize distances
        for (int node = 0; node < 9; node++) {
            distances.put(node, Integer.MAX_VALUE);
        }
        distances.put(start, 0);
        pq.offer(new Node(start, 0));

        while (!pq.isEmpty()) {
            Node current = pq.poll();
            int currentNode = current.id;
            int currentDist = current.distance;

            // Skip if we've already found a better path
            if (currentDist > distances.get(currentNode)) continue;

            for (Edge edge : graph.getEdges(currentNode)) {
                int neighbor = edge.dest;
                int newDist = distances.get(currentNode) + edge.weight;
                if (newDist < distances.get(neighbor)) {
                    distances.put(neighbor, newDist);
                    previousNodes.put(neighbor, currentNode);
                    pq.offer(new Node(neighbor, newDist));
                }
            }
        }
        return previousNodes;
    }
}
```
