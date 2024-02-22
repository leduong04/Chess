package chess_ant;

//file này kiểm tra tính hợp lê của nước đi qua hàm isValidMove(int startX, int startY, int endX, int endY, String[][] board)
public class isValidMove {

    public static boolean isValidMove(int startX, int startY, int endX, int endY, String[][] board) {
        String s = board[startX][startY];
        if (s.equals("R") || s.equals("r") || s.equals("Q") || s.equals("q")) {
            if (Xe_Hau(s, startX, startY, endX, endY, board)) {
                return true;
            }

            
        }

        if (s.equals("B") || s.equals("b") || s.equals("Q") || s.equals("q")) {
            if (Cheo(s, startX, startY, endX, endY, board) && Tuong_Hau(s, board)) {
                return true;
            }
        }

        if (s.equals("n") || s.equals("N")) {
            if (Ma(s, startX, startY, endX, endY, board)) {
                return true;
            }
        }

        if (s.equals("K") || s.equals("k")) {
            if (Vua(s, startX, startY, endX, endY, board)) {
                return true;
            }
        }

        if (s.equals("p") || s.equals("P")) {
            if (Tot(s, startX, startY, endX, endY, board)) {
                return true;
            }
        }

        return false;
    }

    public static boolean Xe_Hau(String s, int startX, int startY, int endX, int endY, String[][] board) {

        if (s.equals("Q") || s.equals("q") || s.equals("r") || s.equals("R")) {
            if (Math.abs(startX - endX) != 0 && Math.abs(startY - endY) == 0
                    || Math.abs(startX - endX) == 0 && Math.abs(startY - endY) != 0) {
                if (KhacPhe(s, board[endX][endY]) || board[endX][endY].equals("| |")) {
                    int x = startX;
                    int y = startY;


                    if (startX > endX) {
                        while (x > endX + 1) {
                            x--;
                            if (!board[x][y].equals("| |")) {
                                
                                return false;
                            }

                        }
                        return true;

                    }

                    if (startX < endX) {
                        while (x < endX - 1) {
                            x++;
                            if (!board[x][y].equals("| |")) {
                                return false;
                            }
                        }
                        return true;
                    }

                    if (startY > endY) {
                        while (y > endY + 1) {
                            y--;
                            if (!board[x][y].equals("| |")) {
                                return false;
                            }
                        }
                        return true;
                    }

                    if (startY < endY) {
                        while (y < endY - 1) {
                            y++;
                            if (!board[x][y].equals("| |")) {
                                return false;
                            }
                        }
                        return true;
                    }
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static boolean Cheo(String s, int startX, int startY, int endX, int endY, String[][] board) {
        if (Math.abs(startX - endX) == Math.abs(startY - endY)) {
            int x = startX;
            int y = startY;

            if (KhacPhe(s, board[endX][endY]) || board[endX][endY].equals("| |")) {

                if (startX > endX && startY > endY) {
                    while (x != endX + 1) {
                        x--;
                        y--;
                        if (board[x][y] != "| |") {

                            return false;
                        }
                    }
                    return true;
                }

                if (startX < endX && startY > endY) {
                    while (x != endX - 1) {
                        x++;
                        y--;
                        if (board[x][y] != "| |") {
                            return false;
                        }
                    }
                    return true;
                }

                if (startX > endX && startY < endY) {
                    while (x != endX + 1) {
                        x--;
                        y++;
                        if (board[x][y] != "| |") {
                            return false;
                        }
                    }
                    return true;
                }

                if (startX < endX && startY < endY) {
                    while (x != endX - 1) {
                        x++;
                        y++;
                        if (board[x][y] != "| |") {
                            return false;
                        }
                    }
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean Tuong_Hau(String s, String[][] board) {
        if (s.equals("B") || s.equals("b") || s.equals("q") || s.equals("Q")) {
            return true;
        }

        return false;
    }

    public static boolean Ma(String s, int startX, int startY, int endX, int endY, String[][] board) {

        if (s.equals("N") || s.equals("n")) {
            if (KhacPhe(s, board[endX][endY]) || board[endX][endY].equals("| |")) {
                if (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 2
                        || Math.abs(startX - endX) == 2 && Math.abs(startY - endY) == 1) {
                    
                    return true;
                }
            }
        }
        return false;

    }

    public static boolean Vua(String x, int startX, int startY, int endX, int endY, String[][] board) {
        if (x.equals("K") || x.equals("k")) {
            if (KhacPhe(x, board[endX][endY]) || board[endX][endY].equals("| |")) {
                if (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 1) {
                    return true;
                }

                if (Math.abs(startX - endX) == 1 && Math.abs(startY - endY) == 0) {
                    return true;
                }

                if (Math.abs(startX - endX) == 0 && Math.abs(startY - endY) == 1) {
                    return true;
                }



            }
        }
        return false;
    }

    public static boolean Tot(String s, int startX, int startY, int endX, int endY, String[][] board) {
        if (s.equals("p")) {
            if (KhacPhe(s, board[endX][endY]) || board[endX][endY].equals("| |")) {
                if (endX - startX == -2 && startX == 6 && startY == endY && board[endX][endY].equals("| |")) {
                    if (board[startX - 1][startY].equals("| |")) {
                        return true;
                    }
                }
                if (endX - startX == -1 && startY == endY && board[endX][endY].equals("| |")) {
                    return true;
                }
                if (endX - startX == -1 && endY - startY == 1 && !board[endX][endY].equals("| |")
                        && KhacPhe(s, board[endX][endY])) {
                    return true;
                }
                if (endX - startX == -1 && endY - startY == -1 && !board[endX][endY].equals("| |")
                        && KhacPhe(s, board[endX][endY])) {
                    return true;
                }
            }
        }

        if (s.equals("P")) {
            if (KhacPhe(s, board[endX][endY]) || board[endX][endY].equals("| |")) {
                if (endX - startX == 2 && startX == 1 && startY == endY && board[endX][endY].equals("| |")) {
                    if (board[startX + 1][startY].equals("| |")) {
                        return true;
                    }
                }
                if (endX - startX == 1 && startY == endY && board[endX][endY].equals("| |")) {
                    return true;
                }
                if (endX - startX == 1 && endY - startY == 1 && !board[endX][endY].equals("| |")
                        && KhacPhe(s, board[endX][endY])) {
                    return true;
                }
                if (endX - startX == 1 && endY - startY == -1 && !board[endX][endY].equals("| |")
                        && KhacPhe(s, board[endX][endY])) {
                    return true;
                }
            }
        }
        return false;
    }

    public static boolean KhacPhe(String a, String b) {
        char start = a.charAt(0);
        char end = b.charAt(0);
        return (Character.isUpperCase(start) && Character.isUpperCase(end) == false
                || Character.isUpperCase(end) && Character.isUpperCase(start) == false);
    }

    public static void main(String[] args) {
        String[][] board = ReadBoardFromFile.ReadBoardFromFile();
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
        System.out.println(isValidMove(0, 7, 1, 7, board));
    }
}
