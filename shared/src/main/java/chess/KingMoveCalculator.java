package chess;

import java.util.Collection;
import java.util.HashSet;

public class KingMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(row, col)).getTeamColor();

        // column to the left of the king
        int checkRow = row - 1;
        for (int i = 0; i < 3; i++) {
            if (checkRow <= 7 && ((col - 1) <= 7)) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, col - 1));
                if (checkPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col - 1), null));
                }
                else if (checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col - 1), null));
                }
            }

            checkRow++;
        }

        // same column as the king
        checkRow = row - 1;
        for (int i = 0; i < 3; i++) {
            if (checkRow <= 7) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, col));
                if (checkPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col), null));
                }
                else if (checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col), null));
                }
            }

            checkRow++;
        }

        // column to right of the king
        checkRow = row - 1;
        for (int i = 0; i < 3; i++) {
            if (checkRow <= 7 && ((col + 1) <= 7)) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, col + 1));
                if (checkPiece == null) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col + 1), null));
                }
                else if (checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, col + 1), null));
                }
            }

            checkRow++;
        }

        return moves;
    }
}