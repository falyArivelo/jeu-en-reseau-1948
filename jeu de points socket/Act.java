package listener;

import java.awt.event.*;

import javax.swing.JButton;

import assets.*;
import window.*;

public class Act implements ActionListener {

    Fenetre fenetre;

    public Act(Fenetre fenetre){

        this.fenetre = fenetre;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == fenetre.getActualiser()) {
            fenetre.getFeuille().actualiser();
        }
    }


}
