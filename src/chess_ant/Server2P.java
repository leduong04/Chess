package chess_ant;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server2P {
    private static final int SERVER_PORT = 12345;
    private static List<ClientHandler> clients = new ArrayList<>();

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

                // Tạo và lưu trữ một cặp client mới
                ClientHandler clientHandler = new ClientHandler(clientSocket_1, inputStream_1, outputStream_1, clientSocket_2, inputStream_2, outputStream_2);
                clients.add(clientHandler);

                // Bắt đầu xử lý cặp client mới
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
                outputStream2.writeObject("2");

                String message;
                while (true) {
                    // Đọc tin nhắn từ client thứ nhất và gửi cho client thứ 2
                    if ((message = (String) inputStream1.readObject()) != null) {
                        System.out.println("Client 1: " + message);
                        outputStream2.writeObject(message);
                    }

                    // Đọc tin nhắn từ client thứ 2 và gửi cho client thứ nhất
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

