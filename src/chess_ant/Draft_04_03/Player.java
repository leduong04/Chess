package chess_ant.Draft_04_03;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

import chess_ant.*;

public class Player extends JFrame {
    private JButton[][] chessCells = new JButton[8][8];
    private String[][] boardState = new String[8][8];
    private int fromRow = -1, fromCol = -1;

    public Player() {
        setTitle("Real-time Chess Board");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(8, 8));
        initializeChessCells();
        updateBoardFromFile();
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
    }

    public static String[][] ReadBoardFromFile() {
        String[][] board = new String[8][8];

        try {
            Scanner scanner = new Scanner(new File("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt"));

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
    private void updateChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = boardState[i][j];
                ImageIcon icon = new ImageIcon(getPieceImagePath(piece));
                chessCells[i][j].setIcon(icon);
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
                makeMove.makeMove(fromRow, fromCol, toRow, toCol, boardState, 1);
                // MessengerApp.sendMessage();
                WriteBoardToFile(boardState);
                fromRow = -1;
                fromCol = -1;
            }
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
                FileWriter writer = new FileWriter("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
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

    private String getPieceImagePath(String piece) {
        String path = "";
        if ("P".equals(piece)) {
            path = "D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\img\\Black_Pawn.png";
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

    public static void main(String[] args) {
        WriteBoardToFile(initializeBoard.initializeBoard());
        SwingUtilities.invokeLater(() -> {
            Player chessBoard = new Player();
            chessBoard.setVisible(true);
            Timer timer = new Timer(100, e -> {
                chessBoard.updateBoardFromFile();
            });
            timer.start();
        });
    }
}
