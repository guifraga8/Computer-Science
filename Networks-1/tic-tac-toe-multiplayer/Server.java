package main.java.com.server;

import main.java.com.quiz.Question;
import main.java.com.tictactoe.GameState;
import main.java.com.tictactoe.Symbol;
import main.java.com.tictactoe.TicTacToe;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    public static final String HOST = "127.0.0.1";
    public static final int PORT = 6789;

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        /* Creates the server */
        ServerSocket server = new ServerSocket(Server.PORT);
        System.out.println("Connection established on PORT " + Server.PORT);

        do {
            System.out.println("Awaiting connection...");
            Socket socket = server.accept();

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

            /* Asks if they want to play or not */
            out.writeObject("Do you wanna start the game? 0 => Yes or 1 => No: ");
            out.flush();
            String end = (String) in.readObject();
            if (end.equals("1")) {
                out.writeObject("fim");
                out.flush();
                System.out.println("Closing server...");
                out.close();
                in.close();
                socket.close();
                server.close();
                break;
            } else {
                out.writeObject("");
            }

            TicTacToe game = new TicTacToe();

            while (game.getState().equals(GameState.PLAYING)) {
                out.writeObject("\n" + game.getBoard().show());
                out.flush();

                int index = (int) (Math.random() * game.getQuestions().size());
                Question question = game.getQuestions().get(index);

                out.writeObject(game.getCurrentPlayer() + "'s turn...");
                out.flush();

                out.writeObject(question.getStatement() + " A) " + question.getChoices().get(0) + " B) " + question.getChoices().get(1));
                out.flush();

                out.writeObject("What's the answer (0 => A, 1 => B): ");
                out.flush();
                int answer = Integer.parseInt(in.readUTF());

                if (answer == question.getAnswer()) {
                    out.writeObject("correct");
                    out.writeObject("Congratulations to " + game.getCurrentPlayer());
                    out.writeObject("Enter the position: ");
                    String position = (String) in.readObject();
                    int x = Integer.parseInt(String.valueOf(position.charAt(0)));
                    int y = Integer.parseInt(String.valueOf(position.charAt(1)));
                    out.flush();
                    if (game.getBoard().makeMove(x, y, game.getCurrentPlayer())) {
                        out.writeObject("Position (" + x + ", " + y + ") set...");
                    } else {
                        out.writeObject("Invalid position! Last chance to get it right... Enter the position: ");
                        out.flush();
                        position = (String) in.readObject();
                        x = Integer.parseInt(String.valueOf(position.charAt(0)));
                        y = Integer.parseInt(String.valueOf(position.charAt(1)));
                        game.getBoard().makeMove(x, y, game.getCurrentPlayer());
                    }
                } else {
                    out.writeObject("wrong");
                    out.writeObject("Almost there...");
                }
                out.flush();

                if (game.isWin(game.getCurrentPlayer())) {
                    out.writeObject("Game has ended! Congratulations to " + game.getCurrentPlayer());
                    out.flush();
                    break;
                } else {
                    out.writeObject("Nobody has won yet! Keep playing...");
                }

                if (game.getCurrentPlayer().equals(Symbol.X)) {
                    game.setCurrentPlayer(Symbol.O);
                } else {
                    game.setCurrentPlayer(Symbol.X);
                }
            }
        } while (true);

        server.close();
    }
}
