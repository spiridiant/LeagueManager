package main.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;
    public static final Dimension BUTTON_SIZE = new Dimension(120, 25);
    private static final int LOGO_WIDTH = 550;
    private static final int LOGO_HEIGHT = 350;
    private static final int GAP_HEIGHT = 10;

    public MenuPanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        //this.setBackground(new Color(34, 34, 34));

         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

         JButton insert = new JButton("Insert");
         insert.addActionListener(e -> {
            if (leagueManager.getComponent(1) instanceof InsertPanel) {
                ((InsertPanel) leagueManager.getComponent(1)).updateContent();
            }
             cl.show(leagueManager, "insert");
         });

         JButton delete = new JButton("Delete");
         delete.addActionListener(e -> {
            if (leagueManager.getComponent(2) instanceof DeletePanel) {
                ((DeletePanel) leagueManager.getComponent(2)).updateContent();
            }
             cl.show(leagueManager, "delete");
         });

         JButton update = new JButton("Update");
         update.addActionListener(e -> {
             cl.show(leagueManager, "update");
         });

        JButton select = new JButton("Select");
        select.addActionListener(e -> {
            cl.show(leagueManager, "select");
        });

         JButton join = new JButton("Join");
         join.addActionListener(e -> {
             cl.show(leagueManager, "join");
         });

        JButton groupBy = new JButton("Group By");
        groupBy.addActionListener(e -> {
            cl.show(leagueManager, "group_by");
        });

        JButton nested = new JButton("Nested");
        nested.addActionListener(e -> {
            cl.show(leagueManager, "nested");
         });

        JButton division = new JButton("Division");
        division.addActionListener(e -> {
            cl.show(leagueManager, "division");
        });

        JButton project = new JButton("project");
        project.addActionListener(e -> {
            cl.show(leagueManager, "project");
        });

        JButton having = new JButton("Find Player Hegihts");
        having.addActionListener(e -> {
            cl.show(leagueManager, "having");
        });

        JButton quit = new JButton("Quit");
        quit.addActionListener(e -> {
            System.exit(0);
        });

        insert.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        delete.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        update.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        select.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        join.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        groupBy.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        nested.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        division.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        project.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        having.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        quit.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        insert.setMaximumSize(BUTTON_SIZE);
        delete.setMaximumSize(BUTTON_SIZE);
        update.setMaximumSize(BUTTON_SIZE);
        select.setMaximumSize(BUTTON_SIZE);
        join.setMaximumSize(BUTTON_SIZE);
        groupBy.setMaximumSize(BUTTON_SIZE);
        nested.setMaximumSize(BUTTON_SIZE);
        division.setMaximumSize(BUTTON_SIZE);
        project.setMaximumSize(BUTTON_SIZE);
        having.setMaximumSize(BUTTON_SIZE);
        quit.setMaximumSize(BUTTON_SIZE);

        add(Box.createVerticalStrut(LOGO_HEIGHT));

        add(insert);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(delete);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(update);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(select);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(join);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(groupBy);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(nested);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(division);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(project);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(having);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(quit);
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawLogo(g);
    }

    private void drawLogo(Graphics g) {
        Image image;
        try {
            image = ImageIO.read(getClass().getResource("/Images/menuLogo.png"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        g.drawImage(image, (PANEL_WIDTH - LOGO_WIDTH) / 2 - 5, 0, LOGO_WIDTH, LOGO_HEIGHT, new Color(0, 0, 0, 0), this);
    }
}
