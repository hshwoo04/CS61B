package graph;

/* See restrictions in Graph.java. */

import java.util.List;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.util.Comparator;

/** The shortest paths through an edge-weighted labeled graph of type GRAPHTYPE.
 *  By overrriding methods getWeight, setWeight, getPredecessor, and
 *  setPredecessor, the client can determine how to get parameters of the
 *  search and to return results.  By overriding estimatedDistance, clients
 *  can search for paths to specific destinations using A* search.
 *  @author Sung Hyun Harvey Woo
 */
public abstract class ShortestPaths {
    /** A new Comparator Class.*/
    public class PQComparator implements Comparator<Integer> {
        /** compare method. O1 and O2 and integers. @return int.*/
        public int compare(Integer o1, Integer o2) {

            double aWeight = getWeight(o1) + estimatedDistance(o1);
            double bWeight = getWeight(o2) + estimatedDistance(o2);
            if (o1 == _source) {
                aWeight = 0.0;
            } else if (o2 == _source) {
                bWeight = 0.0;
            }
            if (aWeight < bWeight) {
                return -1;
            } else if (aWeight == bWeight) {
                return 0;
            } else if (aWeight > bWeight) {
                return 1;
            }
            return 0;
        }
    }

    /** The shortest paths in G from SOURCE. */
    public ShortestPaths(Graph G, int source) {
        this(G, source, 0);
    }

    /** A shortest path in G from SOURCE to DEST. */
    public ShortestPaths(Graph G, int source, int dest) {
        _G = G;
        PQComparator comp = new PQComparator();
        _source = source;
        _dest = dest;
        _path = new ArrayList<Integer>();
        _spFringe = new PriorityQueue<Integer>(MAGICNUMBER, comp);
        _weights = new HashMap<Integer, Double>();
        _pathPredecessors = new HashMap<Integer, Integer>();
    }

    /** Initialize the shortest paths.  Must be called before using
     *  getWeight, getPredecessor, and pathTo. */
    public void setPaths() {
        for (int x : _G.vertices()) {
            _spFringe.add(x);
        }

        while (_spFringe.peek() != _dest) {
            int temp = _spFringe.poll();

            for (int y = 0; y < _G.outDegree(temp); y++) {
                int tempVertice = _G.successor(temp, y);
                if (!(_pathPredecessors.containsKey(tempVertice))) {
                    _pathPredecessors.put(tempVertice, temp);
                    setWeight(tempVertice, getWeight(temp)
                        + getWeight(temp, tempVertice));
                } else {
                    double one = getWeight(temp) + getWeight(temp, tempVertice);
                    if (one < getWeight(tempVertice)) {
                        setPredecessor(tempVertice, temp);
                        setWeight(tempVertice, one);
                    }
                }
            }

            ArrayList<Integer> tempArr = new ArrayList<Integer>();
            for (int z : _spFringe) {
                tempArr.add(z);
            }
            _spFringe.clear();
            for (int a : tempArr) {
                _spFringe.add(a);
            }
        }
    }

    /** Returns the starting vertex. */
    public int getSource() {
        return _source;
    }

    /** Returns the target vertex, or 0 if there is none. */
    public int getDest() {
        if (_dest != 0) {
            return _dest;
        }
        return 0;
    }

    /** Returns the current weight of vertex V in the graph.  If V is
     *  not in the graph, returns positive infinity. */
    public abstract double getWeight(int v);

    /** Set getWeight(V) to W. Assumes V is in the graph. */
    protected abstract void setWeight(int v, double w);

    /** Returns the current predecessor vertex of vertex V in the graph, or 0 if
     *  V is not in the graph or has no predecessor. */
    public abstract int getPredecessor(int v);

    /** Set getPredecessor(V) to U. */
    protected abstract void setPredecessor(int v, int u);

    /** Returns an estimated heuristic weight of the shortest path from vertex
     *  V to the destination vertex (if any).  This is assumed to be less
     *  than the actual weight, and is 0 by default. */
    protected double estimatedDistance(int v) {
        return 0.0;
    }

    /** Returns the current weight of edge (U, V) in the graph.  If (U, V) is
     *  not in the graph, returns positive infinity. */
    protected abstract double getWeight(int u, int v);

    /** Returns a list of vertices starting at _source and ending
     *  at V that represents a shortest path to V.  Invalid if there is a
     *  destination vertex other than V. */
    public List<Integer> pathTo(int v) {
        _path.add(_dest);
        for (int a = _dest; a != _source; a = getPredecessor(a)) {
            _path.add(0, getPredecessor(a));
        }
        if (_dest == v) {
            return _path;
        }
        return null;
    }

    /** Returns a list of vertices starting at the source and ending at the
     *  destination vertex. Invalid if the destination is not specified. */
    public List<Integer> pathTo() {
        return pathTo(getDest());
    }

    /** The graph being searched. */
    protected final Graph _G;
    /** The starting vertex. */
    private final int _source;
    /** The target vertex. */
    private final int _dest;
    /** The shortest path. */
    private ArrayList<Integer> _path;
    /** The fringe used for the shortest path. */
    private PriorityQueue<Integer> _spFringe;
    /** HashMap for storing weights for vertices. Uses vertex
      * as a key and stores the weight for the edge. */
    protected HashMap<Integer, Double> _weights;
    /** HashMap for storing predecessors for vertices. Uses vertex
      * as a key and stores the predecessor that is part of the
      * shortest path as the value. */
    protected HashMap<Integer, Integer> _pathPredecessors;
    /** Magic number. */
    static final int MAGICNUMBER = 50;
}
