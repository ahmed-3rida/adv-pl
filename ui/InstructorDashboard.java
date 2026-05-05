package ui;

import models.*;
import service.InstructorService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class InstructorDashboard extends JFrame {

    private final Instructor instructor;
    private DefaultTableModel coursesModel;
    private DefaultTableModel surveysModel;

    public InstructorDashboard(Instructor instructor) {
        super("Instructor Portal — " + instructor.getName());
        this.instructor = instructor;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(980, 660);
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
        tabs.addTab("My Courses",       buildCoursesPanel());
        tabs.addTab("Add/Update Grades", buildGradesPanel());
        tabs.addTab("Publish Grades",    buildPublishPanel());
        tabs.addTab("View Surveys",      buildSurveysPanel());
        root.add(tabs, BorderLayout.CENTER);
    }

    // ── Top bar ───────────────────────────────────────────────────────────────
    private JPanel buildTopBar() {
        JPanel bar = new JPanel(new BorderLayout());
        bar.setBackground(UITheme.BG_CARD);
        bar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));

        JLabel title = new JLabel("Instructor Portal");
        title.setFont(new Font("Segoe UI", Font.BOLD, 18));
        title.setForeground(UITheme.TEXT_PRIMARY);

        JLabel nameTag = new JLabel("[ " + instructor.getName() + " ]");
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

    // ── My Courses tab ────────────────────────────────────────────────────────
    private JPanel buildCoursesPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel heading = UITheme.h2Label("My Assigned Courses");
        p.add(heading, BorderLayout.NORTH);

        String[] cols = {"ID", "Parent", "Room", "Branch", "Price", "Start", "End", "Days", "Students", "Published"};
        coursesModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(coursesModel);
        UITheme.styleTable(table);
        loadCoursesData();

        p.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        return p;
    }

    private void loadCoursesData() {
        coursesModel.setRowCount(0);
        try {
            for (Course c : InstructorService.getAssignedCourses(instructor.getId())) {
                coursesModel.addRow(new Object[]{c.getId(), c.getParentCourseId(), c.getRoom(), c.getBranch(),
                        c.getPrice(), c.getStartDate(), c.getEndDate(), c.getDays(),
                        c.getStudentIds().size(), (c.isGradesPublished() ? "YES" : "NO")});
            }
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    // ── Add/Update Grades tab ─────────────────────────────────────────────────
    private JPanel buildGradesPanel() {
        JPanel outer = new JPanel(new BorderLayout(0, 12));
        outer.setBackground(UITheme.BG_DARK);
        outer.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JLabel heading = UITheme.h2Label("Manage Student Grades");
        outer.add(heading, BorderLayout.NORTH);

        // Form card
        JPanel card = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(6, 8, 6, 8);
        gc.anchor = GridBagConstraints.WEST;
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JTextField tfCourse  = UITheme.textField(); tfCourse.setPreferredSize(new Dimension(180, 32));
        JTextField tfStudent = UITheme.textField(); tfStudent.setPreferredSize(new Dimension(180, 32));
        JTextField tfGrade   = UITheme.textField(); tfGrade.setPreferredSize(new Dimension(180, 32));
        JButton btnLoad      = UITheme.secondaryButton("Load Grades");
        JButton btnSave      = UITheme.primaryButton("Save Grade");

        String[] cols = {"Course ID", "Student ID", "Grade"};
        DefaultTableModel gradeModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable gradeTable = new JTable(gradeModel);
        UITheme.styleTable(gradeTable);

        // Row 0: Course ID + Load
        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0; card.add(UITheme.bodyLabel("Course ID:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1; card.add(tfCourse, gc);
        gc.gridx = 2; gc.gridy = 0; gc.weightx = 0; card.add(btnLoad, gc);
        // Row 1: Student ID
        gc.gridx = 0; gc.gridy = 1; gc.weightx = 0; card.add(UITheme.bodyLabel("Student ID:"), gc);
        gc.gridx = 1; gc.gridy = 1; gc.weightx = 1; card.add(tfStudent, gc);
        // Row 2: Grade + Save
        gc.gridx = 0; gc.gridy = 2; gc.weightx = 0; card.add(UITheme.bodyLabel("Grade (0-100):"), gc);
        gc.gridx = 1; gc.gridy = 2; gc.weightx = 1; card.add(tfGrade, gc);
        gc.gridx = 2; gc.gridy = 2; gc.weightx = 0; card.add(btnSave, gc);

        btnLoad.addActionListener(e -> {
            String cid = tfCourse.getText().trim();
            if (cid.isEmpty()) return;
            gradeModel.setRowCount(0);
            try {
                for (Grade g : InstructorService.getGradesForCourse(cid))
                    gradeModel.addRow(new Object[]{g.getCourseId(), g.getStudentId(), g.getGrade()});
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        gradeTable.getSelectionModel().addListSelectionListener(e -> {
            int row = gradeTable.getSelectedRow();
            if (row < 0) return;
            tfCourse.setText((String) gradeModel.getValueAt(row, 0));
            tfStudent.setText((String) gradeModel.getValueAt(row, 1));
            tfGrade.setText(String.valueOf(gradeModel.getValueAt(row, 2)));
        });

        btnSave.addActionListener(e -> {
            String cid = tfCourse.getText().trim();
            String sid = tfStudent.getText().trim();
            double grade;
            try { grade = Double.parseDouble(tfGrade.getText().trim()); }
            catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, "Invalid grade value."); return;
            }
            try {
                boolean ok = InstructorService.addOrUpdateGrade(cid, sid, grade);
                if (ok) {
                    gradeModel.setRowCount(0);
                    for (Grade g : InstructorService.getGradesForCourse(cid))
                        gradeModel.addRow(new Object[]{g.getCourseId(), g.getStudentId(), g.getGrade()});
                    JOptionPane.showMessageDialog(this, "Grade saved successfully.");
                } else {
                    JOptionPane.showMessageDialog(this, "Could not save grade (check IDs or published status).");
                }
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        outer.add(card, BorderLayout.NORTH);
        outer.add(UITheme.scrollPane(gradeTable), BorderLayout.CENTER);
        return outer;
    }

    // ── Publish Grades tab ────────────────────────────────────────────────────
    private JPanel buildPublishPanel() {
        JPanel p = new JPanel(new BorderLayout(8, 12));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel heading = UITheme.h2Label("Publish Grades");
        p.add(heading, BorderLayout.NORTH);

        JPanel card = UITheme.cardPanel(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        gc.insets = new Insets(10, 10, 10, 10);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        JComboBox<String> cbCourse = UITheme.comboBox();
        try {
            for (Course c : InstructorService.getAssignedCourses(instructor.getId()))
                cbCourse.addItem(c.getId() + " — " + c.getParentCourseId()
                        + " (Published: " + c.isGradesPublished() + ")");
        } catch (IOException ex) { showError(ex.getMessage()); }

        JButton btnPublish = UITheme.successButton("Publish Grades for Selected Course");

        gc.gridx = 0; gc.gridy = 0; gc.weightx = 0;
        card.add(UITheme.bodyLabel("Select Course:"), gc);
        gc.gridx = 1; gc.gridy = 0; gc.weightx = 1;
        card.add(cbCourse, gc);
        gc.gridx = 0; gc.gridy = 1; gc.gridwidth = 2;
        card.add(btnPublish, gc);

        btnPublish.addActionListener(e -> {
            if (cbCourse.getSelectedIndex() < 0) return;
            String item = (String) cbCourse.getSelectedItem();
            String cId  = item.split(" ")[0];
            try {
                boolean ok = InstructorService.publishGrades(cId, instructor.getId());
                if (ok) {
                    JOptionPane.showMessageDialog(this, "✅ Grades published for course " + cId);
                    loadCoursesData();
                    cbCourse.removeAllItems();
                    for (Course c : InstructorService.getAssignedCourses(instructor.getId()))
                        cbCourse.addItem(c.getId() + " — " + c.getParentCourseId()
                                + " (Published: " + c.isGradesPublished() + ")");
                } else {
                    JOptionPane.showMessageDialog(this, "Could not publish (already published or wrong course).");
                }
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        p.add(card, BorderLayout.CENTER);
        return p;
    }

    // ── View Surveys tab ──────────────────────────────────────────────────────
    private JPanel buildSurveysPanel() {
        JPanel p = new JPanel(new BorderLayout(0, 10));
        p.setBackground(UITheme.BG_DARK);
        p.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));

        JPanel topRow = new JPanel(new BorderLayout());
        topRow.setBackground(UITheme.BG_DARK);
        JLabel heading = UITheme.h2Label("Course Surveys");
        JButton btnRefresh = UITheme.secondaryButton("Refresh");
        topRow.add(heading,    BorderLayout.WEST);
        topRow.add(btnRefresh, BorderLayout.EAST);
        p.add(topRow, BorderLayout.NORTH);

        String[] cols = {"Course ID", "Student ID", "Rating", "Comment"};
        surveysModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        JTable table = new JTable(surveysModel);
        UITheme.styleTable(table);

        btnRefresh.addActionListener(e -> {
            surveysModel.setRowCount(0);
            try {
                for (Survey s : service.CourseService.getAllSurveys())
                    surveysModel.addRow(new Object[]{s.getCourseId(), s.getStudentId(), s.getRating(), s.getComment()});
            } catch (IOException ex) { showError(ex.getMessage()); }
        });

        p.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        btnRefresh.doClick();
        return p;
    }

    private void showError(String msg) {
        JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE);
    }
}
