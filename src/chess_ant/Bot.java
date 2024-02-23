package chess_ant;
import java.io.File;
import java.io.IOException;
import java.util.Scanner;

public class Bot {
    private static String[][] previousBoardState;

    public static void Bot(int bot) {

        if (bot == -1) {
            previousBoardState = initializeBoard.initializeBoard();
            WriteBoardToFile.WriteBoardToFile(previousBoardState);

            for (int i = 0; i < 1000; i++) {
                System.out.println(i);
            }
            Chess_AI.Chess_AI(previousBoardState, -1);
            WriteBoardToFile.WriteBoardToFile(previousBoardState);
        } else {
            previousBoardState = ReadBoardFromFile.ReadBoardFromFile();
        }
        while (true) {
            try {
                Thread.sleep(100); // Chờ 1 giây trước khi kiểm tra lại
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[][] currentBoardState = ReadBoardFromFile.ReadBoardFromFile();
            whoWon.displayWinner(currentBoardState);
            if (isBoardStateChanged(currentBoardState)) {
                Chess_AI.Chess_AI(currentBoardState, bot);
                WriteBoardToFile.WriteBoardToFile(currentBoardState);
                System.out.println("AI done");

            }
        }
    }

    // private static String[][] readBoardFromFile(String filename) {
    //     String[][] boardState = new String[8][8];
    //     try {
    //         Scanner scanner = new Scanner(new File(filename));
    //         for (int i = 0; i < 8; i++) {
    //             if (scanner.hasNextLine()) {
    //                 String line = scanner.nextLine();

    //                 // Chia dòng thành các ký tự và lưu vào mảng board
    //                 String[] chars = line.split("");
    //                 for (int j = 0; j < 8; j++) {
    //                     if (chars[j].equals(" ")) {
    //                         boardState[i][j] = "| |";
    //                     } else {
    //                         boardState[i][j] = chars[j];
    //                     }
    //                 }
    //             }
    //         }
    //         scanner.close();
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //     }
    //     return boardState;
    // }

    private static boolean isBoardStateChanged(String[][] currentBoardState) {
        if (previousBoardState == null || previousBoardState.length != currentBoardState.length) {
            previousBoardState = currentBoardState;
            return true;
        }

        for (int i = 0; i < currentBoardState.length; i++) {
            for (int j = 0; j < currentBoardState[i].length; j++) {
                if (!currentBoardState[i][j].equals(previousBoardState[i][j])) {
                    previousBoardState = currentBoardState;
                    return true;
                }
            }
        }
        return false;
    }

    public static void main(String[] args) {

        //
        previousBoardState = initializeBoard.initializeBoard();
        WriteBoardToFile.WriteBoardToFile(previousBoardState);

        for (int i = 0; i < 1000; i++) {
            System.out.println(i);
        }
        Chess_AI.Chess_AI(previousBoardState, -1);
        WriteBoardToFile.WriteBoardToFile(previousBoardState);

        //

        // previousBoardState =
        // readBoardFromFile("src\\chess_ant\\img\\Draft_17_11\\Draft_15_02\\board.txt");

        while (true) {
            try {
                Thread.sleep(100); // Chờ 1 giây trước khi kiểm tra lại
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            String[][] currentBoardState = ReadBoardFromFile.ReadBoardFromFile();

            if (isBoardStateChanged(currentBoardState)) {
                Chess_AI.Chess_AI(currentBoardState, -1);
                WriteBoardToFile.WriteBoardToFile(currentBoardState);
                System.out.println("AI done");

            }
        }
    }
}
