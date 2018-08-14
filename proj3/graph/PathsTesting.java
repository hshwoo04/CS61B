package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;

/** Unit tests for the Graph class.
 *  @author
 */
public class PathsTesting {

    class SamplePath extends SimpleShortestPaths {
        public SamplePath(Graph G, int source, int dest) {
            super(G, source, dest);
        }

        @Override
        public double getWeight(int u, int v) {
            if (u == 4) {
                if (v == 5) {
                    return 11.2;
                } else if (v == 2) {
                    return 12.2;
                } else if (v == 3) {
                    return 102.0;
                }
            } else if (u == 2) {
                if (v == 3) {
                    return 6.5;
                }
            } else if (u == 5) {
                if (v == 6) {
                    return 30.0;
                } else if (v == 3) {
                    return 9.1;
                }
            }
            return 0.0;
        }

        @Override
        protected double estimatedDistance(int v) {
            if (v == 3) {
                return 0.0;
            } else if (v == 2) {
                return 4.0;
            } else if (v == 5) {
                return 5.1;
            } else if (v == 6) {
                return 40.0;
            } else if (v == 4) {
                return 102.0;
            }
            return 0.0;
        }
    }

    @Test
    public void basicPaths() {
        DirectedGraph graph = new DirectedGraph();
        graph.add();
        graph.add();
        graph.add();
        graph.add();
        graph.add();
        graph.add();
        graph.remove(1);
        graph.add(4, 2);
        graph.add(4, 3);
        graph.add(4, 5);
        graph.add(2, 3);
        graph.add(5, 6);
        graph.add(5, 3);
        SamplePath sample = new SamplePath(graph, 4, 3);
        sample.setWeight(2, 12.2);
        sample.setWeight(3, 102.0);
        sample.setWeight(5, 11.2);
        sample.setPaths();


        ArrayList<Integer> vert = new ArrayList<Integer>();
        for (int x : sample._G.vertices()) {
            if (sample.getPredecessor(x) != 0) {
                vert.add(sample.getPredecessor(x));
            }
        }

        assertEquals(true, vert.contains(2));
        assertEquals(false, vert.contains(3));
        assertEquals(true, vert.contains(4));
        assertEquals(true, vert.contains(5));
        assertEquals(false, vert.contains(6));

        List<Integer> sPath = sample.pathTo();
        assertEquals(4, sample.getSource());
        assertEquals(3, sample.getDest());
        ArrayList<Integer> answer = new ArrayList<Integer>();
        answer.add(4);
        answer.add(2);
        answer.add(3);
        assertEquals(3, sPath.size());
        assertEquals(answer, sPath);

    }
}
