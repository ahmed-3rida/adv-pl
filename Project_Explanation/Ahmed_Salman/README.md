# Project Explanation - Basic Course Structure & Parent Courses

## 📌 Assigned Files to Study
1. `models/ParentCourse.java` (Blueprint for courses)
2. `models/Course.java` (Actual instantiated course instances)
3. `ui/ManageParentCoursesFrame.java` (UI for creating course blueprints)
4. `service/CourseService.java` (Business logic for courses)

## 📌 What is this part of the system?
This module is responsible for defining the educational content of the system. It establishes the concept of a "Parent Course" (like a general syllabus) and "Courses" (specific instances of that syllabus running in a specific semester with a specific instructor).

## 📌 What does it do? (Features)
* Defines `ParentCourse` which acts as a template (e.g., "Intro to Programming", 3 credit hours).
* Defines `Course` which represents a real class derived from a Parent Course (e.g., "Intro to Programming - Fall 2026 - Room 101").
* Provides the interface `ManageParentCoursesFrame` for admins to define these templates.
* Provides `CourseService` which handles all logic related to fetching, validating, and associating courses with students and instructors.

## 📌 How it connects with other modules
It provides the core entities that all other modules interact with. The `AdminDashboard` accesses `ManageParentCoursesFrame`. `CourseService` is called heavily by `InstructorService` (to get courses an instructor teaches) and `StudentService` (to get enrolled courses).

## 📌 Possible questions in discussion + answers
**Q: What is the difference between `ParentCourse` and `Course`?**
A: `ParentCourse` is the catalogue definition (e.g. CS101, Computer Science basics). `Course` is the actual class being taught right now (e.g. CS101 Group A taught by Dr. Smith on Mondays). This allows multiple sections of the same course.

**Q: Where does `CourseService` get its data?**
A: It calls `FileManager` methods to load JSON/CSV files and then processes that raw data into lists of objects.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Ahmed Salman:**
أنت مسؤول عن "هيكلة الكورسات والمواد الدراسية".
- فايل `ParentCourse.java`: ده بيمثل المادة الأساسية كفكرة (زي مادة برمجة ١، ٣ ساعات معتمدة). 
- فايل `Course.java`: ده بيمثل الكورس الفعلي اللي بيُدرس حاليا للطلاب (يعني مادة برمجة ١، مجموعة أ، اللي بيديها دكتور فلان يوم الأحد).
- فايل `ManageParentCoursesFrame.java`: الشاشة اللي الأدمن بيضيف منها المواد الأساسية للكلية أو المعهد.
- فايل `CourseService.java`: ده الفايل اللي فيه الشغل كله (اللوجيك) الخاص بالكورسات؛ إزاي نجيب بيانات كورس معين، وإزاي نربط كورس بمادة أساسية.
**باختصار:** انت مسؤول عن أهم جزء في النظام (الكورسات)، وبتمثل فكرة إننا نفصل بين المادة كمنهج (ParentCourse) وبين الكورس الفعلي اللي الطلاب بتسجل فيه (Course).
