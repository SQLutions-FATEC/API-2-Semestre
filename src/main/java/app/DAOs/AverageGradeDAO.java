package app.DAOs;

import app.helpers.DatabaseConnection;
import app.models.AverageGradeModel;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class AverageGradeDAO {
    private Map<String, AverageGradeModel> studentsMap = new HashMap<>();

    public Map<String, AverageGradeModel> fetchAverages(int teamId, int periodId, int sprintId) {

        String sql = "SELECT u.nome AS usuario_nome, c.nome AS criterio, " +
                "COALESCE(SUM(n.valor), 0) AS media_nota, " +
                "(SELECT COUNT(*) FROM usuario WHERE equipe = ?) AS total_users " +
                "FROM usuario u " +
                "LEFT JOIN nota n ON u.id = n.avaliado AND n.periodo = ? AND n.sprint = ? " +
                "JOIN criterio c ON n.criterio = c.id " +
                "WHERE u.equipe = ? " +
                "GROUP BY u.nome, c.nome";


        try (ResultSet resultSet = DatabaseConnection.executeQuery(sql, teamId, periodId, sprintId, teamId)) {
            while (resultSet.next()) {
                String studentName = resultSet.getString("usuario_nome");
                String criteria = resultSet.getString("criterio");
                Double averageGrade = resultSet.getDouble("media_nota");
                int totalUsers = resultSet.getInt("total_users");

                double adjustedAverage = totalUsers > 0 ? averageGrade / totalUsers : 0;
                AverageGradeModel student = studentsMap.getOrDefault(studentName, new AverageGradeModel(studentName));
                student.setAverage(criteria, adjustedAverage);
                studentsMap.put(studentName, student);
            }
        } catch (SQLException e) {
            System.out.println("Erro no SQL de fetchAverages: " + e.getMessage());
        }
        return studentsMap;
    }
}