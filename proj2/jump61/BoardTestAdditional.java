/** tests for MutableBoard and Board */

package jump61;

import static jump61.Side.*;
import org.junit.Test;
import static org.junit.Assert.*;

/* Many of the tests require a print statement and do not
/* use the assertEquals method. */

class BoardTestAdditional {

    @Test
    public void testClear() {
        Board B = new MutableBoard(5);
        B.clear(4);
        System.out.println(B.toString());
    }

    @Test
    public void testCopy() {
        Board B = new MutableBoard(5);
        Board C = new MutableBoard(5);
        C.copy(B);
        assertEquals(true, B.equals(C));
    }

    @Test
    public void testAddSpot() {
        Board B = new MutableBoard(5);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 3);
        System.out.println(B.toString());
    }

    @Test
    public void testOverfilled() {
        MutableBoard B = new MutableBoard(2);
        B.addSpot(RED, 1, 1);
        B.addSpot(BLUE, 1, 2);
        B.addSpot(RED, 2, 1);
        B.addSpot(BLUE, 2, 2);
        assertEquals(true, B.overfilled());
    }

    @Test
    public void testSet() {
        Board B = new MutableBoard(5);
        B.set(1, 1, 2, RED);
        B.set(1, 2, 2, BLUE);
        System.out.println(B.toString());
    }

    /* To test markUndo(), the method must be changed to public temporarily.

    @Test
    public void testUndo() {
        MutableBoard B = new MutableBoard(5);
        MutableBoard C = new MutableBoard(5);
        C.copy(B);
        B.markUndo();
        B.addSpot(RED, 1, 1);
        B.undo();
        assertEquals(true, B.equals(C));
    }*/
}
