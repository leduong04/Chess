package chess_ant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class RegisterForm extends JFrame implements ActionListener {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField;
    private JTextField emailField;
    private JButton registerButton;

    public RegisterForm() {
        setTitle("Register");
        setSize(400, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(6, 2));
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình

        JLabel titleLabel = new JLabel("Đăng Ký");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24)); // Đặt font chữ và kích thước

        JLabel usernameLabel = new JLabel("Username:");
        JLabel passwordLabel = new JLabel("Password:");
        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JLabel emailLabel = new JLabel("Email:");

        usernameField = new JTextField();
        passwordField = new JPasswordField();
        confirmPasswordField = new JPasswordField();
        emailField = new JTextField();

        registerButton = new JButton("Đăng Ký");
        registerButton.addActionListener(this);

        add(titleLabel);
        add(new JLabel());
        add(usernameLabel);
        add(usernameField);
        add(passwordLabel);
        add(passwordField);
        add(confirmPasswordLabel);
        add(confirmPasswordField);
        add(emailLabel);
        add(emailField);
        add(new JLabel());
        add(registerButton);

        getContentPane().setBackground(new Color(240, 240, 240)); // Đặt màu nền
        setVisible(true);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton) {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());
            String email = emailField.getText();

            // Kiểm tra xem mật khẩu và xác nhận mật khẩu có khớp không
            if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this, "Mật khẩu không khớp.");
                return;
            }

            // Kiểm tra định dạng email
            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Địa chỉ email không hợp lệ.");
                return;
            }

            // Thực hiện thêm người dùng vào cơ sở dữ liệu
            if (insertUserIntoDatabase(username, password, email)) {
                JOptionPane.showMessageDialog(this, "Đăng ký thành công.");
                dispose(); // Đóng cửa sổ sau khi đăng ký thành công
            } else {
                JOptionPane.showMessageDialog(this, "Đăng ký không thành công. Vui lòng thử lại.");
            }
        }
    }

    // Hàm kiểm tra định dạng email
    private boolean isValidEmail(String email) {
        // Định dạng email chuẩn
        String emailPattern = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$";
        return email.matches(emailPattern);
    }

    // Hàm thêm người dùng vào cơ sở dữ liệu
    // Hàm thêm người dùng vào cơ sở dữ liệu
private boolean insertUserIntoDatabase(String username, String password, String email) {
    String JDBC_URL = "jdbc:mysql://localhost:3306/projectjava";
    String USERNAME = "root";
    String PASSWORD = "";

    try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
        String sql = "INSERT INTO users (username, password, email, elo) VALUES (?, ?, ?, ?)";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)) {
            preparedStatement.setString(1, username);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, email);
            preparedStatement.setInt(4, 1000); // Thêm Elo 1000 cho người mới đăng ký
            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;
        }
    } catch (SQLException ex) {
        ex.printStackTrace();
        return false;
    }
}


    public static void main(String[] args) {
        SwingUtilities.invokeLater(RegisterForm::new);
    }
}
