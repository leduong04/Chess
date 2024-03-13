package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class serverFile {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

                // Đọc file từ client
                byte[] fileBytes = new byte[1024];
                InputStream inputStream = socket.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream("D:/Downloads/Draft/Java/dmm222.pptx");
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream);
                int bytesRead;
                while ((bytesRead = inputStream.read(fileBytes, 0, fileBytes.length)) != -1) {
                    bufferedOutputStream.write(fileBytes, 0, bytesRead);
                }
                bufferedOutputStream.flush();
                System.out.println("File received from client and saved.");

                // Đóng kết nối
                socket.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

