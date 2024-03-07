package chess_ant.Draft_07_03;

import java.net.*;
import java.io.*;

public class server {

    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
       try{
        ServerSocket ServerSocket = new ServerSocket(SERVER_PORT);

        Socket client1Socket = ServerSocket.accept();
        Socket Client2Socket = ServerSocket.accept();

        BufferedReader fromClient1 = new BufferedReader(new InputStreamReader(client1Socket.getInputStream()));
        PrintWriter toClient1= new PrintWriter(client1Socket.getOutputStream(),true);

        BufferedReader fromClient2 = new BufferedReader(new InputStreamReader(Client2Socket.getInputStream()));
        PrintWriter toClient2= new PrintWriter(Client2Socket.getOutputStream(),true);

        String messager;

        while (true) {
            if((messager=fromClient1.readLine())!=null)
            {
                System.out.println(messager);
                toClient2.println(messager);
            }

            if((messager=fromClient2.readLine())!=null)
            {
                System.out.println(messager);
                toClient1.println(messager);
            }
            
        }


       }

       catch (IOException e) {
        e.printStackTrace();
    }


    }

}