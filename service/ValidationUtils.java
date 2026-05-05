package service;

public class ValidationUtils {

    /**
     * Requirement: Email must contain '@' and '.'
     */
    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        return email.contains("@") && email.contains(".");
    }

    /**
     * Requirement: Password must be at least 8 characters.
     */
    public static boolean isValidPassword(String password) {
        if (password == null)
            return false;
        return password.length() >= 8;
    }

    /**
     * Requirement: Phone must contain digits only.
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty())
            return false;
        return phone.matches("\\d+");
    }
}
            