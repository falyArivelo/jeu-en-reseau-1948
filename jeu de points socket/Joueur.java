package assets;

import java.net.*;
import java.io.*;

import assets.Point;
import listener.Clic;
import window.*;
import java.awt.*;

public class Joueur implements Serializable {
    Fenetre fenetre;
    Feuille feuille;
    boolean tour;
    int score = 0;
    String nom;
    int id;
    Color pointColor;
    boolean turn;

    ClientSideConnection csc;

    public Joueur(String nom) {
        setNom(nom);

    }

    public void start() {
        Thread t = new Thread(new Runnable() {
            public void run() {
                updateTurn();
            }
        });
        t.start();
    }

    public void send(Point point) {

        Thread t = new Thread(new Runnable() {
            public void run() {
                csc.sendCoord(point);
                setTurn(false);

            }
        });
        t.start();

        System.out.println(point.getX() + "-" + point.getY());
    }

    // -----------------------------
    public void connectToServer() throws Exception {

        csc = new ClientSideConnection();
    }

    public void updateTurn() {
        while (true) {
            Point point = (Point) csc.receiveCoord();
            // if (id == 1)
            // point.setColor(Color.blue);
            // if (id == 2)
            // point.setColor(Color.red);

            // point.setProprio(oppo);
            // System.out.println("your enemy clicked" + point.getX() + "-" + point.getY());
            Thread t = new Thread(new Runnable() {
                public void run() {
                    fenetre.getFeuille().drop(point);
            setTurn(true);

                }
            });
            t.start();
        }

    }

    // -----------------------------
    // CLient Connection
    public class ClientSideConnection implements Serializable {
        Socket socket;
        DataInputStream dataIn;
        DataOutputStream dataOut;
        ObjectOutputStream oos;
        ObjectInputStream ois;

        public ClientSideConnection() throws Exception {
            System.out.println("----Client----");
            try {
                socket = new Socket("localhost", 1948);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                // Point point = (Point) ois.readObject();
                id = dataIn.readInt();
                System.out.println("connected to server as Player " + id + "");
            } catch (Exception e) {
                System.out.println(e);

            }
        }

        public void sendCoord(Point point) {
            // String coord = x + ";" + y;
            try {
                // dataOut.write(coord);
                // dataOut.flush();
                
                oos.writeObject(point);
                oos.flush();
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        public Point receiveCoord() {
            // int n = -1;
            Point point = null;
            try {
                // n = dataIn.readInt();
                point = (Point) ois.readObject();

                // System.out.println("player " + otherPlayer + "clicked button " + n);
            } catch (Exception e) {
                System.out.println(e);
            }
            return point;

        }

        public void closeConnection() {
            try {
                socket.close();
                System.out.println("connection closed----------");
            } catch (Exception e) {
                // System.out.println(e);
            }

        }

    }
    // ________________________________

    public void newFeuille() {
        try {
            fenetre = new Fenetre();
            fenetre.getFeuille().setProprio(this);
            fenetre.addMouseListener(new Clic(fenetre));
            fenetre.setTitle("Player " + getId());
            fenetre.setId(getId());
            fenetre.getFeuille().setId(getId());
            // setFeuille(feuille);
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public void drop(int x, int y) {
        Point point = new Point(x, y);
        feuille.drop(point);
    }

    public void addScore() {
        setScore(getScore() + 1);
    }

    public void setFeuille(Feuille feuille) {
        this.feuille = feuille;
    }

    public void setTour(boolean tour) {
        this.tour = tour;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPointColor(Color pointColor) {
        this.pointColor = pointColor;
    }

    public void setFenetre(Fenetre fenetre) {
        this.fenetre = fenetre;
    }

    public void setTurn(boolean turn) {
        this.turn = turn;
    }

    public boolean getTurn() {
        return this.turn;
    }

    public ClientSideConnection getCsc() {
        return csc;
    }

    public Feuille getFeuille() {
        return feuille;
    }

    public int getId() {
        return id;
    }

    public String getNom() {
        return nom;
    }

    public int getScore() {
        return score;
    }

    public boolean getTour() {
        return this.tour;
    }

    public Color getPointColor() {
        return pointColor;
    }

    public Fenetre getFenetre() {
        return fenetre;
    }

    public static void main(String[] args) throws Exception {
        // Player p = new Player(500, 100);
        Joueur p = new Joueur("faly");

        try {
            // p = new Joueur("faly");
            // new Fenetre();
            p.connectToServer();
            p.newFeuille();
            // csc = new ClientSideConnection();
            p.start();
            if(p.getId()== 1){
                p.setTurn(true);
            }
        } catch (Exception e) {
            // p.getCsc().closeConnection();
        }

        // p.setUpGUI();
        // p.setUpButtons();
        // p.startReceivingButtonNums();

    }

}
