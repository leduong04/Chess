package chess_ant;

import javax.swing.JOptionPane;

public class whoWon {
    public static void displayWinner(String[][] board) {
        int winner = whoWon(board);
        String message = "";

        if (winner == 1) {
            message = "Người chơi màu trắng đã chiến thắng!";
        } else if (winner == -1) {
            message = "Người chơi màu đen đã chiến thắng!";
        }

        if (!message.isEmpty()) {
            JOptionPane.showMessageDialog(null, message, "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    private static int whoWon(String[][] board) {
        int k = 0;
        int K = 0;

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals("K")) {
                    K++;
                }
                if (board[i][j].equals("k")) {
                    k++;
                }
            }
        }

        if (k == 0) {
            return -1; // Người chơi màu trắng thắng
        }
        if (K == 0) {
            return 1; // Người chơi màu đen thắng
        }

        return 0; // Trò chơi vẫn tiếp tục
    }
}
