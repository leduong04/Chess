package chess_ant.Draft_04_03;
import java.io.*;
import java.net.*;

import chess_ant.WriteBoardToFile;



public class client2 {
    private Socket socket;
    private ObjectInputStream in;
    private ObjectOutputStream out;
    private static String[][] previousBoardState;

    public client2() {
        try {
            socket = new Socket("localhost", 12345); // Kết nối đến server localhost trên cổng 12345
            out = new ObjectOutputStream(socket.getOutputStream());
            in = new ObjectInputStream(socket.getInputStream());

            String[][] board = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\board.txt");
            out.writeObject(board);

            // Đọc lại board từ server và hiển thị
            String[][] receivedBoard = (String[][]) in.readObject();
            
            WriteBoardToFile(receivedBoard);
            
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

    public static void WriteBoardToFile(String[][] board) {

        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if(board[i][j].equals("| |"))
                {
                    board[i][j]=" ";
                }
            }
        }



        // if(whoWon.whoWon(ReadBoardFromFile.ReadBoardFromFile())==0)
        if(0==0)
        {
            try {
                FileWriter writer = new FileWriter("src\\chess_ant\\Drat_4_3\\board2.txt");
                for (int i = 0; i < 8; i++) {
                    for (int j = 0; j < 8; j++) {
                        writer.write(board[i][j]);
                    }
                    writer.write("\n");
                }
                writer.close();
            } catch (IOException e) {
                System.err.println("Lỗi khi ghi vào tệp board.txt: " + e.getMessage());
            }
    
            for(int i=0; i<8; i++)
            {
                for(int j=0; j<8; j++)
                {
                    if(board[i][j].equals(" "))
                    {
                        board[i][j]="| |";
                    }
                }
            }
        }
    }

    private static boolean isBoardStateChanged(String[][] currentBoardState) {
        if (previousBoardState == null || previousBoardState.length != currentBoardState.length) {
            previousBoardState = currentBoardState;
            return true;
        }

        for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[i].length; j++) {
                if (!currentBoardState[i][j].equals(previousBoardState[i][j])) {
                    previousBoardState = currentBoardState;
                    return true;
                }
            }
        }
        return false;
    }
    public static void main(String[] args) {
        new client2();
    }
}
