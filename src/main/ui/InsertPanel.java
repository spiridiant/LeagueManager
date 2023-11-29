package main.ui;

import main.Exception.NonExistentTeamException;
import main.delegates.TerminalOperationDelegate;
import main.model.Player;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.time.LocalDateTime;

public class InsertPanel extends JPanel {

    private DefaultTableModel tableModel;
    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;

    private TerminalOperationDelegate delegate;
    private CardLayout cl;
    private JPanel leagueManager;

    JTextField textFieldYearSigned = new JTextField(20);
    JTextField textFieldDOB = new JTextField(20);
    JTextField textFieldHeight = new JTextField(20);
    JTextField textFieldName = new JTextField(20);
    JTextField textFieldJerseyNum = new JTextField(20);
    JTextField textFieldTeamName = new JTextField(20);
    JTextField textFieldCityName = new JTextField(20);

    JPanel centerPanel;

    public InsertPanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(new Color(22, 30, 51));

        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;

        tableModel = new DefaultTableModel();

        setLayout(new BorderLayout());
        setElements();
        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(22, 30, 51));
        makePlayerPanel(centerPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void setElements() {
        removeAll();
        makeBackMenuButton();
        makeInsertButton();
        revalidate();
        repaint();
    }

    public void makeBackMenuButton() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));
        back.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        JPanel northPanel = new JPanel();
        northPanel.setBackground(getBackground());
        northPanel.add(back);
        add(northPanel, BorderLayout.NORTH);
    }

    public void makeInsertButton() {
        JButton insertButton = new JButton("Insert Player");
        insertButton.addActionListener((ActionEvent e) -> {
            insertPlayer();
        });
        add(insertButton, BorderLayout.SOUTH);
    }

    public int generateRandomPID() {
        // Generate a random 7-digit PID
        return (int) (Math.random() * 9000000) + 1000000;
    }

    public boolean checkUniquePID(int generatedPID) {
        Player[] playersArray = delegate.getPlayerInfo();

        for (Player player : playersArray) {
            if (player.getPid() == generatedPID) {
                return false;
            }
        }
        return true;
    }

    public void insertPlayer() {
        LocalDateTime yearSigned = LocalDateTime.parse(textFieldYearSigned.getText() + "T00:00");
        LocalDateTime dob = LocalDateTime.parse(textFieldDOB.getText() + "T00:00");
        String height = textFieldHeight.getText();
        String name = textFieldName.getText();
        String jerseyNum = textFieldJerseyNum.getText();
        String teamName = textFieldTeamName.getText();
        String cityName = textFieldCityName.getText();

        int intPID;
        boolean isUnique;

        // Generate a unique PID that doesn't match any existing player's PID
        do {
            intPID = generateRandomPID();
            isUnique = checkUniquePID(intPID);
        } while (!isUnique);

        int intHeight = Integer.parseInt(height);
        int intJNum = Integer.parseInt(jerseyNum);

        try {
            boolean inserted = delegate.insertPlayer(yearSigned, dob, intHeight, name, intJNum, intPID, teamName, cityName);
            if (inserted) {
                JOptionPane.showMessageDialog(this, "Player inserted.");
                updateContent();
            } else {
                JOptionPane.showMessageDialog(this, "Unable to add Player");
            }
        } catch (NonExistentTeamException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    public void makePlayerPanel(JPanel centerPanel) {
        centerPanel.removeAll();
        Player[] playersArray = delegate.getPlayerInfo();

        tableModel.addColumn("Debut Year");
        tableModel.addColumn("Date of Birth");
        tableModel.addColumn("Height");
        tableModel.addColumn("Name");
        tableModel.addColumn("Jersey#");
        tableModel.addColumn("PID");
        tableModel.addColumn("Team");
        tableModel.addColumn("City");

        for(Player player : playersArray) {
            Object[] rowData = {player.getDebutYear(), player.getDateofBirth(),
                    player.getHeight(), player.getName(), player.getJerseyNum(),
                    player.getPid(), player.getTName(), player.getCity()};
            tableModel.addRow(rowData);
        }

        JTable playerTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(playerTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JPanel inputPanel = new JPanel(new GridLayout(7, 2));
        inputPanel.add(new JLabel("Year Signed (YYYY-MM-DD):"));
        inputPanel.add(textFieldYearSigned);
        inputPanel.add(new JLabel("Date of Birth (YYYY-MM-DD):"));
        inputPanel.add(textFieldDOB);
        inputPanel.add(new JLabel("Height (in cm):"));
        inputPanel.add(textFieldHeight);
        inputPanel.add(new JLabel("Name:"));
        inputPanel.add(textFieldName);
        inputPanel.add(new JLabel("Jersey Number:"));
        inputPanel.add(textFieldJerseyNum);
        inputPanel.add(new JLabel("Team Name:"));
        inputPanel.add(textFieldTeamName);
        inputPanel.add(new JLabel("City Name:"));
        inputPanel.add(textFieldCityName);

        JPanel playerSelectPanel = new JPanel();
        playerSelectPanel.setBackground(new Color(22, 30, 51));
        playerSelectPanel.setPreferredSize(new Dimension(600, 445));
        playerSelectPanel.setLayout(new BoxLayout(playerSelectPanel, BoxLayout.Y_AXIS));

        playerSelectPanel.add(scrollPane);
        centerPanel.add(playerSelectPanel);
        centerPanel.add(inputPanel);

    }

    public void updateContent() {
        tableModel.setRowCount(0);

        Player[] playersArray = delegate.getPlayerInfo();
        for (Player player : playersArray) {
            Object[] rowData = {player.getDebutYear(), player.getDateofBirth(),
                    player.getHeight(), player.getName(), player.getJerseyNum(),
                    player.getPid(), player.getTName(), player.getCity()};
            tableModel.addRow(rowData);
        }

        tableModel.fireTableDataChanged();
    }
}
