package chess_ant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton playWithBotButton;
    private JButton playWithRandomPlayerButton;
    private JButton viewHistoryButton; 
    private JButton viewLeaderboardButton; 
    private JButton logoutButton; 
    private JButton registerButton; // Thêm nút Đăng ký

    public MainMenu() {
        setTitle("CHESS");
        setSize(400, 400); // Thay đổi kích thước cho phù hợp với giao diện mới
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(7, 1)); // Thêm một hàng cho nút Đăng ký

        getContentPane().setBackground(new Color(240, 240, 240));

        loginButton = createStyledButton("Đăng nhập");
        playWithBotButton = createStyledButton("Chơi với máy");
        playWithRandomPlayerButton = createStyledButton("Chơi với người ngẫu nhiên");
        viewHistoryButton = createStyledButton("Xem lịch sử các trận");
        viewLeaderboardButton = createStyledButton("Xem bảng xếp hạng");
        logoutButton = createStyledButton("Đăng xuất"); 
        registerButton = createStyledButton("Đăng ký"); // Khởi tạo nút Đăng ký

        loginButton.addActionListener(this);
        playWithBotButton.addActionListener(this);
        playWithRandomPlayerButton.addActionListener(this);
        viewHistoryButton.addActionListener(this);
        viewLeaderboardButton.addActionListener(this);
        logoutButton.addActionListener(this); 
        registerButton.addActionListener(this); // Thêm sự kiện cho nút Đăng ký

        add(registerButton);
        add(loginButton);
        add(playWithBotButton);
        add(playWithRandomPlayerButton);
        add(viewHistoryButton);
        add(viewLeaderboardButton);
        add(logoutButton); 
        // Thêm nút Đăng ký vào giao diện

        setVisible(true);
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE); 
        button.setBackground(new Color(51, 102, 255)); 
        button.setFont(new Font("Arial", Font.BOLD, 16));
        button.setFocusPainted(false); 
        button.setCursor(new Cursor(Cursor.HAND_CURSOR)); 
        return button;
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == registerButton)
        {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn đăng ký.");
            RegisterForm.main(null);
        }
        else if (e.getSource() == loginButton) {
            if (isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Bạn đã đăng nhập.");
                disposeLoginDialog();
            } else {
                showLoginDialog();
            }
        } else if (e.getSource() == playWithBotButton) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn Chơi với máy.");
            boolean playersChoice = false;
            while (!playersChoice) {
                String[] options = { "Trắng", "Đen", "Hủy" };
                int result = JOptionPane.showOptionDialog(null, "Chọn màu quân cờ bạn muốn chơi", "Chọn màu quân cờ",
                        JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                if (result == 0) {
                    PlayVsAi.main(1);
                    playersChoice = true;
                } else if (result == 1) {
                    PlayVsAi.main(-1);
                    playersChoice = true;
                } else if (result == 2) {
                    System.exit(0);
                }
            }
        } else if (e.getSource() == playWithRandomPlayerButton) {
            if (isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Bạn đã chọn Chơi với người ngẫu nhiên.");
                Player2P.main(null);
            } else {
                JOptionPane.showMessageDialog(this, "Hãy đăng nhập và chọn lại sau");
                showLoginDialog();
            }
        } else if (e.getSource() == viewHistoryButton) {
            if (isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Bạn đã chọn Xem lịch sử các trận.");
                GameDataRetriever.main(null);
            } else {
                JOptionPane.showMessageDialog(this, "Hãy đăng nhập và chọn lại sau");
                showLoginDialog();
            }
        } else if (e.getSource() == viewLeaderboardButton) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn Xem bảng xếp hạng.");
            PlayerRankingGUI.main(null);
        } else if (e.getSource() == logoutButton) {
            JOptionPane.showMessageDialog(this, "Bạn đã chọn Đăng xuất.");
            LogOut.main(null);
        }
    }

    private boolean isLoggedIn() {
        return isLoggedin.isLoggedin() != 0;
    }

    private void showLoginDialog() {
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);
    }

    private void disposeLoginDialog() {
        Window window = SwingUtilities.getWindowAncestor(this);
        if (window instanceof JDialog) {
            JDialog dialog = (JDialog) window;
            dialog.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new MainMenu();
            }
        });
    }
}

class LoginDialog extends JDialog {
    private JTextField usernameField;
    private JPasswordField passwordField;

    public LoginDialog(JFrame parent) {
        super(parent, "Đăng nhập", true);
        setSize(300, 150);
        setLocationRelativeTo(parent);
        JLabel usernameLabel = new JLabel("Tên đăng nhập:");
        usernameField = new JTextField(15);
        JLabel passwordLabel = new JLabel("Mật khẩu:");
        passwordField = new JPasswordField(15);
        JButton loginButton = new JButton("Đăng nhập");
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
                int loggedIn = LogIn.LogIn(username, password);
                if (loggedIn != 0) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Đăng nhập thành công!");
                    dispose(); 
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this,
                            "Đăng nhập thất bại! Vui lòng kiểm tra tên đăng nhập và mật khẩu.");
                }
            }
        });
        add(panel);
    }
}
