package assets;

import java.awt.*;
import window.*;
import java.io.*;
public class Point  implements Serializable {
    int x, y;
    int xlocation, ylocation;
    boolean clicked = false;
    boolean traced = false;
    Joueur proprio = null;
    int idJoueur;
    Color color;

    public Point() {

    }

    public Point(int x, int y){
        setX(x);
        setY(y);
    }

    public void setClic(boolean clic) {
        this.clicked = clic;
    }

    public boolean getClic() {
        return this.clicked;
    }

    public boolean getTraced() {
        return this.traced;
    }

    public void trace() {
        this.traced = true;
    }

    public void setProprio(Joueur proprio) {
        this.proprio = proprio;
    }

    public Joueur getProprio() {
        return proprio;
    }

    public void setXlocation(int xlocation) {
        this.xlocation = xlocation;
    }
    public void setIdJoueur(int idJoueur) {
        this.idJoueur = idJoueur;
    }
    public int getIdJoueur() {
        return idJoueur;
    }

    public void setYlocation(int ylocation) {
        this.ylocation = ylocation;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getX() {
        return x;
    }

    public Color getColor() {
        return color;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public int getXlocation() {
        return xlocation;
    }

    public int getYlocation() {
        return ylocation;
    }

    public int getY() {
        return y;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

}