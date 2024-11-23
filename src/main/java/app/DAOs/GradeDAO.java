package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.AverageGradeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class GradeDAO {
    public int createGrade(int grade, int evaluatorId, int evaluatedId, int criteriaId, int periodId, int sprintId) {
        String sql = "INSERT INTO nota (valor, avaliador, avaliado, criterio, periodo, sprint) VALUES (?, ?, ?, ?, ?, ?)";
        int gradeId = 0;

        try {
            gradeId = DatabaseConnection.executeUpdate(sql, grade, evaluatorId, evaluatedId, criteriaId, periodId, sprintId);
        } catch (SQLException e) {
            System.out.println("Erro no SQL de createCriteria: " + e.getMessage());
        } finally {
            DatabaseConnection.closeResources();
        }
        return gradeId;
    }

    public Map<String, AverageGradeModel> selectAverages(int teamId, int periodId, int sprintId) {
        Map<String, AverageGradeModel> studentsMap = new HashMap<>();

        String sql = "SELECT u.nome AS usuario_nome, c.nome AS criterio, AVG(n.valor) AS media_nota FROM nota n " +
                        "JOIN usuario u ON u.id = n.avaliado " +
                        "JOIN periodo p ON p.id = n.periodo " +
                        "JOIN sprint s ON s.id = n.sprint " +
                        "JOIN criterio c ON n.criterio = c.id " +
                        "WHERE p.id = ? AND s.id = ? AND u.equipe = ? AND u.deleted_at IS NULL " +
                        "GROUP BY u.nome, c.nome";

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, periodId, sprintId, teamId)) {
            while (resultSet.next()) {
                String studentName = resultSet.getString("usuario_nome");
                String criteria = resultSet.getString("criterio");
                Double averageGrade = resultSet.getDouble("media_nota");

                AverageGradeModel student = studentsMap.getOrDefault(studentName, new AverageGradeModel(studentName));
                student.setAverage(criteria, averageGrade);
                studentsMap.put(studentName, student);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectAverages: " + e.getMessage());
        }
        return studentsMap;
    }

    public int selectUserGradeEvaluationBySprint(String email, int sprintId) {
        String sql = "SELECT COUNT(*) AS avaliacoes FROM nota n JOIN usuario u ON u.id = n.avaliador WHERE u.email = ? AND n.sprint = ?";
        int evaluations = 0;

        try(ResultSet resultSet = DatabaseConnection.executeQuery(sql, email, sprintId)) {
            while (resultSet.next()) {
                evaluations = resultSet.getInt("avaliacoes");
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de selectUserGradeEvaluationBySprint: " + e.getMessage());
        }
        return evaluations;
    }
}
