package ui;

import models.Admin;
import models.Student;
import service.AdminService;
import service.ValidationUtils;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ManageStudentsFrame extends JFrame {

    private final Admin admin;

    private final JTextField     tfName     = UITheme.textField();
    private final JTextField     tfEmail    = UITheme.textField();
    private final JPasswordField tfPassword = UITheme.passwordField();
    private final JTextField     tfPhone    = UITheme.textField();
    private final JTextField     tfAddress  = UITheme.textField();

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ManageStudentsFrame(Admin admin) {
        super("Manage Students");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(920, 600);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        // ── Top bar ──────────────────────────────────────────────────────────
        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0,0,1,0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel titleLbl = new JLabel("Manage Students");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> goBack());
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────────────────────
        String[] columns = {"ID", "Name", "Email", "Phone", "Address"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);
        table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        table.getSelectionModel().addListSelectionListener(e -> fillFormFromSelection());

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UITheme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 6, 14));
        center.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);

        // ── Form + Buttons (south) ─────────────────────────────────────────────
        JPanel south = new JPanel(new BorderLayout(10, 10));
        south.setBackground(UITheme.BG_DARK);
        south.setBorder(BorderFactory.createEmptyBorder(6, 14, 14, 14));

        JPanel formCard = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets  = new Insets(6, 8, 6, 8);
        gc.fill    = GridBagConstraints.HORIZONTAL;
        gc.weightx = 0;

        addFormRow(formCard, gc, 0, "Name:",     tfName);
        addFormRow(formCard, gc, 1, "Email:",    tfEmail);
        addFormRow(formCard, gc, 2, "Password:", tfPassword);
        addFormRow(formCard, gc, 3, "Phone:",    tfPhone);
        addFormRow(formCard, gc, 4, "Address:",  tfAddress);

        // Buttons
        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);

        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnUpdate = UITheme.primaryButton("Update");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        // Tint Delete red via custom paint
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addStudent());
        btnUpdate.addActionListener(e -> updateStudent());
        btnDelete.addActionListener(e -> deleteStudent());
        btnClear.addActionListener(e  -> clearForm());

        btnPanel.add(btnAdd); btnPanel.add(btnUpdate);
        btnPanel.add(btnDelete); btnPanel.add(btnClear);

        south.add(formCard,  BorderLayout.CENTER);
        south.add(btnPanel,  BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadTable();
    }

    private void addFormRow(JPanel panel, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        panel.add(UITheme.bodyLabel(label), gc);
        gc.gridx = 1; gc.gridy = row; gc.weightx = 1;
        field.setPreferredSize(new Dimension(220, 32));
        panel.add(field, gc);
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Student s : AdminService.getAllStudents())
                tableModel.addRow(new Object[]{s.getId(), s.getName(), s.getEmail(), s.getPhone(), s.getAddress()});
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void fillFormFromSelection() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfName.setText((String) tableModel.getValueAt(row, 1));
        tfEmail.setText((String) tableModel.getValueAt(row, 2));
        tfPassword.setText("");
        tfPhone.setText((String) tableModel.getValueAt(row, 3));
        tfAddress.setText((String) tableModel.getValueAt(row, 4));
    }

    private void addStudent() {
        if (!validateForm(true)) return;
        try {
            boolean ok = AdminService.addStudent(
                    tfName.getText().trim(), tfEmail.getText().trim(),
                    new String(tfPassword.getPassword()).trim(), tfPhone.getText().trim(),
                    tfAddress.getText().trim());
            if (ok) { clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Student added successfully."); }
            else    JOptionPane.showMessageDialog(this, "Email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void updateStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select a student first."); return; }
        if (!validateForm(false)) return;
        String id = (String) tableModel.getValueAt(row, 0);
        try {
            AdminService.updateStudent(id,
                    tfName.getText().trim(), tfEmail.getText().trim(),
                    new String(tfPassword.getPassword()).trim(), tfPhone.getText().trim(),
                    tfAddress.getText().trim());
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Student updated successfully.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void deleteStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select a student first."); return; }
        String id = (String) tableModel.getValueAt(row, 0);
        int confirm = JOptionPane.showConfirmDialog(this, "Delete student " + id + "?", "Confirm Delete", JOptionPane.YES_NO_OPTION);
        if (confirm != JOptionPane.YES_OPTION) return;
        try {
            AdminService.deleteStudent(id);
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Student deleted.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private boolean validateForm(boolean requirePass) {
        String name  = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        String pass  = new String(tfPassword.getPassword()).trim();
        if (name.isEmpty() || email.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Name and Email are required.", "Validation", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (!ValidationUtils.isValidEmail(email)) {
            JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE); return false;
        }
        String phone = tfPhone.getText().trim();
        if (!phone.isEmpty() && !ValidationUtils.isValidPhone(phone)) {
            JOptionPane.showMessageDialog(this, "Phone must contain digits only.", "Validation Error", JOptionPane.ERROR_MESSAGE); return false;
        }
        if (requirePass && pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Password is required.", "Validation", JOptionPane.WARNING_MESSAGE); return false;
        }
        if (!pass.isEmpty() && !ValidationUtils.isValidPassword(pass)) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE); return false;
        }
        return true;
    }

    private void clearForm() {
        tfName.setText(""); tfEmail.setText(""); tfPassword.setText("");
        tfPhone.setText(""); tfAddress.setText("");
        table.clearSelection();
    }

    private void goBack() { dispose(); new AdminDashboard(admin).setVisible(true); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
