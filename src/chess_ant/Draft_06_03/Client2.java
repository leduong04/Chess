package chess_ant.Draft_06_03;

import java.io.*;
import java.net.*;

public class Client2 {
    private static long lastModifiedTime = 0;
    private static String previousBoardState;
    public static void main(String[] args) {
        try {
            Socket socket = new Socket("localhost", 12345); // Kết nối tới máy localhost, cổng 12345

            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));

            // System.out.println("Đã kết nối tới server. Bắt đầu gửi tin nhắn:");

            // Tạo một luồng mới để đọc dữ liệu từ server
            Thread receiveThread = new Thread(() -> {
                try {
                    while (true) {
                        String message = fromServer.readLine();
                        if (message != null) {
                            // System.out.println("Server: " + message);
                            writeToFile(message);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receiveThread.start();
            
            String message;
            while (true) {
                previousBoardState = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
                if (isBoardStateChanged(previousBoardState)) {
                    message=readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
                    toServer.println(message);
                    writeToFile(message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void writeToFile(String data) {
        try {
            System.out.println(data);
            // Xóa file board.txt nếu tồn tại
            File file = new File("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
            if (file.exists()) {
                file.delete();
            }
    
            // Tạo một đối tượng FileWriter để ghi vào file "board.txt"
            FileWriter writer = new FileWriter(file);
            BufferedWriter bufferWriter = new BufferedWriter(writer);
            bufferWriter.write(data); // Ghi dữ liệu vào buffer
            // bufferWriter.newLine(); // Thêm một dòng mới
            bufferWriter.close(); // Đóng buffer để giải phóng tài nguyên
            // System.out.println("Đã ghi dữ liệu mới vào file.");
        } catch (IOException e) {
            System.out.println("Xảy ra lỗi khi ghi vào file: " + e.getMessage());
        }
    }

    public static String readBoardFromFile(String filePath) {
        StringBuilder boardContent = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                boardContent.append(line.trim());
                boardContent.append(System.lineSeparator());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return boardContent.toString();
    }

    // private static boolean isBoardStateChanged(String currentBoardState) {
    //     if (previousBoardState == null || !currentBoardState.equals(previousBoardState)) {
    //         previousBoardState = currentBoardState;
    //         System.out.println("Fuck");
    //         return true;
    //     }
    //     System.out.println("No fuck");
    //     return false;
    // }

    private static boolean isBoardStateChanged(String currentBoardState) {
        String newBoardState = readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\Drat_4_3\\board2.txt");
        if (!currentBoardState.equals(newBoardState)) {
            previousBoardState = newBoardState;
            System.out.println("Fuck");
            return true;
        }
        System.out.println("No fuck");
        return false;
    }
    
}
