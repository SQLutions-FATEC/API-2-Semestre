package app.controllers;

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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import javafx.geometry.Side;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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

    ObservableList<UserModel> studentTableData = FXCollections.observableArrayList();
    ObservableList<UserModel> studentList = FXCollections.observableArrayList();
    ObservableList<TeamModel> teamList = FXCollections.observableArrayList();
    private ContextMenu suggestionsMenu = new ContextMenu();

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        studentSearch.setPromptText("Digite para pesquisar");
        colName.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNome()));
        colRA.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getRa()).asObject().asString());
        colEmail.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEmail()));
        colTeam.setCellValueFactory(cellData -> new SimpleIntegerProperty(cellData.getValue().getEquipeId()).asObject().asString());
        tableStudent.setItems(studentTableData);

        fetchStudents();
        fetchTeams();
        configureAutocomplete();
    }

    private void fetchStudents() {
        UserDAO userDAO = new UserDAO();
        studentList = userDAO.fetchStudents();
    }

    private void fetchTeams() {
        TeamDAO teamDAO = new TeamDAO();
        teamList = teamDAO.fetchTeams();
        ObservableList<String> teamNames = FXCollections.observableArrayList();
        for (TeamModel team : teamList) {
            teamNames.add(team.getName());
        }
        teamChoiceBox.getItems().addAll(teamNames);
        teamChoiceBox.setValue(teamNames.get(0));
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
//        parei aqui, tem que resetar a tabela a cada aluno que adicionar e tem que usar o nome da equipe ao inves do id
        if (!studentTableData.contains(selectedStudent)) {
            studentTableData.add(selectedStudent);
        }
    }

    public void homeScreen(ActionEvent event) throws IOException {
        Utils.setScreen(event, "/professor/professorScreen.fxml");
    }
}
