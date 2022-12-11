package listener;

import java.io.FileNotFoundException;
import java.io.IOException;

import javax.swing.JPanel;

import java.awt.*;
import java.awt.event.*;
import java.awt.Color;
import java.lang.Math;
import assets.*;
import assets.Point;
import window.*;

public class Clic extends JPanel implements java.awt.event.MouseListener {

    Joueur joueur;

    public Clic(Joueur f) {
        this.joueur = f;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // System.out.println(joueur.getFeuille().getProprio().getTurn());
        System.out.println( joueur.getId()+""+joueur.getTurn());

        if (joueur.getTurn()) {
           
            System.out.println( joueur.getId()+""+joueur.getTurn());
            double x = e.getX();
            double y = e.getY();
            int xlocation = (int) (Math.round((x - (Feuille.DIMENSION_CASE / 2)) / Feuille.DIMENSION_CASE)
                    * Feuille.DIMENSION_CASE);
            int ylocation = (int) (Math.round((y - (Feuille.DIMENSION_CASE)) / Feuille.DIMENSION_CASE)
                    * Feuille.DIMENSION_CASE);

            int X = (int) (Math.round((x - (Feuille.DIMENSION_CASE / 2)) / Feuille.DIMENSION_CASE));
            int Y = (int) (Math.round((y - (Feuille.DIMENSION_CASE)) / Feuille.DIMENSION_CASE));
            System.out.println(xlocation + "-" + ylocation);
            Point newPoint = new Point(X, Y);
            if (joueur.getId() == 1) {
                newPoint.setColor(Color.blue);
            }
            if (joueur.getId() == 2) {
                newPoint.setColor(Color.red);

            }
            // newPoint.setColor(Color.blue);
            newPoint.setIdJoueur(joueur.getId());

            joueur.send(newPoint);

            newPoint.setXlocation(xlocation);
            newPoint.setYlocation(ylocation);
            joueur.getFeuille().drop(newPoint);
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // TODO Auto-generated method stub

    }

    @Override
    public void mouseExited(MouseEvent e) {
        // TODO Auto-generated method stub

    }
}
