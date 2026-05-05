# 🎓 Courses Management System

![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white)
![Java Swing](https://img.shields.io/badge/Java_Swing-007396?style=for-the-badge&logo=java&logoColor=white)

A comprehensive desktop application built with Java and Swing, designed to manage educational institutions. It provides a robust role-based authentication system with dedicated portals for Administrators, Instructors, and Students. The system handles course scheduling, student enrollments, grading, infrastructure management (branches and rooms), and student surveys.

## ✨ Key Features

### 🛡️ Role-Based Access Control
- **Admin Portal**: Full CRUD control over Users, Parent Courses, Branches, Rooms, and specific Course instances. View comprehensive system reports.
- **Instructor Portal**: View assigned courses, manage student grades, and publish results.
- **Student Portal**: Browse available courses, enroll in classes, view published grades, and submit feedback surveys.

### 🏢 Infrastructure Management
- Create and manage physical/virtual branches.
- Assign rooms with specific capacities to branches.
- Dynamically filter and assign rooms when scheduling a course instance.

### 📚 Academic Structure
- **Parent Courses**: Define the core syllabus, credit hours, and general course information.
- **Course Instances**: Create specific sections of a Parent Course taught by a specific Instructor in a specific Room.

### 💾 Data Persistence
- Custom lightweight JSON/CSV-based local file storage.
- No external database required—everything is self-contained.

## 🛠️ Technologies Used
- **Java 17+**: Core application logic and Object-Oriented architecture.
- **Java Swing**: Graphical User Interface (GUI) with a customized, modern dark theme.
- **File I/O**: Local data storage and persistence.

## 🚀 How to Run

1. Clone the repository to your local machine.
2. Compile the project using your IDE (e.g., IntelliJ IDEA, Eclipse) or via the terminal.
3. Make sure to compile all `.java` files in the `models`, `service`, `storage`, and `ui` packages.
4. Run the main class: `App.java`.
5. *Note: Ensure your IDE is set to use UTF-8 encoding so the UI renders correctly.*

---

# 📚 Courses Management System - Team Distribution & Project Structure

هذا القسم يوضح تقسيم المهام على فريق العمل (7 طلاب) وهيكلة ملفات المشروع بالكامل، بحيث يكون واضحاً مسؤولية كل شخص عن أي جزء في النظام.

---

## 👥 فريق العمل وتقسيم المهام (Team Members & Responsibilities)

| الاسم (Name) | المسؤولية (Responsibility) | المهام الأساسية (Key Tasks) |
| --- | --- | --- |
| **1. ABO Donia** | الأساسيات، تخزين البيانات والتصميم (Core Architecture, Storage & UI) | حفظ البيانات في الملفات (`FileManager`)، التحكم في شكل البرنامج (`UITheme`)، والـ `ValidationUtils`. |
| **2. Abdelrahman Mohamed** | كلاسات المستخدمين ونظام تسجيل الدخول (User Models & Authentication) | بناء كلاسات المستخدمين (`User`, `Admin`, `Student`, `Instructor`)، والتأكد من صحة بيانات الدخول (`AuthService` و `LoginFrame`). |
| **3. Ahmed Salman** | نقطة البداية وهيكلة الكورسات والمواد (App Entry & Course Logic) | تشغيل النظام (`App`)، تصميم المواد الأساسية (`ParentCourse`) والكورسات الفعّالة (`Course`)، وعمل اللوجيك الخاص بيهم (`CourseService`). |
| **4. Kareem Ayman** | لوحة تحكم الأدمن وإدارة الأشخاص (Admin Dashboard & Users CRUD) | تصميم لوحة تحكم الأدمن، وإضافة/حذف/تعديل الطلاب والدكاترة (`AdminService`, `ManageStudentsFrame`, `ManageInstructorsFrame`). |
| **5. Nada Ibrahim** | البنية التحتية، الفروع، وإنشاء الكورسات (Infrastructure & Course Creation) | إدارة الفروع والقاعات (`Room`, `ManageBranchesFrame`, `ManageRoomsFrame`)، وربط الكورس بالفرع والقاعة المتاحة. |
| **6. Youssef Mohamed** | شاشة الدكتور والدرجات والتقارير (Instructor Dashboard & Grading) | شاشة الدكتور وتعديل الدرجات، وعمل التقارير (`InstructorService`, `InstructorDashboard`, `Grade`, `ReportsFrame`). |
| **7. Youssef Safwat** | شاشة الطالب والتقييمات (Student Dashboard & Surveys) | شاشة الطالب لتسجيل الكورسات ورؤية الدرجات، بالإضافة لنظام التقييم (`StudentService`, `StudentDashboard`, `Survey`, `ViewSurveysFrame`). |

---

## 📂 هيكلة المشروع والمسؤوليات (Project Structure & Ownership)

فيما يلي جميع ملفات المشروع (30 ملف) مقسمة حسب مكانها، وبجوار كل ملف اسم الشخص المسؤول عن شرحه وفهمه:

```text
📦 adv-pl (Project Root)
 ┣ 📜 App.java ........................................... 🧑‍💻 [Ahmed Salman]
 ┃
 ┣ 📂 models (Data Classes)
 ┃ ┣ 📜 Admin.java ....................................... 🧑‍💻 [Abdelrahman Mohamed]
 ┃ ┣ 📜 Course.java ...................................... 🧑‍💻 [Ahmed Salman]
 ┃ ┣ 📜 Grade.java ....................................... 🧑‍💻 [Youssef Mohamed]
 ┃ ┣ 📜 Instructor.java .................................. 🧑‍💻 [Abdelrahman Mohamed]
 ┃ ┣ 📜 ParentCourse.java ................................ 🧑‍💻 [Ahmed Salman]
 ┃ ┣ 📜 Room.java ........................................ 🧑‍💻 [Nada Ibrahim]
 ┃ ┣ 📜 Student.java ..................................... 🧑‍💻 [Abdelrahman Mohamed]
 ┃ ┣ 📜 Survey.java ...................................... 🧑‍💻 [Youssef Safwat]
 ┃ ┗ 📜 User.java ........................................ 🧑‍💻 [Abdelrahman Mohamed]
 ┃
 ┣ 📂 service (Business Logic)
 ┃ ┣ 📜 AdminService.java ................................ 🧑‍💻 [Kareem Ayman]
 ┃ ┣ 📜 AuthService.java ................................. 🧑‍💻 [Abdelrahman Mohamed]
 ┃ ┣ 📜 CourseService.java ............................... 🧑‍💻 [Ahmed Salman]
 ┃ ┣ 📜 InstructorService.java ........................... 🧑‍💻 [Youssef Mohamed]
 ┃ ┣ 📜 StudentService.java .............................. 🧑‍💻 [Youssef Safwat]
 ┃ ┗ 📜 ValidationUtils.java ............................. 🧑‍💻 [ABO Donia]
 ┃
 ┣ 📂 storage (Data Persistence)
 ┃ ┗ 📜 FileManager.java ................................. 🧑‍💻 [ABO Donia]
 ┃
 ┗ 📂 ui (Graphical User Interfaces)
   ┣ 📜 AdminDashboard.java .............................. 🧑‍💻 [Kareem Ayman]
   ┣ 📜 InstructorDashboard.java ......................... 🧑‍💻 [Youssef Mohamed]
   ┣ 📜 LoginFrame.java .................................. 🧑‍💻 [Abdelrahman Mohamed]
   ┣ 📜 ManageBranchesFrame.java ......................... 🧑‍💻 [Nada Ibrahim]
   ┣ 📜 ManageCoursesFrame.java .......................... 🧑‍💻 [Nada Ibrahim]
   ┣ 📜 ManageInstructorsFrame.java ...................... 🧑‍💻 [Kareem Ayman]
   ┣ 📜 ManageParentCoursesFrame.java .................... 🧑‍💻 [Ahmed Salman]
   ┣ 📜 ManageRoomsFrame.java ............................ 🧑‍💻 [Nada Ibrahim]
   ┣ 📜 ManageStudentsFrame.java ......................... 🧑‍💻 [Kareem Ayman]
   ┣ 📜 ReportsFrame.java ................................ 🧑‍💻 [Youssef Mohamed]
   ┣ 📜 StudentDashboard.java ............................ 🧑‍💻 [Youssef Safwat]
   ┣ 📜 UITheme.java ..................................... 🧑‍💻 [ABO Donia]
   ┗ 📜 ViewSurveysFrame.java ............................ 🧑‍💻 [Youssef Safwat]
```

## 📝 ملاحظات هامة للجميع
- **كل طالب ملزم بقراءة الـ README الخاص به في مجلد `Project_Explanation`، حيث يحتوي على تفاصيل وشرح الأكواد الخاصة به.**
- جميع أجزاء المشروع مترابطة. فهمك للجزء الخاص بك يتطلب فهماً عاماً لكيفية اتصاله بالأجزاء الأخرى.
