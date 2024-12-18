package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.TeamDAO;
import app.DAOs.UserDAO;
import app.models.TeamModel;
import app.models.UserModel;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import app.helpers.Utils;
import javafx.scene.control.*;
import javafx.geometry.Side;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.HashMap;
import java.util.Map;

public class EditStudentController implements Initializable {
    @FXML
    public TableView<UserModel> tableStudent;
    @FXML
    public TableColumn<UserModel, String> colName;
    @FXML
    public TableColumn<UserModel, String> colRA;
    @FXML
    public TableColumn<UserModel, String> colEmail;
    @FXML
    public TableColumn<UserModel, String> colTeam;
    @FXML
    public ChoiceBox<String> teamChoiceBox;
    @FXML
    public TextField studentSearch;
    @FXML
    public Button deleteButton;
    @FXML
    public Button confirmButton;

    ObservableList<UserModel> studentTableData = FXCollections.observableArrayList();
    ObservableList<UserModel> studentList = FXCollections.observableArrayList();
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    private ContextMenu suggestionsMenu = new ContextMenu();
    private Map<Integer, String> teamNamesMap = new HashMap<>();
    private int selectedTeamId;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colRA.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRa()).asObject().asString());
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colTeam.setCellValueFactory(cellData -> {
            Integer teamId = cellData.getValue().getEquipeId();
            String teamName = teamNamesMap.getOrDefault(teamId, "Equipe Desconhecida");
            return new SimpleStringProperty(teamName);
        });
        tableStudent.setItems(studentTableData);

        fetchStudents();
        fetchTeams();
        configureAutocomplete();
        setTeamId();
        deleteButton.setDisable(true);
        confirmButton.setDisable(true);
    }

    private void fetchStudents() {
        UserDAO userDAO = new UserDAO();
        studentList = userDAO.selectStudents();
    }

    private void fetchTeams() {
        PeriodDAO periodDAO = new PeriodDAO();
        int periodId = periodDAO.selectCurrentPeriodId();

        TeamDAO teamDAO = new TeamDAO();
        teamList = teamDAO.selectTeamsByPeriod(periodId);
        if (teamList.isEmpty()) return;;

        ObservableList<String> teamNames = FXCollections.observableArrayList();

        for (TeamModel team : teamList) {
            teamNames.add(team.getName());
            teamNamesMap.put(team.getId(), team.getName());
        }

        teamChoiceBox.getItems().addAll(teamNames);
        teamChoiceBox.setValue(teamNames.get(0));
        selectedTeamId = teamList.get(0).getId();
    }

    private void configureAutocomplete() {
        studentSearch.textProperty().addListener((obs, oldValue, newValue) -> {
            updateSuggestions(newValue);
        });

        studentSearch.setOnMouseClicked(event -> {
            if (!suggestionsMenu.isShowing()) {
                updateSuggestions(studentSearch.getText());
                suggestionsMenu.show(studentSearch, Side.BOTTOM, 0, 0);
            }
        });
    }

    private void updateSuggestions(String text) {
        ObservableList<UserModel> filteredList = studentList.filtered(
                student -> student.getNome().toLowerCase().contains(text.toLowerCase())
        );

        suggestionsMenu.getItems().clear();
        for (UserModel student : filteredList) {
            MenuItem suggestionItem = new MenuItem(student.getNome());
            suggestionItem.setOnAction(event -> {
                studentSearch.setText(student.getNome());
                addStudentToTable(student);
                studentSearch.setText("");
                suggestionsMenu.hide();
            });
            suggestionsMenu.getItems().add(suggestionItem);
        }

        if (!suggestionsMenu.getItems().isEmpty()) {
            suggestionsMenu.show(studentSearch, Side.BOTTOM, 0, 0);
        } else {
            suggestionsMenu.hide();
        }
    }

    private void addStudentToTable(UserModel selectedStudent) {
        if (!studentTableData.contains(selectedStudent)) {
            studentTableData.clear();
            studentTableData.add(selectedStudent);
            deleteButton.setDisable(false);
            confirmButton.setDisable(false);
        }
    }

    private void updateTeamNameInTable() {
        UserModel selectedStudent = studentTableData.get(0);

        if (selectedStudent != null) {
            selectedStudent.setEquipeId(selectedTeamId);
            tableStudent.refresh();
        }
    }

    @FXML
    private void deleteStudent() {
        Utils.setAlert("WARNING", "Deleção do aluno", "Tem certeza que deseja deletá-lo?", () -> {
            int ra = studentTableData.get(0).getRa();
            UserDAO userDAO = new UserDAO();
            int rowsAffected = userDAO.deleteStudent(ra);

            if (rowsAffected != 0) {
                studentTableData.clear();
                studentSearch.setText("");
                studentList.removeIf(student -> student.getRa() == ra);
                deleteButton.setDisable(true);
                confirmButton.setDisable(true);
                Utils.setAlert("CONFIRMATION", "Deleção do aluno", "O aluno foi deletado com sucesso");
            }
        });
    }

    @FXML
    private void changeStudentTeam() {
        int ra = studentTableData.get(0).getRa();
        UserDAO userDAO = new UserDAO();
        userDAO.updateStudentTeam(ra, selectedTeamId);
        updateTeamNameInTable();
        studentSearch.setText("");
        Utils.setAlert("CONFIRMATION", "Edição do aluno", "A equipe do aluno foi editada com sucesso");
    }

    @FXML
    private void setTeamId() {
        teamChoiceBox.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            for (Map.Entry<Integer, String> entry : teamNamesMap.entrySet()) {
                if (entry.getValue().equals(newValue)) {
                    selectedTeamId = entry.getKey();
                    break;
                }
            }
        });
    }

    @FXML
    public void goToProfessorScreen(ActionEvent event) throws IOException {
        Utils.setScreen(event, "professorScreen");
    }
}
