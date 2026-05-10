package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class InstructorService {

    public static List<Course> getAssignedCourses(String instructorId) throws IOException {
        List<Course> result = new ArrayList<>();
        for (Course c : FileManager.loadCourses()) {
            if (c.getInstructorId().equals(instructorId)) {
                result.add(c);
            }
        }
        return result;
    }

    public static boolean addOrUpdateGrade(String courseId, String studentId,
                                           double grade) throws IOException {
        Course course = CourseService.getCourseById(courseId);
        if (course == null) {
            System.out.println("[ERROR] Course not found: " + courseId);
            return false;
        }
        if (course.isGradesPublished()) {
            System.out.println("[ERROR] Grades are already published and cannot be modified for course " + courseId);
            return false;
        }

        if (!course.getStudentIds().contains(studentId)) {
            System.out.println("[ERROR] Student " + studentId + " is not enrolled in course " + courseId);
            return false;
        }
        if (grade < 0 || grade > 100) {
            System.out.println("[ERROR] Grade must be between 0 and 100.");
            return false;
        }

        List<Grade> grades = FileManager.loadGrades();
        boolean updated = false;
        for (Grade g : grades) {
            if (g.getCourseId().equals(courseId) && g.getStudentId().equals(studentId)) {
                g.setGrade(grade);
                updated = true;
                break;
            }
        }
        if (!updated) {
            grades.add(new Grade(courseId, studentId, grade));
        }
        FileManager.saveGrades(grades);
        System.out.println("[OK] Grade " + grade + " saved for student " + studentId
                + " in course " + courseId);
        return true;
    }

    public static boolean publishGrades(String courseId, String instructorId) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        for (Course c : courses) {
            if (c.getId().equals(courseId)) {
                if (!c.getInstructorId().equals(instructorId)) {
                    System.out.println("[ERROR] You are not the instructor of course " + courseId);
                    return false;
                }
                if (c.isGradesPublished()) {
                    System.out.println("[WARN] Grades already published for course " + courseId);
                    return false;
                }
                c.setGradesPublished(true);
                FileManager.saveCourses(courses);
                System.out.println("[OK] Grades published for course " + courseId);
                return true;
            }
        }
        System.out.println("[ERROR] Course not found: " + courseId);
        return false;
    }

    public static List<Grade> getGradesForCourse(String courseId) throws IOException {
        List<Grade> result = new ArrayList<>();
        for (Grade g : FileManager.loadGrades()) {
            if (g.getCourseId().equals(courseId)) result.add(g);
        }
        return result;
    }
}
