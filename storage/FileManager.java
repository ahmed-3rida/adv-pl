package storage;

import models.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

    private static final String DATA_DIR = "data";

    public static final String ADMINS_FILE        = DATA_DIR + "/admins.txt";
    public static final String STUDENTS_FILE      = DATA_DIR + "/students.txt";
    public static final String INSTRUCTORS_FILE   = DATA_DIR + "/instructors.txt";
    public static final String PARENT_COURSES_FILE = DATA_DIR + "/parent_courses.txt";
    public static final String COURSES_FILE       = DATA_DIR + "/courses.txt";
    public static final String GRADES_FILE        = DATA_DIR + "/grades.txt";
    public static final String SURVEYS_FILE       = DATA_DIR + "/surveys.txt";
    public static final String ROOMS_FILE         = DATA_DIR + "/rooms.txt";
    public static final String BRANCHES_FILE      = DATA_DIR + "/branches.txt";

    private static void ensure(String filePath) throws IOException {
        File dir = new File(DATA_DIR);
        if (!dir.exists())
            dir.mkdirs();
        File f = new File(filePath);
        if (!f.exists())
            f.createNewFile();
    }

    public static List<String> readLines(String filePath) throws IOException {
        ensure(filePath);
        List<String> lines = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty() && !line.startsWith("#")) {
                    lines.add(line);
                }
            }
        }
        return lines;
    }

    public static void writeLines(String filePath, List<String> lines) throws IOException {
        ensure(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, false))) {
            for (String line : lines) {
                bw.write(line);
                bw.newLine();
            }
        }
    }

    public static void appendLine(String filePath, String line) throws IOException {
        ensure(filePath);
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath, true))) {
            bw.write(line);
            bw.newLine();
        }
    }

    public static List<Admin> loadAdmins() throws IOException {
        List<Admin> list = new ArrayList<>();
        for (String line : readLines(ADMINS_FILE)) {
            try {
                list.add(Admin.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveAdmins(List<Admin> admins) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Admin a : admins)
            lines.add(a.toFileLine());
        writeLines(ADMINS_FILE, lines);
    }

    public static List<Student> loadStudents() throws IOException {
        List<Student> list = new ArrayList<>();
        for (String line : readLines(STUDENTS_FILE)) {
            try {
                list.add(Student.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveStudents(List<Student> students) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Student s : students)
            lines.add(s.toFileLine());
        writeLines(STUDENTS_FILE, lines);
    }

    public static List<Instructor> loadInstructors() throws IOException {
        List<Instructor> list = new ArrayList<>();
        for (String line : readLines(INSTRUCTORS_FILE)) {
            try {
                list.add(Instructor.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveInstructors(List<Instructor> instructors) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Instructor i : instructors)
            lines.add(i.toFileLine());
        writeLines(INSTRUCTORS_FILE, lines);
    }

    public static List<ParentCourse> loadParentCourses() throws IOException {
        List<ParentCourse> list = new ArrayList<>();
        for (String line : readLines(PARENT_COURSES_FILE)) {
            try {
                list.add(ParentCourse.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveParentCourses(List<ParentCourse> courses) throws IOException {
        List<String> lines = new ArrayList<>();
        for (ParentCourse pc : courses)
            lines.add(pc.toFileLine());
        writeLines(PARENT_COURSES_FILE, lines);
    }

    public static List<Course> loadCourses() throws IOException {
        List<Course> list = new ArrayList<>();
        for (String line : readLines(COURSES_FILE)) {
            try {
                list.add(Course.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveCourses(List<Course> courses) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Course c : courses)
            lines.add(c.toFileLine());
        writeLines(COURSES_FILE, lines);
    }

    public static List<Grade> loadGrades() throws IOException {
        List<Grade> list = new ArrayList<>();
        for (String line : readLines(GRADES_FILE)) {
            try {
                list.add(Grade.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveGrades(List<Grade> grades) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Grade g : grades)
            lines.add(g.toFileLine());
        writeLines(GRADES_FILE, lines);
    }

    public static List<Survey> loadSurveys() throws IOException {
        List<Survey> list = new ArrayList<>();
        for (String line : readLines(SURVEYS_FILE)) {
            try {
                list.add(Survey.fromFileLine(line));
            } catch (Exception ignored) {
            }
        }
        return list;
    }

    public static void saveSurveys(List<Survey> surveys) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Survey s : surveys)
            lines.add(s.toFileLine());
        writeLines(SURVEYS_FILE, lines);
    }

    public static List<Room> loadRooms() throws IOException {
        List<Room> list = new ArrayList<>();
        for (String line : readLines(ROOMS_FILE)) {
            Room r = Room.fromFileLine(line);
            if (r != null) list.add(r);
        }
        return list;
    }

    public static void saveRooms(List<Room> rooms) throws IOException {
        List<String> lines = new ArrayList<>();
        for (Room r : rooms) {
            lines.add(r.toFileLine());
        }
        writeLines(ROOMS_FILE, lines);
    }

    public static List<String> loadBranches() throws IOException {
        return readLines(BRANCHES_FILE);
    }

    public static void saveBranches(List<String> branches) throws IOException {
        writeLines(BRANCHES_FILE, branches);
    }
}
