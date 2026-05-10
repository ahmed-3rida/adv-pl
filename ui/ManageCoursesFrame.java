package ui;

import models.*;
import service.AdminService;
import service.CourseService;

import javax.swing.*;
import javax.swing.border.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

public class ManageCoursesFrame extends JFrame {

    private final Admin admin;

    private JComboBox<String> cbParentCourse;
    private JComboBox<String> cbInstructor;
    private JComboBox<String> cbBranch;
    private JComboBox<String> cbRoom;

    private final JTextField tfPrice     = UITheme.textField();
    private final JTextField tfStartDate = UITheme.textField();
    private final JTextField tfEndDate   = UITheme.textField();
    private final JTextField tfDays      = UITheme.textField();

    private final DefaultTableModel tableModel;
    private final JTable table;

    public ManageCoursesFrame(Admin admin) {
        super("Manage Courses");
        this.admin = admin;
        UITheme.applyGlobalDefaults();
        setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        setSize(1100, 660);
        setLocationRelativeTo(null);
        UITheme.styleFrame(this);

        cbParentCourse = UITheme.comboBox();
        cbInstructor   = UITheme.comboBox();
        cbBranch       = UITheme.comboBox();
        cbRoom         = UITheme.comboBox();

        cbBranch.addActionListener(e -> updateRoomsForBranch());
        refreshCombos();

        tfStartDate.setText("YYYY-MM-DD");
        tfEndDate.setText("YYYY-MM-DD");
        tfDays.setText("Mon,Wed,Fri");

        JPanel root = new JPanel(new BorderLayout());
        root.setBackground(UITheme.BG_DARK);
        setContentPane(root);

        JPanel topBar = new JPanel(new BorderLayout());
        topBar.setBackground(UITheme.BG_CARD);
        topBar.setBorder(BorderFactory.createCompoundBorder(
            new MatteBorder(0, 0, 1, 0, UITheme.BORDER_COLOR),
            BorderFactory.createEmptyBorder(12, 20, 12, 20)
        ));
        JLabel titleLbl = new JLabel("Manage Courses");
        titleLbl.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLbl.setForeground(UITheme.TEXT_PRIMARY);
        JButton btnBack = UITheme.secondaryButton("<- Back");
        btnBack.addActionListener(e -> goBack());
        topBar.add(titleLbl, BorderLayout.WEST);
        topBar.add(btnBack,  BorderLayout.EAST);
        root.add(topBar, BorderLayout.NORTH);

        String[] cols = {"ID", "Parent", "Instructor", "Room", "Branch", "Price",
                         "Start", "End", "Days", "Students", "Published"};
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
        gc.insets = new Insets(5, 8, 5, 8);
        gc.fill   = GridBagConstraints.HORIZONTAL;

        Dimension fieldSize = new Dimension(200, 30);
        cbParentCourse.setPreferredSize(fieldSize);
        cbInstructor.setPreferredSize(fieldSize);
        cbBranch.setPreferredSize(fieldSize);
        cbRoom.setPreferredSize(fieldSize);
        tfPrice.setPreferredSize(fieldSize);
        tfStartDate.setPreferredSize(fieldSize);
        tfEndDate.setPreferredSize(fieldSize);
        tfDays.setPreferredSize(fieldSize);

        addFormRow2Col(formCard, gc, 0, 0, "Parent Course:", cbParentCourse);
        addFormRow2Col(formCard, gc, 1, 0, "Instructor:",    cbInstructor);
        addFormRow2Col(formCard, gc, 2, 0, "Branch:",        cbBranch);
        addFormRow2Col(formCard, gc, 3, 0, "Room:",          cbRoom);
        addFormRow2Col(formCard, gc, 0, 2, "Price:",      tfPrice);
        addFormRow2Col(formCard, gc, 1, 2, "Start Date:", tfStartDate);
        addFormRow2Col(formCard, gc, 2, 2, "End Date:",   tfEndDate);
        addFormRow2Col(formCard, gc, 3, 2, "Days:",       tfDays);

        JPanel btnPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 0));
        btnPanel.setBackground(UITheme.BG_DARK);

        JButton btnAdd    = UITheme.primaryButton("Add");
        JButton btnUpdate = UITheme.primaryButton("Update");
        JButton btnDelete = UITheme.primaryButton("Delete");
        JButton btnClear  = UITheme.secondaryButton("Clear");
        JButton btnEnroll = UITheme.successButton("Enroll Student");
        JButton btnRemove = UITheme.secondaryButton("Remove Student");
        btnDelete.setForeground(UITheme.DANGER);

        btnAdd.addActionListener(e    -> addCourse());
        btnUpdate.addActionListener(e -> updateCourse());
        btnDelete.addActionListener(e -> deleteCourse());
        btnClear.addActionListener(e  -> clearForm());
        btnEnroll.addActionListener(e -> enrollStudent());
        btnRemove.addActionListener(e -> removeStudent());

        btnPanel.add(btnAdd); btnPanel.add(btnUpdate); btnPanel.add(btnDelete);
        btnPanel.add(btnClear); btnPanel.add(btnEnroll); btnPanel.add(btnRemove);

        south.add(formCard, BorderLayout.CENTER);
        south.add(btnPanel, BorderLayout.SOUTH);
        root.add(south, BorderLayout.SOUTH);

        loadTable();
    }

    private void addFormRow2Col(JPanel p, GridBagConstraints gc, int row, int colOffset,
                                 String label, JComponent field) {
        gc.gridx = colOffset;     gc.gridy = row; gc.weightx = 0;
        p.add(UITheme.bodyLabel(label), gc);
        gc.gridx = colOffset + 1; gc.gridy = row; gc.weightx = 0.5;
        p.add(field, gc);
    }

    private void refreshCombos() {
        cbParentCourse.removeAllItems();
        cbInstructor.removeAllItems();
        cbBranch.removeAllItems();
        try {
            for (ParentCourse pc : CourseService.getAllParentCourses())
                cbParentCourse.addItem(pc.getId() + " — " + pc.getTitle());
            for (Instructor i : AdminService.getAllInstructors())
                cbInstructor.addItem(i.getId() + " — " + i.getName());
            for (String branch : storage.FileManager.loadBranches())
                cbBranch.addItem(branch);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void updateRoomsForBranch() {
        cbRoom.removeAllItems();
        String sel = (String) cbBranch.getSelectedItem();
        if (sel == null) return;
        try {
            for (Room room : storage.FileManager.loadRooms())
                if (room.getBranchName().equals(sel)) cbRoom.addItem(room.getName());
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void loadTable() {
        tableModel.setRowCount(0);
        try {
            for (Course c : CourseService.getAllCourses())
                tableModel.addRow(new Object[]{
                    c.getId(), c.getParentCourseId(), c.getInstructorId(),
                    c.getRoom(), c.getBranch(), c.getPrice(),
                    c.getStartDate(), c.getEndDate(), c.getDays(),
                    c.getStudentIds().size(), c.isGradesPublished()
                });
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void fillForm() {
        int row = table.getSelectedRow();
        if (row < 0) return;
        selectComboById(cbParentCourse, (String) tableModel.getValueAt(row, 1));
        selectComboById(cbInstructor,   (String) tableModel.getValueAt(row, 2));
        cbBranch.setSelectedItem((String) tableModel.getValueAt(row, 4));
        cbRoom.setSelectedItem((String)   tableModel.getValueAt(row, 3));
        tfPrice.setText(String.valueOf(tableModel.getValueAt(row, 5)));
        tfStartDate.setText(String.valueOf(tableModel.getValueAt(row, 6)));
        tfEndDate.setText(String.valueOf(tableModel.getValueAt(row, 7)));
        tfDays.setText((String) tableModel.getValueAt(row, 8));
    }

    private void selectComboById(JComboBox<String> cb, String id) {
        for (int i = 0; i < cb.getItemCount(); i++) {
            if (cb.getItemAt(i).startsWith(id + " ")) { cb.setSelectedIndex(i); return; }
        }
    }

    private String comboId(JComboBox<String> cb) {
        String item = (String) cb.getSelectedItem();
        return item == null ? "" : item.split(" ")[0];
    }

    private LocalDate parseDate(String s) {
        try { return LocalDate.parse(s); } catch (DateTimeParseException e) { return null; }
    }

    private void addCourse() {
        LocalDate start = parseDate(tfStartDate.getText().trim());
        LocalDate end   = parseDate(tfEndDate.getText().trim());
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Invalid date format (YYYY-MM-DD)."); return;
        }
        if (!end.isAfter(start)) {
            JOptionPane.showMessageDialog(this, "End date must be after start date.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        if (cbParentCourse.getSelectedIndex() < 0 || cbInstructor.getSelectedIndex() < 0) {
            JOptionPane.showMessageDialog(this, "Select parent course and instructor.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        if (cbRoom.getSelectedIndex() < 0 || cbBranch.getSelectedIndex() < 0 || tfPrice.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Room, Branch, and Price are required.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        try {
            double price = Double.parseDouble(tfPrice.getText().trim());
            boolean ok = CourseService.createCourse(comboId(cbParentCourse), comboId(cbInstructor),
                    (String) cbRoom.getSelectedItem(), (String) cbBranch.getSelectedItem(),
                    price, start, end, tfDays.getText().trim());
            if (ok) { clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Course added."); }
            else    JOptionPane.showMessageDialog(this, "Duplicate course detected!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Price must be a number.", "Validation", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void updateCourse() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        LocalDate start = parseDate(tfStartDate.getText().trim());
        LocalDate end   = parseDate(tfEndDate.getText().trim());
        if (start == null || end == null) {
            JOptionPane.showMessageDialog(this, "Invalid date (YYYY-MM-DD)."); return;
        }
        if (!end.isAfter(start)) {
            JOptionPane.showMessageDialog(this, "End date must be after start date.", "Validation", JOptionPane.WARNING_MESSAGE); return;
        }
        String cId = (String) tableModel.getValueAt(row, 0);
        try {
            double price = Double.parseDouble(tfPrice.getText().trim());
            boolean ok = CourseService.updateCourse(cId, comboId(cbParentCourse), comboId(cbInstructor),
                    (String) cbRoom.getSelectedItem(), (String) cbBranch.getSelectedItem(),
                    price, start, end, tfDays.getText().trim());
            if (ok) { clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Course updated."); }
            else    JOptionPane.showMessageDialog(this, "Duplicate course detected!", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void deleteCourse() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        String cId = (String) tableModel.getValueAt(row, 0);
        if (JOptionPane.showConfirmDialog(this, "Delete course " + cId + "?", "Confirm",
                JOptionPane.YES_NO_OPTION) != JOptionPane.YES_OPTION) return;
        try {
            CourseService.deleteCourse(cId);
            clearForm(); loadTable(); JOptionPane.showMessageDialog(this, "Deleted.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void enrollStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        String cId = (String) tableModel.getValueAt(row, 0);
        JComboBox<String> cbStudents = UITheme.comboBox();
        try {
            for (Student s : AdminService.getAllStudents())
                cbStudents.addItem(s.getId() + " — " + s.getName());
        } catch (IOException ex) { showError(ex.getMessage()); return; }
        if (cbStudents.getItemCount() == 0) { JOptionPane.showMessageDialog(this, "No students found."); return; }
        int res = JOptionPane.showConfirmDialog(this, cbStudents, "Select Student to Enroll", JOptionPane.OK_CANCEL_OPTION);
        if (res != JOptionPane.OK_OPTION) return;
        String sId = comboId(cbStudents);
        try {
            boolean ok = CourseService.enrollStudent(cId, sId);
            if (ok) { loadTable(); JOptionPane.showMessageDialog(this, "Student enrolled."); }
            else    JOptionPane.showMessageDialog(this, "Student is already enrolled.", "Warning", JOptionPane.WARNING_MESSAGE);
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void removeStudent() {
        int row = table.getSelectedRow();
        if (row < 0) { JOptionPane.showMessageDialog(this, "Select a course first."); return; }
        String cId = (String) tableModel.getValueAt(row, 0);
        try {
            Course course = CourseService.getCourseById(cId);
            if (course == null) return;
            List<String> studentIds = course.getStudentIds();
            if (studentIds.isEmpty()) { JOptionPane.showMessageDialog(this, "No students enrolled."); return; }
            JComboBox<String> cbEnrolled = UITheme.comboBox();
            List<Student> allStudents = AdminService.getAllStudents();
            for (String sid : studentIds) {
                String label = sid;
                for (Student s : allStudents) {
                    if (s.getId().equals(sid)) { label += " — " + s.getName(); break; }
                }
                cbEnrolled.addItem(label);
            }
            int res = JOptionPane.showConfirmDialog(this, cbEnrolled, "Select Student to Remove", JOptionPane.OK_CANCEL_OPTION);
            if (res != JOptionPane.OK_OPTION) return;
            String sId = comboId(cbEnrolled);
            CourseService.removeStudentFromCourse(cId, sId);
            loadTable(); JOptionPane.showMessageDialog(this, "Student removed.");
        } catch (IOException ex) { showError(ex.getMessage()); }
    }

    private void clearForm() {
        tfPrice.setText(""); tfStartDate.setText("YYYY-MM-DD");
        tfEndDate.setText("YYYY-MM-DD"); tfDays.setText("Mon,Wed,Fri");
        if (cbParentCourse.getItemCount() > 0) cbParentCourse.setSelectedIndex(0);
        if (cbInstructor.getItemCount()   > 0) cbInstructor.setSelectedIndex(0);
        if (cbRoom.getItemCount()         > 0) cbRoom.setSelectedIndex(0);
        if (cbBranch.getItemCount()       > 0) cbBranch.setSelectedIndex(0);
        table.clearSelection();
    }

    private void goBack() { dispose(); new AdminDashboard(admin).setVisible(true); }
    private void showError(String msg) { JOptionPane.showMessageDialog(this, msg, "Error", JOptionPane.ERROR_MESSAGE); }
}
