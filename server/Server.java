package server;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Arrays;

import entities.Map;

public class Server {

    private int port = 8087;

    // private Integer idHandler = 0; // id

    // private volatile Manager m = null;

    // private volatile ConcurrentLinkedQueue<CSMessage> link;

    public Server(int port) {
        this.port = port;
    }

    public Server() {
    }

    /*
     * public void serve() { m = new Manager(); link = new
     * ConcurrentLinkedQueue<>();
     * 
     * try { ServerSocket serverSocket = new ServerSocket(this.port);
     * 
     * boolean finished = false; // Infinite loop waiting for new client while
     * (!finished) { System.out.println("Waiting for a client"); Socket socket =
     * serverSocket.accept();
     * 
     * // i : id of handler idHandler++;
     * 
     * System.out.println("New client");
     * 
     * // Start an handler thread to communicate with the new client new Thread(()
     * -> serveSocket(socket, idHandler)).start(); } serverSocket.close();
     * 
     * } catch (SocketException e) { // To close accept, close serverSocket to go
     * into this handler and send "end" } catch (IOException e) {
     * e.printStackTrace(); } }
     * 
     * public void serveSocket(Socket threadSocket, int playerID) {
     * System.out.println("Player ID : " + playerID);
     * 
     * try { ObjectInputStream is = new
     * ObjectInputStream(threadSocket.getInputStream()); int roomID = (int)
     * is.readObject();
     * 
     * System.out.println("Room ID : " + roomID);
     * 
     * ArrayList<SCMessage> players; synchronized (m) { players = m.connect(roomID,
     * playerID); }
     * 
     * // System.out.println("Players : "+players.size());
     * 
     * InitMessage msg = new InitMessage(playerID, m.getMap(roomID), players);
     * 
     * ObjectOutputStream os = new
     * ObjectOutputStream(threadSocket.getOutputStream()); os.writeObject(msg);
     * os.writeObject(msg); os.flush();
     * 
     * 
     * // CSMessage clientMessage;
     * 
     * new Thread(() -> { try { inputHandler(is); } catch (ClassNotFoundException e)
     * { e.printStackTrace(); } catch (IOException e) { e.printStackTrace(); }
     * }).start(); //new Thread(() -> outputHandler(os, idHandler, roomID)).start();
     * outputHandler(os, idHandler, roomID);
     * 
     * /*while (!finished) { CSMessage clientMessage = (CSMessage) is.readObject();
     * System.out.println("Test"+clientMessage.getKeys()[0]
     * +clientMessage.getKeys()[1] +clientMessage.getKeys()[2]
     * +clientMessage.getKeys()[3] ); System.out.println(clientMessage.getDiff());
     * 
     * synchronized(m) { players = m.play(roomID, playerID, clientMessage); }
     * 
     * os.writeObject(players); os.flush(); }//
     * 
     * is.close(); os.close();
     * 
     * // Close handler and leave thread is.close(); threadSocket.close();
     * 
     * //System.out.println("Client connection "+i+" finished"); } catch
     * (EOFException e) { // nothing sent } catch (IOException e) {
     * e.printStackTrace(); } catch (ClassNotFoundException e) {
     * e.printStackTrace(); } }
     * 
     * public void inputHandler(ObjectInputStream is) throws ClassNotFoundException,
     * IOException { boolean finished = false; while (!finished) { CSMessage
     * clientMessage = (CSMessage) is.readObject(); link.offer(clientMessage);
     * System.out.println(clientMessage.getDiff()); } }
     * 
     * public void outputHandler(ObjectOutputStream os, int playerID, int roomID)
     * throws IOException { boolean finished = false; CSMessage msg;
     * ArrayList<SCMessage> players; while (!finished) { if ((msg = link.poll()) !=
     * null) { synchronized(m) { players = m.play(roomID, playerID, msg); }
     * os.writeObject(players); os.flush(); } } }
     */

    private void serve(){
        DatagramSocket server = null;
        try {
            server = new DatagramSocket(port);
        } catch (SocketException e) {
            e.printStackTrace();
        }

        byte[] buffer = new byte[1];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        try {
            server.receive(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }

        int roomID = Integer.parseInt(new String(buffer));
        print("Le connard au " + packet.getAddress() + " veut se connecter au salon " + roomID+"\n");
        packet.setLength(buffer.length);

        Map map = new Map();
        char[][] tab = map.getTab();
        String mapBuffered = "";
        for (int i=0; i<20; i++){
            mapBuffered+=new String(tab[i])+";";
        }
        println(mapBuffered);

        byte[] buffer2 = mapBuffered.getBytes();

        DatagramPacket packet2 = new DatagramPacket(
            buffer2,             //Les données
            buffer2.length,      //La taille des données
            packet.getAddress(), //L'adresse de l'émetteur
            packet.getPort()     //Le port de l'émetteur
        );

        try {
            server.send(packet2);
        } catch (IOException e) {
            e.printStackTrace();
        }

        packet2.setLength(buffer2.length);
        println("Send");
    }

    public static synchronized void print(String str) {
        System.out.print(str);
    }

    public static synchronized void println(String str) {
        System.err.println(str);
    }

    public static void main(String[] args) {
        new Server().serve();
    }
}
