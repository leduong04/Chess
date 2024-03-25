package chess_ant;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Scanner;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.ObjectInputStream;

public class Player2P extends JFrame {
    private JButton[][] chessCells = new JButton[8][8];
    private String[][] boardState = new String[8][8];
    private int fromRow = -1, fromCol = -1;

    private static Socket socket;
    private static ObjectOutputStream outputStream;

    private static boolean done = false;
    private static boolean turn = true;
    private static int side;
    private static String boardLink = "src\\chess_ant\\board.txt";
    private static final String URL = "jdbc:mysql://localhost:3306/projectjava";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public Player2P() {
        setTitle("Real-time Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeChessCells();
        updateBoardFromFile();

        connectToServer();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private static void connectToServer() {
        try {
            socket = new Socket("localhost", 12345);
            outputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendBoard() {
        try {
            outputStream.writeObject(concatenateBoard(ReadBoardToSend()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String[][] ReadBoardToSend() {
        String[][] board = new String[8][8];

        try {
            Scanner scanner = new Scanner(new File(boardLink));

            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] chars = line.split("");
                    for (int j = 0; j < 8; j++) {
                        if (chars[j].equals(" ")) {
                            board[i][j] = ".";
                        } else {
                            board[i][j] = chars[j];
                        }
                    }
                }
            }

            scanner.close();

            // whoWon.displayWinner(board);

        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy tệp board.txt.");
        }

        return board;
    }

    public static String[][] ReadBoardFromFile() {
        String[][] board = new String[8][8];

        try {
            Scanner scanner = new Scanner(new File(boardLink));

            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] chars = line.split("");
                    for (int j = 0; j < 8; j++) {
                        if (chars[j].equals(" ")) {
                            board[i][j] = "| |";
                        } else {
                            board[i][j] = chars[j];
                        }
                    }
                }
            }

            scanner.close();

            // whoWon.displayWinner(board);

        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy tệp board.txt.");
        }

        return board;
    }

    public static String concatenateBoard(String[][] board) {
        StringBuilder stringBuilder = new StringBuilder();

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                stringBuilder.append(board[i][j]);
            }
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

    private void initializeChessCells() {
        Color color1 = new Color(209, 139, 71);
        Color color2 = new Color(255, 206, 158);
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                JButton button = new JButton();
                button.setHorizontalAlignment(SwingConstants.CENTER);
                button.setVerticalAlignment(SwingConstants.CENTER);
                if ((i + j) % 2 == 0) {
                    button.setBackground(color1);
                } else {
                    button.setBackground(color2);
                }
                button.setOpaque(true);
                button.setBorderPainted(false);
                button.setFocusPainted(false);
                button.addActionListener(new ChessCellActionListener(i, j));
                final int finalI = i;
                final int finalJ = j;
                button.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        button.setBackground(Color.YELLOW);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if ((finalI + finalJ) % 2 == 0) {
                            button.setBackground(color1);
                        } else {
                            button.setBackground(color2);
                        }
                    }
                });
                chessCells[i][j] = button;
                getContentPane().add(button);
            }
        }
    }

    private void updateBoardFromFile() {
        boardState = ReadBoardFromFile();

        updateChessBoard();

        if (whoWon.whoWon(boardState) != 0 && done == false) {
            done = true;
            whoWon.displayWinner(boardState);

        }
    }

    private void updateChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = boardState[i][j];
                ImageIcon icon = new ImageIcon(getPieceImagePath(piece));
                chessCells[i][j].setIcon(icon);
            }
        }
    }

    public static void WriteBoardToFile(String[][] board) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals("| |")) {
                    board[i][j] = " ";
                }
            }
        }

        // if(whoWon.whoWon(ReadBoardFromFile.ReadBoardFromFile())==0)
        if (true) {
            try {
                FileWriter writer = new FileWriter(boardLink);
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

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    if (board[i][j].equals(" ")) {
                        board[i][j] = "| |";
                    }
                }
            }
        }
    }

    private class ChessCellActionListener implements ActionListener {
        private int row, col;

        public ChessCellActionListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void actionPerformed(ActionEvent e) {
            if (fromRow == -1 && fromCol == -1) {
                fromRow = row;
                fromCol = col;
            } else {
                int toRow = row;
                int toCol = col;
                if (turn == true) {
                    // makeMove.makeMove(fromRow, fromCol, toRow, toCol, boardState, 1);
                    // WriteBoardToFile(boardState);
                    // sendBoard();

                    // turn=false;

                    if (makeMove.makeMove(fromRow, fromCol, toRow, toCol, boardState, side) == true) {
                        WriteBoardToFile(boardState);
                        sendBoard();
                        turn = false;
                    }
                }
                fromRow = -1;
                fromCol = -1;
            }
        }
    }

    private String getPieceImagePath(String piece) {
        String path = "";
        if ("P".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Pawn.png";
        }

        if ("B".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Bishop.png";
        }

        if ("K".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_King.png";
        }

        if ("Q".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Queen.png";
        }

        if ("R".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Rook.png";
        }

        if ("N".equals(piece)) {
            path = "src\\chess_ant\\img\\Black_Knight.png";
        }

        if ("p".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Pawn.png";
        }

        if ("b".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Bishop.png";
        }

        if ("k".equals(piece)) {
            path = "src\\chess_ant\\img\\White_King.png";
        }

        if ("q".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Queen.png";
        }

        if ("r".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Rook.png";
        }

        if ("n".equals(piece)) {
            path = "src\\chess_ant\\img\\White_Knight.png";
        }
        return path;
    }

    public static void WriteToTXT(String s) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(boardLink))) {
            for (int i = 0; i < s.length(); i++) {
                char c = s.charAt(i);
                if (c == '.') {
                    writer.print(' ');
                } else {
                    writer.print(c);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getUsername(String userId) {
        String username = null;
        String sql = "SELECT username FROM users WHERE userid = ?";

        try (Connection conn = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, userId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                username = rs.getString("username");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return username;
    }

    public static void main(String[] args) {
        // socket = new Socket("localhost", 12345);
        WriteBoardToFile(initializeBoard.initializeBoard());
        SwingUtilities.invokeLater(() -> {
            Player2P chessBoard = new Player2P();
            chessBoard.setVisible(true);

            Thread messageReceiverThread = new Thread(() -> {
                try {
                    ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                    // ObjectOutputStream outputStream = new
                    // ObjectOutputStream(socket.getOutputStream());
                    String player = (String) inputStream.readObject();
                    if (player.equals("2")) {
                        System.out.println("player 2");
                        side = -1;
                        turn = false;

                        // Đọc userid của đối phương từ server

                        // Gửi userid của client cho server
                        outputStream.writeObject(String.valueOf(isLoggedin.isLoggedin()));

                        String idEnemy = (String) inputStream.readObject();

                        System.out.println("Id đối thủ: " + idEnemy);
                        JOptionPane.showMessageDialog(null, "Đối thủ của bạn là " + getUsername(idEnemy));

                    } else {
                        System.out.println("player 1");
                        side = 1;
                        // ObjectOutputStream outputStream = new
                        // ObjectOutputStream(socket.getOutputStream());

                        // // Gửi userid của client cho server
                        outputStream.writeObject(String.valueOf(isLoggedin.isLoggedin()));

                        // // Đọc userid của đối phương từ server
                        String idEnemy = (String) inputStream.readObject();
                        System.out.println("Id đối thủ: " + idEnemy);
                        JOptionPane.showMessageDialog(null, "Đối thủ của bạn là " + getUsername(idEnemy));

                    }

                    while (true) {

                        String fromServer;
                        if ((fromServer = (String) inputStream.readObject()) != null) {
                            turn = true;
                            // System.out.println(fromServer);
                            WriteToTXT(fromServer);

                        }

                    }
                } catch (IOException | ClassNotFoundException e) {
                    e.printStackTrace();
                }
            });
            messageReceiverThread.start();

            Timer timer = new Timer(100, e -> {
                chessBoard.updateBoardFromFile();
            });
            timer.start();
        });
    }
}
