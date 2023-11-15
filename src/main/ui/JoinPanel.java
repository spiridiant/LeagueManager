package main.ui;

import main.delegates.TerminalOperationDelegate;

import javax.swing.*;
import java.awt.*;

public class JoinPanel extends JPanel {
    private TerminalOperationDelegate delegate;
    private CardLayout cl;
    private JPanel leagueManager;
    public JoinPanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
    }

    public void makeBackMenuButton() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));
        add(back);
    }

    public void makeDisplayPanel() {

    }
}
