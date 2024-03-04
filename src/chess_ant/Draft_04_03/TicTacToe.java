package chess_ant.Draft_04_03;

public class TicTacToe {
    public static final int X = 1;
    public static final int O = -1;
    public static final int EMPTY = 0;

    private int[] board = new int[9];
    private boolean gameEnded = false;

    public TicTacToe() {
        for (int i = 0; i < 9; i++) {
            board[i] = EMPTY;
        }
    }

    public boolean isValidMove(int move, int player) {
        return move >= 0 && move < 9 && board[move] == EMPTY && !gameEnded;
    }

    public void makeMove(int move, int player) {
        board[move] = player;
    }

    public boolean checkWin(int player) {
        return (board[0] == player && board[1] == player && board[2] == player) || // Horizontal top
                (board[3] == player && board[4] == player && board[5] == player) || // Horizontal middle
                (board[6] == player && board[7] == player && board[8] == player) || // Horizontal bottom
                (board[0] == player && board[3] == player && board[6] == player) || // Vertical left
                (board[1] == player && board[4] == player && board[7] == player) || // Vertical middle
                (board[2] == player && board[5] == player && board[8] == player) || // Vertical right
                (board[0] == player && board[4] == player && board[8] == player) || // Diagonal
                (board[2] == player && board[4] == player && board[6] == player); // Diagonal
    }

    public boolean isBoardFull() {
        for (int cell : board) {
            if (cell == EMPTY) {
                return false;
            }
        }
        return true;
    }

    public String getBoardState() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 9; i++) {
            char cell;
            switch (board[i]) {
                case X:
                    cell = 'X';
                    break;
                case O:
                    cell = 'O';
                    break;
                default:
                    cell = ' ';
            }
            sb.append(cell);
            if (i % 3 == 2) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }

    public boolean isGameEnded() {
        return gameEnded;
    }

    public void setGameEnded(boolean gameEnded) {
        this.gameEnded = gameEnded;
    }
}

