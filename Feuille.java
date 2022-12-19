package window;

import java.awt.Dimension;
import java.util.Vector;

import javax.swing.*;
import assets.*;
import listener.*;
import gametools.*;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Color;
import java.awt.RenderingHints;
import java.net.Socket;
import java.awt.BasicStroke;

public class Feuille extends JPanel implements Runnable {
    public final static int WIDTH = 550;
    public final static int HEIGHT = 400;
    public final static int DIMENSION_CASE = 30;
    final static int DIMENSION_POINT = DIMENSION_CASE / 2;
    final static int X_NBR_CASES = (int) Math.round(WIDTH / DIMENSION_CASE);
    final static int Y_NBR_CASES = (int) Math.round(HEIGHT / DIMENSION_CASE);
    Image boule = new ImageIcon("boule.png").getImage();

    Point[][] Points;
    Joueur haveTurn;
    Joueur proprio;
    Joueur opponnent;
    int id;

    Clic clic;

    public Feuille() {
        init();
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
        System.out.println(X_NBR_CASES);
        System.out.println(Y_NBR_CASES);
    }

    @Override
    public void run() {
        // TODO Auto-generated method stub

    }


    public void init() {
        this.Points = new Point[X_NBR_CASES][Y_NBR_CASES];

    }

    public void pushTurn() {
    }

    public void drop(Point point) {
        int x = point.getX();
        int y = point.getY();

        try {
            if (getPoints()[x][y] == null) {
                this.getPoints()[x][y] = point;
                Graphics g = this.getGraphics();
                Graphics2D graph = (Graphics2D) g;
                graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                graph.setColor(point.getColor());

                graph.fillOval(point.getXlocation() - (DIMENSION_POINT / 2),
                        point.getYlocation() - (DIMENSION_POINT / 2), DIMENSION_POINT,
                        DIMENSION_POINT);

                point.setProprio(getHaveTurn());
                for (int i = 1; i <= 2; i++) {
                    goal(i, x, y);

                }
                pushTurn();
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }
    // ________________________________________________________________________

    public void goal(int id, int x, int y) {
        filled_horizontal(id, x, y);
        filled_rightInclined(id, x, y);
        filled_vertical(id, x, y);
        filled_leftInclined(id, x, y);

    }

    public int nbrTrace(int x, int y, int x_incre, int y_incre) {
        int nbr = 0;
        int x2 = x + (x_incre);
        int y2 = y + (y_incre);
        while (getPoints()[x2][y2] != null && getPoints()[x2][y2].getTraced() && nbr <= 5) {
            nbr++;
            x2 = x2 + (x_incre);
            y2 = y2 + (y_incre);
        }
        return nbr;
    }

    public boolean filled_horizontal(int id, int x, int y) {
        int ok = 1;
        int ok1;
        int x1 = x;
        int x2 = x;
        while (getPoints()[x1 + 1][y] != null && getPoints()[x1 + 1][y].getIdJoueur() == id
                && nbrTrace(x1, y, 1, 0) < 5) {
            ok++;
            x1++;
        }
        ok1 = ok;
        while (getPoints()[x2 - 1][y] != null && getPoints()[x2 - 1][y].getIdJoueur() == id
                && nbrTrace(x2, y, -1, 0) < 5) {
            ok1++;
            x2--;
        }
        if (ok1 == 5) {
            trace(x1, y, x2, y);
            if(id==this.getId()){
                this.getProprio().getInfo().getTurn().setForeground(Color.BLUE);
                this.getProprio().getInfo().getTurn().setText("YOU WIN");
            }else{
                this.getProprio().getInfo().getTurn().setForeground(Color.RED);
                this.getProprio().getInfo().getTurn().setText("YOU LOSE");
            }
            // joueur.addScore();
            ;
            int i = 0;

            while (i < 5) {
                getPoints()[x2][y].trace();
                x2++;
                i++;
            }
            return true;
        }
        return false;

    }

    public boolean filled_rightInclined(int id, int x, int y) {
        int ok = 1;
        int ok1;
        int x1 = x;
        int x2 = x;
        int y1 = y;
        int y2 = y;
        while (getPoints()[x1 - 1][y1 + 1] != null && getPoints()[x1 - 1][y1 + 1].getIdJoueur() == id
                && nbrTrace(x1, y1, -1, 1) < 5
                && inclinedRight_barage_2(x1, y1, id)) {
            ok++;
            x1--;
            y1++;
        }
        ok1 = ok;
        while (getPoints()[x2 + 1][y2 - 1] != null && getPoints()[x2 + 1][y2 - 1].getIdJoueur() == id
                && nbrTrace(x2, y2, 1, -1) < 5
                && inclinedRight_barage_1(x2, y2, id)) {
            ok1++;
            x2++;
            y2--;
        }
        if (ok1 == 5) {
            trace(x1, y1, x2, y2);
            if(id==this.getId()){
                this.getProprio().getInfo().getTurn().setForeground(Color.BLUE);
                this.getProprio().getInfo().getTurn().setText("YOU WIN");
            }else{
                this.getProprio().getInfo().getTurn().setForeground(Color.RED);
                this.getProprio().getInfo().getTurn().setText("YOU LOSE");
            }
            // joueur.addScore();
            ;
            for (int i = 0; i < 5; i++) {
                getPoints()[x1][y1].trace();
                x1++;
                y1--;
            }
            return true;
        }
        return false;
    }

    public Boolean filled_vertical(int id, int x, int y) {
        int ok = 1;
        int y1 = y;
        int y2 = y;

        while (getPoints()[x][y1 - 1] != null && getPoints()[x][y1 - 1].getIdJoueur() == id
                && nbrTrace(x, y1, 0, -1) < 5) {
            ok++;
            y1--;
        }
        int ok1 = ok;
        while (getPoints()[x][y2 + 1] != null && getPoints()[x][y2 + 1].getIdJoueur() == id
                && nbrTrace(x, y2, 0, 1) < 5) {
            ok1++;
            y2++;
        }
        if (ok1 == 5) {
            trace(x, y1, x, y2);
            if(id==this.getId()){
                this.getProprio().getInfo().getTurn().setForeground(Color.BLUE);
                this.getProprio().getInfo().getTurn().setText("YOU WIN");
            }else{
                this.getProprio().getInfo().getTurn().setForeground(Color.RED);
                this.getProprio().getInfo().getTurn().setText("YOU LOSE");
            }
            // joueur.addScore();
            int i = 0;
            while (i < 5) {
                getPoints()[x][y1].trace();
                y1++;
                i++;
            }
            return true;
        }

        return false;
    }

    // angle \
    public boolean filled_leftInclined(int id, int x, int y) {
        int ok = 1;
        int ok1;
        int x1 = x;
        int x2 = x;
        int y1 = y;
        int y2 = y;

        while (getPoints()[x1 - 1][y1 - 1] != null && getPoints()[x1 - 1][y1 - 1].getIdJoueur() == id
                && nbrTrace(x1, y1, -1, -1) < 5
                && inclinedLeft_barage_1(x1, y1, id)) {
            ok++;
            x1--;
            y1--;
        }
        ok1 = ok;
        while (getPoints()[x2 + 1][y2 + 1] != null && getPoints()[x2 + 1][y2 + 1].getIdJoueur() == id
                && nbrTrace(x2, y2, 1, 1) < 5
                && inclinedLeft_barage_2(x2, y2, id)) {
            ok1++;
            x2++;
            y2++;
        }
        if (ok1 == 5) {
            trace(x1, y1, x2, y2);
            if(id==this.getId()){
                this.getProprio().getInfo().getTurn().setForeground(Color.BLUE);
                this.getProprio().getInfo().getTurn().setText("YOU WIN");
            }else{
                this.getProprio().getInfo().getTurn().setForeground(Color.RED);
                this.getProprio().getInfo().getTurn().setText("YOU LOSE");
            }
            // joueur.addScore();
            for (int i = 0; i < 5; i++) {
                getPoints()[x1][y1].trace();
                x1++;
                y1++;
            }
            return true;
        }

        return false;
    }

    public boolean inclinedRight_barage_1(int x, int y, int id) {
        try {
            if (getPoints()[x + 1][y].getTraced() && getPoints()[x][y - 1].getTraced()
                    && getPoints()[x + 1][y].getIdJoueur() != id
                    && getPoints()[x][y - 1].getIdJoueur() != id) {
                return false;
            }
        } catch (Exception e) {
            // System.out.println(e);
        }

        return true;
    }

    public boolean inclinedRight_barage_2(int x, int y, int id) {
        try {
            if (getPoints()[x][y + 1].getTraced() && getPoints()[x - 1][y].getTraced()
                    && getPoints()[x][y + 1].getIdJoueur() != id
                    && getPoints()[x - 1][y].getIdJoueur() != id) {
                return false;
            }
        } catch (Exception e) {
            // System.out.println(e);

        }
        return true;
    }

    public boolean inclinedLeft_barage_1(int x, int y, int id) {
        try {
            if (getPoints()[x - 1][y].getTraced() && getPoints()[x][y - 1].getTraced()
                    && getPoints()[x - 1][y].getIdJoueur() != id
                    && getPoints()[x][y - 1].getIdJoueur() != id) {
                return false;
            }
        } catch (Exception e) {
            // System.out.println(e);

        }
        return true;

    }

    public boolean inclinedLeft_barage_2(int x, int y, int id) {
        try {
            if (getPoints()[x][y + 1].getTraced() && getPoints()[x + 1][y].getTraced()
                    && getPoints()[x][y + 1].getIdJoueur() != id
                    && getPoints()[x + 1][y].getIdJoueur() != id) {
                return false;
            }
        } catch (Exception e) {

        }
        return true;
    }

    public void trace(int x1, int y1, int x2, int y2) {
        Graphics g = getGraphics();
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

            graph.setColor(new Color(255,0,208));
        //

        ((Graphics2D) g).setStroke(new BasicStroke((float) (DIMENSION_CASE * 0.16)));
        graph.drawLine(x1 * DIMENSION_CASE, y1 * DIMENSION_CASE, x2 * DIMENSION_CASE, y2 * DIMENSION_CASE);
    }

    public void paint(Graphics g) {
        Graphics2D graph = (Graphics2D) g;
        graph.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);

        graph.setStroke(new BasicStroke(1)); //line 5Px
        graph.setPaint(new Color(190, 190, 190)); // couleur

        for (int i = DIMENSION_CASE; i <= HEIGHT; i += DIMENSION_CASE) {
            graph.drawLine(0, i, WIDTH, i); // coordonées debut et fin
        }
        for (int i = DIMENSION_CASE; i <= WIDTH; i += DIMENSION_CASE) {
            graph.drawLine(i, 0, i, HEIGHT);
        }
        // boucle();
    }

    public void actualiser() {
        for (Point[] point : Points) {
            for (Point p : point) {
                this.drop(p);
            }
        }
    }

    public void boucle() {
    
        for (Point[] points : getPoints()) {
            for (Point point : points) {
                if (point != null)
                    drop(point);
            }
        }
        
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setHaveTurn(Joueur haveTurn) {
        this.haveTurn = haveTurn;
    }

    public Joueur getHaveTurn() {
        return haveTurn;
    }

    public static int getDimensionCase() {
        return DIMENSION_CASE;
    }

    public static int getyNbrCases() {
        return Y_NBR_CASES;
    }

    public static int getxNbrCases() {
        return X_NBR_CASES;
    }

    public Point[][] getPoints() {
        return Points;
    }

    public void setPoints(Point[][] points) {
        Points = points;
    }


    public Clic getClic() {
        return clic;
    }

    public void setClic(Clic clic) {
        this.clic = clic;
    }

    public Joueur getProprio() {
        return proprio;
    }

    public void setProprio(Joueur proprio) {
        this.proprio = proprio;
    }

}
