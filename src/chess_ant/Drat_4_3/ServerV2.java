package chess_ant.Drat_4_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ServerV2 {
    private static final int SERVER_PORT = 12345;
    private static List<ClientHandler> clients = new ArrayList<>();

    private static boolean insertGameData(String player1id, String player2id) {
        boolean success = false;
        try (Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/chess", "root",
                "")) {
            String sql = "INSERT INTO games (player1id, player2id) VALUES (?, ?)";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
                pstmt.setString(1, player1id);
                pstmt.setString(2, player2id);
                int rowsAffected = pstmt.executeUpdate();
                if (rowsAffected > 0) {
                    success = true;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return success;
    }

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");

            while (true) {
                Socket clientSocket_1 = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket_1.getInetAddress().getHostAddress());
                ObjectInputStream inputStream_1 = new ObjectInputStream(clientSocket_1.getInputStream());
                ObjectOutputStream outputStream_1 = new ObjectOutputStream(clientSocket_1.getOutputStream());

                Socket clientSocket_2 = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket_2.getInetAddress().getHostAddress());
                ObjectInputStream inputStream_2 = new ObjectInputStream(clientSocket_2.getInputStream());
                ObjectOutputStream outputStream_2 = new ObjectOutputStream(clientSocket_2.getOutputStream());

                ClientHandler clientHandler = new ClientHandler(clientSocket_1, inputStream_1, outputStream_1,
                        clientSocket_2, inputStream_2, outputStream_2);
                clients.add(clientHandler);

                Thread thread = new Thread(clientHandler);
                thread.start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    static class ClientHandler implements Runnable {
        private Socket clientSocket1;
        private ObjectInputStream inputStream1;
        private ObjectOutputStream outputStream1;
        private Socket clientSocket2;
        private ObjectInputStream inputStream2;
        private ObjectOutputStream outputStream2;

        public ClientHandler(Socket clientSocket1, ObjectInputStream inputStream1, ObjectOutputStream outputStream1,
                Socket clientSocket2, ObjectInputStream inputStream2, ObjectOutputStream outputStream2) {
            this.clientSocket1 = clientSocket1;
            this.inputStream1 = inputStream1;
            this.outputStream1 = outputStream1;
            this.clientSocket2 = clientSocket2;
            this.inputStream2 = inputStream2;
            this.outputStream2 = outputStream2;
        }

        @Override
        public void run() {
            try {

                outputStream1.writeObject("1");
                String player1id = (String) inputStream1.readObject();
                System.out.println("player1id=" + player1id);

                outputStream2.writeObject("2");
                String player2id = (String) inputStream2.readObject();
                System.out.println("player2id=" + player2id);

                outputStream2.writeObject(player1id);
                outputStream1.writeObject(player2id);

                if (insertGameData(player1id, player2id)) {
                    System.out.println("Insert thành công!");
                } else {
                    System.out.println("Insert thất bại!");
                }

                String message;
                while (true) {
                    if ((message = (String) inputStream1.readObject()) != null) {
                        System.out.println("Client 1: " + message);
                        outputStream2.writeObject(message);
                    }

                    if ((message = (String) inputStream2.readObject()) != null) {
                        System.out.println("Client 2: " + message);
                        outputStream1.writeObject(message);
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            } finally {
                try {
                    clientSocket1.close();
                    clientSocket2.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
