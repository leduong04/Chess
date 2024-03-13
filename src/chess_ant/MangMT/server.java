package chess_ant.MangMT;

import java.net.*;
import java.io.*;

public class server {
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            // Tạo một ServerSocket để lắng nghe kết nối từ client trên cổng 12345
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            System.out.println("SERVER IS RUNNING");

            // Chấp nhận kết nối từ client thứ nhất
            Socket client1Socket = serverSocket.accept();

            // Tạo luồng đầu vào và đầu ra cho client thứ nhất
            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
            PrintWriter toClient1 = new PrintWriter(client1Socket.getOutputStream(), true);

            // Thông báo rằng đây là client 1
            toClient1.println("1");

            // Chấp nhận kết nối từ client thứ 2
            Socket client2Socket = serverSocket.accept();

            // Tạo luồng vào ra cho client thứ 2
            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
            PrintWriter toClient2 = new PrintWriter(client2Socket.getOutputStream(), true);

            // Thông báo rằng đây là client 2
            toClient2.println("2");

            String message;
            while (true) {
                // Đọc tin nhắn từ client thứ nhất và gửi cho client thứ 2
                if ((message = fromClient1.readLine()) != null) {
                    System.out.println(message);
                    toClient2.println(message);
                }
                // Đọc tin nhắn từ client thứ 2 và gửi cho client thứ nhất
                if ((message = fromClient2.readLine()) != null) {
                    System.out.println(message);
                    toClient1.println(message);
                }

                // Kiểm tra xem một trong hai client đã đóng kết nối chưa
                if (client1Socket.isClosed() || client2Socket.isClosed()) {
                    break;
                }
            }
            
            // Đóng kết nối và socket khi kết thúc
            client1Socket.close();
            client2Socket.close();
            serverSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
