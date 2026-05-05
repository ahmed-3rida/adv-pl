package ui;

import models.Admin;
import models.ParentCourse;
import service.CourseService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class ManageParentCoursesFrame extends JFrame {

    private final Admin admin;

    private final JTextField tfTitle = UITheme.textField();
    private final JTextField tfDesc  = UITheme.textField();

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ManageParentCoursesFrame(Admin admin) {
        super("Manage Parent Courses");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(820, 560);
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
        JLabel titleLbl = new JLabel("Manage Parent Courses");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> goBack());
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────────────────────
        String[] cols = {"ID", "Title", "Description"};
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

        // ── Form + Buttons ─────────────────────────────────────────────────────
        JPanel south = new JPanel(new BorderLayout(10, 10));
        south.setBackground(UITheme.BG_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));

        JPanel formCard = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        formCard.add(UITheme.bodyLabel("Title:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        tfTitle.setPreferredSize(new Dimension(320, 32));
        formCard.add(tfTitle, gc);

        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0;
        formCard.add(UITheme.bodyLabel("Description:"), gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1;
        tfDesc.setPreferredSize(new Dimension(320, 32));
        formCard.add(tfDesc, gc);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);

        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnUpdate = UITheme.primaryButton("Update");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e  -> clearForm());

        btnPanel.add(btnAdd); btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete); btnPanel.add(btnClear);

        south.add(formCard, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadTable();
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (ParentCourse pc : CourseService.getAllParentCourses())
                tableModel.addRow(new Object[]{pc.getId(), pc.getTitle(), pc.getDescription()});
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfTitle.setText((String) tableModel.getValueAt(row, 1));
        tfDesc.setText((String)  tableModel.getValueAt(row, 2));
    }

    private void addCourse() {
        if (tfTitle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        try {
            CourseService.addParentCourse(tfTitle.getText().trim(), tfDesc.getText().trim());
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Parent course added.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void updateCourse() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        if (tfTitle.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Title is required.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        String id = (String) tableModel.getValueAt(row, 0);
        try {
            CourseService.updateParentCourse(id, tfTitle.getText().trim(), tfDesc.getText().trim());
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Parent course updated.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void deleteCourse() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        String id = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete parent course " + id + "?", "Confirm",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            CourseService.deleteParentCourse(id);
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Deleted.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void clearForm() {
        tfTitle.setText(""); tfDesc.setText("");
        table.clearSelection();
    }

    private void goBack() { dispose(); new AdminDashboard(admin).setVisible(true); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
