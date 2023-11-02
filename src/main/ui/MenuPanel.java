package main.ui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel  {
    private static final int PANEL_WIDTH = 1600;
    private static final int PANEL_HEIGHT = 900;
    private static final int BUTTON_WIDTH = 200;
    private static final int BUTTON_HEIGHT = 30;

    public MenuPanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        JButton insert = new JButton("insert");
        insert.addActionListener(e -> cl.show(leagueManager, "insert"));
        add(insert);

        JButton delete = new JButton("delete");
        delete.addActionListener(e -> cl.show(leagueManager, "delete"));
        add(delete);

        JButton update = new JButton("update");
        update.addActionListener(e -> cl.show(leagueManager, "update"));

        JLabel title = new JLabel("Basketball League Management System");

        add(title);
        add(insert);
        add(delete);
        add(update);
    }
}
