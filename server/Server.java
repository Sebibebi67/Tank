package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;
import java.util.ArrayList;

import message.CSMessage;
import message.InitMessage;
import message.SCMessage;

public class Server {

    private int port = 25577;

    private Integer idHandler = 0; // id

    private volatile Manager m = null;
    
    public Server(int port){
        this.port = port;
    }
    public Server(){
    }
    
    public void serve() {
        m = new Manager();
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
        System.out.println("Player ID : "+playerID);

        boolean finished = false;
        try {
            ObjectInputStream is = new ObjectInputStream(threadSocket.getInputStream());
            int roomID = (int) is.readObject();

            System.out.println("Room ID : "+roomID);

            ArrayList<SCMessage> players;
            synchronized(m){
                players = m.connect(roomID, playerID);
            }

            //System.out.println("Players : "+players.size());

            InitMessage msg = new InitMessage(
                playerID,
                m.getMap(roomID),
                players
            );

            ObjectOutputStream os = new ObjectOutputStream(threadSocket.getOutputStream());
            os.writeObject(msg);
            os.flush(); 

            //CSMessage clientMessage;
            while (!finished) {
                CSMessage clientMessage = (CSMessage) is.readObject();
                /*System.out.println("Test"+clientMessage.getKeys()[0]
                    +clientMessage.getKeys()[1]
                    +clientMessage.getKeys()[2]
                    +clientMessage.getKeys()[3]
                );*/
                System.out.println(clientMessage.getDiff());
        
                synchronized(m){
                    players = m.play(roomID, playerID, clientMessage);
                }
                os.writeObject(players);
                os.flush();
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