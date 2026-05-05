# Project Explanation - Student Portal & Feedback System

## 📌 Assigned Files to Study
1. `service/StudentService.java` (Logic for student operations)
2. `ui/StudentDashboard.java` (Student's main view)
3. `models/Survey.java` (Entity for course feedback)
4. `ui/ViewSurveysFrame.java` (Admin UI to read student feedback)

## 📌 What is this part of the system?
This module covers the entire user journey for a Student. It allows them to enroll in classes, view their grades (if published), and provide feedback (surveys) on their educational experience.

## 📌 What does it do? (Features)
* `StudentDashboard` is the central hub where students can browse available courses and enroll themselves.
* It displays the student's current enrollments and their Grades. It strictly enforces checking if `isGradesPublished` is true before showing the grade.
* It provides a UI for students to fill out a `Survey` for a completed course.
* `ViewSurveysFrame` is used by Admins (and sometimes instructors) to read the collected surveys to monitor teaching quality.

## 📌 How it connects with other modules
It takes over after `LoginFrame` validates a Student. It interacts with `Course` structure to handle enrollments. It waits for the Instructor module to update `Grade` objects and publish them before displaying anything to the student.

## 📌 Possible questions in discussion + answers
**Q: How do we prevent a student from enrolling in the same course twice?**
A: Inside `StudentDashboard` (or `StudentService`), when a student clicks enroll, the system checks if the `Course`'s enrolled students list already contains this student's ID. If it does, it shows an error message and stops the enrollment.

**Q: Where do the Surveys go after the student submits them?**
A: The `Survey` object is created and passed to `FileManager` to be saved in a JSON/CSV file. Later, the `ViewSurveysFrame` reads this file and populates a table for the administration to review.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Youssef Safwat:**
أنت مسؤول عن "شاشة الطالب ونظام التقييم (Surveys)".
- فايل `StudentDashboard.java` و `StudentService.java`: دي الشاشة الرئيسية للطالب، بيقدر منها يشوف الكورسات المتاحة ويسجل فيها (Enroll)، ويشوف الكورسات اللي هو مسجل فيها حالياً. كمان بيقدر يشوف درجاته بس بشرط إن الدكتور يكون عملها Publish.
- فايل `Survey.java`: ده كلاس بيمثل التقييم أو الفيدباك اللي الطالب بيكتبه عن الكورس.
- فايل `ViewSurveysFrame.java`: دي الشاشة اللي الأدمن بيقدر يدخل عليها عشان يشوف رأي وتقييمات الطلاب في الكورسات والدكاترة عشان يراقب جودة التعليم.
**باختصار:** انت مسؤول عن تجربة الطالب من أول ما يسجل في كورس لحد ما يشوف نتيجته، ومسؤول عن نظام الاستبيانات اللي الطالب بيقيم بيه الكورس.
