package chess_ant.Drat_4_3;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;

public class Server {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");
            Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress().getHostAddress());

                // Tạo luồng đầu vào để đọc các đối tượng từ client
                ObjectInputStream inputStream = new ObjectInputStream(clientSocket.getInputStream());

            while (true) {
                

                // Đọc đối tượng Move từ client
                Move move = (Move) inputStream.readObject();

                // Xử lý thông tin từ client
                int fromRow = move.getFromRow();
                int fromCol = move.getFromCol();
                int toRow = move.getToRow();
                int toCol = move.getToCol();

                // In thông tin từ client
                System.out.println("Received move from client:");
                System.out.println("From Row: " + fromRow + ", From Col: " + fromCol);
                System.out.println("To Row: " + toRow + ", To Col: " + toCol);

                // Thực hiện các hoạt động cần thiết với thông tin nhận được từ client

                // Đóng kết nối với client
                // clientSocket.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
class Move implements Serializable {
    private static final long serialVersionUID = 1L;
    private int fromRow, fromCol, toRow, toCol;

    public Move(int fromRow, int fromCol, int toRow, int toCol) {
        this.fromRow = fromRow;
        this.fromCol = fromCol;
        this.toRow = toRow;
        this.toCol = toCol;
    }

    public int getFromRow() {
        return fromRow;
    }

    public int getFromCol() {
        return fromCol;
    }

    public int getToRow() {
        return toRow;
    }

    public int getToCol() {
        return toCol;
    }
}