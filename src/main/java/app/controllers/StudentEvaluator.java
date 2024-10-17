package app.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;

public class StudentEvaluator {

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
    private ComboBox<String> MudarSprint; // ComboBox para selecionar a Sprint

    @FXML
    private Label labelDataSprint; // Label para mostrar a data da Sprint

    @FXML
    private Label LabelNumeroSprint; // Label do número da Sprint

    private ObservableList<Aluno> alunos;

    @FXML
    public void initialize() {
        // Inicializa as colunas
        colunaAluno.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colunaProatividade.setCellValueFactory(new PropertyValueFactory<>("proatividade"));
        colunaAutonomia.setCellValueFactory(new PropertyValueFactory<>("autonomia"));
        colunaColaboracao.setCellValueFactory(new PropertyValueFactory<>("colaboracao"));
        colunaEntrega.setCellValueFactory(new PropertyValueFactory<>("entrega"));

        // Configura as colunas para usar ComboBox
        colunaProatividade.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaAutonomia.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaColaboracao.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));
        colunaEntrega.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(0, 1, 2, 3)));

        // Adiciona listener para salvar as edições
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

        // Configura o ComboBox para mudar a Sprint
        MudarSprint.getItems().addAll("Sprint 1", "Sprint 2", "Sprint 3", "Sprint 4");
        MudarSprint.setOnAction(event -> atualizarDataSprint());

        // Cria a lista de alunos
        alunos = FXCollections.observableArrayList();
        tableView.setItems(alunos);

        // Adiciona alguns dados de exemplo
        adicionarDados();
    }

    @FXML
    private void enviarNotas() {
        // Percorre todos os alunos na tabela
        for (Aluno aluno : alunos) {
            // Aqui você já está trabalhando com os objetos Aluno que foram atualizados na tabela
            System.out.println("Aluno: " + aluno.getNome());
            System.out.println("Proatividade: " + aluno.getProatividade());
            System.out.println("Autonomia: " + aluno.getAutonomia());
            System.out.println("Colaboração: " + aluno.getColaboracao());
            System.out.println("Entrega: " + aluno.getEntrega());
        }
    }

    @FXML
    private Label LabelDataSprint; // Adicione isso para referenciar o Label

    private void atualizarDataSprint() {
        String selectedSprint = MudarSprint.getSelectionModel().getSelectedItem();
        if (selectedSprint != null) {
            // Atualiza o Label
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

    private void setCellFactory(TableColumn<Aluno, Integer> column) {
        column.setCellFactory(new Callback<TableColumn<Aluno, Integer>, TableCell<Aluno, Integer>>() {
            @Override
            public TableCell<Aluno, Integer> call(TableColumn<Aluno, Integer> param) {
                return new TableCell<Aluno, Integer>() {
                    @Override
                    protected void updateItem(Integer item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty || item == null) {
                            setText(null);
                        } else {
                            setText(item.toString());
                            setStyle("-fx-alignment: CENTER;"); // Centraliza as notas
                        }
                    }
                };
            }
        });
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
