package models;

public class Grade {
    private String courseId;
    private String studentId;
    private double grade;

    public Grade(String courseId, String studentId, double grade) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.grade = grade;
    }

    public String getCourseId()   { return courseId; }
    public String getStudentId()  { return studentId; }
    public double getGrade()      { return grade; }
    public void setGrade(double grade) { this.grade = grade; }

    public String toFileLine() {
        return courseId + "|" + studentId + "|" + grade;
    }

    public static Grade fromFileLine(String line) {
        String[] parts = line.split("\\|", -1);
        if (parts.length < 3) throw new IllegalArgumentException("Invalid Grade line: " + line);
        return new Grade(parts[0], parts[1], Double.parseDouble(parts[2]));
    }

    @Override
    public String toString() {
        return "Course: " + courseId + " | Student: " + studentId + " | Grade: " + grade;
    }
}
