package visual;

import chess.ChessBoard;
import chess.ChessGame;

import static visual.EscapeSequences.*;

public class DrawBoard {
    ChessGame game;

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public void draw() {
        ChessBoard board = this.game.getBoard();

        System.out.println(SET_BG_COLOR_BLACK);
        System.out.println(SET_TEXT_COLOR + "222m");
        // white on bottom
        for (int i = 1; i < 9; i++) {
            System.out.println("yeah this lit");
        }

        // black on bottom

    }
}
