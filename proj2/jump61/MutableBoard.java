
package jump61;

import static jump61.Side.*;
import static jump61.Square.square;
import java.util.Stack;


/** A Jump61 board state that may be modified.
 *  @author Harvey Woo
 */
class MutableBoard extends Board {

    /** An N x N board in initial configuration. */
    MutableBoard(int N) {
        _board =  new Square[N][N];
        _undoStack = new Stack<ConstantBoard>();
        _size = N;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                _board[i][j] = square(WHITE, 1);
            }
        }
    }

    /** A board whose initial contents are copied from BOARD0, but whose
     *  undo history is clear. */
    MutableBoard(Board board0) {
        this(board0.size());
        Square[][] tempArr = new Square[_size][_size];
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                tempArr[i][j] = board0.get(i + 1, j + 1);
            }
        }
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                _board[i][j] = tempArr[i][j];
            }
        }
    }

    @Override
    void clear(int N) {
        _board = new Square[N][N];
        _size = N;
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                _board[i][j] = square(WHITE, 1);
            }
        }
        _undoStack = new Stack<ConstantBoard>();
        announce();
    }

    @Override
    void copy(Board board) {
        Square[][] tempArr = new Square[_size][_size];
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                tempArr[i][j] = board.get(i + 1, j + 1);
            }
        }
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                _board[i][j] = tempArr[i][j];
            }
        }
    }

    /** Copy the contents of BOARD into me, without modifying my undo
     *  history.  Assumes BOARD and I have the same size. */
    private void internalCopy(MutableBoard board) {
        Square[][] tempArr = new Square[_size][_size];
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                tempArr[i][j] = board.get(i + 1, j + 1);
            }
        }
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                _board[i][j] = tempArr[i][j];
            }
        }
    }

    @Override
    int size() {
        return _size;
    }

    @Override
    Square get(int n) {
        return _board[row(n) - 1][col(n) - 1];
    }

    @Override
    int numOfSide(Side side) {
        int count = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                if (_board[i][j].getSide().equals(side)) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    int numPieces() {
        int count = 0;
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                count = count + _board[i][j].getSpots();
            }
        }
        return count;
    }

    @Override
    void addSpot(Side player, int r, int c) {
        markUndo();
        Square tempSquare = this._board[r - 1][c - 1];
        int numSpots = tempSquare.getSpots() + 1;
        _board[r - 1][c - 1] = Square.square(player, numSpots);
        announce();
        while (overfilled()) {
            if (numOfSide(RED) == _size * _size || numOfSide(BLUE)
                == _size * _size) {
                getWinner();
                break;
            }
            addSpotCheck();
            announce();
        }
        announce();
    }

    @Override
    void addSpot(Side player, int n) {
        markUndo();
        Square tempSquare = get(n);
        int numSpots = tempSquare.getSpots() + 1;
        _board[row(n) - 1][col(n) - 1] = Square.square(player, numSpots);
        announce();
        while (overfilled()) {
            if (numOfSide(RED) == _size * _size || numOfSide(BLUE)
                == _size * _size) {
                getWinner();
                break;
            }
            addSpotCheck();
            announce();
        }
        announce();
    }

    /** Does the exact same thing as addspot but without the overfull checks
    /* simply adds one dot and ends. The ROW and COL start from 0.
    /* PLAYER is the side. */
    void altAddSpot(Side player, int row, int col) {
        Square tempSquare = _board[row][col];
        int numSpots = tempSquare.getSpots() + 1;
        _board[row][col] = Square.square(player, numSpots);
        announce();
    }

    /** Returns true if any of the squares are overfilled. */
    boolean overfilled() {
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                int numNeighbors = neighbors(i + 1, j + 1);
                if (_board[i][j].getSpots() > numNeighbors) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Checks the board for any overfilled squares and adjusts spots
    /* accordingly. */
    void addSpotCheck() {
        for (int i = 0; i < _size; i++) {
            for (int j = 0; j < _size; j++) {
                int numNeighbors = neighbors(i + 1, j + 1);
                if (_board[i][j].getSpots() > numNeighbors) {
                    if (i - 1 >= 0) {
                        altAddSpot(_board[i][j].getSide(), i - 1, j);
                    }
                    if (i + 1 < _size) {
                        altAddSpot(_board[i][j].getSide(), i + 1, j);
                    }
                    if (j + 1 < _size) {
                        altAddSpot(_board[i][j].getSide(), i, j + 1);
                    }
                    if (j - 1 >= 0) {
                        altAddSpot(_board[i][j].getSide(), i, j - 1);
                    }
                    internalSet((sqNum(i + 1, j + 1)),
                        square(_board[i][j].getSide(), 1));
                }
            }
        }
    }



    @Override
    void set(int r, int c, int num, Side player) {
        if (num <= 0) {
            internalSet(sqNum(r, c), Square.INITIAL);
        } else {
            internalSet(sqNum(r, c), square(player, num));
        }
        clearUndoHistory();
    }

    @Override
    void set(int n, int num, Side player) {
        if (num <= 0) {
            internalSet(n, Square.INITIAL);
        } else {
            internalSet(n, square(player, num));
        }
        clearUndoHistory();
        announce();
    }


    @Override
    void undo() {
        if (!_undoStack.empty()) {
            ConstantBoard tempBoard = _undoStack.pop();
            for (int i = 0; i < _size; i++) {
                for (int j = 0; j < _size; j++)  {
                    _board[i][j] = tempBoard.get(i + 1, j + 1);
                }
            }
        }
    }

    /** Record the beginning of a move in the undo history. */
    private void markUndo() {
        MutableBoard temp = new MutableBoard(this);
        _undoStack.push(new ConstantBoard(temp));
    }

    /** Clears the Undo history. */
    private void clearUndoHistory() {
        _undoStack = new Stack<ConstantBoard>();
    }

    /** Set the contents of the square with index IND to SQ. Update counts
     *  of numbers of squares of each color.  */
    private void internalSet(int ind, Square sq) {
        _board[row(ind) - 1][col(ind) - 1] = sq;
    }

    /** Notify all Observers of a change. */
    private void announce() {
        setChanged();
        notifyObservers();
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof MutableBoard)) {
            return obj.equals(this);
        } else {
            for (int i = 0; i < _size; i++) {
                for (int j = 0; j < _size; j++) {
                    MutableBoard mutObj = (MutableBoard) obj;
                    if (_board[i][j].getSpots()
                        == mutObj._board[i][j].getSpots()) {
                        if (_board[i][j].getSide()
                            == mutObj._board[i][j].getSide()) {
                            return true;
                        }
                        return false;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return this.hashCode();
    }
    /** Default board that is an array of arrays. */
    private Square[][] _board;
    /** Size of each side. */
    private int _size;
    /** Stack for the undo function. Stores ConstantBoard objects. */
    private Stack<ConstantBoard> _undoStack;
}
