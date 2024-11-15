package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.SprintModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SprintDAO {
    ObservableList<SprintModel> sprintList = FXCollections.observableArrayList();

    public ObservableList<SprintModel> selectSprints(int selectedPeriodId) {
        String sql = "SELECT * FROM sprint s WHERE s.periodo = ? ORDER BY s.data_inicio";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, selectedPeriodId)) {
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

//    TODO: fazer uma query que busca todas as sprints do periodo atual, para usar no lugar do selectSprints
//    da linha 38 do PeerEvaluationController (selectedPeriodId). Dica: periodo eh baseado em semestre e ano, pode fazer no Utils essa verificacao
}
