package main.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

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

        JTextField contractLength = new JTextField("Enter the new contract length here");
        contractLength.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField)e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        contractLength.setPreferredSize(new Dimension(200,30));
        JButton updateContractLengthButton = new JButton("update contract length");
        updateContractLengthButton.addActionListener((ActionEvent e) -> {
            String newLength = contractLength.getText();

        });

        JTextField bonus = new JTextField("Enter the new bonus amount here");
        bonus.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField)e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        bonus.setPreferredSize(new Dimension(200,30));
        JButton updateBonusButton = new JButton("update contract length");
        updateBonusButton.addActionListener((ActionEvent e) -> {
            String newBonus = contractLength.getText();

        });

        add(contractLength);
        add(updateContractLengthButton);

        add(bonus);
        add(updateBonusButton);
    }
}
