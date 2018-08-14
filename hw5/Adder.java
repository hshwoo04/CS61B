/** An alternative addition procedure based on bit operations.
 *  without using the operators +, -, /, *, or \%, ++, --, +=, -=, *=, \=,
 *  %=, or any method calls. Instead, use the bitwise operators &, |, ^, ~,
 *  <<, >>, >>>, and &=, etc.
 *  @author
 */
public class Adder {
    /** Returns X+Y. */
    public static int add(int x, int y) {
    	int tempResult = x & y;
        for (int i = 0; i < 32; i += 1) {
        	tempResult =  x & y;
        	x = tempResult << 1;
        }
        return y;
    }

}
