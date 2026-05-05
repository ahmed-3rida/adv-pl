# Project Explanation - Admin Module

## 📌 What is this part of the system?
This module handles all operations related to managing the system's users (Students and Instructors). It is the control center for the Admin.

## 📌 What does it do? (features)
* Add, update, and delete Students.
* Add, update, and delete Instructors.
* Validates inputs (e.g., checking if an email is already used).
* Uses email/password validation for security.

## 📌 Key classes used
* `models.Admin`, `models.Student`, `models.Instructor`
* `service.AdminService`
* `service.ValidationUtils`

## 📌 Important methods
* `addStudent(...)` and `addInstructor(...)`
* `updateStudent(...)` and `updateInstructor(...)`
* `deleteStudent(String id)`

## 📌 How it connects with other modules
The UI (like `ManageStudentsFrame`) calls `AdminService`. The `AdminService` checks logic (like duplicate emails), formats the data, and then passes it to `FileManager` to be saved in text files.

## 📌 Example from the code
```java
// Adding a new student
public static boolean addStudent(String name, String email, String password, String phone, String address) throws IOException {
    List<Student> students = FileManager.loadStudents();
    
    // Check if email already exists
    for (Student s : students) {
        if (s.getEmail().equalsIgnoreCase(email)) return false; 
    }
    
    // Create ID and add to list
    String id = "S" + (students.size() + 1);
    Student newStudent = new Student(id, name, email, password, phone, address);
    students.add(newStudent);
    FileManager.saveStudents(students);
    return true;
}
```

## 📌 Possible questions in discussion + answers

**Q: How do you prevent two students from having the same email?**
A: Inside `AdminService`, before creating a new student, I load the list of all students and loop through them. If I find the same email, the method returns `false` and stops.

**Q: How do you generate IDs for new users?**
A: I take the current size of the list and add 1. For example, if there are 5 students, the new ID will be `"S" + (5 + 1) = "S6"`.

**Q: How does the system validate emails and passwords?**
A: We use a `ValidationUtils` class. It checks if the email contains `@` and `.`, and ensures the password is at least 8 characters long.
