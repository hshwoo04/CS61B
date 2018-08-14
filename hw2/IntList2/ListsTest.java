import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *
 *  @author FIXME
 */

public class ListsTest {
    /** FIXME
     */

    // It might initially seem daunting to try to set up
    // Intlist2 expected.
   	//
    // There is an easy way to get the IntList2 that you want in just
    // few lines of code! Make note of the IntList2.list method that
    // takes as input a 2D array.

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ListsTest.class));
    }

    public static void testNaturalRuns(){
        IntList temp = IntList.list(1, 3, 7, 5, 4, 6, 9, 10, 10, 11);
        IntList array1 = IntList.list(1,3,7);
        IntList array2 = IntList.list(5);
        IntList array3 = IntList.list(4, 6, 9, 10);
        IntList array4 = IntList.list(10, 11);                        
        IntList2 answer = IntList2.list(array1,array2,array3,array4);
        assertEquals(answer, Lists.naturalRuns(temp));
    }

}
