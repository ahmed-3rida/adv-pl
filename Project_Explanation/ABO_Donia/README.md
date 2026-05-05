# Project Explanation - Core Architecture, Storage & Utilities

## 📌 Assigned Files to Study
1. `storage/FileManager.java` (Data Persistence Layer)
2. `ui/UITheme.java` (Core UI Styles & Components)
3. `service/ValidationUtils.java` (Input Validation)

## 📌 What is this part of the system?
This module is the backbone of the entire application. It handles the starting point of the application, styling logic for all Graphical User Interfaces (GUI), how data is saved and loaded, and global validation logic.

## 📌 What does it do? (Features)
* Initializes the application and sets up the primary window stage in `App.java`.
* Contains `FileManager`, which handles writing to and reading from JSON/CSV files locally so that data persists across application restarts.
* Provides a standardized theme (`UITheme.java`) meaning colors, buttons, and layout constraints are central, allowing all screens to look exactly the same and switch to dark/light modes if necessary.
* Includes `ValidationUtils` which centrally checks user inputs (e.g. checking if email is valid, passwords meet length criteria).

## 📌 How it connects with other modules
Almost every module relies on this part. When `AdminService` creates a user, it uses `FileManager` to save it. When `LoginFrame` is shown, it uses `UITheme` for its button styles and `ValidationUtils` to check the format of the inputted username/password.

## 📌 Possible questions in discussion + answers
**Q: Why do we have `FileManager` separate from the services?**
A: To follow the Single Responsibility Principle. Services handle business logic, while `FileManager` purely handles the mechanics of file input/output (I/O). If we decide to use a database later instead of files, we only change `FileManager` and the rest of the app stays the same.

**Q: What is the benefit of `UITheme`?**
A: It prevents code duplication. Instead of every developer writing button colors over and over, they just call `UITheme.createPrimaryButton()`.

---
## 📝 ملخص بالعربي (Arabic Summary)
**الجزء الخاص بـ ABO Donia:**
أنت مسؤول عن "أساسيات النظام وتخزين البيانات". 
- فايل `FileManager.java`: ده من أهم الفايلات في المشروع، وظيفته ياخد أي بيانات (طلاب، كورسات، درجات) ويسجلها في فايلات عشان لما نقفل البرنامج ونفتحه نلاقي الداتا موجودة وماتتمسحش.
- فايل `UITheme.java`: ده الفايل اللي فيه ألوان وتصميم الزراير والخلفيات، وظيفته إن شكل البرنامج كله يبقى موحد واحترافي وأي حد بيعمل شاشة جديدة بياخد التصميم منه.
- فايل `ValidationUtils.java`: ده الفايل اللي بيتأكد إن البيانات اللي اليوزر بيدخلها صحيحة، زي إن الإيميل مكتوب صح أو الباسورد مش أقل من عدد معين.
**باختصار:** انت مسؤول عن تشغيل البرنامج، حفظ الداتا، الشكل العام للبرنامج، والتأكد من صحة المدخلات.
