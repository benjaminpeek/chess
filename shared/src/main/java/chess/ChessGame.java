package chess;

import java.util.Collection;
import java.util.HashSet;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor teamTurn;
    private ChessBoard board;

    // helper variable to undo simulated moves
    private ChessPiece takenPiece;

    public ChessGame() {
        teamTurn = TeamColor.WHITE;
        board = new ChessBoard();
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return this.teamTurn;
    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */
    public void setTeamTurn(TeamColor team) {
        this.teamTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessPiece piece = this.getBoard().getPiece(startPosition);
        if (piece == null) {
            return new HashSet<>();
        }
        HashSet<ChessMove> possibleMoves = (HashSet<ChessMove>)piece.pieceMoves(this.getBoard(), startPosition);

        HashSet<ChessMove> viableMoves = new HashSet<>();
        for (ChessMove move : possibleMoves) {
            // simulate the move
            simulateMove(move);
            // if the simulated move has not left the king in check, consider it valid
            if (!isInCheck(piece.getTeamColor())) {
                viableMoves.add(move);
            }
            undoMove(move);
        }
        return viableMoves;
    }

    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        if (board.getPiece(move.getStartPosition()).getTeamColor() != this.getTeamTurn()) {
            throw new InvalidMoveException();
        }
        HashSet<ChessMove> validMoves = (HashSet<ChessMove>) validMoves(move.getStartPosition());
        if (validMoves.isEmpty()) {throw new InvalidMoveException();}
        if (validMoves.contains(move)) {
            ChessPiece piece = this.getBoard().getPiece(move.getStartPosition());
            // move piece to the new position, promote if necessary
            if (move.getPromotionPiece() != null) {
                this.getBoard().addPiece(move.getEndPosition(), new ChessPiece(this.getTeamTurn(), move.getPromotionPiece()));
            } else {
                this.getBoard().addPiece(move.getEndPosition(), piece);
            }
            // make the piece's old position null
            this.getBoard().addPiece(move.getStartPosition(), null);
            // change the teamTurn
            if (this.getTeamTurn() == TeamColor.WHITE) {
                this.setTeamTurn(TeamColor.BLACK);
            } else {
                this.setTeamTurn(TeamColor.WHITE);
            }
        } else {
            throw new InvalidMoveException();
        }
    }

    /**
     * Helper method to simulate a move whether it is valid or not
     *
     * @param move - the ChessMove to simulate
     */
    private void simulateMove(ChessMove move) {
        ChessPiece piece = this.getBoard().getPiece(move.getStartPosition());
        this.takenPiece = this.getBoard().getPiece(move.getEndPosition());
        // move piece to the new position
        this.getBoard().addPiece(move.getEndPosition(), piece);
        // make the piece's old position null
        this.getBoard().addPiece(move.getStartPosition(), null);
    }

    /**
     * Helper method to undo a simulated move
     *
     * @param move - the ChessMove to undo
     */
    private void undoMove(ChessMove move) {
        ChessPiece movedPiece = this.getBoard().getPiece(move.getEndPosition());
        // put the moved piece back to its start position
        this.getBoard().addPiece(move.getStartPosition(), movedPiece);
        // put the taken piece back where it was
        this.getBoard().addPiece(move.getEndPosition(), this.takenPiece);
    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        // first find the position of the current team's king
        ChessPosition kingPosition = null;
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(i, j));
                if (checkPiece != null && checkPiece.getTeamColor() == teamColor
                        && checkPiece.getPieceType() == ChessPiece.PieceType.KING) {
                    kingPosition = new ChessPosition(i, j);
                }
            }
        }
        // now find all the opponent pieces and call pieceMoves on them, collecting all opponent moves
        HashSet<ChessMove> opponentMoves;
        if(teamColor == TeamColor.WHITE) {
            opponentMoves = teamMoves(TeamColor.BLACK);
        } else {
            opponentMoves = teamMoves(TeamColor.WHITE);
        }

        // check if the king's position appears in any of them as an end position
        for (ChessMove move : opponentMoves) {
            if (move.getEndPosition().equals(kingPosition)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
//        // this team's king is in check, and none of the pieces have any valid moves
//        HashSet<ChessMove> allMoves = teamMoves(teamColor);
//        HashSet<ChessMove> allValidMoves = new HashSet<>();
//        for(ChessMove move : allMoves) {
//            allValidMoves.addAll(validMoves(move.getStartPosition()));
//        }

        return isInCheck(teamColor) && isInStalemate(teamColor);
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        HashSet<ChessMove> allMoves = teamMoves(teamColor);
        HashSet<ChessMove> allValidMoves = new HashSet<>();
        for(ChessMove move : allMoves) {
            allValidMoves.addAll(validMoves(move.getStartPosition()));
        }
        return allValidMoves.isEmpty();
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        this.board = board;
    }

    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {
        return this.board;
    }

    /**
     * Helper method that finds all the moves that a team can make, valid or not
     *
     * @param teamColor - the team whose moves to collect
     * @return - the total collection of all moves the team passed in can make
     */
    private HashSet<ChessMove> teamMoves(TeamColor teamColor) {
        HashSet<ChessMove> moves = new HashSet<>();
        for (int i = 1; i <= 8; i++) {
            for (int j = 1; j <= 8; j++) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(i, j));
                if (checkPiece != null && checkPiece.getTeamColor() == teamColor) {
                    moves.addAll(checkPiece.pieceMoves(this.board, new ChessPosition(i, j)));
                }
            }
        }
        return moves;
    }
}
