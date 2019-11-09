package client;

import message.*;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

import message.InitMessage;

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

            InitMessage message = null;
            while(!finished){
                Object o = in.readObject();
                if (o instanceof InitMessage){
                    finished = true;
                    message = (InitMessage) o;
                }
            }
            in.close();
            out.close();

            System.out.println(message.getId());

            new Game(socket, message.getId());

        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

    }
}   