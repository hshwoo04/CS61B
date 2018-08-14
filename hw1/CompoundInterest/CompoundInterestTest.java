import static org.junit.Assert.*;
import org.junit.Test;

public class CompoundInterestTest {

    @Test 
    public void testNumYears() {
        /** Example assert statement for comparing integers.

        assertEquals(0, 0); */ 
        double delta = 1e-15;

        assertEquals(1, CompoundInterest.numYears(2015), delta);
        assertEquals(50, CompoundInterest.numYears(2064), delta);
        assertEquals(10, CompoundInterest.numYears(2024), delta);
    }

    @Test 
    public void testFutureValue() {
        double tolerance = 0.01;
        assertEquals(11, CompoundInterest.futureValue(10,10,2015), tolerance);
        assertEquals(140.49,CompoundInterest.futureValue(100,12,2017), tolerance);
    }

    @Test 
    public void testFutureValueReal() {
        double tolerance = 0.01;
        assertEquals(10.45,CompoundInterest.futureValueReal(10,10,2015,5), tolerance);
        assertEquals(11.80,CompoundInterest.futureValueReal(10,12,2016,3), tolerance);
    }


    @Test 
    public void testTotalSavings() {
        double tolerance = 0.01;  
        assertEquals(16550 ,CompoundInterest.totalSavings(5000, 2016, 10), tolerance);
        assertEquals(2500,CompoundInterest.totalSavings(1000,2015, 50), tolerance);

    }

    @Test 
    public void testTotalSavingsReal() {
        double tolerance = 0.01;    
        assertEquals(16220.655 ,CompoundInterest.totalSavingsReal(5000, 2016, 10, 1), tolerance);
        assertEquals(2425 ,CompoundInterest.totalSavingsReal(1000,2015, 50, 3), tolerance);

    }


    /* Run the unit tests in this file. */
    public static void main(String... args) {
        System.exit(ucb.junit.textui.runClasses(CompoundInterestTest.class));
    }       
}   