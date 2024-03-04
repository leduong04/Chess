package chess_ant.Draft_04_03;

import javax.swing.*;
import java.awt.BorderLayout;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TestServer {
    private static List<ClientHandler> clients = new ArrayList<>();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Server");
        JButton startButton = new JButton("Start Server");
        JTextArea logTextArea = new JTextArea();

        startButton.addActionListener(e -> {
            startButton.setEnabled(false);
            logTextArea.append("Server is running\n");
            new Thread(() -> startServer(logTextArea)).start();
        });

        frame.getContentPane().add(BorderLayout.NORTH, startButton);
        frame.getContentPane().add(BorderLayout.CENTER, new JScrollPane(logTextArea));
        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void startServer(JTextArea logTextArea) {
        try {
            int port = 12345;
            ServerSocket serverSocket = new ServerSocket(port);
            logTextArea.append("Server is listening on port " + port + "\n");

            while (true) {
                Socket clientSocket = serverSocket.accept();
                logTextArea.append("New client connected\n");

                ClientHandler clientHandler = new ClientHandler(clientSocket, logTextArea);
                clients.add(clientHandler);
                new Thread(clientHandler).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static synchronized void matchPlayers(ClientHandler clientHandler) {
        for (ClientHandler otherClient : clients) {
            if (otherClient != clientHandler && !otherClient.isInGame()) {
                clientHandler.setInGame(true);
                otherClient.setInGame(true);

                clientHandler.sendMessage("Match found! Your opponent is: " + otherClient.getClientName());
                otherClient.sendMessage("Match found! Your opponent is: " + clientHandler.getClientName());
                return;
            }
        }
    }

    public static synchronized void broadcastMessage(ClientHandler sender, String message) {
        for (ClientHandler otherClient : clients) {
            if (otherClient != sender) {
                otherClient.sendMessage(sender.getClientName() + ": " + message);
            }
        }
    }

    // Inner class for ClientHandler
    private static class ClientHandler implements Runnable {
        private Socket clientSocket;
        private BufferedReader input;
        private PrintWriter output;
        private String clientName;
        private boolean inGame;
        private JTextArea logTextArea;  // Added JTextArea for logging

        public ClientHandler(Socket socket, JTextArea logTextArea) {
            try {
                this.clientSocket = socket;
                this.input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                this.output = new PrintWriter(socket.getOutputStream(), true);
                this.inGame = false;
                this.clientName = input.readLine(); // Read client name
                this.logTextArea = logTextArea;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        public String getClientName() {
            return clientName;
        }

        public boolean isInGame() {
            return inGame;
        }

        public void setInGame(boolean inGame) {
            this.inGame = inGame;
        }

        public void sendMessage(String message) {
            output.println(message);
        }

        @Override
        public void run() {
            try {
                TestServer.matchPlayers(this);

                while (true) {
                    String message = input.readLine();
                    if (message != null) {
                        TestServer.broadcastMessage(this, message);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
