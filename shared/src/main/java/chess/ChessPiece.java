package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {

        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        ROOK,
        BISHOP,
        KNIGHT,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return this.pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return this.type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        // switch on type of piece we are, if bishop: do it in place here or call a helper method for bishop moves, or
        // helper from a different class, different methods for all the pieces
        switch (this.type) {
            case KING -> {
                System.out.println("king");
            }
            case QUEEN -> {
                System.out.println("queen");
            }
            case ROOK -> {
                System.out.println("rook");
            }
            case BISHOP -> {
                System.out.println("bishop");
            }
            case KNIGHT -> {
                System.out.println("knight");
            }
            case PAWN -> {
                System.out.println("pawn");
            }
        }

        return new ArrayList<>();
    }

    @Override
    public boolean equals(Object otherObj) {
        if (otherObj == null) {
            return false;
        }
        if (otherObj == this) {
            return true;
        }
        if (otherObj.getClass() != this.getClass()) {
            return false;
        }

        ChessPiece otherPiece = (ChessPiece)otherObj;
        return (otherPiece.pieceColor == this.pieceColor) && (otherPiece.type == this.type);
    }

    @Override
    public String toString() {
        switch (this.type) {
            case KING -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "K";}
                    case BLACK -> {return "k";}
                }
            }
            case QUEEN -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "Q";}
                    case BLACK -> {return "q";}
                }
            }
            case ROOK -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "R";}
                    case BLACK -> {return "r";}
                }
            }
            case BISHOP -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "B";}
                    case BLACK -> {return "b";}
                }
            }
            case KNIGHT -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "N";}
                    case BLACK -> {return "n";}
                }
            }
            case PAWN -> {
                switch (this.pieceColor) {
                    case WHITE -> {return "P";}
                    case BLACK -> {return "p";}
                }
            }
        }

        return "no_piece_found";
    }
}
