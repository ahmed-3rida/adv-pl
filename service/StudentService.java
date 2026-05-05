package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Student-facing operations:
 *  - View all courses
 *  - View grades for a specific course
 *  - Submit a survey
 *  - Update personal information
 */
public class StudentService {

    public static List<Course> getAllCourses() throws IOException {
        return FileManager.loadCourses();
    }

    /** Get published grades for a student in a given course. */
    public static Grade getGrade(String studentId, String courseId) throws IOException {
        // Check if grades are published for the course
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

    /** Submit a survey for a course (one survey per student per course). */
    public static boolean submitSurvey(String studentId, String courseId,
                                       int rating, String comment) throws IOException {
        // Validate student is enrolled
        Course course = CourseService.getCourseById(courseId);
        if (course == null) {
            System.out.println("[ERROR] Course not found: " + courseId);
            return false;
        }
        if (!course.getStudentIds().contains(studentId)) {
            System.out.println("[ERROR] You are not enrolled in course " + courseId);
            return false;
        }
        // Check if already submitted
        List<Survey> surveys = FileManager.loadSurveys();
        for (Survey s : surveys) {
            if (s.getCourseId().equals(courseId) && s.getStudentId().equals(studentId)) {
                System.out.println("[WARN] You already submitted a survey for course " + courseId);
                return false;
            }
        }
        if (rating < 1 || rating > 5) {
            System.out.println("[ERROR] Rating must be between 1 and 5.");
            return false;
        }
        Survey survey = new Survey(courseId, studentId, rating, comment);
        surveys.add(survey);
        FileManager.saveSurveys(surveys);
        System.out.println("[OK] Survey submitted. Thank you!");
        return true;
    }

    /** Update student's own personal information. */
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

    /** Return the courses a specific student is enrolled in. */
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
