package service;

public class ValidationUtils {

    public static boolean isValidEmail(String email) {
        if (email == null)
            return false;
        return email.contains("@") && email.contains(".");
    }

    public static boolean isValidPassword(String password) {
        if (password == null)
            return false;
        return password.length() >= 8;
    }

    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty())
            return false;
        return phone.matches("\\d+");
    }
}