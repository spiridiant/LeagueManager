package main.ui;

import main.delegates.TerminalOperationDelegate;
import main.model.TeamStaff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

public class JoinPanel extends JPanel {
    private TerminalOperationDelegate delegate;
    private CardLayout cl;
    private JPanel leagueManager;

    private JScrollPane scrollPane;
    public JoinPanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                setElements();
            }
        });
    }

    public void setElements() {
        makeBackMenuButton();
        makeOperationPanel();
        makeDisplayPanel(0);
        revalidate();
        repaint();
    }

    public void makeBackMenuButton() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));
        add(back);
    }

    public void makeOperationPanel() {
        JPanel operationPanel = new JPanel();
        JLabel title = new JLabel("Enter the lower end salary: ");
        JTextField salaryInput = new JTextField();
        salaryInput.setPreferredSize(new Dimension(100, 30));
        JButton filter = new JButton("Filter");
        filter.addActionListener((ActionEvent e) -> {
            try {
                String input = salaryInput.getText();
                int salary = Integer.parseInt(input);
                if(scrollPane != null) {
                    remove(scrollPane);
                }
                makeDisplayPanel(salary);
                revalidate();
                repaint();
            } catch (NumberFormatException num) {
                JOptionPane.showMessageDialog(this, "Invalid salary, it needs to be an integer");
            }
        });
        operationPanel.add(title);
        operationPanel.add(salaryInput);
        operationPanel.add(filter);
        add(operationPanel);
    }
    public void makeDisplayPanel(int salary) {
        TeamStaff[] staffArray = delegate.getTeamStaffInfo(salary);
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Team Name");
        tableModel.addColumn("Home City");
        tableModel.addColumn("Staff Name");
        tableModel.addColumn("Salary");
        for(TeamStaff staff : staffArray) {
            Object[] rowData = {staff.getTName(), staff.getCity(),staff.getStaffName(), staff.getSalary()};
            tableModel.addRow(rowData);
        }
        JTable staffTable = new JTable(tableModel);
        scrollPane = new JScrollPane(staffTable);
        scrollPane.setPreferredSize(new Dimension(500, 400));
        add(scrollPane);
    }
}