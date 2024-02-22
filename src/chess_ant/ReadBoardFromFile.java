package chess_ant;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ReadBoardFromFile {
    public static void main(String[] args) {
        // Khởi tạo mảng board
        String[][] board = new String[8][8];

        try {
            // Tạo một đối tượng Scanner để đọc từ tệp board.txt
            Scanner scanner = new Scanner(new File("board.txt"));

            // Đọc từng dòng từ tệp
            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    // Chia dòng thành các ký tự và lưu vào mảng board
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

            // Đóng Scanner
            scanner.close();

            // In mảng board để kiểm tra xem dữ liệu đã được đọc đúng hay không
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
            // Tạo một đối tượng Scanner để đọc từ tệp board.txt
            Scanner scanner = new Scanner(new File("board.txt"));

            // Đọc từng dòng từ tệp
            for (int i = 0; i < 8; i++) {
                if (scanner.hasNextLine()) {
                    String line = scanner.nextLine();

                    // Chia dòng thành các ký tự và lưu vào mảng board
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

            // Đóng Scanner
            scanner.close();

            
        } catch (FileNotFoundException e) {
            System.err.println("Không tìm thấy tệp board.txt.");
        }

        return board;
    }
}
