package chess.calculators;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);

        // 8 spaces to check for the knight:
        // left 1 up 2
        int checkCol = myCol - 1;
        int checkRow = myRow + 2;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // left 1 down 2
        checkCol = myCol - 1;
        checkRow = myRow - 2;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // left 2 up 1
        checkCol = myCol - 2;
        checkRow = myRow + 1;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // left 2 down 1
        checkCol = myCol - 2;
        checkRow = myRow - 1;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // right 1 up 2
        checkCol = myCol + 1;
        checkRow = myRow + 2;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // right 1 down 2
        checkCol = myCol + 1;
        checkRow = myRow - 2;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // right 2 up 1
        checkCol = myCol + 2;
        checkRow = myRow + 1;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        // right 2 down 1
        checkCol = myCol + 2;
        checkRow = myRow - 1;
        knightMoveCheck(checkRow, checkCol, board, new ChessPosition(checkRow, checkCol), moves, myPiece, myPosition);

        return moves;
    }

    private void knightMoveCheck(int checkRow, int checkCol, ChessBoard board, ChessPosition checkPosition,
                                 HashSet<ChessMove> moves, ChessPiece myPiece, ChessPosition myPosition) {
        if (checkCol >= 1 && checkCol <= 8
                && checkRow >= 1 && checkRow <= 8) {
            ChessPiece checkPiece = board.getPiece(checkPosition);
            if (checkPiece == null || checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }
    }
}
