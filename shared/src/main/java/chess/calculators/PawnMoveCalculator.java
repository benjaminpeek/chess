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
        whiteDiagonalMiddleMoves(board, myPosition, moves, myRow, myCol, myColor);
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
        if (leftCol(myCol) >= 1 && leftCol(myCol) <= 8) {
            whiteDiagonalPromoMoves(board, myPosition, moves, myRow, leftCol(myCol), myColor);
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            whiteDiagonalPromoMoves(board, myPosition, moves, myRow, rightCol(myCol), myColor);
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
        // diagonal moves no promotion
        if (whiteCheckRow(myRow) >= 1 && whiteCheckRow(myRow) <= 8) {
            whiteDiagonalMiddleMoves(board, myPosition, moves, myRow, myCol, myColor);
        }
    }

    private void whiteDiagonalMiddleMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                          int myCol, ChessGame.TeamColor myColor) {
        if (leftCol(myCol) >= 1 && leftCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(whiteCheckRow(myRow), leftCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(whiteCheckRow(myRow), leftCol(myCol)), null));
            }
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(whiteCheckRow(myRow), rightCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(whiteCheckRow(myRow), rightCol(myCol)), null));
            }
        }
    }

    private void whiteDiagonalPromoMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                         int checkCol, ChessGame.TeamColor myColor) {
        ChessPiece checkPiece = board.getPiece(new ChessPosition(whiteCheckRow(myRow), checkCol));
        if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, checkCol), ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, checkCol), ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, checkCol), ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, checkCol), ChessPiece.PieceType.KNIGHT));
        }
    }

    private int whiteCheckRow(int row) {
        return row + 1;
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
        blackDiagonalMiddleMoves(board, myPosition, moves, myRow, myCol, myColor);
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
        if (leftCol(myCol) >= 1 && leftCol(myCol) <= 8) {
            blackDiagonalPromoMoves(board, myPosition, moves, myRow, leftCol(myCol), myColor);
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            blackDiagonalPromoMoves(board, myPosition, moves, myRow, rightCol(myCol), myColor);
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
        if (blackCheckRow(myRow) >= 1 && blackCheckRow(myRow) <= 8) {
            blackDiagonalMiddleMoves(board, myPosition, moves, myRow, myCol, myColor);
        }
    }

    private void blackDiagonalMiddleMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                          int myCol, ChessGame.TeamColor myColor) {
        if (leftCol(myCol) >= 1 && leftCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(blackCheckRow(myRow), leftCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), leftCol(myCol)), null));
            }
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(blackCheckRow(myRow), rightCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), rightCol(myCol)), null));
            }
        }
    }

    private void blackDiagonalPromoMoves(ChessBoard board, ChessPosition myPosition, HashSet<ChessMove> moves, int myRow,
                                         int checkCol, ChessGame.TeamColor myColor) {
        ChessPiece checkPiece = board.getPiece(new ChessPosition(blackCheckRow(myRow), checkCol));
        if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
            moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), checkCol), ChessPiece.PieceType.QUEEN));
            moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), checkCol), ChessPiece.PieceType.ROOK));
            moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), checkCol), ChessPiece.PieceType.BISHOP));
            moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), checkCol), ChessPiece.PieceType.KNIGHT));
        }
    }

    private int blackCheckRow(int row) {
        return row - 1;
    }


    private int leftCol(int col) {
        return col - 1;
    }

    private int rightCol(int col) {
        return col + 1;
    }
}
