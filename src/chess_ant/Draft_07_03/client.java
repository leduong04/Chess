package chess_ant.Draft_07_03;

import java.io.*;
import java.net.*;

public class client{
    private static String SERVER_ID = "localhost";
    private static int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try{
            Socket socket = new Socket(SERVER_ID,SERVER_PORT);
            
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter toServer = new PrintWriter(socket.getOutputStream(), true);

            System.out.print("Enter message: ");
            BufferedReader fromUser = new BufferedReader(new InputStreamReader(System.in));

            String message;

            while (true) {
                if((message = fromUser.readLine())!=null)
                {
                    toServer.println(message);
                }

                if((message=fromServer.readLine())!=null)
                {
                    System.out.println("From the orther: "+message);
                }
                
            }
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }


    }
}