# Project Explanation - Team Leader & File Handling

## 📌 What is this part of the system?
This is the core foundation of the Courses Management System. It includes the overall architecture (how parts talk to each other) and the Data Storage module (File I/O).

## 📌 What does it do? (features)
* Manages reading from and writing to text files.
* Ensures data is not lost when the program closes.
* Provides the main entry point to start the app.
* Acts as the bridge between the Business Logic (Services) and the hard drive.

## 📌 Key classes used
* `storage.FileManager`
* `src/App.java` (Entry point)
* `models.User` (Base class for all users)

## 📌 Important methods
* `readLines(String filePath)`: Reads text files and ignores blank lines.
* `writeLines(String filePath, List<String> lines)`: Saves lists of data back to files.
* `loadAdmins()`, `saveAdmins()`, etc.: Specific methods for each entity.

## 📌 How it connects with other modules
Every Service class (like `AdminService` or `StudentService`) calls `FileManager` to get data (Lists of objects) and calls it again to save changes. The GUI never talks to `FileManager` directly.

## 📌 Example from the code
```java
// Saving a list of admins to the text file
public static void saveAdmins(List<Admin> admins) throws IOException {
    List<String> lines = new ArrayList<>();
    for (Admin a : admins) {
        lines.add(a.toFileLine()); // Convert object to text string (e.g. A01|Admin|...)
    }
    writeLines(ADMINS_FILE, lines);
}
```

## 📌 Possible questions in discussion + answers

**Q: Why did you use text files instead of a Database like MySQL?**
A: To keep the project simple, portable, and meet the specific requirements of this course phase without needing external servers.

**Q: What happens if a file doesn't exist when the program starts?**
A: The `ensure(String filePath)` method inside `FileManager` automatically creates the `src/data` folder and the missing `.txt` files so the program won't crash.

**Q: Explain the MVC or Layered Architecture in our project.**
A: 
1. **Models**: Store data (e.g., `Student`, `Course`).
2. **Storage**: Handles files (`FileManager`).
3. **Service (Logic)**: Does the thinking (`AuthService`, `AdminService`).
4. **UI**: The screens user sees (`LoginFrame`).
UI talks to Service. Service talks to Storage. Storage talks to Models.
