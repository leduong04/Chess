package chess_ant;
public class Chess_AI {

    public static int evaluateBoard(String[][] board) {
        int whiteScore = 0;
        int blackScore = 0;

        int pawnValue = 1;
        int knightValue = 3;
        int bishopValue = 3;
        int rookValue = 5;
        int queenValue = 9;
        int kingValue = 10;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String piece = board[i][j];
                if (!piece.equals("| |")) {
                    int pieceValue = 0;

                    if (piece.equals("P") || piece.equals("p")) {
                        pieceValue = pawnValue;
                    } else if (piece.equals("N") || piece.equals("n")) {
                        pieceValue = knightValue;
                    } else if (piece.equals("B") || piece.equals("b")) {
                        pieceValue = bishopValue;
                    } else if (piece.equals("R") || piece.equals("r")) {
                        pieceValue = rookValue;
                    } else if (piece.equals("Q") || piece.equals("q")) {
                        pieceValue = queenValue;
                    } else {
                        pieceValue = kingValue;
                    }
                    if (Character.isUpperCase(piece.charAt(0))) {
                        whiteScore += pieceValue;
                    } else {
                        blackScore += pieceValue;
                    }
                }
            }
        }


        return whiteScore - blackScore;
    }

   
    public static int checkWin(String[][] board, int bot) {
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
            if (bot == 1) {
                return 10;
            } else {
                return -10;
            }
        }
        if (K == 0) {
            if (bot == 1) {
                return -10;
            } else {
                return 10;
            }
        }

        return 0;
    }

    public static String[] getChars(int mode) {
        String[] chars = null;
        if (mode == 1) {
            chars = new String[] { "R", "N", "B", "Q", "K", "P" };
        } else if (mode == -1) {
            chars = new String[] { "r", "n", "b", "q", "k", "p" };
        } else {
            throw new IllegalArgumentException("Invalid mode. Mode must be 0 or 1.");
        }
        return chars;
    }

    
    public static int[] Chess_AI(String[][] board, int bot) {
        int bestScore = Integer.MIN_VALUE;
        int[] bestMove = new int[4];
    
        String[] s = (bot == 1) ? getChars(1) : getChars(-1);
        String[][] temp = new String[8][8];
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals(s[0]) || board[i][j].equals(s[1]) || board[i][j].equals(s[2])
                        || board[i][j].equals(s[3]) || board[i][j].equals(s[4]) || board[i][j].equals(s[5])) {
                    for (int m = 0; m < 8; m++) {
                        for (int n = 0; n < 8; n++) {
                            if (isValidMove.isValidMove(i, j, m, n, board)) {
                                String[][] newBoard = cloneBoard(board); // Clone board
                                makeMove(newBoard, i, j, m, n); // Make move on the cloned board
                                int score = minimax(0, Integer.MIN_VALUE, Integer.MAX_VALUE, 1, newBoard, temp, bot);
                                if (score > bestScore) {
                                    bestScore = score;
                                    bestMove[0] = i;
                                    bestMove[1] = j;
                                    bestMove[2] = m;
                                    bestMove[3] = n;
                                }
                            }
                        }
                    }
                }
            }
        }
    
        makeMove(board, bestMove[0], bestMove[1], bestMove[2], bestMove[3]);

        
        return bestMove;
    }
    
    public static int minimax(int depth, int alpha, int beta, int player, String[][] board, String[][] temp, int bot) {
        if (checkWin(board, bot) != 0 || depth == 0) {
            return evaluateBoard(board);
        }
    
        String[] s = (player == bot) ? getChars(bot) : getChars(-bot);
        int bestValue = (player == bot) ? Integer.MIN_VALUE : Integer.MAX_VALUE;
    
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (board[i][j].equals(s[0]) || board[i][j].equals(s[1]) || board[i][j].equals(s[2])
                        || board[i][j].equals(s[3]) || board[i][j].equals(s[4]) || board[i][j].equals(s[5])) {
                    for (int m = 0; m < 8; m++) {
                        for (int n = 0; n < 8; n++) {
                            if (isValidMove.isValidMove(i, j, m, n, board)) {
                                String[][] newBoard = cloneBoard(board); // Clone board
                                makeMove(newBoard, i, j, m, n); // Make move on the cloned board
                                int value = minimax(depth - 1, alpha, beta, -player, newBoard, temp, bot);
                                if (player == bot) {
                                    bestValue = Math.max(bestValue, value);
                                    alpha = Math.max(alpha, bestValue);
                                } else {
                                    bestValue = Math.min(bestValue, value);
                                    beta = Math.min(beta, bestValue);
                                }
                                if (beta <= alpha)
                                    break;
                            }
                        }
                        if (beta <= alpha)
                            break;
                    }
                }
            }
        }
        return bestValue;
    }
    
    private static String[][] cloneBoard(String[][] board) {
        String[][] newBoard = new String[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                newBoard[i][j] = board[i][j];
            }
        }
        return newBoard;
    }
    
    private static void makeMove(String[][] board, int fromRow, int fromCol, int toRow, int toCol) {
        board[toRow][toCol] = board[fromRow][fromCol];
        board[fromRow][fromCol] = "| |";
    }
    
    
    
}
