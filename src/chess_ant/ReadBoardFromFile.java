package chess_ant;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

//file này để cập nhật bàn cờ cho board.txt bằng hàm ReadBoardFromFile()
public class ReadBoardFromFile {
    public static void main(String[] args) {
        String[][] board = new String[8][8];

        try {
            Scanner scanner = new Scanner(new File("board.txt"));

            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] chars = line.split("");
                    for (int j = 0; j < 8; j++) {
                        if (chars[j].equals(" ")) {
                            board[i][j] = "| |";
                        } else {
                            board[i][j] = chars[j];
                        }
                    }
                }
            }

            scanner.close();

            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    System.out.print(board[i][j] + " ");
                }
                System.out.println();
            }
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy tệp board.txt.");
        }
    }

    public static String[][] ReadBoardFromFile() {
        String[][] board = new String[8][8];

        try {
            Scanner scanner = new Scanner(new File("board.txt"));

            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    String[] chars = line.split("");
                    for (int j = 0; j < 8; j++) {
                        if (chars[j].equals(" ")) {
                            board[i][j] = "| |";
                        } else {
                            board[i][j] = chars[j];
                        }
                    }
                }
            }

            scanner.close();

            
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy tệp board.txt.");
        }

        return board;
    }
}
