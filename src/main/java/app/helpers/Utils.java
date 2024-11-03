package app.helpers;
import app.controllers.AverageController;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

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

    public static void setScreen(ActionEvent event, String screenFile) {
        Map<String, String[]> files = new HashMap<>();

        files.put("studentScreen", new String[]{"/student/studentScreen.fxml", "Tela do aluno"});
        files.put("professorScreen", new String[]{"/professor/professorScreen.fxml", "Tela do professor"});
        files.put("criteriaScreen", new String[]{"/professor/criteriaScreen.fxml", "Definir critérios"});
        files.put("addStudentScreen", new String[]{"/professor/addStudentScreen.fxml", "Adicionar aluno"});
        files.put("editStudentScreen", new String[]{"/professor/editStudentScreen.fxml", "Editar aluno"});
        files.put("setScore", new String[]{"/professor/setScore.fxml", "Definir pontuação"});

        String screenFXML = files.get(screenFile)[0];
        String screenName = files.get(screenFile)[1];

        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(Utils.class.getResource(screenFXML)));
            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(screenName);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setPopup(String screenFile, int height, int width, Consumer<Object> controllerAction) {
        Map<String, String[]> files = new HashMap<>();

        files.put("averageScreen", new String[]{"/professor/averageScreen.fxml", "Médias"});

        String screenFXML = files.get(screenFile)[0];
        String screenName = files.get(screenFile)[1];

        try {
            FXMLLoader loader = new FXMLLoader(Utils.class.getResource(screenFXML));
            Parent root = loader.load();

            Object controller = loader.getController();
            if (controllerAction != null) {
                controllerAction.accept(controller);
            }

            Stage stage = new Stage();
            Scene scene = new Scene(root, width, height);

            stage.setScene(scene);
            stage.setTitle(screenName);
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setAlert(String type, String title, String text) {
        Alert.AlertType alertType = switch (type.toUpperCase()) {
            case "INFORMATION" -> Alert.AlertType.INFORMATION;
            case "ERROR" -> Alert.AlertType.ERROR;
            case "WARNING" -> Alert.AlertType.WARNING;
            case "CONFIRMATION" -> Alert.AlertType.CONFIRMATION;
            default -> Alert.AlertType.NONE;
        };

        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }
}
