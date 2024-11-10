package app.DAOs;

import app.models.EvaluationModel;
import app.helpers.DatabaseConnection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PeerEvaluationDAO {

    public List<EvaluationModel> fetchPeerEvaluations(int sprintId) {
        String sql = "SELECT u.nome AS avaliador, n.avaliado, n.criterio, s.descricao " +
                "FROM nota n " +
                "JOIN usuario u ON u.id = n.avaliador " +
                "JOIN sprint s ON s.id = n.sprint " +
                "WHERE s.id = ?";

        List<EvaluationModel> evaluations = new ArrayList<>();

        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, sprintId)) {
            while (resultSet.next()) {
                String evaluatorName = resultSet.getString("avaliador");
                int evaluatedStudentId = resultSet.getInt("avaliado");
                String criteria = resultSet.getString("criterio");
                String sprintDescription = resultSet.getString("descricao");

                // Cria um objeto EvaluationModel para cada avaliação e adiciona à lista
                EvaluationModel evaluation = new EvaluationModel(evaluatorName, evaluatedStudentId, criteria, sprintDescription);
                evaluations.add(evaluation);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de fetchPeerEvaluations: " + e.getMessage());
        }

        return evaluations;
    }
}
