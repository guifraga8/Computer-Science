package main.java.com.tictactoe;

import main.java.com.quiz.Question;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TicTacToe {
    private Board board;
    private List<Question> questions;
    private GameState state;
    private Symbol currentPlayer;

    public TicTacToe() throws IOException {
        this.board = new Board();
        this.questions = new ArrayList<>();
        this.initQuestions();
        this.state = GameState.PLAYING;
        this.currentPlayer = Symbol.X;
    }

    public Board getBoard() {
        return this.board;
    }
    public List<Question> getQuestions() {
        return this.questions;
    }
    public GameState getState() {
        return this.state;
    }
    public Symbol getCurrentPlayer() {
        return this.currentPlayer;
    }
    public void setCurrentPlayer(Symbol symbol) {
        this.currentPlayer = symbol;
    }

    private void initQuestions() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("C:\\Users\\fveronezipeters\\www\\tic-tac-toe-multiplayer\\src\\main\\java\\com\\quiz\\questions.csv"));

        String line;
        while ((line = in.readLine()) != null) {
            String[] props = line.strip().split(",");

            Question question = new Question(props[0], Integer.parseInt(props[3]));
            question.addChoice(props[1]);
            question.addChoice(props[2]);
            this.questions.add(question);
        }
    }
    public boolean isWin(Symbol symbol) {
        if (this.board.getBoard()[0][0] != Symbol.EMPTY && this.board.getBoard()[0][0] == this.board.getBoard()[0][1] && this.board.getBoard()[0][1] == this.board.getBoard()[0][2])
            return true;
        else if (this.board.getBoard()[1][0] != Symbol.EMPTY && this.board.getBoard()[1][0] == this.board.getBoard()[1][1] && this.board.getBoard()[1][1] == this.board.getBoard()[1][2])
            return true;
        else if (this.board.getBoard()[2][0] != Symbol.EMPTY && this.board.getBoard()[2][0] == this.board.getBoard()[2][1] && this.board.getBoard()[2][1] == this.board.getBoard()[2][2])
            return true;
        else if (this.board.getBoard()[0][0] != Symbol.EMPTY && this.board.getBoard()[0][0] == this.board.getBoard()[1][0] && this.board.getBoard()[1][0] == this.board.getBoard()[2][0])
            return true;
        else if (this.board.getBoard()[0][1] != Symbol.EMPTY && this.board.getBoard()[0][1] == this.board.getBoard()[1][1] && this.board.getBoard()[1][1] == this.board.getBoard()[2][1])
            return true;
        else if (this.board.getBoard()[0][2] != Symbol.EMPTY && this.board.getBoard()[0][2] == this.board.getBoard()[1][2] && this.board.getBoard()[1][2] == this.board.getBoard()[2][2])
            return true;
        else if (this.board.getBoard()[0][0] != Symbol.EMPTY && this.board.getBoard()[0][0] == this.board.getBoard()[1][1] && this.board.getBoard()[1][1] == this.board.getBoard()[2][2])
            return true;
        else if (this.board.getBoard()[0][2] != Symbol.EMPTY && this.board.getBoard()[0][2] == this.board.getBoard()[1][1] && this.board.getBoard()[1][1] == this.board.getBoard()[2][0])
            return true;

        return false;
    }
}
