/* NOTE: The file ArrayUtil.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #2. */

/** Array utilities.
 *  @author
 */
class Arrays {
    /* 2a. */
    /** Returns a new array consisting of the elements of A followed by the
     *  the elements of B. */
    static int[] catenate(int[] A, int[] B) {
        int intLength = A.length + B.length;
        int[] combined = new int[intLength];
        int combinedIndex = 0;
        int bIndex = 0;
        while (combinedIndex < A.length){
            combined[combinedIndex] = A[combinedIndex];
            combinedIndex += 1;
        } 
        while (combinedIndex < intLength){
            combined[combinedIndex] = B[bIndex];
            combinedIndex += 1;
            bIndex += 1;
        }
        return combined;

    }

    /* 2b. */
    /** Returns the array formed by removing LEN items from A,
     *  beginning with item #START. */
    static int[] remove(int[] A, int start, int len) {
        int[] aTemp = new int[A.length-len];
        int num = 0;
        while (num < start){
            aTemp[num] = A[num];
            num++;
        }
        int num2 = start + len;
        while (num <= A.length){
            aTemp[num] = A[num2];
            num++;
            num2++;
        }
        return A;
    }

    /* 4 (optional). */
    /** Returns the array of arrays formed by breaking up A into
     *  maximal ascending lists, without reordering.
     *  For example, if A is {1, 3, 7, 5, 4, 6, 9, 10}, then
     *  returns the three-element array
     *  {{1, 3, 7}, {5}, {4, 6, 9, 10}}. */
    static int[][] naturalRuns(int[] A) {
        /* *Replace this body with the solution. */
        return null;
    }
}
