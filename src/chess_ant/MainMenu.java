package chess_ant;


import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainMenu extends JFrame implements ActionListener {
    private JButton loginButton;
    private JButton playWithBotButton;
    private JButton playWithRandomPlayerButton;

    public MainMenu() {
        setTitle("Main Menu");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridLayout(3, 1));

        loginButton = new JButton("Đăng nhập");
        playWithBotButton = new JButton("Chơi với máy");
        playWithRandomPlayerButton = new JButton("Chơi với người ngẫu nhiên");

        loginButton.addActionListener(this);
        playWithBotButton.addActionListener(this);
        playWithRandomPlayerButton.addActionListener(this);

        add(loginButton);
        add(playWithBotButton);
        add(playWithRandomPlayerButton);

        setVisible(true);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginButton) {
            // Thực hiện đăng nhập
            if (isLoggedIn()) {
                JOptionPane.showMessageDialog(this, "Bạn đã đăng nhập.");
                // Đóng form đăng nhập sau khi đăng nhập thành công
                disposeLoginDialog();
            } else {
                // Hiển thị giao diện đăng nhập
                showLoginDialog();
            }
        } else if (e.getSource() == playWithBotButton) {
            // Xử lý khi nhấn nút Chơi với máy
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
            // Xử lý khi nhấn nút Chơi với người ngẫu nhiên
            if(isLoggedIn())
            {
                JOptionPane.showMessageDialog(this, "Bạn đã chọn Chơi với người ngẫu nhiên.");
                Player2P.main(null);
            }
            else{
                JOptionPane.showMessageDialog(this, "Hãy đăng nhập và chọn lại sau");
                showLoginDialog();
            }
        }
    }

    private boolean isLoggedIn() {
        return isLoggedin.isLoggedin() != 0;
    }

    private void showLoginDialog() {
        // Hiển thị form đăng nhập
        LoginDialog loginDialog = new LoginDialog(this);
        loginDialog.setVisible(true);
    }

    private void disposeLoginDialog() {
        // Đóng form đăng nhập
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
                
                // Gọi hàm LogIn() để thực hiện đăng nhập
                int loggedIn = LogIn.LogIn(username, password);

                if (loggedIn!=0) {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Đăng nhập thành công!");
                    dispose(); // Đóng form đăng nhập
                } else {
                    JOptionPane.showMessageDialog(LoginDialog.this, "Đăng nhập thất bại! Vui lòng kiểm tra tên đăng nhập và mật khẩu.");
                }
            }
        });

        add(panel);
    }
}

