package chess_ant.MangMT;

import java.net.*;
import java.io.*;

public class server {
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(SERVER_PORT);

            System.out.println("SERVER IS RUNNING");

            Socket client1Socket = serverSocket.accept();

            BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
            PrintWriter toClient1 = new PrintWriter(client1Socket.getOutputStream(), true);

            toClient1.println("1");

            
            Socket client2Socket = serverSocket.accept();

            BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(client2Socket.getInputStream()));
            PrintWriter toClient2 = new PrintWriter(client2Socket.getOutputStream(), true);

            toClient2.println("2");
            String message;
            while (true) {
                if ((message = fromClient1.readLine()) != null) {
                    System.out.println(message);
                    toClient2.println(message);
                }

                if ((message = fromClient2.readLine()) != null) {
                    System.out.println(message);
                    toClient1.println(message);
                }

                if (client1Socket.isClosed() || client2Socket.isClosed()) {
                    break;
                }
            }
            
            client1Socket.close();
            client2Socket.close();
            serverSocket.close();
            
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
