package chess_ant.Drat_4_3;

import java.io.*;
import java.net.*;

public class Client {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;

    public Client() {
        try {
            socket = new Socket("localhost", 12345); // Kết nối đến server localhost trên cổng 12345
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            String[][] board = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\board.txt");
            out.writeObject(board);

            // Đọc lại board từ server và hiển thị
            String[][] receivedBoard = (String[][]) in.readObject();
            printReceivedBoard(receivedBoard);

            socket.close();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private String[][] readBoardFromFile(String filePath) {
        String[][] board = new String[8][8];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < 8; i++) {
                String line = reader.readLine();
                board[i] = line.split("");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }

    private void printReceivedBoard(String[][] board) {
        for (String[] row : board) {
            for (String cell : row) {
                System.out.print(cell);
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
