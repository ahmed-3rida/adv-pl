# Project Explanation - User Models & Authentication System

## 📌 Assigned Files to Study
1. `models/User.java` (Base User Class)
2. `models/Admin.java`
3. `models/Student.java`
4. `models/Instructor.java`
5. `service/AuthService.java` (Authentication Logic)
6. `ui/LoginFrame.java` (Login Screen)

## 📌 What is this part of the system?
This part represents the Object-Oriented representations of the people who use the system, and the security layer that allows them to log in and access their respective dashboards.

## 📌 What does it do? (Features)
* Defines the core `User` abstraction containing common attributes (id, name, username, password).
* Implements inheritance by creating specific classes (`Admin`, `Student`, `Instructor`) that extend the `User` class.
* Validates login credentials against stored data (`AuthService.java`).
* Determines the type of user logging in and opens the corresponding Dashboard (`LoginFrame.java`).

## 📌 How it connects with other modules
This is the gateway to the application. `LoginFrame` takes user input, sends it to `AuthService`. `AuthService` interacts with `FileManager` to read the stored users. Once validated, the system redirects to either `AdminDashboard`, `InstructorDashboard`, or `StudentDashboard` depending on the class type of the logged-in user.

## 📌 Possible questions in discussion + answers
**Q: How does the system know if a user is an Admin, Student, or Instructor?**
A: Because of Polymorphism and Inheritance. When `AuthService` checks the credentials, the returned `User` object is actually an instance of one of the specific sub-classes. We can use the `instanceof` keyword or role properties to direct them appropriately.

**Q: Why make `User` an abstract class or parent class?**
A: Because Admins, Students, and Instructors all share common data (like username, password). Putting these in `User` prevents code duplication, demonstrating proper OOP Inheritance principles.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Abdelrahman Mohamed:**
أنت مسؤول عن "بيانات المستخدمين ونظام تسجيل الدخول".
- فايلات Models (`User, Admin, Student, Instructor`): دي الكلاسات اللي بتمثل أي شخص بيستخدم النظام. كلاس `User` هو الأساس (الأب) وفيه الحاجات المشتركة زي الاسم والباسورد، وباقي الكلاسات بتورث منه عشان مفيش كود يتكرر.
- فايل `AuthService.java`: ده العقل المدبر لتسجيل الدخول. بياخد اليوزرنيم والباسورد ويتأكد إنهم موجودين وصح.
- فايل `LoginFrame.java`: دي الشاشة الأولى اللي بتظهر لأي حد، بياخد البيانات منها ويبعتها للـ AuthService، ولما يتأكد إنها صح، بيفتح الشاشة المناسبة (سواء شاشة الأدمن أو الطالب أو الدكتور) حسب نوع الشخص.
**باختصار:** انت مسؤول عن حماية النظام والتأكد من هوية الشخص اللي بيعمل تسجيل دخول وتوجيهه للشاشة بتاعته، ومسؤول عن تطبيق مفهوم الـ Inheritance (الوراثة) في الكلاسات.
