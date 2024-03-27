package chess_ant.Draft_22_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Draft_24 extends JFrame implements ActionListener {
    private JButton playWithHumanButton;
    private JButton playWithBotButton;

    public Draft_24() {
        setTitle("Game Selection");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        playWithHumanButton = new JButton("Play with Human");
        playWithBotButton = new JButton("Play with Bot");
        playWithHumanButton.addActionListener(this);
        playWithBotButton.addActionListener(this);
        add(playWithHumanButton);
        add(playWithBotButton);
        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == playWithHumanButton) {
            // if (isLoggedIn() == 0) {
            //     showLoginDialog();
            // } else {
            //     Player.main(null);
            // }

            while(isLoggedIn()==0)
            {
                showLoginDialog();
                if(isLoggedIn()!=0)
                {
                    break;
                }
            }
            Player.main(null);
        } else if (e.getSource() == playWithBotButton) {
            // Remove the title
            setTitle("");
            System.out.println("Playing with bot...");
        }
    }

    private int isLoggedIn() {
        // Xác định đường dẫn đến tệp lưu trữ thông tin đăng nhập
        String filePath = "src\\chess_ant\\Draft_22_03\\login_info.txt";

        try {
            File file = new File(filePath);

            // Kiểm tra xem tệp tồn tại và có thể đọc được không
            if (file.exists() && file.canRead()) {
                BufferedReader reader = new BufferedReader(new FileReader(file));
                String line;

                // Đọc dòng đầu tiên từ tệp, đại diện cho userid
                if ((line = reader.readLine()) != null && !line.trim().isEmpty()) {
                    // Chuyển đổi dòng đọc được thành số nguyên và trả về
                    return Integer.parseInt(line.trim());
                }

                reader.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (NumberFormatException ex) {
            // Xử lý nếu không thể chuyển đổi thành số nguyên
            ex.printStackTrace();
        }

        // Trả về 0 nếu có bất kỳ lỗi nào xảy ra hoặc không tìm thấy thông tin đăng nhập
        return 0;
    }

    


    private void showLoginDialog() {
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "root", "")) {
            // Show login dialog
            JOptionPane.showMessageDialog(this, "Please log in to play with another human.");
            // Instantiate LoginDialog
            LoginDialog loginDialog = new LoginDialog(this, conn);
            loginDialog.setVisible(true); // Hiển thị dialog
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToXAMPP.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    

    public void openChessBoard() {
        ChessBoardInterface chessBoard = new ChessBoardInterface();
        chessBoard.setVisible(true);
        this.dispose();
    }

    public static int checkLogin(Connection conn, String username, String password) {
        String sql = "SELECT userid FROM users WHERE username = ? AND password = ?";
        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Nếu có kết quả từ truy vấn, trả về userid của người chơi

                    int userId = rs.getInt("userid");
                    writeUserIDToFile(userId);
                    return rs.getInt("userid");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ConnectToXAMPP.class.getName()).log(Level.SEVERE, null, ex);
        }
        // Nếu có lỗi hoặc không có kết quả từ truy vấn, trả về 0 để chỉ định rằng đăng
        // nhập thất bại
        return 0;
    }

    private static void writeUserIDToFile(int userId) {
        String filePath = "src\\chess_ant\\Draft_22_03\\login_info.txt";
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(String.valueOf(userId));
        } catch (IOException ex) {
            // Hiển thị thông báo nếu có lỗi xảy ra khi ghi vào tệp
            JOptionPane.showMessageDialog(null, "An error occurred while writing to file.");
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        

        // try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "root", "")) {
        //     System.out.println("Kết nối MySQL thành công!");

        //     SwingUtilities.invokeLater(new Runnable() {
        //         public void run() {
        //             new Draft_24();
        //         }
        //     });
            

        // } catch (SQLException ex) {
        //     Logger.getLogger(ConnectToXAMPP.class.getName()).log(Level.SEVERE, null, ex);
        // }
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Draft_24();
            }
        });
    }
}

class ChessBoardInterface extends JFrame {
    public ChessBoardInterface() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
    }
}

class LoginDialog extends JDialog {
    private Connection conn;

    public LoginDialog(JFrame parent, Connection conn) {
        super(parent, "Login", true);
        this.conn = conn;

        setSize(300, 150);
        setLocationRelativeTo(parent);

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Login");

        JPanel panel = new JPanel(new GridLayout(3, 2));
        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                int userId = Draft_24.checkLogin(conn, username, password);

                if (userId != 0) {
                    // Login successful, do something here
                    JOptionPane.showMessageDialog(LoginDialog.this, "Login successful. UserId: " + userId);
                    dispose();
                } else {
                    // Login failed
                    JOptionPane.showMessageDialog(LoginDialog.this, "Login failed. Please try again.");
                }
            }
        });

        add(panel);
        setVisible(true);
    }
}
