package chess;

import java.util.Collection;
import java.util.Objects;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;


    public ChessPiece(ChessGame.TeamColor pieceColor, PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
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
        PieceMoveCalculator moveCalculator = null;
        switch (this.type) {
            case KING -> {
                moveCalculator = new KingMoveCalculator();
            }
            case KNIGHT -> {
                moveCalculator = new KnightMoveCalculator();
            }
            case BISHOP -> {
                moveCalculator = new BishopMoveCalculator();
            }
            case ROOK -> {
                moveCalculator = new RookMoveCalculator();
            }
            case QUEEN -> {
                moveCalculator = new QueenMoveCalculator();
            }
            case PAWN -> {
                moveCalculator = new PawnMoveCalculator();
            }
        }

        assert moveCalculator != null;
        return moveCalculator.pieceMoves(board, myPosition);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ChessPiece that = (ChessPiece) o;
        return this.pieceColor == that.pieceColor && this.type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

    @Override
    public String toString() {
        String str = "";
        switch (this.type) {
            case KING -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "k";
                    }
                    case WHITE -> {
                        str = "K";
                    }
                }
            }
            case PAWN -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "p";
                    }
                    case WHITE -> {
                        str = "P";
                    }
                }
            }
            case ROOK -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "r";
                    }
                    case WHITE -> {
                        str = "R";
                    }
                }
            }
            case QUEEN -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "q";
                    }
                    case WHITE -> {
                        str = "Q";
                    }
                }
            }
            case BISHOP -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "b";
                    }
                    case WHITE -> {
                        str = "B";
                    }
                }
            }
            case KNIGHT -> {
                switch (this.pieceColor) {
                    case BLACK -> {
                        str = "n";
                    }
                    case WHITE -> {
                        str = "N";
                    }
                }
            }
        }
        return str;
    }
}
