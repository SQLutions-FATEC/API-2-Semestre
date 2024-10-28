package app.helpers;

public class Utils {
    public static boolean isOnlyLetters(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        return input.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    public static int[] getPeriodFromFilter(String period) {
        String[] parts = period.split(" - ");
        String semesterPart = parts[0];
        String yearPart = parts[1];
        String semesterNumber = semesterPart.split("º")[0];
        int semester = Integer.parseInt(semesterNumber);
        int year = Integer.parseInt(yearPart);

        return new int[]{semester, year};
    }
}
