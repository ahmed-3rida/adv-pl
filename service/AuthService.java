package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.List;

/**
 * Handles login and logout for all user roles.
 */
public class AuthService {

    /** Attempt login across all user files. Returns matched User or null. */
    public static User login(String email, String password) throws IOException {
        // Check admins
        List<Admin> admins = FileManager.loadAdmins();
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(email) && a.getPassword().equals(password))
                return a;
        }
        // Check students
        List<Student> students = FileManager.loadStudents();
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email) && s.getPassword().equals(password))
                return s;
        }
        // Check instructors
        List<Instructor> instructors = FileManager.loadInstructors();
        for (Instructor i : instructors) {
            if (i.getEmail().equalsIgnoreCase(email) && i.getPassword().equals(password))
                return i;
        }
        return null;
    }

}
