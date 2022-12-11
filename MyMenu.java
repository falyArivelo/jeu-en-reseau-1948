package window;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MyMenu extends JPanel {
    final int WIDTH = 550;
    final int HEIGHT = 400;
    JButton host_btn;
    JButton join_btn;
    JTextField host_port;

    JTextField ipAddress;
    JTextField port;

    public MyMenu() {

        JLabel m = new JLabel("Menu");
        host_port = new JTextField();
        ipAddress = new JTextField();
        port = new JTextField();
        ipAddress.setText("localhost");
        port.setText("1948");
        host_port.setText("1948");

        this.add(m);
        this.add(ipAddress);
        this.add(port);
        this.add(host_port);

        host_btn = new JButton("Host");
        host_btn.setFocusable(false);
        this.add(host_btn);
        host_btn.setBounds((WIDTH / 2) - 100, 20, 100, 30);
        host_port.setBounds((WIDTH / 2) - 100 + 120, 20, 100, 30);

        join_btn = new JButton("Join");
        join_btn.setFocusable(false);

        this.add(join_btn);
        join_btn.setBounds((WIDTH / 2) - 100, 60, 100, 30);

        ipAddress.setBounds((WIDTH / 2) - 100 + 110, 60, 100, 30);
        port.setBounds((WIDTH / 2) - 100 + 220, 60, 100, 30);

        host_btn.setFocusable(false);
        // setBackground(Color.RED);
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setFocusable(true);
        this.setLayout(null);
    }

    public JButton getHost_btn() {
        return host_btn;
    }

    public JButton getJoin_btn() {
        return join_btn;
    }

    public JTextField getIpAddress() {
        return ipAddress;
    }

    public JTextField getPort() {
        return port;
    }

    public JTextField getHost_port() {
        return host_port;
    }
}