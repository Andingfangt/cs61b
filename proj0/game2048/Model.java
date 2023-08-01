package game2048;

import java.util.ArrayList;
import java.util.Formatter;
import java.util.Objects;
import java.util.Observable;


/** The state of a game of 2048.
 *  @author TODO: YOUR NAME HERE
 */
public class Model extends Observable {
    /** Current contents of the board. */
    private Board board;
    /** Current score. */
    private int score;
    /** Maximum score so far.  Updated when game ends. */
    private int maxScore;
    /** True iff game is ended. */
    private boolean gameOver;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */

    /** Largest piece value. */
    public static final int MAX_PIECE = 2048;

    /** A new 2048 game on a board of size SIZE with no pieces
     *  and score 0. */
    public Model(int size) {
        board = new Board(size);
        score = maxScore = 0;
        gameOver = false;
    }

    /** A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes. */
    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        int size = rawValues.length;
        board = new Board(rawValues, score);
        this.score = score;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /** Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     *  0 <= COL < size(). Returns null if there is no tile there.
     *  Used for testing. Should be deprecated and removed.
     *  */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /** Return the number of squares on one side of the board.
     *  Used for testing. Should be deprecated and removed. */
    public int size() {
        return board.size();
    }

    /** Return true iff the game is over (there are no moves, or
     *  there is a tile with value 2048 on the board). */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /** Return the current score. */
    public int score() {
        return score;
    }

    /** Return the current maximum game score (updated at end of game). */
    public int maxScore() {
        return maxScore;
    }

    /** Clear the board to empty and reset the score. */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /** Add TILE to the board. There must be no Tile currently at the
     *  same position. */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /** Tilt the board toward SIDE. Return true iff this changes the board.
     *
     * 1. If two Tile objects are adjacent in the direction of motion and have
     *    the same value, they are merged into one Tile of twice the original
     *    value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     *    tilt. So each move, every tile will only ever be part of at most one
     *    merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     *    value, then the leading two tiles in the direction of motion merge,
     *    and the trailing tile does not.
     * */
    public boolean tilt(Side side) {
        boolean changed;
        changed = false;

        // TODO: Modify this.board (and perhaps this.score) to account
        // for the tilt to the Side SIDE. If the board changed, set the
        // changed local variable to true.
        if (side != Side.NORTH){
            board.setViewingPerspective(side);
        }
        changed = helprow3(changed);
        changed = helprow2(changed);
        changed = helprow1(changed);
        changed = helprow0(changed);

        if (side != Side.NORTH){
            board.setViewingPerspective(Side.NORTH);
        }

        checkGameOver();
        if (changed) {
            setChanged();
        }

        return changed;

    }

    /** Checks if the game is over and sets the gameOver variable
     *  appropriately.
     */
    private boolean helprow3(boolean changed){
        int r = 3;
        for (int c=0; c<board.size();c++){
            if (board.tile(c,r) != null) {
                Tile t = board.tile(c, r);
                if (emptySpaceExists(board)){
                    board.move(c,r,t);
                }
            }
        }
        return changed;
    }

    private boolean helprow2(boolean changed){
        int r = 2;
        for (int c=0; c<board.size();c++){
            if (board.tile(c,r) != null){
                Tile t = board.tile(c,r);
                if (board.tile(c,r+1) == null ){
                    board.move(c,r+1,t);
                    changed = true;
                }else if (samevalue(c,r+1,c,r)){
                    board.move(c,r+1,t);
                    score += board.tile(c,r+1).value();
                    board.tile(c,r+1).ischanged = true;
                    changed = true;
                } else if (emptySpaceExists(board)) {
                    board.move(c,r,t);
                    changed =true;
                }
            }
        }
        return changed;
    }

    private boolean helprow1(boolean changed){
        int r = 1;
        for (int c=0; c<board.size();c++) {
            Tile t = board.tile(c, r);
            if (t != null) {
                if (board.tile(c, r + 1) == null) {
                    if (board.tile(c, r + 2) == null){
                        board.move(c, r + 2, t);
                        changed =true;
                    }else {
                        if(samevalue(c,r+2,c,r)){
                            board.move(c,r+2,t);
                            score += board.tile(c,r+2).value();
                        }else {
                            board.move(c,r+1,t);
                        }
                        changed =true;
                    }
                } else if (board.tile(c,r+2)==null) {
                    if (samevalue(c,r+1,c,r)){
                        board.move(c,r+2,t);
                        score += board.tile(c,r+2).value();
                        changed =true;
                    } else{
                        board.move(c,r+1,t);
                        changed =true;
                    }

                }else {
                    if (samevalue(c,r+1,c,r)){
                        board.move(c,r+1,t);
                        score += board.tile(c,r+1).value();
                        changed = true;
                    }else if (emptySpaceExists(board)) {
                        board.move(c,r,t);
                        changed =true;
                    }
                }
            }
        }
        return changed;
    }

    private boolean helprow0(boolean changed){
        int r = 0;
        for (int c =0;c<board.size();c++){
            if (board.tile(c,r) != null){
                Tile t = board.tile(c,r);
                if (r0movetor3(c)){
                    board.move(c,3,t);
                    changed = true;
                }else if (r0movetor2(c)){
                    board.move(c,2,t);
                    changed =true;
                }else if (r0movetor1(c)){
                    board.move(c,1,t);
                    changed =true;
                }

            }
        }
        return changed;
    }

    private boolean r0movetor1(int c){
        if (nullcount(c) == 0){
            if (samevalue(c,0,c,1)){
                score += board.tile(c,0).value()*2;
                return true;
            }
        }else if ((nullcount(c) == 1)){
            return !is_near_eql(c);
        }
        return false;
    }
    private boolean r0movetor2(int c) {
        if (nullcount(c) == 0){
            if (samevalue(c,0,c,1) && samevalue(c,2,c,3)){
                score += board.tile(c,0).value()*2;
                return true;
            }
        }else if (nullcount(c) == 1 ){
            if (isfourdiff(c)) {
                return false;
            } else if(is_near_eql(c)){
                if (!is_lefttow_eql(c)){
                    score += board.tile(c,0).value()*2;
                }
                return true;
            }
        }else if (nullcount(c) == 2){
            if (isfourdiff(c)){
                return true;
            } else return samebutchanged(c, exsist_r(c));
        }
        return false;
    }
    private int exsist_r(int c){
        for (int r = 1;r<=3;r++){
            if (!isnull(c,r)){
                return r;
            }
        }
        return 0;
    }
    private boolean samebutchanged(int c,int r){
        return board.tile(c,r).ischanged ;
    }
    private boolean is_lefttow_eql(int c){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            int value = isnull(c, i) ? -i : board.tile(c, i).value();
            if (value > 0) {
                list.add(value);
            }
        }
        return Objects.equals(list.get(0), list.get(1));
    }
    private boolean is_near_eql(int c){
        ArrayList<Integer> list = new ArrayList<>();
        for (int i = 3; i >= 0; i--) {
            int value = isnull(c, i) ? -i : board.tile(c, i).value();
            if (value > 0) {
                list.add(value);
            }
        }
        return Objects.equals(list.get(0), list.get(1)) || Objects.equals(list.get(1), list.get(2));
    }
    private boolean isfourdiff(int c){
        int tri = (isnull(c,3))? -3 : board.tile(c,3).value();
        int sec = (isnull(c,2))? -2 : board.tile(c,2).value();
        int one = (isnull(c,1))? -1 : board.tile(c,1).value();
        int zero = board.tile(c,0).value();
        return tri != sec && tri != one && tri != zero && sec != one && sec != zero && one != zero;
    }

    private int nullcount(int c){
        int res = 0;
        for (int r =0;r<board.size();r++){
            if (isnull(c,r)){
                res ++;
            }
        }
        return res;
    }
    private boolean r0movetor3(int c){
        if (isnull(c,1) && isnull(c,2) && isnull(c,3)){
            return true;
        }else if (tow_xx(c) || x_tow_x(c) || xx_two(c)){
            score += board.tile(c,0).value()*2;
            return true;
        }
        return false;
    }

    private boolean tow_xx(int c){
        return !isnull(c,3) && samevalue(c,0,c,3) && isnull(c,1) && isnull(c,2) && !board.tile(c,3).ischanged;
    }

    private boolean x_tow_x(int c){
        return !isnull(c,2) && samevalue(c,0,c,2) && isnull(c,1) && isnull(c,3);
    }

    private boolean xx_two(int c){
        return !isnull(c,1) && samevalue(c,0,c,1) && isnull(c,3) && isnull(c,2);
    }
    private boolean isnull(int c, int r){
        return board.tile(c,r) == null;
    }
    private boolean samevalue(int c1, int r1, int c2, int r2){
        int v1 = board.tile(c1,r1).value();
        int v2 = board.tile(c2,r2).value();
        return v1 == v2;
    }

    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    /** Determine whether game is over. */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /** Returns true if at least one space on the Board is empty.
     *  Empty spaces are stored as null.
     * */
    public static boolean emptySpaceExists(Board b) {
        int len = b.size();
        for (int i=0; i<len; i++){
            for (int j=0;j<len;j++){
               if (b.tile(i,j) == null){
                   return true;
               }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        int len = b.size();
        for (int i=0; i<len; i++){
            for (int j=0;j<len;j++){
                if (b.tile(i,j) == null){
                    continue;
                }if(b.tile(i,j).value() == MAX_PIECE){
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    public static boolean atLeastOneMoveExists(Board b) {
        if (emptySpaceExists(b)){
            return true;
        }
        int len = b.size();
        for (int i=0; i<len; i++){
            for (int j=0;j<len;j++){
                int cv = b.tile(i,j).value();
                int upv = -1;
                int rgtv = -1;
                if (i < len-1){
                    upv = b.tile(i+1,j).value();
                }
                if (j < len-1){
                    rgtv = b.tile(i, j+1).value();
                }
                if (cv == upv || cv == rgtv ){
                    return true;
                }
            }
        }
        return false;
    }


    @Override
     /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}
