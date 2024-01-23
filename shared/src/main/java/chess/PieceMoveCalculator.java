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

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();



        return moves;
    }

    public Collection<ChessMove> orthogonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();



        return moves;
    }
}
