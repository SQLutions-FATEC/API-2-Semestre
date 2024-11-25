package app.controllers;

import app.DAOs.SprintDAO;
import app.DAOs.PeerEvaluationDAO;
import app.helpers.Utils;
import app.models.EvaluationModel;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.util.Map;
import java.util.HashMap;


import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class PeerEvaluationController {
    @FXML
    private TableView<EvaluationModel> evaluationTable;
    @FXML
    private ComboBox<String> previousEvaluationsComboBox;

    private ObservableList<EvaluationModel> evaluationData = FXCollections.observableArrayList();
    private ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();
    private SprintDAO sprintDAO = new SprintDAO();
    private PeerEvaluationDAO peerEvaluationDAO = new PeerEvaluationDAO();
    private String userEmail = "";

    //método para receber o e-mail do aluno
    public void passData(String email) {
        userEmail = email;
        fetchSprints();
    }

    private void fetchSprints() {
    }

    //metodo para buscar sprints e atualizar a ChoiceBox
    public class SprintController {

        private int selectedPeriodId; // Variável representando o ID do período selecionado
        private SprintDAO sprintDAO = new SprintDAO(); // Instância de SprintDAO
        private Map<String, Integer> sprintIdMap = new HashMap<>(); // Mapeia descrição -> ID da sprint

        // Método para buscar sprints e atualizar a ComboBox
        public void fetchSprints() {
            try {
                // Obtém a lista de Sprints do DAO
                ObservableList<SprintModel> sprintList = sprintDAO.selectSprints(selectedPeriodId); // Usa o período atual

                // Verifica se a lista de Sprints foi carregada corretamente
                if (sprintList == null || sprintList.isEmpty()) {
                    System.out.println("Nenhuma sprint encontrada.");
                    return; // Sai se não houver sprints
                }

                // Prepara as opções para exibição
                ArrayList<String> sprintOptionsList = new ArrayList<>();
                SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");

                for (SprintModel sprint : sprintList) {
                    String formattedStartDate = dateFormat.format(sprint.getStartDate());
                    String formattedEndDate = dateFormat.format(sprint.getEndDate());
                    String sprintDescription = sprint.getDescription() + ": (" + formattedStartDate + " - " + formattedEndDate + ")";
                    sprintOptionsList.add(sprintDescription);
                    sprintIdMap.put(sprintDescription, sprint.getId()); // Adiciona no mapa
                }

                // Atualiza a ComboBox
                previousEvaluationsComboBox.getItems().clear(); // Limpa itens duplicados
                previousEvaluationsComboBox.getItems().addAll(sprintOptionsList);

                // Define o valor padrão na ComboBox
                String currentSprint = Utils.getCurrentSprint(sprintOptionsList);
                if (currentSprint != null) {
                    previousEvaluationsComboBox.setValue(currentSprint);
                } else if (!sprintOptionsList.isEmpty()) {
                    previousEvaluationsComboBox.setValue(sprintOptionsList.get(0));
                }

                System.out.println("Sprints carregadas na ComboBox: " + sprintOptionsList);

                // Listener para carregar avaliações ao selecionar uma Sprint
                previousEvaluationsComboBox.setOnAction(event -> {
                    String selectedSprintDescription = previousEvaluationsComboBox.getValue();
                    if (selectedSprintDescription != null && sprintIdMap.containsKey(selectedSprintDescription)) {
                        int selectedSprintId = sprintIdMap.get(selectedSprintDescription); // Obtém o ID pelo mapa
                        System.out.println("Sprint selecionada: " + selectedSprintDescription);
                        loadPeerEvaluations(selectedSprintId);
                    } else {
                        System.out.println("Nenhuma sprint válida selecionada.");
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
                System.out.println("Erro ao carregar as sprints: " + e.getMessage());
            }
        }
    }
    //método para carregar avaliações no TableView
    private void loadPeerEvaluations(int sprintId) {
        evaluationData.clear();
        evaluationData.addAll(peerEvaluationDAO.fetchPeerEvaluations(sprintId));
        evaluationTable.setItems(evaluationData);
    }

    //método para configurar colunas da tabela
    @FXML
    public void initialize() {
        TableColumn<EvaluationModel, String> evaluatorColumn = new TableColumn<>("Avaliador");
        TableColumn<EvaluationModel, String> criteriaColumn = new TableColumn<>("Critério");
        TableColumn<EvaluationModel, String> sprintColumn = new TableColumn<>("Sprint");
        TableColumn<EvaluationModel, Integer> evaluatedColumn = new TableColumn<>("Avaliado");

        evaluatorColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatorName"));
        criteriaColumn.setCellValueFactory(new PropertyValueFactory<>("criteria"));
        sprintColumn.setCellValueFactory(new PropertyValueFactory<>("sprintDescription"));
        evaluatedColumn.setCellValueFactory(new PropertyValueFactory<>("evaluatedStudentId"));

        // Adicionando as colunas individualmente para evitar o problema com varargs genéricos
        evaluationTable.getColumns().add(evaluatorColumn);
        evaluationTable.getColumns().add(criteriaColumn);
        evaluationTable.getColumns().add(sprintColumn);
        evaluationTable.getColumns().add(evaluatedColumn);
    }

    public void handleBackToEvaluation(ActionEvent actionEvent) {
        System.out.println("Botão 'Voltar' clicado!");
        previousEvaluationsComboBox.getScene().getWindow().hide();     }
}