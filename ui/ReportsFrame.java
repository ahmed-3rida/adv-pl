package ui;

import models.Admin;
import models.Course;
import service.CourseService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.util.List;

public class ReportsFrame extends JFrame {

    private final Admin admin;
    private final DefaultTableModel tableModel;
    private final JTable table;

    public ReportsFrame(Admin admin) {
        super("Reports");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(960, 560);
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
        JLabel titleLbl = new JLabel("Reports");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> { dispose(); new AdminDashboard(admin).setVisible(true); });
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);

        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT, 14, 12));
        controls.setBackground(UITheme.BG_CARD);
        controls.setBorder(new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR));

        JLabel lbl = UITheme.bodyLabel("Within next N days:");
        JTextField tfDays = UITheme.textField();
        tfDays.setText("7");
        tfDays.setPreferredSize(new Dimension(70, 32));

        JButton btnStart = UITheme.primaryButton("Courses Near to Start");
        JButton btnEnd   = UITheme.primaryButton("Courses Near to End");

        btnStart.addActionListener(e -> {
            int d = parseDays(tfDays.getText(), 7);
            try { loadTable(CourseService.getCoursesNearToStart(d)); } catch (IOException ex) { showError(ex.getMessage()); }
        });
        btnEnd.addActionListener(e -> {
            int d = parseDays(tfDays.getText(), 7);
            try { loadTable(CourseService.getCoursesNearToEnd(d)); } catch (IOException ex) { showError(ex.getMessage()); }
        });

        controls.add(lbl); controls.add(tfDays);
        controls.add(Box.createHorizontalStrut(8));
        controls.add(btnStart); controls.add(btnEnd);

        JPanel north = new JPanel(new BorderLayout());
        north.setBackground(UITheme.BG_DARK);
        north.add(topBar,    BorderLayout.NORTH);
        north.add(controls,  BorderLayout.SOUTH);
        root.add(north, BorderLayout.NORTH);

        String[] cols = {"ID", "Parent", "Instructor", "Room", "Branch", "Price", "Start", "End", "Days"};
        tableModel = new DefaultTableModel(cols, 0) {
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        table = new JTable(tableModel);
        UITheme.styleTable(table);

        JPanel center = new JPanel(new BorderLayout());
        center.setBackground(UITheme.BG_DARK);
        center.setBorder(BorderFactory.createEmptyBorder(14, 14, 14, 14));
        center.add(UITheme.scrollPane(table), BorderLayout.CENTER);
        root.add(center, BorderLayout.CENTER);
    }

    private void loadTable(List<Course> courses) {
        tableModel.setRowCount(0);
        for (Course c : courses)
            tableModel.addRow(new Object[]{c.getId(), c.getParentCourseId(), c.getInstructorId(),
                    c.getRoom(), c.getBranch(), c.getPrice(), c.getStartDate(), c.getEndDate(), c.getDays()});
        if (courses.isEmpty()) JOptionPane.showMessageDialog(this, "No courses found for the given range.");
    }

    private int parseDays(String s, int def) {
        try { return Integer.parseInt(s.trim()); } catch (NumberFormatException e) { return def; }
    }

    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
