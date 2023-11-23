package main.ui;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class MenuPanel extends JPanel {

    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;

    private static final int LOGO_HEIGHT = 400;
    private static final int GAP_HEIGHT = 15;

    public MenuPanel(CardLayout cl, JPanel leagueManager) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        //this.setBackground(new Color(34, 34, 34));

         setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

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
             cl.show(leagueManager, "update");
         });
         JButton join = new JButton("join");
         join.addActionListener(e -> {
             cl.show(leagueManager, "join");
         });

        JButton nested = new JButton("nested");
        nested.addActionListener(e -> {
            cl.show(leagueManager, "nested");
         });

        insert.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        delete.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        update.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        join.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        nested.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        add(Box.createVerticalStrut(LOGO_HEIGHT));

        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(insert);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(delete);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(update);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(join);
        add(Box.createVerticalStrut(GAP_HEIGHT));
        add(nested);
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
        g.drawImage(image, 220, 0, 630, 400, new Color(0, 0, 0, 0), this);
    }
}
