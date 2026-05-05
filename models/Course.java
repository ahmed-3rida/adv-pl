package models;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * A scheduled course instance.
 * Stores enrolled student IDs and grade references.
 *
 * File format (courses.txt):
 *   id|parentCourseId|instructorId|room|branch|price|startDate|endDate|days|gradesPublished|studentIds(comma-sep)
 */
public class Course {
    private static final DateTimeFormatter FMT = DateTimeFormatter.ISO_LOCAL_DATE;

    private String id;
    private String parentCourseId;
    private String instructorId;
    private String room;
    private String branch;
    private double price;
    private LocalDate startDate;
    private LocalDate endDate;
    private String days;           // e.g. "Mon,Wed,Fri"
    private boolean gradesPublished;
    private List<String> studentIds;

    public Course(String id, String parentCourseId, String instructorId,
                  String room, String branch, double price,
                  LocalDate startDate, LocalDate endDate, String days) {
        this.id = id;
        this.parentCourseId = parentCourseId;
        this.instructorId = instructorId;
        this.room = room;
        this.branch = branch;
        this.price = price;
        this.startDate = startDate;
        this.endDate = endDate;
        this.days = days;
        this.gradesPublished = false;
        this.studentIds = new ArrayList<>();
    }

    // Getters
    public String getId()              { return id; }
    public String getParentCourseId()  { return parentCourseId; }
    public String getInstructorId()    { return instructorId; }
    public String getRoom()            { return room; }
    public String getBranch()          { return branch; }
    public double getPrice()           { return price; }
    public LocalDate getStartDate()    { return startDate; }
    public LocalDate getEndDate()      { return endDate; }
    public String getDays()            { return days; }
    public boolean isGradesPublished() { return gradesPublished; }
    public List<String> getStudentIds(){ return studentIds; }

    // Setters
    public void setParentCourseId(String parentCourseId) { this.parentCourseId = parentCourseId; }
    public void setInstructorId(String instructorId)     { this.instructorId = instructorId; }
    public void setRoom(String room)                     { this.room = room; }
    public void setBranch(String branch)                 { this.branch = branch; }
    public void setPrice(double price)                   { this.price = price; }
    public void setStartDate(LocalDate startDate)        { this.startDate = startDate; }
    public void setEndDate(LocalDate endDate)            { this.endDate = endDate; }
    public void setDays(String days)                     { this.days = days; }
    public void setGradesPublished(boolean gradesPublished) { this.gradesPublished = gradesPublished; }
    public void setStudentIds(List<String> studentIds)   { this.studentIds = studentIds; }

    public void addStudent(String studentId) {
        if (!studentIds.contains(studentId)) studentIds.add(studentId);
    }

    public void removeStudent(String studentId) {
        studentIds.remove(studentId);
    }

    public String toFileLine() {
        String sids = String.join(",", studentIds);
        return id + "|" + parentCourseId + "|" + instructorId + "|" + room + "|" + branch
                + "|" + price + "|" + startDate.format(FMT) + "|" + endDate.format(FMT)
                + "|" + days + "|" + gradesPublished + "|" + sids;
    }

    public static Course fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 11) throw new IllegalArgumentException("Invalid Course line: " + line);
        Course c = new Course(
                parts[0], parts[1], parts[2], parts[3], parts[4],
                Double.parseDouble(parts[5]),
                LocalDate.parse(parts[6], FMT),
                LocalDate.parse(parts[7], FMT),
                parts[8]
        );
        c.gradesPublished = Boolean.parseBoolean(parts[9]);
        if (!parts[10].isBlank()) {
            for (String sid : parts[10].split(",")) {
                sid = sid.trim();
                if (!sid.isEmpty() && !sid.equalsIgnoreCase("false")) {
                    c.studentIds.add(sid);
                }
            }
        }
        return c;
    }

    @Override
    public String toString() {
        return "[" + id + "] ParentCourse: " + parentCourseId
                + " | Instructor: " + instructorId
                + " | Room: " + room + " | Branch: " + branch
                + " | Price: " + price
                + " | " + startDate + " -> " + endDate
                + " | Days: " + days
                + " | Students: " + studentIds.size()
                + " | Grades Published: " + gradesPublished;
    }
}
