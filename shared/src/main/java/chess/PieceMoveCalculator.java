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

    /**
     * Method for finding all the diagonal moves a piece could make
     *
     * @param board - the ChessBoard
     * @param myPosition - the current ChessPosition of the piece
     * @return - a collection of possible diagonal moves the piece could make
     */
    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        // check the 4 diagonal directions, do not go out of range
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn())).getTeamColor();

        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();
        ChessPiece currPiece = board.getPiece(new ChessPosition(currRow, currCol));

        // up-left
        currRow++;
        currCol--;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow++;
            currCol--;
        }


        currRow = myPosition.getRow() - 1;
        currCol = myPosition.getColumn() - 1;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // down-left
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow--;
            currCol--;
        }


        currRow = myPosition.getRow() + 1;
        currCol = myPosition.getColumn() + 1;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // up-right
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow++;
            currCol++;
        }


        currRow = myPosition.getRow() - 1;
        currCol = myPosition.getColumn() + 1;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // down-right
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow--;
            currCol++;
        }

        return moves;
    }

    /**
     * Method for finding all the orthogonal moves a piece could make
     *
     * @param board - the ChessBoard
     * @param myPosition - the current ChessPosition of the piece
     * @return - a collection of possible orthogonal moves the piece could make
     */
    public Collection<ChessMove> orthogonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        // check the 4 orthogonal directions, do not go out of range
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn())).getTeamColor();

        int currRow = myPosition.getRow();
        int currCol = myPosition.getColumn();
        ChessPiece currPiece = board.getPiece(new ChessPosition(currRow, currCol));

        // up: same column, row++
        currRow++;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));

            if (currPiece != null && currPiece.getTeamColor() == myColor) {
                break;
            }
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow++;
        }


        currRow = myPosition.getRow() - 1;
        currCol = myPosition.getColumn();
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // down: same column, row--
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));

            if (currPiece != null && currPiece.getTeamColor() == myColor) {
                break;
            }
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currRow--;
        }


        currRow = myPosition.getRow();
        currCol = myPosition.getColumn() - 1;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // left: same row, col--
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));

            if (currPiece != null && currPiece.getTeamColor() == myColor) {
                break;
            }
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currCol--;
        }


        currRow = myPosition.getRow();
        currCol = myPosition.getColumn() + 1;
        if (currRow - 1 >= 0 && currRow - 1 <= 7 && currCol - 1 >= 0 && currCol - 1 <= 7) {
            currPiece = board.getPiece(new ChessPosition(currRow, currCol));
        }
        // right: same row, col++
        while((currRow - 1 >= 0) && (currRow - 1 <= 7) && (currCol - 1 >= 0) && (currCol - 1 <= 7)
                && ((currPiece == null) || (currPiece.getTeamColor() != myColor))) {

            currPiece = board.getPiece(new ChessPosition(currRow, currCol));

            if (currPiece != null && currPiece.getTeamColor() == myColor) {
                break;
            }
            if (currPiece != null && currPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));
                break;
            }

            moves.add(new ChessMove(myPosition, new ChessPosition(currRow, currCol), null));

            currCol++;
        }

        return moves;
    }
}
