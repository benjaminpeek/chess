package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);

        // check three columns: left of the king, same as the king, right of the king
        // column to the left of the king (myCol - 1)
        for (int currRow = myRow - 1; currRow < myRow + 2; currRow++) {
            if (currRow >= 1 && currRow <= 8) {
                if (myCol - 1 >= 1 && myCol - 1 <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(currRow, myCol - 1));
                    if (checkPiece == null || checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(currRow, myCol - 1), null));
                    }
                }
            }
        }

        // same column as the king
        for (int currRow = myRow - 1; currRow < myRow + 2; currRow++) {
            if (currRow >= 1 && currRow <= 8) {
                if (myCol >= 1 && myCol <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(currRow, myCol));
                    if (checkPiece == null || checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(currRow, myCol), null));
                    }
                }
            }
        }

        // column to the right of the king
        for (int currRow = myRow - 1; currRow < myRow + 2; currRow++) {
            if (currRow >= 1 && currRow <= 8) {
                if (myCol + 1 >= 1 && myCol + 1 <= 8) {
                    ChessPiece checkPiece = board.getPiece(new ChessPosition(currRow, myCol + 1));
                    if (checkPiece == null || checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(currRow, myCol + 1), null));
                    }
                }
            }
        }

        return moves;
    }
}
