package app.helpers;

import app.DAOs.CriteriaDAO;
import app.DAOs.GradeDAO;
import app.interfaces.ScreenController;
import app.models.AverageGradeModel;
import app.models.CriteriaModel;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.Consumer;

public class Utils {
    private static Stage primaryStage;

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
        files.put("setSprintDataScreen", new String[]{"/professor/setSprintDataScreen.fxml", "Definir sprint"});
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
            if (stage != null) {
                primaryStage = stage;
            }
            primaryStage.setScene(scene);
            primaryStage.setTitle(screenName);
            primaryStage.show();
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

    private static Alert.AlertType setAlertType(String type) {
        return switch (type.toUpperCase()) {
            case "INFORMATION" -> Alert.AlertType.INFORMATION;
            case "ERROR" -> Alert.AlertType.ERROR;
            case "WARNING" -> Alert.AlertType.WARNING;
            case "CONFIRMATION" -> Alert.AlertType.CONFIRMATION;
            default -> Alert.AlertType.NONE;
        };
    }

    public static void setAlert(String type, String title, String text, Runnable onOkAction) {
        Alert alert = new Alert(setAlertType(type));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait()
                .filter(response -> response == ButtonType.OK)
                .ifPresent(_ -> {
                    if (onOkAction != null) {
                        onOkAction.run();
                    }
                });
    }


    public static Date setDate(Date date, int days) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, days);

        return calendar.getTime();
    }

    public static void setAlert(String type, String title, String text) {
        Alert alert = new Alert(setAlertType(type));
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static String getDownloadsPath() {
        String os = System.getProperty("os.name").toLowerCase();
        Path caminho;

        if (os.contains("win") || os.contains("mac") || os.contains("nix") || os.contains("nux")) {
            caminho = Paths.get(System.getProperty("user.home"), "Downloads");
        } else {
            caminho = Paths.get(System.getProperty("user.home"), "Documentos");
        }

        return caminho.toString();
    }

    public static void createCsv(String caminhoArquivo, String teamName, int equipeID, int periodoID, String sprintDescription, int sprintID) throws SQLException {
        try {
            File file = new File(caminhoArquivo);

            if (file.exists()) {
                String timestamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
                String novoCaminho = caminhoArquivo.replaceFirst("(\\.csv)$", "_" + teamName + "_" + timestamp + "$1");
                file = new File(novoCaminho);
                caminhoArquivo = file.getAbsolutePath();
            } else {
                caminhoArquivo = Utils.getDownloadsPath() + "\\" + teamName + "_relatorio.csv";
            }

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(caminhoArquivo))) {
                writer.write("Equipe: " + teamName);
                writer.newLine();

                writer.write("Sprint: " + sprintDescription);
                writer.newLine();

                writer.write("Usuario");

                StringBuilder criteriaSQL = new StringBuilder();
                CriteriaDAO criteriaDAO = new CriteriaDAO();
                ObservableList<CriteriaModel> criterias = criteriaDAO.selectCriteriasByPeriodId(periodoID);

                for (CriteriaModel criteria: criterias) {
                    criteriaSQL.append(",");
                    criteriaSQL.append(criteria.getName());
                }

                writer.write(criteriaSQL.toString());
                writer.newLine();

                StringBuilder averageSQL = new StringBuilder();
                GradeDAO gradeDAO = new GradeDAO();
                Map<String, AverageGradeModel> averages = gradeDAO.selectAverages(equipeID, periodoID, sprintID);

                for (AverageGradeModel average: averages.values()) {
                    averageSQL.append(average.getName());
                    for (Map.Entry<String, Integer> currentAverage: average.getAverages().entrySet()) {
                        averageSQL.append(",");
                        averageSQL.append(currentAverage.getValue());
                    }
                    averageSQL.append("\n");
                }

                writer.write(averageSQL.toString());
                writer.newLine();

                Utils.setAlert("CONFIRMATION", "CSV", "Arquivo CSV gerado com sucesso em: " + caminhoArquivo);
            }
        } catch (IOException e) {
            System.err.println("Erro ao gerar o arquivo CSV: " + e.getMessage());
        }
    }

    public static String formatDate(LocalDate date) {
        if (date == null) {
            return "";
        }
        return date.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
