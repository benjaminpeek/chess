package visual;

import chess.ChessBoard;
import chess.ChessGame;
import chess.ChessPiece;
import chess.ChessPosition;

import static visual.EscapeSequences.*;

public class DrawBoard {
    ChessGame game;
    String bgColor;
    String textColor;
    ChessGame.TeamColor povColor;

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public void drawWhite() {
        povColor = ChessGame.TeamColor.WHITE;
        ChessBoard board = this.game.getBoard();

        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR + "222m");
        // header letters
        System.out.print(EMPTY);
        System.out.print(" a ");
        System.out.print(" b ");
        System.out.print(" c ");
        System.out.print(" d ");
        System.out.print(" e ");
        System.out.print(" f ");
        System.out.print(" g ");
        System.out.print(" h ");
        System.out.print(EMPTY);
        System.out.print(RESET_BG_COLOR);
        System.out.println();

        // s
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(" 8 ");
        bgColor = SET_BG_COLOR + "222m";
        System.out.print(bgColor);
        for (int i = 1; i < 9; i++) {
            ChessPiece piece = board.getPiece(new ChessPosition(8, i));
            setTextColor(piece.getTeamColor());
            System.out.print(" " + piece + " ");
            switchBgColor();
        }
        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR + "222m");
        System.out.print(" 8 ");
        System.out.print(RESET_BG_COLOR);

        System.out.println();
    }

    public void drawBlack() {
        povColor = ChessGame.TeamColor.BLACK;
    }

    private void drawHeaders() {

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
}
