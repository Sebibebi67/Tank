package client;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.URL;
import java.net.UnknownHostException;

public class Main {

    public static void main(String[] args) {

        // URL pageURL = null;
        // try { 
        // pageURL = new URL(getDocumentBase( ), "http://www.javasoft.com"); 
        // } catch (MalformedURLException mue) {}


        String url = "vps.tonychouteau.fr";
        int port = 8087;
        DatagramSocket client = null;

        //Init connexion
        try {
            client = new DatagramSocket();
        } catch (SocketException e) {
            System.out.println("line 21");
            e.printStackTrace();
        }
        System.out.println("ok boomer");

        //Création buffer
        String envoi = "7";
        byte[] buffer = envoi.getBytes();

        //On crée notre datagramme
        InetAddress adresse = null;
        try {
            adresse = InetAddress.getByName(url);
        } catch (UnknownHostException e) {
            System.out.println("line 34");
            e.printStackTrace();
        }
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length, adresse, port);
        
        
        //On lui affecte les données à envoyer
        packet.setData(buffer);

              

        //On envoie au serveur
        try {
            client.send(packet);
        } catch (IOException e) {
            System.out.println("line 48");
            e.printStackTrace();
        }
        System.out.println("send nudes"); 

        //Et on récupère la réponse du serveur
        byte[] buffer2 = new byte[8000];
        DatagramPacket packet2 = new DatagramPacket(buffer2, buffer2.length, adresse, port);
        try {
            client.receive(packet2);
        } catch (IOException e) {
            System.out.println("line 58");
            e.printStackTrace();
        }
        System.out.println(new String(packet2.getData()));


        

        // Socket socket = null;

        // int roomId = 7;

        // try {
        //     socket = new Socket(url, port);

        //     ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
        //     out.writeObject(roomId);
        //     out.flush(); 
            
        //     ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
        //     Object o = in.readObject();
        //     InitMessage message =(InitMessage) o;

        //     // in.close();
        //     // out.close();

        //    //System.out.println(message.getId());

            // new Game(7, message.getTab(), message.getMessages());

        // } catch (IOException | ClassNotFoundException e) {
        //     e.printStackTrace();
        // }

    }

    public char[][] stringToTab(String string){
        char[][] tab = new char[20][40];
        String[] array = string.split(";", 20);
        for (int i = 0 ; i< 20 ; i++){
            tab[i] = array[i].toCharArray();
        }
        return tab;
    }
}   