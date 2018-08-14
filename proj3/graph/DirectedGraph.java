package graph;
import java.util.ArrayList;


/* See restrictions in Graph.java. */

/** Represents a general unlabeled directed graph whose vertices are denoted by
 *  positive integers. Graphs may have self edges.
 *
 *  @author Sung Hyun Harvey Woo
 */
public class DirectedGraph extends GraphObj {

    @Override
    public boolean isDirected() {
        return true;
    }

    @Override
    public int inDegree(int v) {
        if (_predecessor.containsKey(v)) {
            ArrayList<Integer> temp = _predecessor.get(v);
            return temp.size();
        }
        return 0;
    }

    @Override
    public int predecessor(int v, int k) {
        if (_vertex.contains(v) && (_predecessor.get(v).size() >= (k + 1))) {
            return _predecessor.get(v).get(k);
        }
        return 0;
    }

    @Override
    public Iteration<Integer> predecessors(int v) {
        return Iteration.iteration(_predecessor.get(v).iterator());
    }
}
