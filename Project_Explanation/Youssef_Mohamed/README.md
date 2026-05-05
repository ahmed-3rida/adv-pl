# Project Explanation - GUI (Swing)

## 📌 What is this part of the system?
This module handles everything the user sees and clicks on. It is the visual representation of the system using Java Swing.

## 📌 What does it do? (features)
* Provides Login screens with role-based routing.
* Provides Dashboards (menus) for Admin, Student, and Instructor.
* Uses `JTable` to display lists of data (like Students, Courses, Grades).
* Takes user input through text fields and drop-downs (`JComboBox`).
* Shows popup messages for errors and success alerts.

## 📌 Key classes used
* `ui.LoginFrame`, `ui.AdminDashboard`, `ui.StudentDashboard`, `ui.InstructorDashboard`
* `javax.swing.JFrame`, `JPanel`, `JTable`, `DefaultTableModel`, `JOptionPane`

## 📌 Important concepts used
* **Layout Managers**: `BorderLayout`, `GridLayout`, `FlowLayout` to arrange buttons and tables cleanly.
* **Event Listeners**: `addActionListener(e -> {...})` to define what happens when a button is clicked.
* **Table Models**: Using `DefaultTableModel` to easily add rows and make the table non-editable by the user directly.

## 📌 How it connects with other modules
The UI is the "View" in MVC. It NEVER reads files directly. Instead, when a user clicks "Add Student", the UI collects text from the fields and calls `AdminService.addStudent()`. Depending on the boolean result, it shows a success or error popup.

## 📌 Example from the code
```java
// Example of a button click listener using Lambda
btnSave.addActionListener(e -> {
    String name = tfName.getText().trim();
    String email = tfEmail.getText().trim();
    
    // Call the Service logic
    boolean success = AdminService.addStudent(name, email, ...);
    
    if (success) {
        JOptionPane.showMessageDialog(this, "Student added.");
        loadTable(); // Refresh the JTable
    } else {
        JOptionPane.showMessageDialog(this, "Email already exists.", "Error", JOptionPane.ERROR_MESSAGE);
    }
});
```

## 📌 Possible questions in discussion + answers

**Q: Why use `DefaultTableModel` instead of just passing arrays to `JTable`?**
A: `DefaultTableModel` makes it very easy to clear the table (`setRowCount(0)`) and dynamically add rows (`addRow(new Object[]{...})`) after loading data from the service. It also allows us to easily disable cell editing.

**Q: How do you switch between screens?**
A: When moving to a new screen, we create a new instance of the next frame and make it visible (`new AdminDashboard().setVisible(true)`), and we close the current frame using `dispose();` so memory isn't wasted.

**Q: What is `SwingUtilities.invokeLater()` in App.java?**
A: It ensures that the GUI is created on the Event Dispatch Thread (EDT), which is a rule in Java Swing to prevent freezing and thread-safety issues.
