package ui;

import models.Admin;
import service.AdminService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ManageBranchesFrame extends JFrame {

    private final Admin admin;
    private final DefaultTableModel tableModel;
    private final JTable table;
    private final JTextField tfBranchName = UITheme.textField();

    public ManageBranchesFrame(Admin admin) {
        super("Manage Branches");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(680, 500);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        // ── Top bar ──────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel titleLbl = new JLabel("Manage Branches");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> { dispose(); new AdminDashboard(admin).setVisible(true); });
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────────────────────
        String[] cols = {"Branch Name"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> {
            int row = table.getSelectedRow();
            if (row >= 0) tfBranchName.setText((String) tableModel.getValueAt(row, 0));
        });

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UITheme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        center.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        // ── Form + Buttons ─────────────────────────────────────────────────────
        JPanel south = new JPanel(new BorderLayout(10, 10));
        south.setBackground(UITheme.BG_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));

        JPanel formCard = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        formCard.add(UITheme.bodyLabel("Branch Name:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        tfBranchName.setPreferredSize(new Dimension(260, 32));
        formCard.add(tfBranchName, gc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);

        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addBranch());
        btnDelete.addActionListener(e -> deleteBranch());
        btnClear.addActionListener(e  -> { tfBranchName.setText(""); table.clearSelection(); });

        btnPanel.add(btnAdd); btnPanel.add(btnDelete); btnPanel.add(btnClear);

        south.add(formCard, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (String b : AdminService.getAllBranches())
                tableModel.addRow(new Object[]{b});
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void addBranch() {
        String name = tfBranchName.getText().trim();
        if (name.isEmpty()) { JOptionPane.showMessageDialog(this, "Enter a branch name."); return; }
        try {
            if (AdminService.addBranch(name)) {
                tfBranchName.setText(""); loadTable();
                JOptionPane.showMessageDialog(this, "Branch added.");
            } else {
                JOptionPane.showMessageDialog(this, "Branch already exists or is invalid.");
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }

    private void deleteBranch() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a branch to delete."); return; }
        String name = (String) tableModel.getValueAt(row, 0);
        String msg  = "Delete branch '" + name + "'?\n\nWARNING: This will also delete ALL rooms in this branch!";
        if (JOptionPane.showConfirmDialog(this, msg, "Confirm Deletion",
                JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE) != JOptionPane.YES_OPTION) return;
        try {
            if (AdminService.deleteBranch(name)) {
                loadTable(); tfBranchName.setText(""); table.clearSelection();
                JOptionPane.showMessageDialog(this, "Branch '" + name + "' and all its rooms deleted.");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to delete branch.");
            }
        } catch (IOException e) { JOptionPane.showMessageDialog(this, "Error: " + e.getMessage()); }
    }
}
