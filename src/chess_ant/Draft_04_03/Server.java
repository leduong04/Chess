package chess_ant.Draft_04_03;
import java.awt.*;
import java.io.*;
import java.net.*;

public class Server {
    private ServerSocket serverSocket;
    private Socket[] clients;
    private int currentPlayer = 0;
    private char[] marks = {'X', 'O'};
    private char[][] board = new char[3][3];
    private boolean gameOver = false;

    public Server() {
        try {
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

            while (!gameOver) {
                String message = ins[currentPlayer].readLine();
                if (message != null) {
                    int row = Integer.parseInt(message.split(",")[0]);
                    int col = Integer.parseInt(message.split(",")[1]);

                    if (board[row][col] == 0) {
                        board[row][col] = marks[currentPlayer];
                        if (checkWin()) {
                            outs[currentPlayer].println("win");
                            outs[1 - currentPlayer].println("lose");
                            gameOver = true;
                        } else if (checkDraw()) {
                            outs[0].println("draw");
                            outs[1].println("draw");
                            gameOver = true;
                        } else {
                            outs[currentPlayer].println("valid," + row + "," + col);
                            outs[1 - currentPlayer].println("opponent," + row + "," + col);
                            currentPlayer = 1 - currentPlayer;
                        }
                    } else {
                        outs[currentPlayer].println("invalid");
                    }
                }
            }

            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean checkWin() {
        for (int i = 0; i < 3; i++) {
            if (board[i][0] == board[i][1] && board[i][1] == board[i][2] && board[i][0] != 0) {
                return true;
            }
            if (board[0][i] == board[1][i] && board[1][i] == board[2][i] && board[0][i] != 0) {
                return true;
            }
        }
        if (board[0][0] == board[1][1] && board[1][1] == board[2][2] && board[0][0] != 0) {
            return true;
        }
        return board[0][2] == board[1][1] && board[1][1] == board[2][0] && board[0][2] != 0;
    }

    private boolean checkDraw() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (board[i][j] == 0) {
                    return false;
                }
            }
        }
        return true;
    }

    public static void main(String[] args) {
        new Server();
    }
}
