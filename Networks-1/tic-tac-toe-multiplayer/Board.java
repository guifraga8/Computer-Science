package main.java.com.tictactoe;

public class Board {
    private Symbol[][] board;

    public Board() {
        this.initBoard();
    }

    public Symbol[][] getBoard() {
        return this.board;
    }

    private void initBoard() {
        this.board = new Symbol[3][3];
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 3; col ++) {
                this.board[row][col] = Symbol.EMPTY;
            }
        }
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        for (int row = 0; row < 3; row ++) {
            for (int col = 0; col < 3; col ++) {
                if (this.board[row][col].equals(Symbol.EMPTY)) {
                    sb.append("|   ");
                } else {
                    sb.append("| " + this.board[row][col] + " ");
                }
            }
            sb.append("|\n");
        }
        return sb.toString();
    }

    public boolean makeMove(int x, int y, Symbol symbol) {
        if (x >= 0 && x <= 3 && y >= 0 && y <= 3) {
            this.board[x][y] = symbol;
            return true;
        }
        return false;
    }
}
