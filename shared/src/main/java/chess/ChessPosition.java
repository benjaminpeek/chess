package chess;

import java.util.Objects;

/**
 * Represents a single square position on a chess board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPosition {

    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /**
     * @return which row this position is in
     * 1 codes for the bottom row
     */
    public int getRow() {
        return this.row;
    }

    /**
     * @return which column this position is in
     * 1 codes for the left row
     */
    public int getColumn() {
        return this.col;
    }

    @Override
    public String toString() {
        return this.row + "," + this.col;
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (otherObj == this) {
            return true;
        }
        if (otherObj.getClass() != this.getClass()) {
            return false;
        }

        ChessPosition otherPosition = (ChessPosition) otherObj;
        return (otherPosition.row == this.row) && (otherPosition.col == this.col);
    }

    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
