package chess_ant.Draft_04_03;
import java.io.*;
import java.net.*;
import java.nio.file.*;

public class ChessClient {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;
    private String[][] previousBoardState;

    public static void main(String[] args) {
        new ChessClient();
    }

    public ChessClient() {
        connectToServer();
        waitForBoardUpdate();
        waitForMessages();
    }

    private void connectToServer() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void waitForBoardUpdate() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    Thread.sleep(1000); // Kiểm tra sự thay đổi mỗi giây
                    String[][] currentBoard = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
                    if (isBoardStateChanged(currentBoard)) {
                        sendBoard(currentBoard);
                    }
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void waitForMessages() {
        Thread thread = new Thread(() -> {
            try {
                while (true) {
                    String message = input.readLine();
                    if (message != null) {
                        updateBoard(message);
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        thread.start();
    }

    private void sendBoard(String[][] board) {
        StringBuilder boardString = new StringBuilder();
        for (String[] row : board) {
            for (String cell : row) {
                boardString.append(cell);
            }
            boardString.append("\n");
        }
        output.println(boardString.toString());
    }

    private String[][] readBoardFromFile(String filePath) {
        String[][] board = new String[8][8];
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            for (int i = 0; i < 8; i++) {
                String line = reader.readLine();
                board[i] = line.split(",");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board;
    }

    private void updateBoard(String boardString) {
        try (PrintWriter writer = new PrintWriter(new FileWriter("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt"))) {
            String[] rows = boardString.split("\n");
            for (int i = 0; i < 8; i++) {
                writer.println(rows[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean isBoardStateChanged(String[][] currentBoard) {
        if (previousBoardState == null) {
            previousBoardState = currentBoard;
            return true;
        }

        for (int i = 0; i < currentBoard.length; i++) {
            for (int j = 0; j < currentBoard[i].length; j++) {
                if (!currentBoard[i][j].equals(previousBoardState[i][j])) {
                    previousBoardState = currentBoard;
                    return true;
                }
            }
        }
        return false;
    }
}

