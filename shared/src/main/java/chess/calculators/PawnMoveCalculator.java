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
            ChessPiece checkPiece = board.getPiece(new ChessPosition(whiteCheckRow(myRow), leftCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol(myCol)), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol(myCol)), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol(myCol)), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, leftCol(myCol)), ChessPiece.PieceType.KNIGHT));
            }
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(whiteCheckRow(myRow), rightCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol(myCol)), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol(myCol)), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol(myCol)), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, rightCol(myCol)), ChessPiece.PieceType.KNIGHT));
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
        if (whiteCheckRow(myRow) >= 1 && whiteCheckRow(myRow) <= 8) {
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
            ChessPiece checkPiece = board.getPiece(new ChessPosition(blackCheckRow(myRow), leftCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), leftCol(myCol)), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), leftCol(myCol)), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), leftCol(myCol)), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), leftCol(myCol)), ChessPiece.PieceType.KNIGHT));
            }
        }
        if (rightCol(myCol) >= 1 && rightCol(myCol) <= 8) {
            ChessPiece checkPiece = board.getPiece(new ChessPosition(blackCheckRow(myRow), rightCol(myCol)));
            if (checkPiece != null && checkPiece.getTeamColor() != myColor) {
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), rightCol(myCol)), ChessPiece.PieceType.QUEEN));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), rightCol(myCol)), ChessPiece.PieceType.ROOK));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), rightCol(myCol)), ChessPiece.PieceType.BISHOP));
                moves.add(new ChessMove(myPosition, new ChessPosition(blackCheckRow(myRow), rightCol(myCol)), ChessPiece.PieceType.KNIGHT));
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
        if (blackCheckRow(myRow) >= 1 && blackCheckRow(myRow) <= 8) {
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
    }

    private int whiteCheckRow(int row) {
        return row + 1;
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
