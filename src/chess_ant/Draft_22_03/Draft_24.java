package chess_ant.Draft_22_03;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

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
            JOptionPane.showMessageDialog(this, "You chose to play with another human.");
            // gọi hàm chơi với người
            Player.main(null);
        } else if (e.getSource() == playWithBotButton) {
            // Remove the title
            setTitle("");
            System.out.println("fuck............................");
        }
    }

    public void openChessBoard() {
        ChessBoardInterface chessBoard = new ChessBoardInterface();
        chessBoard.setVisible(true);
        this.dispose();
    }

    public static void main(String[] args) {
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
