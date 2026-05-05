# Project Explanation - Admin Dashboard & User Management

## 📌 Assigned Files to Study
1. `ui/AdminDashboard.java` (Main Admin Hub)
2. `service/AdminService.java` (Logic for admin operations)
3. `ui/ManageStudentsFrame.java` (UI for Student CRUD)
4. `ui/ManageInstructorsFrame.java` (UI for Instructor CRUD)

## 📌 What is this part of the system?
This is the central control hub for system administrators. It allows them to manage the people in the system (students and instructors).

## 📌 What does it do? (Features)
* The `AdminDashboard` serves as the main menu containing buttons to navigate to all other management screens.
* The `AdminService` provides the business logic to create, read, update, and delete (CRUD) operations for Students and Instructors.
* `ManageStudentsFrame` and `ManageInstructorsFrame` are user interfaces that list users in tables, and provide forms to add new ones or edit/delete existing ones.

## 📌 How it connects with other modules
The `AdminDashboard` is launched by the `LoginFrame` when an Admin logs in. It links out to other admin screens. `AdminService` directly updates the data via `FileManager`, making the new students and instructors immediately available for login via `AuthService`.

## 📌 Possible questions in discussion + answers
**Q: How do `ManageStudentsFrame` and `AdminService` interact?**
A: The UI frame collects text from the input fields (like name, username) when a button is clicked. It passes these values to `AdminService`. The service performs logic checks (like making sure the username is unique), and if it passes, it updates the data file.

**Q: What is CRUD?**
A: Create, Read, Update, Delete. It refers to the four basic functions needed to manage persistent data. These frames implement complete CRUD for the users.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Kareem Ayman:**
أنت مسؤول عن "لوحة تحكم الأدمن وإدارة المستخدمين".
- فايل `AdminDashboard.java`: دي الشاشة الرئيسية للأدمن أول ما يدخل، وفيها كل الزراير اللي بتوديه على باقي شاشات التحكم.
- فايل `AdminService.java`: ده الفايل اللي فيه اللوجيك اللي بيعمل إضافة أو مسح أو تعديل في بيانات الطلاب والدكاترة.
- فايلات `ManageStudentsFrame` و `ManageInstructorsFrame`: دي الشاشات اللي بتعرض جداول فيها كل الطلاب والدكاترة، وفيها أماكن عشان الأدمن يكتب بيانات شخص جديد ويضيفه أو يمسح حد موجود.
**باختصار:** انت مسؤول عن تحكم الأدمن في الأشخاص (CRUD Operations)، إزاي يضيف ويمسح ويعدل بيانات الطلاب والدكاترة عشان يقدروا يستخدموا السيستم.
