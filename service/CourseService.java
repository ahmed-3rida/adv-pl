package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CourseService {

    public static List<ParentCourse> getAllParentCourses() throws IOException {
        return FileManager.loadParentCourses();
    }

    public static boolean addParentCourse(String title, String description) throws IOException {
        List<ParentCourse> list = FileManager.loadParentCourses();
        String id = "PC" + (list.size() + 1);
        ParentCourse pc = new ParentCourse(id, title, description);
        list.add(pc);
        FileManager.saveParentCourses(list);
        System.out.println("[OK] Parent course added: " + pc);
        return true;
    }

    public static boolean updateParentCourse(String pcId, String title, String description) throws IOException {
        List<ParentCourse> list = FileManager.loadParentCourses();
        for (ParentCourse pc : list) {
            if (pc.getId().equals(pcId)) {
                pc.setTitle(title);
                pc.setDescription(description);
                FileManager.saveParentCourses(list);
                System.out.println("[OK] Parent course updated: " + pc);
                return true;
            }
        }
        System.out.println("[ERROR] Parent course not found: " + pcId);
        return false;
    }

    public static boolean deleteParentCourse(String pcId) throws IOException {
        List<ParentCourse> list = FileManager.loadParentCourses();
        boolean removed = list.removeIf(pc -> pc.getId().equals(pcId));
        if (removed) {
            FileManager.saveParentCourses(list);
            System.out.println("[OK] Parent course deleted: " + pcId);
        } else {
            System.out.println("[ERROR] Parent course not found: " + pcId);
        }
        return removed;
    }

    public static List<Course> getAllCourses() throws IOException {
        return FileManager.loadCourses();
    }

    public static Course getCourseById(String courseId) throws IOException {
        for (Course c : FileManager.loadCourses()) {
            if (c.getId().equals(courseId)) return c;
        }
        return null;
    }

    public static boolean createCourse(String parentCourseId, String instructorId,
                                       String room, String branch, double price,
                                       LocalDate startDate, LocalDate endDate,
                                       String days) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        for (Course c : courses) {
            if (c.getParentCourseId().equals(parentCourseId) &&
                c.getInstructorId().equals(instructorId) &&
                c.getRoom().equals(room) &&
                c.getBranch().equals(branch) &&
                c.getStartDate().equals(startDate) &&
                c.getEndDate().equals(endDate) &&
                c.getDays().equalsIgnoreCase(days)) {
                System.out.println("[ERROR] Duplicate course detected.");
                return false;
            }
        }
        String id = "C" + (courses.size() + 1);
        Course course = new Course(id, parentCourseId, instructorId, room, branch,
                price, startDate, endDate, days);
        courses.add(course);
        FileManager.saveCourses(courses);
        System.out.println("[OK] Course created: " + course);
        return true;
    }

    public static boolean updateCourse(String courseId, String parentCourseId,
                                       String instructorId, String room, String branch,
                                       double price, LocalDate startDate, LocalDate endDate,
                                       String days) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        for (Course c : courses) {
            if (!c.getId().equals(courseId) &&
                c.getParentCourseId().equals(parentCourseId) &&
                c.getInstructorId().equals(instructorId) &&
                c.getRoom().equals(room) &&
                c.getBranch().equals(branch) &&
                c.getStartDate().equals(startDate) &&
                c.getEndDate().equals(endDate) &&
                c.getDays().equalsIgnoreCase(days)) {
                System.out.println("[ERROR] Duplicate course detected during update.");
                return false;
            }
        }
        for (Course c : courses) {
            if (c.getId().equals(courseId)) {
                c.setParentCourseId(parentCourseId);
                c.setInstructorId(instructorId);
                c.setRoom(room);
                c.setBranch(branch);
                c.setPrice(price);
                c.setStartDate(startDate);
                c.setEndDate(endDate);
                c.setDays(days);
                FileManager.saveCourses(courses);
                System.out.println("[OK] Course updated: " + c);
                return true;
            }
        }
        System.out.println("[ERROR] Course not found: " + courseId);
        return false;
    }

    public static boolean deleteCourse(String courseId) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        boolean removed = courses.removeIf(c -> c.getId().equals(courseId));
        if (removed) {
            FileManager.saveCourses(courses);
            System.out.println("[OK] Course deleted: " + courseId);
        } else {
            System.out.println("[ERROR] Course not found: " + courseId);
        }
        return removed;
    }

    public static boolean enrollStudent(String courseId, String studentId) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        for (Course c : courses) {
            if (c.getId().equals(courseId)) {
                if (c.getStudentIds().contains(studentId)) {
                    System.out.println("[WARN] Student already enrolled.");
                    return false;
                }
                c.addStudent(studentId);
                FileManager.saveCourses(courses);
                System.out.println("[OK] Student " + studentId + " enrolled in " + courseId);
                return true;
            }
        }
        System.out.println("[ERROR] Course not found: " + courseId);
        return false;
    }

    public static boolean removeStudentFromCourse(String courseId, String studentId) throws IOException {
        List<Course> courses = FileManager.loadCourses();
        for (Course c : courses) {
            if (c.getId().equals(courseId)) {
                c.removeStudent(studentId);
                FileManager.saveCourses(courses);
                System.out.println("[OK] Student " + studentId + " removed from " + courseId);
                return true;
            }
        }
        System.out.println("[ERROR] Course not found: " + courseId);
        return false;
    }

    public static List<Course> getCoursesNearToStart(int days) throws IOException {
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(days);
        List<Course> result = new ArrayList<>();
        for (Course c : FileManager.loadCourses()) {
            LocalDate sd = c.getStartDate();
            if (!sd.isBefore(today) && !sd.isAfter(threshold)) {
                result.add(c);
            }
        }
        return result;
    }

    public static List<Course> getCoursesNearToEnd(int days) throws IOException {
        LocalDate today = LocalDate.now();
        LocalDate threshold = today.plusDays(days);
        List<Course> result = new ArrayList<>();
        for (Course c : FileManager.loadCourses()) {
            LocalDate ed = c.getEndDate();
            if (!ed.isBefore(today) && !ed.isAfter(threshold)) {
                result.add(c);
            }
        }
        return result;
    }

    public static List<Survey> getAllSurveys() throws IOException {
        return FileManager.loadSurveys();
    }
}
