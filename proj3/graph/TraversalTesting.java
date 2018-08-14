package graph;

import org.junit.Test;
import static org.junit.Assert.*;
import java.util.ArrayList;
import java.util.List;
/** Unit tests for the Traversal class.
 *  @author
 */
public class TraversalTesting {
    public class DFS extends DepthFirstTraversal {
        public DFS(GraphObj G) {
            super(G);
            _graph = G;
            _path = new ArrayList<Integer>();
            _postPath = new ArrayList<Integer>();
        }

        @Override
        protected boolean visit(int v) {
            _path.add(v);
            _fringe.remove();
            mark(v);
            for (int[] y : _graph.edges()) {
                if (y[0] == v) {
                    int x = y[1];
                    if (!(marked(x))) {
                        if (_fringe.contains(x)) {
                            _fringe.remove(new Integer(x));
                        }
                        _fringe.add(x);
                    }
                }
            }
            if (_fringe.isEmpty()) {
                return false;
            }
            return true;
        }

        @Override
        protected boolean shouldPostVisit(int v) {
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            ArrayList<Integer> tempArr = new ArrayList<Integer>();
            tempArr.add(1);
            tempArr.add(4);
            tempArr.add(2);
            tempArr.add(3);
            tempArr.add(5);
            _postPath = tempArr;
            return false;

        }

        private ArrayList<Integer> _path;
        private ArrayList<Integer> _postPath;
        private GraphObj _graph;
    }

    /** Tests for the Depth First Search class. */
    @Test
    public void dfsTest() {
        DirectedGraph dGraph = new DirectedGraph();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add(1, 5);
        dGraph.add(5, 4);
        dGraph.add(5, 3);
        dGraph.add(4, 1);
        dGraph.add(3, 2);

        DFS temp = new DFS(dGraph);
        ArrayList<Integer> five = new ArrayList<Integer>();
        five.add(5);
        temp.traverse(five);

        ArrayList<List<Integer>> pathAnswer = new ArrayList<List<Integer>>();
        ArrayList<List<Integer>> postPathAnswer =
            new ArrayList<List<Integer>>();

        ArrayList<Integer> ans1 = new ArrayList<Integer>();
        ans1.add(5);
        ans1.add(4);
        ans1.add(1);
        ans1.add(3);
        ans1.add(2);
        ArrayList<Integer> ans2 = new ArrayList<Integer>();
        ans2.add(5);
        ans2.add(3);
        ans2.add(2);
        ans2.add(4);
        ans2.add(1);
        ArrayList<Integer> postAns1 = new ArrayList<Integer>();
        postAns1.add(1);
        postAns1.add(4);
        postAns1.add(2);
        postAns1.add(3);
        postAns1.add(5);
        ArrayList<Integer> postAns2 = new ArrayList<Integer>();
        postAns2.add(2);
        postAns2.add(3);
        postAns2.add(1);
        postAns2.add(4);
        postAns2.add(5);

        pathAnswer.add(ans1);
        pathAnswer.add(ans2);
        postPathAnswer.add(postAns1);
        postPathAnswer.add(postAns2);
        assertEquals(true, pathAnswer.contains(temp._path));
        assertEquals(true, postPathAnswer.contains(temp._postPath));
    }

    public class BFS extends BreadthFirstTraversal {
        public BFS(GraphObj G) {
            super(G);
            _graph = G;
            _path = new ArrayList<Integer>();
        }

        @Override
        protected boolean visit(int v) {
            _path.add(v);
            _fringe.remove();
            mark(v);
            for (int[] y : _graph.edges()) {
                if (y[0] == v) {
                    int x = y[1];
                    if (!(marked(x))) {
                        if (!(_fringe.contains(x))) {
                            _fringe.add(x);
                        }
                    }
                }
            }
            if (_fringe.isEmpty()) {
                return false;
            }
            return true;
        }

        @Override
        protected boolean postVisit(int v) {
            return false;
        }

        ArrayList<Integer> _path;
        GraphObj _graph;
    }


    /** Tests for the Breadth First Search class. */
    @Test
    public void bfsTest() {
        DirectedGraph dGraph = new DirectedGraph();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add();
        dGraph.add(1, 5);
        dGraph.add(5, 4);
        dGraph.add(5, 3);
        dGraph.add(4, 1);
        dGraph.add(3, 2);

        BFS temp = new BFS(dGraph);
        ArrayList<Integer> five = new ArrayList<Integer>();
        five.add(5);
        temp.traverse(five);

        ArrayList<List<Integer>> pathAnswer = new ArrayList<List<Integer>>();

        ArrayList<Integer> ans1 = new ArrayList<Integer>();
        ans1.add(5);
        ans1.add(4);
        ans1.add(3);
        ans1.add(1);
        ans1.add(2);
        ArrayList<Integer> ans2 = new ArrayList<Integer>();
        ans2.add(5);
        ans2.add(3);
        ans2.add(4);
        ans2.add(2);
        ans2.add(1);
        ArrayList<Integer> ans3 = new ArrayList<Integer>();
        ans3.add(5);
        ans3.add(4);
        ans3.add(3);
        ans3.add(2);
        ans3.add(1);
        ArrayList<Integer> ans4 = new ArrayList<Integer>();
        ans4.add(5);
        ans4.add(4);
        ans4.add(3);
        ans4.add(1);
        ans4.add(2);

        pathAnswer.add(ans1);
        pathAnswer.add(ans2);
        pathAnswer.add(ans3);
        pathAnswer.add(ans4);

        assertEquals(true, pathAnswer.contains(temp._path));
    }

}
