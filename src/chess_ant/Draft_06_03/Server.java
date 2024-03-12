package chess_ant.Draft_06_03;

import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(12345); // Sử dụng cổng 12345
            System.out.println("Server đã khởi động và đang chờ kết nối...");

            // Kết nối client 1
            Socket client1Socket = serverSocket.accept();
            System.out.println("Client 1 đã kết nối.");

            // Kết nối client 2
            Socket client2Socket = serverSocket.accept();
            System.out.println("Client 2 đã kết nối.");

            // Tạo luồng riêng biệt cho client 1
            Thread client1Thread = new Thread(() -> {
                try {
                    BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
                    PrintWriter toClient2 = new PrintWriter(client2Socket.getOutputStream(), true);

                    String message;
                    while ((message = fromClient1.readLine()) != null) {
                        System.out.println("Client 1: " + message);
                        toClient2.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            client1Thread.start();

            // Tạo luồng riêng biệt cho client 2
            Thread client2Thread = new Thread(() -> {
                try {
                    BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
                    PrintWriter toClient1 = new PrintWriter(client1Socket.getOutputStream(), true);

                    String message;
                    while ((message = fromClient2.readLine()) != null) {
                        System.out.println("Client 2: " + message);
                        toClient1.println(message);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            client2Thread.start();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
