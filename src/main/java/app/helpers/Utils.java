package app.helpers;

public class Utils {
    public static boolean isOnlyLetters(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }
        return input.matches("[a-zA-ZÀ-ÿ\\s]+");
    }
}
