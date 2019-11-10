package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import message.InitMessage;
import message.SCMessage;

public class Server {

    private int port = 25577;

    private Integer idHandler = 0; // id
    
    public Server(int port){
        this.port = port;
    }
    public Server(){
    }
    
    public void serve() {
        try {
            ServerSocket serverSocket = new ServerSocket(this.port);

            boolean finished = false;
            // Infinite loop waiting for new client
            while (!finished) {
                System.out.println("Waiting for a client");
                Socket socket = serverSocket.accept();

                // i : id of handler
                idHandler++;

                System.out.println("New client");

                // Start an handler thread to communicate with the new client
                new Thread(() -> serveSocket(socket, idHandler)).start();
            }
            serverSocket.close();

        } catch (SocketException e) {
            // To close accept, close serverSocket to go into this handler and send "end"
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void serveSocket(Socket threadSocket, int playerID) {
        boolean finished = false;
        System.out.println("Player ID : "+playerID);

        try {
            ObjectInputStream is = new ObjectInputStream(threadSocket.getInputStream());
            int roomID = (int) is.readObject();

            System.out.println("Room ID : "+roomID);
            Manager m = new Manager();
            ArrayList<SCMessage> players = m.connect(roomID, playerID);

            InitMessage initmsg = new InitMessage(
                playerID,
                m.getMap(roomID),
                players
            );

            ObjectOutputStream os = new ObjectOutputStream(threadSocket.getOutputStream());
            os.writeObject(initmsg);

            while (!finished) {
                
            }
            is.close();
            os.close();

            // Close handler and leave thread
            is.close();
            threadSocket.close();

            //System.out.println("Client connection "+i+" finished");
        } catch (EOFException e) {
            // nothing sent
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) {
        new Server().serve();
    }
}