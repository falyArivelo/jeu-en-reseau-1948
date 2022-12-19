package network;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import assets.*;

public class GameServer {
    ServerSocket ss;
    private int numPlayers;
    ServerSideConnection player1;
    ServerSideConnection player2;
    Joueur j1;
    Joueur j2;
    int x;
    

    public GameServer(int port) {
        System.out.println("----GAmeSErver-------");
        numPlayers = 0;
        try {
            ss = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    public void acceptConnections() {
        try {
            System.out.println("waiting for connection..");
            while (numPlayers < 2) {
                Socket s = ss.accept();
                numPlayers++;
                System.out.println("player " + numPlayers + "has conncted");
                ServerSideConnection ssc = new ServerSideConnection(s, numPlayers);
                if (numPlayers == 1) {
                    player1 = ssc;
                } else {
                    player2 = ssc;
                }
                Thread t = new Thread(ssc);
                t.start();
            }
            System.out.println("we now have 2 players no longer acepped");
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    // ---------------------
    private class ServerSideConnection implements Runnable {
        Socket socket;
        DataInputStream dataIn;
        DataOutputStream dataOut;
        ObjectOutputStream oos;
        ObjectInputStream ois;
        int playerId;

        public ServerSideConnection(Socket s, int id) {
            socket = s;
            playerId = id;
            try {
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        @Override
        public void run() {
            try {
                dataOut.writeInt(playerId);
                dataOut.flush();

                while (true) {
                    if (playerId == 1) {
                        Point point = (Point) ois.readObject();

                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                player2.sendCoord(point);
                            }
                        });
                        t.start();
                    } else {
                        Point point = (Point) ois.readObject();

                        Thread t = new Thread(new Runnable() {
                            public void run() {
                                player1.sendCoord(point);
                            }
                        });
                        t.start();

                    }
                }
            } catch (Exception e) {
                System.out.println("EN ATTENTE D'UN ADVERSAIRE");
            }
        }

        public void sendCoord(Point point) {

            try {
      
                oos.writeObject(point);
                oos.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

    }

    public void closeConnection() {
        try {
            ss.close();
            System.out.println("connection closed----------");
        } catch (Exception e) {
        }

    }

}