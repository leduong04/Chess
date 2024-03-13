package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class clientFile {
    private static final int SERVER_PORT = 12345;
    private static final String SERVER_IP = "localhost";

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, SERVER_PORT);
            System.out.println("Connected to server.");

            // Đọc file từ ổ đĩa
            File file = new File("C:\\Users\\Home\\Desktop\\CNXHKH.pptx");
            byte[] fileBytes = new byte[(int) file.length()];
            FileInputStream fileInputStream = new FileInputStream(file);
            BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream);
            bufferedInputStream.read(fileBytes, 0, fileBytes.length);

            // Gửi file tới server
            OutputStream outputStream = socket.getOutputStream();
            outputStream.write(fileBytes, 0, fileBytes.length);
            outputStream.flush();
            System.out.println("File sent to server.");

            // Đóng kết nối
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

