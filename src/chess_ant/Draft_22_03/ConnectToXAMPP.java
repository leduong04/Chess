package chess_ant.Draft_22_03;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConnectToXAMPP {

    public static void main(String[] args) {
        String url = "jdbc:mysql://localhost:3306/chess";
        String user = "root";
        String password = "";
        try (Connection conn = DriverManager.getConnection(url, user, password)) {
            System.out.println("Kết nối MySQL thành công!");

            // Tạo câu truy vấn SQL
            String sql = "SELECT * FROM users";

            // Tạo đối tượng Statement
            try (Statement stmt = conn.createStatement();
                 ResultSet rs = stmt.executeQuery(sql)) {

                // Xuất ra thông tin từ bảng users
                while (rs.next()) {
                    int userId = rs.getInt("userid");
                    String username = rs.getString("username");
                    String email = rs.getString("email");
                    int elo = rs.getInt("elo");
                    System.out.println("User ID: " + userId + ", Username: " + username + ", Email: " + email + ", Elo: " + elo);
                }
            } catch (SQLException ex) {
                Logger.getLogger(ConnectToXAMPP.class.getName()).log(Level.SEVERE, null, ex);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ConnectToXAMPP.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}


