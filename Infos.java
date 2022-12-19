package window;

import javax.swing.JLabel;
import javax.swing.JPanel;

import assets.Joueur;
import java.awt.*; 

public class Infos extends JPanel {
    Joueur joueur;
    JLabel turn;
    JLabel score;

    public Infos(Joueur joueur) {
        setJoueur(joueur);
        turn = new JLabel("turn");
        turn.setFont(new Font("Serif",Font.BOLD,30));
        turn.setForeground(Color.BLACK);
        score = new JLabel("score");
        this.add(turn);
    
        this.setPreferredSize(new Dimension(550, 50));
        setBackground(Color.WHITE);
    }

    public Joueur getJoueur() {
        return joueur;
    }

    public void setJoueur(Joueur joueur) {
        this.joueur = joueur;
    }

    public JLabel getTurn() {
        return turn;
    }

    public void setTurn(JLabel turn) {
        this.turn = turn;
    }

    public JLabel getScore() {
        return score;
    }

    public void setScore(JLabel score) {
        this.score = score;
    }

}
