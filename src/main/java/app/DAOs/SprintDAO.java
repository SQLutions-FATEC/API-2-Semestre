package app.DAOs;

import app.helpers.DatabaseConnection;
import app.helpers.Utils;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SprintDAO {

    public ObservableList<SprintModel> selectSprints(int selectedPeriodId) {
        ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();
        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? ORDER BY s.data_inicio";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, selectedPeriodId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectSprints: " + e.getMessage());
        }
        return sprintList;
    }

    // Novo método para buscar sprints do período atual
    public ObservableList<SprintModel> selectCurrentPeriodSprints() {
        ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

        // Obter o período atual (Ex.: 1º semestre de 2024)
        int currentPeriodId = Utils.getSelectedPeriodId();
        if (currentPeriodId == -1) {
            System.out.println("Período atual não identificado.");
            return sprintList; // Retorna lista vazia se não encontrar o período
        }

        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? ORDER BY s.data_inicio";

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, currentPeriodId)) {
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String description = resultSet.getString("descricao");
                Date dataInicio = resultSet.getDate("data_inicio");
                Date dataFim = resultSet.getDate("data_fim");

                SprintModel sprint = new SprintModel(id, description, dataInicio, dataFim);
                sprintList.add(sprint);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectCurrentPeriodSprints: " + e.getMessage());
        }

        return sprintList;
    }
}

//    TODO: fazer uma query que busca todas as sprints do periodo atual, para usar no lugar do selectSprints
//    da linha 38 do PeerEvaluationController (selectedPeriodId). Dica: periodo eh baseado em semestre e ano, pode fazer no Utils essa verificacao
