# 📚 Courses Management System - Team Distribution & Project Structure

هذا الملف يوضح تقسيم المهام على فريق العمل (7 طلاب) وهيكلة ملفات المشروع بالكامل، بحيث يكون واضحاً مسؤولية كل شخص عن أي جزء في النظام.

---

## 👥 فريق العمل وتقسيم المهام (Team Members & Responsibilities)

| الاسم (Name) | المسؤولية (Responsibility) | المهام الأساسية (Key Tasks) |
| --- | --- | --- |
| **1. ABO Donia** | الأساسيات، تخزين البيانات والتصميم (Core Architecture, Storage & UI) | تشغيل النظام (`App`)، حفظ البيانات في الملفات (`FileManager`)، التحكم في شكل البرنامج (`UITheme`)، والـ `ValidationUtils`. |
| **2. Abdelrahman Mohamed** | كلاسات المستخدمين ونظام تسجيل الدخول (User Models & Authentication) | بناء كلاسات المستخدمين (`User`, `Admin`, `Student`, `Instructor`)، والتأكد من صحة بيانات الدخول (`AuthService` و `LoginFrame`). |
| **3. Ahmed Salman** | هيكلة الكورسات والمواد (Course Structure & Logic) | تصميم المواد الأساسية (`ParentCourse`) والكورسات الفعّالة (`Course`)، وعمل اللوجيك الخاص بيهم (`CourseService`). |
| **4. Kareem Ayman** | لوحة تحكم الأدمن وإدارة الأشخاص (Admin Dashboard & Users CRUD) | تصميم لوحة تحكم الأدمن، وإضافة/حذف/تعديل الطلاب والدكاترة (`AdminService`, `ManageStudentsFrame`, `ManageInstructorsFrame`). |
| **5. Nada Ibrahim** | البنية التحتية، الفروع، وإنشاء الكورسات (Infrastructure & Course Creation) | إدارة الفروع والقاعات (`Room`, `ManageBranchesFrame`, `ManageRoomsFrame`)، وربط الكورس بالفرع والقاعة المتاحة. |
| **6. Youssef Mohamed** | شاشة الدكتور والدرجات والتقارير (Instructor Dashboard & Grading) | شاشة الدكتور وتعديل الدرجات، وعمل التقارير (`InstructorService`, `InstructorDashboard`, `Grade`, `ReportsFrame`). |
| **7. Youssef Safwat** | شاشة الطالب والتقييمات (Student Dashboard & Surveys) | شاشة الطالب لتسجيل الكورسات ورؤية الدرجات، بالإضافة لنظام التقييم (`StudentService`, `StudentDashboard`, `Survey`, `ViewSurveysFrame`). |

---

## 📂 هيكلة المشروع والمسؤوليات (Project Structure & Ownership)

فيما يلي جميع ملفات المشروع (30 ملف) مقسمة حسب مكانها، وبجوار كل ملف اسم الشخص المسؤول عن شرحه وفهمه:

```text
📦 adv-pl (Project Root)
 ┣ 📜 App.java ........................................... 🧑‍💻 [ABO Donia]
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
