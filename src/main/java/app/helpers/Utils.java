package app.helpers;

import app.interfaces.ScreenController;
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

    public static int[] getCurrentSemesterAndYearNumbers() {
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        int currentMonth = currentDate.getMonthValue();

        int semester;
        if (currentMonth >= 1 && currentMonth <= 6) {
            semester = 1;
        } else {
            semester = 2;
        }

        return new int[] {currentYear, semester};
    }

    public static String[] getCurrentSemesterAndYear() {
        int[] period = getCurrentSemesterAndYearNumbers();

        String semester = period[1] + "º semestre";
        String year = String.valueOf(period[0]);
        return new String[] {year, semester};
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

    public static void setScreen(ActionEvent event, String screenFile, Object data) {
        Map<String, String[]> files = new HashMap<>();

        files.put("loginScreen", new String[]{"/loginScreen.fxml", "Tela de login"});
        files.put("studentScreen", new String[]{"/student/studentScreen.fxml", "Tela do aluno"});
        files.put("outOfEvaluationPeriodScreen", new String[]{"/student/outOfEvaluationPeriodScreen.fxml", "Fora do período"});
        files.put("alreadyEvaluatedScreen", new String[]{"/student/alreadyEvaluatedScreen.fxml", "Período avaliado"});
        files.put("professorScreen", new String[]{"/professor/professorScreen.fxml", "Tela do professor"});
        files.put("criteriaScreen", new String[]{"/professor/criteriaScreen.fxml", "Definir critérios"});
        files.put("addStudentScreen", new String[]{"/professor/addStudentScreen.fxml", "Adicionar aluno"});
        files.put("editStudentScreen", new String[]{"/professor/editStudentScreen.fxml", "Editar aluno"});
        files.put("setScore", new String[]{"/professor/setScore.fxml", "Definir pontuação"});
        files.put("setSprintData", new String[]{"/professor/setSprintData.fxml", "Definir sprint"});
        files.put("outOfSetScorePeriodScreen", new String[]{"/professor/outOfSetScorePeriodScreen.fxml", "Fora do período"});

        String screenFXML = files.get(screenFile)[0];
        String screenName = files.get(screenFile)[1];

        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Utils.class.getResource(screenFXML)));
            Parent root = loader.load();

            if (
                data != null ||
                Objects.equals(screenFile, "professorScreen") || Objects.equals(screenFile, "studentScreen")
            ) {
                ScreenController controller = loader.getController();
                controller.initData(data);
            }

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.setTitle(screenName);
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setScreen(ActionEvent event, String screenFile) {
        setScreen(event, screenFile, null);
    }

    public static void setPopup(String screenFile, int height, int width, Consumer<Object> controllerAction) {
        Map<String, String[]> files = new HashMap<>();

        files.put("averageScreen", new String[]{"/professor/averageScreen.fxml", "Médias"});
        files.put("pastEvaluationsScreen", new String[]{"/student/pastEvaluationsScreen.fxml", "Avaliações passadas"});

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

    public static Date setDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }
}
