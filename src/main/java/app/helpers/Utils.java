package app.helpers;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Utils {
    public static boolean isOnlyLetters(String input) {
        if (input == null || input.isEmpty()) {
            return false;
        }

        return input.matches("[a-zA-ZÀ-ÿ\\s]+");
    }

    public static int[] getPeriodFromFilter(String period) {
        if (!period.contains("-")) {
            throw new IllegalArgumentException("A string não contém o caractere '-'.");
        }
        String[] parts = period.split(" - ");
        String semesterPart = parts[0];
        String yearPart = parts[1];
        String semesterNumber = semesterPart.split("º")[0];
        int semester = Integer.parseInt(semesterNumber);
        int year = Integer.parseInt(yearPart);

        return new int[]{semester, year};
    }

    public static int[] getSprintFromFilter(String sprint) {
        if (!sprint.contains("-")) {
            throw new IllegalArgumentException("A string não contém o caractere '-'.");
        }
        String[] parts = sprint.split(" - ");
        String semesterPart = parts[0];
        String yearPart = parts[1];
        String semesterNumber = semesterPart.split("º")[0];
        int semester = Integer.parseInt(semesterNumber);
        int year = Integer.parseInt(yearPart);

        return new int[]{semester, year};
    }

    public static String[] obterSemestreEAnoAtual() {
        LocalDate dataAtual = LocalDate.now();

        int anoAtual = dataAtual.getYear();

        int mesAtual = dataAtual.getMonthValue();

        String semestre;
        if (mesAtual >= 1 && mesAtual <= 6) {
            semestre = "1º semestre";
        } else {
            semestre = "2º semestre";
        }

        return new String[] {String.valueOf(anoAtual), semestre};
    }
    
    public static String getCurrentSprint(ArrayList<String> sprints) {
        LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        for (String sprint : sprints) {
            String[] dates = sprint.split("[()\\-]");
            LocalDate startDate = LocalDate.parse(dates[1].trim(), formatter);
            LocalDate endDate = LocalDate.parse(dates[2].trim(), formatter);

            if ((dataAtual.isEqual(startDate) || dataAtual.isAfter(startDate)) &&
                    (dataAtual.isEqual(endDate) || dataAtual.isBefore(endDate))) {
                return sprint;
            }
        }
        return null;
    }


}
