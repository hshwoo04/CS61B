package graph;

import org.junit.Test;
import static org.junit.Assert.*;

/** Unit tests for the Graph class.
 *  @author Sung Hyun Harvey Woo
 */
public class GraphTesting {

    @Test
    public void emptyGraph() {
        DirectedGraph g = new DirectedGraph();
        assertEquals("Initial graph has vertices", 0, g.vertexSize());
        assertEquals("Initial graph has edges", 0, g.edgeSize());
    }

    /** Testing methods that do not distinguish between Directed and
      * Undirected graphs. Using Undirected graph for simplicity. */
    @Test
    public void generalTest() {
        UndirectedGraph temp = new UndirectedGraph();
        temp.add();
        temp.add();
        temp.add();
        temp.add(1, 2);
        temp.add(1, 3);
        assertEquals(3, temp.vertexSize());
        assertEquals(3, temp.maxVertex());
        assertEquals(2, temp.edgeSize());
        assertEquals(true, temp.contains(1));
        assertEquals(true, temp.contains(1, 2));
        assertEquals(2, temp.successor(1, 0));
        assertEquals(true, temp.mine(1));
        assertEquals(2, temp.outDegree(1));
        int first = temp.edgeId(1, 2);
        int second = temp.edgeId(1, 3);
        int third = temp.edgeId(1, 2);
        assertEquals(true, first == third);
        assertEquals(false, first == second);
        temp.remove(1);
        assertEquals(false, temp.contains(1, 2));
    }

    /** Testing methods for Directed Graphs. */
    @Test
    public void directedTest() {
        DirectedGraph temp = new DirectedGraph();
        temp.add();
        temp.add();
        temp.add();
        temp.add(1, 2);
        temp.add(1, 3);
        assertEquals(2, temp.edgeSize());
        assertEquals(true, temp.contains(1, 3));
        assertEquals(true, temp.contains(1, 2));
        assertEquals(2, temp.successor(1, 0));
        assertEquals(0, temp.successor(1, 3));
        temp.remove(1, 3);
        assertEquals(true, temp.contains(1, 2));
        assertEquals(false, temp.contains(1, 3));
        assertEquals(true, temp.contains(2));
        temp.add(1, 3);
        int first = temp.edgeId(1, 2);
        int second = temp.edgeId(1, 3);
        int third = temp.edgeId(1, 2);
        assertEquals(true, first == third);
        assertEquals(false, first == second);
        assertEquals(1, temp.inDegree(3));
        assertEquals(1, temp.inDegree(2));
        assertEquals(0, temp.inDegree(1));
        assertEquals(1, temp.predecessor(3, 0));
        assertEquals(1, temp.predecessor(2, 0));

    }

    /** Testing methods for Undirected Graphs. */
    @Test
    public void undirectedTest() {
        UndirectedGraph temp = new UndirectedGraph();
        temp.add();
        temp.add();
        temp.add();
        temp.add(1, 2);
        temp.add(1, 3);
        assertEquals(true, temp.contains(1, 3));
        assertEquals(true, temp.contains(1, 2));
        assertEquals(2, temp.successor(1, 0));
        assertEquals(0, temp.successor(1, 3));
        temp.remove(1, 3);
        assertEquals(true, temp.contains(1, 2));
        assertEquals(false, temp.contains(1, 3));
        assertEquals(true, temp.contains(2));
        temp.add(1, 3);
        int first = temp.edgeId(1, 2);
        int second = temp.edgeId(1, 3);
        int third = temp.edgeId(1, 2);
        assertEquals(true, first == third);
        assertEquals(false, first == second);
        assertEquals(1, temp.inDegree(3));
        assertEquals(1, temp.inDegree(2));
        assertEquals(2, temp.inDegree(1));
        assertEquals(1, temp.predecessor(3, 0));
        assertEquals(1, temp.predecessor(2, 0));
    }

    @Test
    public void removeTest() {
        DirectedGraph temp = new DirectedGraph();
        temp.add();
        temp.add();
        temp.add();
        temp.add();
        temp.add();
        temp.add(1, 2);
        temp.add(1, 3);
        temp.add(2, 5);
        temp.add(3, 4);
        temp.add(4, 1);
        temp.add(5, 5);

        assertEquals(6, temp.edgeSize());
        temp.remove(1);

        assertEquals(3, temp.edgeSize());

        UndirectedGraph temp1 = new UndirectedGraph();
        temp1.add();
        temp1.add();
        temp1.add();
        temp1.add();
        temp1.add();
        temp1.add(1, 2);
        temp1.add(1, 3);
        temp1.add(2, 5);
        temp1.add(3, 4);
        temp1.add(4, 1);
        temp1.add(5, 5);

        assertEquals(6, temp1.edgeSize());
        temp1.remove(1);

        assertEquals(3, temp1.edgeSize());


    }
}
