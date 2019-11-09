package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String url = "127.0.0.1";
        int port = 25577;
        Socket socket = null;

        int roomId = 7;

        try {
            socket = new Socket(url, port);

            ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
            out.writeObject(roomId);
            
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            Boolean finished = false;

            int id = -1;
            while(!finished){
                Object o = in.readObject();
                if (o instanceof Integer){
                    finished = true;
                    id = (int) o;
                }
            }
            in.close();
            out.close();

            System.out.println("id : "+id);

            new Game(socket, id);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}   