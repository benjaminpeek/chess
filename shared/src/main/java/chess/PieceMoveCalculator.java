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

    public Collection<ChessMove> diagonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        // check the 4 diagonal directions, do not go out of range
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn())).getTeamColor();

        int curRow = myPosition.getRow();
        int curCol = myPosition.getColumn();
        ChessPiece currPiece = board.getPiece(new ChessPosition(myPosition.getRow(), myPosition.getColumn()));

        // up-left
        while((curRow - 1 >= 0) && (curRow - 1 <= 7) && (curCol - 1 >= 0) && (curCol - 1 <= 7)
                && (currPiece == null) || (currPiece.getTeamColor() != myColor)) {

            moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));

            curRow++;
            curCol--;
            currPiece = board.getPiece(new ChessPosition(curRow, curCol));
        }


        curRow = myPosition.getRow();
        curCol = myPosition.getColumn();
        // down-left
        while((curRow - 1 >= 0) && (curRow - 1 <= 7) && (curCol - 1 >= 0) && (curCol - 1 <= 7)
                && (currPiece == null) || (currPiece.getTeamColor() != myColor)) {

            moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));

            curRow--;
            curCol--;
            currPiece = board.getPiece(new ChessPosition(curRow, curCol));
        }


        curRow = myPosition.getRow();
        curCol = myPosition.getColumn();
        // up-right
        while((curRow - 1 >= 0) && (curRow - 1 <= 7) && (curCol - 1 >= 0) && (curCol - 1 <= 7)
                && (currPiece == null) || (currPiece.getTeamColor() != myColor)) {

            moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));

            curRow++;
            curCol++;
            currPiece = board.getPiece(new ChessPosition(curRow, curCol));
        }


        curRow = myPosition.getRow();
        curCol = myPosition.getColumn();
        // down-right
        while((curRow - 1 >= 0) && (curRow - 1 <= 7) && (curCol - 1 >= 0) && (curCol - 1 <= 7)
                && (currPiece == null) || (currPiece.getTeamColor() != myColor)) {

            moves.add(new ChessMove(myPosition, new ChessPosition(curRow, curCol), null));

            curRow--;
            curCol++;
            currPiece = board.getPiece(new ChessPosition(curRow, curCol));
        }


        return moves;
    }

    public Collection<ChessMove> orthogonalMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        // check all 4 orthogonal directions, do not go out of range
        int row = myPosition.getRow();
        int col = myPosition.getColumn();
        ChessGame.TeamColor myColor = board.getPiece(new ChessPosition(row, col)).getTeamColor();

        return moves;
    }
}
