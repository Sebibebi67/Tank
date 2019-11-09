package client;

import message.*;

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
            Object o = in.readObject();
            InitMessage message =(InitMessage) o;

            in.close();
            out.close();

            System.out.println(message.getId());

            new Game(socket, message.getId(), message.getTab());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}   