package main.ui;

import javax.swing.*;
import java.awt.*;

public class MenuPanel extends JPanel {
    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;

    public MenuPanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(new Color(34, 34, 34));

        JLabel title = new JLabel("Basketball League Management System");
        title.setForeground(Color.WHITE);

        JButton insert = new JButton("insert");
        insert.addActionListener(e -> {
            if (leagueManager.getComponent(1) instanceof InsertPanel) {
                ((InsertPanel) leagueManager.getComponent(1)).updateContent();
            }
            cl.show(leagueManager, "insert");
        });

        JButton delete = new JButton("delete");
        delete.addActionListener(e -> {
            if (leagueManager.getComponent(2) instanceof DeletePanel) {
                ((DeletePanel) leagueManager.getComponent(2)).updateContent();
            }
            cl.show(leagueManager, "delete");
        });

        JButton update = new JButton("update");
        update.addActionListener(e -> {
            if (leagueManager.getComponent(3) instanceof UpdatePanel) {
                ((UpdatePanel) leagueManager.getComponent(3)).updateContent();
            }
            cl.show(leagueManager, "update");
        });

        JButton join = new JButton("join");
        join.addActionListener(e -> {
//            if (leagueManager.getComponent(4) instanceof JoinPanel) {
//                ((JoinPanel) leagueManager.getComponent(4)).updateContent();
//            }
            cl.show(leagueManager, "join");
        });

        add(title);
        add(insert);
        add(delete);
        add(update);
        add(join);
    }
}
