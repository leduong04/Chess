package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class clientFile {
    // Port của server
    private static final int SERVER_PORT = 12345;
    // Địa chỉ IP của server
    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) {
        try {
            // Kết nối tới server thông qua địa chỉ IP và cổng
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server.");

            // Đọc file từ ổ đĩa
            File file = new File("C:\\Users\\Home\\Desktop\\CNXHKH.pptx"); 
            // Mảng byte để lưu dữ liệu từ file
            byte[] fileBytes = new byte[(int) file.length()]; 
            // Luồng đầu vào để đọc file
            FileInputStream fileInputStream = new FileInputStream(file); 
            // Luồng đầu vào được lưu vào bộ đệm
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream); 
            // Đọc dữ liệu từ file vào mảng byte
            bufferedInputStream.read(fileBytes, 0, fileBytes.length); 

            // Gửi dữ liệu của file tới server
            OutputStream outputStream = socket.getOutputStream(); // Luồng đầu ra tới server
            // Ghi dữ liệu của file vào luồng đầu ra
            outputStream.write(fileBytes, 0, fileBytes.length); 
            // Xả bộ đệm để đảm bảo dữ liệu được gửi đến server
            outputStream.flush(); 
            System.out.println("File sent to server.");

            // Đóng kết nối sau khi hoàn thành
            socket.close();
        } catch (IOException e) {
            // Xử lý ngoại lệ nếu có lỗi xảy ra
            e.printStackTrace();
        }
    }
}
