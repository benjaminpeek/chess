package chess.calculators;

import chess.ChessBoard;
import chess.ChessMove;
import chess.ChessPosition;
import chess.PieceMoveCalculator;

import java.util.Collection;
import java.util.HashSet;

public class QueenMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {

        HashSet<ChessMove> moves = new HashSet<>(this.diagonalMoves(board, myPosition));
        moves.addAll(this.orthogonalMoves(board, myPosition));

        return moves;
    }
}
