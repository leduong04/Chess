package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class serverFile {
    // Port mà server lắng nghe kết nối từ client
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            // Tạo một ServerSocket để lắng nghe các kết nối từ client
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");

            while (true) {
                // Chấp nhận kết nối từ client và tạo một socket để truy cập dữ liệu
                Socket socket = serverSocket.accept();
                System.out.println("Client connected: " + socket.getInetAddress().getHostAddress());

                // Đọc dữ liệu từ client và lưu vào file cục bộ
                byte[] fileBytes = new byte[1024]; // Mảng byte để lưu dữ liệu từ client
                // Luồng đầu vào từ client
                InputStream inputStream = socket.getInputStream(); 
                // Luồng đầu ra để lưu file
                FileOutputStream fileOutputStream = new FileOutputStream("D:/Downloads/Draft/Java/draft.pptx"); 
                // Luồng đầu ra được lưu vào bộ đệm
                BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream); 
                int bytesRead;
                // Đọc dữ liệu từ client và ghi vào file cục bộ
                while ((bytesRead = inputStream.read(fileBytes, 0, fileBytes.length)) != -1) {
                    // Ghi dữ liệu vào file cục bộ
                    bufferedOutputStream.write(fileBytes, 0, bytesRead); 
                }
                 // Xả bộ đệm để đảm bảo dữ liệu được ghi vào file
                bufferedOutputStream.flush();
                System.out.println("File received from client and saved.");

                // Đóng kết nối và các luồng sau khi hoàn thành
                bufferedOutputStream.close();
                fileOutputStream.close();
                inputStream.close();
                socket.close();
            }
        } catch (IOException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
    }
}
