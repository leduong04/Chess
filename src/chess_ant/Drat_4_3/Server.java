package chess_ant.Drat_4_3;

import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket[] clients;
    private int currentPlayer = 0;

    public Server(){
        try{
            serverSocket = new ServerSocket(12345);
            clients = new Socket[2];

            for (int i = 0; i < 2; i++) {
                clients[i] = serverSocket.accept();
                System.out.println("Player " + (i + 1) + " connected.");
            }

            PrintWriter[] outs = new PrintWriter[2];
            BufferedReader[] ins = new BufferedReader[2];

            for (int i = 0; i < 2; i++) {
                outs[i] = new PrintWriter(clients[i].getOutputStream(), true);
                ins[i] = new BufferedReader(new InputStreamReader(clients[i].getInputStream()));
            }

            outs[0].println("Player 1");
            outs[1].println("Player 2");

            while (true) {
                String message = ins[currentPlayer].readLine();
                if(message.equals("-1,-1,-1,-1"))
                {
                    break;
                }
                if(message!=null)
                {
                    // int fromRow = Integer.parseInt(message.split(",")[0]);
                    // int fromCol = Integer.parseInt(message.split(",")[0]);
                    // int toRow = Integer.parseInt(message.split(",")[0]);
                    // int toCol = Integer.parseInt(message.split(",")[0]);
                    outs[currentPlayer].println(message);
                    currentPlayer = 1 - currentPlayer;

                }
            }
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace(); // Xử lý ngoại lệ IOException
        }
    }

    public static void main(String[] args) {
        new Server(); // Khởi chạy server
    }
}
