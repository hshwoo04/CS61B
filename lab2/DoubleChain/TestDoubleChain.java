import org.junit.Test;
import static org.junit.Assert.*;

/** Perform tests of the DoubleChain class
 */

public class TestDoubleChain {

    /** Tests the constructor of DoubleChain */
    @Test
    public void testConstructor() {
        DoubleChain d = new DoubleChain(5);
        assertEquals(d.val, 5, 1e-6);
        assertEquals(d.prev, null);        assertEquals(d.next, null);
        DoubleChain e = new DoubleChain(5);
        DoubleChain f = new DoubleChain(7);
        DoubleChain g = new DoubleChain(e, 6, f);
        assertEquals(d.val, 6, 1e-6);
        assertEquals(d.prev, e);
        assertEquals(d.next, f);
 

    }

    public static void main(String[] args) {
        ucb.junit.textui.runClasses(TestDoubleChain.class);
    }
}
