package chess;

import java.util.Collection;
import java.util.HashSet;

public class RookMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        return new HashSet<>(orthogonalMoves(board, myPosition));
    }
}
