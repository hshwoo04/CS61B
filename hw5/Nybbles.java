        /** Represents an array of integers each in the range -8..7.
 *  Such integers may be represented in 4 bits (called nybbles).
 *  @author
 */
public class Nybbles {
    /** Return an array of size N. */
    public Nybbles(int N) {
        // DON'T CHANGE THIS.
        _data = new int[(N + 7) / 8];
        _n = N;
    }

    /** Return the size of THIS. */
    public int size() {
        return _n;
    }

    /** Return the Kth integer in THIS array, numbering from 0.
     *  Assumes 0 <= K < N. */
    public int get(int k) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else {
            int nybbleIndex = (k/8)-1;
            int intIndex = (nybbleIndex/8) - 1;
            int kNybble = _data[intIndex] << (4*(k-1));
            kNybble = kNybble >>> 28;
            return kNybble;
        }
    }

    /** Set the Kth integer in THIS array to VAL.  Assumes
     *  0 <= K < N and -8 <= VAL < 8. */
    public void set(int k, int val) {
        if (k < 0 || k >= _n) {
            throw new IndexOutOfBoundsException();
        } else if (val < -8 || val >= 8) {
            throw new IllegalArgumentException();
        } else {
            int left = _data[(k/8)-(k%8)] >>> (4 * k);
            left = left << (4 * k);
            int right = _data[(k/8)-(k%8)] << ((9-k)*4);
            right = right >>>((9-k)*4);
            int middle = val << ((8-k)*4);

            _data[ (k/8) - (k%8) ] =
                left + middle + right;
        }
    }

    // DON'T CHANGE OR ADD TO THESE.
    /** Size of current array (in nybbles). */
    private int _n;
    /** The array data, packed 8 nybbles to an int. */
    private int[] _data;
}
