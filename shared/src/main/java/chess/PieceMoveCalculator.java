package chess;

import java.util.Collection;
import java.util.HashSet;

public abstract class PieceMoveCalculator {
    public abstract Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition);

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);

        // 4 diagonal directions to check:
        // up-left
        int checkRow = myRow + 1;
        int checkCol = myCol - 1;
        while (checkRow >= 1 && checkRow <= 8
                && checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkRow++;
            checkCol--;
        }

        // up-right
        checkRow = myRow + 1;
        checkCol = myCol + 1;
        while (checkRow >= 1 && checkRow <= 8
                && checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkRow++;
            checkCol++;
        }

        // down-left
        checkRow = myRow - 1;
        checkCol = myCol - 1;
        while (checkRow >= 1 && checkRow <= 8
                && checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkRow--;
            checkCol--;
        }

        // down-right
        checkRow = myRow - 1;
        checkCol = myCol + 1;
        while (checkRow >= 1 && checkRow <= 8
                && checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkRow--;
            checkCol++;
        }

        return moves;
    }

    public Collection<ChessMove> orthogonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);

        // 4 orthogonal directions:
        // up
        int checkRow = myRow + 1;
        while (checkRow >= 1 && checkRow <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, myCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, myCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, myCol), null));
                break;
            } else {
                break;
            }
            checkRow++;
        }

        // down
        checkRow = myRow - 1;
        while (checkRow >= 1 && checkRow <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, myCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, myCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, myCol), null));
                break;
            } else {
                break;
            }
            checkRow--;
        }

        // left
        int checkCol = myCol - 1;
        while (checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkCol--;
        }

        // right
        checkCol = myCol + 1;
        while (checkCol >= 1 && checkCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow, checkCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow, checkCol), null));
            } else if (checkPiece.getTeamColor() != myPiece.getTeamColor()){
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow, checkCol), null));
                break;
            } else {
                break;
            }
            checkCol++;
        }

        return moves;
    }
}
