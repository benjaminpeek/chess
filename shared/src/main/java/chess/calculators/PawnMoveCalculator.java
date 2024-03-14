package chess.calculators;

import chess.*;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();
        ChessPiece myPiece = board.getPiece(myPosition);
        ChessGame.TeamColor myColor = myPiece.getTeamColor();

        // a pawn is either white or black,
            // has or has not moved yet (check based on color and row)
            // is or is not about to promote (check based on color and row again)
            // pawns can take diagonally, so check the 2 spaces in front of the pawn every time it moves...
                // adding diagonal spaces only if there is a piece of a different color on it

        switch (myColor) {
            case WHITE -> {
                // has not moved yet, check the two spaces in front of it and the two diagonal spaces.
                if (myRow == 2) {
                    whiteUnmovedPawnMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
                // else if piece is about to promote
                else if (myRow == 7) {
                    whitePawnPromotionMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
                // piece is not at the beginning, or at the end, no promotion
                else {
                    whiteMiddlePawnMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
            }
            case BLACK -> {
                // has not moved yet, check the two spaces in front of it and the two diagonal spaces.
                if (myRow == 7) {
                    blackUnmovedPawnMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
                // else if piece is about to promote
                else if (myRow == 2) {
                    blackPawnPromotionMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
                // piece is not at the beginning, or at the end, no promotion
                else {
                    blackMiddlePawnMoves(board, myPosition, moves, myRow, myCol, myColor);
                }
            }
        }

        return moves;
    }

    private void whiteUnmovedPawnMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves,
                                  int myRow, int myCol, ChessGame.TeamColor myColor) {
        // 1 and 2 spaces ahead, no promotion
        if (board.getPiece(new ChessPosition(myRow + 1, myCol)) == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), null));
            if (board.getPiece(new ChessPosition(myRow + 2, myCol)) == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 2, myCol), null));
            }
        }
        // diagonal spaces, no promotion
        int checkRow = myRow + 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (leftCol >= 1 && leftCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), null));
            }
        }
        if (rightCol >= 1 && rightCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), null));
            }
        }
    }

    private void whitePawnPromotionMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                         int myCol, ChessGame.TeamColor myColor) {
        // straight ahead one space, yes promotion
        if (board.getPiece(new ChessPosition(myRow + 1, myCol)) == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.KNIGHT));
        }
        // diagonal spaces, yes promotion
        int checkRow = myRow + 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (leftCol >= 1 && leftCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol), ChessPiece.PieceType.KNIGHT));
            }
        }
        if (rightCol >= 1 && rightCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol), ChessPiece.PieceType.KNIGHT));
            }
        }
    }

    private void whiteMiddlePawnMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                      int myCol, ChessGame.TeamColor myColor) {
        // 1 space ahead, check it exists
        if (myRow + 1 >= 1 && myRow + 1 <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), null));
            }
        }
        int checkRow = myRow + 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (checkRow >= 1 && checkRow <= 8) {
            if (leftCol >= 1 && leftCol <= 8) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
                if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), null));
                }
            }
            if (rightCol >= 1 && rightCol <= 8) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
                if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), null));
                }
            }
        }
    }

    private void blackUnmovedPawnMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                       int myCol, ChessGame.TeamColor myColor) {
        // 1 and 2 spaces ahead, no promotion
        if (board.getPiece(new ChessPosition(myRow - 1, myCol)) == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), null));
            if (board.getPiece(new ChessPosition(myRow - 2, myCol)) == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 2, myCol), null));
            }
        }
        // diagonal spaces, no promotion
        int checkRow = myRow - 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (leftCol >= 1 && leftCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), null));
            }
        }
        if (rightCol >= 1 && rightCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), null));
            }
        }
    }

    private void blackPawnPromotionMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                         int myCol, ChessGame.TeamColor myColor) {
        if (board.getPiece(new ChessPosition(myRow - 1, myCol)) == null) {
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.KNIGHT));
        }
        // diagonal spaces, yes promotion
        int checkRow = myRow - 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (leftCol >= 1 && leftCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), ChessPiece.PieceType.KNIGHT));
            }
        }
        if (rightCol >= 1 && rightCol <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), ChessPiece.PieceType.KNIGHT));
            }
        }
    }

    private void blackMiddlePawnMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                      int myCol, ChessGame.TeamColor myColor) {
        if (myRow - 1 >= 1 && myRow + 1 <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol));
            if (checkPiece == null) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), null));
            }
        }
        int checkRow = myRow - 1;
        int leftCol = myCol - 1;
        int rightCol = myCol + 1;
        if (checkRow >= 1 && checkRow <= 8) {
            if (leftCol >= 1 && leftCol <= 8) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, leftCol));
                if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, leftCol), null));
                }
            }
            if (rightCol >= 1 && rightCol <= 8) {
                ChessPiece checkPiece = board.getPiece(new ChessPosition(checkRow, rightCol));
                if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                    moves.add(new ChessMove(myPosition, new ChessPosition(checkRow, rightCol), null));
                }
            }
        }
    }
}
