package main.ui;

import javax.swing.*;
import java.awt.*;

public class UpdatePanel extends JPanel {
    private static final int PANEL_WIDTH = 1600;
    private static final int PANEL_HEIGHT = 900;

    public UpdatePanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));

        JLabel title = new JLabel("do update here");

        add(title);
        add(back);
    }
}
