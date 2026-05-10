package ui;

import models.Admin;
import models.Room;
import service.AdminService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class ManageRoomsFrame extends JFrame {


    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField tfRoomName = UITheme.textField();
    private final JComboBox<String> cbBranch = UITheme.comboBox();

    public ManageRoomsFrame(Admin admin) {
        super("Manage Rooms");

        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(760, 540);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);


        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel titleLbl = new JLabel("Manage Rooms");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> { dispose(); new AdminDashboard(admin).setVisible(true); });
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);


        String[] cols = {"Room Name", "Branch"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> fillForm());

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UITheme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        center.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);


        JPanel south = new JPanel(new BorderLayout(10, 10));
        south.setBackground(UITheme.BG_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));

        JPanel formCard = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        formCard.add(UITheme.bodyLabel("Room Name:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        tfRoomName.setPreferredSize(new Dimension(240, 32));
        formCard.add(tfRoomName, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        formCard.add(UITheme.bodyLabel("Select Branch:"), gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1;
        cbBranch.setPreferredSize(new Dimension(240, 32));
        formCard.add(cbBranch, gc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);

        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnUpdate = UITheme.primaryButton("Update");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addRoom());
        btnUpdate.addActionListener(e -> updateRoom());
        btnDelete.addActionListener(e -> deleteRoom());
        btnClear.addActionListener(e  -> { tfRoomName.setText(""); table.clearSelection(); });

        btnPanel.add(btnAdd); btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete); btnPanel.add(btnClear);

        south.add(formCard, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadBranches();
        loadTable();
    }

    private void loadBranches() {
        cbBranch.removeAllItems();
        try {
            for (String b : AdminService.getAllBranches()) cbBranch.addItem(b);
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error loading branches: " + e.getMessage()); }
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Room r : AdminService.getAllRooms())
                tableModel.addRow(new Object[]{r.getName(), r.getBranchName()});
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error loading rooms: " + e.getMessage()); }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfRoomName.setText((String) tableModel.getValueAt(row, 0));
        cbBranch.setSelectedItem((String) tableModel.getValueAt(row, 1));
    }

    private void addRoom() {
        String name   = tfRoomName.getText().trim();
        String branch = (String) cbBranch.getSelectedItem();
        if (name.isEmpty() || branch == null) {
            JOptionPane.showMessageDialog(this, "Enter room name and select a branch."); return;
        }
        try {
            if (AdminService.addRoom(name, branch)) {
                tfRoomName.setText(""); loadTable();
                JOptionPane.showMessageDialog(this, "Room added.");
            } else {
                JOptionPane.showMessageDialog(this, "Room already exists in this branch.");
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void updateRoom() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a room to update."); return; }
        String oldName   = (String) tableModel.getValueAt(row, 0);
        String oldBranch = (String) tableModel.getValueAt(row, 1);
        String newName   = tfRoomName.getText().trim();
        String newBranch = (String) cbBranch.getSelectedItem();
        if (newName.isEmpty() || newBranch == null) {
            JOptionPane.showMessageDialog(this, "Enter room name and select a branch."); return;
        }
        try {
            if (AdminService.updateRoom(oldName, oldBranch, newName, newBranch)) {
                tfRoomName.setText(""); table.clearSelection(); loadTable();
                JOptionPane.showMessageDialog(this, "Room updated.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to update room (duplicate or invalid input).");
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void deleteRoom() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a room to delete."); return; }
        String name   = (String) tableModel.getValueAt(row, 0);
        String branch = (String) tableModel.getValueAt(row, 1);
        if (JOptionPane.showConfirmDialog(this, "Delete room " + name + " in branch " + branch + "?",
                "Confirm", JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            if (AdminService.deleteRoom(name, branch)) {
                loadTable(); JOptionPane.showMessageDialog(this, "Room deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete room.");
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }
}
