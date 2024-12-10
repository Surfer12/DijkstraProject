## 1. Priority Queue Maintenance

```mermaid
sequenceDiagram
    participant PQ as Priority Queue
    participant VN as Visited Nodes
    participant DS as Distance State

    Note over PQ: Initial Queue State<br/>[(0,0)]
    
    PQ->>DS: Poll Node 0
    Note over DS: Process neighbors of 0<br/>Update distances
    DS->>PQ: Add neighbors with priorities:<br/>(1,7), (2,9), (5,14)
    
    Note over PQ: Queue State<br/>[(1,7), (2,9), (5,14)]
    
    PQ->>DS: Poll Node 1 (lowest distance)
    Note over DS: Process neighbors of 1<br/>Update distances
    DS->>PQ: Add/Update neighbors:<br/>(2,17), (3,22)
    
    Note over PQ: Queue State<br/>[(2,9), (5,14), (3,22)]
```

### Priority Queue Efficiency:
1. Binary Heap Implementation
```java
PriorityQueue<Node> pq = new PriorityQueue<>((a, b) -> 
    distances.get(a) - distances.get(b));
```

2. Key Operations:
```java
// O(log n) insertion
pq.offer(new Node(vertex, distance));

// O(log n) removal of minimum element
Node current = pq.poll();

// O(1) peek at minimum element
Node next = pq.peek();
```

## 2. Path Reconstruction

```mermaid
graph TD
    subgraph Path Tracking
        0((0)) -->|"prev[1]=0"| 1((1))
        1 -->|"prev[2]=1"| 2((2))
        2 -->|"prev[5]=2"| 5((5))
        
        style 0 fill:#f9f,stroke:#333
        style 1,2,5 fill:#bbf,stroke:#333
    end

    subgraph Previous Nodes Map
        PM[Previous Nodes HashMap:<br/>5 → 2<br/>2 → 1<br/>1 → 0]
    end
```

### Path Tracking Implementation:
```java
private Map<Node, Node> previousNodes = new HashMap<>();

private void trackPath(Node current, Node neighbor, int newDistance) {
    if (newDistance < distances.get(neighbor)) {
        distances.put(neighbor, newDistance);
        previousNodes.put(neighbor, current);  // Track the path
    }
}

private List<Node> reconstructPath(Node destination) {
    List<Node> path = new ArrayList<>();
    Node current = destination;
    
    while (current != null) {
        path.add(0, current);  // Add to front of list
        current = previousNodes.get(current);  // Move to previous node
    }
    return path;
}
```

## 3. Edge Relaxation

```mermaid
sequenceDiagram
    participant C as Current Node
    participant N as Neighbor Node
    participant D as Distance Array
    
    Note over C,N: Edge Relaxation Process
    C->>N: Check edge weight (w)
    Note over C,D: Current distance = d
    Note over N,D: Neighbor distance = n
    
    alt d + w < n
        N->>D: Update distance
        Note over D: New distance = d + w
        N->>D: Update previous node
    else d + w >= n
        Note over D: Keep current distance
    end
```

### Edge Relaxation Implementation:
```java
private void relaxEdge(Node current, Node neighbor, int weight) {
    int currentDistance = distances.get(current);
    int neighborDistance = distances.get(neighbor);
    int newDistance = currentDistance + weight;
    
    if (newDistance < neighborDistance) {
        // Update distance
        distances.put(neighbor, newDistance);
        // Update previous node
        previousNodes.put(neighbor, current);
        // Add to priority queue with new priority
        pq.offer(neighbor);
    }
}
```

## Combined Process Example

```mermaid
graph TD
    subgraph Processing State
        PQ[Priority Queue<br/>Current: (2,9)]
        DM[Distance Map<br/>0:0, 1:7, 2:9, 3:∞, 4:∞, 5:14]
        PM[Previous Map<br/>1←0, 2←0, 5←0]
    end
    
    subgraph Edge Relaxation
        direction LR
        C((Current: 2)) -->|"weight=2"| N((Neighbor: 5))
        Note["Old distance to 5: 14<br/>New possible distance: 11<br/>11 < 14, so update"]
    end
    
    subgraph Updated State
        PQ2[Priority Queue<br/>Next: (5,11)]
        DM2[Distance Map<br/>0:0, 1:7, 2:9, 3:∞, 4:∞, 5:11]
        PM2[Previous Map<br/>1←0, 2←0, 5←2]
    end
```

### Key Optimizations:

1. **Priority Queue Management**
   - Maintain heap property after every update
   - Only add nodes when their distance improves
   - Use decrease-key operation when available

2. **Path Tracking**
   - Only update previous node when finding shorter path
   - Store only essential path information
   - Reconstruct path only when needed

3. **Edge Relaxation**
   - Process each edge at most once
   - Early termination when possible
   - Skip relaxation if current node's distance is infinity