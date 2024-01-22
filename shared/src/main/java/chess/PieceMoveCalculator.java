package chess;

import java.util.Collection;
import java.util.HashSet;

public abstract class PieceMoveCalculator {
    /**
     * Calculates the possible moves a piece can make at a given position.
     *
     * @param board      - the ChessBoard
     * @param myPosition - the current ChessPosition of the piece
     * @return - a collection of possible moves for the piece
     */
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);
}
