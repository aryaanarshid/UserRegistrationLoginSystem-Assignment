package userregistrationloginsystem;

import java.util.Scanner;

/**
 * ============================================================================
 * Course: Fundamentals of Programming - Assignment 02
 * Project: User Registration and Login System
 * 
 * Description:
 * A menu-driven console application in Java that allows up to 3 users to
 * register accounts with strict input validation, and log in with their
 * registered credentials.
 * 
 * Implemented Requirements & Rules:
 * 1. Registration: Accepts Full Name, Username, Email, and Password.
 * 2. Input Validation:
 *    - Rule 1: Username must have at least 5 characters and no spaces.
 *    - Rule 2: Email must contain '@' and a period ('.') after '@'.
 *    - Rule 3: Password must have at least 8 characters, >=1 uppercase letter,
 *              >=1 lowercase letter, >=1 digit, and >=1 special character.
 *    - Rule 4: No field may be left empty.
 *    - Rule 5: Username must be unique (duplicate usernames rejected).
 * 3. Login: Authenticates username & password against registered accounts.
 * 4. Authentication: Clear success and error notifications.
 * 5. Re-attempt: Allows user to retry login after an unsuccessful attempt.
 * 6. Capacity: Stores exactly up to 3 registered users in memory.
 * 
 * Programming Constraints Demonstrated:
 * - String methods: length(), contains(), indexOf(), equals(), isEmpty()
 * - Character methods: Character.isUpperCase(), Character.isLowerCase(), Character.isDigit()
 * - Menu-driven interface using Scanner.
 * ============================================================================
 */
public class UserRegistrationLoginSystem {

    // ========================================================================
    // CONSTANTS & STORAGE
    // ========================================================================
    
    // Rule 9: Maximum number of users allowed to be stored
    private static final int MAX_USERS = 3;

    // Array to hold registered User objects in memory during program execution
    private static final User[] users = new User[MAX_USERS];

    // Counter to track the current number of registered users (0 to 3)
    private static int userCount = 0;

    // Scanner instance for reading keyboard input from the user
    private static final Scanner scanner = new Scanner(System.in);

    /**
     * Inner class representing a User entity.
     * Encapsulates the user's registered details.
     */
    public static class User {
        private final String fullName;
        private final String username;
        private final String email;
        private final String password;

        public User(String fullName, String username, String email, String password) {
            this.fullName = fullName;
            this.username = username;
            this.email = email;
            this.password = password;
        }

        public String getFullName() {
            return fullName;
        }

        public String getUsername() {
            return username;
        }

        public String getEmail() {
            return email;
        }

        public String getPassword() {
            return password;
        }
    }

    // ========================================================================
    // MAIN ENTRY POINT
    // ========================================================================

    public static void main(String[] args) {
        boolean running = true;

        System.out.println("=================================================");
        System.out.println("  WELCOME TO USER REGISTRATION & LOGIN SYSTEM   ");
        System.out.println("=================================================");

        // Main program loop: displays menu until the user decides to exit
        while (running) {
            displayMenu();
            System.out.print("Enter your choice (1-3): ");
            String input = scanner.nextLine().trim();

            switch (input) {
                case "1":
                    handleRegistration();
                    break;
                case "2":
                    handleLogin();
                    break;
                case "3":
                    System.out.println("\nThank you for using the system. Goodbye!");
                    running = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid selection! Please enter 1, 2, or 3.\n");
                    break;
            }
        }

        // Close scanner upon exiting program
        scanner.close();
    }

    /**
     * Displays the main menu options and current registration capacity.
     */
    private static void displayMenu() {
        System.out.println("\n-------------------------------------------------");
        System.out.println("                   MAIN MENU                     ");
        System.out.println("-------------------------------------------------");
        System.out.println(" 1. Register a New Account (Slots used: " + userCount + "/" + MAX_USERS + ")");
        System.out.println(" 2. Login to Your Account");
        System.out.println(" 3. Exit Application");
        System.out.println("-------------------------------------------------");
    }

    // ========================================================================
    // REGISTRATION MODULE
    // ========================================================================

    /**
     * Handles the user registration workflow.
     * Enforces capacity limit (Rule 9) and all validation rules.
     */
    private static void handleRegistration() {
        System.out.println("\n=================================================");
        System.out.println("               USER REGISTRATION                 ");
        System.out.println("=================================================");

        // Rule 9: Check if maximum capacity of 3 users has been reached
        if (userCount >= MAX_USERS) {
            System.out.println("[!] Registration Limit Reached!");
            System.out.println("    A maximum of " + MAX_USERS + " users can be registered in this system.");
            System.out.println("    Returning to main menu...\n");
            return;
        }

        // 1. Accept Full Name
        System.out.print("Enter Full Name: ");
        String fullName = scanner.nextLine().trim();
        // Rule 4: Field cannot be empty
        if (fullName.isEmpty()) {
            System.out.println("[X] Registration Failed: Full Name cannot be left empty.");
            return;
        }

        // 2. Accept Username
        System.out.print("Enter Username (min 5 chars, no spaces): ");
        String username = scanner.nextLine().trim();
        // Validate username syntax rules (Rule 1 & Rule 4)
        if (!isValidUsername(username)) {
            return;
        }
        // Rule 5: Check uniqueness (no duplicate usernames)
        if (isUsernameTaken(username)) {
            System.out.println("[X] Registration Failed: The username '" + username + "' is already registered.");
            System.out.println("    Please choose a different, unique username.");
            return;
        }

        // 3. Accept Email Address
        System.out.print("Enter Email Address (e.g. user@domain.com): ");
        String email = scanner.nextLine().trim();
        // Validate email rules (Rule 2 & Rule 4)
        if (!isValidEmail(email)) {
            return;
        }

        // 4. Accept Password
        System.out.print("Enter Password (min 8 chars, uppercase, lowercase, digit, special char): ");
        String password = scanner.nextLine();
        // Validate password rules (Rule 3 & Rule 4)
        if (!isValidPassword(password)) {
            return;
        }

        // Store user in array
        users[userCount] = new User(fullName, username, email, password);
        userCount++;

        // Rule 7: Clear success message
        System.out.println("\n[✔] Registration Successful!");
        System.out.println("    Welcome aboard, " + fullName + "!");
        System.out.println("    Account created with username: " + username);
        System.out.println("    Remaining registration slots: " + (MAX_USERS - userCount));
    }

    // ========================================================================
    // LOGIN & AUTHENTICATION MODULE
    // ========================================================================

    /**
     * Handles the user login workflow.
     * Allows re-attempts on failure (Rule 8) and verifies credentials (Rule 6).
     */
    private static void handleLogin() {
        System.out.println("\n=================================================");
        System.out.println("                   USER LOGIN                    ");
        System.out.println("=================================================");

        // Verify that there is at least one registered user in the system
        if (userCount == 0) {
            System.out.println("[!] No registered users found in the system.");
            System.out.println("    Please register an account first from the main menu.");
            return;
        }

        // Rule 8: Loop to allow the user to re-attempt login after an unsuccessful attempt
        while (true) {
            System.out.print("Enter Username: ");
            String loginUsername = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String loginPassword = scanner.nextLine();

            // Check if fields are empty
            if (loginUsername.isEmpty() || loginPassword.isEmpty()) {
                System.out.println("\n[X] Login Failed: Username and Password cannot be empty.");
            } else {
                // Rule 6: Authenticate against registered credentials
                User authenticatedUser = authenticate(loginUsername, loginPassword);

                if (authenticatedUser != null) {
                    // Rule 7: Clear message for successful login
                    System.out.println("\n[✔] Login Successful!");
                    System.out.println("    Welcome back, " + authenticatedUser.getFullName() + "!");
                    System.out.println("    Email: " + authenticatedUser.getEmail());
                    System.out.println("    Status: Authenticated successfully.");
                    break; // Exit login loop on successful login
                } else {
                    // Rule 7: Clear message for failed login attempts
                    System.out.println("\n[X] Login Failed: Invalid username or password.");
                }
            }

            // Rule 8: Prompt to retry login
            System.out.print("\nWould you like to try logging in again? (yes/no): ");
            String retryChoice = scanner.nextLine().trim();

            // If user enters anything other than "yes" or "y", exit back to main menu
            if (!retryChoice.equalsIgnoreCase("yes") && !retryChoice.equalsIgnoreCase("y")) {
                System.out.println("Returning to main menu...");
                break;
            }

            System.out.println("\n--- Retrying Login ---");
        }
    }

    /**
     * Authenticates credentials against registered users.
     * Uses String equals() method for precise match.
     * 
     * @param username The username entered
     * @param password The password entered
     * @return User object if credentials match; null otherwise.
     */
    private static User authenticate(String username, String password) {
        for (int i = 0; i < userCount; i++) {
            // Constraint: String equals() method used for exact credentials comparison
            if (users[i].getUsername().equals(username) && users[i].getPassword().equals(password)) {
                return users[i];
            }
        }
        return null;
    }

    // ========================================================================
    // INPUT VALIDATION METHODS
    // ========================================================================

    /**
     * Validates the username according to:
     * - Rule 1: Must contain at least 5 characters and must not contain spaces.
     * - Rule 4: Must not be empty.
     * 
     * @param username The username to validate
     * @return true if valid, false otherwise with an error message printed.
     */
    public static boolean isValidUsername(String username) {
        // Rule 4: isEmpty() method check
        if (username.isEmpty()) {
            System.out.println("[X] Validation Error: Username cannot be left empty.");
            return false;
        }

        // Rule 1: length() constraint (at least 5 characters)
        if (username.length() < 5) {
            System.out.println("[X] Validation Error: Username must be at least 5 characters long (Entered: " 
                               + username.length() + " characters).");
            return false;
        }

        // Rule 1: contains() constraint (must not contain spaces)
        if (username.contains(" ")) {
            System.out.println("[X] Validation Error: Username must not contain any spaces.");
            return false;
        }

        return true;
    }

    /**
     * Validates the email address according to:
     * - Rule 2: Must contain '@' and a period ('.') after '@'.
     * - Rule 4: Must not be empty.
     * 
     * @param email The email to validate
     * @return true if valid, false otherwise with an error message printed.
     */
    public static boolean isValidEmail(String email) {
        // Rule 4: isEmpty() check
        if (email.isEmpty()) {
            System.out.println("[X] Validation Error: Email address cannot be left empty.");
            return false;
        }

        // Rule 2: contains() check for '@'
        if (!email.contains("@")) {
            System.out.println("[X] Validation Error: Email address must contain the '@' character.");
            return false;
        }

        // Rule 2: indexOf() check for period '.' after '@'
        int atIndex = email.indexOf('@');
        
        // Ensure '@' is not the very first character
        if (atIndex == 0) {
            System.out.println("[X] Validation Error: Email cannot start with '@'.");
            return false;
        }

        // Look for the period '.' occurring AFTER the '@'
        int dotAfterAtIndex = email.indexOf('.', atIndex);

        if (dotAfterAtIndex == -1) {
            System.out.println("[X] Validation Error: Email address must contain a period ('.') after '@'.");
            return false;
        }

        // Period must not be immediately following '@' (e.g. "user@.com" is invalid)
        if (dotAfterAtIndex == atIndex + 1) {
            System.out.println("[X] Validation Error: Email domain name cannot start with a period ('.') right after '@'.");
            return false;
        }

        // Period must not be the last character (e.g. "user@domain." is invalid)
        if (dotAfterAtIndex == email.length() - 1) {
            System.out.println("[X] Validation Error: Email cannot end with a period ('.').");
            return false;
        }

        return true;
    }

    /**
     * Validates the password according to:
     * - Rule 3: At least 8 characters long, contains at least one uppercase letter,
     *           at least one lowercase letter, at least one digit, and at least one special character.
     * - Rule 4: Must not be empty.
     * 
     * @param password The password to validate
     * @return true if valid, false otherwise with detailed error messages.
     */
    public static boolean isValidPassword(String password) {
        // Rule 4: isEmpty() check
        if (password.isEmpty()) {
            System.out.println("[X] Validation Error: Password cannot be left empty.");
            return false;
        }

        // Rule 3: length() check (at least 8 characters)
        if (password.length() < 8) {
            System.out.println("[X] Validation Error: Password must be at least 8 characters long (Entered: " 
                               + password.length() + " characters).");
            return false;
        }

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;
        boolean hasSpecialChar = false;

        // Character-checking methods used in a loop across password characters
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUppercase = true;
            } else if (Character.isLowerCase(ch)) {
                hasLowercase = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isWhitespace(ch)) {
                // Any non-alphanumeric and non-space symbol qualifies as a special character
                hasSpecialChar = true;
            }
        }

        // Report specific missing requirements for maximum clarity (Rule 7)
        if (!hasUppercase) {
            System.out.println("[X] Validation Error: Password must contain at least one uppercase letter (A-Z).");
            return false;
        }
        if (!hasLowercase) {
            System.out.println("[X] Validation Error: Password must contain at least one lowercase letter (a-z).");
            return false;
        }
        if (!hasDigit) {
            System.out.println("[X] Validation Error: Password must contain at least one digit (0-9).");
            return false;
        }
        if (!hasSpecialChar) {
            System.out.println("[X] Validation Error: Password must contain at least one special character (!@#$%^&* etc.).");
            return false;
        }

        return true;
    }

    /**
     * Checks whether a username has already been registered (Rule 5).
     * Compares case-insensitively so "john" and "John" are treated as the same username.
     * 
     * @param username The username to check
     * @return true if already taken, false if available.
     */
    public static boolean isUsernameTaken(String username) {
        for (int i = 0; i < userCount; i++) {
            // Using equalsIgnoreCase() so usernames are uniquely identified regardless of casing
            if (users[i].getUsername().equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
