package chess_ant.Drat_4_3;


import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket[] clients;
    private int currentPlayer = 0;

    public Server() {
        try {
            serverSocket = new ServerSocket(12345);
            clients = new Socket[2];

            for (int i = 0; i < 2; i++) {
                clients[i] = serverSocket.accept();
                System.out.println("Player " + (i + 1) + " connected.");
            }

            ObjectOutputStream[] outs = new ObjectOutputStream[2];
            ObjectInputStream[] ins = new ObjectInputStream[2];

            for (int i = 0; i < 2; i++) {
                outs[i] = new ObjectOutputStream(clients[i].getOutputStream());
                ins[i] = new ObjectInputStream(clients[i].getInputStream());
            }

            // outs[0].writeObject("Player 1");
            // outs[1].writeObject("Player 2");

            while (true) {
                String[][] board = (String[][]) ins[currentPlayer].readObject();
                if (board == null) {
                    break;
                }

                outs[currentPlayer].writeObject(board);
                currentPlayer = 1 - currentPlayer;
            }
            serverSocket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new Server();
    }
}
