package main.ui;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

public class LeagueManager extends JFrame {

    private JPanel leagueManager;

    public LeagueManager() {
        super("Basketball League Manager");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setUndecorated(false);
        setResizable(false);

        leagueManager = new JPanel();
        CardLayout cl = new CardLayout();
        leagueManager.setLayout(cl);

        MenuPanel menuPanel = new MenuPanel(cl, leagueManager);
        leagueManager.add(menuPanel, "menu");

        InsertPanel insertPanel = new InsertPanel(cl, leagueManager);
        leagueManager.add(insertPanel, "insert");

        DeletePanel deletePanel = new DeletePanel(cl, leagueManager);
        leagueManager.add(deletePanel, "delete");

        UpdatePanel updatePanel = new UpdatePanel(cl, leagueManager);
        leagueManager.add(updatePanel, "update");

        cl.show(leagueManager, "menu");
        add(leagueManager);

        pack();
        centreOnScreen();
        setVisible(true);
    }

    // Centres frame on desktop, from SpaceInvader
    // modifies: this
    // effects:  location of frame is set so frame is centred on desktop
    private void centreOnScreen() {
        Dimension scrn = Toolkit.getDefaultToolkit().getScreenSize();
        setLocation((scrn.width - getWidth()) / 2, (scrn.height - getHeight()) / 2);
    }

    /**
     * EFFECT:      Start the program
     */
    public static void main(String[] args) throws IOException, InterruptedException {
        new LeagueManager();
    }

}
