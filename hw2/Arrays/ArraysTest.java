import org.junit.Test;
import static org.junit.Assert.*;

/** FIXME
 *  @author FIXME
 */

public class ArraysTest {
    /** FIXME
     */

    public static void main(String[] args) {
        System.exit(ucb.junit.textui.runClasses(ArraysTest.class));
    }

public static void testCatenate(){
	int[] alpha = {1,2};
	int[] beta = {3,4,5};
	int[] gamma = {1,2,3,4,5};
	int[] intArray = Arrays.catenate(alpha, beta);
	assertEquals(intArray, gamma);
}

public static void testRemove(){
	int[] gamma = {1,2,3,4,5};
	int[] modGamma = Arrays.remove(gamma, 1, 3);
	int[] answer = {1,5};
	assertEquals(modGamma, answer);
}

}
