package chess_ant.Draft_22_03;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.swing.JOptionPane;

public class LogIn {

    public static int LogIn(String username, String password) {
        // Thông tin kết nối đến cơ sở dữ liệu MySQL
        String url = "jdbc:mysql://localhost:3306/chess";
        String user = "root";
        String pass = "";

        // Biến lưu trữ ID của người dùng đăng nhập
        int userId = 0;

        // Thực hiện kết nối đến cơ sở dữ liệu
        try (Connection conn = DriverManager.getConnection(url, user, pass)) {
            // Chuẩn bị truy vấn SQL để kiểm tra thông tin đăng nhập
            String sql = "SELECT userid FROM users WHERE username = ? AND password = ?";
            try (PreparedStatement stmt = conn.prepareStatement(sql)) {
                // Thay thế các tham số trong truy vấn với thông tin đăng nhập cụ thể
                stmt.setString(1, username);
                stmt.setString(2, password);

                // Thực thi truy vấn và lấy kết quả
                try (ResultSet rs = stmt.executeQuery()) {
                    // Kiểm tra xem có kết quả trả về từ truy vấn không
                    if (rs.next()) {
                        // Nếu có, lấy ID của người dùng
                        userId = rs.getInt("userid");
                        writeUserIDToFile(userId);
                        return userId;
                        
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Trả về ID của người dùng. Nếu không tìm thấy, userId sẽ vẫn là 0.
        return 0;
    }

        public static void writeUserIDToFile(int userId) {
        String filePath = "src\\chess_ant\\Draft_22_03\\login_info.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(userId));
        } catch (IOException ex) {
            // Hiển thị thông báo nếu có lỗi xảy ra khi ghi vào tệp
            JOptionPane.showMessageDialog(null, "An error occurred while writing to file.");
            ex.printStackTrace();
        }
    }

    
}
