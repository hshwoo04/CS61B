package graph;

/* See restrictions in Graph.java. */

/** A partial implementation of ShortestPaths that contains the weights of
 *  the vertices and the predecessor edges.   The client needs to
 *  supply only the two-argument getWeight method.
 *  @author Sung Hyun Harvey Woo
 */
public abstract class SimpleShortestPaths extends ShortestPaths {

    /** The shortest paths in G from SOURCE. */
    public SimpleShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public SimpleShortestPaths(Graph G, int source, int dest) {
        super(G, source, dest);
    }

    @Override
    public double getWeight(int v) {
        if (_G.mine(v)) {
            if (v == getSource()) {
                return 0.0;
            }
            if (_weights.containsKey(v)) {
                return _weights.get(v);
            } else {
                return Double.POSITIVE_INFINITY;
            }
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    @Override
    protected void setWeight(int v, double w) {
        if (_G.mine(v)) {
            _weights.put(v, w);
        }
    }

    @Override
    public int getPredecessor(int v) {
        if (_pathPredecessors.containsKey(v)) {
            return _pathPredecessors.get(v);
        }
        return 0;
    }

    @Override
    protected void setPredecessor(int v, int u) {
        _pathPredecessors.put(v, u);
    }


}
