package main.ui;

import main.delegates.TerminalOperationDelegate;

import javax.swing.*;
import java.awt.*;

public class TerminalWindow extends JFrame {
    public static final Color BACKGROUND_COLOR = new Color(22, 30, 51);
    private JPanel leagueManager;

    public TerminalWindow(TerminalOperationDelegate delegate) {
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

        DeletePanel deletePanel = new DeletePanel(cl, leagueManager, delegate);
        leagueManager.add(deletePanel, "delete");

        UpdatePanel updatePanel = new UpdatePanel(cl, leagueManager, delegate);
        leagueManager.add(updatePanel, "update");

        JoinPanel joinPanel = new JoinPanel(cl, leagueManager, delegate);
        leagueManager.add(joinPanel, "join");

        NestedAggregationGroupByPanel nestedAggregationGroupByPanel = new NestedAggregationGroupByPanel(cl, leagueManager, delegate);
        leagueManager.add(nestedAggregationGroupByPanel, "nested");

        leagueManager.setBackground(BACKGROUND_COLOR);
        menuPanel.setBackground(BACKGROUND_COLOR);
        insertPanel.setBackground(BACKGROUND_COLOR);
        deletePanel.setBackground(BACKGROUND_COLOR);
        updatePanel.setBackground(BACKGROUND_COLOR);
        joinPanel.setBackground(BACKGROUND_COLOR);
        nestedAggregationGroupByPanel.setBackground(BACKGROUND_COLOR);


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
}
