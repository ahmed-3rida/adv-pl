package ui;

import models.*;
import service.StudentService;
import service.ValidationUtils;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class StudentDashboard extends JFrame {

    private final Student student;

    public StudentDashboard(Student student) {
        super("Student Portal — " + student.getName());
        this.student = student;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(920, 620);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        // ── Top bar ──────────────────────────────────────────────────────────
        root.add(buildTopBar(), BorderLayout.NORTH);

        // ── Tabbed content ────────────────────────────────────────────────────
        JTabbedPane tabs = new JTabbedPane();
        tabs.setBackground(UITheme.BG_DARK);
        tabs.setForeground(UITheme.TEXT_SECONDARY);
        tabs.setFont(UITheme.FONT_BTN);
        tabs.addTab("All Courses",   buildAllCoursesPanel());
        tabs.addTab("My Courses",    buildEnrolledCoursesPanel());
        tabs.addTab("My Grades",     buildGradesPanel());
        tabs.addTab("Survey",        buildSurveyPanel());
        tabs.addTab("Update Info",   buildUpdateInfoPanel());
        root.add(tabs, BorderLayout.CENTER);
    }

    // ── Top bar ───────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(UITheme.BG_CARD);
        bar.setBorder(new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));
        bar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JLabel title = new JLabel("Student Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel nameTag = new JLabel("[ " + student.getName() + " ]");
        nameTag.setFont(UITheme.FONT_BTN);
        nameTag.setForeground(UITheme.ACCENT);

        JButton logout = UITheme.secondaryButton("Logout");
        logout.addActionListener(e -> { dispose(); new LoginFrame().setVisible(true); });

        JPanel right = new JPanel(new FlowLayout(FlowLayout.RIGHT, 12, 0));
        right.setBackground(UITheme.BG_CARD);
        right.add(nameTag);
        right.add(logout);

        bar.add(title, BorderLayout.WEST);
        bar.add(right, BorderLayout.EAST);
        return bar;
    }

    // ── All Courses tab ───────────────────────────────────────────────────────
    private JPanel buildAllCoursesPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel heading = UITheme.h2Label("All Available Courses");
        p.add(heading, BorderLayout.NORTH);

        String[] cols = {"ID", "Parent", "Instructor", "Room", "Branch", "Price", "Start", "End", "Days"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        UITheme.styleTable(table);

        try {
            for (Course c : StudentService.getAllCourses()) {
                model.addRow(new Object[]{c.getId(), c.getParentCourseId(), c.getInstructorId(),
                        c.getRoom(), c.getBranch(), c.getPrice(),
                        c.getStartDate(), c.getEndDate(), c.getDays()});
            }
        } catch (IOException ex) { showError(ex.getMessage()); }

        p.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ── My Courses tab ────────────────────────────────────────────────────────
    private JPanel buildEnrolledCoursesPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel heading = UITheme.h2Label("My Enrolled Courses");
        p.add(heading, BorderLayout.NORTH);

        String[] cols = {"ID", "Parent", "Instructor", "Room", "Start", "End"};
        DefaultTableModel model = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(model);
        UITheme.styleTable(table);

        try {
            for (Course c : StudentService.getEnrolledCourses(student.getId())) {
                model.addRow(new Object[]{c.getId(), c.getParentCourseId(), c.getInstructorId(),
                        c.getRoom(), c.getStartDate(), c.getEndDate()});
            }
        } catch (IOException ex) { showError(ex.getMessage()); }

        p.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        return p;
    }

    // ── Grades tab ────────────────────────────────────────────────────────────
    private JPanel buildGradesPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 12));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = UITheme.h2Label("View My Grade");
        p.add(heading, BorderLayout.NORTH);

        JPanel card = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JLabel lbl = UITheme.bodyLabel("Course ID:");
        JTextField tfCourseId = UITheme.textField();
        tfCourseId.setPreferredSize(new Dimension(180, 34));
        JButton btnView = UITheme.primaryButton("View Grade");

        JLabel lblResult = new JLabel(" ", SwingConstants.CENTER);
        lblResult.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblResult.setForeground(UITheme.SUCCESS);

        btnView.addActionListener(e -> {
            String cid = tfCourseId.getText().trim();
            if (cid.isEmpty()) return;
            try {
                Grade g = StudentService.getGrade(student.getId(), cid);
                if (g != null) {
                    lblResult.setText("Your grade: " + g.getGrade() + " / 100");
                    lblResult.setForeground(UITheme.SUCCESS);
                } else {
                    lblResult.setText("Grade not published yet.");
                    lblResult.setForeground(UITheme.TEXT_SECONDARY);
                }
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0; card.add(lbl, gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1; card.add(tfCourseId, gc);
        gc.gridx = 2; gc.gridy = 0; gc.weightx = 0; card.add(btnView, gc);
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 3;
        card.add(lblResult, gc);

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ── Survey tab ────────────────────────────────────────────────────────────
    private JPanel buildSurveyPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 12));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = UITheme.h2Label("Submit a Course Survey");
        p.add(heading, BorderLayout.NORTH);

        JPanel card = UITheme.cardPanel(null);
        card.setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JTextField tfCourse  = UITheme.textField();
        JTextField tfRating  = UITheme.textField();
        tfRating.setText("1-5");
        JTextField tfComment = UITheme.textField();
        JButton    btnSubmit = UITheme.successButton("Submit Survey");

        int row = 0;
        addFormRow(card, gc, row++, "Course ID:", tfCourse);
        addFormRow(card, gc, row++, "Rating (1-5):", tfRating);
        addFormRow(card, gc, row++, "Comment:", tfComment);

        gc.gridx = 1; gc.gridy = row;
        card.add(btnSubmit, gc);

        btnSubmit.addActionListener(e -> {
            String cid = tfCourse.getText().trim();
            String comment = tfComment.getText().trim();
            int rating;
            try { rating = Integer.parseInt(tfRating.getText().trim()); }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Rating must be a number between 1 and 5."); return;
            }
            try {
                boolean ok = StudentService.submitSurvey(student.getId(), cid, rating, comment);
                if (ok) {
                    JOptionPane.showMessageDialog(this, "Survey submitted. Thank you!");
                    tfCourse.setText(""); tfRating.setText("1-5"); tfComment.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Could not submit survey (check enrollment or duplication).");
                }
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ── Update Info tab ───────────────────────────────────────────────────────
    private JPanel buildUpdateInfoPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 12));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = UITheme.h2Label("Update Personal Information");
        p.add(heading, BorderLayout.NORTH);

        JPanel card = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(8, 8, 8, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JTextField     tfName    = UITheme.textField(); tfName.setText(student.getName());
        JTextField     tfEmail   = UITheme.textField(); tfEmail.setText(student.getEmail());
        JPasswordField tfPass    = UITheme.passwordField();
        JTextField     tfPhone   = UITheme.textField(); tfPhone.setText(student.getPhone());
        JTextField     tfAddress = UITheme.textField(); tfAddress.setText(student.getAddress());
        JButton        btnSave   = UITheme.primaryButton("Save Changes");

        int row = 0;
        addFormRow(card, gc, row++, "Name:", tfName);
        addFormRow(card, gc, row++, "Email:", tfEmail);
        addFormRow(card, gc, row++, "New Password:", tfPass);
        addFormRow(card, gc, row++, "Phone:", tfPhone);
        addFormRow(card, gc, row++, "Address:", tfAddress);
        gc.gridx = 1; gc.gridy = row;
        card.add(btnSave, gc);

        btnSave.addActionListener(e -> {
            String name  = tfName.getText().trim();
            String email = tfEmail.getText().trim();
            String pass  = new String(tfPass.getPassword()).trim();

            if (!ValidationUtils.isValidEmail(email)) {
                JOptionPane.showMessageDialog(this, "Invalid email format.", "Validation Error", JOptionPane.ERROR_MESSAGE); return;
            }
            String phone = tfPhone.getText().trim();
            if (!phone.isEmpty() && !ValidationUtils.isValidPhone(phone)) {
                JOptionPane.showMessageDialog(this, "Phone must contain digits only.", "Validation Error", JOptionPane.ERROR_MESSAGE); return;
            }
            if (!pass.isEmpty() && !ValidationUtils.isValidPassword(pass)) {
                JOptionPane.showMessageDialog(this, "Password must be at least 8 characters.", "Validation Error", JOptionPane.ERROR_MESSAGE); return;
            }
            try {
                boolean ok = StudentService.updatePersonalInfo(student.getId(), name, email, pass, phone, tfAddress.getText().trim());
                if (ok) {
                    student.setName(name);
                    student.setEmail(email);
                    student.setPhone(phone);
                    student.setAddress(tfAddress.getText().trim());
                    setTitle("Student Portal — " + student.getName());
                    JOptionPane.showMessageDialog(this, "Information updated successfully.");
                }
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ── Helpers ───────────────────────────────────────────────────────────────
    private void addFormRow(JPanel panel, GridBagConstraints gc, int row, String label, JComponent field) {
        gc.gridx = 0; gc.gridy = row; gc.weightx = 0;
        panel.add(UITheme.bodyLabel(label), gc);
        gc.gridx = 1; gc.gridy = row; gc.weightx = 1;
        field.setPreferredSize(new Dimension(240, 34));
        panel.add(field, gc);
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
