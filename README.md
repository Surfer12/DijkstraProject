# Dijkstra Algorithm Project

This project implements Dijkstra's algorithm to find the shortest path in a weighted graph. It consists of three main
classes:

## Files

- **src/Main.java**: Contains the `Main` class with the `main` method. It initializes a `WeightedGraph` instance, adds
  edges to the graph, and calls the Dijkstra algorithm to find the shortest path from a starting vertex.

- **src/DijkstraGeneric.java**: Implements Dijkstra's algorithm. It includes methods to calculate the shortest path from
  a starting vertex and to print the path and total distance.

- **src/WeightedGraph.java**: Represents a weighted graph. It includes methods to add edges, retrieve the number of
  vertices, and get the adjacency list. It also defines a nested `Node` class to represent graph nodes.

## How to Run

1. Ensure you have Java installed on your machine.
2. Compile the Java files:
   ```
   javac src/*.java
   ```
3. Run the `Main` class:
   ```
   java src.Main
   ```

This will execute the program and display the shortest path from the starting vertex to the specified destination.