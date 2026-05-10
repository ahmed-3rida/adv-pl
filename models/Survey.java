package models;

public class Survey {
    private String courseId;
    private String studentId;
    private int rating;
    private String comment;

    public Survey(String courseId, String studentId, int rating, String comment) {
        this.courseId = courseId;
        this.studentId = studentId;
        this.rating = rating;
        this.comment = comment;
    }

    public String getCourseId()  { return courseId; }
    public String getStudentId() { return studentId; }
    public int getRating()       { return rating; }
    public String getComment()   { return comment; }

    public void setRating(int rating)     { this.rating = rating; }
    public void setComment(String comment) { this.comment = comment; }

    public String toFileLine() {
        return courseId + "|" + studentId + "|" + rating + "|" + comment.replace("|", ";");
    }

    public static Survey fromFileLine(String line) {
        String[] parts = line.split("\\|", 4);
        if (parts.length < 4) throw new IllegalArgumentException("Invalid Survey line: " + line);
        return new Survey(parts[0], parts[1], Integer.parseInt(parts[2]), parts[3]);
    }

    @Override
    public String toString() {
        return "Course: " + courseId + " | Student: " + studentId
                + " | Rating: " + rating + "/5 | Comment: " + comment;
    }
}
