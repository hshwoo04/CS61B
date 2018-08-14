package game2048;

import ucb.util.CommandArgs;

import game2048.gui.Game;
import static game2048.Main.Side.*;

/** The main class for the 2048 game.
 *  @author Sung Hyun Harvey Woo
 */
public class Main {

    /** Size of the board: number of rows and of columns. */
    static final int SIZE = 4;
    /** Number of squares on the board. */
    static final int SQUARES = SIZE * SIZE;

    /** Symbolic names for the four sides of a board. */
    static enum Side { NORTH, EAST, SOUTH, WEST };

    /** The main program.  ARGS may contain the options --seed=NUM,
     *  (random seed); --log (record moves and random tiles
     *  selected.); --testing (take random tiles and moves from
     *  standard input); and --no-display. */
    public static void main(String... args) {
        CommandArgs options =
            new CommandArgs("--seed=(\\d+) --log --testing --no-display",
                            args);
        if (!options.ok()) {
            System.err.println("Usage: java game2048.Main [ --seed=NUM ] "
                               + "[ --log ] [ --testing ] [ --no-display ]");
            System.exit(1);
        }

        Main game = new Main(options);

        while (game.play()) {
            /* No action */
        }
        System.exit(0);
    }

    /** A new Main object using OPTIONS as options (as for main). */
    Main(CommandArgs options) {
        boolean log = options.contains("--log"),
            display = !options.contains("--no-display");
        long seed = !options.contains("--seed") ? 0 : options.getLong("--seed");
        _testing = options.contains("--testing");
        _game = new Game("2048", SIZE, seed, log, display, _testing);
    }

    /** Reset the score for the current game to 0 and clear the board. */
    void clear() {
        _score = 0;
        _count = 0;
        _game.clear();
        _game.setScore(_score, _maxScore);
        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[r][c] = 0;
            }
        }
    }

    /** Play one game of 2048, updating the maximum score. Return true
     *  iff play should continue with another game, or false to exit. */
    boolean play() {

        while (true) {
            if (firstMove) {
                setRandomPiece();
                firstMove = false;
            }
            setRandomPiece();
            _score += newScore;
            _game.setScore(_score, _maxScore);
            newScore = 0;
            _game.displayMoves();
            if (gameOver()) {
                _game.endGame();
                _maxScore = Math.max(_score, _maxScore);
                _score = 0;
                _game.setScore(0, Math.max(_score, _maxScore));
            }
        GetMove:
            while (true) {
                String key = _game.readKey();
                if (key.equals("\u2191")) {
                    key = "Up";
                }
                if (key.equals("\u2190")) {
                    key = "Left";
                }
                if (key.equals("\u2192")) {
                    key = "Right";
                }
                if (key.equals("\u2193")) {
                    key = "Down";
                }
                switch (key) {
                case "Up": case "Down": case "Left": case "Right":
                    if (!gameOver() && tiltBoard(keyToSide(key))) {
                        break GetMove;
                    }
                    break;
                case "New Game":
                    clear();
                    firstMove = true;
                    return true;
                case "Quit":
                    firstMove = true;
                    return false;
                default:
                    break;
                }
            }
            return true;
        }
    }

    /** Return true iff the current game is over (no more moves
     *  possible). */
    boolean gameOver() {

        if (_count == SIZE * SIZE && boardChecker(_board)) {
            return true;
        }

        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                if (_board[r][c] == WIN) {
                    return true;
                }
            }
        }
        return false;
    }

    /** Add a tile to a random, empty position, choosing a value (2 or
     *  4) at random.  Has no effect if the board is currently full. */
    void setRandomPiece() {
        if (_count == SQUARES) {
            return;
        }
        int[] tempTile = _game.getRandomTile();
        while (_board[tempTile[1]][tempTile[2]] != 0) {
            tempTile = _game.getRandomTile();
        }
        _board[tempTile[1]][tempTile[2]] = tempTile[0];
        _game.addTile(tempTile[0], tempTile[1], tempTile[2]);
        _count++;
        _game.displayMoves();
    }




    /** Perform the result of tilting the board toward SIDE.
     *  Returns true iff the tilt changes the board. **/
    boolean tiltBoard(Side side) {
        /** As a suggestion (see the project text), you might try copying
         * the board to a local array, turning it so that edge SIDE faces
         * north.  That way, you can re-use the same logic for all
         * directions.  (As usual, you don't have to). */

        /** The boolean trueOrFalse is an indicator if the
         * tiltboard should return true or false*/

        /** mergeHistory keeps track of squares that have already merged,
         * so as not to repeat merges.*/

        /** The method goes through each tile and decides whether or
         * not the square moves or merges starting from row 1. SecondaryR
         * are the row values of the tiles above board[r][c] and
         * emptyRow is used to determine if the rows above are all empty.
         * previousRowValue is the first value (not 0) above board[r][c].*/

        boolean[][] mergeHistory = new boolean[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                mergeHistory[i][j] = false;

            }
        }
        int[][] board = new int[SIZE][SIZE];


        boolean trueOrFalse = false;

        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                board[r][c] =
                    _board[tiltRow(side, r, c)][tiltCol(side, r, c)];
            }
        }




        for (int r = 1; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                if (board[r][c] != 0) {
                    int secondaryR = r - 1;

                    while (secondaryR >= 0) {
                        int previousRowValue = board[secondaryR][c];
                        int emptyRow = secondaryR;

                        while (emptyRow >= 0) {
                            if (board[emptyRow][c] != 0) {
                                break;
                            }
                            if (emptyRow == 0) {
                                _game.moveTile(board[r][c],
                                    tiltRow(side, r, c), tiltCol(side, r, c),
                                    tiltRow(side, emptyRow, c),
                                    tiltCol(side, emptyRow, c));
                                board[emptyRow][c] = board[r][c];
                                board[r][c] = 0;
                                secondaryR = -1;
                                emptyRow = -1;
                                trueOrFalse = true;
                            }
                            emptyRow--;
                        }

                        if (previousRowValue == 0) {
                            secondaryR--;
                        } else if (previousRowValue == board[r][c]) {
                            if (mergeHistory[secondaryR][c]) {
                                if (secondaryR == r) {
                                    secondaryR = -1;
                                } else {
                                    _game.moveTile(board[r][c],
                                        tiltRow(side, r, c),
                                        tiltCol(side, r, c),
                                        tiltRow(side, secondaryR + 1, c),
                                        tiltCol(side, secondaryR + 1, c));
                                    board[secondaryR + 1][c] = board[r][c];
                                    board[r][c] = 0;
                                    secondaryR = -1;
                                    trueOrFalse = true;
                                }
                            } else if (!mergeHistory[secondaryR][c]) {
                                newScore += (previousRowValue * 2);
                                _game.mergeTile(board[r][c],
                                    previousRowValue * 2, tiltRow(side, r , c),
                                    tiltCol(side, r, c),
                                    tiltRow(side, secondaryR, c),
                                    tiltCol(side, secondaryR, c));
                                board[secondaryR][c] = board[r][c] * 2;
                                board[r][c] = 0;
                                mergeHistory[secondaryR][c] = true;
                                secondaryR = -1;
                                _count--;
                                trueOrFalse = true;
                            }
                        } else if (previousRowValue != board[r][c]) {
                            if (secondaryR + 1 == r) {
                                secondaryR = -1;
                            } else {
                                _game.moveTile(board[r][c], tiltRow(side, r, c)
                                    , tiltCol(side, r, c),
                                    tiltRow(side, secondaryR + 1, c),
                                    tiltCol(side, secondaryR + 1, c));
                                board[secondaryR + 1][c] = board[r][c];
                                board[r][c] = 0;
                                secondaryR = -1;
                                trueOrFalse = true;
                            }
                        }
                    }
                }
            }
        }

        for (int r = 0; r < SIZE; r += 1) {
            for (int c = 0; c < SIZE; c += 1) {
                _board[tiltRow(side, r, c)][tiltCol(side, r, c)]
                    = board[r][c];
            }
        }

        _game.displayMoves();
        return trueOrFalse;
    }


    /** Return the row number on a playing board that corresponds to row R
     *  and column C of a board turned so that row 0 is in direction SIDE (as
     *  specified by the definitions of NORTH, EAST, etc.).  So, if SIDE
     *  is NORTH, then tiltRow simply returns R (since in that case, the
     *  board is not turned).  If SIDE is WEST, then column 0 of the tilted
     *  board corresponds to row SIZE - 1 of the untilted board, and
     *  tiltRow returns SIZE - 1 - C. */
    int tiltRow(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return r;
        case EAST:
            return c;
        case SOUTH:
            return SIZE - 1 - r;
        case WEST:
            return SIZE - 1 - c;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** @param tempBoard When the board is full, boardChecker checks the board
     * to see if there are possible moves available. returns a boolean value. */

    boolean boardChecker(int[][] tempBoard) {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (i - 1 >= 0 && i - 1 < SIZE && j >= 0 && j < SIZE) {
                    if (tempBoard[i][j] == tempBoard[i - 1][j]) {
                        return false;
                    }
                }
                if (i >= 0 && i < SIZE && j - 1 >= 0 && j - 1 < SIZE) {
                    if (tempBoard[i][j] == tempBoard[i][j - 1]) {
                        return false;
                    }
                }
                if (i + 1 >= 0 && i + 1 < SIZE && j >= 0 && j < SIZE) {
                    if (tempBoard[i][j] == tempBoard[i + 1][j]) {
                        return false;
                    }
                }
                if (i >= 0 && i < SIZE && j + 1 >= 0 && j + 1 < SIZE) {
                    if (tempBoard[i][j] == tempBoard[i][j + 1]) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    /** Return the column number on a playing board that corresponds to row
     *  R and column C of a board turned so that row 0 is in direction SIDE
     *  (as specified by the definitions of NORTH, EAST, etc.). So, if SIDE
     *  is NORTH, then tiltCol simply returns C (since in that case, the
     *  board is not turned).  If SIDE is WEST, then row 0 of the tilted
     *  board corresponds to column 0 of the untilted board, and tiltCol
     *  returns R. */
    int tiltCol(Side side, int r, int c) {
        switch (side) {
        case NORTH:
            return c;
        case EAST:
            return SIZE - 1 - r;
        case SOUTH:
            return SIZE - 1 - c;
        case WEST:
            return r;
        default:
            throw new IllegalArgumentException("Unknown direction");
        }
    }

    /** Return the side indicated by KEY ("Up", "Down", "Left",
     *  or "Right"). */
    Side keyToSide(String key) {
        switch (key) {
        case "Up":
            return NORTH;
        case "Down":
            return SOUTH;
        case "Left":
            return WEST;
        case "Right":
            return EAST;
        default:
            throw new IllegalArgumentException("unknown key designation");
        }
    }

    /** Score needed to win. */
    static final int WIN = 2048;
    /** Score to add to current score. */
    private int newScore = 0;
    /** Represents whether or not it is the first move.
     * used for when to set random tile once or twice.*/
    private boolean firstMove = true;
    /** Represents the board: _board[r][c] is the tile value at row R,
     *  column C, or 0 if there is no tile there. */
    private final int[][] _board = new int[SIZE][SIZE];

    /** True iff --testing option selected. */
    private boolean _testing;
    /** THe current input source and output sink. */
    private Game _game;
    /** The score of the current game, and the maximum final score
     *  over all games in this session. */
    private int _score, _maxScore;
    /** Number of tiles on the board. */
    private int _count;
}
