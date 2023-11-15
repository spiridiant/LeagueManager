package main.ui;

import main.delegates.TerminalOperationDelegate;
import main.model.Contract;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class UpdatePanel extends JPanel {
    private static final int PANEL_WIDTH = 1080;
    private static final int PANEL_HEIGHT = 720;

    private TerminalOperationDelegate delegate;
    private CardLayout cl;
    private JPanel leagueManager;

    private JTextField contractLength;
    private JPanel operationPanel;
    private JTextField contractBonus;
    private Contract selectedContract;


    public UpdatePanel(CardLayout cl, JPanel leagueManager, TerminalOperationDelegate delegate) {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));

        this.delegate = delegate;
        this.cl = cl;
        this.leagueManager = leagueManager;

        setLayout(new BorderLayout());
        setElements();

    }


    private void setElements() {
        removeAll();
        makeBackMenuButton();
        makeCenterPanel();
        makeUpdateButton();
    }

    public void makeBackMenuButton() {
        JButton back = new JButton("Back to the Menu");
        back.addActionListener(e -> cl.show(leagueManager, "menu"));
        add(back, BorderLayout.NORTH);
    }

    public void makeUpdateButton() {
        JButton updateButton = new JButton("Update Contract");
        updateButton.addActionListener((ActionEvent e) -> {
            if(selectedContract != null){
                String length = contractLength.getText();
                int newLength = 0;
                try {
                    newLength = Integer.parseInt(length);
                    if(newLength <= 0 || newLength > 5) {
                        throw new NumberFormatException();
                    }
                    String bonus = contractBonus.getText();
                    int newBonus = 0;
                    try {
                        newBonus = Integer.parseInt(bonus);
                        if(newBonus < 0 || newBonus > 75000) {
                            throw new NumberFormatException();
                        }
                        int id = selectedContract.getID();
                        boolean updated = delegate.updateContract(id, newBonus, newLength);
                        if(updated) {
                            JOptionPane.showMessageDialog(this, "Contract " + id +  " updated.");

                            setElements();
                            revalidate();
                            repaint();
                        } else {
                            JOptionPane.showMessageDialog(this, "Unable to update contract.");
                        }
                    } catch (NumberFormatException numE) {
                        JOptionPane.showMessageDialog(this, "Invalid Bonus amount, bonus needs to be an integer >= 0 and <= 75,000");
                    }
                } catch (NumberFormatException numE) {
                    JOptionPane.showMessageDialog(this, "Invalid contract length, contract length needs to be an integer > 0 and <= 5");
                }
            } else {
                JOptionPane.showMessageDialog(this, "No contract selected.");
            }
        });
        add(updateButton, BorderLayout.SOUTH);
    }

    public void makeCenterPanel() {
        JPanel centerPanel = new JPanel();
        makeContractPanel(centerPanel);
        add(centerPanel, BorderLayout.CENTER);
    }

    public void makeContractPanel(JPanel centerPanel) {
        Contract[] contractsArray = delegate.getContractInfo();
//        DefaultListModel<Contract> listModel = new DefaultListModel<>();
//        for(Contract contract : contractsArray) {
//            listModel.addElement(contract);
//        }
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Bonus");
        tableModel.addColumn("Player ID");
        tableModel.addColumn("Length");
        tableModel.addColumn("Value");
        tableModel.addColumn("Signed Date");

        for(Contract contract : contractsArray) {
            Object[] rowData = {contract.getID(), contract.getBonus(),contract.getPid(), contract.getLength(), contract.getValue(), contract.getSignedDate()};
            tableModel.addRow(rowData);
        }

        JTable contractTable = new JTable(tableModel);
        JScrollPane scrollPane = new JScrollPane(contractTable);
        scrollPane.setPreferredSize(new Dimension(600, 300));


        JButton select = new JButton("Select Contract");
        select.addActionListener((ActionEvent e) -> {
            int selectedRow = contractTable.getSelectedRow();
            if (selectedRow != -1) {
                selectedContract = getContractFromSelectedRow(contractTable, selectedRow);
                if(operationPanel != null) {
                    centerPanel.remove(operationPanel);
                }
                makeOperationPane(selectedContract.getBonus(), selectedContract.getLength());
                centerPanel.add(operationPanel);
                revalidate();
                repaint();
            } else {
                JOptionPane.showMessageDialog(this, "No contract selected.");
            }
        });

        JPanel contractSelectPanel = new JPanel();
        contractSelectPanel.setPreferredSize(new Dimension(600, 450));
        contractSelectPanel.setLayout(new BoxLayout(contractSelectPanel, BoxLayout.Y_AXIS));
        contractSelectPanel.add(scrollPane);
        contractSelectPanel.add(select);
        centerPanel.add(contractSelectPanel);
    }

    private Contract getContractFromSelectedRow(JTable table, int selectedRow) {
        int id = (int) table.getValueAt(selectedRow, 0);

        int bonus = (int) table.getValueAt(selectedRow, 1);
        int length = (int) table.getValueAt(selectedRow, 3);
        return new Contract(id, bonus, length);
    }

    public void makeOperationPane(int curr_bonus, int curr_length) {
        operationPanel = new JPanel();
        operationPanel.setLayout(new GridLayout(2, 2));
        operationPanel.setPreferredSize(new Dimension(200, 60));

        JLabel lengthLabel = new JLabel("Contract length");
        JLabel bonusLabel = new JLabel("Bonus");

        contractLength = new JTextField(Integer.toString(curr_length));
        contractLength.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField)e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        contractLength.setPreferredSize(new Dimension(100,30));
        contractBonus = new JTextField(Integer.toString(curr_bonus));
        contractBonus.addFocusListener(new FocusAdapter() {
            public void focusGained(FocusEvent e) {
                JTextField source = (JTextField)e.getComponent();
                source.setText("");
                source.removeFocusListener(this);
            }
        });
        contractBonus.setPreferredSize(new Dimension(100,30));
        operationPanel.add(lengthLabel);
        operationPanel.add(bonusLabel);
        operationPanel.add(contractLength);
        operationPanel.add(contractBonus);
    }
}
