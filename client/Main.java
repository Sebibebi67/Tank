package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

public class Main {

    public static void main(String[] args) {
        String url = "127.0.0.1";
        int port = 25577;
        Socket socket = null;

        try {
            socket = new Socket(url, port);
            ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
            int id = (int) in.readObject();

            System.out.println("id : "+id);

            new Game(socket, id);

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}   