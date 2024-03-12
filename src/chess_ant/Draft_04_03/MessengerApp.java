package chess_ant.Draft_04_03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MessengerApp {
    private static final String SERVER_IP = "localhost";
    private static final int SERVER_PORT = 12345;

    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public static void main(String[] args) {
        MessengerApp messenger = new MessengerApp();
        messenger.connectToServer();

        // Example usage:
        messenger.sendMessage(readBoardFromFile("D:\\Project\\Project_Java\\Chess_Ant\\src\\chess_ant\\board.txt"));
        String receivedMessage = messenger.waitForMessages();
        System.out.println("Received message: " + receivedMessage);
    }

    private static String readBoardFromFile(String filePath) {
        StringBuilder board = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                board.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return board.toString();
    }
    public void connectToServer() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void sendMessage(String message) {
        output.println(message);
    }

    public String waitForMessages() {
        try {
            String message = input.readLine();
            return message;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}






