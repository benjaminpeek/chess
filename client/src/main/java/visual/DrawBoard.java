package visual;

import chess.ChessBoard;
import chess.ChessGame;

public class DrawBoard {
    ChessGame game;

    public DrawBoard(ChessGame game) {
        this.game = game;
    }

    public StringBuilder draw() {
        ChessBoard board = this.game.getBoard();

        // white on bottom

        // black on bottom

        return new StringBuilder();
    }
}
