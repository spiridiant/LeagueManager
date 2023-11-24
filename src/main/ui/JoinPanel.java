package main.ui;

import main.Exception.InvalidSalaryException;
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

    //    private JScrollPane scrollPane;
    public JoinPanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent evt) {
                setElements("0");
            }
        });
    }

    public void setElements(String salary) {
        removeAll();
        makeBackMenuButton();
        makeOperationPanel();
        makeDisplayPanel(salary);
        revalidate();
        repaint();
    }

    public void makeBackMenuButton() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));
        back.setAlignmentX(JComponent.CENTER_ALIGNMENT);
        add(back);
    }

    public void makeOperationPanel() {
        JPanel operationPanel = new JPanel();
        operationPanel.setPreferredSize(new Dimension(200, 50));
        operationPanel.setBackground(getBackground());
        JLabel title = new JLabel("Enter the lower end salary: ");
        title.setForeground(Color.WHITE);
        JTextField salaryInput = new JTextField();
        salaryInput.setPreferredSize(new Dimension(100, 30));
        JButton filter = new JButton("Filter");
        filter.addActionListener((ActionEvent e) -> {
            String input = salaryInput.getText();
            setElements(input);
            revalidate();
            repaint();

        });
        operationPanel.add(title);
        operationPanel.add(salaryInput);
        operationPanel.add(filter);
        add(operationPanel);
    }

    public void makeDisplayPanel(String salary) {
        TeamStaff[] staffArray = new TeamStaff[0];
        try {
            staffArray = delegate.getTeamStaffInfo(salary);
        } catch (InvalidSalaryException e) {
            JOptionPane.showMessageDialog(this, e.getMessage());
        }
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("Team Name");
        tableModel.addColumn("Home City");
        tableModel.addColumn("Staff Name");
        tableModel.addColumn("Salary");
        for (TeamStaff staff : staffArray) {
            Object[] rowData = {staff.getTName(), staff.getCity(), staff.getStaffName(), staff.getSalary()};
            tableModel.addRow(rowData);
        }
        JTable staffTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(staffTable);
        scrollPane.setBackground(getBackground());
        JPanel displayPanel = new JPanel();
        displayPanel.setBackground(getBackground());
        displayPanel.setPreferredSize(new Dimension(600, 400));
        displayPanel.add(scrollPane);
        add(displayPanel);
    }
}