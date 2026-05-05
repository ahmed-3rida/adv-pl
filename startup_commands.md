# تعليمات تشغيل المشروع (Setup & Run Instructions)

اتبع هذه الخطوات لتشغيل نظام إدارة الدورات (Courses Management System) من المجلد الحالي:

## 1. التجهيز (Setup)
تأكد من أنك في المجلد الرئيسي للمشروع:
`Project Final`

يجب أن يحتوي المجلد على:
*   `App.java`: الملف الرئيسي.
*   `models/`, `service/`, `storage/`, `ui/`: مجلدات الكود.
*   `data/`: مجلد البيانات.

---

## 2. أوامر التشغيل (PowerShell / CMD)

### الخطوة الأولى: إنشاء مجلد الكلاسات (Bin)
قم بفتح الـ Terminal في المجلد الرئيسي واكتب:
```powershell
if (-not (Test-Path bin)) { New-Item -ItemType Directory bin }
```

### الخطوة الثانية: تجميع الكود (Compile)
قم بكتابة هذا الأمر لترجمة جميع ملفات الجافا:
```powershell
javac -d bin App.java models/*.java storage/*.java service/*.java ui/*.java
```

### الخطوة الثالثة: تشغيل البرنامج (Run)
استخدم هذا الأمر لفتح الواجهة الرسومية (Swing GUI):
```powershell
java -cp bin App
```

---

## 3. معلومات الدخول الافتراضية
*   **Email:** `admin@cms.com`
*   **Password:** `admin123`

---

## 4. حل المشاكل الشائعة
*   **مشكلة المسارات:** تأكد من أنك تشغل الأمر من المجلد الذي يحتوي على مجلد `data`.
*   **التعديلات:** إذا قمت بتعديل الكود، يجب عليك إعادة تشغيل أمر الـ Compile (الخطوة الثانية) ثم الـ Run (الخطوة الثالثة).
