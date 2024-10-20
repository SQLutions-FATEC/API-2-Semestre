package app.controllers;

import app.models.Aluno;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class StudentController {
    protected Stage stage;
    protected Parent root;
    protected Scene scene;

    @FXML
    private TableView<Aluno> tableView;

    @FXML
    private TableColumn<Aluno, String> colunaAluno;

    @FXML
    private TableColumn<Aluno, Integer> colunaProatividade;

    @FXML
    private TableColumn<Aluno, Integer> colunaAutonomia;

    @FXML
    private TableColumn<Aluno, Integer> colunaColaboracao;

    @FXML
    private TableColumn<Aluno, Integer> colunaEntrega;

    @FXML
    private ComboBox<String> MudarSprint;

    @FXML
    private Label LabelNumeroSprint;

    private ObservableList<Aluno> alunos;

    @FXML
    public void initialize() {
        colunaAluno.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaProatividade.setCellValueFactory(new PropertyValueFactory<>("proatividade"));
        colunaAutonomia.setCellValueFactory(new PropertyValueFactory<>("autonomia"));
        colunaColaboracao.setCellValueFactory(new PropertyValueFactory<>("colaboracao"));
        colunaEntrega.setCellValueFactory(new PropertyValueFactory<>("entrega"));

        colunaProatividade.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaAutonomia.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaColaboracao.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaEntrega.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));

        colunaProatividade.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setProatividade(event.getNewValue());
        });

        colunaAutonomia.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setAutonomia(event.getNewValue());
        });

        colunaColaboracao.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setColaboracao(event.getNewValue());
        });

        colunaEntrega.setOnEditCommit(event -> {
            Aluno aluno = event.getRowValue();
            aluno.setEntrega(event.getNewValue());
        });

        MudarSprint.getItems().addAll("Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4");
        MudarSprint.setOnAction(event -> atualizarDataSprint());

        alunos = FXCollections.observableArrayList();
        tableView.setItems(alunos);

        adicionarDados();
    }

    @FXML
    private void enviarNotas() {
        for (Aluno aluno : alunos) {
            System.out.println("Aluno: " + aluno.getNome());
            System.out.println("Proatividade: " + aluno.getProatividade());
            System.out.println("Autonomia: " + aluno.getAutonomia());
            System.out.println("Colaboração: " + aluno.getColaboracao());
            System.out.println("Entrega: " + aluno.getEntrega());
        }
    }

    @FXML
    private Label LabelDataSprint;

    private void atualizarDataSprint() {
        String selectedSprint = MudarSprint.getSelectionModel().getSelectedItem();
        if (selectedSprint != null) {
            LabelNumeroSprint.setText(selectedSprint);

            switch (selectedSprint) {
                case "Sprint 1":
                    LabelDataSprint.setText("09/09/2024 a 29/09/2024");
                    break;
                case "Sprint 2":
                    LabelDataSprint.setText("30/09/2024 a 20/10/2024");
                    break;
                case "Sprint 3":
                    LabelDataSprint.setText("21/10/2024 a 10/11/2024");
                    break;
                case "Sprint 4":
                    LabelDataSprint.setText("11/11/2024 a 01/12/2024");
                    break;
                default:
                    LabelDataSprint.setText("Selecione uma Sprint");
                    break;
            }
        }
    }

    public void voltarPrincipalScreen(ActionEvent event) throws IOException {
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("/student/mainScreen.fxml")));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);

        stage.setScene(scene);
        stage.setTitle("Login");
        stage.show();
    }

    private void adicionarDados() {
        alunos.add(new Aluno("Augusto Piatto", 0, 0, 0, 0));
        alunos.add(new Aluno("Bryan de Assis", 0, 0, 0, 0));
        alunos.add(new Aluno("Cainã Melo", 0, 0, 0, 0));
        alunos.add(new Aluno("Davi Soares", 0, 0, 0, 0));
        alunos.add(new Aluno("Enzo Lemos", 0, 0, 0, 0));
        alunos.add(new Aluno("Glória Felix", 0, 0, 0, 0));
        alunos.add(new Aluno("João Paulista", 0, 0, 0, 0));
        alunos.add(new Aluno("Lucas Eduardo", 0, 0, 0, 0));
        alunos.add(new Aluno("Tiago Torres", 0, 0, 0, 0));
    }
}
