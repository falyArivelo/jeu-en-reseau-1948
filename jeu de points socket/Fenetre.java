package window;

import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import java.awt.Color;
import java.awt.FlowLayout;
import assets.*;

import window.*;
import listener.*;

public class Fenetre extends JFrame {
    Feuille feuille;
    // Clic clic;
    JButton actualiser;
    int id;

    public Fenetre() throws IOException {
        actualiser = new JButton("actualiser");
        // this.add(actualiser);
        // actualiser.addActionListener(new Act(this));

        actualiser.setFocusable(false);
        feuille = new Feuille();
        setFeuille(feuille);
        this.setLayout(new FlowLayout());
        this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        this.add(feuille);
        this.pack();
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setVisible(true);
        // Clic clic = new Clic(this);
        // this.addMouseListener(clic);
        this.setFocusable(true);
        // this.addMouseListener(new Clic(this));
    }

    public void setFeuille(Feuille feuille) {
        this.feuille = feuille;
    }
public void setId(int id) {
    this.id = id;
}
public int getId() {
    return id;
}
    // public void setClic(Clic clic) {
    //     this.clic = clic;
    // }

    // public Clic getClic() {
    //     return clic;
    // }

    public Feuille getFeuille() {
        return feuille;
    }
    public JButton getActualiser() {
        return actualiser;
    }
}
