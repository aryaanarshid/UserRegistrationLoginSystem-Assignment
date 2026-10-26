package userregistrationloginsystem;

import java.util.Scanner;

/**
 * ============================================================================
 * Course: Fundamentals of Programming - Assignment 02
 * Project: User Registration and Login System (Pure Procedural / Array-based)
 * 
 * Description:
 * A menu-driven console application in Java that allows up to 3 users to
 * register accounts with strict input validation, and log in with their
 * registered credentials.
 * 
 * NOTE: Implemented without any custom User class, using standard parallel 
 * arrays and variables as per introductory programming conventions.
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
 * 3. Login: Authenticates username & password against registered credentials.
 * 4. Authentication: Clear success and error notifications.
 * 5. Re-attempt: Allows user to retry login after an unsuccessful attempt.
 * 6. Capacity: Stores exactly up to 3 registered users using parallel arrays.
 * 
 * Programming Constraints Demonstrated:
 * - String methods: length(), contains(), indexOf(), equals(), isEmpty()
 * - Character methods: Character.isUpperCase(), Character.isLowerCase(), Character.isDigit()
 * - Menu-driven interface using Scanner.
 * ============================================================================
 */
public class UserRegistrationLoginSystem {

    // ========================================================================
    // CONSTANTS & VARIABLES (Storage for 3 Users Without Any Custom Class)
    // ========================================================================

    // Rule 9: Maximum number of users allowed to be stored
    private static final int MAX_USERS = 3;

    // Parallel arrays to store information for up to 3 users in variables
    private static final String[] fullNames = new String[MAX_USERS];
    private static final String[] usernames = new String[MAX_USERS];
    private static final String[] emails = new String[MAX_USERS];
    private static final String[] passwords = new String[MAX_USERS];

    // Variable to track the current number of registered users (0 to 3)
    private static int registeredCount = 0;

    // Scanner instance for reading keyboard input from the user
    private static final Scanner scanner = new Scanner(System.in);

    // ========================================================================
    // MAIN ENTRY POINT
    // ========================================================================

    public static void main(String[] args) {
        boolean isRunning = true;

        System.out.println("=================================================");
        System.out.println("  WELCOME TO USER REGISTRATION & LOGIN SYSTEM   ");
        System.out.println("=================================================");

        // Main program loop: keeps displaying menu until the user chooses to exit
        while (isRunning) {
            displayMenu();
            System.out.print("Enter your choice (1-3): ");
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    handleRegistration();
                    break;
                case "2":
                    handleLogin();
                    break;
                case "3":
                    System.out.println("\nThank you for using the system. Goodbye!");
                    isRunning = false;
                    break;
                default:
                    System.out.println("\n[!] Invalid choice! Please enter 1, 2, or 3.\n");
                    break;
            }
        }

        // Close scanner upon application termination
        scanner.close();
    }

    /**
     * Displays the main menu with options and current user count.
     */
    private static void displayMenu() {
        System.out.println("\n-------------------------------------------------");
        System.out.println("                   MAIN MENU                     ");
        System.out.println("-------------------------------------------------");
        System.out.println(" 1. Register a New Account (Slots used: " + registeredCount + "/" + MAX_USERS + ")");
        System.out.println(" 2. Login to Your Account");
        System.out.println(" 3. Exit Application");
        System.out.println("-------------------------------------------------");
    }

    // ========================================================================
    // REGISTRATION MODULE
    // ========================================================================

    /**
     * Handles user registration:
     * Enforces the 3-user capacity limit and all required validation checks.
     */
    private static void handleRegistration() {
        System.out.println("\n=================================================");
        System.out.println("               USER REGISTRATION                 ");
        System.out.println("=================================================");

        // Rule 9: Store information of 3 users only
        if (registeredCount >= MAX_USERS) {
            System.out.println("[!] Registration Limit Reached!");
            System.out.println("    Maximum " + MAX_USERS + " users are already registered in the system.");
            System.out.println("    Returning to main menu...\n");
            return;
        }

        // 1. Full Name input
        System.out.print("Enter Full Name: ");
        String enteredFullName = scanner.nextLine().trim();
        // Rule 4: No field may be left empty (isEmpty() method)
        if (enteredFullName.isEmpty()) {
            System.out.println("[X] Registration Failed: Full Name cannot be left empty.");
            return;
        }

        // 2. Username input
        System.out.print("Enter Username (min 5 chars, no spaces): ");
        String enteredUsername = scanner.nextLine().trim();
        // Validate Username rules (Rule 1 & Rule 4)
        if (!validateUsername(enteredUsername)) {
            return;
        }
        // Rule 5: Username must be unique
        if (isUsernameAlreadyTaken(enteredUsername)) {
            System.out.println("[X] Registration Failed: The username '" + enteredUsername + "' is already registered.");
            System.out.println("    Please choose a unique username.");
            return;
        }

        // 3. Email Address input
        System.out.print("Enter Email Address (e.g. user@domain.com): ");
        String enteredEmail = scanner.nextLine().trim();
        // Validate Email rules (Rule 2 & Rule 4)
        if (!validateEmail(enteredEmail)) {
            return;
        }

        // 4. Password input
        System.out.print("Enter Password (min 8 chars, uppercase, lowercase, digit, special char): ");
        String enteredPassword = scanner.nextLine();
        // Validate Password rules (Rule 3 & Rule 4)
        if (!validatePassword(enteredPassword)) {
            return;
        }

        // Store user information in parallel arrays at the current index
        fullNames[registeredCount] = enteredFullName;
        usernames[registeredCount] = enteredUsername;
        emails[registeredCount] = enteredEmail;
        passwords[registeredCount] = enteredPassword;
        registeredCount++;

        // Rule 7: Clear message for successful registration
        System.out.println("\n[✔] Registration Successful!");
        System.out.println("    Welcome aboard, " + enteredFullName + "!");
        System.out.println("    Username registered: " + enteredUsername);
        System.out.println("    Remaining slots: " + (MAX_USERS - registeredCount));
    }

    // ========================================================================
    // LOGIN & AUTHENTICATION MODULE
    // ========================================================================

    /**
     * Handles the user login process:
     * Authenticates credentials and allows immediate re-attempt on failure.
     */
    private static void handleLogin() {
        System.out.println("\n=================================================");
        System.out.println("                   USER LOGIN                    ");
        System.out.println("=================================================");

        // Verify if any users are registered
        if (registeredCount == 0) {
            System.out.println("[!] No users are currently registered in the system.");
            System.out.println("    Please register an account first from the main menu.");
            return;
        }

        // Rule 8: Allow the user to attempt login again after an unsuccessful attempt
        while (true) {
            System.out.print("Enter Username: ");
            String inputUsername = scanner.nextLine().trim();

            System.out.print("Enter Password: ");
            String inputPassword = scanner.nextLine();

            // Validate that login fields are not empty
            if (inputUsername.isEmpty() || inputPassword.isEmpty()) {
                System.out.println("\n[X] Login Failed: Username and password cannot be empty.");
            } else {
                // Rule 6: Authenticate credentials against registered users
                int userIndex = findUserIndexByCredentials(inputUsername, inputPassword);

                if (userIndex != -1) {
                    // Rule 7: Clear message for successful login
                    System.out.println("\n[✔] Login Successful!");
                    System.out.println("    Welcome back, " + fullNames[userIndex] + "!");
                    System.out.println("    Email Address: " + emails[userIndex]);
                    System.out.println("    Account Status: Authenticated.");
                    break; // Exit login loop on success
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
     * Verifies username and password against registered users.
     * Uses String equals() method for precise match.
     * 
     * @param username The username entered
     * @param password The password entered
     * @return index of matching user if authenticated; -1 if invalid credentials.
     */
    private static int findUserIndexByCredentials(String username, String password) {
        for (int i = 0; i < registeredCount; i++) {
            // Programming Constraint: equals() method used to verify credentials
            if (usernames[i].equals(username) && passwords[i].equals(password)) {
                return i;
            }
        }
        return -1;
    }

    // ========================================================================
    // INPUT VALIDATION METHODS
    // ========================================================================

    /**
     * Validates username according to:
     * - Rule 1: Must contain at least 5 characters and must not contain spaces.
     * - Rule 4: Must not be empty.
     * 
     * @param username The username to validate
     * @return true if valid; false with error message otherwise.
     */
    public static boolean validateUsername(String username) {
        // Rule 4: isEmpty() method check
        if (username.isEmpty()) {
            System.out.println("[X] Validation Error: Username cannot be left empty.");
            return false;
        }

        // Rule 1: length() constraint (at least 5 characters)
        if (username.length() < 5) {
            System.out.println("[X] Validation Error: Username must contain at least 5 characters (Entered: " 
                               + username.length() + ").");
            return false;
        }

        // Rule 1: contains() constraint (must not contain spaces)
        if (username.contains(" ")) {
            System.out.println("[X] Validation Error: Username must not contain spaces.");
            return false;
        }

        return true;
    }

    /**
     * Validates email according to:
     * - Rule 2: Must contain '@' and a period ('.') after '@'.
     * - Rule 4: Must not be empty.
     * 
     * @param email The email to validate
     * @return true if valid; false with error message otherwise.
     */
    public static boolean validateEmail(String email) {
        // Rule 4: isEmpty() check
        if (email.isEmpty()) {
            System.out.println("[X] Validation Error: Email address cannot be left empty.");
            return false;
        }

        // Rule 2: contains() check for '@'
        if (!email.contains("@")) {
            System.out.println("[X] Validation Error: Email address must contain '@'.");
            return false;
        }

        // Rule 2: indexOf() check for period '.' after '@'
        int atIndex = email.indexOf('@');

        // '@' should not be the first character
        if (atIndex == 0) {
            System.out.println("[X] Validation Error: Email address cannot start with '@'.");
            return false;
        }

        // Look for '.' after '@'
        int dotAfterAtIndex = email.indexOf('.', atIndex);

        if (dotAfterAtIndex == -1) {
            System.out.println("[X] Validation Error: Email address must contain a period ('.') after '@'.");
            return false;
        }

        // Period cannot be immediately next to '@' (e.g. user@.com)
        if (dotAfterAtIndex == atIndex + 1) {
            System.out.println("[X] Validation Error: Email domain cannot start with a period ('.') immediately after '@'.");
            return false;
        }

        // Period cannot be the last character (e.g. user@domain.)
        if (dotAfterAtIndex == email.length() - 1) {
            System.out.println("[X] Validation Error: Email address cannot end with a period ('.').");
            return false;
        }

        return true;
    }

    /**
     * Validates password according to:
     * - Rule 3: Must be at least 8 characters long, contain at least one uppercase letter,
     *           one lowercase letter, one digit, and one special character.
     * - Rule 4: Must not be empty.
     * 
     * @param password The password to validate
     * @return true if valid; false with specific error message otherwise.
     */
    public static boolean validatePassword(String password) {
        // Rule 4: isEmpty() check
        if (password.isEmpty()) {
            System.out.println("[X] Validation Error: Password cannot be left empty.");
            return false;
        }

        // Rule 3: length() check (at least 8 characters)
        if (password.length() < 8) {
            System.out.println("[X] Validation Error: Password must be at least 8 characters long (Entered: " 
                               + password.length() + ").");
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;

        // Loop through characters using Character checking methods
        for (int i = 0; i < password.length(); i++) {
            char ch = password.charAt(i);

            if (Character.isUpperCase(ch)) {
                hasUpper = true;
            } else if (Character.isLowerCase(ch)) {
                hasLower = true;
            } else if (Character.isDigit(ch)) {
                hasDigit = true;
            } else if (!Character.isWhitespace(ch)) {
                // Non-alphanumeric and non-whitespace character is a special character
                hasSpecial = true;
            }
        }

        // Display clear, specific validation feedback (Rule 7)
        if (!hasUpper) {
            System.out.println("[X] Validation Error: Password must contain at least one uppercase letter (A-Z).");
            return false;
        }
        if (!hasLower) {
            System.out.println("[X] Validation Error: Password must contain at least one lowercase letter (a-z).");
            return false;
        }
        if (!hasDigit) {
            System.out.println("[X] Validation Error: Password must contain at least one digit (0-9).");
            return false;
        }
        if (!hasSpecial) {
            System.out.println("[X] Validation Error: Password must contain at least one special character (!@#$%^&* etc.).");
            return false;
        }

        return true;
    }

    /**
     * Checks if a username has already been registered (Rule 5).
     * Compares case-insensitively using equalsIgnoreCase().
     * 
     * @param username The username to check
     * @return true if username is already registered; false otherwise.
     */
    public static boolean isUsernameAlreadyTaken(String username) {
        for (int i = 0; i < registeredCount; i++) {
            // Check uniqueness across all currently registered users
            if (usernames[i].equalsIgnoreCase(username)) {
                return true;
            }
        }
        return false;
    }
}
