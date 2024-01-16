package java.chess;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {
    public int row;
    public int column;
    public ChessPosition(int row, int col) {
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return row;
        throw new RuntimeException("Not implemented");//what am I supposed to do with this
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return column;
        throw new RuntimeException("Not implemented");
    }

    //toString
    //equals
    //hashCode
}
