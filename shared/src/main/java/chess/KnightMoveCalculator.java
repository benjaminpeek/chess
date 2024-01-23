package chess;

import org.junit.jupiter.api.parallel.Resources;

import java.util.Collection;
import java.util.HashSet;

public class KnightMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(row, col)).getTeamColor();

        // left 2, up 1
        int checkRow = row + 1;
        int checkCol = col - 2;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // left 2, down 1
        checkRow = row - 1;
        checkCol = col - 2;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // left 1, up 2
        checkRow = row + 2;
        checkCol = col - 1;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // left 1, down 2
        checkRow = row - 2;
        checkCol = col - 1;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // right 2, up 1
        checkRow = row + 1;
        checkCol = col + 2;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // right 2, down 1
        checkRow = row - 1;
        checkCol = col + 2;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // right 1, up 2
        checkRow = row + 2;
        checkCol = col + 1;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        // right 1, down 2
        checkRow = row - 2;
        checkCol = col + 1;
        if ((checkRow - 1 >= 0) && (checkRow - 1 <= 7) && (checkCol - 1 >= 0) && (checkCol - 1 <= 7)) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null || checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            }
        }

        return moves;
    }
}
