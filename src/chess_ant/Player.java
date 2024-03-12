package chess_ant;

import javax.swing.*;

import chess_ant.Draft_04_03.MessengerApp;

import java.awt.*;
import java.awt.event.*;

//file này thực thi các nước của người chơi và hiển thị giao diện bàn cờ
public class Player extends JFrame {
    private JLabel[][] chessCells = new JLabel[8][8];
    private String[][] boardState = new String[8][8];
    private int fromRow = -1, fromCol = -1;
    public static int bot = 0;
    // bot = 1: Bot is Black
    // bot = -1: Bot is White

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
                JLabel label = new JLabel();
                label.setHorizontalAlignment(SwingConstants.CENTER);
                label.setVerticalAlignment(SwingConstants.CENTER);

                if ((i + j) % 2 == 0) {
                    label.setBackground(color1);
                } else {
                    label.setBackground(color2);
                }

                label.setOpaque(true);

                label.addMouseListener(new ChessCellClickListener(i, j));

                final int finalI = i;
                final int finalJ = j;
                label.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseEntered(MouseEvent e) {
                        label.setBackground(Color.YELLOW);
                    }

                    @Override
                    public void mouseExited(MouseEvent e) {
                        if ((finalI + finalJ) % 2 == 0) {
                            label.setBackground(color1);
                        } else {
                            label.setBackground(color2);
                        }
                    }
                });

                chessCells[i][j] = label;
                getContentPane().add(label);
            }
        }
    }

    private void updateBoardFromFile() {

        // System.out.println("UPDATE");
        boardState = ReadBoardFromFile.ReadBoardFromFile();
        updateChessBoard();
    }

    private void updateChessBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = boardState[i][j];
                ImageIcon icon = new ImageIcon(getPieceImagePath(piece));
                chessCells[i][j].setIcon(icon);
            }
        }
        // whoWon.displayWinner(boardState);
        // whoWon.displayWinner(ReadBoardFromFile.ReadBoardFromFile());
    }

    private class ChessCellClickListener extends MouseAdapter {
        private int row, col;

        public ChessCellClickListener(int row, int col) {
            this.row = row;
            this.col = col;
        }

        public void mouseClicked(MouseEvent e) {
            if (fromRow == -1 && fromCol == -1) {
                fromRow = row;
                fromCol = col;
            } else {
                int toRow = row;
                int toCol = col;

                makeMove.makeMove(fromRow, fromCol, toRow, toCol, boardState, bot);
                // MessengerApp.sendMessage();
                WriteBoardToFile.WriteBoardToFile(boardState);
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

    // public static void main(int botInput) {
    //     bot = botInput;
    //     WriteBoardToFile.WriteBoardToFile(initializeBoard.initializeBoard());

    //     SwingUtilities.invokeLater(() -> {
    //         Player chessBoard = new Player();
    //         chessBoard.setVisible(true);

    //         Timer timer = new Timer(100, e -> {
    //             chessBoard.updateBoardFromFile();
    //             whoWon.displayWinner(ReadBoardFromFile.ReadBoardFromFile());
    //         });
    //         timer.start();
    //     });
    // }

    // public static void main(int botInput) {
    // bot = botInput;
    // WriteBoardToFile.WriteBoardToFile(initializeBoard.initializeBoard());

    // SwingUtilities.invokeLater(() -> {
    // Player chessBoard = new Player();
    // chessBoard.setVisible(true);

    // Timer timer = new Timer(100, e -> {
    // chessBoard.updateBoardFromFile();
    // int result = whoWon.whoWon(ReadBoardFromFile.ReadBoardFromFile());

    // if (result != 0) {
    // String message = "";

    // if (result == 1) {
    // message = "Người chơi màu trắng đã chiến thắng!";
    // } else if (result == -1) {
    // message = "Người chơi màu đen đã chiến thắng!";
    // }

    // if (!message.isEmpty()) {
    // JOptionPane.showMessageDialog(null, message, "Thông báo",
    // JOptionPane.INFORMATION_MESSAGE);
    // }
    // ((Timer) e.getSource()).stop(); // Ngừng timer nếu kết quả không phải 0
    // }
    // });
    // timer.start();
    // });
    // }


    public static void main(int botInput) {
        bot = botInput;
        // ChessNotation.New();
        // WriteBoardToFile.WriteBoardToFile(initializeBoard.initializeBoard());

        SwingUtilities.invokeLater(() -> {
            Player chessBoard = new Player();
            chessBoard.setVisible(true);

            Timer timer = new Timer(100, e -> {
                chessBoard.updateBoardFromFile();
                // whoWon.displayWinner(ReadBoardFromFile.ReadBoardFromFile());
            });
            timer.start();
        });
    }
}
