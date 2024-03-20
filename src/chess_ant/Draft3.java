package chess_ant;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Draft3 extends JFrame implements ActionListener {
    private JButton playWithHumanButton;
    private JButton playWithBotButton;

    public Draft3() {
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
            // Add code here to start game with human
        } else if (e.getSource() == playWithBotButton) {
            // Remove the title
            setTitle("");
            // Open chess board interface
            // openChessBoard();
            Draft2.main(-1);
            // Bot.Bot(-1);
        }
    }

    // Define function to open chess board interface
    public void openChessBoard() {
        // Create and display the chess board interface
        ChessBoardInterface chessBoard = new ChessBoardInterface();
        chessBoard.setVisible(true);
        // Close the current frame (Game Selection)
        this.dispose();
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                new Draft3();
            }
        });
    }
}

class ChessBoardInterface extends JFrame {
    public ChessBoardInterface() {
        setTitle("Chess Game");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add chess board components here
        
        setVisible(true);
    }
}
