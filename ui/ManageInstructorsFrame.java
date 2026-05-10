package ui;

import models.Admin;
import models.Instructor;
import service.AdminService;
import service.ValidationUtils;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class ManageInstructorsFrame extends JFrame {

    private final Admin admin;

    private final JTextField     tfName  = UITheme.textField();
    private final JTextField     tfEmail = UITheme.textField();
    private final JPasswordField tfPass  = UITheme.passwordField();
    private final JTextField     tfSpec  = UITheme.textField();
    private final JTextField     tfPhone = UITheme.textField();

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ManageInstructorsFrame(Admin admin) {
        super("Manage Instructors");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(920, 600);
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
        JLabel titleLbl = new JLabel("Manage Instructors");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> goBack());
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);


        String[] cols = {"ID", "Name", "Email", "Specialization", "Phone"};
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

        addFormRow(formCard, gc, 0, "Name:",           tfName);
        addFormRow(formCard, gc, 1, "Email:",          tfEmail);
        addFormRow(formCard, gc, 2, "Password:",       tfPass);
        addFormRow(formCard, gc, 3, "Specialization:", tfSpec);
        addFormRow(formCard, gc, 4, "Phone:",          tfPhone);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);
        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnUpdate = UITheme.primaryButton("Update");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addInstructor());
        btnUpdate.addActionListener(e -> updateInstructor());
        btnDelete.addActionListener(e -> deleteInstructor());
        btnClear.addActionListener(e  -> clearForm());

        btnPanel.add(btnAdd); btnPanel.add(btnUpdate); btnPanel.add(btnDelete); btnPanel.add(btnClear);

        south.add(formCard, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
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
            for (Instructor i : AdminService.getAllInstructors())
                tableModel.addRow(new Object[]{i.getId(), i.getName(), i.getEmail(), i.getSpecialization(), i.getPhone()});
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        tfName.setText((String) tableModel.getValueAt(row, 1));
        tfEmail.setText((String) tableModel.getValueAt(row, 2));
        tfPass.setText("");
        tfSpec.setText((String) tableModel.getValueAt(row, 3));
        tfPhone.setText((String) tableModel.getValueAt(row, 4));
    }

    private void addInstructor() {
        if (!validate(true)) return;
        try {
            boolean ok = AdminService.addInstructor(tfName.getText().trim(), tfEmail.getText().trim(),
                    new String(tfPass.getPassword()).trim(), tfSpec.getText().trim(), tfPhone.getText().trim());
            if (ok) { clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Instructor added successfully."); }
            else JOptionPane.showMessageDialog(this, "Email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void updateInstructor() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select an instructor first."); return; }
        if (!validate(false)) return;
        String id = (String) tableModel.getValueAt(row, 0);
        try {
            AdminService.updateInstructor(id, tfName.getText().trim(), tfEmail.getText().trim(),
                    new String(tfPass.getPassword()).trim(), tfSpec.getText().trim(), tfPhone.getText().trim());
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Instructor updated.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void deleteInstructor() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Please select an instructor first."); return; }
        String id = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete instructor " + id + "?", "Confirm",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            AdminService.deleteInstructor(id);
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Instructor deleted.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private boolean validate(boolean requirePass) {
        String name  = tfName.getText().trim();
        String email = tfEmail.getText().trim();
        String pass  = new String(tfPass.getPassword()).trim();
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
        tfName.setText(""); tfEmail.setText(""); tfPass.setText(""); tfSpec.setText(""); tfPhone.setText("");
        table.clearSelection();
    }

    private void goBack() { dispose(); new AdminDashboard(admin).setVisible(true); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
