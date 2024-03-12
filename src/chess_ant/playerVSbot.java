package chess_ant;

//file này thực hiện người đánh với máy
import javax.swing.JOptionPane;

public class playerVSbot {
    public static void main(String[] args) {
        boolean playersChoice = false;
        while (!playersChoice) {
            String[] options = { "Trắng", "Đen", "Hủy" };
            int result = JOptionPane.showOptionDialog(null, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);

            ChessNotation.New();
            WriteBoardToFile.WriteBoardToFile(initializeBoard.initializeBoard());
            if (result == 0) {
                Player.main(1);
                Bot.Bot(1);
                playersChoice = true;
            } else if (result == 1) {
                Player.main(-1);
                Bot.Bot(-1);
                playersChoice = true;
            } else if (result == 2) {
                System.exit(0);
            }
        }
    }
}
