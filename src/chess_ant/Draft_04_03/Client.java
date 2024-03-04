package chess_ant.Draft_04_03;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;

public class Client extends Frame implements ActionListener {
    private Button[][] buttons;
    private PrintWriter out;
    private BufferedReader in;
    private boolean myTurn = false;

    public Client() {
        super("Tic Tac Toe");
        buttons = new Button[3][3];
        setLayout(new GridLayout(3, 3));

        try {
            Socket socket = new Socket("localhost", 12345);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            String player = in.readLine();
            if (player.equals("Player 1")) {
                myTurn = true;
            }

            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    buttons[i][j] = new Button("");
                    buttons[i][j].addActionListener(this);
                    add(buttons[i][j]);
                }
            }

            setSize(300, 300);
            setVisible(true);

            while (true) {
                String message = in.readLine();
                if (message != null) {
                    if (message.equals("win")) {
                        System.out.println("You win!");
                        break;
                    } else if (message.equals("lose")) {
                        System.out.println("You lose!");
                        break;
                    } else if (message.equals("draw")) {
                        System.out.println("Draw!");
                        break;
                    } else if (message.startsWith("valid")) {
                        String[] parts = message.split(",");
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);
                        buttons[row][col].setLabel("X");
                        myTurn = false;
                    } else if (message.startsWith("opponent")) {
                        String[] parts = message.split(",");
                        int row = Integer.parseInt(parts[1]);
                        int col = Integer.parseInt(parts[2]);
                        buttons[row][col].setLabel("O");
                        myTurn = true;
                    } else if (message.equals("invalid")) {
                        System.out.println("Invalid move. Try again.");
                        myTurn = true;
                    }
                }
            }

            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void actionPerformed(ActionEvent e) {
        if (myTurn) {
            Button button = (Button) e.getSource();
            int row = -1, col = -1;
            outerLoop:
            for (int i = 0; i < 3; i++) {
                for (int j = 0; j < 3; j++) {
                    if (buttons[i][j] == button) {
                        row = i;
                        col = j;
                        break outerLoop;
                    }
                }
            }
            out.println(row + "," + col);
            myTurn = false;
        }
    }

    public static void main(String[] args) {
        new Client();
    }
}
