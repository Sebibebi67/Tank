package server;

import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketException;

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

                ObjectOutputStream os = new ObjectOutputStream(socket.getOutputStream());
                os.writeObject(idHandler);
                os.close();

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

    public void serveSocket(Socket threadSocket, int i) {
        boolean finished = false;
        System.out.println("Id : "+i);

        try {
            ObjectInputStream is = new ObjectInputStream(threadSocket.getInputStream());

            //o = is.readObject();

            while (!finished) {
                /*o = is.readObject();
                if (o instanceof Integer) {

                    if ((int) o == -7) {
                        finished = true;
                        continue;
                    }
                    
                    //System.out.print("");
                    //link.setData((int) o);
                    synchronized(link) {
                        link.offer((int) o);
                    }
                }*/
            }

            // Close handler and leave thread
            is.close();
            threadSocket.close();

            //System.out.println("Client connection "+i+" finished");
        } catch (EOFException e) {
            // nothing sent
        } catch (IOException e) {
            e.printStackTrace();
        }/* catch (ClassNotFoundException e) {
            e.printStackTrace();
        }*/
    }

    public static void main(String[] args) {
        new Server().serve();
    }
}