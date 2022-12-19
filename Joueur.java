package assets;

import java.net.*;
import java.io.*;
import java.lang.ProcessHandle.Info;

import javax.swing.*;
import assets.Point;
import listener.*;
import window.*;
import java.awt.*;

public class Joueur extends JFrame implements Serializable {
    JPanel actuPanel;
    Feuille feuille;
    Infos info;
    MyMenu menu;
    boolean tour;
    int score = 0;
    String nom;
    int id;
    Color pointColor;
    boolean turn;
    boolean run;
    ClientSideConnection csc;

    public Joueur(String nom) {
        setNom(nom);
        Click click = new Click(this);
        menu = new MyMenu();
        menu.getHost_btn().addActionListener(click);
        menu.getJoin_btn().addActionListener(click);
        feuille = new Feuille();
        setFeuille(feuille);
        setActuPanel(menu);
        this.add(getActuPanel(), BorderLayout.NORTH);
        info = new Infos(this);
        this.add(info, BorderLayout.SOUTH);
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        this.setFocusable(true);
    }

    public void refresh(JPanel next) {
        this.remove(actuPanel);
        setActuPanel(next);
        this.add(actuPanel);
        this.revalidate();
        this.repaint();

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
                if (!getTurn()) {
                    getInfo().getTurn().setText("player 2 turn ");
                }
                getInfo().getScore().setText("score : "+ getScore());

            }
        });
        t.start();

        System.out.println(point.getX() + "-" + point.getY());
    }

    // -----------------------------
    public void connectToServer(String host, int port) throws Exception {
        csc = new ClientSideConnection(host, port);
        try {
            this.start();
        } catch (Exception e) {

            System.out.println("tsy mety connecte");
        }
        if (this.getId() == 1) {
            this.setTurn(true);
        }
        this.setTitle("Player " + this.getId());
        this.getFeuille().setId(this.getId());

        run = true;
    }

    public void updateTurn() {
        while (true) {
            Point point = (Point) csc.receiveCoord();
            setTurn(true);
            if (getTurn()) {
                getInfo().getTurn().setText(" your turn ");
            }
            getInfo().getScore().setText("score : "+ getScore());
            Thread t = new Thread(new Runnable() {
                public void run() {
                    System.out.println(getId());
                    getFeuille().drop(point);
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

        public ClientSideConnection(String host, int port) throws Exception {
            System.out.println("----Client----");
            try {
                socket = new Socket(host, port);
                dataIn = new DataInputStream(socket.getInputStream());
                dataOut = new DataOutputStream(socket.getOutputStream());
                oos = new ObjectOutputStream(socket.getOutputStream());
                ois = new ObjectInputStream(socket.getInputStream());
                id = dataIn.readInt();
                System.out.println("connected to server as Player " + id + "");
            } catch (Exception e) {
                System.out.println(e);

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

        public Point receiveCoord() {
            Point point = null;
            try {
                point = (Point) ois.readObject();

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
            }

        }

        public Socket getSocket() {
            return socket;
        }

    }
    // ________________________________

    public static void main(String[] args) throws Exception {
        com.formdev.flatlaf.FlatLightLaf.setup();
        Joueur p = new Joueur("faly");
        p.addMouseListener(new Clic(p));

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

    public void setActuPanel(JPanel actuPanel) {
        this.actuPanel = actuPanel;
    }

    public JPanel getActuPanel() {
        return actuPanel;
    }

    public MyMenu getMenu() {
        return menu;
    }

    public Infos getInfo() {
        return info;
    }

    public void setInfo(Infos info) {
        this.info = info;
    }

}
