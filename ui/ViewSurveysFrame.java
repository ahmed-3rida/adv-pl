package ui;

import models.Survey;
import service.CourseService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;

public class ViewSurveysFrame extends JFrame {

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ViewSurveysFrame() {
        super("View All Surveys");
        UITheme.applyGlobalDefaults();
        setSize(820, 520);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
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

        JLabel titleLbl = new JLabel("All Student Surveys");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);

        JPanel rightBar = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 0));
        rightBar.setBackground(UITheme.BG_CARD);

        JButton btnRefresh = UITheme.primaryButton("Refresh");
        JButton btnClose   = UITheme.secondaryButton("Close");

        btnRefresh.addActionListener(e -> loadData());
        btnClose.addActionListener(e -> dispose());

        rightBar.add(btnRefresh);
        rightBar.add(btnClose);

        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(rightBar, BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        // ── Table ─────────────────────────────────────────────────────────────
        String[] cols = {"Course ID", "Student ID", "Rating", "Comment"};
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

        // ── Summary bar ───────────────────────────────────────────────────────
        JPanel statusBar = new JPanel(new BorderLayout());
        statusBar.setBackground(UITheme.BG_CARD);
        statusBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(1, 0, 0, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(8, 20, 8, 20)
        ));
        JLabel countLbl = new JLabel("Total surveys: 0");
        countLbl.setFont(UITheme.FONT_SMALL);
        countLbl.setForeground(UITheme.TEXT_MUTED);
        statusBar.add(countLbl, BorderLayout.WEST);
        root.add(statusBar, BorderLayout.SOUTH);

        // Track row count for status
        tableModel.addTableModelListener(e -> countLbl.setText("Total surveys: " + tableModel.getRowCount()));

        loadData();
    }

    private void loadData() {
        tableModel.setRowCount(0);
        try {
            for (Survey s : CourseService.getAllSurveys())
                tableModel.addRow(new Object[]{
                    s.getCourseId(), s.getStudentId(), s.getRating(), s.getComment()
                });
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error loading surveys: " + ex.getMessage(),
                "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
