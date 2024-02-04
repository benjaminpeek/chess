package chess;

import java.util.Collection;

public class RookMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return this.orthogonalMoves(board, myPosition);
    }
}
