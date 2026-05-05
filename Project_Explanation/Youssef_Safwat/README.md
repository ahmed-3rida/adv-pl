# Project Explanation - Student Module

## 📌 What is this part of the system?
This module represents the Student Portal. It handles everything a student needs to do after logging into the system.

## 📌 What does it do? (features)
* Shows all available courses in the system.
* Shows courses the student is currently enrolled in.
* Allows the student to view their published grades.
* Allows students to submit surveys/feedback for courses.
* Allows students to update their personal info (name, password, phone, address).

## 📌 Key classes used
* `models.Student`, `models.Survey`, `models.Grade`
* `service.StudentService`

## 📌 Important methods
* `getEnrolledCourses(String studentId)`
* `getGrade(String studentId, String courseId)`
* `submitSurvey(...)`
* `updatePersonalInfo(...)`

## 📌 How it connects with other modules
It reads from `CourseService` to find courses. It reads `Grade` files (created by Instructors) to show marks. It creates `Survey` files which are later read by Admins and Instructors.

## 📌 Example from the code
```java
// Getting courses a student is enrolled in
public static List<Course> getEnrolledCourses(String studentId) throws IOException {
    List<Course> result = new ArrayList<>();
    for (Course c : CourseService.getAllCourses()) {
        // If the course contains this student's ID, add it to the result list
        if (c.getStudentIds().contains(studentId)) {
            result.add(c);
        }
    }
    return result;
}
```

## 📌 Possible questions in discussion + answers

**Q: Can a student view grades for a course if the instructor hasn't published them?**
A: No. In `StudentService.getGrade()`, we first check the course. If `course.isGradesPublished()` is false, the method returns null, and the UI says "Grade not available yet."

**Q: How do you prevent a student from submitting multiple surveys for the same course?**
A: Inside `submitSurvey()`, we loop through all existing surveys. If we find one with the same `studentId` and `courseId`, we return false to prevent duplicates.

**Q: Does the student see all courses or just theirs?**
A: The student has two tabs: "All Courses" (to see everything offered) and "My Courses" (filtered by their ID to show only what they are enrolled in).
