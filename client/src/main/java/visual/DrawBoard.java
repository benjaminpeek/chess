package visual;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;
import chess.ChessMove;

import java.util.Collection;

import static visual.EscapeSequences.*;

public class DrawBoard {
    ChessGame game;
    String bgColor;
    String textColor;
    ChessGame.TeamColor povColor;

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public void drawWhite(Collection<ChessMove> highlights) {
        povColor = ChessGame.TeamColor.WHITE;

        // header letters
        drawLetters();

        System.out.print(RESET_BG_COLOR);
        System.out.println();
        // draw the rows
        for (int i = 8; i > 0; i--) {
            drawRow(this.game.getBoard(), i, highlights);
        }
        // footer letters
        drawLetters();

        System.out.print(RESET_BG_COLOR);
        System.out.println();

        System.out.println();
    }

    public void drawBlack(Collection<ChessMove> highlights) {
        povColor = ChessGame.TeamColor.BLACK;

        // header letters
        drawLetters();

        System.out.print(RESET_BG_COLOR);
        System.out.println();
        // draw the rows
        for (int i = 1; i < 9; i++) {
            drawRow(this.game.getBoard(), i, highlights);
        }
        // footer letters
        drawLetters();

        System.out.print(RESET_BG_COLOR);
        System.out.println();

        System.out.println();
    }

    private void drawLetters() {
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR + "222m");
        System.out.print(EMPTY);
        switch (povColor) {
            case WHITE -> {
                System.out.print(" a ");
                System.out.print(" b ");
                System.out.print(" c ");
                System.out.print(" d ");
                System.out.print(" e ");
                System.out.print(" f ");
                System.out.print(" g ");
                System.out.print(" h ");
            }
            case BLACK -> {
                System.out.print(" h ");
                System.out.print(" g ");
                System.out.print(" f ");
                System.out.print(" e ");
                System.out.print(" d ");
                System.out.print(" c ");
                System.out.print(" b ");
                System.out.print(" a ");
            }
        }
        System.out.print(EMPTY);
    }

    private void drawRow(ChessBoard board, int row, Collection<ChessMove> highlights) {
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" " + row + " ");

        if (row % 2 == 0) {bgColor = SET_BG_COLOR + "222m";}
        else {bgColor = SET_BG_COLOR + "95m";}
        System.out.print(bgColor);

        switch (povColor) {
            case WHITE -> {
                for (int i = 1; i < 9; i++) {
                    if (highlights != null) {
                        for (ChessMove move : highlights) {
                            if (move.getStartPosition().getRow() == row && move.getStartPosition().getColumn() == i) {
                                System.out.print(SET_BG_COLOR_BLUE);
                            }
                            if (move.getEndPosition().getRow() == row && move.getEndPosition().getColumn() == i) {
                                if ((row % 2 == 0 && i % 2 != 0) || (row % 2 != 0 && i % 2 == 0)) {
                                    System.out.print(SET_BG_COLOR_GREEN);
                                } else {
                                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                                }
                            }
                        }
                    }
                    drawSquare(board, row, i);
                    System.out.print(bgColor);
                }
            }
            case BLACK -> {
                for (int i = 8; i > 0; i--) {
                    if (highlights != null) {
                        for (ChessMove move : highlights) {
                            if (move.getStartPosition().getRow() == row && move.getStartPosition().getColumn() == i) {
                                System.out.print(SET_BG_COLOR_BLUE);
                            }
                            if (move.getEndPosition().getRow() == row && move.getEndPosition().getColumn() == i) {
                                if (row % 2 == 0) {
                                    System.out.print(SET_BG_COLOR_GREEN);
                                } else {
                                    System.out.print(SET_BG_COLOR_DARK_GREEN);
                                }
                            }
                        }
                    }
                    drawSquare(board, row, i);
                    System.out.print(bgColor);
                }
            }
        }

        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR + "222m");
        System.out.print(" " + row + " ");
        System.out.print(RESET_BG_COLOR);

        System.out.println();
    }

    private void drawSquare(ChessBoard board, int row, int col) {
        ChessPiece piece = board.getPiece(new ChessPosition(row, col));
        if (piece != null) {
            setTextColor(piece.getTeamColor());
            System.out.print(" " + piece.toString().toUpperCase() + " ");
        } else {
            System.out.print(EMPTY);
        }
        switchBgColor();
    }

    private void switchBgColor() {
        switch(bgColor) {
            case SET_BG_COLOR + "222m" -> bgColor = SET_BG_COLOR + "95m";
            case SET_BG_COLOR + "95m" -> bgColor = SET_BG_COLOR + "222m";
        }
        System.out.print(bgColor);
    }

    private void setTextColor(ChessGame.TeamColor color) {
        switch (color) {
            case ChessGame.TeamColor.WHITE -> textColor = SET_TEXT_COLOR_WHITE;
            case ChessGame.TeamColor.BLACK -> textColor = SET_TEXT_COLOR_BLACK;
        }
        System.out.print(textColor);
    }

    public void setGame(ChessGame game) {
        this.game = game;
    }

    public ChessGame getGame() {
        return this.game;
    }

    public void highlightMoves(ChessPosition piecePosition) {
        Collection<ChessMove> moves = this.game.validMoves(piecePosition);
        switch (povColor) {
            case WHITE -> drawWhite(moves);
            case BLACK -> drawBlack(moves);
        }
    }

}
