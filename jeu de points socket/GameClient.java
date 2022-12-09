package gametools;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

import window.Feuille;

public class GameClient extends Thread {
    InetAddress ipAddress;
    DatagramSocket socket;
    Feuille feuille;

    public GameClient(Feuille feuille,String ipAddress){
        this.feuille = feuille;
        try {
            this.socket = new DatagramSocket();
            this.ipAddress   = InetAddress.getByName(ipAddress);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void run(){
        while(true){
            byte[] data = new byte[1024];
            DatagramPacket packet = new DatagramPacket(data, data.length);
            try {
            socket.receive(packet);
                
            } catch (Exception e) {
                System.out.println(e);

            }
            System.out.println("SERVER>"+new String(packet.getData()));    
        }
    }

    public void sendData(byte[] data){
        DatagramPacket packet = new DatagramPacket(data,data.length,ipAddress,1948);
        try {
        socket.send(packet);
            
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    
}
