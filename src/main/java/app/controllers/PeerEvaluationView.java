package app.controllers;

import javafx.application.Application;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.geometry.Pos;

public class PeerEvaluationView extends Application {

    @Override
    public void start(Stage primaryStage) {
        // Labels de Título e Período
        Label titleLabel = new Label("SPRINT 1");
        Label periodLabel = new Label("Período: 09/09/2024 a 29/09/2024");

        // ComboBox para consulta de avaliações anteriores
        ComboBox<String> previousEvaluations = new ComboBox<>();
        previousEvaluations.getItems().add("Selecione");
        Label consultLabel = new Label("Consultar avaliações anteriores");

        // Tabela de Avaliações
        TableView<String[]> evaluationTable = new TableView<>();
        evaluationTable.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Colunas da Tabela
        TableColumn<String[], String> studentColumn = new TableColumn<>("Alunos");
        TableColumn<String[], String> proactivityColumn = new TableColumn<>("Proatividade");
        TableColumn<String[], String> autonomyColumn = new TableColumn<>("Autonomia");
        TableColumn<String[], String> collaborationColumn = new TableColumn<>("Colaboração");
        TableColumn<String[], String> deliveryColumn = new TableColumn<>("Entrega");

        evaluationTable.getColumns().addAll(studentColumn, proactivityColumn, autonomyColumn, collaborationColumn, deliveryColumn);

        // Configurando as colunas (exemplo de dados fictícios)
        studentColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[0]));
        proactivityColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[1]));
        autonomyColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[2]));
        collaborationColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[3]));
        deliveryColumn.setCellValueFactory(data -> new SimpleStringProperty(data.getValue()[4]));

        // Adicionar dados fictícios
        evaluationTable.getItems().addAll(
                new String[]{"Aluno 1", "1", "1", "1", "1"},
                new String[]{"Aluno 2", "2", "2", "2", "2"},
                new String[]{"Aluno 3", "3", "3", "3", "3"},
                new String[]{"Aluno 4", "1", "1", "1", "1"},
                new String[]{"Aluno 5", "2", "2", "2", "2"},
                new String[]{"Aluno 6", "3", "3", "3", "3"}
        );

        // Botão de Voltar
        Button backButton = new Button("Voltar para tela de avaliação");

        // Layout principal
        VBox layout = new VBox(10);
        layout.setAlignment(Pos.CENTER);

        HBox titleBox = new HBox(10, titleLabel, periodLabel);
        titleBox.setAlignment(Pos.CENTER_LEFT);

        HBox consultBox = new HBox(10, consultLabel, previousEvaluations);
        consultBox.setAlignment(Pos.CENTER_RIGHT);

        HBox headerBox = new HBox(200, titleBox, consultBox);
        headerBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(headerBox, evaluationTable, backButton);

        // Cena e Estágio
        Scene scene = new Scene(layout, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Histórico de Avaliações");
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
