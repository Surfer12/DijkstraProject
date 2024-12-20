# Dijkstra's Algorithm with Case Study

## 1. Practical Example
Consider the following weighted graph:
![alt text](<Dijkstra Algorithm Image.jpg>)
```mermaid
graph TD
    0((0)) -->|4| 1((1))
    0 -->|7| 6((6))
    1 -->|9| 2((2))
    1 -->|11| 6((6))
    1 -->|20| 7((7))
    2 -->|6| 3((3))
    2 -->|2| 4((4))
    3 -->|5| 5((5))
    3 -->|10| 4((4))
    4 -->|15| 8((5))
    4 -->|1| 7((7))
    5 -->|12| 8((8))
    6 -->|1| 7((7))
    7 -->|3| 8((8))

    style 0 1 2 3 4 5 6 7 8 fill:#bbf,stroke:#333,stroke-width:2px
```

### Sample Path Analysis: 0 to last node number five.
```mermaid
sequenceDiagram
    participa nt PQ as Priority Queue
    participant D as Distance Array
    participant P as Path Tracking

    Note over PQ,P: Initialize Algorithm
    Note over PQ: [(0,0)]
    Note over D: [0: **0**, 1: ∞, 2: ∞, 3: ∞, 4: ∞, 5: ∞, 6: ∞, 7: ∞, 8: ∞]
    Note over P: All nodes: null

    rect rgb(200, 255, 200)
        Note over PQ: Process Node 0 (dist: 0)
        PQ->>D: Update neighbors of 0
        Note over PQ: [(1,4), (6,7)]
        Note over D: [0: **0**, 1: **4**, 2: ∞, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: ∞, 8: ∞]
        Note over P: 1: 0, 6: 0
    end

    rect rgb(200, 220, 255)
        Note over PQ: Process Node 1 (dist: 4)
        PQ->>D: Update neighbors of 1
        Note over PQ: [(6,7), (2,13), (7,24)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: 24, 8: ∞]
        Note over P: Previous + 2: 1
    end

    rect rgb(255, 220, 220)
        Note over PQ: Process Node 6 (dist: 7)
        PQ->>D: Update neighbors of 6
        Note over PQ: [(7,8), (2,13)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: ∞, 5: ∞, 6: **7**, 7: 8, 8: ∞]
        Note over P: Previous + 7: 6
    end

    rect rgb(220, 255, 220)
        Note over PQ: Process Node 7 (dist: 8)
        PQ->>D: Update neighbors of 7
        Note over PQ: [(4,9), (2,13), (8,11)]
        Note over D: [0: **0**, 1: **4**, 2: 13, 3: ∞, 4: 9, 5: ∞, 6: **7**, 7: **8**, 8: 11]
        Note over P: Previous + 4: 7
    end

    rect rgb(220, 220, 255)
        Note over PQ: Process Node 4 (dist: 9)
        PQ->>D: Update neighbors of 4
        Note over PQ: [(2,11), (8,11), (3,15), (5,24)]
        Note over D: [0: **0**, 1: **4**, 2: 11, 3: 15, 4: **9**, 5: 24, 6: **7**, 7: **8**, 8: 11]
        Note over P: Previous + 2: 4, 3: 4
    end

    rect rgb(255, 255, 220)
        Note over PQ: Process Node 2 (dist: 11)
        PQ->>D: Update neighbors of 2
        Note over PQ: [(8,11), (3,17), (5,24)]
        Note over D: [0: **0**, 1: **4**, 2: **11**, 3: 17, 4: **9**, 5: 24, 6: **7**, 7: **8**, 8: 11]
        Note over P: Update 3: 2
    end
```

```mermaid
graph TD
    subgraph Distance Table
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


```mermaid
graph TD
    subgraph Final Shortest Paths
        P5[0 → 6 → 7 → 8 → 4 → 2 → 3 → 5: 22 units]
    end
```

### Alternative Paths Analysis for first four nodes
```mermaid
graph TD
    subgraph Alternative Routes
        P1[Path 1: 0→1→2→4→8<br/>Cost: 20]
        P2[Path 2: 0→6→7→8<br/>Cost: 11]
        P3[Path 3: 0→1→6→7→8<br/>Cost: 19]
    end
```



## 2. Algorithm Implementation for Example

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

        Map<Integer, Integer> distances = findShortestPath(graph, 0);
        System.out.println("Shortest distances from node 0:");
        for(Map.Entry<Integer, Integer> entry : distances.entrySet()){
            System.out.println("Node " + entry.getKey() + ": " + entry.getValue());
        }
    }
}
```
