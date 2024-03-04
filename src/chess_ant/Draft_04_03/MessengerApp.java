// package chess_ant.Draft_04_03;

// import java.io.BufferedReader;
// import java.io.IOException;
// import java.io.InputStreamReader;
// import java.io.PrintWriter;
// import java.net.Socket;

// public class MessengerApp {
//     private static final String SERVER_IP = "localhost";
//     private static final int SERVER_PORT = 12345;

//     private Socket socket;
//     private BufferedReader input;
//     private PrintWriter output;

//     public static void main(String[] args) {
//         MessengerApp messenger = new MessengerApp();
//         messenger.connectToServer();

//         // Example usage:
//         messenger.sendMessage("Hello from client!");
//         String receivedMessage = messenger.waitForMessages();
//         System.out.println("Received message: " + receivedMessage);
//     }

//     public void connectToServer() {
//         try {
//             socket = new Socket(SERVER_IP, SERVER_PORT);
//             input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//             output = new PrintWriter(socket.getOutputStream(), true);
//         } catch (IOException e) {
//             e.printStackTrace();
//         }
//     }

//     public void sendMessage(String message) {
//         output.println(message);
//     }

//     public String waitForMessages() {
//         try {
//             String message = input.readLine();
//             return message;
//         } catch (IOException e) {
//             e.printStackTrace();
//             return null;
//         }
//     }
// }








package chess_ant.Draft_04_03;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Arrays;
import java.util.*;

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
        messenger.IdPlayer();
        messenger.sendMessage(1,2,3,4);
        int[] receivedMessage = messenger.waitForMessages();
        // System.out.println("Received message: " + receivedMessage);
        for(int i=0; i<4; i++)
        {
            System.out.println(receivedMessage[i]);
        }
    }

    public boolean connectToServer() {
        try {
            socket = new Socket(SERVER_IP, SERVER_PORT);
            input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            output = new PrintWriter(socket.getOutputStream(), true);
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    public void IdPlayer() {
        output.println("12345");
    }

    public void sendMessage(int fromRow, int fromCol, int toRow, int toCol) {
        int[] s = { fromRow, fromCol, toRow, toCol };
        String message = Arrays.toString(s);
        output.println(message);
    }

    // public int[] waitForMessages() {
    //     try {
    //         String s = input.readLine();
    //         String[] stringArray = s.substring(1, s.length() - 1).split(", ");
    //         int[] newArray = new int[stringArray.length];
    //         for (int i = 0; i < stringArray.length; i++) {
    //             newArray[i] = Integer.parseInt(stringArray[i]);
    //         }
    //         return newArray;
    //     } catch (IOException e) {
    //         e.printStackTrace();
    //         return null;
    //     }
    // }
    public int[] waitForMessages() {
        try {
            String s = input.readLine();
            String[] stringArray = s.substring(1, s.length() - 1).split(", ");
            List<Integer> integerList = new ArrayList<>();
    
            for (String str : stringArray) {
                try {
                    integerList.add(Integer.parseInt(str));
                } catch (NumberFormatException e) {
                    // Chuỗi không phải là số, bỏ qua và tiếp tục
                    System.err.println("Chuỗi không phải là số: " + str);
                }
            }
    
            int[] newArray = new int[integerList.size()];
            for (int i = 0; i < integerList.size(); i++) {
                newArray[i] = integerList.get(i);
            }
    
            return newArray;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
