# Project Explanation - Courses Module

## 📌 What is this part of the system?
This module is responsible for managing all academic courses, their schedules, and generating reports.

## 📌 What does it do? (features)
* Manage "Parent Courses" (the main subject, e.g., "Java").
* Manage "Scheduled Courses" (specific instances with an instructor, room, and dates).
* Enroll students into courses or remove them.
* Generate reports (e.g., finding courses starting or ending within X days).

## 📌 Key classes used
* `models.ParentCourse`
* `models.Course`
* `service.CourseService`

## 📌 Important methods
* `createCourse(...)`
* `enrollStudent(String courseId, String studentId)`
* `getCoursesNearToStart(int days)`
* `getCoursesNearToEnd(int days)`

## 📌 How it connects with other modules
This module connects the Admin, Instructor, and Student. Admins create the courses. Instructors teach them. Students enroll in them. The `Course` model holds a list of `studentIds` to link students to the course.

## 📌 Example from the code
```java
// Enrolling a student in a course
public static boolean enrollStudent(String courseId, String studentId) throws IOException {
    List<Course> courses = FileManager.loadCourses();
    for (Course c : courses) {
        if (c.getId().equals(courseId)) {
            // Check if student is already inside
            if (c.getStudentIds().contains(studentId)) {
                return false; 
            }
            c.addStudent(studentId);
            FileManager.saveCourses(courses);
            return true;
        }
    }
    return false;
}
```

## 📌 Possible questions in discussion + answers

**Q: What is the difference between ParentCourse and Course?**
A: `ParentCourse` is the general subject (like Math 101). `Course` is the actual class being taught in a specific room, by a specific instructor, on specific dates.

**Q: How do you know which students are in a course?**
A: The `Course` object has a `List<String> studentIds`. It stores the IDs of the students enrolled in that specific class.

**Q: How does the reporting function work?**
A: `getCoursesNearToStart` gets today's date using `LocalDate.now()`, adds the number of days the user requested, and then checks if any course's start date falls within that range.
