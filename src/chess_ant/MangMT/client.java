package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class client {
    // Địa chỉ IP của server
    private static String SERVER_ID = "192.168.76.101";
    // Cổng mà server lắng nghe kết nối
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            // Kết nối đến server thông qua địa chỉ IP và cổng đã chỉ định
            Socket socket = new Socket(SERVER_ID, SERVER_PORT);

            // Tạo luồng đầu vào và đầu ra để giao tiếp với server
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

            // Tạo luồng đầu vào từ bàn phím để nhập dữ liệu từ người dùng
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));

            String message;

            //Nhập thông báo là client 1 hay 2
            String turn = fromServer.readLine();
            System.out.println("You are client " + turn);
            
            //Nếu là 1 thì nhập tin nhắn trước
            while (turn.equals("1")) {
                System.out.print("Enter message:");
                if ((message = fromUser.readLine()) != null) {
                    toServer.println(message);
                }

                if ((message = fromServer.readLine()) != null) {
                    System.out.println("From your friend: " + message);
                }

            }
            //Nếu là 2 thì nhận tin nhắn trước
            while (turn.equals("2")) {
                if ((message = fromServer.readLine()) != null) {
                    System.out.println("From your friend: " + message);
                }
                System.out.print("Enter message:");
                if ((message = fromUser.readLine()) != null) {
                    toServer.println(message);
                }
            }

            socket.close();

        }

        catch (IOException e) {
            System.out.println("connection failed");
        }

    }
}