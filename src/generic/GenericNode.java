package legacyalgo.generic;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class GenericNode<T> {
    T data;
    List<GenericNode<T>> neighbors;
    GenericNode<T> left;
    GenericNode<T> right;

    public GenericNode(T data) {
        this.data = data;
        this.neighbors = new ArrayList<>();
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public List<GenericNode<T>> getNeighbors() {
        return neighbors;
    }

    public void addNeighbor(GenericNode<T> neighbor) {
        if (neighbor != null && !neighbors.contains(neighbor)) {
            neighbors.add(neighbor);
        }
    }

    public void removeNeighbor(GenericNode<T> neighbor) {
        neighbors.remove(neighbor);
    }

    public GenericNode<T> getLeft() {
        return left;
    }

    public void setLeft(GenericNode<T> left) {
        this.left = left;
    }

    public GenericNode<T> getRight() {
        return right;
    }

    public void setRight(GenericNode<T> right) {
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GenericNode<?> that)) return false;
        return Objects.equals(data, that.data);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data);
    }
}
