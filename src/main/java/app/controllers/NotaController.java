package app.controllers;

import app.helpers.NotaDAO;
import app.models.NotaModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.util.List;

public class NotaController {

    @FXML
    private TableView<NotaModel> tableViewNotas;

    @FXML
    private TableColumn<NotaModel, Integer> colAluno;

    @FXML
    private TableColumn<NotaModel, Integer> colCriterio;

    @FXML
    private TableColumn<NotaModel, Double> colMediaNota;

    public void initialize() {
        colAluno.setCellValueFactory(new PropertyValueFactory<>("idAluno"));
        colCriterio.setCellValueFactory(new PropertyValueFactory<>("idCriterio"));
        colMediaNota.setCellValueFactory(new PropertyValueFactory<>("mediaNota"));
    }

    public void carregarDados(int idEquipe, int idSemestre, int idSprint, Integer idAluno) {
        NotaDAO notaDAO = new NotaDAO();
        List<NotaModel> notas = notaDAO.buscarNotas(idEquipe, idSemestre, idSprint, idAluno);
        ObservableList<NotaModel> observableNotas = FXCollections.observableArrayList(notas);
        tableViewNotas.setItems(observableNotas);
    }
}