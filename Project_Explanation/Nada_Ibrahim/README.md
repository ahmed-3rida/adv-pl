# Project Explanation - Branches, Rooms & Course Scheduling Management

## 📌 Assigned Files to Study
1. `models/Room.java` (Entity representing physical/virtual rooms)
2. `ui/ManageBranchesFrame.java` (UI to manage branches/campuses)
3. `ui/ManageRoomsFrame.java` (UI to manage rooms within branches)
4. `ui/ManageCoursesFrame.java` (UI to create actual courses and assign rooms)

## 📌 What is this part of the system?
This module organizes the physical or logical infrastructure of the institution and ties it to the actual scheduled courses. It handles where courses are taught.

## 📌 What does it do? (Features)
* Defines `Room.java` to track capacity and location limits.
* Allows admins to create geographical Branches (e.g., Main Campus, Downtown Branch) in `ManageBranchesFrame`.
* Allows admins to create specific Rooms linked to those Branches in `ManageRoomsFrame`.
* The `ManageCoursesFrame` is where it all comes together: Admins create a course, pick an instructor, and pick a Room (dynamically filtered by Branch) to schedule the class.

## 📌 How it connects with other modules
It depends on Ahmed Salman's `Course` models. This module is launched from Kareem Ayman's `AdminDashboard`. The UI dynamically updates; for example, in `ManageCoursesFrame`, selecting a specific Branch from a dropdown will update the Rooms dropdown to only show rooms in that branch.

## 📌 Possible questions in discussion + answers
**Q: How does the filtering of rooms by branch work in the UI?**
A: In `ManageCoursesFrame`, we attach an event listener to the Branch dropdown. When its value changes, an event triggers that fetches only the `Room` objects associated with that specific branch and populates the second dropdown.

**Q: Why separate Branches and Rooms?**
A: For scalability and normalization. If an institution grows to have multiple campuses, having a simple list of rooms gets messy. Hierarchical structures (Branch -> Room) provide a cleaner user experience and better data management.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ Nada Ibrahim:**
أنتِ مسؤولة عن "إدارة الفروع والقاعات وإنشاء الكورسات".
- فايل `Room.java`: بيمثل القاعة اللي الطلاب هتحضر فيها، وسعتها كام.
- فايل `ManageBranchesFrame.java`: الشاشة اللي الأدمن بيضيف منها فروع للكلية أو السنتر (زي فرع المعادي، فرع مدينة نصر).
- فايل `ManageRoomsFrame.java`: الشاشة اللي الأدمن بيضيف منها قاعات جوا الفروع دي.
- فايل `ManageCoursesFrame.java`: دي من أهم الشاشات، لأن هنا الأدمن بيعمل كورس جديد، ويختارله دكتور، ويختار الفرع، وبناءً على الفرع بيظهرله القاعات المتاحة فيه عشان يحدد مكان الكورس.
**باختصار:** أنتِ مسؤولة عن البنية التحتية (فروع وقاعات) وربطها بالكورسات، وإنك تعملي شاشة ذكية بتفلتر القاعات على حسب الفرع اللي الأدمن بيختاره.
