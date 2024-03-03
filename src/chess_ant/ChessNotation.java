package chess_ant;

import java.util.HashMap;
import java.util.Map;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ChessNotation {
    public static String ChessNotation( int fromRow, int fromCol, int toRow, int toCol, String ChessNotation) {

        char toColChar = (char) ('a' + toCol);
        int toRowInt = 8 - toRow;

        String[][] board = ReadBoardFromFile.ReadBoardFromFile();
        String pieceName = board[fromRow][fromCol];

        if(!isAllUpperCaseRegex(pieceName))
        {
            ChessNotation+=(lastNumber(ChessNotation)+1)+". ";
        }

        ChessNotation += pieceName;
        if (!board[toRow][toCol].equals("| |")) {
            ChessNotation += 'x';
        }

        ChessNotation += toColChar+"" + toRowInt+" ";
        System.out.println(ChessNotation);

        rewriteFile("src\\chess_ant\\Notation.txt", ChessNotation);

        return ChessNotation;
    }

    private static int lastNumber(String input) {
        String cleanString = input.trim(); // Loại bỏ các khoảng trắng ở đầu và cuối chuỗi
    
        int lastDotIndex = cleanString.lastIndexOf('.');
        if (lastDotIndex == -1) {
            System.out.println("Không tìm thấy dấu chấm trong chuỗi.");
            return 0;
        }
    
        String beforeLastDot = cleanString.substring(0, lastDotIndex);
    
        int lastSpaceIndex = beforeLastDot.lastIndexOf(' ');
        // if (lastSpaceIndex == -1) {
        //     System.out.println("Không tìm thấy dấu cách trong chuỗi số.");
        //     return 0;
        // }
    
        String numberString = beforeLastDot.substring(lastSpaceIndex + 1);
    
        if (isNumeric(numberString)) {
            System.out.println("Số đứng trước dấu chấm cuối cùng: " + numberString);
        } else {
            System.out.println("Không có số đứng trước dấu chấm cuối cùng.");
            return 0;
        }
    
        System.out.println("number: " + numberString);
        int number = Integer.valueOf(numberString);
        return number;
    }
    

    public static boolean isNumeric(String str) {
        return str.matches("-?\\d+(\\.\\d+)?");
    }

    public static boolean isAllUpperCaseRegex(String str) {
        return str.matches("[A-Z]+");
    }
    

    public static String readFromFile(String filePath) {
        StringBuilder content = new StringBuilder();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                content.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return content.toString();
    }

    public static void rewriteFile(String filePath, String content) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(content);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void New() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("src\\chess_ant\\Notation.txt"))) {
            writer.write("");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        System.out.println(ChessNotation(6, 0, 4, 0, readFromFile("src\\chess_ant\\Notation.txt")));

    }
}