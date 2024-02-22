package chess_ant;

//file này để in ra bàn cờ dưới dạng text 
public class printBoard {
    public static void printBoard(String[][] board) {
        for (int i = 0; i < 8; i++) {

            for (int j = 0; j < 8; j++) {

                if (board[i][j].equals("| |")) {
                    System.out.print("  ");
                }

                else {
                    System.out.print(board[i][j] + " ");
                }
            }
            System.out.println();
        }
    }
}
