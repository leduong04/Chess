package chess_ant;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

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
        try {
            Scanner scanner = new Scanner(new File("board.txt"));

            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] chars = line.split("");
                    for (int j = 0; j < 8; j++) {
                        if (chars[j].equals(" ")) {
                            boardState[i][j] = "| |";
                        } else {
                            boardState[i][j] = chars[j];
                        }
                    }
                }
            }

            
            scanner.close();

            updateChessBoard();
        } catch (IOException e) {
            e.printStackTrace();
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
                WriteBoardToFile.WriteBoardToFile(boardState);
                fromRow = -1;
                fromCol = -1;
            }
        }
    }



    

    private String getPieceImagePath(String piece) {
        String path = "";
        if ("P".equals(piece)) {
            path = "img\\Black_Pawn.png";
        }

        if ("B".equals(piece)) {
            path = "img\\Black_Bishop.png";
        }

        if ("K".equals(piece)) {
            path = "img\\Black_King.png";
        }

        if ("Q".equals(piece)) {
            path = "img\\Black_Queen.png";
        }

        if ("R".equals(piece)) {
            path = "img\\Black_Rook.png";
        }

        if ("N".equals(piece)) {
            path = "img\\Black_Knight.png";
        }

        if ("p".equals(piece)) {
            path = "img\\White_Pawn.png";
        }

        if ("b".equals(piece)) {
            path = "img\\White_Bishop.png";
        }

        if ("k".equals(piece)) {
            path = "img\\White_King.png";
        }

        if ("q".equals(piece)) {
            path = "img\\White_Queen.png";
        }

        if ("r".equals(piece)) {
            path = "img\\White_Rook.png";
        }

        if ("n".equals(piece)) {
            path = "img\\White_Knight.png";
        }
        return path;
    }

    public static void main(int botInput) {
        bot = botInput;
        WriteBoardToFile.WriteBoardToFile(initializeBoard.initializeBoard());

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
