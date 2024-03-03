package chess_ant;
import javax.swing.*;


    
    public class Draft {
        public static void main(String[] args) {
            String input = "1. e4 d5 2. exd5 Qxd5 3. Nc3 Qa5 4. d4 c6 5. Nf3 Bf5 6. Bd3 Bxd3 7. Qxd3 e6";
    
            // Tìm vị trí của dấu chấm cuối cùng
            int lastDotIndex = input.lastIndexOf('.');
    
            // Kiểm tra nếu không tìm thấy dấu chấm
            if (lastDotIndex == -1) {
                System.out.println("Không tìm thấy dấu chấm trong chuỗi.");
                return;
            }
    
            // Lấy phần số trước dấu chấm cuối cùng
            String beforeLastDot = input.substring(0, lastDotIndex);
    
            // Tìm vị trí của dấu cách cuối cùng trong phần số trước dấu chấm cuối cùng
            int lastSpaceIndex = beforeLastDot.lastIndexOf(' ');
    
            // Kiểm tra nếu không tìm thấy dấu cách
            if (lastSpaceIndex == -1) {
                System.out.println("Không tìm thấy dấu cách trong chuỗi số.");
                return;
            }
    
            // Lấy phần số đứng trước dấu chấm cuối cùng
            String numberString = beforeLastDot.substring(lastSpaceIndex + 1);
    
            // Kiểm tra xem chuỗi số có chứa các ký tự số không
            if (isNumeric(numberString)) {
                System.out.println("Số đứng trước dấu chấm cuối cùng: " + numberString);
            } else {
                System.out.println("Không có số đứng trước dấu chấm cuối cùng.");
            }
        }
    
        // Phương thức kiểm tra xem một chuỗi có chứa toàn ký tự số không
        public static boolean isNumeric(String str) {
            return str.matches("-?\\d+(\\.\\d+)?");
        }
    }
    
