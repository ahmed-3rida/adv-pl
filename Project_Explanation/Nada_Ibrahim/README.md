# Project Explanation - Instructor Module

## 📌 What is this part of the system?
This module is the Instructor Portal. It controls how teachers interact with their assigned classes and students.

## 📌 What does it do? (features)
* Displays courses assigned specifically to the logged-in instructor.
* Allows the instructor to add or update student grades.
* Allows the instructor to "Publish" grades so students can see them.
* Allows instructors to view student surveys/feedback.

## 📌 Key classes used
* `models.Instructor`, `models.Grade`
* `service.InstructorService`

## 📌 Important methods
* `getAssignedCourses(String instructorId)`
* `addOrUpdateGrade(...)`
* `publishGrades(String courseId, String instructorId)`

## 📌 How it connects with other modules
It filters the master list of `Courses` (from `CourseService`) to show only the instructor's classes. It creates/updates `Grade` records, which the `StudentService` later reads. 

## 📌 Example from the code
```java
// Publishing grades for a course
public static boolean publishGrades(String courseId, String instructorId) throws IOException {
    List<Course> courses = FileManager.loadCourses();
    for (Course c : courses) {
        if (c.getId().equals(courseId)) {
            // Set the boolean flag to true
            c.setGradesPublished(true);
            FileManager.saveCourses(courses);
            return true;
        }
    }
    return false;
}
```

## 📌 Possible questions in discussion + answers

**Q: Can an instructor add grades for any student?**
A: No. Inside `addOrUpdateGrade()`, the code checks `if (!course.getStudentIds().contains(studentId))`. If the student is not enrolled in that specific course, it refuses to add the grade.

**Q: What exactly does "Publishing" grades do?**
A: It simply changes a boolean variable `isGradesPublished` from `false` to `true` inside the `Course` object. The student module checks this boolean before displaying the grade.

**Q: Can instructors change a grade after it's published?**
A: Yes, instructors can always fix grading errors. Publishing only controls visibility for the student, not edit-ability for the teacher.
