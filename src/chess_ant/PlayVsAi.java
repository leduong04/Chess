package chess_ant;

import javax.swing.*;


import java.awt.*;
import java.awt.event.*;

public class PlayVsAi extends JFrame {
    private static JLabel[][] chessCells = new JLabel[8][8];
    private static String[][] boardState = new String[8][8];
    private int fromRow = -1, fromCol = -1;
    public static int bot = 0;
    public static boolean turn;
    

    public PlayVsAi() {
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


        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) {
                    boardState[i][j] = "P";
                } else if (i == 6) {
                    boardState[i][j] = "p";
                } else {
                    boardState[i][j] = "| |";
                }
            }
        }

        boardState[0][0] = "R";
        boardState[0][1] = "N";
        boardState[0][2] = "B";
        boardState[0][3] = "Q";
        boardState[0][4] = "K";
        boardState[0][5] = "B";
        boardState[0][6] = "N";
        boardState[0][7] = "R";

        boardState[7][0] = "r";
        boardState[7][1] = "n";
        boardState[7][2] = "b";
        boardState[7][3] = "q";
        boardState[7][4] = "k";
        boardState[7][5] = "b";
        boardState[7][6] = "n";
        boardState[7][7] = "r";
        if (bot == -1) {
            Chess_AI.Chess_AI(boardState, -1);
            turn=true;
        }
        updateChessBoard();
    }

    private static void updateChessBoard() {
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
                if (whoWon.displayWinner(boardState) == 0 && turn == true) {
                    if (makeMove.makeMove(fromRow, fromCol, toRow, toCol, boardState, bot))

                    {
                        updateChessBoard();
                        turn = false;

                        whoWon.displayWinner(boardState);
                    }
                }

                if (whoWon.displayWinner(boardState) == 0 && turn == false) {
                    Chess_AI.Chess_AI(boardState, bot);

                    updateChessBoard();
                    turn = true;

                    whoWon.displayWinner(boardState);
                }

                fromRow = -1;
                fromCol = -1;
            }
        }
    }

    private static String getPieceImagePath(String piece) {
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

    public static void main(int botInput) {

        // Bot.Bot(-1);

        bot = botInput;
        if (bot == 1) {
            turn = true;
        } else {
            turn = false;
        }

        SwingUtilities.invokeLater(() -> {
            PlayVsAi chessBoard = new PlayVsAi();
            chessBoard.setVisible(true);

        });
    }

    public static void main(String[] args) {
        main(-1);
    }
}
