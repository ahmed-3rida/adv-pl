package service;

import models.*;
import storage.FileManager;

import java.io.IOException;
import java.util.List;

public class AdminService {

    public static List<Student> getAllStudents() throws IOException {
        return FileManager.loadStudents();
    }

    public static boolean addStudent(String name, String email, String password,
                                     String phone, String address) throws IOException {
        List<Student> students = FileManager.loadStudents();
        for (Student s : students) {
            if (s.getEmail().equalsIgnoreCase(email)) {
                System.out.println("[ERROR] A student with email '" + email + "' already exists.");
                return false;
            }
        }
        String id = "S" + (students.size() + 1);
        Student newStudent = new Student(id, name, email, password, phone, address);
        students.add(newStudent);
        FileManager.saveStudents(students);
        System.out.println("[OK] Student added: " + newStudent);
        return true;
    }

    public static boolean updateStudent(String studentId, String name, String email,
                                        String password, String phone, String address) throws IOException {
        List<Student> students = FileManager.loadStudents();
        for (Student s : students) {
            if (s.getId().equals(studentId)) {
                s.setName(name);
                s.setEmail(email);
                if (!password.isBlank()) s.setPassword(password);
                s.setPhone(phone);
                s.setAddress(address);
                FileManager.saveStudents(students);
                System.out.println("[OK] Student updated: " + s);
                return true;
            }
        }
        System.out.println("[ERROR] Student not found: " + studentId);
        return false;
    }

    public static boolean deleteStudent(String studentId) throws IOException {
        List<Student> students = FileManager.loadStudents();
        boolean removed = students.removeIf(s -> s.getId().equals(studentId));
        if (removed) {
            FileManager.saveStudents(students);
            System.out.println("[OK] Student deleted: " + studentId);
        } else {
            System.out.println("[ERROR] Student not found: " + studentId);
        }
        return removed;
    }

    public static List<Instructor> getAllInstructors() throws IOException {
        return FileManager.loadInstructors();
    }

    public static boolean addInstructor(String name, String email, String password,
                                        String specialization, String phone) throws IOException {
        List<Instructor> instructors = FileManager.loadInstructors();
        for (Instructor i : instructors) {
            if (i.getEmail().equalsIgnoreCase(email)) {
                System.out.println("[ERROR] An instructor with email '" + email + "' already exists.");
                return false;
            }
        }
        String id = "I" + (instructors.size() + 1);
        Instructor newInstructor = new Instructor(id, name, email, password, specialization, phone);
        instructors.add(newInstructor);
        FileManager.saveInstructors(instructors);
        System.out.println("[OK] Instructor added: " + newInstructor);
        return true;
    }

    public static boolean updateInstructor(String instructorId, String name, String email,
                                           String password, String specialization,
                                           String phone) throws IOException {
        List<Instructor> instructors = FileManager.loadInstructors();
        for (Instructor i : instructors) {
            if (i.getId().equals(instructorId)) {
                i.setName(name);
                i.setEmail(email);
                if (!password.isBlank()) i.setPassword(password);
                i.setSpecialization(specialization);
                i.setPhone(phone);
                FileManager.saveInstructors(instructors);
                System.out.println("[OK] Instructor updated: " + i);
                return true;
            }
        }
        System.out.println("[ERROR] Instructor not found: " + instructorId);
        return false;
    }

    public static boolean deleteInstructor(String instructorId) throws IOException {
        List<Instructor> instructors = FileManager.loadInstructors();
        boolean removed = instructors.removeIf(i -> i.getId().equals(instructorId));
        if (removed) {
            FileManager.saveInstructors(instructors);
            System.out.println("[OK] Instructor deleted: " + instructorId);
        } else {
            System.out.println("[ERROR] Instructor not found: " + instructorId);
        }
        return removed;
    }

    public static List<Room> getAllRooms() throws IOException {
        return FileManager.loadRooms();
    }

    public static boolean addRoom(String roomName, String branchName) throws IOException {
        if (roomName == null || roomName.isBlank() || branchName == null || branchName.isBlank()) return false;
        List<Room> rooms = FileManager.loadRooms();
        for (Room r : rooms) {
            if (r.getName().equalsIgnoreCase(roomName.trim()) && r.getBranchName().equalsIgnoreCase(branchName.trim())) return false;
        }
        rooms.add(new Room(roomName.trim(), branchName.trim()));
        FileManager.saveRooms(rooms);
        return true;
    }

    public static boolean updateRoom(String oldRoomName, String oldBranchName, String newRoomName, String newBranchName) throws IOException {
        if (newRoomName == null || newRoomName.isBlank() || newBranchName == null || newBranchName.isBlank()) return false;
        List<Room> rooms = FileManager.loadRooms();
        for (Room r : rooms) {
            if (r.getName().equalsIgnoreCase(newRoomName.trim()) &&
                r.getBranchName().equalsIgnoreCase(newBranchName.trim()) &&
                !(r.getName().equalsIgnoreCase(oldRoomName) && r.getBranchName().equalsIgnoreCase(oldBranchName))) {
                return false;
            }
        }
        boolean updated = false;
        for (Room r : rooms) {
            if (r.getName().equalsIgnoreCase(oldRoomName) && r.getBranchName().equalsIgnoreCase(oldBranchName)) {
                r.setName(newRoomName.trim());
                r.setBranchName(newBranchName.trim());
                updated = true;
                break;
            }
        }
        if (updated) {
            FileManager.saveRooms(rooms);
        }
        return updated;
    }

    public static boolean deleteRoom(String roomName, String branchName) throws IOException {
        List<Room> rooms = FileManager.loadRooms();
        boolean removed = rooms.removeIf(r -> r.getName().equalsIgnoreCase(roomName) && r.getBranchName().equalsIgnoreCase(branchName));
        if (removed) {
            FileManager.saveRooms(rooms);
        }
        return removed;
    }

    public static List<String> getAllBranches() throws IOException {
        return FileManager.loadBranches();
    }

    public static boolean addBranch(String branch) throws IOException {
        if (branch == null || branch.isBlank()) return false;
        List<String> branches = FileManager.loadBranches();
        for (String b : branches) {
            if (b.equalsIgnoreCase(branch.trim())) return false;
        }
        branches.add(branch.trim());
        FileManager.saveBranches(branches);
        return true;
    }

    public static boolean deleteBranch(String branch) throws IOException {
        List<String> branches = FileManager.loadBranches();
        boolean removed = branches.removeIf(b -> b.equalsIgnoreCase(branch));
        if (removed) {
            FileManager.saveBranches(branches);
            List<Room> rooms = FileManager.loadRooms();
            boolean roomsRemoved = rooms.removeIf(r -> r.getBranchName().equalsIgnoreCase(branch));
            if (roomsRemoved) {
                FileManager.saveRooms(rooms);
            }
        }
        return removed;
    }
}
