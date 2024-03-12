package chess_ant.Drat_4_3;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.Serializable;

public class Server {
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);
            System.out.println("Server is running...");
            Socket clientSocket_1 = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket_1.getInetAddress().getHostAddress());

            ObjectInputStream inputStream_1 = new ObjectInputStream(clientSocket_1.getInputStream());

            ObjectOutputStream outputStream_1 = new ObjectOutputStream(clientSocket_1.getOutputStream());

            Socket clientSocket_2 = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket_2.getInetAddress().getHostAddress());

            ObjectInputStream inputStream_2 = new ObjectInputStream(clientSocket_2.getInputStream());

            ObjectOutputStream outputStream_2 = new ObjectOutputStream(clientSocket_2.getOutputStream());

            outputStream_2.writeObject("2");

            String from1;
            String from2;

            while (true) {

                // outputStream_1.writeObject("This is server");

                if((from1=(String) inputStream_1.readObject())!=null)
                {
                    outputStream_2.writeObject(from1);
                }
                

                

                if((from2=(String)inputStream_2.readObject())!=null)
                {
                    outputStream_1.writeObject(from2);
                }
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