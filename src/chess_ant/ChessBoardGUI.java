// package chess_ant;
// import javax.swing.JOptionPane;

// public class ChessBoardGUI {
//     public static void main(String[] args) {
//         int bot;
//         boolean playersChoice = false; // Ban đầu, không có lựa chọn của người chơi
//         while (!playersChoice) { // Lặp cho đến khi người chơi chọn màu quân
//             String[] options = { "Trắng", "Đen" };
//             int result = JOptionPane.showOptionDialog(null, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
//                     JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
//             if (result == JOptionPane.YES_OPTION) {
//                 bot = 1;
//                 Player.main(1);
//                 Bot.Bot(1);
//                 playersChoice = true; // Đặt cờ để thoát khỏi vòng lặp
//             } else if (result == JOptionPane.NO_OPTION) {
//                 bot = -1;
//                 Player.main(-1);
//                 Bot.Bot(-1);
//                 playersChoice = true; // Đặt cờ để thoát khỏi vòng lặp
//             } else {
//                 JOptionPane.showMessageDialog(null, "Vui lòng chọn màu quân cờ!");
//             }
//         }
//     }
// }


package chess_ant;

import javax.swing.JOptionPane;

public class ChessBoardGUI {
    public static void main(String[] args) {
        boolean playersChoice = false; 
        while (!playersChoice) { 
            String[] options = { "Trắng", "Đen", "Hủy" }; 
            int result = JOptionPane.showOptionDialog(null, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
                    JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
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
