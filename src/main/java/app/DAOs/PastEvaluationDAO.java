package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.PastEvaluationModel;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PastEvaluationDAO {
    public ObservableList<PastEvaluationModel> selectPastEvaluations(int teamId, int sprintId) {
        ObservableList<PastEvaluationModel> evaluations = FXCollections.observableArrayList();

        String sql = "SELECT uador.nome AS avaliador, uado.nome AS avaliado, c.nome AS criterio, s.descricao, n.valor AS nota " +
                "FROM nota n " +
                "JOIN usuario uador ON uador.id = n.avaliador " +
                "JOIN usuario uado ON uado.id = n.avaliado " +
                "JOIN equipe e ON e.id = uador.equipe " +
                "JOIN sprint s ON s.id = n.sprint " +
                "JOIN criterio c ON c.id = n.criterio " +
                "WHERE e.id = ? AND s.id = ? " +
                "ORDER BY uador.id";

        sprintId = 1;
        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, teamId, sprintId)) {
            while (resultSet.next()) {
                String evaluatorName = resultSet.getString("avaliador");
                String evaluatedName = resultSet.getString("avaliado");
                String criteriaName = resultSet.getString("criterio");
                String sprintDescription = resultSet.getString("descricao");
                int nota = resultSet.getInt("nota");

                PastEvaluationModel evaluation = new PastEvaluationModel(evaluatorName, evaluatedName, criteriaName, sprintDescription, nota);
                evaluations.add(evaluation);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectPastEvaluations: " + e.getMessage());
        }
        return evaluations;
    }
}
