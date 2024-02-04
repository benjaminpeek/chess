package chess;

import java.util.Collection;

public class BishopMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return this.diagonalMoves(board, myPosition);
    }
}
