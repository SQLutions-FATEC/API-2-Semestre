package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.TeamDAO;
import app.helpers.Utils;
import app.models.PeriodModel;
import app.models.TeamModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.*;

public class ProfessorController implements Initializable {
    @FXML
    public TableView<TeamModel> teamTable;
    @FXML
    public TableColumn<TeamModel, String> colName;
    @FXML
    public TableColumn<TeamModel, String> colGithub;
    @FXML
    public TableColumn<TeamModel, Void> colVisualize;
    @FXML
    public Label title;
    @FXML
    public Label description;
    @FXML
    public ChoiceBox<String> periodChoiceBox;

    String[] period = Utils.getCurrentSemesterAndYear();
    Integer selectedPeriodId;
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    Map<String, PeriodModel> periodMap = new HashMap<>();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        periodChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            handlePeriodListSelectionChange(newValue);
        });
        fetchPeriods();
    }

    private void fetchPeriods() {
        ArrayList<String> periodOptionsList = new ArrayList<>();

        PeriodDAO periodDAO = new PeriodDAO();
        ObservableList<PeriodModel> periodList = periodDAO.selectPeriods();

        for (PeriodModel period : periodList) {
            String periodName = String.format("%dº semestre - %d", period.getSemester(), period.getYear());
            periodOptionsList.add(periodName);
            periodMap.put(periodName, period);
        }

        periodChoiceBox.getItems().addAll(periodOptionsList);
        periodChoiceBox.setValue(period[1] + " - " + period[0]);
    }

    public void fetchTeams() {
        TeamDAO teamDAO = new TeamDAO();
        teamList = teamDAO.selectTeamsByPeriod(selectedPeriodId);

        title.setText("Lista de Equipes");
        description.setText("Segue a lista das equipes cadastradas:");

        colName.setCellValueFactory(new PropertyValueFactory<>("name"));
        colGithub.setCellValueFactory(new PropertyValueFactory<>("github"));

        colVisualize.setCellFactory(column -> new TableCell<>() {
            @Override
            protected void updateItem(Void item, boolean empty) {
                super.updateItem(item, empty);
                if (empty) {
                    setGraphic(null);
                } else {
                    Button btn = new Button("Visualizar");
                    btn.setOnAction((ActionEvent event) -> {
                        TeamModel equipe = getTableView().getItems().get(getIndex());
                        openPopup(equipe.getId(), selectedPeriodId);
                    });
                    setGraphic(btn);
                    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
                    setStyle("-fx-alignment: CENTER;");
                }
            }
        });
        teamTable.setItems(teamList);
    }

    private void openPopup(int teamId, int periodId) {
        Utils.setPopup("averageScreen", 400, 600, controller -> {
            if (controller instanceof AverageController) {
                ((AverageController) controller).passData(teamId, periodId);
            }
        });
    }

    private void handlePeriodListSelectionChange(String period) {
        PeriodModel selectedPeriod = periodMap.getOrDefault(period, null);
        selectedPeriodId = (selectedPeriod != null) ? selectedPeriod.getId() : 0;
        teamList.clear();
        fetchTeams();
    }

    @FXML
    public void goToLoginScreen(ActionEvent event) {
        Utils.setScreen(event, "loginScreen");
    }

    @FXML
    public void goToAddStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "addStudentScreen");
    }

    @FXML
    public void goToCriteriaScreen(ActionEvent event) {
        Utils.setScreen(event, "criteriaScreen");
    }

    @FXML
    public void goToEditStudentScreen(ActionEvent event) {
        Utils.setScreen(event, "editStudentScreen");
    }

    @FXML
    public void goToSetScoreScreen(ActionEvent event) {
        Utils.setScreen(event, "setScore");
    }
    public void definirDataSprint(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/professor/setSprintData.fxml")));
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        stage.setScene(new Scene(root));
        stage.setTitle("Definir Data Sprint");
        stage.show();
    }

}
