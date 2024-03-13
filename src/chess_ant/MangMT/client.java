package chess_ant.MangMT;

import java.io.*;
import java.net.*;

public class client {
    private static String SERVER_ID = "192.168.76.101";
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_ID, SERVER_PORT);

            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));

            String message;

            String turn = fromServer.readLine();
            System.out.println("You are client " + turn);

            while (turn.equals("1")) {
                System.out.print("Enter message:");
                if ((message = fromUser.readLine()) != null) {
                    toServer.println(message);
                }

                if ((message = fromServer.readLine()) != null) {
                    System.out.println("From your friend: " + message);
                }

            }

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
            // e.printStackTrace();
            System.out.println("connection failed");
        }

    }
}