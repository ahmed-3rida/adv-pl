package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentService {

    public static List<Course> getAllCourses() throws IOException {
        return FileManager.loadCourses();
    }

    public static Grade getGrade(String studentId, String courseId) throws IOException {
        Course course = CourseService.getCourseById(courseId);
        if (course == null) {
            System.out.println("[ERROR] Course not found: " + courseId);
            return null;
        }
        if (!course.isGradesPublished()) {
            System.out.println("[INFO] Grades have not been published for course " + courseId + " yet.");
            return null;
        }
        for (Grade g : FileManager.loadGrades()) {
            if (g.getCourseId().equals(courseId) && g.getStudentId().equals(studentId)) {
                return g;
            }
        }
        System.out.println("[INFO] No grade found for you in course " + courseId);
        return null;
    }

    public static int submitSurvey(String studentId, String courseId,
                                   int rating, String comment) throws IOException {
        Course course = CourseService.getCourseById(courseId);
        if (course == null) {
            System.out.println("[ERROR] Course not found: " + courseId);
            return 0;
        }
        if (!course.getStudentIds().contains(studentId)) {
            System.out.println("[ERROR] You are not enrolled in course " + courseId);
            return 0;
        }
        if (rating < 1 || rating > 5) {
            System.out.println("[ERROR] Rating must be between 1 and 5.");
            return 0;
        }
        List<Survey> surveys = FileManager.loadSurveys();
        boolean updated = false;
        for (Survey s : surveys) {
            if (s.getCourseId().equals(courseId) && s.getStudentId().equals(studentId)) {
                s.setRating(rating);
                s.setComment(comment);
                updated = true;
                break;
            }
        }
        if (!updated) {
            surveys.add(new Survey(courseId, studentId, rating, comment));
        }
        FileManager.saveSurveys(surveys);
        System.out.println(updated ? "[OK] Survey updated." : "[OK] Survey submitted. Thank you!");
        return updated ? 2 : 1;
    }

    public static boolean updatePersonalInfo(String studentId, String name, String email,
                                             String password, String phone,
                                             String address) throws IOException {
        List<Student> students = FileManager.loadStudents();
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                s.setName(name);
                s.setEmail(email);
                if (!password.isBlank()) s.setPassword(password);
                s.setPhone(phone);
                s.setAddress(address);
                FileManager.saveStudents(students);
                System.out.println("[OK] Personal info updated.");
                return true;
            }
        }
        System.out.println("[ERROR] Student not found.");
        return false;
    }

    public static List<Course> getEnrolledCourses(String studentId) throws IOException {
        List<Course> result = new ArrayList<>();
        for (Course c : FileManager.loadCourses()) {
            if (c.getStudentIds().contains(studentId)) {
                result.add(c);
            }
        }
        return result;
    }
}
