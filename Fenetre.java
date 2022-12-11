// package window;

// import java.io.IOException;

// import javax.swing.*;
// import javax.swing.JFrame;
// import java.awt.Color;
// import java.awt.FlowLayout;
// import assets.*;

// import window.*;
// import listener.*;

// public class Fenetre extends JFrame {
//     JPanel actuPanel;
//     Feuille feuille;
//     MyMenu Mymenu;
//     int id;

//     public Fenetre() throws IOException {
//         Click click = new Click(this);
//         Mymenu = new MyMenu();
//         Mymenu.getHost_btn().addActionListener(click);
//         feuille = new Feuille();
//         setFeuille(feuille);
//         setActuPanel(Mymenu);
//         this.add(getActuPanel());

//         this.setLayout(new FlowLayout());
//         this.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//         this.pack();
//         this.setLocationRelativeTo(null);
//         this.setResizable(false);
//         this.setVisible(true);

//         this.setFocusable(true);
//     }

//     public void refresh(JPanel next) {
//         this.remove(actuPanel);
//         setActuPanel(next);
//         this.add(actuPanel);
//         this.revalidate();
//         this.repaint();

//     }

//     public void setActuPanel(JPanel actuPanel) {
//         this.actuPanel = actuPanel;
//     }

//     public JPanel getActuPanel() {
//         return actuPanel;
//     }

//     public MyMenu getMyMenu() {
//         return Mymenu;
//     }

//     public void setFeuille(Feuille feuille) {
//         this.feuille = feuille;
//     }

//     public void setId(int id) {
//         this.id = id;
//     }

//     public int getId() {
//         return id;
//     }

//     public Feuille getFeuille() {
//         return feuille;
//     }

// }
