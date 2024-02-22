package chess_ant;
public class initializeBoard {

    public static String[][] board = new String[8][8];

    public static String[][] initializeBoard() {
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (i == 1) {
                    board[i][j] = "P";
                } else if (i == 6) {
                    board[i][j] = "p";
                }
                else
                {
                    board[i][j]="| |";
                }
            }
        }


        board[0][0] = "R";
        board[0][1] = "N";
        board[0][2] = "B";
        board[0][3] = "Q";
        board[0][4] = "K";
        board[0][5] = "B";
        board[0][6] = "N";
        board[0][7] = "R";

        board[7][0] = "r";
        board[7][1] = "n";
        board[7][2] = "b";
        board[7][3] = "q";
        board[7][4] = "k";
        board[7][5] = "b";
        board[7][6] = "n";
        board[7][7] = "r";


        return board;
    }
    
}
