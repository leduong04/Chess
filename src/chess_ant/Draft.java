package chess_ant;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class Draft {

    private static String previousBoardState;
    private static String currentBoardState;
    public static void main(String[] args) {
        while(true)
        {
            currentBoardState = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
            if(isBoardStateChanged())
            {
                System.out.println("File changes");
                // break;
            }

            
        }
    }

    private static boolean isBoardStateChanged() {
        if(previousBoardState==null)
        {
            previousBoardState = currentBoardState;
        }
        
        if (!previousBoardState.equals(currentBoardState)) {
            previousBoardState = currentBoardState;
            
            System.out.println("Fuck-------------------------------------------------");
            return true;
        }
        // System.out.println("No fuck");
        return false;
    }


    // private static boolean isBoardStateChanged(String currentBoardState) {
    //     String newBoardState = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
    //     if (!currentBoardState.equals(newBoardState)) {
    //         // previousBoardState = newBoardState;
    //         System.out.println("File changes");
    //         return true;
    //     }
    //     return false;
    // }
    
    // private static boolean isBoardStateChanged(String currentBoardState) {
    //     String newBoardState = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
    //     if (!currentBoardState.equals(newBoardState)) {
    //         previousBoardState = newBoardState;
    //         System.out.println("Fuck");
    //         return true;
    //     }
    //     // System.out.println("No fuck");
    //     return false;
    // }

    public static String readBoardFromFile(String filePath) {
        StringBuilder boardContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boardContent.append(line.trim());
                boardContent.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boardContent.toString();
    }
}
