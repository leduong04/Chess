package chess_ant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class EditUserDialogMain extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;
    private JPasswordField confirmPasswordField; // Thêm confirmPasswordField
    private JTextField emailField;
    private int userId;
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/projectjava";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "";

    public EditUserDialogMain(JFrame parent, int userId) {
        super(parent, "Sửa thông tin người dùng", true);
        this.userId = userId;
        setSize(300, 250); // Tăng kích thước để chứa confirmPasswordField
        setLocationRelativeTo(parent);
        initComponents();
    }

    private void initComponents() {
        JPanel panel = new JPanel(new GridLayout(5, 2)); // Thay đổi layout để chứa confirmPasswordField

        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField(15);
        panel.add(usernameLabel);
        panel.add(usernameField);

        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField(15);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JLabel confirmPasswordLabel = new JLabel("Xác nhận mật khẩu:"); // Label cho confirmPasswordField
        confirmPasswordField = new JPasswordField(15);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);

        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField(15);
        panel.add(emailLabel);
        panel.add(emailField);

        JButton saveButton = new JButton("Lưu");
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveChanges();
                dispose();
            }
        });
        panel.add(saveButton);

        JButton cancelButton = new JButton("Hủy");
        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        panel.add(cancelButton);

        add(panel);
    }

    private void saveChanges() {
        try (Connection connection = DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD)) {
            String newPassword = new String(passwordField.getPassword()); 
            String confirmedPassword = new String(confirmPasswordField.getPassword()); 

            if (!newPassword.equals(confirmedPassword)) { 
                JOptionPane.showMessageDialog(this, "Mật khẩu nhập lại không trùng khớp!");
                return;
            }

            StringBuilder sql = new StringBuilder("UPDATE users SET ");
            boolean hasChanges = false;

            if (!usernameField.getText().isEmpty()) {
                sql.append("username = ?, ");
                hasChanges = true;
            }

            if (newPassword.length() > 0) { 
                sql.append("password = ?, ");
                hasChanges = true;
            }

            if (!emailField.getText().isEmpty()) {
                sql.append("email = ?, ");
                hasChanges = true;
            }

            if (hasChanges) {
                sql.deleteCharAt(sql.length() - 2);
                sql.append("WHERE userid = ?");
                
                try (PreparedStatement preparedStatement = connection.prepareStatement(sql.toString())) {
                    int parameterIndex = 1;
                    if (!usernameField.getText().isEmpty()) {
                        preparedStatement.setString(parameterIndex++, usernameField.getText());
                    }
                    if (newPassword.length() > 0) { 
                        preparedStatement.setString(parameterIndex++, newPassword);
                    }
                    if (!emailField.getText().isEmpty()) {
                        preparedStatement.setString(parameterIndex++, emailField.getText());
                    }
                    preparedStatement.setInt(parameterIndex, userId);
                    
                    int rowsUpdated = preparedStatement.executeUpdate();
                    if (rowsUpdated > 0) {
                        JOptionPane.showMessageDialog(this, "Thông tin đã được cập nhật thành công!");
                    } else {
                        JOptionPane.showMessageDialog(this, "Không thể cập nhật thông tin người dùng.");
                    }
                }
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Sửa thông tin người dùng");
        frame.setSize(400, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        EditUserDialogMain dialog = new EditUserDialogMain(frame, isLoggedin.isLoggedin()); 

        dialog.setVisible(true);
    }
}
