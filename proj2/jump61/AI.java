
package jump61;

import java.util.ArrayList;

/** An automated Player.
 *  @author Harvey Woo
 */
class AI extends Player {

    /** Time allotted to all but final search depth (milliseconds). */
    private static final long TIME_LIMIT = 15000;

    /** Number of calls to minmax between checks of elapsed time. */
    private static final long TIME_CHECK_INTERVAL = 10000;

    /** Number of milliseconds in one second. */
    private static final double MILLIS = 1000.0;

    /** A new player of GAME initially playing COLOR that chooses
     *  moves automatically.
     */
    public AI(Game game, Side color) {
        super(game, color);
    }

    @Override
    void makeMove() {
        try {
            ArrayList<Integer> mainMoves = new ArrayList<Integer>();
            MutableBoard temp = new MutableBoard(getGame().getBoard());
            minmax(getSide(), temp, 3, Integer.MIN_VALUE, mainMoves);
            int bestMove = mainMoves.get((int)
                (Math.random() * mainMoves.size()));
            while (!(getBoard().isLegal(getSide(), bestMove))) {
                bestMove = mainMoves.get
                ((int) (Math.random() * mainMoves.size()));
            }
            getGame().makeMove(bestMove);
            getGame().reportMove(getSide(), getBoard().
                row(bestMove), getBoard().col(bestMove));
        } catch (GameException ex) {
            getGame().reportError("Invalid command!");
        }
    }

    /** Return the minimum of CUTOFF and the minmax value of board B
     *  (which must be mutable) for player P to a search depth of D
     *  (where D == 0 denotes statically evaluating just the next move).
     *  If MOVES is not null and CUTOFF is not exceeded, set MOVES to
     *  a list of all highest-scoring moves for P; clear it if
     *  non-null and CUTOFF is exceeded. the contents of B are
     *  invariant over this call. */
    private int minmax(Side p, Board b, int d, int cutoff,
                       ArrayList<Integer> moves) {
        int firstMove = d;
        if (d == 0) {
            return staticEval(p, b);
        } else if (d % 2 == 1) {
            int max = Integer.MIN_VALUE;
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size(); j++) {
                    if (b.get(i + 1, j + 1).getSide() == p
                        || b.get(i + 1, j + 1).getSide() == Side.WHITE) {
                        b.addSpot(p, i + 1, j + 1);
                        MutableBoard temp = new MutableBoard(b);
                        b.undo();
                        ArrayList<Integer> empty = new ArrayList<Integer>();
                        int tempValue = minmax(p.opposite(), temp,
                            d - 1, max, empty);
                        if (max < tempValue) {
                            max = tempValue;
                            if (d == firstMove) {
                                moves.clear();
                                moves.add(b.sqNum(i + 1, j + 1));
                            }
                        } else if (max == tempValue) {
                            if (d == firstMove) {
                                moves.add(b.sqNum(i + 1, j + 1));
                            }
                        }
                    }
                }
            }
            return max;
        } else if (d % 2 == 0) {
            int min = Integer.MAX_VALUE;
            for (int i = 0; i < b.size(); i++) {
                for (int j = 0; j < b.size(); j++) {
                    if (b.get(i + 1, j + 1).getSide() == p
                        || b.get(i + 1, j + 1).getSide() == Side.WHITE) {
                        b.addSpot(p, i + 1, j + 1);
                        MutableBoard temp = new MutableBoard(b); b.undo();
                        ArrayList<Integer> empty = new ArrayList<Integer>();
                        int tempValue = minmax(p.opposite(), temp,
                            d - 1, min, empty);
                        if (min > tempValue) {
                            min = tempValue;
                            if (d == firstMove) {
                                moves.clear();
                                moves.add(b.sqNum(i + 1, j + 1));
                            }
                        } else if (min == tempValue) {
                            if (d == firstMove) {
                                moves.add(b.sqNum(i + 1, j + 1));
                            }
                        }
                    }
                }
            }
            return min;
        }
        return cutoff;
    }

    /** Returns heuristic value of board B for player P.
     *  Higher is better for P. */
    private int staticEval(Side p, Board b) {
        double totalP = 0;
        for (int i = 0; i < b.size(); i++) {
            for (int j = 0; j < b.size(); j++) {
                if (b.get(i + 1, j + 1).getSide() == p) {
                    double curP = 0;
                    int numNeighbors = b.neighbors(i + 1, j + 1);
                    int numSpots = b.get(i + 1, j + 1).getSpots();
                    if (numNeighbors == 2) {
                        curP += (TWO * b.size());
                        if (numSpots == 2) {
                            curP += 2;
                        }
                    } else if (numNeighbors == 3) {
                        curP += (ONE * b.size());
                        if (numSpots == 3) {
                            curP += THREE;
                        } else if (numSpots == 2) {
                            curP += ONE;
                        }
                    } else if (numNeighbors == 4) {
                        curP += (1 * b.size());
                        if (numSpots == 4) {
                            curP += 3;
                        } else if (numSpots == 3) {
                            curP += 2;
                        } else if (numSpots == 2) {
                            curP += 1;
                        }
                    }
                    totalP += curP;
                }
            }
        }
        int totalPoints = (int) (totalP + 0.5);
        return totalPoints;
    }
    /** value 1.25.*/
    public static final double ONE = 1.25;
    /** value 1.5.*/
    public static final double TWO = 1.5;
    /** value 2.5.*/
    public static final double THREE = 2.5;
}
