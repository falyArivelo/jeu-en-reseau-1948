package listener;

import java.awt.event.*;
import window.*;
import assets.*;
import network.*;

public class Click implements ActionListener {
    Joueur joueur;

    public Click(Joueur joueur) {
        this.joueur = joueur;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == joueur.getMenu().getHost_btn()) {
            int host_port = Integer.valueOf(joueur.getMenu().getHost_port().getText());
            GameServer gs = new GameServer(host_port);

            Thread t = new Thread(new Runnable() {
                public void run() {
                    try {
                        gs.acceptConnections();
                    } catch (Exception ex) {
                        System.out.println(ex);
                    }
                }
            });
            t.start();

            try {
                joueur.connectToServer("localhost", host_port);
                if (joueur.getCsc().getSocket().isConnected() && !joueur.getCsc().getSocket().isClosed()) {
                    joueur.refresh(joueur.getFeuille());
                    joueur.getFeuille().setProprio(joueur);
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }
        }
        if (e.getSource() == joueur.getMenu().getJoin_btn()) {
            String ip = joueur.getMenu().getIpAddress().getText();
            int port = Integer.valueOf(joueur.getMenu().getPort().getText());

            try {
                joueur.connectToServer(ip, port);
                if (joueur.getCsc().getSocket().isConnected() && !joueur.getCsc().getSocket().isClosed()) {
                    joueur.refresh(joueur.getFeuille());
                }

            } catch (Exception ex) {
                System.out.println(ex);
            }

        }


    }

}
