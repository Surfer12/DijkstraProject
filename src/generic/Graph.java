package legacyalgo.generic;

import java.util.Set;

public interface Graph<T> {

    Set<T> getNeighbors(T node);

}