package chess_ant;
import javax.swing.JOptionPane;

public class makeMove {
    public static void makeMove(int fromRow, int fromCol, int toRow, int toCol, String[][] board, int bot) {
    if(checkSide(board[fromRow][fromCol], bot)) {
        if (isValidMove.isValidMove(fromRow, fromCol, toRow, toCol, board)) {
            board[toRow][toCol] = board[fromRow][fromCol];
            board[fromRow][fromCol] = "| |";
        } else {
            // Hiển thị thông báo khi nước đi không hợp lệ
            JOptionPane.showMessageDialog(null, "Nước đi không hợp lệ, hãy thực hiện lại");
        }
    } else {
        // Hiển thị thông báo khi chọn sai quân
        JOptionPane.showMessageDialog(null, "Không phải quân của bạn, hãy thực hiện lại");
        
    }
}
    public static boolean checkSide(String str, int bot) {
        if (str.matches("[A-Z]+") && bot == -1) {
            return true;
        }
        if (str.matches("[A-Z]+") == false && bot == 1) {
            return true;
        }
        return false;
    }
}
