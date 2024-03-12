package chess_ant.Draft_07_03;

import java.net.*;
import java.io.*;

public class server {
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            // Chấp nhận kết nối từ client đầu tiên
            Socket client1Socket = serverSocket.accept();

            // Tạo luồng vào và ra cho client đầu tiên
            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
            PrintWriter toClient1 = new PrintWriter(client1Socket.getOutputStream(), true);

            // Gửi thông điệp "1" cho client đầu tiên
            toClient1.println("1");

            // Chấp nhận kết nối từ client thứ hai
            Socket client2Socket = serverSocket.accept();

            // Tạo luồng vào và ra cho client thứ hai
            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
            PrintWriter toClient2 = new PrintWriter(client2Socket.getOutputStream(), true);

            toClient2.println("2");
            // Bắt đầu trao đổi tin nhắn giữa hai client
            String message;
            while (true) {
                // Đọc tin nhắn từ client đầu tiên và gửi cho client thứ hai
                if ((message = fromClient1.readLine()) != null) {
                    System.out.println(message);
                    toClient2.println(message);
                }

                // Đọc tin nhắn từ client thứ hai và gửi cho client đầu tiên
                if ((message = fromClient2.readLine()) != null) {
                    System.out.println(message);
                    toClient1.println(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
