package chess_ant;

import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JOptionPane;

public class LogOut {
    public static void main(String[] args) {
        // Đường dẫn của file cần xóa dữ liệu
        String filePath = "src\\chess_ant\\login_info.txt";

        // Thực hiện xóa dữ liệu trong file
        if (clearFileData(filePath)) {
            // Hiển thị thông báo khi xóa dữ liệu thành công
            JOptionPane.showMessageDialog(null, "Đăng xuất thành công");
        } else {
            // Hiển thị thông báo khi không thể xóa dữ liệu
            JOptionPane.showMessageDialog(null, "Không thể đăng xuất", "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Hàm để xóa dữ liệu trong file
    private static boolean clearFileData(String filePath) {
        try (FileWriter writer = new FileWriter(filePath)) {
            // Ghi dữ liệu rỗng vào file để xóa dữ liệu hiện có
            writer.write("");
            return true; // Trả về true nếu xóa dữ liệu thành công
        } catch (IOException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi xảy ra
        }
    }
}
