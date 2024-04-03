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

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public void draw() {
        ChessBoard board = this.game.getBoard();

        System.out.print(SET_BG_COLOR_BLACK);
        System.out.print(SET_TEXT_COLOR + "222m");
        // white on bottom
        // top row
        System.out.print(EMPTY);
        System.out.print(" a ");
        System.out.print(" b ");
        System.out.print(" c ");
        System.out.print(" d ");
        System.out.print(" e ");
        System.out.print(" f ");
        System.out.print(" g ");
        System.out.println(" h ");

        System.out.print(" 8 ");
        bgColor = SET_BG_COLOR + "222m";
        System.out.print(bgColor);
        textColor = SET_TEXT_COLOR_BLACK;
        System.out.print(textColor);
        for (int i = 1; i < 9; i++) {
            ChessPiece piece = board.getPiece(new ChessPosition(8, i));
            setTextColor(piece.getTeamColor());
            System.out.println(" " + "" + " ");
        }

        System.out.println();
    }

    private void switchBgColor() {
        if (bgColor.equals(SET_BG_COLOR_BLACK)) {
            bgColor = SET_BG_COLOR_WHITE;
        } else if (bgColor.equals(SET_BG_COLOR_WHITE)) {
            bgColor = SET_BG_COLOR_BLACK;
        }
    }

    private void setTextColor(ChessGame.TeamColor color) {
        if (color == ChessGame.TeamColor.WHITE) {
            textColor = SET_TEXT_COLOR_WHITE;
        } else if (color == ChessGame.TeamColor.BLACK) {
            textColor = SET_TEXT_COLOR_BLACK;
        }
    }
}
