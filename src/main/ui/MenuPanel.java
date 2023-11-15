package main.ui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel  {
    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720 ;

    public MenuPanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JLabel title = new JLabel("Basketball League Management System");

        JButton insert = new JButton("insert");
        insert.addActionListener(e -> cl.show(leagueManager, "insert"));

        JButton delete = new JButton("delete");
        delete.addActionListener(e -> cl.show(leagueManager, "delete"));

        JButton update = new JButton("update");
        update.addActionListener(e -> cl.show(leagueManager, "update"));

        JButton join = new JButton("join");
        join.addActionListener(e -> cl.show(leagueManager, "join"));

        add(title);
        add(insert);
        add(delete);
        add(update);
        add(join);
    }
}
