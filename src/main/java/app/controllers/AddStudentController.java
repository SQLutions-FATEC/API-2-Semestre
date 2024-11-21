package app.controllers;

import app.DAOs.PeriodDAO;
import app.DAOs.TeamDAO;
import app.DAOs.UserDAO;
import app.helpers.Utils;
import app.models.UserModel;
import app.models.EquipeModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

public class AddStudentController {
    protected Scene scene;

    @FXML
    public TableView<UserModel> tableView;
    @FXML
    public TableColumn<UserModel, Integer> colRa;
    @FXML
    public TableColumn<UserModel, String> colName;
    @FXML
    public TableColumn<UserModel, String> colEmail;
    @FXML
    public TableColumn<UserModel, String> colPassword;
    @FXML
    public Label labelTeamName;
    @FXML
    public Label labelTeamGithub;
    @FXML
    public Button buttonSendCSV;

    BufferedReader reader = null;
    String line;

    private void processCSV(File file) {
        try {
            reader = new BufferedReader(new FileReader(file));

            ObservableList<UserModel> alunoList = FXCollections.observableArrayList();
            EquipeModel equipe;
            boolean isPrimeiraLinha = true;

            while ((line = reader.readLine()) != null) {
                String[] linha = line.split(",");

                if (isPrimeiraLinha) {
                    if (linha.length < 2) {
                        System.out.println("Linha da equipe inválida: " + line);
                        return;
                    }
                    equipe = new EquipeModel(0, linha[0], linha[1]);
                    isPrimeiraLinha = false;
                    labelTeamName.setText(equipe.getNome());
                    labelTeamGithub.setText(equipe.getGithub());
                    continue;
                }

                if (linha.length != 4) {
                    System.out.println("Linha de aluno inválida: " + line);
                    continue;
                }

                UserModel aluno = new UserModel(
                        Integer.parseInt(linha[0]),
                        linha[1],
                        linha[2],
                        linha[3],
                        0
                );
                alunoList.add(aluno);
            }

            colRa.setCellValueFactory(new PropertyValueFactory<>("ra"));
            colName.setCellValueFactory(new PropertyValueFactory<>("nome"));
            colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));
            colPassword.setCellValueFactory(new PropertyValueFactory<>("senha"));

            tableView.setItems(alunoList);
        } catch (IOException e) {
            System.out.println("Erro ao ler o arquivo: " + e.getMessage());
        } finally {
            try {
                if (reader != null) {
                    reader.close();
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }

    @FXML
    public void acceptCSV() {
        PeriodDAO periodDAO = new PeriodDAO();
        int periodId = periodDAO.selectCurrentPeriodId();

        TeamDAO teamDAO = new TeamDAO();
        int teamId = teamDAO.createTeam(labelTeamName.getText(), labelTeamGithub.getText(), periodId);

        UserDAO userDAO = new UserDAO();
        userDAO.createStudents(tableView.getItems(), teamId);
        Utils.setAlert("CONFIRMATION", "Adição de alunos", "Os alunos foram adicionados com sucesso");
        tableView.getItems().clear();
    }

    @FXML
    public void sendCSV() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Selecione o arquivo CSV");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("CSV Files", "*.csv"));

        Stage stage = (Stage) buttonSendCSV.getScene().getWindow();
        File selectedFile = fileChooser.showOpenDialog(stage);

        if (selectedFile != null) {
            processCSV(selectedFile);
        } else {
            System.out.println("Nenhum arquivo selecionado.");
        }
    }

    @FXML
    public void goToProfessorScreen(ActionEvent event) {
        Utils.setScreen(event, "professorScreen");
    }
}
