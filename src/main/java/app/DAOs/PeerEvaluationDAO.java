package app.DAOs;

import app.models.EvaluationModel;
import app.helpers.DatabaseConnection;
import java.sql.PreparedStatement;
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

        try (PreparedStatement statement = DatabaseConnection.getConnection(true).prepareStatement(sql)) {
            statement.setInt(1, sprintId);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String evaluatorName = resultSet.getString("avaliador");
                    int evaluatedStudentId = resultSet.getInt("avaliado");
                    String criteria = resultSet.getString("criterio");
                    String sprintDescription = resultSet.getString("descricao");

                    EvaluationModel evaluation = new EvaluationModel(evaluatorName, evaluatedStudentId, criteria, sprintDescription);
                    evaluations.add(evaluation);
                }
            }
        } catch (SQLException e) {
            System.out.println("Erro ao buscar avaliações: " + e.getMessage());
        }

        return evaluations;
    }
}
