package za.ac.cput.util;

import java.util.regex.Pattern;

public class Helper {

    // Regex patterns
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
    private static final String PHONE_REGEX = "^(\\+27|0)[0-9]{9}$"; // South African phone numbers
    private static final String PASSWORD_REGEX = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=!]).{8,}$";
    // Min 8 chars, at least 1 uppercase, 1 lowercase, 1 digit, 1 special char

    private Helper() {
        // Private constructor to prevent instantiation
    }

    /**
     * Validates email address
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        return Pattern.matches(EMAIL_REGEX, email);
    }

    /**
     * Validates South African phone number
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        return Pattern.matches(PHONE_REGEX, phone);
    }

    /**
     * Validates password strength
     */
    public static boolean isValidPassword(String password) {
        if (password == null) return false;
        return Pattern.matches(PASSWORD_REGEX, password);
    }

    /**
     * Validates that a string is not null or empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }

    /**
     * Validates that a number is positive
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }

    /**
     * Validates that an integer is positive
     */
    public static boolean isPositive(int number) {
        return number > 0;
    }

    /**
     * Validates product price (positive and reasonable)
     */
    public static boolean isValidPrice(double price) {
        return isPositive(price) && price < 1_000_000; // Example: Max price 1 million
    }
}
