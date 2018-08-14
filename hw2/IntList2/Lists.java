/* NOTE: The file Utils.java contains some functions that may be useful
 * in testing your answers. */

/** HW #2, Problem #1. */

/** List problem.
 *  @author
 */
class Lists {
    /** Return the list of lists formed by breaking up L into "natural runs":
     *  that is, maximal strictly ascending sublists, in the same order as
     *  the original.  For example, if L is (1, 3, 7, 5, 4, 6, 9, 10, 10, 11),
     *  then result is the four-item list ((1, 3, 7), (5), (4, 6, 9, 10), (10, 11)).
     *  Destructive: creates no new IntList items, and may modify the
     *  original list pointed to by L. */
    static IntList2 naturalRuns(IntList L) {
        IntList2 output = new IntList2();
        IntList temp = new IntList();
        while (L.tail != null){
            if (L.head < L.tail.head){
            temp = new IntList(L.head, temp);
            L = L.tail;
            }
            else{
            temp = Utils.reverse(temp);
            output = new IntList2(temp,output);
            L = L.tail;
            }
        }
        output = Utils.reverse(output);
        return output;
        }

}
