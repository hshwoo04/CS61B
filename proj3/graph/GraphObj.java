package graph;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Arrays;
import java.util.Set;

/* See restrictions in Graph.java. */

/** A partial implementation of Graph containing elements common to
 *  directed and undirected graphs.
 *
 *  @author Sung Hyun Harvey Woo
 */
abstract class GraphObj extends Graph {

    /** A new, empty Graph. */
    GraphObj() {
        _vertex = new ArrayList<Integer>();
        _edge = new HashMap<Integer, ArrayList<Integer>>();
        _predecessor = new HashMap<Integer, ArrayList<Integer>>();
    }

    @Override
    public int vertexSize() {
        return _vertex.size();
    }

    @Override
    public int maxVertex() {
        int max = 0;
        for (int x : _vertex) {
            max = Math.max(max, x);
        }
        return max;
    }

    @Override
    public int edgeSize() {
        int count = 0;
        for (int x : _edge.keySet()) {
            if (!(_edge.get(x).isEmpty())) {
                count += _edge.get(x).size();
            }
        }
        if (isDirected()) {
            return count;
        } else {
            for (int x : _edge.keySet()) {
                if (_edge.get(x).contains(x)) {
                    count++;
                }
            }
            return (count / 2);
        }
    }

    @Override
    public abstract boolean isDirected();

    @Override
    public int outDegree(int v) {
        if (_vertex.contains(v)) {
            ArrayList<Integer> temp = _edge.get(v);
            return temp.size();
        }
        return 0;
    }

    @Override
    public abstract int inDegree(int v);

    @Override
    public boolean contains(int u) {
        for (int x : _vertex) {
            if (x == u) {
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean contains(int u, int v) {

        if (isDirected()) {
            ArrayList<Integer> temp = _edge.get(u);
            if (temp.contains(v)) {
                return true;
            }
        } else {
            if (_edge.containsKey(u)) {
                ArrayList<Integer> temp = _edge.get(u);
                if (temp.contains(v)) {
                    return true;
                }
            }
            if (_edge.containsKey(v)) {
                ArrayList<Integer> temp = _edge.get(v);
                if (temp.contains(u)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int add() {
        for (int temp = 1; temp >= 1; temp++) {
            if (!(_vertex.contains(temp))) {
                _vertex.add(temp);
                _edge.put(temp, new ArrayList<Integer>());
                _predecessor.put(temp, new ArrayList<Integer>());
                return temp;
            }
        }
        return 0;
    }

    @Override
    public int add(int u, int v) {
        if (isDirected()) {
            if (!(_edge.get(u).contains(v))) {
                _edge.get(u).add(v);
                _predecessor.get(v).add(u);
            }
        } else {
            if (!(_edge.get(u).contains(v))) {
                _edge.get(u).add(v);
            }
            if (!(_edge.get(v).contains(u))) {
                _edge.get(v).add(u);
            }
        }
        return u;
    }

    @Override
    public void remove(int v) {
        if (_vertex.contains(v)) {
            _vertex.remove(new Integer(v));
            if (isDirected()) {
                _edge.remove(v);
                _predecessor.remove(v);
                Set<Integer> temp = _edge.keySet();
                for (int x : temp) {
                    if (_edge.get(x).contains(v)) {
                        _edge.get(x).remove(new Integer(v));
                    }
                    if (_predecessor.get(x).contains(v)) {
                        _predecessor.get(x).remove(new Integer(v));
                    }
                }
            } else {
                _edge.remove(v);
                Set<Integer> temp = _edge.keySet();
                for (int x : temp) {
                    if (_edge.get(x).contains(v)) {
                        _edge.get(x).remove(new Integer(v));
                    }
                }
            }
        }
    }

    @Override
    public void remove(int u, int v) {
        if (isDirected()) {
            if (_edge.get(u).contains(v)) {
                _edge.get(u).remove(new Integer(v));
            }
            if (_predecessor.get(v).contains(u)) {
                _predecessor.get(v).remove(new Integer(u));
            }
        } else {
            if (_edge.get(u).contains(v)) {
                _edge.get(u).remove(new Integer(v));
            }
            if (_edge.get(v).contains(u)) {
                _edge.get(v).remove(new Integer(u));
            }
        }
    }

    @Override
    public Iteration<Integer> vertices() {
        return Iteration.iteration(_vertex.iterator());
    }

    @Override
    public int successor(int v, int k) {
        if (_vertex.contains(v) && (_edge.get(v).size() >= (k + 1))) {
            return _edge.get(v).get(k);
        }
        return 0;
    }

    @Override
    public abstract int predecessor(int v, int k);

    @Override
    public Iteration<Integer> successors(int v) {
        return Iteration.iteration(_edge.get(v).iterator());
    }

    @Override
    public abstract Iteration<Integer> predecessors(int v);

    @Override
    public Iteration<int[]> edges() {
        ArrayList<int[]> temp = new ArrayList<int[]>();
        if (isDirected()) {
            for (int x : _edge.keySet()) {
                for (int y : _edge.get(x)) {
                    int[] addArr = new int[2];
                    addArr[0] = x;
                    addArr[1] = y;
                    temp.add(addArr);
                }
            }
        } else {
            for (int x : _edge.keySet()) {
                for (int y : _edge.get(x)) {
                    int[] addArr = new int[2];
                    int[] checkArr = new int[2];
                    addArr[0] = x;
                    addArr[1] = y;
                    checkArr[0] = y;
                    checkArr[1] = x;
                    for (int[] z : temp) {
                        if (Arrays.equals(z, checkArr)
                            || Arrays.equals(z, addArr)) {
                            break;
                        }
                        temp.add(addArr);

                    }
                }
            }
        }
        return Iteration.iteration(temp.iterator());
    }

    @Override
    protected boolean mine(int v) {
        if (_vertex.contains(new Integer(v))) {
            return true;
        }
        return false;
    }

    @Override
    protected void checkMyVertex(int v) {
        if (!(mine(v))) {
            throw new IllegalArgumentException("Vertice does not exist!");
        }
    }

    @Override
    protected int edgeId(int u, int v) {
        int a = (int) ((0.5 * (u + v) * (u + v + 1)) + v + 0.5);
        int b = (int) ((0.5 * (u + v) * (u + v + 1)) + u + 0.5);
        if (contains(u, v)) {
            if (isDirected()) {
                return a;
            } else {
                return Math.max(a, b);
            }
        }
        return 0;
    }
    /** The vertex arraylist. */
    protected ArrayList<Integer> _vertex;
    /** The edges hashmap arranged by vertex. */
    protected HashMap<Integer, ArrayList<Integer>> _edge;
    /** The predecessor hashmap, arranged by the vertex. */
    protected HashMap<Integer, ArrayList<Integer>> _predecessor;


}
