package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.List;

public class AuthService {

    public static User login(String email, String password) throws IOException {
        List<Admin> admins = FileManager.loadAdmins();
        for (Admin a : admins) {
            if (a.getEmail().equalsIgnoreCase(email) && a.getPassword().equals(password))
                return a;
        }
        List<Student> students = FileManager.loadStudents();
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email) && s.getPassword().equals(password))
                return s;
        }
        List<Instructor> instructors = FileManager.loadInstructors();
        for (Instructor i : instructors) {
            if (i.getEmail().equalsIgnoreCase(email) && i.getPassword().equals(password))
                return i;
        }
        return null;
    }

}
