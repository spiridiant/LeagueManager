package main.ui;

import main.Exception.NoAttributeSelectedException;
import main.Exception.NoComparatorSelectedException;
import main.delegates.TerminalOperationDelegate;
import main.model.Team;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class SelectPanel extends JPanel {

    private DefaultTableModel tableModel;

    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;
    private TerminalOperationDelegate delegate;
    private CardLayout cl;
    private JPanel leagueManager;
    private JComboBox<String> attributeComboBox;
    private JComboBox<String> comparisonComboBox;
    private JTextField textFieldValue;

    JPanel centerPanel;

    public SelectPanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(new Color(22, 30, 51));

        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;

        setLayout(new BorderLayout());

        JPanel filterPanel = createFilterPanel(); // Create the filter panel
        add(filterPanel, BorderLayout.NORTH);    // Add the filter panel to the top (North)

        tableModel = new DefaultTableModel();

        setElements();

        centerPanel = new JPanel();
        centerPanel.setBackground(new Color(22, 30, 51));
        makeTeamPanel(centerPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    private void setElements() {
        removeAll();
        makeBackMenuButton();
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

    private JPanel createFilterPanel() {
        JPanel filterPanel = new JPanel();

        JLabel label = new JLabel("Values are case sensitive.");

        String[] attributes = {"Team Name", "Cap Space", "Arena", "City Name", "Division Name"};
        attributeComboBox = new JComboBox<>(attributes);

        String[] comparisons = {">=", ">", "=", "<", "<="};
        comparisonComboBox = new JComboBox<>(comparisons);

        textFieldValue = new JTextField(10);

        JButton selectButton = new JButton("Select Teams");
        selectButton.addActionListener(this::selectTeams);

        filterPanel.add(attributeComboBox);
        filterPanel.add(comparisonComboBox);
        filterPanel.add(textFieldValue);
        filterPanel.add(selectButton, BorderLayout.SOUTH);
        filterPanel.add(label);

        return filterPanel;
    }

    public void makeTeamPanel(JPanel centerPanel) {
        centerPanel.removeAll();

        JPanel filterPanel = createFilterPanel(); // Create the filter panel

        Team[] teamsArray = delegate.getTeamInfo();

        tableModel.addColumn("City");
        tableModel.addColumn("Name");
        tableModel.addColumn("Arena");
        tableModel.addColumn("Division");
        tableModel.addColumn("Cap Space");

        for (Team team : teamsArray) {
            Object[] rowData = {
                    team.getCity(),
                    team.getName(),
                    team.getArena(),
                    team.getDivision(),
                    team.getCap_space()
            };
            tableModel.addRow(rowData);
        }

        JTable teamTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(teamTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));

        JPanel teamSelectPanel = new JPanel();
        teamSelectPanel.setBackground(new Color(22, 30, 51));
        teamSelectPanel.setPreferredSize(new Dimension(600, 445));
        teamSelectPanel.setLayout(new BoxLayout(teamSelectPanel, BoxLayout.Y_AXIS));

        teamSelectPanel.add(filterPanel); // Add filterPanel to teamSelectPanel
        teamSelectPanel.add(scrollPane); // Add scrollPane to teamSelectPanel

        centerPanel.add(teamSelectPanel, BorderLayout.CENTER); // Use BorderLayout.CENTER for teamSelectPanel
    }

    private void selectTeams(ActionEvent e) {
        String attribute = (String) attributeComboBox.getSelectedItem();
        String comparison = (String) comparisonComboBox.getSelectedItem();
        String value = textFieldValue.getText();

        try {
            Team[] teams = delegate.selectTeams(attribute, comparison, value);

            updateTable(teams);

        } catch (NoAttributeSelectedException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        } catch (NoComparatorSelectedException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage());
        }
    }

    private void updateTable(Team[] teams) {
        tableModel.setRowCount(0);
        tableModel.setColumnCount(0);

        tableModel.addColumn("Team Name");
        tableModel.addColumn("City");
        tableModel.addColumn("Arena");
        tableModel.addColumn("Division");
        tableModel.addColumn("Cap Space");

        for (Team team : teams) {
            Object[] rowData = {
                    team.getName(),
                    team.getCity(),
                    team.getArena(),
                    team.getDivision(),
                    team.getCap_space()
            };
            tableModel.addRow(rowData);
        }
    }


}
