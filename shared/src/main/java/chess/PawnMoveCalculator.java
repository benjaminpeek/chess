package chess;

import java.util.Collection;
import java.util.HashSet;

public class PawnMoveCalculator extends PieceMoveCalculator {
    @Override
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        HashSet<ChessMove> moves = new HashSet<>();

        ChessPiece myPiece = board.getPiece(myPosition);
        int myRow = myPosition.getRow();
        int myCol = myPosition.getColumn();

        // REMEMBER CHESS NOTATION, 1-8 NOT 0-7!
        switch (myPiece.getTeamColor()) {
            case WHITE -> {
                if (myRow == 2) {
                    // check the two spaces directly in front of me
                    if (board.getPiece(new ChessPosition(myRow + 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), null));
                        if (board.getPiece(new ChessPosition(myRow + 2, myCol)) == null) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 2, myCol), null));
                        }
                    }
                    // check the diagonals as well, have to know if this is an edge piece
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    null));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    null));
                        }
                    // else, this piece is not on an edge but still on the front row
                    } else {
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    null));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    null));
                        }
                    }
                    // on second to last row ready to promote to a new piece on next move
                } else if (myRow == 7) {
                    if (board.getPiece(new ChessPosition(myRow + 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), ChessPiece.PieceType.KNIGHT));
                    }
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                        // this piece is not on an edge but still on the second-to-last row
                    } else {
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                    }
                    // piece is not on the front or second to last row, in middle of board
                } else {
                    if (board.getPiece(new ChessPosition(myRow + 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol), null));
                    }
                    // still might be an edge piece, though
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    null));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    null));
                        }
                    } else {
                        // check both left and right
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow + 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow + 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol - 1),
                                    null));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow + 1, myCol + 1),
                                    null));
                        }
                    }
                }
            }
            case BLACK -> {
                // piece is on front row for black
                if (myRow == 7) {
                    // check the two spaces directly in front of this piece
                    if (board.getPiece(new ChessPosition(myRow - 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), null));
                        if (board.getPiece(new ChessPosition(myRow - 2, myCol)) == null) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 2, myCol), null));
                        }
                    }
                    // check the diagonals as well, have to know if this is an edge piece
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    null));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    null));
                        }
                        // else, this piece is not on an edge but still on the front row
                    } else {
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    null));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    null));
                        }
                    }
                  // about to promote as black, moving into row 1
                } else if (myRow == 2) {
                    if (board.getPiece(new ChessPosition(myRow - 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.QUEEN));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.ROOK));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.BISHOP));
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), ChessPiece.PieceType.KNIGHT));
                    }
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                        // this piece is not on an edge but still on the second-to-last row
                    } else {
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.QUEEN));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.ROOK));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.BISHOP));
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    ChessPiece.PieceType.KNIGHT));
                        }
                    }
                } else {
                    if (board.getPiece(new ChessPosition(myRow - 1, myCol)) == null) {
                        moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol), null));
                    }
                    // still might be an edge piece, though
                    if (myCol == 1) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    null));
                        }
                    } else if (myCol == 8) {
                        ChessPiece checkPiece = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        if (checkPiece != null && checkPiece.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    null));
                        }
                    } else {
                        // check both left and right
                        ChessPiece checkPieceLeft = board.getPiece(new ChessPosition(myRow - 1, myCol - 1));
                        ChessPiece checkPieceRight = board.getPiece(new ChessPosition(myRow - 1, myCol + 1));

                        if (checkPieceLeft != null && checkPieceLeft.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol - 1),
                                    null));
                        }
                        if (checkPieceRight != null && checkPieceRight.getTeamColor() != myPiece.getTeamColor()) {
                            moves.add(new ChessMove(myPosition, new ChessPosition(myRow - 1, myCol + 1),
                                    null));
                        }
                    }
                }
            }
        }


        return moves;
    }
}
