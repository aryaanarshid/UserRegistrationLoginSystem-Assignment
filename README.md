# User Registration and Login System

A robust, menu-driven console application built in Java for **Fundamentals of Programming (Assignment 02)**. The system allows up to 3 users to register their credentials with thorough validation checks and log in securely.

---

## 📋 Features & Functional Requirements

1. **User Registration**:
   - Collects **Full Name**, **Username**, **Email Address**, and **Password**.
   - Validates each input field against rigorous academic rules.
   - Enforces a maximum capacity of **3 registered users**.
2. **Input Validation**:
   - **Rule 1 (Username)**: At least 5 characters long and contains no spaces (`length()`, `contains(" ")`).
   - **Rule 2 (Email)**: Contains `@` and a period (`.`) occurring after `@` (`indexOf('@')`, `indexOf('.')`).
   - **Rule 3 (Password)**: Minimum 8 characters; contains at least one uppercase letter, one lowercase letter, one digit, and one special character (`Character.isUpperCase()`, `Character.isLowerCase()`, `Character.isDigit()`).
   - **Rule 4 (Non-empty Fields)**: No registration field may be left empty (`isEmpty()`).
   - **Rule 5 (Unique Username)**: Prevents duplicate accounts using case-insensitive check (`equalsIgnoreCase()`).
3. **User Authentication & Login**:
   - Compares credentials using `equals()` against registered users.
   - Non-hardcoded, dynamic verification.
   - **Rule 8 (Retry Mechanism)**: Allows users to attempt login again immediately after a failed attempt.
4. **Clear User Feedback**:
   - Explicit notifications for duplicate accounts, validation errors, successful registration, successful login, and failed attempts.

---

## 📂 Project Directory Structure

```text
UserRegistrationLoginSystem-Assignment/
├── .gitignore                                 # Git ignore rules for clean commits
├── README.md                                  # Documentation and run guide
├── build.xml                                  # Ant build script
├── manifest.mf                                # Java manifest file
├── nbproject/                                 # NetBeans project configuration
│   ├── project.properties
│   └── project.xml
└── src/
    └── userregistrationloginsystem/
        └── UserRegistrationLoginSystem.java   # Main Java source code with detailed comments
```

---

## 🚀 How to Run in Apache NetBeans

### Method 1: Open as an Existing Project (Recommended)
1. Open **Apache NetBeans**.
2. Go to the menu bar: **File** ➔ **Open Project...** (or press `Cmd + Shift + O` on macOS / `Ctrl + Shift + O` on Windows/Linux).
3. Navigate to this folder (`UserRegistrationLoginSystem-Assignment`) and select it (it will show a coffee cup project icon).
4. Click **Open Project**.
5. Press **Run Project** button (green play icon) or press **F6** to run!
6. Use the NetBeans **Output Console** tab at the bottom to interact with the menu.

### Method 2: Open Source File Directly
1. In NetBeans, open `src/userregistrationloginsystem/UserRegistrationLoginSystem.java`.
2. Right-click anywhere in the editor window and select **Run File** (or press `Shift + F6`).

---

## 💻 How to Run from Terminal / Command Line

If you have JDK installed on your terminal path:

```bash
# 1. Navigate to the project root directory
cd UserRegistrationLoginSystem-Assignment

# 2. Compile the Java source file
javac -d build/classes src/userregistrationloginsystem/UserRegistrationLoginSystem.java

# 3. Run the compiled application
java -cp build/classes userregistrationloginsystem.UserRegistrationLoginSystem
```

---

## 🐙 Git & GitHub Fork Workflow

If you forked this assignment repository on GitHub and want to commit and push your solution:

### 1. Initialize Git (if not already cloned from your fork)
```bash
git init
```

### 2. Check the Status
```bash
git status
```
*(Notice that `.gitignore` prevents NetBeans temporary build files and `.DS_Store` from polluting your repo).*

### 3. Stage All Assignment Files
```bash
git add .
```

### 4. Commit Your Work
```bash
git commit -m "Complete User Registration and Login System assignment with NetBeans configuration"
```

### 5. Push to Your Forked Repository on GitHub
If you haven't connected your GitHub remote yet:
```bash
# Replace with your actual GitHub username and repository URL:
git remote add origin https://github.com/YOUR_USERNAME/UserRegistrationLoginSystem-Assignment.git

# Set default branch to main and push
git branch -M main
git push -u origin main
```

If you already cloned your fork:
```bash
git push origin main
```

---

## 📝 Sample Console Interaction

```text
=================================================
  WELCOME TO USER REGISTRATION & LOGIN SYSTEM   
=================================================

-------------------------------------------------
                   MAIN MENU                     
-------------------------------------------------
 1. Register a New Account (Slots used: 0/3)
 2. Login to Your Account
 3. Exit Application
-------------------------------------------------
Enter your choice (1-3): 1

=================================================
               USER REGISTRATION                 
=================================================
Enter Full Name: Aryan Arshad
Enter Username (min 5 chars, no spaces): aryan123
Enter Email Address (e.g. user@domain.com): aryan@cui.edu.pk
Enter Password (min 8 chars, uppercase, lowercase, digit, special char): Secret@2026

[✔] Registration Successful!
    Welcome aboard, Aryan Arshad!
    Account created with username: aryan123
    Remaining registration slots: 2
```
