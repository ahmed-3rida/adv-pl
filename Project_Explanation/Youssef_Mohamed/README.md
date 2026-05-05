# Project Explanation - Instructor Operations & Reporting

## 📌 Assigned Files to Study
1. `service/InstructorService.java` (Logic for instructor operations)
2. `ui/InstructorDashboard.java` (Instructor's main view)
3. `models/Grade.java` (Entity representing a student's score)
4. `ui/ReportsFrame.java` (Admin/Instructor reports generation)

## 📌 What is this part of the system?
This part contains the primary tools for the teaching staff. It dictates what an instructor can do once they log into the system, mainly focusing on viewing their courses, grading students, and generating reports.

## 📌 What does it do? (Features)
* `InstructorDashboard` lists the specific courses assigned to the logged-in instructor.
* It allows the instructor to select a course, view enrolled students, and assign/update `Grade` records.
* It allows instructors to "Publish" grades to make them visible to students.
* The `ReportsFrame` is used to generate data views and statistics (e.g., passing rates, student performance) for academic evaluation.

## 📌 How it connects with other modules
It takes over after the `LoginFrame` authenticates an Instructor. It interacts heavily with `FileManager` to read Courses and update Grades. The `Grade` data created here is later consumed by the Student module so students can see their results.

## 📌 Possible questions in discussion + answers
**Q: Can an instructor add grades for any student?**
A: No. Inside `InstructorDashboard` and `InstructorService`, the code filters the courses to show only those assigned to this instructor. Furthermore, grading checks ensure the student is actually enrolled in that specific course.

**Q: What exactly does "Publishing" grades do?**
A: It simply changes a boolean variable `isGradesPublished` from `false` to `true` inside the `Course` object. The student module checks this boolean before displaying the grade.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Youssef Mohamed:**
أنت مسؤول عن "شاشة الدكتور وإدارة الدرجات والتقارير".
- فايل `InstructorDashboard.java` و `InstructorService.java`: دي الشاشة الرئيسية بتاعة الدكتور واللوجيك بتاعها، الدكتور بيدخل يلاقي الكورسات بتاعته بس (مش كورسات دكاترة تانية)، ويقدر يدخل على كل كورس ويشوف الطلاب ويحطلهم درجات.
- فايل `Grade.java`: ده الكلاس اللي بيمثل درجة الطالب في المادة.
- فايل `ReportsFrame.java`: دي شاشة بتعمل تقارير (Reports) عن أداء الطلاب ونسب النجاح، مفيدة جداً للإدارة والدكاترة.
**باختصار:** انت مسؤول عن تجربة الدكتور على السيستم؛ إزاي بيشوف كورساته، إزاي بيحط أو يعدل درجات، وإزاي بيعمل Publish للدرجات عشان الطلاب تقدر تشوفها، بجانب شاشة التقارير.
