### Refactored `Dijkstra's Algorithm Implementation Ov.md`

# Dijkstra's Algorithm Implementation Overview

## Introduction

This document provides an overview of the key terms and concepts used in the implementation of Dijkstra's algorithm
within the provided Java project. The implementation leverages generics to enhance flexibility and reusability. Below
are the primary components and their definitions.

## Key Components

### 1. WeightedGraph.java

**Path:** `/Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/WeightedGraph.java`

**Summary:**
Defines a weighted, undirected graph using an adjacency list representation.

**Key Concepts:**

- **Vertices:** The number of nodes in the graph.
- **Adjacency List:** A list of lists where each sublist contains nodes adjacent to a specific vertex.
- **Node Class:** Represents an edge with a destination vertex and associated weight.
- **Comparable Interface:** Enables nodes to be compared based on weight, facilitating priority queue operations.

### 2. Main.java

**Path:** `/Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/Main.java`

**Summary:**
Initializes the graph, adds edges, and executes Dijkstra's algorithm to find the shortest path.

**Key Concepts:**

- **Graph Initialization:** Creates a `WeightedGraph` instance with a specified number of vertices.
- **Edge Addition:** Adds edges between vertices with corresponding weights.
- **Distance Calculation:** Invokes the Dijkstra algorithm to compute shortest distances from a starting vertex.

### 3. GenericNode.java

**Path:** `/Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/GenericNode.java`

**Summary:**
Defines a generic node class to support various data types for vertices.

**Key Concepts:**

- **Generics (`<T>`):** Allows the node to store any data type, enhancing flexibility.
- **Neighbors:** A list of adjacent nodes.
- **Tree Structure:** Includes `left` and `right` pointers for potential tree-based algorithms.

### 4. DijkstraGenericCopy.java

**Path:** `/Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/DijkstraGenericCopy.java`

**Summary:**
Implements Dijkstra's algorithm using generics to handle different types of vertex identifiers and includes path
reconstruction.

**Key Concepts:**

- **Generic Implementation:** Utilizes generics (`<T>`) to allow vertices of any data type.
- **Distance Map:** Stores the shortest distance from the source to each vertex.
- **Priority Queue:** Orders nodes based on their current shortest distance.
- **Path Reconstruction:** Implements a method to reconstruct the shortest path from the source to any destination node.

### 5. DijkstraGeneric.java

**Path:** `/Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/DijkstraGeneric.java`

**Summary:**
Placeholder for the generic implementation of Dijkstra's algorithm.

**Key Concepts:**

- **To Be Implemented:** Intended to contain the generic version of the algorithm as demonstrated in
  `DijkstraGenericCopy.java`.

## Use of Generics

The implementation has been enhanced to incorporate generics, allowing the graph and Dijkstra's algorithm to handle
various types of vertex identifiers beyond just integers. This enhancement increases the flexibility and reusability of
the code.

### GenericNode Class

The `GenericNode<T>` class allows nodes to store data of any type, facilitating the creation of more versatile graphs.

```java
public class GenericNode<T> {
    // ...existing code...
    private T data;
    // ...existing code...
    // Getters and setters for data
}
```

### Generic Dijkstra's Algorithm

The `DijkstraGenericCopy.java` file demonstrates a generic implementation of Dijkstra's algorithm, enabling it to work
with any data type for vertices.

```java
public class DijkstraGenericCopy{
    public static <T> Map<GenericNode<T>, Integer> dijkstra(
            Map<GenericNode<T>, Map<GenericNode<T>, Integer>> graph, 
            GenericNode<T> source) {
        // ...existing code...
    }

    public static void printShortestDistances(Map<GenericNode<?>, Integer> distances) {
        // ...existing code...
    }
}
```

### Path Reconstruction

Implementing a method to reconstruct the shortest path from the source to any destination node enhances the utility of
the algorithm by providing not just the distances but the actual paths taken.

```java
public class DijkstraGenericCopy{
    // ...existing code...

    public static <T> Map<GenericNode<T>, Integer> dijkstra(
            Map<GenericNode<T>, Map<GenericNode<T>, Integer>> graph, 
            GenericNode<T> source, GenericNode<T> destination) {
        // ...existing code...
    }

    private static <T> void reconstructPath(Map<GenericNode<T>, GenericNode<T>> previous, GenericNode<T> destination) {
        // filepath: /Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/DijkstraGenericCopy.java
        // ...existing code...
        GenericNode<T> current = destination;
        // ...existing code...
        // Path reconstruction logic
    }
    
    // ...existing code...
}
```

### Potential Enhancements

- **Integration with DijkstraGeneric.java:** Migrating the generic implementation from `DijkstraGenericCopy.java` to
  `DijkstraGeneric.java` for consistency.
- **Enhanced Path Reconstruction:** Refining the `reconstructPath` method to handle multiple destinations and provide
  more detailed path information.

## Conclusion

The incorporation of generics into the Dijkstra's algorithm implementation significantly broadens its applicability,
allowing it to manage diverse data types for vertices. This enhancement, coupled with the foundational structures like
adjacency lists and priority queues, ensures efficient performance and versatility. Future developments can focus on
completing the generic implementation and adding features like path reconstruction to further enrich the algorithm's
functionality.

# Summary of Changes

### [DijkstraGenericCopy.java](file:///Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/DijkstraGenericCopy.java)

**Summary:**
Implemented path reconstruction to retrieve the actual shortest path from the source to the destination node.

```java


public class DijkstraGenericCopy{
    public static <T> Map<GenericNode<T>, Integer> dijkstra(
            Map<GenericNode<T>, Map<GenericNode<T>, Integer>> graph, 
            GenericNode<T> source, GenericNode<T> destination) {
        Map<GenericNode<T>, Integer> distances = new HashMap<>();
        Map<GenericNode<T>, GenericNode<T>> previous = new HashMap<>(); // Track previous nodes
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
            
            if (current.equals(destination)) break; // Stop if destination is reached
            
            for (Map.Entry<GenericNode<T>, Integer> neighbor : graph.get(current).entrySet()) {
                GenericNode<T> next = neighbor.getKey();
                int newDist = distances.get(current) + neighbor.getValue();
                
                if (newDist < distances.get(next)) {
                    distances.put(next, newDist);
                    previous.put(next, current); // Update previous node
                    queue.add(next);
                }
            }
        }
        
        reconstructPath(previous, destination); // Reconstruct and print path
        return distances;
    }

    private static <T> void reconstructPath(Map<GenericNode<T>, GenericNode<T>> previous, GenericNode<T> destination) {
        List<GenericNode<T>> path = new ArrayList<>();
        GenericNode<T> current = destination;
        
        while (current != null) {
            path.add(0, current);
            current = previous.get(current);
        }
        
        System.out.print("Path: ");
        for (GenericNode<T> node : path) {
            System.out.print(node.getData() + " ");
        }
        System.out.println();
    }
    
    public static void printShortestDistances(Map<GenericNode<?>, Integer> distances) {
        // ...existing code...
    }
}
```

### [Main.java](file:///Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/Main.java)

**Summary:**
Updated the `main` method to specify the destination node and accommodate the updated Dijkstra method with path
reconstruction.

```java


public class Main {
    public static void main(String[] args) {
        WeightedGraph graph = new WeightedGraph(6);
        graph.addEdge(0, 1, 7);
        graph.addEdge(0, 2, 9);
        graph.addEdge(0, 5, 14);
        graph.addEdge(1, 2, 10);
        graph.addEdge(1, 3, 15);
        graph.addEdge(2, 3, 11);
        graph.addEdge(2, 5, 2);
        graph.addEdge(3, 4, 6);
        graph.addEdge(4, 5, 9);

        // ...existing code...

        GenericNode<Integer> source = new GenericNode<>(0);
        GenericNode<Integer> destination = new GenericNode<>(5);
        Map<GenericNode<Integer>, Integer> distances = DijkstraGenericCopy.dijkstra(genericGraph, source, destination);

        System.out.println("The shortest path from node 0 to node 5 is " + distances.get(destination));
    }
}
```

### [WeightedGraph.java](file:///Users/ryanoates/CIS233Midterm/cis233midterm/DijkstraProject/src/WeightedGraph.java)

**Summary:**
Ensured compatibility with `GenericNode` by modifying the `addEdge` method to handle generic nodes.

```java


public class WeightedGraph {
    // ...existing code...
    
    public void addEdge(GenericNode<Integer> source, GenericNode<Integer> destination, int weight) {
        adjList.get(source.getData()).add(new Node(destination.getData(), weight));
        adjList.get(destination.getData()).add(new Node(source.getData(), weight)); // For undirected graph
    }
    
    // ...existing code...
}
```

## Conclusion

By tracking previous nodes and implementing a path reconstruction method, the Dijkstra's algorithm now not only
calculates the shortest distances but also provides the actual path taken from the source to the destination node. These
enhancements improve the utility and informativeness of the algorithm's output, making it more robust and user-friendly.

