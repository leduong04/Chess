package chess_ant;
import java.io.FileWriter;
import java.io.IOException;

//file này để cập nhật bàn cờ cho board.txt bằng hàm WriteBoardToFile(String[][] board)
public class WriteBoardToFile {
    public static void main(String[] args) {
        
        String[][] board = {
                { "R", "N", "B", "Q", "K", "B", "N", "R" },
                { "P", "P", "P", "P", "P", "P", "P", "P" },
                { " ", " ", " ", " ", " ", " ", " ", " " },
                { " ", " ", " ", " ", " ", " ", " ", " " },
                { " ", " ", " ", " ", " ", " ", " ", " " },
                { " ", " ", " ", " ", " ", " ", " ", " " },
                { "p", "p", "p", "p", "p", "p", "p", "p" },
                { "r", "n", "b", "q", "k", "b", "n", "r" }
        };

        try {
            FileWriter writer = new FileWriter("src\\chess_ant\\board.txt");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    writer.write(board[i][j]);
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Đã ghi mảng board vào tệp board.txt thành công.");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào tệp board.txt: " + e.getMessage());
        }
    }

    public static void WriteBoardToFile(String[][] board) {

        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if(board[i][j].equals("| |"))
                {
                    board[i][j]=" ";
                }
            }
        }



        try {
            FileWriter writer = new FileWriter("src\\chess_ant\\board.txt");
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    writer.write(board[i][j]);
                }
                writer.write("\n");
            }
            writer.close();
            System.out.println("Đã ghi mảng board vào tệp board.txt thành công.");
        } catch (IOException e) {
            System.err.println("Lỗi khi ghi vào tệp board.txt: " + e.getMessage());
        }

        for(int i=0; i<8; i++)
        {
            for(int j=0; j<8; j++)
            {
                if(board[i][j].equals(" "))
                {
                    board[i][j]="| |";
                }
            }
        }
    }
    
}
