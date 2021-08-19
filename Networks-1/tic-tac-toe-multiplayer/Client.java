package main.java.com.tictactoe;

import main.java.com.server.Server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    public static void main(String[] args) throws IOException, ClassNotFoundException {

        Scanner scanner = new Scanner(System.in);

        System.out.println("Establishing connection...");
        Socket socket = new Socket(Server.HOST, Server.PORT);
        System.out.println("Connection established!");

        ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        ObjectInputStream in = new ObjectInputStream(socket.getInputStream());

        System.out.println(in.readObject());
        String option = scanner.nextLine();
        out.writeObject(option);
        out.flush();
        String endServer = (String) in.readObject();
        if (endServer.equals("fim")) {
            out.close();
            in.close();
            socket.close();
            System.exit(0);
        }
        do {
            System.out.println(in.readObject());
            System.out.println(in.readObject());
            System.out.println(in.readObject());
            System.out.println(in.readObject());

            String ans = scanner.nextLine();
            out.writeUTF(ans);
            out.flush();

            String s = (String) in.readObject();
            if (s.equals("correct")) {
                System.out.println(in.readObject());
                System.out.println(in.readObject());
                String[] position = scanner.nextLine().strip().split(" ");
                out.writeObject(position[0] + "" + position[1]);
                String ok = (String) in.readObject();
                System.out.println(ok);
                if (!ok.contains("Invalid")) {
                    System.out.println(ok);
                } else {
                    position = scanner.nextLine().strip().split(" ");
                    out.writeObject(position[0] + "" + position[1]);
                }
            } else {
                System.out.println(in.readObject());
            }
            String end = (String) in.readObject();
            if (end.equals("Game has ended! Congratulations to X") || end.equals("Game has ended! Congratulations to O")) {
                System.out.println(end);
                break;
            } else {
                System.out.println(end);
            }
        } while (true);
    }

}
